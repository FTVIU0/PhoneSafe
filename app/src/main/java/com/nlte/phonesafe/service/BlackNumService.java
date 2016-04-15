package com.nlte.phonesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.nlte.phonesafe.db.dao.BlackNumDao;
import com.nlte.phonesafe.utils.Constants;
import com.nlte.phonesafe.utils.ToastUtil;

/**黑名单拦截的服务，当服务开启时，则实现短信或者电话的监听，当服务停止后，短信和电话的监听应该停止
 * 1. 拦截短信：动态注册短信接收器，在接收器获取电话号码，再判断是否为黑名单号码，假如为真，则拦截
 * 2. 拦截电话：
 */
public class BlackNumService extends Service {
    private BlackNumDao blackNumDao;
    private TelephonyManager telephonyManager;//电话管理者
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获得短信内容，取得电话号码，判断是否为黑名单
            Object[] objects = (Object[])intent.getExtras().get("pdus");
            //遍历目标数组
            for (Object objs : objects){
                byte[] pdu = (byte[])objs;
                SmsMessage smsMessage = SmsMessage.createFromPdu(pdu);//创建短信
                String number = smsMessage.getOriginatingAddress();//取得电话号码
                String body = smsMessage.getMessageBody();//取得短信内容
                String newNumber = number;
                if (newNumber.startsWith("+86")){
                    newNumber = newNumber.substring(3);
                }
                //查询黑名单，假如查询不到，则返回-1；
                int mode = blackNumDao.queryBlackNumMode(newNumber);
                /**当拦截的模式为all 或者  sms  则 拦截短信
                 * all：全部拦截
                 * sms ：短信拦截
                 *  contains: 内容容纳某些关键字 ，也实现拦截
                 */
                System.out.println(mode);
                //ToastUtil.show(getApplicationContext(), "电话："+number+" 内容： "+body+" 拦截模式："+mode);
                if (mode == Constants.ALL || mode == Constants.SMS || body.contains("qwer")){
                    ToastUtil.show(getApplicationContext(), "电话："+number+" 内容： "+body+" 拦截模式："+mode);
                    //截断短信但是在Android 4.4 版本及以后，该方式不能截断系统的短信接收器 ，
//					  需要设置该短信接收器为default sms 类型，要把相关的短信功能都实现，比如 彩信，多媒体短信
                    abortBroadcast();
                }
            }
        }
    };
    //电话监听
    PhoneStateListener listener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:
                    String newNumber = incomingNumber;
                    if (newNumber.startsWith("+86")){
                        newNumber = newNumber.substring(3);
                    }
                    //查询黑名单，假如查询不到，则返回-1；
                    int mode = blackNumDao.queryBlackNumMode(newNumber);
                    /**当拦截的模式为all 或者  sms  则 拦截短信
                     * all：全部拦截
                     * sms ：短信拦截
                     *  contains: 内容容纳某些关键字 ，也实现拦截
                     */
                    System.out.println(mode);
                    if (mode == Constants.ALL || mode == Constants.CALL ){
                        endCall(incomingNumber);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    //拦截电话
    private void endCall(String incomingNumber) {

    }

    @Override
    public void onCreate() {
        blackNumDao = new BlackNumDao(this);
        /*1.短信拦截的功能*/
        IntentFilter filter = new IntentFilter();
        //优先级，最高为1000 也可以设置比该值更大的优先级；假如优先级相同，动态注册的优先比静态注册的优先级要高
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        //订阅广播事件，短信的广播事件
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        //动态之策广播接收器
        registerReceiver(receiver, filter);
        /*2.电话拦截*/
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        //注销短信接收器
        unregisterReceiver(receiver);
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

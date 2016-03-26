package com.nlte.phonesafe.business;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.lidroid.xutils.db.annotation.Id;
import com.nlte.phonesafe.entity.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**联系人的业务类
 * Created by NLTE on 2016/3/26 0026.
 */
public class ContactsManager {
    public static List<ContactInfo> getAllContactsInfo(Context context){
        List<ContactInfo> contactList = new ArrayList<ContactInfo>();
        /**
         * 获取系统通过内容提供者提供的数据
         * 1，内容访问者
         * 2，Uri1：content://com.android.contacts/raw_contacts
         *    Uri2: content://com.android.contacts/data
         * 3, 遍历cursor数据
         */
        /*1, 获取内容访问者*/
        ContentResolver contentResolver = context.getContentResolver();
        /*2,准备要访问的Uri */
        String uri1 = "content://com.android.contacts/raw_contacts";
        String uri2 = "content://com.android.contacts/data";
        /*3.1, 遍历结果:先查询表raw_contacts*/
        Cursor cursor = contentResolver.query(
                Uri.parse(uri1),
                new String[]{"contact_id"},
                null,
                null,
                null);
        while(cursor.moveToNext()){//假如有联系人，再查询联系人的详细信息
            String contactId = cursor.getString(0);
            if (contactId != null){//contactId不为null时才往下查
                //根据联系人的ID信息来获取联系人的详细信息，即查询view_data()
                Cursor dataCursor = contentResolver.query(
                        Uri.parse(uri2),
                        new String[]{"mimetype", "data1"},
                        "raw_contact_id=?",
                        new String[]{contactId},
                        null);
                ContactInfo contactInfo = new ContactInfo();
                while (dataCursor.moveToNext()){
                    //取得mimetype
                    String minetype = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                    //取得data1
                    String data1 = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                    //判断data1的数据类型
                    if ("vnd.android.cursor.item/phone_v2".equals(minetype)){
                        contactInfo.setNum(data1);
                    }else if ("vnd.android.cursor.item/name".equals(minetype)){
                        contactInfo.setName(data1);
                    }
                }
                dataCursor.close();
                contactList.add(contactInfo);
            }
        }
        cursor.close();
        return contactList;
    }
}

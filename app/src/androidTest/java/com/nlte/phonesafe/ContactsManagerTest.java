package com.nlte.phonesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.nlte.phonesafe.BuildConfig;
import com.nlte.phonesafe.business.ContactsManager;
import com.nlte.phonesafe.entity.ContactInfo;

import java.util.List;

/**
 * Created by NLTE on 2016/3/26 0026.
 */
public class ContactsManagerTest extends AndroidTestCase{

    public void testGetAllContactsInfo()  {
        List<ContactInfo> data = ContactsManager.getAllContactsInfo(getContext());
        //遍历
        Log.d("ContactsManagerTest", "fgfgfgfgfgfgfg");
        for (ContactInfo temp:data){
            Log.d("ContactsManagerTest", temp.toString());
        }
    }
}
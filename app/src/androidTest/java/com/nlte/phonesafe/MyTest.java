package com.nlte.phonesafe;

import android.test.AndroidTestCase;

/**
 * Created by NLTE on 2016/3/24 0024.
 */
public class MyTest extends AndroidTestCase {
    public void testAdd(){
        int a =1;
        int b = 2;
        int c= a+b;
        assertEquals(c, 3);
    }
}

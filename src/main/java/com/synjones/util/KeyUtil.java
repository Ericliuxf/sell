package com.synjones.util;

import java.util.Random;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/2 14:36
 */
public class KeyUtil {

    public static synchronized String getUniqueKey(){

        int a = new Random().nextInt(900000) + 100000;

        return System.currentTimeMillis()+String.valueOf(a);

    }

}

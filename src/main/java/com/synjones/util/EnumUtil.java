package com.synjones.util;

import com.synjones.enums.CodeEnum;
import jdk.internal.dynalink.beans.StaticClass;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/11 15:20
 */
public class EnumUtil {

    public static <T extends CodeEnum> T  getByCode(Integer code, Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }

}

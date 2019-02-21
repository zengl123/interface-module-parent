package com.drore.tdp.utils;

/**
 * 描述:
 * 项目名:cloud-tdp-data-interface
 *
 * @Author:ZENLIN
 * @Created 2017/12/7  9:30.
 */
public class GpsDataConvertUtil {
    /**
     * 将Integer类型的经纬度数据转换成Double类型的数据
     */
    public static Double toDoubleDegree(Integer num, boolean isPositive) {
        Double result = Arith.div(num, 360000.00);
        //isPositive表示正负数的意思,正数为tru,负数为false
        if (!isPositive) {
            result = Arith.sub(0, result);
        }
        return result;
    }
}


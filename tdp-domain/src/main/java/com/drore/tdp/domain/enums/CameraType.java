package com.drore.tdp.domain.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 监控类型枚举(0-枪机;1-半球;2-快球;3-云台)
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/25  15:16.
 */
public enum CameraType {
    one(0, "枪机"),
    two(1, "半球"),
    three(2, "快球"),
    four(3, "云台"),
    five(104, "报警柱"),
    other(-1, "其他");
    private Integer code;
    private String value;

    CameraType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 StatusEnum 。
     */
    public static CameraType find(Integer code) {
        for (CameraType cameraType : values()) {
            if (cameraType.getCode().equals(code)) {
                return cameraType;
            }
        }
        throw new IllegalArgumentException("ResultInfo CameraType not legal:" + code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<CameraType> getAllStatus() {
        List<CameraType> list = new ArrayList();
        for (CameraType cameraType : values()) {
            list.add(cameraType);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<Integer> getAllStatusCode() {
        List<Integer> list = new ArrayList();
        for (CameraType cameraType : values()) {
            list.add(cameraType.getCode());
        }
        return list;
    }
}

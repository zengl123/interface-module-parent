package com.drore.tdp.bo;

import lombok.Data;

@Data
public class ThirdEncoderDevice {
    /**
     * 编码id
     */
    private String encoderUuid;
    /**
     * 编码地址
     */
    private String encoderIp;
    /**
     * 编码端口
     */
    private Integer encoderPort;
}

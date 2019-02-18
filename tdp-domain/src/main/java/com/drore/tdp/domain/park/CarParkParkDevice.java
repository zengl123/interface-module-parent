package com.drore.tdp.domain.park;

import com.drore.tdp.domain.BaseModel;
import lombok.Data;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/1/24  11:24.
 */
@Data
public class CarParkParkDevice extends BaseModel {
    private String deviceNo;
    private String deviceName;
    private Integer totalNumber;
    private Integer remainderNumber;
    private Integer usedNumber;
}

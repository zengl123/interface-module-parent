package com.drore.tdp.constant;

/**
 * 描述:
 * 项目名:tdp-module-parent
 *
 * @Author:ZENLIN
 * @Created 2019/2/14  11:18.
 */
public interface Hk8200Constant {
    /**
     * 分页获取组织树
     */
    String FIND_CONTROL_UNIT_PAGE = "/artemis/api/common/v1/remoteControlUnitRestService/findControlUnitPage";
    /**
     * 分页获取监控点信息
     */
    String FIND_CAMERA_INFO_PAGE = "/artemis/api/common/v1/remoteCameraInfoRestService/findCameraInfoPage";
    /**
     * 根据组织编号分页获取监控点信息
     */
    String FIND_CAMERA_INFO_PAGE_BY_TREE_NODE = "/artemis/api/common/v1/remoteControlUnitRestService/findCameraInfoPageByTreeNode";

    Integer SUCCESS_CODE=200;
}

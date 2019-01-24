package com.drore.tdp.common.base;


import com.drore.tdp.common.constant.ResponseCode;

/**
 * @Description
 * @Project my-shopping-parent
 * @Package com.zl.study.common.utils
 * @ClassName BaseApiService
 * @Author 曾灵
 * @QQ|Email 3195690389|17363645521@163.com
 * @Date 2018-10-14 下午9:41
 * @Version 1.0
 * @Modified By
 */
public class BaseApiService {
    /**
     * 响应成功
     *
     * @return
     */
    public ResponseBase success() {
        return setResult(ResponseCode.SUCCESS_CODE, true, ResponseCode.SUCCESS_CODE_200_VALUE, null);
    }

    /**
     * 响应成功有响应描述信息
     *
     * @param message
     * @return
     */
    public ResponseBase success(String message) {
        return setResult(ResponseCode.SUCCESS_CODE, true, message, null);
    }

    /**
     * 响应成功有响应结果和响应描述信息
     *
     * @param data
     * @param message
     * @return
     */
    public ResponseBase success(Object data, String message) {
        return setResult(ResponseCode.SUCCESS_CODE, true, message, data);
    }

    /**
     * 响应错误
     *
     * @return
     */
    public ResponseBase error() {
        return setResult(ResponseCode.ERROR_CODE, false, ResponseCode.ERROR_CODE_500_VALUE, null);
    }

    /**
     * 响应失败有响应描述信息
     *
     * @param message
     * @return
     */
    public ResponseBase error(String message) {
        return setResult(ResponseCode.ERROR_CODE, false, message, null);
    }

    private ResponseBase setResult(Integer code, Boolean status, String message, Object data) {
        return new ResponseBase(code, status, message, data);
    }
}

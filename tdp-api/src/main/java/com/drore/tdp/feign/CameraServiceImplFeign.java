package com.drore.tdp.feign;

import com.drore.tdp.service.CameraService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description:<br>
 * @Project my-shop-parent<br>
 * @Package com.zl.study.impl.member.feign<>br
 * @ClassName MemberFeign<br>
 * @Author 曾灵<br>
 * @QQ|Email 3195690389|17363645521@163.com<br>
 * @Date 2019-01-12 上午2:30<br>
 * @Version 1.0<br>
 * @Modified By <br>
 */
@FeignClient(name = "hk-camera-7600-server")
public interface CameraServiceImplFeign extends CameraService {

}

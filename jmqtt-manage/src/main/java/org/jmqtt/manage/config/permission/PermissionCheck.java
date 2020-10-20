package org.jmqtt.manage.config.permission;

import org.jmqtt.manage.utils.Constants;
import top.hserver.core.interfaces.PermissionAdapter;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.server.context.Webkit;
import top.hserver.core.server.util.JsonResult;

@Bean
public class PermissionCheck implements PermissionAdapter {

    @Override
    public void requiresPermissions(RequiresPermissions requiresPermissions, Webkit webkit) throws Exception {
        //这里你可以实现一套自己的权限检查算法逻辑，判断，
        //如果满足权限，不用其他操作，如果不满足权限，那么你可以通过，Webkit里面的方法直接输出相关内容
        //或者自定义一个异常类，在全局异常类做相关操作
        try {
            String token = webkit.httpRequest.getHeader(Constants.MANAGE_AUTHORIZATION);
            if (token == null) {
                webkit.httpResponse.sendJson(JsonResult.error(1, "Header缺失必要参数[Authorization]"));
            }
        } catch (Exception e) {
            webkit.httpResponse.sendJson(JsonResult.error(-5, "权限校验异常"));
        }
    }

    @Override
    public void requiresRoles(RequiresRoles requiresRoles, Webkit webkit) throws Exception {
        //这里你可以实现一套自己的角色检查算法逻辑，判断，
    }

    @Override
    public void sign(Sign sign, Webkit webkit) throws Exception {
        //这里你可以实现一套自己的接口签名算法检查算法逻辑，判断，
    }
}
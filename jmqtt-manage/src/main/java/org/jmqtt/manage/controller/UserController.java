package org.jmqtt.manage.controller;

import org.jmqtt.manage.pojo.dto.LoginDTO;
import top.hserver.core.ioc.annotation.Controller;
import top.hserver.core.ioc.annotation.POST;
import top.hserver.core.server.util.JsonResult;

@Controller("/api")
public class UserController extends BaseController{

    @POST("/login")
    public JsonResult login(LoginDTO loginDTO) {
        LoginDTO login = new LoginDTO();
        return login == null ? JsonResult.error("登录失败") : JsonResult.ok().put("data", login);
    }
}

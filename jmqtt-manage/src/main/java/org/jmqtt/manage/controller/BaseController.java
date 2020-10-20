package org.jmqtt.manage.controller;

import org.jmqtt.manage.utils.Constants;
import org.jmqtt.manage.config.permission.TokenUtil;
import top.hserver.core.interfaces.HttpRequest;

public class BaseController {

    public String getUserId(HttpRequest request){
        String token = request.getHeader(Constants.MANAGE_AUTHORIZATION);
        return TokenUtil.getToken(token).getUserId();
    }

    public String[] getRoleIds(HttpRequest request){
        String token = request.getHeader(Constants.MANAGE_AUTHORIZATION);
        return TokenUtil.getToken(token).getRoleIds();
    }

}
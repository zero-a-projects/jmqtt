package org.jmqtt.manage.exception;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.commons.lang3.StringUtils;
import top.hserver.core.interfaces.GlobalException;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.server.context.Webkit;
import top.hserver.core.server.exception.ValidateException;
import top.hserver.core.server.util.JsonResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Bean
public class WebException implements GlobalException {

    @Override
    public void handler(Throwable t, int httpStatusCode, String errorDescription, Webkit webkit) {
        String message = t.getMessage();
        if (StringUtils.isNotBlank(message) && "不能找到处理当前请求的资源".equals(message)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("message", errorDescription);
            webkit.httpResponse.sendStatusCode(HttpResponseStatus.NOT_FOUND);
            webkit.httpResponse.sendTemplate("error" + File.separator + "404.ftl", map);
            return;
        }
        webkit.httpResponse.sendStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        webkit.httpResponse.sendJson(JsonResult.error(1, StringUtils.isNotBlank(errorDescription) ? errorDescription : "服务异常"));
    }
}
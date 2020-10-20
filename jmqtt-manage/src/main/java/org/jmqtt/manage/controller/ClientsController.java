package org.jmqtt.manage.controller;

import org.jmqtt.manage.utils.PageInfo;
import org.jmqtt.remoting.session.ClientSession;
import org.jmqtt.remoting.session.ConnectManager;
import top.hserver.core.interfaces.HttpResponse;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.server.util.JsonResult;

@Controller("/api")
public class ClientsController {

    /**
     * 分页查询客户端
     *
     * @param pageNum  页码数
     * @param pageSize 页容量
     * @return
     */
    @RequiresPermissions("get:/api/getClients")
    @RequestMapping(value = "/getClients", method = RequestMethod.GET)
    public PageInfo<ClientSession> getClients(Integer pageNum, Integer pageSize, HttpResponse httpResponse) {
        PageInfo<ClientSession> pageInfo = new PageInfo<>(pageNum, pageSize, ConnectManager.getInstance().getClientsInfo());
        return pageInfo;
    }

    /**
     * 踢出客户端
     *
     * @param clientId
     * @return
     */
    @RequiresPermissions("get:/api/deleteClient")
    @RequestMapping(value = "/deleteClient", method = RequestMethod.DELETE)
    public JsonResult deleteClient(String clientId, HttpResponse httpResponse) {
        try {
            ConnectManager.getInstance().removeClient(clientId);
        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.sendJson(JsonResult.error(1, "操作失败"));
        }
        return JsonResult.ok(0, "操作成功");
    }
}

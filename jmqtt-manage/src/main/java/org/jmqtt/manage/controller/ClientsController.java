package org.jmqtt.manage.controller;

import org.jmqtt.remoting.session.ClientSession;
import org.jmqtt.remoting.session.ConnectManager;
import top.hserver.core.ioc.annotation.Controller;
import top.hserver.core.ioc.annotation.GET;

import java.util.List;

@Controller
public class ClientsController {

    @GET("/getClients")
    public List<ClientSession> getClients() {
        List<ClientSession> clientsInfo = ConnectManager.getInstance().getClientsInfo();
        return clientsInfo;
    }
}

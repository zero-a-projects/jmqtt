package org.jmqtt.remoting.session;

import org.jmqtt.common.log.LoggerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConnectManager {

    private static final Logger log = LoggerFactory.getLogger(LoggerName.REMOTING);

    private Map<String, ClientSession> clientCache = new ConcurrentHashMap<>();

    private static final ConnectManager INSTANCE = new ConnectManager();

    private ConnectManager() {
    }

    public static ConnectManager getInstance() {
        return INSTANCE;
    }

    public ClientSession getClient(String clientId) {
        return this.clientCache.get(clientId);
    }

    public ClientSession putClient(String clientId, ClientSession clientSession) {
        return this.clientCache.put(clientId, clientSession);
    }

    public boolean containClient(String clientId) {
        return this.clientCache.containsKey(clientId);
    }

    public ClientSession removeClient(String clientId) {
        return this.clientCache.remove(clientId);
    }

    public List<ClientSession> getClientsInfo() {
        List<ClientSession> clientSessionList = null;
        if (!clientCache.isEmpty()) {
            clientSessionList = new ArrayList<>(clientCache.values()).stream().sorted(Comparator.comparing(ClientSession::getConnectionTime)).collect(Collectors.toList());
        }
        return clientSessionList;
    }

    public void cleanConnectCloseClientSession() {
        clientCache.forEach((key, value) -> {
            if (value.getCtx().isRemoved()) {
                clientCache.remove(key);
                log.info("Jmqtt CleanConnectCloseClientSession ClientId:{}", key);
            }
        });
    }
}

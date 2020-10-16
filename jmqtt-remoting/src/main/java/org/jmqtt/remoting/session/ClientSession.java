package org.jmqtt.remoting.session;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientSession {

    private String clientId;
    private boolean cleanSession;
    private transient ChannelHandlerContext ctx;
    private String userName;
    private Date connectionTime;
    private int heartbeatSec;
    private String connectionIp;
    private String mqttVersion;

    private transient AtomicInteger messageIdCounter = new AtomicInteger(1);

    public ClientSession() {
    }

    public ClientSession(String clientId, boolean cleanSession) {
        this.clientId = clientId;
        this.cleanSession = cleanSession;
    }

    public ClientSession(String clientId, boolean cleanSession, ChannelHandlerContext ctx) {
        this.clientId = clientId;
        this.cleanSession = cleanSession;
        this.ctx = ctx;
    }

    public String getClientId() {
        return clientId;
    }


    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public int generateMessageId() {
        int messageId = messageIdCounter.getAndIncrement();
        messageId = Math.abs(messageId % 0xFFFF);
        if (messageId == 0) {
            return generateMessageId();
        }
        return messageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getHeartbeatSec() {
        return heartbeatSec;
    }

    public void setHeartbeatSec(int heartbeatSec) {
        this.heartbeatSec = heartbeatSec;
    }

    public String getConnectionIp() {
        return connectionIp;
    }

    public void setConnectionIp(String connectionIp) {
        this.connectionIp = connectionIp;
    }

    public String getMqttVersion() {
        return mqttVersion;
    }

    public void setMqttVersion(String mqttVersion) {
        this.mqttVersion = mqttVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSession that = (ClientSession) o;
        return Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}

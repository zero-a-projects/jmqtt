package org.jmqtt.broker.acl;

/**
 * publish messages and subscribe topic permisson
 */
public interface PubSubPermission {

    /**
     * verify the clientId whether can publish message to the topic
     */
    boolean publishVerify(String clientId,String topic,String remoteIp);

    /**
     * verify the clientId whether can subscribe the topic
     */
    boolean subscribeVerify(String clientId,String topic,String remoteIp);

}

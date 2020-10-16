package org.jmqtt.store.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.lang3.StringUtils;
import org.jmqtt.common.helper.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HazelcastBean {

    protected static final Logger log = LoggerFactory.getLogger(HazelcastBean.class);

    private HazelcastInstance hazelcastInstance = null;

    private static HazelcastBean hazelcastBean = null;

    public static HazelcastBean getInstance() {
        if (null == hazelcastBean) {
            synchronized (HazelcastBean.class) {
                if (null == hazelcastBean) {
                    hazelcastBean = new HazelcastBean();
                }
            }
        }
        return hazelcastBean;
    }

    private HazelcastBean() {
        initHazelcast();
    }

    private void initHazelcast() {
        HazelcastInstance getHazelcastInstance = Hazelcast.getHazelcastInstanceByName(Constants.HAZELCAST_JMQTT);
        if (getHazelcastInstance != null) {
            log.warn("Hazelcast already initialized");
            this.hazelcastInstance = getHazelcastInstance;
        }
        Config config = new Config();
        config.setInstanceName(Constants.HAZELCAST_JMQTT);
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        //关闭组播协议组建集群
        joinConfig.getMulticastConfig().setEnabled(false);
        //开启TCP协议组建集群
        joinConfig.getTcpIpConfig().setEnabled(true);
        //设置连接超时时间
        config.getNetworkConfig().getJoin().getTcpIpConfig().setConnectionTimeoutSeconds(30);
        //从系统变量中获取
        String clusterMemberValue = System.getProperty("clusterMember");
        if (StringUtils.isNotBlank(clusterMemberValue)) {
            String[] members = clusterMemberValue.split(",");
            for (String member : members) {
                String clusterMemberAddress = member + ":5701";
                joinConfig.getTcpIpConfig().addMember(clusterMemberAddress);
            }
        }
        //初始化Jmqtt集群Topic
        TopicConfig jMqttClusterTopic = initJmqttTopic(Constants.JMQTT_CLUSTER_TOPIC);
        //初始化Jmatt黑名单Topic
        TopicConfig jmqttClusterBlacklistTopic = initJmqttTopic(Constants.JMQTT_CLUSTER_BLACKLIST_TOPIC);

        config.addTopicConfig(jMqttClusterTopic);
        config.addTopicConfig(jmqttClusterBlacklistTopic);
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    private TopicConfig initJmqttTopic(String topicName) {
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setName(topicName);
        topicConfig.setGlobalOrderingEnabled(true);
        return topicConfig;
    }

    public HazelcastInstance getHazelcastInstance() {
        if (null == this.hazelcastInstance) {
            initHazelcast();
        }
        return this.hazelcastInstance;
    }
}

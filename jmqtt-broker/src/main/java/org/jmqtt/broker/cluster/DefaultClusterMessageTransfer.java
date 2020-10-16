
package org.jmqtt.broker.cluster;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import org.jmqtt.broker.cluster.command.CommandCode;
import org.jmqtt.broker.cluster.command.CommandReqOrResp;
import org.jmqtt.broker.dispatcher.MessageDispatcher;
import org.jmqtt.common.helper.Constants;
import org.jmqtt.common.helper.ThreadFactoryImpl;
import org.jmqtt.store.hazelcast.HazelcastBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultClusterMessageTransfer extends ClusterMessageTransfer {

    private ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryImpl("HAZELCAST_CLUSTER_CONSUME_THREAD"));

    public DefaultClusterMessageTransfer(MessageDispatcher messageDispatcher) {
        super(messageDispatcher);
    }

    @Override
    public void startup() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                subscribe();
            }
        });
    }

    private void subscribe() {
        ITopic<String> topic = HazelcastBean.getInstance().getHazelcastInstance().getTopic(Constants.JMQTT_CLUSTER_TOPIC);
        topic.addMessageListener(new MessageListener<String>() {
            @Override
            public void onMessage(Message<String> message) {
                CommandReqOrResp request = new CommandReqOrResp(CommandCode.MESSAGE_CLUSTER_TRANSFER, JSONObject.parseObject(message.getMessageObject(),
                        org.jmqtt.common.model.Message.class));
                consumeClusterMessage(request);
            }
        });
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public CommandReqOrResp sendMessage(CommandReqOrResp commandReqOrResp) {
        ITopic<String> topic = HazelcastBean.getInstance().getHazelcastInstance().getTopic(Constants.JMQTT_CLUSTER_TOPIC);
        topic.publish(JSONObject.toJSONString(commandReqOrResp.getBody()));
        CommandReqOrResp response = new CommandReqOrResp(commandReqOrResp.getCommandCode());
        return response;
    }
}
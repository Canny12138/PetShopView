package com.example.controller;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/30 14:56
 **/
public class SyncProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group_test");

        producer.setNamesrvAddr("159.75.4.201:9876");
        producer.setSendMsgTimeout(1000000000);
        producer.start();
        for(int i=0;i<10;i++){
            Message msg = new Message("TopicTest","TagA",("hello rocketmq"+i).getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n",sendResult);
        }
        producer.shutdown();
    }
}

package com.enhao.learning.in.kafka.sample.producer_client.send_message_mode;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.awt.*;
import java.util.Properties;

/**
 * 异步发送消息模式
 *
 * @author enhao
 */
@Slf4j
public class AsyncProducer {
    /**
     * kafka 集群地址
     */
    public static final String brokerList = "localhost:9092";

    /**
     * 消息发往的主题topic
     */
    public static final String topic = "async-message-mode-topic";

    public static Properties initConfig() {
        Properties properties = new Properties();
        // 集群地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        // 序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 客户端id
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        return properties;
    }

    public static void main(String[] args) {
        Properties properties = initConfig();
        // 生产者实例
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 消息对象
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, async producer");
        // 发送消息
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("async producer send error", exception);
            } else {
                System.out.println(metadata.topic() + "-" + metadata.partition() + "-" + metadata.offset());
            }
        });
    }
}

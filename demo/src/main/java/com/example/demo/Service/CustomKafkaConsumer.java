package com.example.demo.Service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomKafkaConsumer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "my-group2");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        Map<TopicPartition, Long> partitionOffsetLimits = new HashMap<>();
        String topic = "my-topic";
        // partitionOffsetLimits.put(new TopicPartition(topic, 0), 20L);
        // partitionOffsetLimits.put(new TopicPartition(topic, 1), 30L);
        // partitionOffsetLimits.put(new TopicPartition(topic, 2), 55L);
        // partitionOffsetLimits.put(new TopicPartition(topic, 3), 50L);
        partitionOffsetLimits.put(new TopicPartition(topic, 4), 60L);

        for (Map.Entry<TopicPartition, Long> entry : partitionOffsetLimits.entrySet()) {
            TopicPartition partition = entry.getKey();
            Long offset = entry.getValue();
            consumer.assign(Collections.singletonList(partition));
            consumer.seek(partition, offset);
            System.out.println("Consumer will start from offset " + offset + " for partition " + partition.partition());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted, Failed to complete operation");
            }
        }
        consumer.close();
    }
}
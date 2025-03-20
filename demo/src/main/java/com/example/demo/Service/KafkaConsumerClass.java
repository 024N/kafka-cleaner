// package com.example.demo.Service;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.apache.kafka.clients.consumer.Consumer;
// import org.apache.kafka.clients.consumer.ConsumerRecord;
// import org.apache.kafka.common.TopicPartition;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
// import org.springframework.kafka.support.Acknowledgment;
// import org.springframework.stereotype.Component;

// import java.util.HashMap;
// import java.util.Map;

// @Component
// public class KafkaConsumerClass {

//     private final ObjectMapper objectMapper = new ObjectMapper();
//     private final Map<TopicPartition, Long> partitionOffsetLimits = new HashMap<>();

//     public KafkaConsumerClass() {
//         String topic = "my-topic";
//         partitionOffsetLimits.put(new TopicPartition(topic, 0), 20L);
//         partitionOffsetLimits.put(new TopicPartition(topic, 1), 30L);
//         partitionOffsetLimits.put(new TopicPartition(topic, 2), 40L);
//         partitionOffsetLimits.put(new TopicPartition(topic, 3), 50L);
//         partitionOffsetLimits.put(new TopicPartition(topic, 4), 60L);
//     }

//     @KafkaListener(id = "app", topics = {"my-topic"}, groupId = "my-group4", concurrency = "10", containerFactory = "kafkaManualAckListenerContainerFactory")
//     public void listen(String message, ConsumerRecordMetadata meta, Acknowledgment ack, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer) { // manuel ack yapılacak < Acknowledgment ack > parametresi eklenmeli
//         try {
//             // System.out.println("Received message: " + message + " and Meta:" + meta + " Thread: " + Thread.currentThread().getName() + " ID:" + Thread.currentThread().getId());
//             if (meta != null) {
//                 TopicPartition partition = new TopicPartition(meta.topic(), meta.partition());
//                 long offset = meta.offset();
//                 Long offsetLimit = partitionOffsetLimits.get(partition);

//                 if (offsetLimit != null && offset <= offsetLimit) {
//                     ack.acknowledge();
//                 } else {
//                     System.out.println("Offset is greater than limit for partition " + partition + ", stopping consumption.");
//                     consumer.pause(consumer.assignment());
//                 }
//             }
//         } catch (Exception e) {
//             System.err.println("Error processing message: " + e.getMessage());
//         }
//     }
// }
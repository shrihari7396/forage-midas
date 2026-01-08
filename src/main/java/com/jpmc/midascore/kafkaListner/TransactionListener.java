package com.jpmc.midascore.kafkaListner;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group"
    )
    public void listen(Transaction transaction) {
        // For Task 2, do nothing.
        // Just receiving the transaction is enough.
        System.out.println("Received transaction: " + transaction);
    }
}

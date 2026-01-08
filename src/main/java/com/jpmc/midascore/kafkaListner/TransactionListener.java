package com.jpmc.midascore.kafkaListner;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    protected final DatabaseConduit  databaseConduit;

    public TransactionListener(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group"
    )
    @Transactional
    public void listen(Transaction transaction) {
        // For Task 2, do nothing.
        // Just receiving the transaction is enough.
        long senderId = transaction.getSenderId();
        long recipientId = transaction.getRecipientId();
        float amount = transaction.getAmount();

        UserRecord sender = databaseConduit.getUserRecordById(senderId);
        UserRecord recipient = databaseConduit.getUserRecordById(recipientId);

        if(sender == null || recipient == null) {
            return;
        }

        if(amount <= sender.getBalance()) {
            sender.setBalance(sender.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            databaseConduit.save(sender);
            databaseConduit.save(recipient);
        }

        System.out.println("Received transaction: " + transaction);
    }
}

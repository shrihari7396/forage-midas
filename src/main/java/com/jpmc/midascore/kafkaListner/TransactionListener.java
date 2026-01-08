package com.jpmc.midascore.kafkaListner;

import com.jpmc.midascore.clients.IncentiveClient;
import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.Transaction;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    protected final DatabaseConduit  databaseConduit;

    static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    protected final IncentiveClient incentiveClient;

    public TransactionListener(DatabaseConduit databaseConduit,  IncentiveClient incentiveClient) {
        this.databaseConduit = databaseConduit;
        this.incentiveClient = incentiveClient;
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

        // This for task 4
        float incentiveAmount = incentiveClient.getIncentive(transaction);
        if(amount <= sender.getBalance()) {
            sender.setBalance(sender.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount + incentiveAmount);
            databaseConduit.save(sender);
            databaseConduit.save(recipient);
        }
//        logger.info("Received transaction: {}", transaction);
        logger.info(
                "Processed transaction sender={}, recipient={}, amount={}, incentive={}",
                senderId, recipientId, amount, incentiveAmount
        );

    }
}

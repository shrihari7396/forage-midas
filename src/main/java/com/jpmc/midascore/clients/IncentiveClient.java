package com.jpmc.midascore.clients;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveClient {

    private final RestTemplate restTemplate;

    static final Logger logger = LoggerFactory.getLogger(IncentiveClient.class);

    public IncentiveClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public float getIncentive(Transaction transaction) {
        Balance response = restTemplate.postForObject(
                "http://localhost:8085/incentive",
                transaction,
                Balance.class
        );
        assert response != null;
        return response.getAmount();
    }
}

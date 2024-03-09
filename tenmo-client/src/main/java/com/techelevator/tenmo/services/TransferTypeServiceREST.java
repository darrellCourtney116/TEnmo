package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeServiceREST implements TransferTypeService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferTypeServiceREST(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
    }

    @Override
    public TransferType getTransferTypeById(int id) {
        HttpEntity entity = createEntity();
        TransferType transferType = new TransferType();
        try {
            transferType = restTemplate.exchange(baseUrl + "transfertype/" + id, HttpMethod.GET, entity, TransferType.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    private HttpEntity<Void> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

    @Override
    public TransferType getTransferTypeFromDesc(String description) {
        HttpEntity entity = createEntity();
        TransferType transferType = new TransferType();
        try {
            transferType = restTemplate.exchange(baseUrl + "transfertype/" + description, HttpMethod.GET,
                    entity, TransferType.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }
}

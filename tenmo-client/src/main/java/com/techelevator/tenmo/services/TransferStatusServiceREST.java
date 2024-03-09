package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusServiceREST implements TransferStatusService{
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferStatusServiceREST(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
    }
    @Override
    public TransferStatus getTransferStatusById(int id) {
        HttpEntity<Void> entity = createEntity();
        TransferStatus transferStatus = new TransferStatus();
        try {
            transferStatus = restTemplate.exchange(baseUrl + "transferstatus/" + id,
                    HttpMethod.GET, entity, TransferStatus.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatus(String description) {
        HttpEntity<Void> entity = createEntity();
        TransferStatus transferStatus = new TransferStatus();
        try {
            transferStatus = restTemplate.exchange(baseUrl + "transferstatus/" + description,
                    HttpMethod.GET, entity, TransferStatus.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatus;
    }

    private HttpEntity<Void> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }
}

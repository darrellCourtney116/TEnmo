package com.techelevator.tenmo.services;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountServiceREST implements AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthenticatedUser currentUser;

    public AccountServiceREST(String base_url, AuthenticatedUser currentUser) {
            this.baseUrl = base_url;
            this.currentUser = currentUser;
    }


    @Override
    public BigDecimal getBalance() {
        HttpEntity entity = createEntity();
        Balance balance = null;
        try {
            balance =
                    restTemplate.exchange(baseUrl + "balance/" + currentUser.getUser().getId(), HttpMethod.GET, entity, Balance.class).getBody();
            //balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance.getBalance();
    }

    private HttpEntity<Void> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

    @Override
    public Account getAccountByUserId(int userId) {
        HttpEntity entity = createEntity();
        Account account = new Account();
        try {
            account = restTemplate.exchange(baseUrl + "account/user/" + userId,
                    HttpMethod.GET,
                    entity,
                    Account.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    @Override
    public Account getAccountById(int id) {
        HttpEntity entity = createEntity();
        Account account = new Account();
        try {
            account = restTemplate.exchange(baseUrl + "account/" + id, HttpMethod.GET, entity,
                    Account.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }
}

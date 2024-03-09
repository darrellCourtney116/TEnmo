package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserServiceREST implements UserService{
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private String baseUrl;

    public UserServiceREST(String baseUrl, AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
        this.baseUrl = baseUrl;
    }

    @Override
    public User[] getUsers() {
        User[] users = null;
        HttpEntity entity = createEntity();
        try {
            users = restTemplate.exchange(baseUrl + "users/", HttpMethod.GET,
                    entity, User[].class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        User user = new User();
        HttpEntity entity = createEntity();
        try {
            user = restTemplate.exchange(baseUrl + "users/" + id, HttpMethod.GET,
                    entity, User.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        HttpEntity entity = createEntity();
        try {
            user = restTemplate.exchange(baseUrl + "users/" + username, HttpMethod.GET,
                    entity, User.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    private HttpEntity<Void> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }
}

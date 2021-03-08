package org.example;

import org.example.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

/**
 * Hello world!
 *
 */
public class RestClient {

    private static final String URL = "http://91.241.64.178:7081/api/users";
    private static final StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
        RestClient restClient = new RestClient();

        String header = restClient.getAllUsers();

        restClient.createUser(header);
        restClient.updateUser(header);
        restClient.deleteUser(header);

        System.out.println(result);
    }

    private String getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> result = getRestTemplate().exchange(URL, HttpMethod.GET, entity, String.class);

        return result.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    private void createUser(String header) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", header);

        User newUser = new User(3L, "James", "Brown", (byte) 10);

        HttpEntity<User> requestBody = new HttpEntity<>(newUser, headers);

        ResponseEntity<String> response = getRestTemplate().exchange(URL, HttpMethod.POST, requestBody, String.class);

        result.append(response.getBody());

    }

    private void updateUser(String header) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", header);

        User user = new User(3L, "Thomas", "Shelby", (byte) 10);

        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = getRestTemplate().exchange(URL, HttpMethod.PUT, requestBody, String.class);

        result.append(response.getBody());
    }

    private void deleteUser(String header) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", header);

        HttpEntity<User> requestBody = new HttpEntity<>(headers);

        ResponseEntity<String> response = getRestTemplate().exchange(URL + "/" + 3, HttpMethod.DELETE, requestBody, String.class);

        result.append(response.getBody());
    }

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}

package com.example.user_management.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Component
@Slf4j
public class QuizManagementServiceClient {

    private final RestTemplate restTemplate;

    private static final String QUIZ_SERVICE_BASE_URL = "http://localhost:8082/api/v1/quiz";

    public QuizManagementServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getQuizDetailsByUserId(Long userId) {
        try {
            log.info("Calling Quiz Management Service for userId: {}", userId);

            String url = UriComponentsBuilder.fromHttpUrl(QUIZ_SERVICE_BASE_URL)
                    .path("/details")
                    .queryParam("userId", userId)
                    .toUriString();

            // Make the HTTP request and handle the response
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Check if the response is not null and handle accordingly
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.warn("Received non-OK response from Quiz Management Service: {}", response.getStatusCode());
                return null;
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error communicating with Quiz Management Service for userId {}: {} - {}", userId, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to fetch quiz details: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while calling Quiz Management Service for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Failed to fetch quiz details", e);
        }
    }

    public boolean validateUserQuizParticipation(Long userId, Long quizId) {
        try {
            log.info("Validating user {} participation in quiz {}", userId, quizId);

            String url = UriComponentsBuilder.fromHttpUrl(QUIZ_SERVICE_BASE_URL)
                    .path("/validate-participation")
                    .queryParam("userId", userId)
                    .queryParam("quizId", quizId)
                    .toUriString();

            // Make the HTTP request and handle the response
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody() != null ? response.getBody() : false;
            } else {
                log.warn("Received non-OK response from Quiz Management Service: {}", response.getStatusCode());
                return false;
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error validating quiz participation for user {} in quiz {}: {} - {}", userId, quizId, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Quiz participation validation failed: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while validating quiz participation for user {}: {}", userId, e.getMessage());
            throw new RuntimeException("Quiz participation validation failed", e);
        }
    }
}

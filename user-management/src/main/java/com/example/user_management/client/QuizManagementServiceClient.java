package com.example.user_management.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QuizManagementServiceClient {

    private final RestTemplate restTemplate;
    private static final String QUIZ_SERVICE_BASE_URL = "http://localhost:8082/api/v1/quizzes";

    public QuizManagementServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches quiz details by userId from the Quiz Management Service.
     */
    public String getQuizDetailsByUserId(Long userId) {
        try {
            log.info("Calling Quiz Management Service for userId: {}", userId);

            // Construct URL with query parameter
            String url = UriComponentsBuilder.fromHttpUrl(QUIZ_SERVICE_BASE_URL)
                    .queryParam("userId", userId)
                    .toUriString();

            log.debug("Making GET request to URL: {}", url);

            // Make HTTP GET request
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Check response status
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.warn("Received non-OK response from Quiz Management Service: {}",
                        response.getStatusCode());
                return null; // Or handle this differently
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error communicating with Quiz Management Service for userId {}: {} - {}", 
                      userId, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to fetch quiz details: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while calling Quiz Management Service for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Failed to fetch quiz details", e);
        }
    }

    /**
     * Validates user participation in a quiz by userId and quizId.
     */
    public boolean validateUserQuizParticipation(Long userId, Long quizId) {
        try {
            log.info("Validating participation for userId {} in quizId {}", userId, quizId);

            // Construct URL with query parameters
            String url = UriComponentsBuilder.fromHttpUrl(QUIZ_SERVICE_BASE_URL)
                    .path("/validate-participation")
                    .queryParam("userId", userId)
                    .queryParam("quizId", quizId)
                    .toUriString();

            log.debug("Making GET request to URL: {}", url);

            // Make HTTP GET request to validate participation
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

            // Return the participation validation result
            return response.getStatusCode().is2xxSuccessful() && response.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("Error validating participation for userId {} in quizId {}: {} - {}",
                      userId, quizId, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Quiz participation validation failed: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while validating participation for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Quiz participation validation failed", e);
        }
    }
}

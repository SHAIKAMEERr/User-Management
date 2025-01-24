package com.example.user_management.service.impl;

import com.example.user_management.client.QuizManagementServiceClient;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private final QuizManagementServiceClient quizManagementServiceClient;

    // Constructor Injection
    public UserManagementService(QuizManagementServiceClient quizManagementServiceClient) {
        this.quizManagementServiceClient = quizManagementServiceClient;
    }

    /**
     * Fetches quiz details for a user.
     */
    public String getUserQuizDetails(Long userId) {
        try {
            // Delegate to QuizManagementServiceClient
            return quizManagementServiceClient.getQuizDetailsByUserId(userId);
        } catch (Exception e) {
            // Log exception and handle accordingly
            throw new RuntimeException("Error occurred while fetching quiz details for userId: " + userId, e);
        }
    }

    /**
     * Validates if the user has participated in a quiz.
     */
    public boolean validateUserQuizParticipation(Long userId, Long quizId) {
        try {
            // Delegate to QuizManagementServiceClient
            return quizManagementServiceClient.validateUserQuizParticipation(userId, quizId);
        } catch (Exception e) {
            // Log exception and handle accordingly
            throw new RuntimeException("Error occurred while validating quiz participation for userId: " + userId, e);
        }
    }
}

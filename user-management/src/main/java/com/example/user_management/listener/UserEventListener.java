package com.example.user_management.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.user_management.dao.UserEntity;
import com.example.user_management.enums.Status;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEventListener {

    @Autowired
    private JavaMailSender mailSender;  // Email sending service (JavaMailSender)

    // Handle user creation event
    @EventListener
    public void handleUserCreatedEvent(UserEntity user) {
        log.info("User created event received for user: {}", user.getEmail());

        // Check if the user is ACTIVE
        if (user.getStatus() == Status.ACTIVE) {
            log.info("User is active, sending welcome email to user: {}", user.getEmail());
            sendWelcomeEmail(user.getEmail());
        } else {
            log.warn("User is not active; no welcome email sent. Status: {}", user.getStatus());
        }
    }

    // Handle user update event
    @EventListener
    public void handleUserUpdatedEvent(UserEntity user) {
        log.info("User updated event received for user: {}", user.getEmail());

        // Placeholder for any business logic required during user update
        log.info("Updating user details for user: {}", user.getEmail());

        // Here you might want to update related entities or notify other services
    }

    // Handle user deletion event
    @EventListener
    public void handleUserDeletedEvent(UserEntity user) {
        log.info("User deleted event received for user: {}", user.getEmail());

        // Placeholder for any business logic required during user deletion
        log.info("Performing cleanup operations for deleted user: {}", user.getEmail());

        // Example cleanup logic: Delete related records from other services
        cleanupDeletedUserData(user);
    }

    // Mock method to send a welcome email
    private void sendWelcomeEmail(String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("no-reply@myapp.com");
            helper.setTo(email);
            helper.setSubject("Welcome to Our Platform");
            helper.setText("Dear user, welcome to our platform! We're excited to have you.");

            // Send the email
            mailSender.send(mimeMessage);
            log.info("Welcome email sent successfully to: {}", email);
        } catch (Exception e) {
            log.error("Failed to send welcome email to: {}", email, e);
        }
    }

    // Placeholder for user data cleanup
    private void cleanupDeletedUserData(UserEntity user) {
        try {
            // Perform necessary data cleanup here, like removing records in other services or databases
            log.info("Cleaning up user data for deleted user: {}", user.getEmail());
            // Add cleanup logic, e.g., removing user data from related services
        } catch (Exception e) {
            log.error("Error occurred during cleanup of user data for: {}", user.getEmail(), e);
        }
    }
}

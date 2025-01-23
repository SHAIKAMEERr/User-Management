package com.example.user_management.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class HmacSHA256Utils {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * Generates an HMAC SHA-256 hash for the given data using the provided secret key.
     *
     * @param data      The input data to hash.
     * @param secretKey The secret key used for hashing.
     * @return The Base64-encoded HMAC SHA-256 hash.
     * @throws IllegalArgumentException if data or secretKey is null or empty.
     * @throws HmacSHA256Exception      if any error occurs during hash generation.
     */
    public static String generateHmacSHA256(String data, String secretKey) {
        if (data == null || secretKey == null || data.isEmpty() || secretKey.isEmpty()) {
            log.error("Invalid input: data and secretKey must not be null or empty");
            throw new IllegalArgumentException("Data and secretKey must not be null or empty");
        }

        try {
            log.debug("Generating HMAC SHA-256 hash");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            log.error("Error generating HMAC SHA-256: {}", e.getMessage(), e);
            throw new HmacSHA256Exception("Error generating HMAC SHA-256", e);
        }
    }

    /**
     * Custom exception for HMAC SHA-256 related errors.
     */
    public static class HmacSHA256Exception extends RuntimeException {
        public HmacSHA256Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

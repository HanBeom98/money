package com.sparta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ApiGatewayController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE = "http://user-service";
    private static final String VIDEO_SERVICE = "http://video-service";

    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

    // User Service로 요청을 프록시
    @GetMapping("/api/users/**")
    public ResponseEntity<String> forwardUserRequests(HttpServletRequest request) {
        String backendUrl = USER_SERVICE + request.getRequestURI();
        return forwardRequest(backendUrl);
    }

    // Video Service로 요청을 프록시
    @GetMapping("/api/videos/**")
    public ResponseEntity<String> forwardVideoRequests(HttpServletRequest request) {
        String backendUrl = VIDEO_SERVICE + request.getRequestURI();
        return forwardRequest(backendUrl);
    }

    // 공통 메서드: 특정 백엔드 서비스로 요청을 프록시
    private ResponseEntity<String> forwardRequest(String backendUrl) {
        try {
            // RestTemplate을 사용하여 요청을 백엔드로 전달
            ResponseEntity<String> response = restTemplate.getForEntity(backendUrl, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpStatusCodeException e) {
            logger.error("HTTP Status error when forwarding request to {}", backendUrl, e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            logger.error("Service unavailable for {}", backendUrl, e);
            return ResponseEntity.status(500).body("Service unavailable: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error forwarding request to {}", backendUrl, e);
            return ResponseEntity.status(500).body("Error forwarding request: " + e.getMessage());
        }
    }
}

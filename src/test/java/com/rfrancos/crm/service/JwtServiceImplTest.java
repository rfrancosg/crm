package com.rfrancos.crm.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rfrancos.crm.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;


class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    private final String secretKey = "mysecretkeymysecretkeymysecretkeymysecretkey"; // 32-byte key for HS256
    private final long expirationTime = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        setPrivateField(jwtService, "secretKey", secretKey);
        setPrivateField(jwtService, "jwtExpiration", expirationTime);

        when(userDetails.getUsername()).thenReturn("testuser");
    }

    @Test
    void generateToken_ShouldReturnNonEmptyToken() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_WithInvalidUsername_ShouldReturnFalse() {
        String token = jwtService.generateToken(userDetails);
        when(userDetails.getUsername()).thenReturn("invaliduser");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertFalse(isValid);
    }

    @Test
    void extractClaim_ShouldReturnExpectedClaimValue() {
        String token = jwtService.generateToken(userDetails);

        String subject = jwtService.extractClaim(token, Claims::getSubject);

        assertEquals("testuser", subject);
    }

    @Test
    void getExpirationTime_ShouldReturnConfiguredExpirationTime() {
        long expiration = jwtService.getExpirationTime();

        assertEquals(expirationTime, expiration);
    }

    private void setPrivateField(Object target, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}


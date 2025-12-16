package com.example.demo.service;

import com.example.demo.model.Session;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Tạo session ID ngẫu nhiên
     */
    public String generateSessionId() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    /**
     * Tạo và lưu session mới
     */
    public Session createSession(String userId, String email) {
        String sessionId = generateSessionId();
        Instant expiresAt = Instant.now().plus(24, ChronoUnit.HOURS); // Session hết hạn sau 24 giờ
        
        Session session = new Session(sessionId, userId, email, expiresAt);
        sessions.put(sessionId, session);
        
        // Cleanup expired sessions periodically (có thể thêm scheduled task nếu cần)
        cleanupExpiredSessions();
        
        return session;
    }

    /**
     * Lấy session theo session ID
     */
    public Session getSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null && !session.isValid()) {
            sessions.remove(sessionId);
            return null;
        }
        return session;
    }

    /**
     * Kiểm tra session có hợp lệ không
     */
    public boolean isSessionValid(String sessionId) {
        Session session = getSession(sessionId);
        return session != null && session.isValid();
    }

    /**
     * Revoke (thu hồi) session
     */
    public boolean revokeSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null && !session.isRevoked()) {
            session.setRevoked(true);
            return true;
        }
        return false;
    }

    /**
     * Revoke tất cả sessions của một user
     */
    public void revokeAllUserSessions(String userId) {
        sessions.values().stream()
                .filter(session -> session.getUserId().equals(userId) && !session.isRevoked())
                .forEach(session -> session.setRevoked(true));
    }

    /**
     * Xóa các session đã hết hạn
     */
    private void cleanupExpiredSessions() {
        Instant now = Instant.now();
        sessions.entrySet().removeIf(entry -> {
            Session session = entry.getValue();
            return now.isAfter(session.getExpiresAt()) || session.isRevoked();
        });
    }

    /**
     * Lấy số lượng sessions hiện tại (để debug/monitoring)
     */
    public int getActiveSessionCount() {
        cleanupExpiredSessions();
        return (int) sessions.values().stream()
                .filter(Session::isValid)
                .count();
    }
}


package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    // Lưu user trong memory bằng ConcurrentHashMap để thread-safe
    private final Map<String, User> users = new ConcurrentHashMap<>();

    /**
     * Lưu hoặc cập nhật user
     */
    public User save(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        user.setUpdatedAt(java.time.LocalDateTime.now());
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Tìm user theo ID
     */
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Tìm user theo email
     */
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();
    }

    /**
     * Lấy tất cả users
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Xóa user theo ID
     */
    public boolean deleteById(String id) {
        return users.remove(id) != null;
    }

    /**
     * Kiểm tra user có tồn tại không
     */
    public boolean existsById(String id) {
        return users.containsKey(id);
    }

    /**
     * Đếm số lượng users
     */
    public long count() {
        return users.size();
    }

    /**
     * Xóa tất cả users
     */
    public void deleteAll() {
        users.clear();
    }
}


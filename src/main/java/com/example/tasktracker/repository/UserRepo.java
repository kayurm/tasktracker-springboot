package com.example.tasktracker.repository;

import com.example.tasktracker.model.UserDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class UserRepo {

    private final Map<Long, UserDto> usersMap = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public UserDto getById(long id) {
        return usersMap.get(id);
    }

    public List<UserDto> getAllNotDeleted() {
//        return new ArrayList<>(usersMap.values());

        return usersMap.values().stream()
                .filter(u -> !u.isDeleted())
                .collect(Collectors.toList());
    }

    public UserDto save(UserDto user) {
        long assignedId = counter.incrementAndGet();
        user.setId(assignedId);
        user.setDeleted(false);
        usersMap.put(assignedId, user);
        return user;
    }

    public UserDto update(UserDto user) {
        long id = user.getId();
        usersMap.replace(id, user);
        return user;
    }

    public boolean idExists(long id) {
        return usersMap.containsKey(id);
    }

    public boolean emailExists(String email) {
        return usersMap.values().stream().anyMatch(u -> email.equals(u.getEmail()));
    }

    public String delete(long userId) {
        usersMap.get(userId).setDeleted(true);
        return "deleted user with id: " + userId;
    }
}

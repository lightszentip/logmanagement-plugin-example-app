package com.lightszentip.sample.logmanagementapp;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.lightszentip.sample.logmanagementapp.model.User;

@Service
public class AppServiceImpl implements AppService {

    private final ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<Long, User>();

    @Override
    public void addUser(final User user) {
	this.userMap.put(user.getId(), user);
    }

    @Override
    public Collection<User> getUsers() {
	return this.userMap.values();
    }

    @Override
    public User removeUser(final Long userId) {
	return this.userMap.remove(userId);
    }

}

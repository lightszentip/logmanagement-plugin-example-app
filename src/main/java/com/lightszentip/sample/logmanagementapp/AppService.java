package com.lightszentip.sample.logmanagementapp;

import java.util.Collection;

import com.lightszentip.sample.logmanagementapp.model.User;

public interface AppService {
    public void addUser(final User user);

    public Collection<User> getUsers();

    public User removeUser(final Long userId);
}

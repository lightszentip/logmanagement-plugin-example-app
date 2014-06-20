package com.lightszentip.sample.logmanagementapp;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lightszentip.sample.logmanagementapp.model.User;

@Controller
public class UserController {

	private final AppService appService;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	private static final String templateSuccess = "User %s was removed.";
	private static final String templateError = "No user was removed.";

	@Autowired
	public UserController(final AppService appService) {
		super();
		this.appService = appService;
	}

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value="/createuser",method = RequestMethod.POST )
	public @ResponseBody User createUser(
			@RequestParam(value = "name", required = false, defaultValue = "User") final String name) {
		final User user = new User(this.counter.incrementAndGet(), name);
		this.appService.addUser(user);
		LOG.debug("User was added.");
		return user;
	}

	@RequestMapping(value="/showusers",method = RequestMethod.GET)
	public @ResponseBody Collection<User> showUsers() {
		LOG.debug("Show users was called.");
		return this.appService.getUsers();
	}

	@RequestMapping(value="/removeuser",method = RequestMethod.DELETE)
	public @ResponseBody String removeUsers(
			@RequestParam(value = "userId", required = true) final Long userId) {
		final User removeUser = this.appService.removeUser(userId);
		if (removeUser != null) {
			LOG.debug("User was deleted.");
			return String.format(templateSuccess, removeUser.getUsername());
		}
		return templateError;
	}

}

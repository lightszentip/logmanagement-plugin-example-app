package com.lightszentip.sample.logmanagementapp;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lightszentip.module.logmanagement.plugin.LogManagementPlugin;
import com.lightszentip.module.logmanagement.plugin.LogManagementPluginFactory;
import com.lightszentip.module.logmanagement.plugin.util.LogLevel;

@Controller
@RequestMapping("logmanagement")
public class LogController {
	
	private final LogManagementPlugin logPlugin;
	
	public LogController() {
		this.logPlugin = LogManagementPluginFactory.getLogManagementPlugin();
	}

	@RequestMapping(value="/showLogger",method = RequestMethod.GET )
	public @ResponseBody Map<String, String> showLogger(
			@RequestParam(required = false, value = "all") final boolean showAllLogger) {
		if (showAllLogger) {
			return this.logPlugin.getAllLogger();
		}
		return this.logPlugin.getLoggerWithLogLevel();
	}
	
	@RequestMapping(value="/showRootLogLevel",method = RequestMethod.GET)
	public @ResponseBody String showRootLogLevel() {
		return this.logPlugin.getRootLogLevel();
	}

	@RequestMapping("/changeRootLogger")
	public @ResponseBody Map<String, String> changeRootLogger(
			@RequestParam(required = true, value = "logLevel") final LogLevel logLevel) {
		this.logPlugin.changeRootLogLevel(logLevel);
		return this.showLogger(false);
	}

	@RequestMapping(value="/changeLogger",method = RequestMethod.POST)
	public @ResponseBody Map<String, String> changeRootLogger(
			@RequestParam(required = true, value = "logger") final String logger,
			@RequestParam(required = true, value = "logLevel") final LogLevel logLevel) {
		this.logPlugin.setLogLevelForLogger(logger, logLevel);
		return this.showLogger(false);
	}
	
}

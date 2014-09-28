package com.karniyarik.common.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cz.mallat.uasparser.CachingOnlineUpdateUASparser;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

/**
 * SSS: The tool is downloaded from http://user-agent-string.info/download/UASparser-for-JAVA
 * @author siyamed
 */
public class RobotChecker {
	private static RobotChecker instance = null;

	private UASparser parser = null;
	private final Logger logger;

	private RobotChecker() {
		try {
			logger = Logger.getLogger(this.getClass().getName());
			parser = new CachingOnlineUpdateUASparser();
		} catch (IOException e) {
			throw new RuntimeException("Cannot init robots checker");
		}
	}

	public static RobotChecker getInstance() {
		if (instance == null) {
			instance = new RobotChecker();
		}
		return instance;
	}

	public boolean isRobot(String name) {
		if (StringUtils.isNotBlank(name)) {
			UserAgentInfo uai;
			try {
				uai = parser.parse(name);
				if (uai.getTyp().equalsIgnoreCase("robot")) {
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				logger.info("Robot_IO_Exception: " + name);
			}
		}
		return false;
	}

	public UserAgentInfo getUserAgentInfo(String name) {
		UserAgentInfo uai = null;
		if (StringUtils.isNotBlank(name)) {
			try {
				uai = parser.parse(name);
			} catch (IOException e) {
				logger.info("Robot_IO_Exception: " + name);
			}
		}
		return uai;
	}
}

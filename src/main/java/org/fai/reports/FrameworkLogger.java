package org.fai.reports;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public final class FrameworkLogger {

		
	private static Logger log = LogManager.getLogger(FrameworkLogger.class);
	
	public static void initiLogger() {
		DOMConfigurator.configure("log4j.xml");
		
	}
	
	public static void logInfo(String message) {
		log.info(message);
	}
	public static void logError(String message) {
		log.error(message);
	}
	public static void logDebug(String message) {
		log.debug(message);
	}
	public static void logFatal(String message) {
		log.fatal(message);
	}
	public static void logWarn(String message) {
		log.warn(message);
	}
	public static void logTrace(String message) {
		log.trace(message);
	}

}

package util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class LogUtil {

	public static void initLogger() throws SecurityException, IOException
	{
		//ConsoleHandler handler = new ConsoleHandler();
		//handler.setLevel(Level.FINEST);
		//LogManager.getLogManager().reset();
		LogManager.getLogManager().readConfiguration(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdk-log.properties"));
//		
//		java.util.logging.Logger l = java.util.logging.Logger.getLogger("");
//		l.addHandler(handler);
//		l.setLevel(Level.FINEST);
	}
}

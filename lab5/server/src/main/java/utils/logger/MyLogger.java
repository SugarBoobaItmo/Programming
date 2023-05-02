package utils.logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 
 * The MyLogger class provides functionality to log messages to a file.
 * 
 */
public class MyLogger {
    // path to the log file
    private static final String LOG_FILE_PATH = "logs.log";
    private Logger logger;

    public MyLogger() {
        logger = Logger.getLogger(MyLogger.class.getName());
        logger.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            removeConsoleHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        info("Logger initialized at time: "+ LocalDateTime.now());
    }

    /**
     * 
     * Logs a message with the INFO level.
     * 
     * @param message the message to log
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * 
     * Logs a message with the WARNING level.
     * 
     * @param message the message to log
     */
    public void warning(String message) {
        logger.warning(message);
    }

    /**
     * 
     * Removes the console handler from the logger.
     * 
     * @param message the message to log
     */
    public void removeConsoleHandler() {
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                rootLogger.removeHandler(handler);
            }
        }
    }
}


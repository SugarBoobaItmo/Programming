package utils.logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    
    private static final String LOG_FILE_PATH = "logs.log";
    private Logger logger;

    public MyLogger() {
        logger = Logger.getLogger(MyLogger.class.getName());
        logger.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        info("Logger initialized at time: "+ LocalDateTime.now());
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warning(String message) {
        logger.warning(message);
    }
}


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger{
    public static void main(String... args){
        Logger LOGGER = LogManager.getRootLogger();

        LOGGER.info("Testing");
    }
}
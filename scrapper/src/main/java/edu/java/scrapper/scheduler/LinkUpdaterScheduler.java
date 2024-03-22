package edu.java.scrapper.scheduler;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Setter
public class LinkUpdaterScheduler {
    private final static Logger LOGGER = LogManager.getLogger();
    private int counter;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        setCounter(1 + counter);
        LOGGER.info("update method called" + counter);
    }
}

package edu.java.bot;

import edu.java.bot.bot.Bot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private final Bot bot;

    public Runner(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run(String... args) {
        bot.start();
    }
}

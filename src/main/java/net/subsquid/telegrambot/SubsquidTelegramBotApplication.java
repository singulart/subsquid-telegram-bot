package net.subsquid.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubsquidTelegramBotApplication {

	public static void main(String[] args) {
		if (System.getProperty("BOT_TOKEN") == null) {
			System.err.println("Please set environment variables correctly: BOT_TOKEN");
			System.exit(-1);
		}
		SpringApplication.run(SubsquidTelegramBotApplication.class, args);
	}

}

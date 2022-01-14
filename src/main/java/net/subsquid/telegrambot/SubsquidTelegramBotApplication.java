package net.subsquid.telegrambot;

import static net.subsquid.telegrambot.Constants.CHANNEL;
import static net.subsquid.telegrambot.Constants.TOKEN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubsquidTelegramBotApplication {

	public static void main(String[] args) {
		if (System.getProperty(TOKEN) == null || System.getProperty(CHANNEL) == null) {
			System.err.println("Please set environment variables correctly: BOT_TOKEN, CHAT_ID");
			System.exit(-1);
		}
		SpringApplication.run(SubsquidTelegramBotApplication.class, args);
	}

}

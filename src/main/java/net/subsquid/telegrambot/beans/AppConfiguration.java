package net.subsquid.telegrambot.beans;

import static net.subsquid.telegrambot.Constants.TOKEN;

import com.pengrad.telegrambot.TelegramBot;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    @Bean
    public TelegramBot bot() {
        return new TelegramBot.Builder(System.getProperty(TOKEN))
            .okHttpClient(
                new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY))
                    .build()
            ).build();
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(TelegramNotifierJob.class);
        jobDetailFactory.setDescription("Gets the data about deployed indexers and posts their statuses to Telegram");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job, @Value("${repeat.interval.secs:3600}") long repeatInterval) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setRepeatInterval(repeatInterval * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
        @Value("${connect.timeout.millis:200}") long connectTimeout,
        @Value("${read.timeout.millis:400}") long readTimeout
    ) {

        return builder
            .setConnectTimeout(Duration.of(connectTimeout, ChronoUnit.MILLIS))
            .setReadTimeout(Duration.of(readTimeout, ChronoUnit.MILLIS))
            .build();
    }
}

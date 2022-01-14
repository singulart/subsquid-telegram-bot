package net.subsquid.telegrambot.beans;

import java.util.List;
import java.util.logging.Logger;
import net.subsquid.telegrambot.SubsquidIndexerStatus;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramNotifierJob implements Job {
    private final Logger log = Logger.getLogger(TelegramNotifierJob.class.getName());

    @Value("${data.feed.url}")
    private String dataFeedURL;

    private final RestTemplate restTemplate;


    @Autowired
    public TelegramNotifierJob(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing the job");
        var responseObject = restTemplate.getForObject(dataFeedURL, SubsquidIndexerStatus[].class);
        if(responseObject != null) {
            log.info("TODO Implement me");
        } else {
            log.warning("Did not receive indexer list");
        }
    }
}
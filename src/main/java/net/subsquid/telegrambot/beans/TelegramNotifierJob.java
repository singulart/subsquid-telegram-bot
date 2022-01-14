package net.subsquid.telegrambot.beans;

import static java.lang.String.format;
import static net.subsquid.telegrambot.Constants.CHANNEL;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    private final TelegramBot bot;
    private final String telegramChannelId = System.getProperty(CHANNEL);

    @Autowired
    public TelegramNotifierJob(RestTemplate restTemplate, TelegramBot bot) {
        this.restTemplate = restTemplate;
        this.bot = bot;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing the job");
        var responseObject = restTemplate.getForObject(dataFeedURL, SubsquidIndexerStatus[].class);
        if(responseObject != null) {
            String chatNotificationText = responseToChatMessage(responseObject);
            SendResponse sendResponse = bot.execute(
                new SendMessage(telegramChannelId, chatNotificationText).parseMode(ParseMode.MarkdownV2)
            );
            log.info(sendResponse.toString());
        } else {
            log.warning("Did not receive indexer list");
        }
    }

    private String responseToChatMessage(SubsquidIndexerStatus[] responseObject) {
        String header = "Subsquid Indexers: \n";
        String body = Arrays.stream(responseObject).map((status) -> format("*Indexer URL:* %s\n"
            + "```\nHydra Version: %s\n"
            + "Synced: %s\n"
            + "Last Imported Block: %s\n"
            + "Target Block: %s\n"
            + "Sync progress: %.2f%%\n"
            + "```", status.getUrl(), status.getHydraVersion(), status.isInSync(),
            status.getLastComplete(),
            status.getChainHeight(),
            100.0 * status.getLastComplete() / status.getChainHeight()))
            .collect(Collectors.joining("\n\n"));
        return header + body;
    }
}
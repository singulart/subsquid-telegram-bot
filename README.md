# Subsquid indexers Telegram bot

A Telegram bot written in Spring Boot which uses Quartz scheduler to periodically fetch the statuses of the running Subsquid indexers from  HTTP [data feed](https://github.com/singulart/indexer-api).

# Configuration

The following environment variables must be set when running the bot:
1. `BOT_TOKEN` - Telegram Bot Token
2. `CHAT_ID` - ID of the channel where to post notifications using the bot. The Bot must have permissions to send messages in that channel or group
3. `DATA_FEED_URL` - URL of the data feed to fetch statuses from
4. `REPEAT_INTERVAL_SECS` - time interval (in seconds) for Quartz to re-query the statuses

# Building / Running Locally

`mvn clean package`

`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DBOT_TOKEN=_________:______________________ -DCHAT_ID=-00000000000 -Dserver.port=8081"`

The `server.port` property is only required if you run both Telegram bot and its [data feed](https://github.com/singulart/indexer-api) on the same box.

# Running in Prod
`java -jar target/telegram-bot-0.0.1-SNAPSHOT.jar`


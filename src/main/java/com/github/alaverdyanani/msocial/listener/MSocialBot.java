package com.github.alaverdyanani.msocial.listener;

import com.github.alaverdyanani.msocial.entity.User;
import com.github.alaverdyanani.msocial.scheduler.DailyDomainScheduler;
import com.github.alaverdyanani.msocial.service.MessageService;
import com.github.alaverdyanani.msocial.service.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MSocialBot extends DefaultAbsSender implements LongPollingBot {
    private final Logger logger = LoggerFactory.getLogger(MSocialBot.class);

    private final UserService userService;
    private final MessageService messageService;
    private final String botName;
    public MSocialBot(@Value("${bot.name}")String botName,
                      @Value("${bot.token}")String botToken,
                      UserService userService,
                      MessageService messageService) {

        super(new DefaultBotOptions(), botToken);
        this.botName = botName;
        this.userService = userService;
        this.messageService = messageService;
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Update received " + update);
        if (update.hasMessage()) {
            long userChatId = update.getMessage().getFrom().getId();
            Optional<User> userOptional = userService.findByChatId(userChatId);
            if (userOptional.isEmpty()) {
                userService.createUser(userChatId);
            } else {
                userService.updateMessageTime(userOptional.get());
            }
            String receivedMessage = update.getMessage().getText();
            messageService.createReceivedMessage(receivedMessage, userChatId);
            String sendMessage;
            if ("/start".equals(receivedMessage)) {
                sendMessage = "Hello " + update.getMessage().getFrom().getFirstName();
            } else {
                sendMessage = "Echo: " + update.getMessage().getText();
            }
            createAndSendMessage(userChatId, sendMessage);
        }
    }
    private  void createAndSendMessage(long userChatId, String sendMessage){
        try {
            execute(SendMessage.builder()
                .chatId(userChatId)
                .text(sendMessage)
                .build());
        } catch (TelegramApiException e){
            logger.error(e.getMessage(),e);
        }
        messageService.createSendMessage(sendMessage, userChatId);
        logger.info("Message:\"" + sendMessage + "\" sent and saved!");
    }

    @Scheduled(cron = "${cron.value}")
    @Async
    @Transactional
    public void  scheduled() {
        int count = 0;
        try {
            count = DailyDomainScheduler.fetchAndUpdateData();
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String message = dateTimeFormatter.format(now) + ". Collected " + count + " domens.";
        List<Long> userChatIds = userService.getAllUsersChatIds();
        for (Long userChatId : userChatIds) {
            createAndSendMessage(userChatId, message);
        }
    }
    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }
}

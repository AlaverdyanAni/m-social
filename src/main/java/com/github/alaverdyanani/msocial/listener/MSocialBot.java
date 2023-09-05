package com.github.alaverdyanani.msocial.listener;

import com.github.alaverdyanani.msocial.client.TelegramBotClient;
import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import com.github.alaverdyanani.msocial.entity.User;
import com.github.alaverdyanani.msocial.repository.UserRepository;
import com.github.alaverdyanani.msocial.service.DailyDomainService;
import com.github.alaverdyanani.msocial.service.MessageService;
import com.github.alaverdyanani.msocial.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;
import java.io.IOException;
import java.util.List;

@Component
public class MSocialBot implements LongPollingBot {
    private final TelegramBotClient telegramBotClient;
    private final String botToken;
    private final String botUserName;
    private Update update;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MessageService messageService;
    private  final DailyDomainService dailyDomainService;
    public MSocialBot(TelegramBotClient telegramBotClient,
                      @Value("${bot.token}")String botToken,
                      @Value("${bot.username}")String botUserName,
                      UserRepository userRepository, UserService userService,
                      MessageService messageService, DailyDomainService dailyDomainService) {
        this.telegramBotClient = telegramBotClient;
        this.botToken = botToken;
        this.botUserName = botUserName;
        this.userRepository = userRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.dailyDomainService = dailyDomainService;
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().startsWith("/start")) {
                userService.saveUser(update.getMessage().getChatId());
            SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getChatId()),"You are successfully registered!");
        telegramBotClient.execute(sendMessage);
        }else if (update.getMessage().hasText()){
            messageService.saveMessage(update.getMessage().getChatId(),update.getMessage().getText());
            SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getChatId()),"Your message has been successfully saved!");
       telegramBotClient.execute(sendMessage);
        }
    }

    @Override
    public BotOptions getOptions() {
        return telegramBotClient.getOptions();
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(telegramBotClient);

    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    @Scheduled(cron = "@daily")
    public void saveDailyDomains() throws IOException {
        dailyDomainService.saveDailyDomains();
    }
    @Scheduled(cron = "@daily")
    public void sendDailyDomains() throws TelegramApiException {
        List<DailyDomainDto> dailyDomainDtos = dailyDomainService.sendDailyDomains();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            SendMessage dailyDomains = new SendMessage();
            dailyDomains.setChatId(user.getChatId());
            dailyDomains.setText(dailyDomainDtos.toString());
            telegramBotClient.execute(dailyDomains);

        }
    }

}

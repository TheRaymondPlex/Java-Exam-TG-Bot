package com.tgexambot.ExamTGBot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    @SneakyThrows
    private void handleMessage(Message message) {

        if (message.hasText()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDateTime date = LocalDateTime.now();

                switch (command) {
                    case "/start":
                        execute(SendMessage.builder()
                                .text("Привет! Я экзаменационный бот для проверки изменения курсов валют за 10 дней." +  "\n\n"
                                        + "Введи \"/uah\", чтобы получить информацию по курсу Украинской Гривны." + "\n"
                                        + "Введи \"/usd\", чтобы получить информацию по курсу Доллара США." + "\n"
                                        + "Введи \"/eur\", чтобы получить информацию по курсу ЕВРО.")
                                .chatId(message.getChatId().toString())
                                .build());
                        return;

                    case "/uah":
                        String uahUrl = "https://bhom.ru/currencies/uah/?sb=yes&startdate=" + dtf.format(date.plusDays(-10)) + "&enddate=" + dtf.format(date);
                        String text = "Получить ссылку";

                        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
                        rowInline.add(InlineKeyboardButton.builder().text(text).url(uahUrl).build());
                        rowsInline.add(rowInline);
                        markupInline.setKeyboard(rowsInline);
                        message.setReplyMarkup(markupInline);

                        execute(SendMessage.builder()
                                .text("Нажми на кнопку, чтобы просмотреть информацию о курсе Гривны.")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(rowsInline).build())
                                .build());
                        return;

                    case "/usd":
                        String usdUrl = "https://bhom.ru/currencies/usd/?sb=yes&startdate=" + dtf.format(date.plusDays(-10)) + "&enddate=" + dtf.format(date);
                        String text1 = "Получить ссылку";

                        InlineKeyboardMarkup markupInlineUsd = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> rowsInlineUsd = new ArrayList<>();
                        List < InlineKeyboardButton > rowInlineUsd = new ArrayList < > ();
                        rowInlineUsd.add(InlineKeyboardButton.builder().text(text1).url(usdUrl).build());
                        rowsInlineUsd.add(rowInlineUsd);
                        markupInlineUsd.setKeyboard(rowsInlineUsd);
                        message.setReplyMarkup(markupInlineUsd);

                        execute(SendMessage.builder()
                                .text("Нажми на кнопку, чтобы просмотреть информацию о курсе Доллара США.")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(rowsInlineUsd).build())
                                .build());
                        return;

                    case "/eur":
                        String url = "https://bhom.ru/currencies/eur/?sb=yes&startdate=" + dtf.format(date.plusDays(-10)) + "&enddate=" + dtf.format(date);
                        String text2 = "Получить ссылку";

                        InlineKeyboardMarkup markupInlineEur = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> rowsInlineEur = new ArrayList<>();
                        List < InlineKeyboardButton > rowInlineEur = new ArrayList < > ();
                        rowInlineEur.add(InlineKeyboardButton.builder().text(text2).url(url).build());
                        rowsInlineEur.add(rowInlineEur);
                        markupInlineEur.setKeyboard(rowsInlineEur);
                        message.setReplyMarkup(markupInlineEur);

                        execute(SendMessage.builder()
                                .text("Нажми на кнопку, чтобы просмотреть информацию о курсе ЕВРО.")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(rowsInlineEur).build())
                                .build());

                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "it_overone_java_exam_bot";
    }

    @Override
    public String getBotToken() {
        return "5412047514:AAG9sFepwNV2c64ztAzHTvtsrGg5_ymNJ0U";
    }

    @SneakyThrows
    public static void main(String[] args) {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}

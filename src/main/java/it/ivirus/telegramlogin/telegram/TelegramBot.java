package it.ivirus.telegramlogin.telegram;

import it.ivirus.telegramlogin.TelegramLogin;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    private TelegramLogin plugin;

    public TelegramBot(TelegramLogin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return plugin.getConfig().getString("bot.name");
    }

    @Override
    public String getBotToken() {
        return plugin.getConfig().getString("bot.token");
    }

}

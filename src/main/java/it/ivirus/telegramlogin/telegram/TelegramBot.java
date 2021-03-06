package it.ivirus.telegramlogin.telegram;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.spigot.listeners.LoginListener;
import it.ivirus.telegramlogin.spigot.listeners.PlayerListener;
import it.ivirus.telegramlogin.telegram.callbackmanager.CallbackHandler;
import it.ivirus.telegramlogin.telegram.callbackmanager.TextCommandHandler;
import org.bukkit.Bukkit;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramLogin plugin;
    private final CallbackHandler callbackHandler = CallbackHandler.getInstance();
    private final TextCommandHandler textCommandHandler = TextCommandHandler.getInstance();

    public TelegramBot(TelegramLogin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new LoginListener(plugin, this), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(plugin, this), plugin);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            callbackHandler.run(update);
        } else textCommandHandler.run(update);
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

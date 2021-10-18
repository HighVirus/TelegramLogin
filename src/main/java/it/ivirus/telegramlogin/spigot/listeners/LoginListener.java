package it.ivirus.telegramlogin.spigot.listeners;

import it.ivirus.telegramlogin.TelegramLogin;
import it.ivirus.telegramlogin.data.PlayerData;
import it.ivirus.telegramlogin.telegram.TelegramBot;
import it.ivirus.telegramlogin.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LoginListener implements Listener {
    private final TelegramLogin plugin;
    private final TelegramBot bot;
    private final PlayerData playerData = PlayerData.getInstance();

    public LoginListener(TelegramLogin plugin, TelegramBot bot) {
        this.plugin = plugin;
        this.bot = bot;
    }

    /*@EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (playerData.getPlayerCache().containsKey(player.getUniqueId())) {
            TelegramPlayer telegramPlayer = playerData.getPlayerCache().get(player.getUniqueId());
            if (telegramPlayer.isLocked()) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString());
                return;
            }
            playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
            //Util.sendPluginMessage(player, PluginMessageAction.ADD);
            try {
                bot.execute(MessageFactory.loginRequest(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID(), event.getRealAddress().toString()));
                player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        plugin.getSql().getTelegramPlayer(player.getUniqueId().toString()).whenComplete((telegramPlayer, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }).thenAccept(telegramPlayer -> {
            if (telegramPlayer == null) {
                if (plugin.getConfig().getBoolean("2FA.enabled")) return;
                playerData.getPlayerWaitingForChatid().add(player.getUniqueId());
                //Util.sendPluginMessage(player, PluginMessageAction.ADD);
                player.sendMessage(LangConstants.ADD_CHATID.getFormattedString().replaceAll("%bot_tag%", bot.getBotUsername()));
            } else {
                if (telegramPlayer.isLocked()) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.kickPlayer(LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString()), 1);
                    return;
                }
                playerData.getPlayerCache().put(player.getUniqueId(), telegramPlayer);
                //Util.sendPluginMessage(player, PluginMessageAction.ADD);
                playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
                try {
                    bot.execute(MessageFactory.loginRequest(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID(), event.getRealAddress().toString()));
                    player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    @EventHandler
    public void onPlayerPostLogin(PlayerJoinEvent event){
        /*if (playerData.getPlayerInLogin().containsKey(event.getPlayer().getUniqueId())
                || playerData.getPlayerWaitingForChatid().contains(event.getPlayer().getUniqueId())){
            Util.sendPluginMessage(event.getPlayer(), PluginMessageAction.ADD);
        }*/


        Player player = event.getPlayer();
        if (playerData.getPlayerCache().containsKey(player.getUniqueId())) {
            TelegramPlayer telegramPlayer = playerData.getPlayerCache().get(player.getUniqueId());
            if (telegramPlayer.isLocked()) {
                //event.disallow(PlayerLoginEvent.Result.KICK_OTHER, LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString());
                player.kickPlayer(LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString());
                return;
            }
            playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
            Util.sendPluginMessage(player, PluginMessageAction.ADD);
            try {
                bot.execute(MessageFactory.loginRequest(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID(), player.getAddress().getHostString()));
                player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        plugin.getSql().getTelegramPlayer(player.getUniqueId().toString()).whenComplete((telegramPlayer, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }).thenAccept(telegramPlayer -> {
            if (telegramPlayer == null) {
                if (plugin.getConfig().getBoolean("2FA.enabled")) return;
                playerData.getPlayerWaitingForChatid().add(player.getUniqueId());
                Util.sendPluginMessage(player, PluginMessageAction.ADD);
                player.sendMessage(LangConstants.ADD_CHATID.getFormattedString().replaceAll("%bot_tag%", bot.getBotUsername()));
            } else {
                if (telegramPlayer.isLocked()) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.kickPlayer(LangConstants.KICK_ACCOUNT_LOCKED.getFormattedString()), 1);
                    return;
                }
                playerData.getPlayerCache().put(player.getUniqueId(), telegramPlayer);
                Util.sendPluginMessage(player, PluginMessageAction.ADD);
                playerData.getPlayerInLogin().put(player.getUniqueId(), telegramPlayer);
                try {
                    bot.execute(MessageFactory.loginRequest(telegramPlayer.getPlayerUUID(), telegramPlayer.getChatID(), player.getAddress().getHostString()));
                    player.sendMessage(LangConstants.WAIT_FOR_LOGIN_CONFIRM.getFormattedString());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

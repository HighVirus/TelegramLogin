package it.ivirus.telegramlogin.util;

import it.ivirus.telegramlogin.TelegramLogin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Util {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendPluginMessage(Player player, PluginMessageAction action) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("cache");
            out.writeUTF(action.getType());
            out.writeUTF(player.getUniqueId().toString());

            player.sendPluginMessage(TelegramLogin.getInstance(), "hxj:telegramlogin", b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

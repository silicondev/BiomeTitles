package uk.phyre.biomeTitles.Helpers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;

public final class MessageHelper {
    public static void SendPlayerAndConsole(JavaPlugin plugin, CommandSender sender, String message) {
        if (sender instanceof Player) {
            sender.sendMessage("[BiomeTitles] " + message);
        }
        plugin.getLogger().info(message);
    }

    public static String GetColorCodedJson(String colorCoded) {
        String[] parts = colorCoded.split("&");
        boolean bold = false;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        boolean italic = false;
        String colour = "white";

        var list = new LinkedList<String>();

        for (String part : parts) {
            var code = part.substring(0, 1);
            var text = part.substring(1);

            switch (code.toLowerCase()) {
                // FORMATTING
                case "k":
                    obfuscated = true;
                    break;
                case "l":
                    bold = true;
                    break;
                case "m":
                    strikethrough = true;
                    break;
                case "n":
                    underlined = true;
                    break;
                case "o":
                    italic = true;
                    break;
                case "r":
                    bold = false;
                    underlined = false;
                    strikethrough = false;
                    obfuscated = false;
                    italic = false;
                    colour = "white";

                // COLOURING
                case "0":
                    colour = "black";
                    break;
                case "1":
                    colour = "dark_blue";
                    break;
                case "2":
                    colour = "dark_green";
                    break;
                case "3":
                    colour = "dark_aqua";
                    break;
                case "4":
                    colour = "dark_red";
                    break;
                case "5":
                    colour = "dark_purple";
                    break;
                case "6":
                    colour = "gold";
                    break;
                case "7":
                    colour = "gray";
                    break;
                case "8":
                    colour = "dark_gray";
                    break;
                case "9":
                    colour = "blue";
                    break;
                case "a":
                    colour = "green";
                    break;
                case "b":
                    colour = "aqua";
                    break;
                case "c":
                    colour = "red";
                    break;
                case "d":
                    colour = "light_purple";
                    break;
                case "e":
                    colour = "yellow";
                    break;
                case "f":
                    colour = "white";
                    break;
            }

            if (text.length() == 0)
                continue;

            list.add(String.format("{\"text\":\"%s\",\"color\":\"%s\",\"bold\":%s,\"underline\":%s,\"strikethrough\":%s,\"obfuscated\":%s,\"italic\":%s}", text, colour, bold, underlined, strikethrough, obfuscated, italic));
        }

        return String.join(",", list);
    }
}

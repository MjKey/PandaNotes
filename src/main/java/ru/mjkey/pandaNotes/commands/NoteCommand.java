package ru.mjkey.pandaNotes.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.mjkey.pandaNotes.PandaNotes;
import ru.mjkey.pandaNotes.utils.NoteUtils;

public class NoteCommand implements CommandExecutor {
    private final PandaNotes plugin;
    private final MiniMessage miniMessage;

    public NoteCommand(PandaNotes plugin) {
        this.plugin = plugin;
        this.miniMessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cИспользование: /note <текст>");
            return true;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (!NoteUtils.isEmptyNote(handItem)) {
            player.sendMessage("§cВы должны держать Пустую записку в руке!");
            return true;
        }

        // Собираем текст из аргументов
        String text = String.join(" ", args);
        
        try {
            // Парсим MiniMessage и проверяем его валидность
            miniMessage.deserialize(text);
            
            // Сохраняем оригинальный текст в записке (с тегами MiniMessage)
            NoteUtils.saveNoteContent(plugin, handItem, text);
            player.sendMessage("§aЗаписка успешно создана!");
            
        } catch (Exception e) {
            player.sendMessage("§cОшибка в формате текста MiniMessage!");
            return true;
        }

        return true;
    }
} 
package ru.mjkey.pandaNotes.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import ru.mjkey.pandaNotes.PandaNotes;
import ru.mjkey.pandaNotes.utils.NoteUtils;

public class NoteListener implements Listener {
    private final PandaNotes plugin;

    public NoteListener(PandaNotes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNoteUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.getType().equals(Material.PAPER)) {
            return;
        }

        if (!event.getAction().name().contains("RIGHT_CLICK")) {
            return;
        }

        if (!NoteUtils.isNote(item)) {
            return;
        }

        event.setCancelled(true);

        if (NoteUtils.isEmptyNote(item)) {
            player.sendMessage("§cИспользуйте команду /note <текст> чтобы написать записку");
            return;
        }

        // Читаем записку и конвертируем в legacy формат
        String miniMessageContent = NoteUtils.getNoteContent(plugin, item);
        String legacyContent = NoteUtils.convertMiniMessageToLegacy(miniMessageContent);
        
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        if (bookMeta != null) {
            bookMeta.title(Component.text("Записка"));
            bookMeta.author(Component.text(player.getName()));
            bookMeta.pages(Component.text(legacyContent)); // Используем конвертированный текст
            book.setItemMeta(bookMeta);
            
            player.openBook(book);
        }
    }
}
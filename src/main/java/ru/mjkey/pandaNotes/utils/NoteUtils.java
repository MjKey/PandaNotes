package ru.mjkey.pandaNotes.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.mjkey.pandaNotes.PandaNotes;

public class NoteUtils {
    private static final String NOTE_CONTENT_KEY = "note_content";
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
            .character('§')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();
    
    public static String convertMiniMessageToLegacy(String miniMessageText) {
        Component component = miniMessage.deserialize(miniMessageText);
        return legacySerializer.serialize(component);
    }
    
    public static void saveNoteContent(PandaNotes plugin, ItemStack note, String content) {
        ItemMeta meta = note.getItemMeta();
        if (meta == null) return;
        
        NamespacedKey key = new NamespacedKey(plugin, NOTE_CONTENT_KEY);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, content);
        meta.displayName(Component.text("§fЗаписка"));
        note.setItemMeta(meta);
    }
    
    public static String getNoteContent(PandaNotes plugin, ItemStack note) {
        ItemMeta meta = note.getItemMeta();
        if (meta == null) return "";
        
        NamespacedKey key = new NamespacedKey(plugin, NOTE_CONTENT_KEY);
        return meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "");
    }
    
    public static boolean isNote(ItemStack item) {
        if (item == null || item.getType() != Material.PAPER) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        
        Component name = meta.displayName();
        return name != null && (name.toString().contains("§fПустая записка") || name.toString().contains("§fЗаписка"));
    }
    
    public static boolean isEmptyNote(ItemStack item) {
        if (!isNote(item)) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        
        Component name = meta.displayName();
        return name != null && name.toString().contains("§fПустая записка");
    }
    
    public static boolean isSameNote(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) return false;
        if (item1.getType() != item2.getType()) return false;
        
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 == null || meta2 == null) return false;
        
        Component name1 = meta1.displayName();
        Component name2 = meta2.displayName();
        if (name1 == null || name2 == null) return false;
        
        return name1.toString().equals(name2.toString());
    }
}
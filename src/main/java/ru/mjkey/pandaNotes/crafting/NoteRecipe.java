package ru.mjkey.pandaNotes.crafting;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import ru.mjkey.pandaNotes.PandaNotes;

public class NoteRecipe {
    private static final String RECIPE_KEY = "empty_note";
    private final PandaNotes plugin;

    public NoteRecipe(PandaNotes plugin) {
        this.plugin = plugin;
    }

    public ShapelessRecipe createRecipe() {
        ItemStack emptyNote = new ItemStack(Material.PAPER);
        ItemMeta meta = emptyNote.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text("§fПустая записка"));
            emptyNote.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(plugin, RECIPE_KEY);
        ShapelessRecipe recipe = new ShapelessRecipe(key, emptyNote);
        recipe.addIngredient(Material.PAPER);
        recipe.addIngredient(Material.INK_SAC);

        return recipe;
    }
}
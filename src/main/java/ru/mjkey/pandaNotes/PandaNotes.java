package ru.mjkey.pandaNotes;

import org.bukkit.plugin.java.JavaPlugin;
import ru.mjkey.pandaNotes.commands.NoteCommand;
import ru.mjkey.pandaNotes.crafting.NoteRecipe;
import ru.mjkey.pandaNotes.listeners.NoteListener;

public final class PandaNotes extends JavaPlugin {

    @Override
    public void onEnable() {
        // Регистрируем рецепт
        NoteRecipe noteRecipe = new NoteRecipe(this);
        getServer().addRecipe(noteRecipe.createRecipe());

        // Регистрируем слушатель событий
        getServer().getPluginManager().registerEvents(new NoteListener(this), this);

        // Регистрируем команду
        getCommand("note").setExecutor(new NoteCommand(this));
    }

    @Override
    public void onDisable() {
        // Очищаем рецепты при выключении
        getServer().clearRecipes();
    }
}

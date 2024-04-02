package com.lith.shapelessportal;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.shapelessportal.config.ConfigManager;
import com.lith.shapelessportal.events.CreateNetherPortal;

public class Plugin extends MainPlugin<ConfigManager> {
    public static Plugin plugin;

    public void onEnable() {
        Plugin.plugin = this;

        registerConfigs();
        registerEvents();

        Static.log.info("Plugin enabled");
    }

    public void onDisable() {
        Static.log.info("Plugin disabled");
    }

    private void registerConfigs() {
        new ConfigManager(this);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new CreateNetherPortal(), this);
    }
}
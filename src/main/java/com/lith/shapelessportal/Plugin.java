package com.lith.shapelessportal;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;

public class Plugin extends MainPlugin<PluginConfigManager> {
    public static Plugin plugin;

    public void onEnable() {
        Plugin.plugin = this;

        Static.log.info("Plugin enabled");
    }

    public void onDisable() {
        Static.log.info("Plugin disabled");
    }
}
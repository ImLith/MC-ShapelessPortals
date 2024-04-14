package com.lith.shapelessportal;

import com.lith.lithcore.abstractClasses.AbstractPlugin;
import com.lith.lithcore.helpers.ReloadConfigCmd;
import com.lith.shapelessportal.Static.Commands;
import com.lith.shapelessportal.config.ConfigManager;
import com.lith.shapelessportal.events.CreateNetherPortal;

public class Plugin extends AbstractPlugin<Plugin, ConfigManager> {
    @Override
    public void onEnable() {
        configs = new ConfigManager(this);
        super.onEnable();
    }

    @Override
    protected void registerEvents() {
        registerEvent(new CreateNetherPortal(this));
    }

    @Override
    protected void registerCommands() {
        new ReloadConfigCmd<Plugin>(this, Commands.Permission.RELOAD, Commands.Name.RELOAD);
    }
}
package com.lith.shapelessportal.config;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import com.lith.shapelessportal.Static.ConfigKey;

public class ConfigManager extends PluginConfigManager {
    public static PortalConfig portalConfig;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        portalConfig = new PortalConfig();
    }

    public final class PortalConfig {
        public final int maxPortalSize = getInt(ConfigKey.MAX_PORTAL_SIZE);
    }
}

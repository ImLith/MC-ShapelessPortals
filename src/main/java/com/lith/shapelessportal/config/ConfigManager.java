package com.lith.shapelessportal.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import com.lith.shapelessportal.Static;
import com.lith.shapelessportal.Static.ConfigKey;

public class ConfigManager extends PluginConfigManager {
    public static PortalConfig portalConfig;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        portalConfig = new PortalConfig();
    }

    public final class PortalConfig {
        public final int maxPortalSize = getInt(ConfigKey.MAX_PORTAL_SIZE);
        public final Set<Material> portalBlocks;

        public PortalConfig() {
            portalBlocks = new HashSet<>();

            validatePortalMaterials();
        }

        private void validatePortalMaterials() {
            if (!config.contains(ConfigKey.ALLOWED_MATERIALS)) {
                setDefaultPortalBlocks();
                return;
            }

            List<String> allowedMaterials = config.getStringList(ConfigKey.ALLOWED_MATERIALS);
            if (allowedMaterials.isEmpty()) {
                setDefaultPortalBlocks();
                return;
            }

            for (String materialName : allowedMaterials) {
                Material material = Material.matchMaterial(materialName);

                if (material == null) {
                    Static.log.warning("Material " + materialName + " is not a valid material! Skip");
                    continue;
                }

                portalBlocks.add(material);
            }

            if (portalBlocks.size() == 0)
                setDefaultPortalBlocks();

        }

        private void setDefaultPortalBlocks() {
            portalBlocks.add(Material.OBSIDIAN);
            portalBlocks.add(Material.CRYING_OBSIDIAN);

            Static.log.warning("No valid material list found in the configs! Using default.");
        }
    }
}

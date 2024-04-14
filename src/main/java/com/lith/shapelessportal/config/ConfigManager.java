package com.lith.shapelessportal.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import com.lith.lithcore.abstractClasses.AbstractConfigManager;
import com.lith.shapelessportal.Plugin;
import com.lith.shapelessportal.Static.ConfigKey;

public class ConfigManager extends AbstractConfigManager<Plugin, ConfigManager> {
    public static PortalConfig portalConfig;

    public ConfigManager(final Plugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();
        portalConfig = new PortalConfig();
    }

    public final class PortalConfig {
        public final int maxPortalSize = config.getInt(ConfigKey.MAX_PORTAL_SIZE);
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
                    plugin.log.warning("Material " + materialName + " is not a valid material! Skip");
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

            plugin.log.warning("No valid material list found in the configs! Using default.");
        }
    }
}

package com.lith.shapelessportal;

import java.util.logging.Logger;

public class Static {
    public static final String pluginName = "ShapelessPortal";
    public static final Logger log = Logger.getLogger(Static.pluginName);

    final public static class ConfigKey {
        public static final String MAX_PORTAL_SIZE = "max_portal_size";
    }
}

package com.lith.shapelessportal;

public class Static {
    final public static class ConfigKey {
        public static final String MAX_PORTAL_SIZE = "max_portal_size";
        public static final String ALLOWED_MATERIALS = "allowed_materials";
    }

    public final static class Commands {
        public final static class Name {
            public final static String RELOAD = "spReload";
        }

        public final static class Permission {
            private final static String PREFIX = "shaplessportals.";
            public final static String RELOAD = PREFIX + "reload";
        }
    }
}

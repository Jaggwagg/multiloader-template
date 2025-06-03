package jaggwagg.template.config;

import jaggwagg.template.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC = BUILDER.build();

    public enum Config {
        TEST_CONFIG_VALUE("testConfigValue",
                true,
                "Whether to exist or not"),

        ITEM_STRINGS("items",
                List.of("minecraft:iron_ingot"),
                "A list of items to log on common setup.",
                ModConfig::validateItemName),

        EXAMPLE_INT("exampleInt",
                42,
                "An example integer config"),

        EXAMPLE_DOUBLE("exampleDouble",
                3.14,
                "An example double config"),

        EXAMPLE_STRING("exampleString",
                "default_value",
                "An example string config");

        private final String key;
        private final ModConfigSpec.ConfigValue<?> configValue;
        private Object cachedValue;

        Config(String key, boolean defaultValue, String comment) {
            this.key = key;
            this.configValue = BUILDER.comment(comment).define(key, defaultValue);
        }

        Config(String key, int defaultValue, String comment) {
            this.key = key;
            this.configValue = BUILDER.comment(comment).define(key, defaultValue);
        }

        Config(String key, double defaultValue, String comment) {
            this.key = key;
            this.configValue = BUILDER.comment(comment).define(key, defaultValue);
        }

        Config(String key, String defaultValue, String comment) {
            this.key = key;
            this.configValue = BUILDER.comment(comment).define(key, defaultValue);
        }

        @SuppressWarnings("deprecation")
        <T> Config(String key, List<T> defaultValue, String comment, Predicate<Object> validator) {
            this.key = key;
            this.configValue = BUILDER.comment(comment).defineListAllowEmpty(key, defaultValue, validator);
        }

        @SuppressWarnings("deprecation")
        <T> Config(String key, List<T> defaultValue, String comment) {
            this.key = key;
            this.configValue = BUILDER.comment(comment).defineListAllowEmpty(key, defaultValue, obj -> true);
        }

        public String getKey() {
            return this.key;
        }

        @SuppressWarnings("unchecked")
        public <T> T get() {
            return (T) this.cachedValue;
        }

        @SuppressWarnings("unchecked")
        public <T> T getRaw() {
            return (T) this.configValue.get();
        }

        void updateCache() {
            this.cachedValue = this.configValue.get();
        }

        @SuppressWarnings("unchecked")
        void updateCacheWithProcessing() {
            if (this == Config.ITEM_STRINGS) {
                this.cachedValue = ((List<String>) this.configValue.get()).stream()
                        .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                        .collect(Collectors.toSet());
            } else {
                updateCache();
            }
        }
    }

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        for (Config config : Config.values()) {
            config.updateCacheWithProcessing();
        }
    }
}

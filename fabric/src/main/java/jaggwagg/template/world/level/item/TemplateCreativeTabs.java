package jaggwagg.template.world.level.item;

import jaggwagg.template.Constants;
import jaggwagg.template.world.level.block.TemplateBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;
import java.util.function.Supplier;

public class TemplateCreativeTabs {
    // Force loads enum
    @SuppressWarnings("unused")
    private static final CreativeTabs[] TABS = CreativeTabs.values();

    public enum CreativeTabs {
        TEMPLATE_TAB(() -> FabricItemGroup.builder()
                .title(Component.translatable("creativetab.template.template_tab"))
                .icon(() -> new ItemStack(TemplateBlocks.Blocks.TEMPLATE_BLOCK.getBlock()))
                .build());

        private final String id;
        private final CreativeModeTab tab;
        private final ResourceKey<CreativeModeTab> key;

        CreativeTabs(Supplier<CreativeModeTab> tabSupplier) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, this.id);
            this.tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, location, tabSupplier.get());
            this.key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, location);
        }

        public String getId() {
            return this.id;
        }

        public CreativeModeTab getTab() {
            return this.tab;
        }

        public ResourceKey<CreativeModeTab> getKey() {
            return this.key;
        }
    }

    // Force loads class
    public static void init() {}
}

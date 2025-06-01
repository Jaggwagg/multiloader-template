package jaggwagg.template.world.level.item;

import jaggwagg.template.Constants;
import jaggwagg.template.world.level.block.TemplateBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

public class TemplateCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    // Force loads enum
    @SuppressWarnings("unused")
    private static final CreativeTabs[] TABS = CreativeTabs.values();

    public enum CreativeTabs {
        TEMPLATE_TAB(() -> CreativeModeTab.builder()
                .title(Component.translatable("creativetab.template.template_tab"))
                .icon(() -> new ItemStack(TemplateBlocks.Blocks.TEMPLATE_BLOCK.getBlock()))
                .build());

        private final String id;
        private final DeferredHolder<CreativeModeTab, CreativeModeTab> tab;

        CreativeTabs(Supplier<CreativeModeTab> tabSupplier) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            this.tab = CREATIVE_TABS.register(this.id, tabSupplier);
        }

        public String getId() {
            return this.id;
        }

        public DeferredHolder<CreativeModeTab, CreativeModeTab> getTab() {
            return this.tab;
        }
    }
}

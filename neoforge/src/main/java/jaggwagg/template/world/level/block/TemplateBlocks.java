package jaggwagg.template.world.level.block;

import jaggwagg.template.Constants;
import jaggwagg.template.world.level.item.TemplateCreativeTabs;
import jaggwagg.template.world.level.item.TemplateItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TemplateBlocks {
    public static final DeferredRegister.Blocks MOD_BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);

    // Force loads enum
    @SuppressWarnings("unused")
    private static final Blocks[] BLOCKS = Blocks.values();

    public enum Blocks {
        TEMPLATE_BLOCK(() -> new Block(BlockBehaviour.Properties.of()
                .destroyTime(1.0f)
                .explosionResistance(1.0f)
                .sound(SoundType.GRAVEL)
        ), true);

        private final String id;
        private final DeferredBlock<Block> block;

        <T extends Block> Blocks(Supplier<T> blockSupplier, boolean hasBlockItem) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            this.block = MOD_BLOCKS.register(this.id, blockSupplier);

            if (hasBlockItem) {
                TemplateItems.MOD_ITEMS.registerSimpleBlockItem(this.block, new Item.Properties());
            }
        }

        public String getId() {
            return this.id;
        }

        public DeferredBlock<Block> getBlock() {
            return this.block;
        }
    }

    @SubscribeEvent
    public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == TemplateCreativeTabs.CreativeTabs.TEMPLATE_TAB.getTab().getKey()) {
            for (Blocks block : Blocks.values()) {
                event.accept(block.getBlock());
            }
        }
    }
}

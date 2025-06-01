package jaggwagg.template.world.level.block;

import jaggwagg.template.Constants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Locale;
import java.util.function.Supplier;

public class TemplateBlocks {
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
        private final Block block;

        <T extends Block> Blocks(Supplier<T> blockSupplier, boolean hasBlockItem) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            this.block = Registry.register(
                    BuiltInRegistries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, this.id),
                    blockSupplier.get()
            );

            if (hasBlockItem) {
                Registry.register(
                        BuiltInRegistries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id),
                        new BlockItem(block, new Item.Properties())
                );
            }
        }

        public String getId() {
            return this.id;
        }

        public Block getBlock() {
            return this.block;
        }
    }

    // Force loads enum
    public static void init() {}

    public static void buildCreativeTabContents(FabricItemGroupEntries entries) {
        for (Blocks block : Blocks.values()) {
            entries.accept(block.getBlock());
        }
    }
}

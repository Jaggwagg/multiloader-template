package jaggwagg.template.world.level.item;

import jaggwagg.template.Constants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Locale;
import java.util.function.Supplier;

public class TemplateItems {
    // Force loads enum
    @SuppressWarnings("unused")
    private static final Items[] ITEMS = Items.values();

    public enum Items {
        TEMPLATE_ITEM(() -> new Item(new Item.Properties()));

        private final String id;
        private final Item item;

        <T extends Item> Items(Supplier<T> itemSupplier) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            this.item = Registry.register(
                    BuiltInRegistries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, this.id),
                    itemSupplier.get()
            );
        }

        public String getId() {
            return this.id;
        }

        public Item getItem() {
            return this.item;
        }
    }

    // Force loads class
    public static void init() {}

    public static void buildCreativeTabContents(FabricItemGroupEntries entries) {
        for (Items item : Items.values()) {
            entries.accept(item.getItem());
        }
    }
}

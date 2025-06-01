package jaggwagg.template.server.item;

import jaggwagg.template.Constants;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items MOD_ITEMS = DeferredRegister.createItems(Constants.MOD_ID);

    // Force loads enum
    @SuppressWarnings("unused")
    private static final Items[] ITEMS = Items.values();

    public enum Items {
        TEMPLATE_ITEM(() -> new Item(new Item.Properties()));

        private final String id;
        private final DeferredItem<Item> item;

        <T extends Item> Items(Supplier<T> itemSupplier) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            this.item = MOD_ITEMS.register(this.id, itemSupplier);
        }

        public String getId() {
            return this.id;
        }

        public Item getItem() {
            return this.item.get();
        }
    }
}

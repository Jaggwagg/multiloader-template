package jaggwagg.template.world.level.item;

import jaggwagg.template.Constants;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final DeferredRegister.Items MOD_ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
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

        public DeferredItem<Item> getItem() {
            return this.item;
        }
    }

    @SubscribeEvent
    public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModCreativeTabs.CreativeTabs.TEMPLATE_TAB.getTab().getKey()) {
            for (Items item : Items.values()) {
                event.accept(item.getItem());
            }
        }
    }
}

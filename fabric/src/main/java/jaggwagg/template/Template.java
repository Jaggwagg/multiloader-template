package jaggwagg.template;

import jaggwagg.template.world.entity.ModEntities;
import jaggwagg.template.world.level.block.ModBlocks;
import jaggwagg.template.world.level.item.ModCreativeTabs;
import jaggwagg.template.world.level.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class Template implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        Common.init();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModCreativeTabs.init();

        ItemGroupEvents.modifyEntriesEvent(ModCreativeTabs.CreativeTabs.TEMPLATE_TAB.getKey())
                .register(ModBlocks::buildCreativeTabContents);
        ItemGroupEvents.modifyEntriesEvent(ModCreativeTabs.CreativeTabs.TEMPLATE_TAB.getKey())
                .register(ModItems::buildCreativeTabContents);
        ItemGroupEvents.modifyEntriesEvent(ModCreativeTabs.CreativeTabs.TEMPLATE_TAB.getKey())
                .register(ModEntities::buildCreativeTabContents);
    }
}

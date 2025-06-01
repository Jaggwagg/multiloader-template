package jaggwagg.template;

import jaggwagg.template.world.level.block.TemplateBlocks;
import jaggwagg.template.world.level.entity.TemplateEntities;
import jaggwagg.template.world.level.item.TemplateCreativeTabs;
import jaggwagg.template.world.level.item.TemplateItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class Template implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        TemplateCommon.init();
        TemplateBlocks.init();
        TemplateItems.init();
        TemplateEntities.init();
        TemplateCreativeTabs.init();

        ItemGroupEvents.modifyEntriesEvent(TemplateCreativeTabs.CreativeTabs.TEMPLATE_TAB.getKey())
                .register(TemplateBlocks::buildCreativeTabContents);
        ItemGroupEvents.modifyEntriesEvent(TemplateCreativeTabs.CreativeTabs.TEMPLATE_TAB.getKey())
                .register(TemplateItems::buildCreativeTabContents);
        ItemGroupEvents.modifyEntriesEvent(TemplateCreativeTabs.CreativeTabs.TEMPLATE_TAB.getKey())
                .register(TemplateEntities::buildCreativeTabContents);
    }
}

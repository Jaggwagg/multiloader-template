package jaggwagg.template;

import jaggwagg.template.config.TemplateConfig;
import jaggwagg.template.world.level.block.TemplateBlocks;
import jaggwagg.template.world.level.entity.TemplateEntities;
import jaggwagg.template.world.level.item.TemplateCreativeTabs;
import jaggwagg.template.world.level.item.TemplateItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MOD_ID)
public class Template {
    public Template(IEventBus modEventBus, ModContainer container) {
        Constants.LOG.info("NeoForge mod!");
        TemplateCommon.init();

        TemplateBlocks.MOD_BLOCKS.register(modEventBus);
        TemplateItems.MOD_ITEMS.register(modEventBus);
        TemplateEntities.MOD_ENTITIES.register(modEventBus);
        TemplateCreativeTabs.CREATIVE_TABS.register(modEventBus);

        modEventBus.register(this);
        modEventBus.register(TemplateEntities.class);

        container.registerConfig(ModConfig.Type.COMMON, TemplateConfig.SPEC);
    }

    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Constants.LOG.info("Blocks registered successfully");
            TemplateBlocks.Blocks.TEMPLATE_BLOCK.getBlock();
        });
    }
}

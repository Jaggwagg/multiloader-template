package jaggwagg.template;

import jaggwagg.template.config.ModConfig;
import jaggwagg.template.world.entity.ModEntities;
import jaggwagg.template.world.level.block.ModBlocks;
import jaggwagg.template.world.level.item.ModCreativeTabs;
import jaggwagg.template.world.level.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MOD_ID)
public class Template {
    public Template(IEventBus modEventBus, ModContainer container) {
        Constants.LOG.info("NeoForge mod!");
        Common.init();

        ModBlocks.MOD_BLOCKS.register(modEventBus);
        ModItems.MOD_ITEMS.register(modEventBus);
        ModEntities.MOD_ENTITIES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        modEventBus.register(this);
        modEventBus.register(ModEntities.class);

        container.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }

    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Constants.LOG.info("Blocks registered successfully");
            ModBlocks.Blocks.TEMPLATE_BLOCK.getBlock();
        });
    }
}

package jaggwagg.template;

import jaggwagg.template.server.block.ModBlocks;
import jaggwagg.template.server.entity.ModEntities;
import jaggwagg.template.server.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MOD_ID)
public class Template {
    public Template(IEventBus modEventBus) {
        Constants.LOG.info("NeoForge mod!");
        TemplateCommon.init();

        ModBlocks.MOD_BLOCKS.register(modEventBus);
        ModItems.MOD_ITEMS.register(modEventBus);
        ModEntities.MOD_ENTITIES.register(modEventBus);

        modEventBus.register(this);
        modEventBus.register(ModEntities.class);
    }

    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Constants.LOG.info("Blocks registered successfully");
            ModBlocks.Blocks.TEMPLATE_BLOCK.getBlock();
        });
    }
}

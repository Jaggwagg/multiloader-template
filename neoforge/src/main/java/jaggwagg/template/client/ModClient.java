package jaggwagg.template.client;

import jaggwagg.template.Constants;
import jaggwagg.template.world.entity.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class ModClient {
    public ModClient(IEventBus modEventBus) {
        modEventBus.register(this);
    }

    @SubscribeEvent
    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (ModEntities.Entities entity : ModEntities.Entities.values()) {
            entity.registerRenderer(event);
        }
    }
}

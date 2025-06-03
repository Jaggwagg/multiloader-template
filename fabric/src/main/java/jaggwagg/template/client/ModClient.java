package jaggwagg.template.client;

import jaggwagg.template.world.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntities.initClient();
    }
}

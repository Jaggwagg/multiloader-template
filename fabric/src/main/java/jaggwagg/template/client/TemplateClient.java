package jaggwagg.template.client;

import jaggwagg.template.world.level.entity.TemplateEntities;
import net.fabricmc.api.ClientModInitializer;

public class TemplateClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TemplateEntities.initClient();
    }
}

package jaggwagg.template;

import net.fabricmc.api.ModInitializer;

public class Template implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        TemplateCommon.init();
    }
}

package jaggwagg.template.client.renderer.entity;

import jaggwagg.template.Constants;
import jaggwagg.template.world.entity.TemplateZombie;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TemplateZombieRenderer extends MobRenderer<TemplateZombie, ZombieModel<TemplateZombie>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "textures/entity/template_zombie.png");

    public TemplateZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TemplateZombie entity) {
        return TEXTURE;
    }
}

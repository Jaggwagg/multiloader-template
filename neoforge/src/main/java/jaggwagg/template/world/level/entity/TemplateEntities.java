package jaggwagg.template.world.level.entity;

import jaggwagg.template.Constants;
import jaggwagg.template.client.renderer.entity.TemplateZombieRenderer;
import jaggwagg.template.world.level.item.TemplateCreativeTabs;
import jaggwagg.template.world.level.item.TemplateItems;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TemplateEntities {
    public static final DeferredRegister<EntityType<?>> MOD_ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);

    // Force loads enum
    @SuppressWarnings("unused")
    private static final Entities[] ENTITIES = Entities.values();

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (Entities entity : Entities.values()) {
            entity.registerAttributes(event);
        }
    }

    public enum Entities {
        TEMPLATE_ZOMBIE(() -> EntityType.Builder.of(TemplateZombie::new, MobCategory.MONSTER)
                .build("template_zombie"),
                true,
                0x7E9680, 0xC5D1C5,
                TemplateZombie::createAttributes,
                TemplateZombieRenderer::new);

        private final String id;
        private final DeferredHolder<EntityType<?>, EntityType<?>> entityType;
        private final DeferredItem<Item> spawnEgg;
        private final Supplier<AttributeSupplier.Builder> attributeSupplier;
        private final EntityRendererProvider<?> rendererProvider;

        <T extends Entity> Entities(Supplier<EntityType<T>> entityTypeSupplier, boolean hasSpawnEgg, int primaryColor, int secondaryColor) {
            this(entityTypeSupplier, hasSpawnEgg, primaryColor, secondaryColor, null, null);
        }

        <T extends Entity> Entities(Supplier<EntityType<T>> entityTypeSupplier, boolean hasSpawnEgg, int primaryColor, int secondaryColor, Supplier<AttributeSupplier.Builder> attributeSupplier) {
            this(entityTypeSupplier, hasSpawnEgg, primaryColor, secondaryColor, attributeSupplier, null);
        }

        <T extends Entity> Entities(Supplier<EntityType<T>> entityTypeSupplier, boolean hasSpawnEgg, int primaryColor, int secondaryColor, Supplier<AttributeSupplier.Builder> attributeSupplier, EntityRendererProvider<T> rendererProvider) {
            this.id = this.name().toLowerCase(Locale.ROOT);
            this.entityType = MOD_ENTITIES.register(this.id, entityTypeSupplier);
            this.attributeSupplier = attributeSupplier;
            this.rendererProvider = rendererProvider;

            if (hasSpawnEgg) {
                this.spawnEgg = TemplateItems.MOD_ITEMS.register(this.id + "_spawn_egg",
                        () -> new SpawnEggItem(this.getEntityType(), primaryColor, secondaryColor, new Item.Properties()));
            } else {
                this.spawnEgg = null;
            }
        }

        public void registerAttributes(EntityAttributeCreationEvent event) {
            if (this.attributeSupplier != null) {
                event.put(this.getEntityType(), this.attributeSupplier.get().build());
            }
        }

        public void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            if (this.rendererProvider != null) {
                event.registerEntityRenderer(this.getEntityType(), this.rendererProvider);
            }
        }

        public String getId() {
            return this.id;
        }

        @SuppressWarnings("unchecked")
        public <T extends Entity> EntityType<T> getEntityType() {
            return (EntityType<T>)this.entityType.get();
        }

        public DeferredItem<Item> getSpawnEgg() {
            return this.spawnEgg;
        }
    }

    @SubscribeEvent
    public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == TemplateCreativeTabs.CreativeTabs.TEMPLATE_TAB.getTab().getKey()) {
            for (Entities entity : Entities.values()) {
                if (entity.getSpawnEgg() != null) {
                    event.accept(entity.getSpawnEgg());
                }
            }
        }
    }
}

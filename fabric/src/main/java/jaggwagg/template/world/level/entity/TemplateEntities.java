package jaggwagg.template.world.level.entity;

import jaggwagg.template.Constants;
import jaggwagg.template.client.renderer.entity.TemplateZombieRenderer;
import jaggwagg.template.level.entity.TemplateZombie;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.Locale;
import java.util.function.Supplier;

public class TemplateEntities {
    // Force loads enum
    @SuppressWarnings("unused")
    private static final Entities[] ENTITIES = Entities.values();

    public enum Entities {
        TEMPLATE_ZOMBIE(() -> EntityType.Builder.of(TemplateZombie::new, MobCategory.MONSTER)
                .build("template_zombie"),
                true,
                0x7E9680, 0xC5D1C5,
                TemplateZombie::createAttributes,
                TemplateZombieRenderer::new);

        private final String id;
        private final EntityType<?> entityType;
        private final Item spawnEgg;
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
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, this.id);

            this.entityType = Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceLocation, entityTypeSupplier.get());
            this.attributeSupplier = attributeSupplier;
            this.rendererProvider = rendererProvider;

            if (hasSpawnEgg) {
                ResourceLocation eggLocation = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, this.id + "_spawn_egg");
                this.spawnEgg = Registry.register(BuiltInRegistries.ITEM, eggLocation,
                        new SpawnEggItem(this.getEntityType(), primaryColor, secondaryColor, new Item.Properties()));
            } else {
                this.spawnEgg = null;
            }
        }

        public String getId() {
            return this.id;
        }

        @SuppressWarnings("unchecked")
        public <T extends Entity> EntityType<T> getEntityType() {
            return (EntityType<T>) this.entityType;
        }

        public Item getSpawnEgg() {
            return this.spawnEgg;
        }

        public Supplier<AttributeSupplier.Builder> getAttributeSupplier() {
            return this.attributeSupplier;
        }

        public EntityRendererProvider<?> getRendererProvider() {
            return this.rendererProvider;
        }
    }

    public static void init() {
        for (Entities entity : Entities.values()) {
            if (entity.getAttributeSupplier() != null) {
                FabricDefaultAttributeRegistry.register(entity.getEntityType(), entity.getAttributeSupplier().get());
            }
        }
    }

    public static void initClient() {
        for (Entities entity : Entities.values()) {
            if (entity.getRendererProvider() != null) {
                EntityRendererRegistry.register(entity.getEntityType(), entity.getRendererProvider());
            }
        }
    }

    public static void buildCreativeTabContents(FabricItemGroupEntries entries) {
        for (Entities entity : Entities.values()) {
            if (entity.getSpawnEgg() != null) {
                entries.accept(entity.getSpawnEgg());
            }
        }
    }
}

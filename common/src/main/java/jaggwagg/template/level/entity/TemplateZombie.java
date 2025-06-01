package jaggwagg.template.level.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TemplateZombie extends Zombie {
    public TemplateZombie(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 1000.0d)
                .add(Attributes.MOVEMENT_SPEED, 2.0d)
                .add(Attributes.ATTACK_DAMAGE, 100.0d);
    }
}

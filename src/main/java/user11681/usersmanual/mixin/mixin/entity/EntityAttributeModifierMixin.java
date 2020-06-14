package user11681.usersmanual.mixin.mixin.entity;

import java.util.UUID;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import user11681.usersmanual.item.ItemModifiers;

@Mixin(EntityAttributeModifier.class)
public abstract class EntityAttributeModifierMixin {
    @Shadow
    @Final
    @Mutable
    private UUID uuid;

    @Inject(method = "<init>(Ljava/util/UUID;Ljava/lang/String;DLnet/minecraft/entity/attribute/EntityAttributeModifier$Operation;)V", at = @At("RETURN"))
    public void construct(final UUID uuid, final String name, final double value, final EntityAttributeModifier.Operation operation, final CallbackInfo info) {
        if (ItemModifiers.RESERVED_IDENTIFIERS.contains(uuid)) {
            this.uuid = uuid;
        }
    }
}

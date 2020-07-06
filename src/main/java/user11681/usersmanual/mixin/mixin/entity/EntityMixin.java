package user11681.usersmanual.mixin.mixin.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import user11681.mirror.Fields;
import user11681.usersmanual.mixin.duck.entity.BossEntityDuck;

@Mixin(Entity.class)
public abstract class EntityMixin implements BossEntityDuck {
    private static final Map<Class<? extends Entity>, Boolean> REGISTRY = new HashMap<>();

    private final Entity self = (Entity) (Object) this;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(final EntityType<?> type, final World world, final CallbackInfo info) {
        final Entity thiz = self;
        final Class<? extends Entity> clazz = thiz.getClass();

        if (!REGISTRY.containsKey(clazz)) {
            final List<Field> fields = Fields.getAllFields(thiz.getClass());

            for (int i = 0, size = fields.size(); i < size && !this.isBoss(); i++) {
                final Field field = fields.get(i);

                field.setAccessible(true);

                if (BossBar.class.isAssignableFrom(field.getType())) {
                    REGISTRY.put(thiz.getClass(), true);
                }
            }
        }
    }

    @Override
    public final boolean isBoss() {
        return REGISTRY.getOrDefault(this.getClass(), false);
    }

    @Override
    public final void setBoss(final boolean boss) {
        REGISTRY.put(self.getClass(), boss);
    }
}

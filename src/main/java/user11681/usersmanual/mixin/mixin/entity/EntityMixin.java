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
import user11681.usersmanual.mixin.duck.entity.BossEntityDuck;
import user11681.usersmanual.reflect.FieldUtil;

@Mixin(Entity.class)
public abstract class EntityMixin implements BossEntityDuck {
    private static final Map<Class<? extends Entity>, Boolean> REGISTRY = new HashMap<>();

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void constructor(final EntityType<?> type, final World world, final CallbackInfo info) {
        final Entity thiz = thiz();
        final Class<? extends Entity> clazz = thiz.getClass();

        if (!REGISTRY.containsKey(clazz)) {
            final List<Field> fields = FieldUtil.getAllFields(thiz.getClass());
            boolean boss = false;

            for (int i = 0, size = fields.size(); i < size && !boss; i++) {
                final Field field = fields.get(i);

                field.setAccessible(true);

                if (BossBar.class.isAssignableFrom(field.getType())) {
                    boss = true;
                }

                field.setAccessible(false);
            }

            REGISTRY.put(thiz.getClass(), boss);
        }
    }

    @Override
    public boolean isBoss() {
        return REGISTRY.get(this.getClass());
    }

    @Override
    public void setBoss(final boolean boss) {
        REGISTRY.put(thiz().getClass(), boss);
    }

    protected Entity thiz() {
        // noinspection  ConstantConditions
        return (Entity) (Object) this;
    }
}

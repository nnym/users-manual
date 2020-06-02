package user11681.usersmanual.client.keyboard;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class ModKeyBinding extends FabricKeyBinding {
    protected ModKeyBinding(final Identifier identifier, final Type type, final int code, final String category) {
        super(identifier, type, code, category);
    }

    @Override
    public void setPressed(final boolean pressed) {
        if (!this.isPressed() && pressed) {
            this.onPress();
        } else {
            this.onHold();
        }

        super.setPressed(pressed);
    }

    protected void onPress() {
    }

    protected void onHold() {
    }
}

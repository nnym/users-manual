package user11681.usersmanual.client.gui.widget.scalable;

import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface ScalableWidgetSupplier extends Supplier <ScalableWidget> {
}

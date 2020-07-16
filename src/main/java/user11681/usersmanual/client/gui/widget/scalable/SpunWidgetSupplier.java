package user11681.usersmanual.client.gui.widget.scalable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public enum SpunWidgetSupplier implements ScalableWidgetSupplier {
    YELLOW_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(1, 129, 2, 22, 24, 2, 22, 24);

            return rectangle;
        }
    },

    YELLOW_SPIKED_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(26, 128, 10, 16, 26, 10, 16, 26);

            return rectangle;
        }
    },

    YELLOW_ROUNDED_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(54, 129, 7, 15, 22, 4, 21, 26);

            return rectangle;
        }
    },

    WHITE_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(1, 155, 2, 22, 24, 2, 22, 24);

            return rectangle;
        }
    },

    WHITE_SPIKED_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(26, 154, 10, 16, 26, 10, 16, 26);

            return rectangle;
        }
    },

    WHITE_ROUNDED_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(54, 155, 7, 15, 22, 4, 21, 26);

            return rectangle;
        }
    },

    BLUE_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(0, 29, 2, 198, 200, 2, 18, 20);

            return rectangle;
        }
    },

    GRAY_RECTANGLE {
        @Override
        public ScalableWidget get() {
            final ScalableWidget rectangle = new ScalableWidget(WIDGET_TEXTURE);

            rectangle.setSlices(0, 55, 2, 198, 200, 2, 18, 20);

            return rectangle;
        }
    },

    WINDOW {
        @Override
        public ScalableWidget get() {
            final ScalableWidget window = new ScalableWidget(WINDOW_TEXTURE);

            window.setSlices(14, 238, 252, 22, 126, 140);

            return window;
        }
    };

    private static final Identifier WIDGET_TEXTURE = new Identifier("textures/gui/advancements/widgets.png");
    private static final Identifier WINDOW_TEXTURE = new Identifier("textures/gui/advancements/window.png");
}

package user11681.usersmanual.client.gui.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public abstract class ExtendedSlider extends SliderWidget {
    public ExtendedSlider(final int x, final int y, final int width, final int height, final double value, final double min,
                          final double max, final Text text) {
        super(x, y, width, height, text, value / (min + max));

        this.value = value;

        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(new TranslatableText("%s: %s", this.getMessage(), this.value));
    }

    public void setValue(final double value) {
        this.value = value;

        this.applyValue();
    }

    @Override
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double amount) {
        return true;
    }
}

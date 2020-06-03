package user11681.usersmanual.client.gui.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.TranslatableText;

public abstract class ExtendedSlider extends SliderWidget {
    protected TranslatableText text;

    public ExtendedSlider(final int x, final int y, final int width, final int height, final double value, final double min,
                          final double max, final TranslatableText text) {
        super(x, y, width, height, value / (min + max));

        this.value = value;
        this.text = text;

        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(String.format("%s: %s", this.text.asFormattedString(), this.value));
    }

    public String getKey() {
        return this.text.getKey();
    }

    public TranslatableText getText() {
        return this.text;
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

package user11681.usersmanual.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import user11681.usersmanual.MainClient;
import user11681.usersmanual.client.gui.widget.ExtendedButtonWidget;

@Environment(EnvType.CLIENT)
public abstract class ScreenTab extends ModScreen {
    protected final List<ScreenTab> tabs;
    protected final List<ExtendedButtonWidget> tabButtons;
    protected final int index;

    protected ExtendedButtonWidget tab;

    public ScreenTab(final Text title, final List<ScreenTab> tabs) {
        super(title);

        this.tabs = tabs;
        this.tabButtons = new ArrayList<>();

        if (!tabs.contains(this)) {
            tabs.add(this);
        }

        this.index = tabs.indexOf(this);
    }

    @SafeVarargs
    protected final <T extends ButtonWidget> T[] addButtons(final T... buttons) {
        for (final T button : buttons) {
            this.addButton(button);
        }

        return buttons;
    }

    protected Text getLabel() {
        return this.title;
    }

    @Override
    public void init() {
        super.init();

        if (this.displayTabs()) {
            for (int index = 0, size = this.tabs.size(); index < size; index++) {
                final ExtendedButtonWidget button = this.addButton(new ExtendedButtonWidget(
                        this.width / 24,
                        this.height / 16 + index * Math.max(this.height / 16, 30),
                        Math.max(96, Math.round(width / 7.5F)),
                        20,
                        this.tabs.get(index).getLabel(),
                        this.setTabAction(index)
                ));

                this.tabButtons.add(button);

                if (index == this.index) {
                    this.tab = button;
                    button.active = false;
                }
            }
        }
    }

    protected boolean displayTabs() {
        return true;
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseScrolled(final double x, final double y, final double dWheel) {
        if (dWheel != 0) {
            final int index = MathHelper.clamp((int) (this.index - dWheel), 0, this.tabs.size() - 1);

            if (index != this.index) {
                this.setTab(index);

                return true;
            }
        }

        return super.mouseScrolled(x, y, dWheel);
    }

    public void setTab(final int tab) {
        MainClient.CLIENT.openScreen(this.tabs.get(tab));
    }

    public void refresh() {
        this.buttons.clear();
        this.init();
    }

    public int getTop(final int rows) {
        return this.getTop(this.height / 16, rows);
    }

    public int getTop(final int sep, final int rows) {
        return (this.height - (rows - 1) * sep) / 2;
    }

    public int getHeight(final int rows, final int row) {
        return this.getTop(rows) + row * this.height / 16;
    }

    public int getHeight(final int sep, final int rows, final int row) {
        return this.getTop(rows, sep) + row * sep;
    }

    protected PressAction setTabAction(final int index) {
        return (final ButtonWidget button) -> ScreenTab.this.setTab(index);
    }
}

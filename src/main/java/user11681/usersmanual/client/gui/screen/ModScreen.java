package user11681.usersmanual.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import user11681.usersmanual.Client;

public abstract class ModScreen extends Screen {
    public static final ItemRenderer ITEM_RENDERER = Client.CLIENT.getItemRenderer();
    public static final ItemModels ITEM_MODELS = ITEM_RENDERER.getModels();
    public static final TextureManager TEXTURE_MANAGER = Client.CLIENT.getTextureManager();
    public static final TextRenderer TEXT_RENDERER = Client.CLIENT.textRenderer;
    public static final ResourceManager RESOURCE_MANAGER = Client.CLIENT.getResourceManager();
    public static final Identifier GLINT = new Identifier("textures/misc/enchanted_item_glint.png");

    protected int textureWidth;
    protected int textureHeight;

    protected ModScreen(final Text title) {
        super(title);
    }

    protected void setTextureSize(final int size) {
        this.textureHeight = this.textureWidth = size;
    }

    @SafeVarargs
    protected final <T extends AbstractButtonWidget> T[] addButtons(final T... buttons) {
        for (final T button : buttons) {
            this.addButton(button);
        }

        return buttons;
    }

    protected <T extends Collection<U>, U extends AbstractButtonWidget> T addButtons(final T buttons) {
        for (final AbstractButtonWidget button : buttons) {
            this.addButton(button);
        }

        return buttons;
    }

    @SafeVarargs
    protected final <T extends AbstractButtonWidget> void removeButtons(final T... buttons) {
        for (final T button : buttons) {
            this.removeButton(button);
        }
    }

    protected <T extends Collection<U>, U extends AbstractButtonWidget> void removeButtons(final T buttons) {
        this.buttons.removeAll(buttons);
        this.children.removeAll(buttons);
    }

    protected <T extends AbstractButtonWidget> void removeButton(final T button) {
        this.buttons.remove(button);
        this.children.remove(button);
    }

    public void drawInterpolatedTexture(final MatrixStack stack, int x, int y, final int startU, final int startV, final int middleU, final int middleV,
                                        final int endU, final int endV, final int finalU, final int finalV, final int width, final int height) {
        final int leftWidth = middleU - startU;
        final int topHeight = middleV - startV;

        this.drawHorizontallyInterpolatedTexture(stack, x, y, startU, startV, middleU, endU, finalU, width, topHeight);
        this.drawHorizontallyInterpolatedTexture(stack, x, y + height - topHeight, startU, endV, middleU, endU, finalU, width, topHeight);
        this.drawVerticallyInterpolatedTexture(stack, x, y, startU, startV, middleV, endV, finalV, leftWidth, height);
        this.drawVerticallyInterpolatedTexture(stack, x + width - leftWidth, y, endU, startV, middleV, endV, finalV, leftWidth, height);
        this.drawVerticallyInterpolatedTexture(stack, x + leftWidth, y + topHeight, middleU, middleV, endV, width - 2 * leftWidth, height - 2 * topHeight);
    }

    public void drawHorizontallyInterpolatedTexture(final MatrixStack stack, int x, final int y, int startU, final int startV, final int middleU,
                                                    final int endU, final int finalU, int width, final int height) {
        if (middleU < startU) {
            startU = middleU;
        }

        final int startWidth = middleU - startU;
        final int finalWidth = finalU - endU;

        drawTexture(stack, x, y, startU, startV, startWidth, height, this.textureWidth, this.textureHeight);

        width -= startWidth + finalWidth;
        x += startWidth;
        x = this.drawHorizontallyInterpolatedTexture(stack, x, y, startV, middleU, endU, width, height);

        drawTexture(stack, x, y, endU, startV, finalWidth, height, this.textureWidth, this.textureHeight);
    }

    public int drawHorizontallyInterpolatedTexture(final MatrixStack stack, int x, final int y, final int startV, final int middleU, final int endU, int width,
                                                   final int height) {
        while (width > 0) {
            final int middleWidth = Math.min(width, endU - middleU);

            drawTexture(stack, x, y, middleU, startV, middleWidth, height, this.textureWidth, this.textureHeight);

            x += middleWidth;
            width -= middleWidth;
        }

        return x;
    }

    public void drawVerticallyInterpolatedTexture(final MatrixStack stack, final int x, int y, final int startU, final int startV, final int middleV,
                                                  final int endV, final int finalV, final int width, int height) {
        final int startHeight = middleV - startV;
        final int finalHeight = finalV - endV;

        drawTexture(stack, x, y, startU, startV, width, startHeight, this.textureWidth, this.textureHeight);
        height -= startHeight + finalHeight;
        y += startHeight;
        y = this.drawVerticallyInterpolatedTexture(stack, x, y, startU, middleV, endV, width, height);

        drawTexture(stack, x, y, startU, endV, width, finalHeight, this.textureWidth, this.textureHeight);
    }

    public int drawVerticallyInterpolatedTexture(final MatrixStack stack, final int x, int y, final int startU, final int middleV, final int endV,
                                                 final int width, int height) {
        while (height > 0) {
            final int middleHeight = Math.min(height, endV - middleV);

            drawTexture(stack, x, y, startU, middleV, width, middleHeight, this.textureWidth, this.textureHeight);
            y += middleHeight;
            height -= middleHeight;
        }

        return y;
    }

    public void renderBackground(final Identifier identifier, int x, int y, final int width, final int height) {
        this.renderBackground(identifier, x, y, width, height, 64, 0);
    }

    public void renderBackground(final Identifier identifier, int x, int y, final int width, final int height, final int chroma) {
        this.renderBackground(identifier, x, y, width, height, chroma, 0);
    }

    public void renderBackground(final Identifier identifier, int x, int y, final int width, final int height, final int chroma, final int alpha) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();
        final float f = 1 << 5;
        final float endX = x + width;
        final float endY = y + height;

        TEXTURE_MANAGER.bindTexture(identifier);
        RenderSystem.color4f(1, 1, 1, 1);

        builder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        builder.vertex(x, endY, 0).color(chroma, chroma, chroma, 255).texture(0, endY / f + alpha).next();
        builder.vertex(endX, endY, 0).color(chroma, chroma, chroma, 255).texture(endX / f, endY / f + alpha).next();
        builder.vertex(endX, y, 0).color(chroma, chroma, chroma, 255).texture(endX / f, alpha).next();
        builder.vertex(x, y, 0).color(chroma, chroma, chroma, 255).texture(0, alpha).next();
        tessellator.draw();
    }

    public void renderGuiItem(final ItemStack itemStack, final int x, final int y, final int zOffset) {
        final ItemRenderer renderer = this.itemRenderer;

        renderer.zOffset += zOffset;
        this.withZ(zOffset, () -> renderer.renderGuiItemIcon(itemStack, x, y));
        renderer.zOffset -= zOffset;
    }

    public void withZ(final int offset, final Runnable runnable) {
        this.addZOffset(offset);
        runnable.run();
        this.addZOffset(-offset);
    }

    public void addZOffset(final int zOffset) {
        this.setZOffset(this.getZOffset() + zOffset);
    }

    public static Identifier getTexture(final SpriteAtlasTexture sprite) {
        final String[] location = sprite.getId().toString().split(":");

        return new Identifier(String.format("%s:textures/%s.png", location[0], location[1]));
    }

    public List<String> wrap(final int width, final String... strings) {
        final List<String> lines = new ArrayList<>();

        for (final String string : strings) {
            lines.addAll(wrap(width, string));
        }

        return lines;
    }

    public List<String> wrap(final int width, final String string) {
        final List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (final String word : string.split(" ")) {
            final int wordWidth = Client.CLIENT.textRenderer.getWidth(word);
            final int lineWidth = TEXT_RENDERER.getWidth(currentLine.toString());
            final boolean wrap = lineWidth + wordWidth > width;

            if (wrap && currentLine.length() == 0) {
                lines.add(word);
            } else {
                if (wrap) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder();
                }

                currentLine.append(word).append(" ");
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}

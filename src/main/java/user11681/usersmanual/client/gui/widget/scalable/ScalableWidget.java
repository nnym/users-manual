package user11681.usersmanual.client.gui.widget.scalable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ScalableWidget extends ButtonWidget {
    protected static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public final int[][][] middles;
    public final int[][][] corners;
    public final int[][] border;

    protected final TextureManager textureManager;
    protected final Identifier texture;

    public int x;
    public int y;
    public int z;

    public int u;
    public int v;

    public int width;
    public int height;

    public int textureWidth;
    public int textureHeight;

    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;

    public ScalableWidget(final Identifier texture) {
        super(0, 0, 0, 0, LiteralText.EMPTY, (final ButtonWidget widget) -> {});

        this.middles = new int[5][4][2];
        this.corners = new int[4][4][2];
        this.border = new int[4][2];
        this.textureManager = CLIENT.getTextureManager();
        this.texture = texture;
        this.textureHeight = this.textureWidth = 256;
    }

    public void color3f(final float r, final float g, final float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1;
    }

    public void color4f(final float r, final float g, final float b, final float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public void renderButton(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        if (this.isHovered()) {
            this.renderToolTip(matrices, mouseX, mouseY);
        }

        this.textureManager.bindTexture(this.texture);

        this.resetColor();

        this.renderCorners(matrices);
        this.renderMiddles(matrices);
    }

    private void renderCorners(final MatrixStack matrices) {
        final int[][][] corners = this.corners;

        for (int i = 0, length = corners.length; i < length; i++) {
            final int[][] corner = corners[i];
            final int[] topLeft = corner[0];
            final int u = topLeft[0];
            final int v = topLeft[1];
            final int width = corner[1][0] - u;
            final int height = corner[2][1] - v;

            drawTexture(matrices, this.x + i % 2 * (this.width - width), this.y + i / 2 * (this.height - height), this.z, u, v, width, height, this.textureHeight, this.textureWidth);
        }
    }

    private void renderMiddles(final MatrixStack matrices) {
        final int[][][] middles = this.middles;
        final int[][][] corners = this.corners;

        final int middleWidth = this.width - corners[0][1][0] + corners[1][0][0] - corners[1][1][0];
        final int middleHeight = this.height - corners[0][2][1] + corners[2][0][1] - corners[2][2][1];

        for (int i = 0, length = middles.length; i < length; i++) {
            final int[][] middle = middles[i];
            final int u = middle[0][0];
            final int v = middle[0][1];
            final int endU = middle[1][0];
            final int endV = middle[2][1];
            final int maxWidth = endU - u;
            final int maxHeight = endV - v;
            int remainingHeight = i % 4 == 0 ? maxHeight : middleHeight;

            final int endX = i % 2 == 0
                    ? this.x + middle[0][0] + middleWidth
                    : i == 1
                    ? this.x + middle[1][0]
                    : this.x + middles[1][1][0] + middleWidth + middle[1][0] - middle[0][0];

            final int endY = i % 4 != 0
                    ? this.y + middle[0][1] + middleHeight
                    : i == 0
                    ? this.y + middle[2][1]
                    : this.y + middles[0][2][1] + middleHeight + middle[2][1] - middle[0][1];

            while (remainingHeight > 0) {
                final int drawnHeight = Math.min(remainingHeight, maxHeight);
                final int y = endY - remainingHeight;
                int remainingWidth = i % 2 == 0 ? middleWidth : maxWidth;

                while (remainingWidth > 0) {
                    final int drawnWidth = Math.min(remainingWidth, maxWidth);

                    drawTexture(matrices, endX - remainingWidth, y, this.z, u, v, drawnWidth, drawnHeight, this.textureHeight, this.textureWidth);

                    remainingWidth -= drawnWidth;
                }

                remainingHeight -= drawnHeight;
            }
        }
    }

    protected void drawBorder() {

    }

    public void setSlices(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2) {
        this.corners[2][3][0] = this.corners[2][1][0] = this.corners[0][3][0] = this.corners[0][1][0] = u0;
        this.corners[3][2][0] = this.corners[3][0][0] = this.corners[1][2][0] = this.corners[1][0][0] = u1;
        this.corners[3][3][0] = this.corners[3][1][0] = this.corners[1][3][0] = this.corners[1][1][0] = u2;
        this.corners[1][3][1] = this.corners[1][2][1] = this.corners[0][3][1] = this.corners[0][2][1] = v0;
        this.corners[3][1][1] = this.corners[3][0][1] = this.corners[2][1][1] = this.corners[2][0][1] = v1;
        this.corners[3][3][1] = this.corners[3][2][1] = this.corners[2][3][1] = this.corners[2][2][1] = v2;

        this.middles[4][2][0] = this.middles[4][0][0] = this.middles[2][2][0] = this.middles[2][0][0] = this.middles[1][3][0] = this.middles[1][1][0] = this.middles[0][2][0] = this.middles[0][0][0] = u0;
        this.middles[4][3][0] = this.middles[4][1][0] = this.middles[3][2][0] = this.middles[3][0][0] = this.middles[2][3][0] = this.middles[2][1][0] = this.middles[0][3][0] = this.middles[0][1][0] = u1;
        this.middles[3][3][0] = this.middles[3][1][0] = u2;
        this.middles[3][1][1] = this.middles[3][0][1] = this.middles[2][1][1] = this.middles[2][0][1] = this.middles[1][1][1] = this.middles[1][0][1] = this.middles[0][3][1] = this.middles[0][2][1] = v0;
        this.middles[4][1][1] = this.middles[4][0][1] = this.middles[3][3][1] = this.middles[3][2][1] = this.middles[2][3][1] = this.middles[2][2][1] = this.middles[1][3][1] = this.middles[1][2][1] = v1;
        this.middles[4][3][1] = this.middles[4][2][1] = v2;
    }

    private void detectBorder() {

    }

    @SuppressWarnings("deprecation")
    protected void resetColor() {
        RenderSystem.color4f(this.r, this.g, this.b, this.a);
    }
}

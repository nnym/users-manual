package user11681.usersmanual.client.gui.widget.scalable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import user11681.usersmanual.resource.Resources;

public class ScalableWidget extends ButtonWidget {
    protected static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public final int[][][][] slices;
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

    private boolean dirty;

    public ScalableWidget(final int x, final int y, final int width, final int height, final Identifier texture) {
        this(x, y, width, height, width, height, texture);
    }

    public ScalableWidget(final int x, final int y, final int width, final int height, final int textureWidth, final int textureHeight, final Identifier texture) {
        this(x, y, texture);

        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public ScalableWidget(final int x, final int y, final Identifier texture) {
        this(texture);

        this.x = x;
        this.y = y;
    }

    public ScalableWidget(final Identifier texture) {
        super(0, 0, 0, 0, LiteralText.EMPTY, (final ButtonWidget widget) -> {});

        this.middles = new int[5][4][2];
        this.corners = new int[4][4][2];
        this.border = new int[4][2];
        this.slices = new int[][][][]{this.middles, this.corners};
        this.textureManager = CLIENT.getTextureManager();
        this.texture = texture;
        this.dirty = true;
    }

    public void color3f(final float r, final float g, final float b) {
        this.color4f(r, g, b, 1);
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

        final int middleWidth = this.width - this.corners[0][0][0] - this.corners[1][0][0];
        final int middleHeight = this.height - this.corners[0][0][1] - this.corners[2][0][1];

        for (int i = 0, length = middles.length; i < length; i++) {
            final int[][] middle = middles[i];
            final int u = middle[0][0];
            final int v = middle[0][1];
            final int endU = middle[1][0];
            final int endV = middle[2][1];
            final int maxWidth = endU - u;
            final int maxHeight = endV - v;
            int endX = this.x + this.width;
            int endY = this.y + this.height;
            int remainingHeight = middleHeight;

            switch (i) {
                case 0:
                case 4:
                    endX = this.x + middle[0][0] + middleWidth;
                    break;
                case 1:
                    endX = this.x + middle[1][0];
                    break;
                case 3:
                    endX = this.x + middles[1][1][0] + middleWidth + middle[1][0];
            }

            switch (i) {
                case 0:
                    endY = this.y + middle[1][1];
                    break;
                case 1:
                case 3:
                    endY = this.y + middle[0][1];
                    break;
                case 4:
                    endY = this.y + middles[0][2][1] + middleHeight + middle[2][1];
            }

            if (i % 2 == 0) {
                endX = this.x + middle[0][0] + middleWidth;
            }

            if (i % 4 != 0) {
                endY = this.y + middle[0][1] + middleHeight;
            }

            while (remainingHeight > 0) {
                final int drawnHeight = Math.min(remainingHeight, maxHeight);
                final int y = endY - remainingHeight;
                int remainingWidth = this.width;

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

    protected void slice() {
        final int[][][] pixels = Resources.getPixels(this.texture);

        this.dirty = false;
    }

    public void setU(final int u) {
        this.u = u;

        this.dirty = true;
    }

    public void setV(final int v) {
        this.v = v;

        this.dirty = true;
    }

    public void setUV(final int u, final int v) {
        this.u = u;
        this.v = v;

        this.dirty = true;
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

        this.dirty = true;
    }

    private void detectBorder() {

    }

    @SuppressWarnings("deprecation")
    protected void resetColor() {
        RenderSystem.color4f(this.r, this.g, this.b, this.a);
    }
}

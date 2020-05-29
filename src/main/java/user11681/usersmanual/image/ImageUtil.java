package user11681.usersmanual.image;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import user11681.usersmanual.Main;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@SuppressWarnings("ConstantConditions")
@Environment(EnvType.CLIENT)
public class ImageUtil {
    public static final ResourceManager RESOURCE_MANAGER = MinecraftClient.getInstance().getResourceManager();

    @Nonnull
    public static BufferedImage readTexture(final Identifier identifier) {
        try {
            return ImageIO.read(RESOURCE_MANAGER.getResource(identifier).getInputStream());
        } catch (final IOException exception) {
            Main.LOGGER.error(exception);
        }

        return null;
    }

    public static int[][][] getPixels(final BufferedImage image) {
        return getPixels(image, 0, 0, image.getWidth(), image.getHeight());
    }

    public static int[][][] getPixels(final BufferedImage image, final int u, final int v, final int width, final int height) {
        final int[][][] pixels = new int[image.getHeight()][image.getWidth()][4];
        final Raster raster = image.getData();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = raster.getPixel(u + x, v + y, (int[]) null);
            }
        }

        return pixels;
    }

    public static int[] getRGB(int color) {
        final int[] rgb = new int[3];

        rgb[0] = color / 0xFF0000;
        rgb[1] = (color %= 0xFF0000) / 0xFF00;
        rgb[2] = color % 0xFF00 / 0xFF;

        return rgb;
    }

    public static ByteArrayInputStream toInputStream(final Raster raster) {
        return new ByteArrayInputStream(((DataBufferByte) raster.getDataBuffer()).getData());
    }

    public static NativeImage toNativeImage(final Raster raster) {
        try {
            return NativeImage.read(toInputStream(raster));
        } catch (IOException exception) {
            Main.LOGGER.error(exception);
        }

        return null;
    }
}

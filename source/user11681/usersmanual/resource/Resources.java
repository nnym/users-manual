package user11681.usersmanual.resource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("ConstantConditions")
public class Resources {
    public static ByteArrayInputStream toInputStream(final Raster raster) {
        return new ByteArrayInputStream(((DataBufferByte) raster.getDataBuffer()).getData());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static byte[] getBytes(final InputStream input) {
        try {
            final byte[] content = new byte[input.available()];

            while (input.read(content) > -1);

            return content;
        } catch (final IOException exception) {
            throw new RuntimeException("An error occurred while attempting to read a resource to a byte array.", exception);
        }
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

        rgb[0] = color >> 16 & 0xFF;
        rgb[1] = color >> 8 & 0xFF;
        rgb[2] = color & 0xFF;

        return rgb;
    }
}

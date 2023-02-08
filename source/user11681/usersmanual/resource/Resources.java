package user11681.usersmanual.resource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("ConstantConditions")
public class Resources {
    public static ByteArrayInputStream toInputStream(Raster raster) {
        return new ByteArrayInputStream(((DataBufferByte) raster.getDataBuffer()).getData());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static byte[] getBytes(InputStream input) {
        try {
            return input.readAllBytes();
        } catch (IOException exception) {
            throw new RuntimeException("An error occurred while attempting to read a resource to a byte array.", exception);
        }
    }

    public static int[][][] getPixels(BufferedImage image) {
        return getPixels(image, 0, 0, image.getWidth(), image.getHeight());
    }

    public static int[][][] getPixels(BufferedImage image, int u, int v, int width, int height) {
        var pixels = new int[image.getHeight()][image.getWidth()][4];
        var raster = image.getData();

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                pixels[y][x] = raster.getPixel(u + x, v + y, (int[]) null);
            }
        }

        return pixels;
    }

    public static int[] getRGB(int color) {
        return new int[]{
            color >> 16 & 0xFF,
            color >> 8 & 0xFF,
            color & 0xFF
        };
    }
}

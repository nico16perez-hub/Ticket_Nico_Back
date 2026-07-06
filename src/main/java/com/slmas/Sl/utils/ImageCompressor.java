package com.slmas.Sl.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.slmas.Sl.domain.Images;
import org.imgscalr.Scalr;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Iterator;

public class ImageCompressor {

    public static Images compressImage(byte[] imageData) throws Exception {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

        // Rotate the image based on EXIF orientation
        BufferedImage rotatedImage = rotateImage(originalImage, imageData);

        // Resize the rotated image to 1280x720 or 720x1280
        BufferedImage resizedImage = resizeImage(rotatedImage, 1280, 720);
        BufferedImage resizedThumbnail = resizeImage(rotatedImage, 320, 184);
        // Convert resized image to WebP

        byte [] fullImage = convertToJPG(resizedImage);
        byte [] thumbnail = convertToJPG(resizedThumbnail);
        return new Images(fullImage, thumbnail);
    }

    private static BufferedImage rotateImage(BufferedImage originalImage, byte[] imageData) throws Exception {
        int orientation = 1; // Default orientation (no rotation)
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageData));
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            }
        } catch (Exception e) {
            throw new Exception("Error al rotar la imagen: " + e.getMessage());
        }

        switch (orientation) {
            case 3:
                return Scalr.rotate(originalImage, Scalr.Rotation.CW_180);
            case 6:
                return Scalr.rotate(originalImage, Scalr.Rotation.CW_90);
            case 8:
                return Scalr.rotate(originalImage, Scalr.Rotation.CW_270);
            default:
                return originalImage; // No rotation needed
        }
    }

    private static BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        return Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);
    }

    private static byte[] convertToJPG(BufferedImage image) throws IOException {
        // Convert image to RGB if it has transparency (e.g., PNG with alpha channel)
        if (image.getType() == BufferedImage.TYPE_INT_ARGB || image.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setColor(Color.WHITE); // Set background color to white
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = newImage;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("ºNo JPG Image Writers found");
        }
        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.85f); // Adjust the quality as needed
        ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(ios);
        writer.write(null, new IIOImage(image, null, null), param);
        ios.close();
        writer.dispose();
        return outputStream.toByteArray();
    }
}

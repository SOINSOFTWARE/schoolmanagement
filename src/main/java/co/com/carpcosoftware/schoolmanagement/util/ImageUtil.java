/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * This class is attempt to encodes and decodes images
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class ImageUtil {

	private static final String IMG_TYPE = "png";

	/**
	 * Decode string to image
	 * 
	 * @param imageString
	 *            The string to decode
	 * @return decoded image
	 */
	public static BufferedImage decodeToImage(String imageString) {

		BufferedImage image = null;
		byte[] imageByte;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Encode image to string
	 * 
	 * @param image
	 *            The image to encode
	 * @return encoded string
	 */
	public static String encodeToString(BufferedImage image) {
		String imageString = null;

		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			ImageIO.write(image, IMG_TYPE, bos);
			byte[] imageBytes = bos.toByteArray();

			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	/**
	 * Encode image to byte array
	 * 
	 * @param image
	 *            The image to encode
	 * @return encoded byte array
	 */
	public static byte[] encodeToByteArray(BufferedImage image) {
		byte[] imageInByte = null;
		if (image != null) {
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				ImageIO.write((BufferedImage) image, IMG_TYPE, baos);
				baos.flush();
				imageInByte = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imageInByte;
	}

	public static BufferedImage byteArrayToBufferedImage(byte[] imageInByte) {
		BufferedImage bImageFromConvert = null;
		if (imageInByte != null) {
			InputStream in = new ByteArrayInputStream(imageInByte);
			try {
				bImageFromConvert = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bImageFromConvert;
	}

}

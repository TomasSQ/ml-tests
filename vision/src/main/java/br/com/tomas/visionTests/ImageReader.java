package br.com.tomas.visionTests;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class ImageReader {

	static List<BufferedImage> getImages() {
		List<BufferedImage> images = new ArrayList<>();
		IntStream.range(1, 11).forEach(i -> images.add(readImage(i)));
		return images;
	}

	static BufferedImage getImage(String name) {
		try {
			return ImageIO.read(ImageReader.class.getClassLoader().getResourceAsStream(name));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static BufferedImage readImage(int i) {
		try {
			return ImageIO.read(ImageReader.class.getClassLoader().getResourceAsStream(String.format("%02d.jpg", i)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

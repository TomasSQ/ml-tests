package br.com.tomas.visionTests;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

class FacesWritter {

	static void write(List<Face> images) {
		IntStream.range(0, images.size()).forEach(i -> writeImage(images, i));
	}

	private static boolean writeImage(List<Face> images, int i) {
		try {
			return ImageIO.write(images.get(i).getImage(), "jpg", new File(String.format("modified-%02d.jpg", i + 1)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

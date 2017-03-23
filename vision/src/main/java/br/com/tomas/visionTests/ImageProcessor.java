package br.com.tomas.visionTests;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageProcessor {

	public static void main(String args[]) throws Exception {
		new ImageProcessor().run();
	}

	private void run() throws Exception {
		List<Rectangle> facesR = FacesReader.getFaces();
		List<BufferedImage> images = ImageReader.getImages();
		List<Face> faces = Face.list(facesR, images);

		ImageHelper.scale(faces);

		ImageHelper.move(faces);

		FacesWritter.write(faces);
	}
}

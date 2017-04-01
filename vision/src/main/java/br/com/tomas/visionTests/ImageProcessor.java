package br.com.tomas.visionTests;

import br.com.tomas.visionTests.helper.ImageHelper;

import java.util.List;

public class ImageProcessor {

	public static void main(String args[]) throws Exception {
		new ImageProcessor().run();
	}

	private void run() throws Exception {
		List<Face> faces = FacesReader.getDefault();

		ImageHelper.scale(faces);

		ImageHelper.move(faces);

		FacesWritter.write(faces);
	}
}

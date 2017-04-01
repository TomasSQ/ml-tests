package br.com.tomas.visionTests.helper;

import br.com.tomas.visionTests.Face;

import java.awt.*;
import java.util.List;

public class ImageHelper {

	public static List<Face> scale(List<Face> faces) {
		faces.forEach(face -> face.scale(600));

		return faces;
	}

	public static void move(List<Face> faces) {
		Dimension imageSize = new Dimension(maxWidth(faces), maxHeight(faces));
		Point middle = new Point(imageSize.width / 2, imageSize.height / 2);
		faces.forEach(face -> face.move(middle, imageSize));
	}

	private static int maxWidth(List<Face> faces) {
		final int maxWidth[] = {0};
		faces.forEach(face -> {
			if (face.getImage().getWidth() > maxWidth[0]) {
				maxWidth[0] = face.getImage().getWidth();
			}
		});

		return maxWidth[0];
	}

	private static int maxHeight(List<Face> faces) {
		final int maxHeight[] = {0};
		faces.forEach(face -> {
			if (face.getImage().getHeight() > maxHeight[0]) {
				maxHeight[0] = face.getImage().getHeight();
			}
		});

		return maxHeight[0];
	}

}

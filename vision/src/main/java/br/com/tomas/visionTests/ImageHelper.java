package br.com.tomas.visionTests;

import java.awt.*;
import java.util.List;

class ImageHelper {

	static List<Face> scale(List<Face> faces) {
		Dimension minD = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		faces.forEach(face -> {
			if (face.getImage().getWidth() < minD.getWidth()) {
				minD.setSize(face.getImage().getWidth(), minD.getHeight());
			}
			if (face.getImage().getHeight() < minD.getHeight()) {
				minD.setSize(minD.getWidth(), face.getImage().getHeight());
			}
		});

		faces.forEach(face -> face.scale(minD.width, minD.height));

		return faces;
	}

	public static void move(List<Face> faces) {
		Dimension imageSize = new Dimension(getImageWidth(faces), getImageHeight(faces));
		Point start = new Point(getMaxX(faces)[0], getMaxY(faces)[0]);

		faces.forEach(face -> face.move(start, imageSize));
	}

	private static int getImageHeight(List<Face> faces) {
		int[] minYIndex = getMinY(faces);
		int[] maxYIndex = getMaxY(faces);
		return maxYIndex[0] - minYIndex[0] + faces.get(minYIndex[1]).getImage().getHeight();
	}

	private static int[] getMaxY(List<Face> faces) {
		final int[] maxYIndex = {0, 0};
		final int[] index = {0};
		faces.forEach(face -> {
			if (face.getFace().y > maxYIndex[0]) {
				maxYIndex[0] = face.getFace().y;
				maxYIndex[1] = index[0];
			}
			index[0]++;
		});

		return maxYIndex;
	}

	private static int[] getMinY(List<Face> faces) {
		final int[] minYIndex = {Integer.MAX_VALUE, 0};
		final int[] index = {0};
		faces.forEach(face -> {
			if (face.getFace().y < minYIndex[0]) {
				minYIndex[0] = face.getFace().y;
				minYIndex[1] = index[0];
			}
			index[0]++;
		});

		return minYIndex;
	}

	private static int getImageWidth(List<Face> faces) {
		int[] minXIndex = getMinX(faces);
		return getMaxX(faces)[0] - minXIndex[0] + faces.get(minXIndex[1]).getImage().getWidth();
	}

	private static int[] getMaxX(List<Face> faces) {
		final int[] maxXIndex = {0, 0};
		final int[] index = {0};
		faces.forEach(face -> {
			if (face.getFace().x > maxXIndex[0]) {
				maxXIndex[0] = face.getFace().x;
				maxXIndex[1] = index[0];
			}
			index[0]++;
		});

		return maxXIndex;
	}

	private static int[] getMinX(List<Face> faces) {
		final int[] minXIndex = {Integer.MAX_VALUE, 0};
		final int[] index = {0};
		faces.forEach(face -> {
			if (face.getFace().x < minXIndex[0]) {
				minXIndex[0] = face.getFace().x;
				minXIndex[1] = index[0];
			}
			index[0]++;
		});

		return minXIndex;
	}
}

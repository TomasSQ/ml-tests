package br.com.tomas.visionTests;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

class FacesReader {

	private static final String FACES_TXT = "faces_02.csv";

	static List<Face> getDefault() {
		List<Face> faces = new ArrayList<>();
		readFaces().forEach(faceStr -> {
			String[] faceArr = faceStr.split(",");

			BufferedImage image = ImageReader.getImage(faceArr[0].replace("./src/main/resources/", ""));
			if (image.getHeight() < image.getWidth()) {
				int x = Integer.parseInt(faceArr[1]);
				int y = Integer.parseInt(faceArr[2]);
				int x1 = Integer.parseInt(faceArr[3]);
				int y1 = Integer.parseInt(faceArr[4]);
				Rectangle r = new Rectangle(x, y, x1 - x, y1 - y);
				faces.add(new Face(r, image));
			}
		});

		return faces;
	}

	private static List<String> readFaces() {
		try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(FACES_TXT).toURI()))) {
			List<String> faces = new ArrayList<>();
			stream.forEach(faces::add);

			return faces;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

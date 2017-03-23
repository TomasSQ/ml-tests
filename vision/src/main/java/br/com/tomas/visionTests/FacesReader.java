package br.com.tomas.visionTests;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

class FacesReader {

	private static final String FACES_TXT = "faces.txt";

	static List<Rectangle> getFaces() {
		JsonArray facesJson = facesAsJson(readFaces());
		List<Rectangle> faces = new ArrayList<>();

		facesJson.forEach(elem -> faces.add(faceJsonAsRectangle(elem.getAsJsonArray())));

		return faces;
	}

	private static java.util.List<String> readFaces() {
		try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(FACES_TXT).toURI()))) {
			java.util.List<String> faces = new ArrayList<>();
			stream.forEach(faces::add);

			return faces;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Rectangle faceJsonAsRectangle(JsonArray faceJson) {
		int minX = faceJson.get(0).getAsJsonObject().get("x").getAsInt();
		int minY = faceJson.get(0).getAsJsonObject().get("y").getAsInt();
		int maxX = faceJson.get(2).getAsJsonObject().get("x").getAsInt();
		int maxY = faceJson.get(2).getAsJsonObject().get("y").getAsInt();
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

	private static JsonArray facesAsJson(java.util.List<String> faces) {
		JsonArray facesJson = new JsonArray();
		faces.forEach(face -> facesJson.add(new JsonParser().parse(face)));
		return facesJson;
	}
}

package br.com.tomas.visionTests;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class Face {

	private Rectangle face;
	private BufferedImage image;

	public Face(Rectangle face, BufferedImage image) {
		this.face = face;
		this.image = image;
	}

	public Face scale(int width, int height) {
		double widthRatio = 1.0d * width / image.getWidth();
		double heightRatio = 1.0d * height / image.getHeight();
		face = new Rectangle((int) (face.x * widthRatio), (int) (face.y * heightRatio), (int) (face.width * widthRatio), (int) (face.height * heightRatio));

		BufferedImage resized = new BufferedImage(width, height, image.getType());
		Graphics2D g = resized.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		image = resized;

		return this;
	}

	public Face move(Point start, Dimension dimension) {
		BufferedImage moved = new BufferedImage(dimension.width, dimension.height, image.getType());
		Graphics2D g = moved.createGraphics();
		g.drawImage(image, start.x - face.x, start.y - face.y, image.getWidth(), image.getHeight(), null);
		g.dispose();
		image = moved;

		return this;
	}

	public Rectangle getFace() {
		return face;
	}

	public void setFace(Rectangle face) {
		this.face = face;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public static List<Face> list(List<Rectangle> facesR, List<BufferedImage> images) {
		List<Face> faces = new ArrayList<>(facesR.size());

		IntStream.range(0, facesR.size()).forEach(i -> faces.add(new Face(facesR.get(i), images.get(i))));

		return faces;
	}
}

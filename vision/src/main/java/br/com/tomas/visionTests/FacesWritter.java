package br.com.tomas.visionTests;

import br.com.tomas.visionTests.helper.GifSequenceWriter;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

class FacesWritter {

	static void write(List<Face> faces) {
		IntStream.range(0, faces.size()).forEach(i -> writeImage(faces, i));

		writeGif(faces);
	}

	private static void writeGif(List<Face> faces) {
		try {
			ImageOutputStream output = new FileImageOutputStream(new File("faces.gif"));

			GifSequenceWriter writer = new GifSequenceWriter(output, faces.get(0).getImage().getType(), 350, false);

			faces.forEach(face -> {
				try {
					writer.writeToSequence(face.getImage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			writer.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean writeImage(List<Face> images, int i) {
		try {
			return ImageIO.write(images.get(i).getImage(), "jpg", new File(String.format("modified-%02d.jpg", i + 1)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

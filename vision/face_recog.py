import os
import csv

import numpy as np
import cv2

import face_recognition

PATH = './src/main/resources'
TARGET_PATH = '{}/selfies_europe'.format(PATH)

def get_known_face_encoding(image_path):
	known_image = face_recognition.load_image_file(image_path)
	known_face_encoding = face_recognition.face_encodings(known_image)[0]

	return known_face_encoding

def find_known_face(image_path, known_face_encoding):
	unknown_image = face_recognition.load_image_file(image_path)

	unknown_face_encodings = face_recognition.face_encodings(unknown_image)

	for i, unknown_face_encoding in enumerate(unknown_face_encodings):
		results = face_recognition.compare_faces([known_face_encoding], unknown_face_encoding)
		if not results[0]:
			continue

		y0, x1, y1, x0 = face_recognition.face_locations(unknown_image)[i]

		return x0, y0, x1, y1

	return 0, 0, 0, 0

def write_csv(r):
	with open('./src/main/resources/faces_02.csv', 'wb') as csvfile:
		spamwriter = csv.writer(csvfile)
		for f in r:
			spamwriter.writerow(f)

if __name__ == "__main__":
	known_face_encoding = get_known_face_encoding("{}/my_faces/xuly.jpg".format(PATH))

	image_paths = [os.path.join(TARGET_PATH, f) for f in os.listdir(TARGET_PATH)]

	r = []
	for image_path in image_paths:
		print('Processing', image_path)
		x0, y0, x1, y1 = find_known_face(image_path, known_face_encoding)
		print(image_path, x0, y0, x1, y1)
		if x0 != x1:
			r.append([image_path, x0, y0, x1, y1])

	write_csv(r)

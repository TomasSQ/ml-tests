import os
import sys
import csv

import numpy as np
import cv2

PATH = './src/main/resources'
TARGET_PATH = '{}/selfies_europe'.format(PATH)
SEPARATOR = ";"

face_cascade = cv2.CascadeClassifier('/home/tomas/opencv/data/haarcascades/haarcascade_frontalface_default.xml')

FACE_WIDTH = 92
FACE_HEIGHT = 112

def database_faces():
	BASE_PATH = '{}/faces'.format(PATH)

	label = 0
	faces = []
	labels = []

	for dir_name, dir_names, file_names in os.walk(BASE_PATH):
		for subdir_name in dir_names:
			subject_path = os.path.join(dir_name, subdir_name)

			for file_name in os.listdir(subject_path):
				faces.append(cv2.cvtColor(cv2.imread("{}/{}".format(subject_path, file_name)), cv2.COLOR_BGR2GRAY))
				labels.append(label)

			label = label + 1

	return faces, labels

def find_faces(image_path):
	faces_found = []

	gray = cv2.cvtColor(cv2.imread(image_path), cv2.COLOR_BGR2GRAY)

	_, width = gray.shape[:2]
	scale_factor = 600.0 / width
	gray = cv2.resize(gray, None, fx = scale_factor, fy = scale_factor, interpolation = cv2.INTER_CUBIC)

	faces = face_cascade.detectMultiScale(gray, 1.3, 5)
	for _, (x, y, w, h) in enumerate(faces):
		face = gray[y: y + h, x: x + w]
		_, width = face.shape[:2]
		scale_factor = float(FACE_WIDTH) / width
		faces_found.append(cv2.resize(face, None, fx = scale_factor, fy = scale_factor, interpolation = cv2.INTER_CUBIC))

	return faces_found

def find_my_faces():
	BASE_PATH = '{}/my_faces'.format(PATH)

	my_faces = []
	for dir_name, dir_names, file_names in os.walk(BASE_PATH):
		images_path = [os.path.join(dir_name, f) for f in file_names if f.endswith('.jpg')]
		for image_path in images_path:
			faces = find_faces(image_path)

			if len(faces) == 1:
				my_faces.append(faces[0])

	return my_faces

def train(faces, labels):
	recognizer = cv2.face.createLBPHFaceRecognizer()

	recognizer.train(faces, np.array(labels))

	return recognizer

def write_csv(r):
	with open('faces_01.csv', 'wb') as csvfile:
		spamwriter = csv.writer(csvfile)
		for f in r:
				spamwriter.writerow(f)

def doIt(label, recognizer):
	image_paths = [os.path.join(TARGET_PATH, f) for f in os.listdir(TARGET_PATH)]

	r = []
	for image_path in image_paths:
		faces_for_prediction = find_faces(image_path)

		img = cv2.imread(image_path)
		gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
		_, width = gray.shape[:2]
		scale_factor = 600.0 / width
		gray = cv2.resize(gray, None, fx = scale_factor, fy = scale_factor, interpolation = cv2.INTER_CUBIC)

		faces = face_cascade.detectMultiScale(gray, 1.3, 5)
		print len(faces_for_prediction), len(faces)
		for i, (x,y,w,h) in enumerate(faces):
			if i >= len(faces_for_prediction):
				continue

			l, conf = recognizer.predict(faces_for_prediction[i])
			print l, conf
			if label == l:
				print '{},{},{},{},{}'.format(image_path, x, y, w, h)
				r.append([image_path, x, y, w, h])

				#cv2.rectangle(gray,(x,y),(x+w,y+h),(255,0,0),2)
				#cv2.imshow('img',gray)
				#cv2.waitKey(0)

	write_csv(r)

if __name__ == "__main__":
	faces, labels = database_faces()

	my_label = labels[len(labels) - 1] + 1
	my_faces = find_my_faces()

	for my_face in my_faces:
		faces.append(my_face)
		labels.append(my_label)

	recognizer = train(faces, labels)

	doIt(my_label, recognizer)

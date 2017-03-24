import numpy as np
import cv2
import os
import csv

PATH = './src/main/resources'
TARGET_PATH = '{}/selfies_europe'.format(PATH)
face_cascade = cv2.CascadeClassifier('/home/tomas/opencv/data/haarcascades/haarcascade_frontalface_default.xml')

def save_faces(r):
	for i in r:
		img = cv2.imread('{}/{:02}.jpg'.format(PATH, i))
		gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

		faces = face_cascade.detectMultiScale(gray, 1.3, 5)
		for faceIndex, (x,y,w,h) in enumerate(faces):
			face = img[y: y + h, x: x + w]
			#cv2.imshow('bla', face)
			#cv2.waitKey(0)
			cv2.imwrite('{}/face_{:02}_{:02}.jpg'.format(PATH, i, faceIndex + 1), face)

def get_images_and_labels():
	images = []
	labels = []
	for i in xrange(1, 3):
		image_paths = [os.path.join(PATH, f) for f in os.listdir(PATH) if f.endswith('_{:02}.jpg'.format(i))]
		for j, image_path in enumerate(image_paths):
			if j + 1 == len(image_paths):
				break
			img = cv2.imread(image_path)
			gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

			images.append(gray)
			labels.append(i)

	return images, labels

def train():
	recognizer = cv2.face.createLBPHFaceRecognizer()
	images, labels = get_images_and_labels()
	recognizer.train(images, np.array(labels))

	return recognizer

def write_csv(r):
	with open('faces_01.csv', 'wb') as csvfile:
		spamwriter = csv.writer(csvfile)
		for f in r:
	    		spamwriter.writerow(f)

def doIt(label):
	recognizer = train()
	image_paths = [os.path.join(TARGET_PATH, f) for f in os.listdir(TARGET_PATH)]
	r = []

	for image_path in image_paths:
		img = cv2.imread(image_path)
		gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

		faces = face_cascade.detectMultiScale(gray, 1.3, 5)
		for faceIndex, (x,y,w,h) in enumerate(faces):
			l, conf = recognizer.predict(gray[y: y + h, x: x + w])
			if label == l:
				print '{},{},{},{},{}'.format(image_path, x, y, w, h)
				r.append([image_path, x, y, w, h])
				#cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
				#cv2.imshow('img',img)
				#cv2.waitKey(0)

	write_csv(r)
#save_faces(xrange(1, 22), './src/main/resources')
doIt(1)

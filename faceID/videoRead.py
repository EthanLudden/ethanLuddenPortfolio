import cv2
import os

#This xml file is used for assisting with framing
faceCascade = cv2.CascadeClassifier(os.path.join("XMLFilesUsed", "haarcascade_frontalface_default.xml"))

#This function processes the rectangle for the webcam frame
def faceFrame(image):
	grey = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	faces = faceCascade.detectMultiScale(grey, 1.3, 5)
	
	#THe next procuedure creates a blue square for return
	if len(faces) == 0:
		return image

	#And now to draw the rectangle
	for (x, y, w, h) in faces:
		cv2.rectangle(image, (x, y), (x+w, y+h), (0xFF, 0x00, 0x00), 2)
	return image


#This function crops an image to the size of the blue square
def crop(image):
	grey = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	faces = faceCascade.detectMultiScale(grey, 1.3, 5)
	
	#And now to draw the rectangle
	for (x, y, w, h) in faces:
		cv2.rectangle(image, (x, y), (x+w, y+h), (0xFF, 0x00, 0x00), 2)
		image = image[y:y+h, x:x+w]
		
	return image

#This function compares the captured face to the other faces.
def compare(frameToCompare):
	#Before comparing, 
	while True:
		x = 1

videoCapture = cv2.VideoCapture(0)
counter = 0 #This counter is to aid with formatting.

#This loop processes the webcam before capture, framing the face
while True:
	ret, frame = videoCapture.read()
	
	if not videoCapture.isOpened():
		sys.exit("Cannot open the video source")
	
	#A helper is called to frame the face
	frame = faceFrame(frame)
	
	cv2.imshow("Video Frame", frame)
	
	if (cv2.waitKey(1) == ord('q')):
		break
	elif (cv2.waitKey(1) % 256 == 32):
		#The image is given a name
		imageName  = "captureToCompare.png"
		#Now the image can be written
		cv2.imwrite(imageName, frame)
		
		cv2.imshow("Video Frame", frame)
		compare(frame)
		break
		
	counter += 1
		
	
videoCapture.release()
cv2.destroyAllWindows()
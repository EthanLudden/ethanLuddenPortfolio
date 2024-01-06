#Author: Ethan Ludden

#The imports used are shown below.
import cv2
import os
import face_recognition as fr
import time #5 second delays are used in display purposes.

#This xml file is used for assisting with framing
faceCascade = cv2.CascadeClassifier(os.path.join("XMLFilesUsed", "haarcascade_frontalface_default.xml"))

#This function processes the rectangle for the webcam frame
def faceFrame(image, text, colour):
	#This step processes the frame.
	grey = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	faces = faceCascade.detectMultiScale(grey, 1.3, 5)
	
	#The next procuedure creates a blue square for return
	if len(faces) == 0:
		return image

	#And now to draw the rectangle
	for (x, y, w, h) in faces:
		cv2.rectangle(image, (x, y), (x+w, y+h), (0xFF, 0x00, 0x00), 2)
		cv2.putText(image, text, (10, 500), cv2.FONT_HERSHEY_SIMPLEX, 2, colour, 3)
	return image

#This function compares the captured face to the other faces. And returns a match or nor.
def compare(frameToCompare, imageFiles):
	#Before comparing, the captured image needs to be encoded.
	imageCaptured = fr.load_image_file(frameToCompare)
	capturedEncoding = fr.face_encodings(imageCaptured)[0]
	for i in range(len(imageFiles)):
		#First comes loading and 
		file = imageFiles[i]
		imageToLoad = fr.load_image_file(file)
		imageEncoding = fr.face_encodings(imageToLoad)[0]
	
		#Each face is compared against the screen capture
		results = fr.compare_faces([capturedEncoding], imageEncoding)
		if results[0]:
			return True
	
	#This default return means no image matched.
	return False

#A video capture is declared
videoCapture = cv2.VideoCapture(0)
counter = 0 #This counter is to aid with formatting.
text = "Press the Enter Key bar to verify" #This string determines what to display on the screen before a capture.
colour = (0, 0, 0) #As will the display colour

#The image to be compared is given a name before the screen capture
imageName  = "captureToCompare.png"

#The first step is to set up an array of all image paths.
imageFiles = []
for picture in os.listdir("faces"):
	imageFile = os.path.join("faces", f"{picture}")
	imageFiles.append(imageFile)

#This loop processes the webcam before capture, framing the face
while True:
	ret, frame = videoCapture.read()
	
	if not videoCapture.isOpened():
		sys.exit("Cannot open the video source")
	
	#A helper is called to frame the face
	frame = faceFrame(frame, text, colour)
	
	cv2.imshow("Video Frame", frame)
	
	if (cv2.waitKey(1) == ord('q')):
		break
	elif (cv2.waitKey(1) == ord('\n')) and text == "Press the Enter Key to verify":
		#Now the image can be written
		frameTwo = faceFrame(frame, "", (0x00, 0x00, 0x00))
		cv2.imwrite(imageName, frameTwo)
		cv2.imshow("Video Frame Capture", frameTwo)
		#Camera display changes when a capture is taken
		text = "Loading"
		colour = (0xFF, 0x00, 0x00)
	#When entering the loading phase, this determines if something is approved or denied.
	elif text == "Loading":
		time.sleep(10)
		matchResult = compare(imageName, imageFiles)
		if (matchResult):
			text = "Approved"
			colour = (0x00, 0xFF, 0x00)
		else:
			text = "Denied"
			colour = (0x00, 0x00, 0xFF)
	#The user also gets to see their verification for a minute
	elif text == "Approved" or text == "Denied":
		time.sleep(60)
		break
		
	counter += 1
	
#The captured picture gets discarded after verification
os.remove("captureToCompare.png")

videoCapture.release()
cv2.destroyAllWindows()

#The following imports are shown below
import face_recognition as fr
import os, sys
import cv2
import numpy as np
import math
import time


def face_confidence(face_distance, face_match_threshold=0.6):
	range = 1.0 - face_match_threshold
	linear_val = (1.0 - face_distance) / (range * 2.0)
	
	if (face_distance > face_match_threshold):
		return str(round(linear_val * 100, 2)) + '%'
	else:
		value = (linear_val + ((1.0 - linear_val) * math.pow((linear_val - 0.5) * 2, 0.2))) * 100
		return str(round(value, 2)) + '%'
		
#This is the face recognition class
class FaceRecognition:
	face_positions = []
	face_encodings = []
	known_face_encodings = []
	face_names = []
	known_face_names = []
	confidences = []
	process_current_frame = True
	
	#This is the constructor
	def __init__(self):
		self.encode_faces()
		
	#This method encodes recognized faces
	def encode_faces(self):
		for image in os.listdir("faces"):
			imageName = f"{image}"
			imagePath = os.path.join("faces", imageName)
			face_image = fr.load_image_file(imagePath)
			face_encoding = fr.face_encodings(face_image)[0]
			
			self.known_face_encodings.append(face_encoding)
			self.known_face_names.append(image)
			
		print(self.known_face_names)
		
						
						
		
	def run_recognition(self):
		video_capture = cv2.VideoCapture(0)
		
		t = 0;
		
		if not video_capture.isOpened():
			sys.exit("Cannot open the video source")
		
		snapShot = False
		
		
		while True:
			t += 1
			ret, frame = video_capture.read()
			
			if self.process_current_frame:
				small_frame = cv2.resize(frame, (0, 0), fx=0.25, fy=0.25)
				
				
				#The frame is coverted to RGB
				rgb_small_frame = np.ascontiguousarray(small_frame[:, :, ::-1])
				
				
							
				#The next step is to find all the faces in the current frame.
				self.face_positions = fr.face_locations(rgb_small_frame)
				self.face_encodings = fr.face_encodings(rgb_small_frame, self.face_positions)
								
				self.face_names = []
				for face_encoding in self.face_encodings:
					matches = fr.compare_faces(self.known_face_encodings, face_encoding)
					name = "unknown"
					confidence = "unknown"
				
					face_distances = fr.face_distance(self.known_face_encodings, face_encoding)
					
					best_match_index = np.argmin(face_distances)
					
					if matches[best_match_index]:
						name = self.known_face_names[best_match_index]
						confidence = face_confidence(face_distances[best_match_index])
						
					#All matches and corresponding confidences are stored.
					self.face_names.append(f"{name} ({confidence})")
					
					if (not confidence == "unknown"):
						#Another variable interprets the confidence as a float.
						confidenceAsFloat = confidence.replace("%", "")
						confidenceAsFloat = float(confidenceAsFloat)
						self.confidences.append(confidenceAsFloat)
						color = (0x00, 0xFF, 0x00)
					else:
						self.confidences.append(confidence)
						color = (0x00, 0x00, 0xFF)
					
			self.process_current_frame = not self.process_current_frame
			
			
			#Now it's time to display any annotations
			for (top, right, bottom, left), name in zip(self.face_positions, self.face_names):
				top *= 4 #x stands for the top
				left *= 4  #y stands for left
				right *= 4 #a stands for width or right
				bottom *= 4 #b stands for bottom or height
				perCent = name[len(name)-7:len(name) - 2:1]
				
				#Converting to a number is important
				perCent = perCent.replace("(", "")
				perCent = float(perCent)
				
				#Confidence has to be 90 per cent to be verified
				if ((not name.startswith("unknown")) and perCent >= 90):
					color = (0x00, 0xFF, 0x00)
				else:
					color = (0x00, 0x00, 0xFF)
				
				cv2.rectangle(frame, (left, top), (right, bottom), color, 2)
				cv2.rectangle(frame, (left, bottom-35), (right, bottom), color, -1)
				cv2.putText(frame, name, (left + 6, bottom - 6), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0xFF, 0xFF, 0xFF), 2)
				
			
			cv2.imshow("Face ID", frame)
			
			key = cv2.waitKey(1)
			
			if key == ord('q'):
				break
				
			
					
		
		video_capture.release()
		cv2.destroyAllWindows()	
				
				
if __name__ == '__main__':
	faceRec = FaceRecognition()
	faceRec.run_recognition()
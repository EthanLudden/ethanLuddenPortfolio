#The following imports for this mp3 player are as follows
import os, sys
import time
import pygame
import cv2
import shutil
#Some imports require prerequisites. Pygame is one of them
from os import environ
import pygame
#These imports come from libraries
from tkinter import *
from tkinter import filedialog
from tkinter import messagebox
from pydub.utils import mediainfo


#These global booleans run when the track is running
paused = True
running = False
#Also the track indicator
track = 0
#Due to bugs, this variable is needed for song selection.
SongBeingSelected = False
songSelected = ""
#The frequency can be adjusted 10 hz after a song is complete
freq = 44100

#The next section is when the next step is to draw the canvas
def canvas(songs):
	root = Tk()
	root.title("Music Player")
	#The next step is to do a bit of geometry First by getting the dimensions of the screen, then setting up the display.
	screenWidth = root.winfo_screenwidth()
	screenHeight = root.winfo_screenheight()
	#The dimensions are placed into a string since the root geometry argument only takes strings. Strings must also be shown as ints.
	dimenstions = str(int(screenWidth/2))  + "x" + str(int(screenHeight /2 + 15)) + "+0+0"
	root.geometry(dimenstions)
	
	album = []
	userAlbum = set()
	
	#The background will be black to make it harmless to some eyes.
	root.configure(background = "#000000")
	root.resizable(False, False)
	#The playlist is now made and packed
	Playlist = Listbox(root, bg = "White", fg = "Black", width = 100, height = 15)
	Playlist.pack()
	Playlist.place(x=0, y=screenHeight/4)
	#Then the mixer is made
	pygame.mixer.init(frequency=freq)
	
	#Now for the menu button
	menuBar = Menu(root)
	root.config(menu = menuBar)
	
	#This function is for the organized menu. It allows for adding more to the song set.
	def readFinder():
		root.directory = filedialog.askdirectory()
		for song in (os.listdir(root.directory)):
			try:
				if song.endswith(".mp3"):
					#Two paths are set up  for user input
					relPathSong = os.path.join(root.directory, song)
					destPathSong = os.path.join(".", "Music", song)
					shutil.copy(relPathSong, destPathSong)
					userAlbum.add(song)
					songs.add(song)

			except PermissionError:
				print("Song does not have permission to load.")
						
	
				
	#This inner function is for the add button or for inserting songs from the songlist into the album.
	def addSong():
		global SongBeingSelected
		#This activation prevents shortcut interferences.
		SongBeingSelected = True
		#An entry bar is being used to make the user enter the text. It also has an entry frame to it. It will be displayed in a new window.
		rootEntry = Tk()
		entryDimenstions = str(int(screenWidth/2))  + "x" + str(int(screenHeight/4)) + "+0+0"
		rootEntry.title("Select Song")
		rootEntry.geometry(entryDimenstions)
		rootEntry.resizable(False, False)
		entryFrame = Frame(rootEntry, bg = "White", width = screenWidth, height = int(screenHeight/8))
		entryFrame.pack()
		#The bar for making user input is set
		entryFrame.place(x = 0, y = 0)
				
		#This  inner function converts a song to a string based on the input in the box.
		def songToString():
			global songSelected
			text = entryBar.get() 
			if text == "":
				return
			else:
				songSelected = text
			#All additional frames are deleted after the button or clicker action has occured
			rootEntry.destroy()
			processSong(songSelected)
			
		#This clicker function works for turning a song into a string.
		def clickerAdding(event):
			key = event.keysym
			#Also, as a precaution, the clicker runs if the function is also running.
			if key == "Return" and SongBeingSelected:
				 songToString()
		
		#This function is for closing the selection button
		def on_closing():
			rootEntry.destroy()
			SongBeingSelected = False
			return
		
		#An enter button is also there for submitting
		enterButton = Button(entryFrame, text = "Enter", bg ="#0000FF", height = 1, width = 15, font = ("Helvetica", 10), command = songToString)
		#As well as a header
		enterHeader = Label(entryFrame, text = "Select Song", height = 1, width = 15, font = ("Helvetica", 10))
		#And the text input bar
		entryBar = Entry(entryFrame, bg = "White", fg = "red")
		
		
		#The components are inserted into the frame
		enterHeader.grid(row = 0, column = 0, padx=5, pady=7)
		enterButton.grid(row = 1, column = 1, padx=5, pady=7)
		entryBar.grid(row = 1, column = 0, padx=5, pady=7)
		
		rootEntry.bind("<Key>", clickerAdding)
		rootEntry.protocol("WM_DELETE_WINDOW", on_closing)
							
	#After recieving user input, this processor processes the song
	def processSong(songSelected)	:		
		global SongBeingSelected
		for song in songs:
			if song.startswith(songSelected):
				album.append(song)
				Playlist.insert("end", songSelected)
		print(album)
		SongBeingSelected = False
		
		return
			
	#And now to organize the menu
	organizedMenu = Menu(menuBar, tearoff = False)
	organizedMenu.add_command(label="Select Folder", command=readFinder)
	menuBar.add_cascade(label='Organize', menu = organizedMenu)
	
	#Now keyboard shortcuts may be used again.
	SongBeingSelected = False
	
	#This function plays a song based on the track.
	def playSong():
		global paused, running, track
		try:
			currentSong = album[track]
			Playlist.selection_clear(0, END)
			Playlist.selection_set(track)
			if paused and running:
				pygame.mixer.music.unpause()
				paused = False
			elif paused:
				pygame.mixer.music.load(os.path.join("Music", currentSong))
				pygame.mixer.music.play()
				paused = False
				running = True
			else:
				pygame.mixer.music.pause()
				paused = True
		except IndexError:
			print("No music in album or no more tracks")
			
	#This function moves onto the next song
	def nextSong():
		global paused, running, track
		#An exception may occur since there might be no more songs left
		try:
			track += 1
			paused = True
			running = False
			playSong()
		except:
			print("No More Songs")
			
	#This function moves onto the next song
	def previousSong():
		global paused, running, track
		#An exception may occur since there might be no more songs left
		try:
			track -= 1
			paused = True
			running = False
			playSong()
		except:
			print("No More Songs")
	
	#This function stops a song from playing
	def stopSong():
		#The reason these global values are reset is because they need to
		global paused
		global running
		
		pygame.mixer.music.stop()
		
		paused = True
		running = False
		
	#This funtion plays a song from the beginning regardless of whether it was stopped or not
	def replaySong():
		global paused
		paused = True
		global running
		running = False
		playSong() #The play function call also is used as a helper for replaying

	
	#This function closes the program
	def exit():
		for song in userAlbum:
			songToRemove = os.path.join("Music", song)
			os.remove(songToRemove)
		sys.exit("Finished")
		
	#This function does the closing.
	def on_closing():
		root.destroy()
		exit()
	
	#This function deals with shortcuts
	def clicker(event):
		global SongBeingSelected
		key = event.keysym
		#A keyboard cannot interfere with song selection. Except for an enter key.
		if SongBeingSelected and key == "Return":
			print(entryBar.get())
			songSelected()
		elif SongBeingSelected:
			print('Song Selection')
			return
		elif (key == "space"):
			playSong() #All music players must have a spacebar for play and pause
		elif (key == "Return"):
			stopSong() #Return will be the stop shortcut
		elif (key == "r" or key == "R"):
			replaySong() #The replay shortcut will be r
		elif (key == "j" or key == "J"):
			nextSong() #The f and j keys will be used for shifting songs
		elif (key == "f" or key == "F"):
			previousSong()
		elif (key == "q" or key == "Q"):
			exit()
		elif (key == "a" or key == "A"):
			addSong()
		elif (key == "semicolon" or key == "colon"):
			readFinder()
		else:
			print(key)
	
	#Up next is a button container
	#This frame is for the controls of the mp3 player. They will go below the frame for the track player.
	controlFrame = Frame(root, bg = "black", width = screenWidth, height = int(screenHeight/4))
	controlFrame.pack()
	controlFrame.place(x = 0, y = int(screenHeight * 3/8))
		
	#The next step is to make the buttons. There are no symbols, instead it's all written
	addButton = Button(controlFrame, text = "Add Song", bg = "#FFFFFF", height = 1, width = 15, command=addSong)
	#This button serves both play and pause because of a spacebar shortcut.
	playButton = Button(controlFrame, text = "Play/Pause", bg = "#FFFFFF", height = 1, width = 15, command=playSong)
	nextButton = Button(controlFrame, text = "Next", bg = "#FFFFFF", height = 1, width = 15, command=nextSong)
	prevButton = Button(controlFrame, text = "Previous", bg = "#FFFFFF", height = 1, width = 15, command=previousSong)
	stopButton = Button(controlFrame, text = "Stop", bg = "#FFFFFF", height = 1, width = 15, command=stopSong)
	replayButton = Button(controlFrame, text = "Replay", bg = "#FFFFFF", height = 1, width = 15, command=replaySong)
	exitButton = Button(controlFrame, text = "Exit", bg = "#FFFFFF", height = 1, width = 15, command=exit)
	
	#Now to add the shortcuts to some of the buttons. A dummy button, not in the frame is used
	root.bind("<Key>", clicker)
	
	#Now the buttons are put into position
	addButton.grid(row=0, column=0, padx=5, pady=7)
	#This configuration has play/pause and previous and next buttons surrounding it.
	playButton.grid(row=0, column=2, padx=5, pady=7)
	nextButton.grid(row=0, column=3, padx=5, pady=7)
	prevButton.grid(row=0, column=1, padx=5, pady=7)
	#The buttons for ending or restarting a track go in the middle one row down
	stopButton.grid(row=1, column=2, padx=7, pady=10)
	replayButton.grid(row=1, column=1, padx=7, pady=10)
	#One of the corners also gets an exit button
	exitButton.grid(row=1, column=0, padx=7, pady=10)
	
	#Finally the window for closing the whole program.
	root.protocol("WM_DELETE_WINDOW", on_closing)
	
	
	 #All music players have a space bar shortcut for play and pause
	root.mainloop()

#This construction begins with setting up a set of mp3 files
def main():
	songs = set()
	try:
		musicDirectory = "Music"
		for song in os.listdir(musicDirectory):
			#Songs will be appended with a distinct order, HOWEVER the user can choose which song they want to listen to.
			if song.endswith(".mp3"):
				songs.add(song)
		print(songs)
		canvas(songs)
					
	#This exception is accessed if there is no music directory
	except FileNotFoundError:
		sys.exit("You need a \"Music\" directory inside the mp3Player Directory")

main()

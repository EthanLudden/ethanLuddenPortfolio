#This is a webscraper for recording pokemon stats in a spreadsheet.
#Author Ethan Ludden

#These are the imports that are being used.
import csv
import requests
import os

from bs4 import BeautifulSoup

#This is the CSV File
filename = "pokemonData.csv"

#By taking the website, webscraping is done by beautifulsoup
website = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_base_stats_(Generation_IX)"
webPage = requests.get(website)
print ("Webpage gotten \n")
soup = BeautifulSoup(webPage.content, "xml")
print ("XML set")
displayText = soup.get_text() #This will be the text to display

#The text from all websites is placed in a temporary file that will be rewritten.
bulbapediaFetched = open("pokemonDataFetched.txt", "w")
bulbapediaFetched.writelines(displayText)
bulbapediaFetched.close()

#Once this file is done, it's closed to prevent leakage.
bulbapediaFetched = open("pokemonDataFetched.txt", "r")

#The header is now discarded
for i in range(352):
	bulbapediaFetched.readline()


#And now the data is written to a new file. To be adjusted for later. 
data = open("pokemonData.txt", "w")
for i in range(27370): #Each line is read one at a time. All gaps are bypassed.
	text = bulbapediaFetched.readline()
	if (text != "\n"):
		data.write(text)

#The footer is also discarded as the two files are closed.
data.close()
bulbapediaFetched.close()
os.remove("pokemonDataFetched.txt") #The temporary file is removed.

#The last step is to assemble the csv file.
data = open("pokemonData.txt", "r")
#Now to set up a database directory
path = os.path.join("..", "Databases", "")
if (os.path.exists(path) == False):
	os.mkdir(path)
	
#Now to open the reading file
dataFile = open((path + filename), "w")

#The do step is the headers of a do while algorithm.
num = data.readline().replace("\n", ",")
name = data.readline().replace("\n", ",")
hp = data.readline().replace("\n", ",")
attack = data.readline().replace("\n", ",")
defense = data.readline().replace("\n", ",")
specialAttack = data.readline().replace("\n", ",")
specialDefense = data.readline().replace("\n", ",")
speed = data.readline().replace("\n", ",")
total = data.readline().replace("\n", ",")
average = data.readline().replace("\n", ",")
stats = [num, name, hp, attack, defense, specialAttack, specialDefense, speed, total, average]
dataFile.writelines(stats);

#The loop repeats the process for the rest of the functions.
while (name != ""):
	#Each attribute gets a newline replaced by a comma. And is appended to the following statlist
	num = data.readline().replace("\n", ",")
	name = data.readline().replace("\n", ",")
	name = name.replace(" ", "_") #Underscores are important for pokemon names
	print(name)
	hp = data.readline().replace("\n", ",")
	attack = data.readline().replace("\n", ",")
	defense = data.readline().replace("\n", ",")
	specialAttack = data.readline().replace("\n", ",")
	specialDefense = data.readline().replace("\n", ",")
	speed = data.readline().replace("\n", ",")
	total = data.readline().replace("\n", ",")
	average = data.readline().replace("\n", ",")
	stats = [num, name, hp, attack, defense, specialAttack, specialDefense, speed, total, average, "\n"]
	dataFile.writelines(stats);

data.close()
dataFile.close()
os.remove("pokemonData.txt") #The temporary file is removed.

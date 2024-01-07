#Author: Ethan Ludden
#This is a webscraper Usually for airlines but can be used for any csv file.

#These are the imports that are being used.
import csv
import requests
import os

from bs4 import BeautifulSoup

#This is the function that does the scraping. It will be used for any webpage argument
def scrape(website, csvFileName, companyName):
	webPage = requests.get(website)
	soup = BeautifulSoup(webPage.content, "xml")
	displayText = soup.get_text()
	#Now time to make the filename. The company name helps ensure organization
	filename = companyName + ".txt"
	#The next step is the path. It is a relative path from the previous directory.
	path = os.path.join("..", (csvFileName + "TextFiles"), "")
	#Now comes the part where the path exsists or needs to be made.
	if (os.path.exists(path) == False):
		os.mkdir(path)
	#Now time to attach the file to the path
	filename = path + filename
	#It's now time to open the file and put the display text. Here, the company name is used
	webfile = open (filename, "w")
	#Writing the company name as a header identifies where the information came from.
	webfile.writelines(companyName)
	#Along with a spacer
	webfile.write("\n")
	#Now the scraped text is written below the header.
	webfile.write(displayText)
	#As always, the file is closed when done to prevent leakage.
	webfile.close()

#This function takes a csv file and reads it one by one
def scrapeWebsites(csvFileName):
	file = open(csvFileName + ".csv", "r")
	#A webpage will be written one line at a time.
	for line in file.readlines(): #When inserting the arguments are separated by comma, but from readlines
		line = line.split("\t") #Therefore, splitting is done manually. 
		#The first column contains the company name used for making the text file name.
		company = line[0];
		#The second column contains the website used in scraping
		website = line[1];
		#All other columns are for additional data.
		#If there is no additional data, the newline is removed from the website.
		website.replace("\n", "")
		#Scraping can now begin
		scrape(website, csvFileName, company)
	file.close()

def airline():
	airline = "airlineWebsites"
	scrapeWebsites(airline)
	
airline()
This is implemented in java and needs java compiler version greater than or equal to 8.
All the java files have been created in the package com.utd.aos.project.two

Below are the steps to execute the project:
1. Login to a dcXX.utdallas.edu machine to run the program.
    ssh sxk190132@dc01.utdallas.edu   
2. Copy the java files downloaded from elearning to a folder, say MutualEx.
3. Copy the project from local system to the remote machine. It contains java files, a readme file, launcher script, cleanup script and config.txt file which the program expects to get the configuration of the system.
	{dc01:~} mkdir MutualEx
    ➜  ~ scp -r MutualEx/src/* sxk190132@dc01.utdallas.edu:~/MutualEx/src
4. Compile the java files
    	{dc01:~/MutualEx} mkdir classes
   	{dc01:~/MutualEx}  javac -d classes src/com/utd/aos/project/aos/*.java 
5. Create a config.txt file for launcher.sh to parse and start nodes. 
	{dc01:~/MutualEx} cd ..
	{dc01:~} mkdir launch
	{dc01:~} vi launch/config.txt
	Below is the format of the config file used by launcher.sh
		3 # number of nodes
		dc01
		dc02
		dc03
	This file should list all the dcXX machines being used for running the program which are present in the application config.txt file.
	The config.txt file used by the application is found in /MutualEx folder along with java files. 
	NOTE: There are 2 config.txt files, one for running the program and another for launcher.sh.
6. Copy the launcher.sh and cleanup.sh files to home directory and run the nodes using launcher.sh.
	{dc01:~} ./launcher.sh
7. Upon completion, run cleanup.sh
	{dc01:~} ./cleanup.sh
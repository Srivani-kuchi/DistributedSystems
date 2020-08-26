netid=sxk190132

# Root directory of your project
PROJDIR=/home/013/s/sx/sxk190132/MutualEx

# Directory where the config file is located on your local system
CONFIGLOCAL=$HOME/launch/config.txt


# Directory your java classes are in
BINDIR=$PROJDIR/classes

# Your main project class
PROG=com/utd/aos/project/two/Node

n=0

cat $CONFIGLOCAL | sed -e "s/#.//" | sed -e "/^\s$/d" |
(
    read i
        while [[ $n -lt $i ]]
	    do
	            read line
		            host=$( echo $line | awk '{ print $1 }' )
			            ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no $netid@$host "cd $PROJDIR && java -cp $BINDIR $PROG" &  
				            n=$(( n + 1 ))
					        done
						)

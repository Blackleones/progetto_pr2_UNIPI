CC= javac
CFLAG= -sourcepath src -d .

build:
	make module
	make main
module:
	$(CC) $(CFLAG) src/**/*.java

main:
	$(CC) $(CFLAG) src/*.java

run: 
	java Main

makeandrun:
	make build
	make run
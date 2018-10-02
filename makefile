
compile:bin
	javac -d bin -cp biuoop-1.4.jar src/*/*.java
run:jar
	java -jar space-invaders.jar
jar: 
	jar cfm space-invaders.jar manifest.mf -C bin . -C resources .
bin:
	mkdir bin

# --== CS400 File Header Information ==--
# Name: Allistair Mascarenhas
# Email: anmascarenha@wisc.edu
# Team: DC
# Role: Back End Developer 2
# TA: Yelun Bao
# Lecturer: Gary Dahl
# Notes to Grader: None

run: compile javafx-sdk FrontEnd BackEnd
	java --module-path javafx-sdk/lib/ --add-modules javafx.web,javafx.graphics StoreFrontEnd

compile: FrontEnd BackEnd DataWranglers TestSuites

test: TestSuites
	java -jar junit5.jar -cp . --scan-classpath -n BackendTest
	java -jar junit5.jar -cp . --scan-classpath -n DataWranglerTest

clean:
	rm *.class

junit5.jar:
	wget http://pages.cs.wisc.edu/~cs400/junit5.jar

# FrontEnd
FrontEnd: javafx-sdk StoreFrontEnd.java
	javac --module-path javafx-sdk/lib/ --add-modules javafx.web,javafx.graphics StoreFrontEnd.java

# Backend
BackEnd: StoreBackEnd.class HashTableMap.class KeyValuePair.class MapADT.class

StoreBackEnd.class:
	javac StoreBackEnd.java
	
HashTableMap.class:
	javac HashTableMap.java

KeyValuePair.class:
	javac KeyValuePair.java

MapADT.class:
	javac MapADT.java


# DataWrangelers
DataWranglers: DataLoader.class Grocery.class

DataLoader.class:
	javac DataLoader.java

Grocery.class:
	javac Grocery.java

# TestEngineers
TestSuites: BackendTest.class DataWranglerTest.class

TestBackEnd: BackendTest.class
	java -jar junit5.jar -cp . --scan-classpath -n BackendTest

TestDataWrangler: DataWranglerTest.class
	java -jar junit5.jar -cp . --scan-classpath -n DataWranglerTest

DataWranglerTest.class:
	javac -cp .:junit5.jar DataWranglerTest.java

BackendTest.class:
	javac -cp .:junit5.jar BackendTest.java

#GenCom - Group selection application for Generation of Committees

GenCom is a tool for selecting groups (i.e. committees) based on some of their Social Network features.

GenCom is for experimental use only and is currently under development. **Use with care**.


A preliminary graphic user interface is available, but it is still in an early development stage. I suggest to use it as an API. 

If you feel this application was in some kind useful, please cite our paper:

_Eduardo Zamudio, Luis S. Berdún, Analía A. Amandi, Social networks and genetic algorithms to choose committees with independent members, Expert Systems with Applications, Volume 43, January 2016, Pages 261-270, ISSN 0957-4174, http://dx.doi.org/10.1016/j.eswa.2015.07.045.
(http://www.sciencedirect.com/science/article/pii/S0957417415005059)_


#Workflow
In order to run the application, please follow this steps:
1. Load the dataset
2. Compile
3. Run the GUI application

##On main window: 
1. Update the data by pressing "Update" button
2. Select a _component_ and press "Select" button
3. Set your AG configuration preferences
4. Press "Generate" button
and wait... 

#Data
A postgresql scheme is located in _/data_

Please, set your database connection parameters in _resources/hibernate.cfg.xml_

#Compile
```
 mvn package -DskipTests
```

#Run
Binaries are located in _target/GenCom-SNAPSHOT.jar_

You can run the application by:
```
java -jar target/GenCom-SNAPSHOT.jar
```

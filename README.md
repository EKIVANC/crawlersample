
**Creating the project:**

The project was created with maven-archetype-quickstart Archetype.


**Running the project:**
Please use the following commands **sequentially** to build and run the project:

- `mvn clean package`
- `cd target`
- `java -jar crawler-sample-1.0-SNAPSHOT.jar`

The entry point of the application is the main method of Solution.js class.

**Unit Tests & Spotbugs maven plugin:**
Unit test can be found under ``src/main/test`` directory. There are 6 unit tests in total. 
The tests could be enhanced further.  
Unit tests can be run with usual maven command:
- `mvn clean test`

PMD & Spotbugs plugins also integrated, please run the following command:
- `mvn clean pmd:pmd site`  
- `open target/site/project-reports.html`


**About the application:**
A concurrent application is developed by using Callable interfaces and java concurrent Executors api.

No dependency or 3rd party library used in application development. (Only JUnit for testing and Log4j for logging)

The abstract factory pattern was used for populating Parser libraries.
In summary we have Parser4Google and Parser4JScript classes both implements the HtmlParser interface.
HtmlParserFactory class was responsible for populating the parser instances according to Parser Type enumeration.

In pros side of using this abstract factory pattern is: 
Thanks to this pattern we can add further functionalities to HtmlParser and force the Parser implementation to implement it. 
In the cons side, adding a new Parser is not easy actually, i can do it better if I spend more time.
To make it better: 
_there are some dublications in parse method implementation,  I can  use ``Template Design pattern`` to remove those dublications.
The better approach did not implemented since already an incomplete solution was expected. 


About the last bonus question which is:
``-> implement deduplication algorithms for the same Javascript libraries with different names``

I think, We can easily do it by getting the content of each javascript library and comparing the content.
However it will dramatically increase the running time of the program because of additional HTTP Requests.

**If I spend more time:**
-> I can focus the defects reported in SpotBugs

-> I can implement the last bonus questions, by getting the content of JS files and comparing them.

-> Get rid of code repeating of Abstraction in HTMLParser implementation by applying template pattern.



  
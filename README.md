# INTROSDE-UI


## DESCRIPTION
The virtual life coach system has the aim of help people (N users are allowed to registrate) who want to achieved a better healthy life status. This is perform by providing:
* registration of health measures and activities
* history of all measures and activities
* feedback on measure addition
* motivational quotes
* set of goals to achieve
* food suggestion for calories bound
This features well be useful for beginner and expert user who wants to monitoring their own progress.


## ARCHITECTURE

As we can see in the Fig. 1 the virtual life coach project is composed into 3 main block:
* User Interface
* System Logic
* Data sources

![](https://github.com/specter9876/introsde-UI/blob/master/img/Schermata%202016-04-30%20alle%2015.19.22.png)

Into this black we fund 5 web service + UI run locally. Among these web services 4 are SOAP and 1 REST:

* DATABASE SERVICE-A SOAP web services that communicates directly with the Database (SQLite) and it is responsible for management of all data model and query. 
* ADAPTER SERVICES-A REST web service that communicate with different data sources: retrieves and expose information useful for services. In detail this layer interacts with two external API (Food data, http://api.edamam.com, Quote data, https://theysaidso.com/)
* STORAGE SERVICES-A SOAP web services that manages and handle all data request, in detail it talks with database web service and adapter web services 
* BUSINESS LOGIC SERVICES- The core of project responsible of decision making, controls and calculation  (in example goals control)
* PROCESS CENTRIC SERVICES-these services serve all requests coming directly from users (from application interface). These are the gateway to all other modules/services in an application context. This layer is doing nothing but redirecting a request to a proper underlying service or a set of services.


* USER INTERFACE-It is run locally provides service coming from process in a fashionable mode




## CODE

A minimalistic but complete Javafx user interface, consist into:
* a login page (StartingPageController.java), the classic login page with username and password.
* a sign up page (RegisterController.java), used to create a new user.
* a Doctor page (HomePageController.java), used to add and see measures.
* a Coach page (HomePageController.java), used to add and see activity
* a Dietologist page (HomePageController.java), used to ask for food just for name or for calories bound
* a Psycologist page (HomePageController.java), used to chose which goal follow and see the history of goal achieved or to do.
* an Account page (HomePageController.java), used just to logout.


## USAGE (Mac)

Download it and type into terminal:
* **/Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/bin/javafxpackager -makeall -appclass application.Main -name "UserInterface" -width 800 -height 600**

The dist folder will be created, in order to launch application double click on dist.jar or digit into terminal 

* **java -jar dist.jar**

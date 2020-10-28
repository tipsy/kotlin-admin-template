# kotlin-admin-template

## Screenshot

## Running the project

From the root directory (`kotlin-admin-template`), do:

```shell
./mvnw clean install
java -jar target/kotlin-admin-template-jar-with-dependencies.jar
```

The server should now be running on http://localhost:8080

## Project structure


```
kotlin-admin-template
├──src                         
│  ├───main
│  │  ├──kotlin
│  │  │  └──kat
│  │  │     ├──account     //crud for user accounts
│  │  │     ├──auth        //authentication and authorization
│  │  │     ├──example     //an example crud feature
│  │  │     ├──Config.kt   //application config
│  │  │     └──Main.kt     //server config, routes and main function
│  │  └──resources
│  │     ├──public         //static files (logos, illustrations)
│  │     └──vue            //vue files 
│  │        ├──components  //reusable vue components
│  │        ├──pages       //one-off vue components (pages with URLs)
│  │        └──layout.html //vue setup file
│  └───test                //different example tests 
└──pom.xml
```


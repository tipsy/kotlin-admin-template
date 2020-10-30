# Kotlin Admin Template

![Screenshot](gfx/kat.png?raw=true)

## What's included?

[Kotlin Admin Template](https://github.com/tipsy/kotlin-admin-template) is a starter project for a typical admin project. It has a user database with roles, authorization per endpoint, and a frontend for displaying data. The project includes the following libraries:

*  [Vuetify](https://vuetifyjs.com/en/) (Material Design Vue components)
*  [Vue](https://vuejs.org/) (JavaScript view layer library)
*  [Javalin](https://javalin.io) (Web server library)
*  [JDBI](https://jdbi.org/) (Database library (not an ORM))
*  [SQLite](https://www.sqlite.org/) (Embedded database)

## Running the project

From the root directory (`kotlin-admin-template`), do:

```shell
./mvnw clean install
java -jar target/kotlin-admin-template-jar-with-dependencies.jar
```

The server should now be running on http://localhost:8080

## Architecture

The project is packaged by feature rather than layer, for example `accounts` and `example` are packages in the project. Each feature has a `Controller` responsible for handling HTTP input and output, and a `Service` responsible for communicating with a data store (typically a database, but could also be an API).

The frontend is based on having one Vue app per URL, and routing is done server side. This allows you to re-use your auth logic for both backend and frontend routes. The frontend architecture is described in detail in [this tutorial](https://javalin.io/tutorials/simple-frontends-with-javalin-and-vue).

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
│  └───test                //integration tests
│      ├───api             //http-client tests towards api
│      └───view            //browser tests (selenium)
└──pom.xml
```

## Running in production?

The project is intended to get you up and running as fast as possible. Before putting this into production, you'll probably want to do a few things:

*   Setup your own session database ([tutorial](https://javalin.io/tutorials/jetty-session-handling))
*   Connect to a login provider, such as [auth0](https://auth0.com/)
*   Replace SQLite with [PostgreSQL](https://www.postgresql.org/) (or similar)
*   Setup a database migration tool (like [liquibase](https://liquibase.org))


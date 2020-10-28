<template id="home-page">
    <app-frame class="home-page">
        <h1>Kotlin Admin Template</h1>

        <h2 class="mt-5 mb-2">What's included?</h2>
        <p>
            <a href="https://github.com/tipsy/kotlin-admin-template">Kotlin Admin Template</a>
            is a starter project for a typical admin project. It has a user database with roles,
            authorization per endpoint, and a frontend for displaying data. The project includes
            the following libraries:
        </p>
        <ul>
            <li v-for="link in links">
                <a :href="link.href" target="_blank">{{ link.text }}</a> ({{ link.comment }})
            </li>
        </ul>

        <h2 class="mt-5 mb-2">Architecture</h2>
        <p>
            The project is packaged by feature rather than layer, for example
            <code>accounts</code> and <code>example</code> are packages in the project.
            Each feature has a <code>Controller</code> responsible for handling HTTP input and output,
            and a <code>Service</code> responsible for communicating with a data store
            (typically a database, but could also be an API).
        </p>
        <p>
            The frontend is based on having one Vue app per URL, and routing is done server side.
            This allows you to re-use your auth logic for both backend and frontend routes.
            The frontend architecture is described in detail in
            <a href="https://javalin.io/tutorials/simple-frontends-with-javalin-and-vue" target="_blank">this tutorial</a>.
        </p>

        <h2 class="mt-5 mb-2">Running in production?</h2>
        <p>
            The project is intended to get you up and running as fast as possible.
            Before putting this into production, you'll probably want to do a few things:
        </p>
        <ul>
            <li>Setup your own session database (<a href="https://javalin.io/tutorials/jetty-session-handling" target="_blank">tutorial</a>)</li>
            <li>Connect to a login provider, such as <a href="https://auth0.com/" target="_blank">auth0</a></li>
            <li>Replace SQLite with <a href="https://www.postgresql.org/" target="_blank">PostgreSQL</a> (or similar)</li>
        </ul>
    </app-frame>
</template>
<script>
    Vue.component("home-page", {
        template: "#home-page",
        data: () => ({
            links: [
                {text: "Vuetify", href: "https://vuetifyjs.com/en/", comment: "Material Design Vue components"},
                {text: "Vue", href: "https://vuejs.org/", comment: "JavaScript view layer library"},
                {text: "Javalin", href: "https://javalin.io", comment: "Web server library"},
                {text: "JDBI", href: "https://jdbi.org/", comment: "Database library (not an ORM)"},
                {text: "SQLite", href: "https://www.sqlite.org/", comment: "Embedded database"},
            ]
        }),
    });
</script>
<style>
    .home-page li {
        margin: 4px 0;
    }
</style>

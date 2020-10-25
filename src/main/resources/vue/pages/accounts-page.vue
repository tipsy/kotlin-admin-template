<template id="accounts-page">
    <app-frame>
        <h1 class="mb-3">Accounts</h1>
        <p>
            This page contains a bunch of examples.
            It's meant to serve as an example of how to setup controllers and services.
            If you're the owner of an example, you can delete it.
        </p>
    </app-frame>
</template>
<script>
    Vue.component("accounts-page", {
        template: "#accounts-page",
        data: () => ({
            accounts: []
        }),
    });
</script>

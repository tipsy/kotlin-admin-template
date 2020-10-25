<template id="unauthorized-page">
    <error-illustration-frame title="Unauthorized" illustration="unauthorized">
        There's a rumor that the admin credentials are<br>
        <code>admin@kat.kat</code>/<code>password</code>
    </error-illustration-frame>
</template>
<script>
    Vue.component("unauthorized-page", {template: "#unauthorized-page"});
</script>

<template id="error-illustration-frame">
    <app-frame>
        <div class="error-illustration-page">
            <h1>{{ title }}</h1>
            <div class="image-wrapper">
                <img :src="`/img/undraw_${illustration}.svg`" :alt="title">
            </div>
            <div class="text">
                <slot></slot>
            </div>
        </div>
    </app-frame>
</template>
<script>
    Vue.component("error-illustration-frame", {
        template: "#error-illustration-frame",
        props: ["title", "illustration"]
    });
</script>
<style>
    .error-illustration-page {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 100px 0;
    }

    .error-illustration-page h1 {
        font-size: 32px;
        font-weight: 500;
        color: rgba(0, 0, 0, 0.67);
    }

    .error-illustration-page .image-wrapper {
        height: 256px;
        margin: 32px 0;
    }

    .error-illustration-page .image-wrapper img {
        display: block;
        max-height: 100%;
    }

    .error-illustration-page .text {
        text-align: center;
        max-width: 400px;
    }
</style>

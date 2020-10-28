<template id="examples-page">
    <app-frame class="examples-page">
        <div class="d-flex justify-space-between align-center mb-3">
            <h1>Examples</h1>
            <v-btn small depressed @click="createExample()" color="green darken-2" dark rounded>
                <v-icon small>mdi-plus</v-icon> Add example
            </v-btn>
        </div>
        <p>
            This page contains a bunch of examples.
            It's meant to serve as an example of how to setup controllers and services.
            If you're the owner of an example, you can delete it.
        </p>
        <v-sheet v-for="example in examples" rounded="lg" class="example pa-5 mt-5" :key="example.id">
            {{ example.text }}
            <v-btn v-if="isOwner(example)" icon color="red lighten-1" @click="deleteExample(example.id)">
                <v-icon>mdi-delete</v-icon>
            </v-btn>
        </v-sheet>
        <h2 v-if="examples.length === 0">
            There are no examples here !
        </h2>
    </app-frame>
</template>
<script>
    Vue.component("examples-page", {
        template: "#examples-page",
        data: () => ({
            examples: [],
        }),
        methods: {
            createExample() {
                let text = prompt("Enter your example text")
                if (text) {
                    axios.post("/api/examples", text)
                        .then(() => this.loadExamples())
                        .catch(() => alert("Failed to create example"))
                }
            },
            deleteExample(id) {
                if (confirm("Are you sure you want to delete this example?")) {
                    axios.delete(`/api/examples/${id}`)
                        .then(() => this.loadExamples())
                        .catch(() => alert("Failed to delete example"))
                }
            },
            loadExamples() {
                axios.get("/api/examples")
                    .then(res => this.examples = res.data)
                    .catch(() => alert("Failed to load examples"))
            },
            isOwner(example) {
                return example.createdBy === this.$javalin.state.userInfo.id;
            }
        },
        created() {
            this.loadExamples();
        }
    });
</script>
<style>
    .examples-page .example {
        background: #f5f5f5;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
</style>

<template id="sign-in-page">
    <app-frame>
        <div class="signin-overlay pa-5">
            <v-card class="signin-card pa-5">
                <h1 class="mt-0 mb-5">Kotlin Admin Template</h1>
                <v-alert v-model="errorAlert" dismissible type="error" class="mb-6">{{ errorMessage }}</v-alert>
                <v-text-field
                    outlined
                    v-model="userId"
                    label="User ID"
                    append-icon="mdi-account">
                </v-text-field>
                <v-text-field
                    outlined
                    v-model="password"
                    label="Password"
                    type="password"
                    @keyup.enter="signInOrUp"
                    append-icon="mdi-lock">
                </v-text-field>
                <div v-if="isSignin">
                    <v-btn @click="signInOrUp" depressed block large color="primary">Sign in</v-btn>
                    <div class="mt-5">No account? <a @click="switchForm">Register</a></div>
                </div>
                <div v-if="!isSignin">
                    <v-btn @click="signInOrUp" depressed block large color="primary">Register</v-btn>
                    <div class="mt-5">Have an account? <a @click="switchForm">Sign in</a></div>
                </div>
            </v-card>
        </div>
    </app-frame>
</template>
<script>
    Vue.component("sign-in-page", {
        template: "#sign-in-page",
        data: () => ({
            errorAlert: false,
            errorMessage: "",
            isSignin: true,
            userId: "",
            password: "",
        }),
        methods: {
            signInOrUp() {
                let url = "/api/auth/" + (this.isSignin ? "sign-in" : "sign-up");
                this.errorAlert = false;
                axios.post(url, {userId: this.userId, password: this.password}).then(() => {
                    window.location = "/"
                }).catch(async error => {
                    await new Promise(resolve => setTimeout(resolve, 500)); // feels too fast otherwise
                    this.errorMessage = error.response.data.title || error.response.data["REQUEST_BODY"][0].message
                    this.errorAlert = true;
                });
            },
            switchForm: function () {
                this.errorAlert = false;
                this.isSignin = !this.isSignin;
            }
        },
    });
</script>
<style>
    .signin-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(125deg, #46a6c3, #008ab4);
    }

    .v-sheet.v-card:not(.v-sheet--outlined).signin-card {
        max-width: 380px;
        margin: 150px auto;
        box-shadow: 0 2px 20px rgba(0, 0, 0, .3);
    }

    .signin-card h1 {
        font-weight: 400;
        color: #444;
        font-size: 28px;
        text-align: center;
    }
</style>

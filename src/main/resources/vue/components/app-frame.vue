<template id="app-frame">
    <v-app>
        <v-app-bar app color="white" flat>
            <v-container class="py-0 fill-height">
                <img src="/logo.svg" class="navbar-logo" alt="KAT logo">
                <v-spacer></v-spacer>
                <v-menu v-if="$javalin.state.userInfo" bottom left offset-y>
                    <template #activator="scope">
                        <v-btn icon color="black" v-on="scope.on">
                            <v-icon>mdi-account-circle</v-icon>
                        </v-btn>
                    </template>
                    <v-list class="account-list">
                        <div class="signed-in-as pa-5">
                            Signed in as
                            <div class="current-user">{{ $javalin.state.userInfo.id }}</div>
                        </div>
                        <v-list-item @click="signOut">Sign out</v-list-item>
                    </v-list>
                </v-menu>
            </v-container>
        </v-app-bar>
        <v-main class="primary">
            <v-container>
                <v-row>
                    <v-col cols="2">
                        <v-sheet rounded="lg">
                            <v-list color="transparent">
                                <v-list-item link href="/" :ripple="false">Home</v-list-item>
                                <v-list-item link href="/examples" :ripple="false">Examples</v-list-item>
                                <v-list-item link href="/accounts" :ripple="false">Accounts</v-list-item>
                            </v-list>
                        </v-sheet>
                    </v-col>
                    <v-col>
                        <v-sheet rounded="lg" class="pa-5">
                            <slot></slot>
                        </v-sheet>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>
</template>
<script>
    Vue.component("app-frame", {
        template: "#app-frame",
        methods: {
            signOut() {
                axios.post("/api/auth/sign-out").then(() => {
                    location.href = "/sign-in";
                });
            }
        }
    });
</script>
<style>
    .navbar-logo {
        height: 16px;
        padding: 0 20px;
    }

    .account-list.v-list {
        min-width: 150px;
        padding: 0;
    }

    .account-list .signed-in-as {
        background: #f9f9fc;
    }

    .account-list .current-user {
        font-weight: 500;
        margin-top: 8px;
    }
</style>

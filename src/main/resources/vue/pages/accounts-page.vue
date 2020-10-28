<template id="accounts-page">
    <app-frame class="accounts-page">
        <h1 class="mb-3">Accounts</h1>
        <p>
            This page shows all the user-accounts in the app. An admin can promote/demote
            standard users to/from the admin role, and also delete all accounts.
        </p>
        <v-sheet v-for="account in accounts" rounded="lg" class="account-row pa-5 mt-5" :key="account.id">
            <div>
                {{ account.id }} <span v-if="isSelf(account.id)"><em>(this is you)</em></span>
            </div>
            <div class="d-flex align-center">
                <v-combobox
                    v-model="account.role"
                    label="Role"
                    :items="['ADMIN', 'USER']"
                    @change="v => updateAccount(account.id, v)"
                    dense
                    solo
                    flat
                    hide-details>
                </v-combobox>
                <v-btn icon color="red lighten-1" @click="deleteAccount(account.id)">
                    <v-icon>mdi-delete</v-icon>
                </v-btn>
            </div>
        </v-sheet>
    </app-frame>
</template>
<script>
    Vue.component("accounts-page", {
        template: "#accounts-page",
        data: () => ({
            accounts: [],
        }),
        methods: {
            deleteAccount(accountId) {
                if (confirm("Are you sure you want to delete " + accountId)) {
                    axios.delete(`/api/accounts/${accountId}`)
                        .then(() => this.loadAccounts())
                        .catch(() => alert("Failed to delete account"))
                }
            },
            updateAccount(accountId, newRole) {
                axios.patch(`/api/accounts/${accountId}`, {role: newRole})
                    .then(() => this.loadAccounts())
                    .catch(() => alert("Failed to update account"))
            },
            loadAccounts() {
                axios.get("/api/accounts")
                    .then(res => this.accounts = res.data.sort((a, b) => a.role.localeCompare(b.role)))
                    .catch(() => alert("Failed to load accounts"))
            },
            isSelf(accountId) {
                return accountId === this.$javalin.state.userInfo.id;
            }
        },
        created() {
            this.loadAccounts();
        }
    });
</script>
<style>
    .accounts-page .account-row {
        background: #f5f5f5;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .accounts-page .account-row .v-input {
        max-width: 128px;
        margin-right: 16px;
    }
</style>

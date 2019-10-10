var navVue = Vue.component("v-nav", {
    props: ["isSigned"],
    template: `
        <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
            <div class="container">
                <router-link class="navbar-brand" to="/">Auction | HARIBO</router-link>
                <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <router-link class="nav-link" to="/artworks">Artworks</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/auction">Auction</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/explorer/auctions">Explorer</router-link>
                        </li>
                        <li class="nav-item" v-if="sharedState.isSigned">
                            <router-link class="nav-link" to="/mypage/wallet_create" v-if="!sharedState.user.hasWallet">Mypage</router-link>
                            <router-link class="nav-link" to="/mypage/wallet_info" v-if="sharedState.user.hasWallet">Mypage</router-link>
                        </li>
                        <li class="nav-item" v-if="!sharedState.isSigned">
                            <router-link class="nav-link" to="/login">Login</router-link>
                        </li>
                        <li class="nav-item" v-if="!sharedState.isSigned">
                            <router-link class="nav-link" to="/register">Register</router-link>
                        </li>
                        <li class="nav-item" v-if="sharedState.isSigned">
                            <router-link class="nav-link" to="/logout">Logout</router-link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    `,
    data() {
        return {
            sharedState: store.state
        }
    }
})
var homeView = Vue.component("Home", {
  template: `
      <div>
          <v-nav></v-nav>
          <div id="main-overview" class="container">
              <div class="row">
                  <div class="col-md-12">
                  <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                  <div class="carousel-inner">
                    <div class="carousel-item active" style="padding-top:10%; padding-left:5%;">
                      <img class="d-block w-100" src="https://1.cms.s81c.com/sites/default/files/2019-02-15/hyperledger_logo_new.png" style="width:90%; height:10%;" alt="First slide">
                    </div>
                    <div class="carousel-item">
                         <div style="padding-top:5%; padding-left:18%;">
                              <img class="d-block w-100" src="https://ipfs.busy.org/ipfs/QmReac8NnfZVSDNhtWRwLiAU5Dx38ozYCqwnPgP44wEXJt" style="width:70%; height:10%;" alt="Second slide">
                         </div>
                    </div>
                      <div class="carousel-item">
                         <div style="padding-top:10%; padding-left:20%;">
                              <img class="d-block w-100" src="https://cdn.pixabay.com/photo/2018/06/30/23/10/blockchain-3508589_960_720.png" style="width:70%; height:10%;" alt="Second slide">
                         </div>
                    </div>
                    <div class="carousel-item">
                          <div class="d-block w-100">
                              <h1>DIGITAL CONTENTS<br>AUCTION</h1>
                              <h4>블록체인 기반 미술품 경매를 시작해보세요.</h4>
                              <router-link :to="{ name: 'register' }" class="btn btn-lg btn-primary">회원가입</router-link>
                              <router-link :to="{ name: 'login' }" class="btn btn-lg btn-primary">로그인</router-link>
                           </div>
                    </div>
                  </div>
                  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                  </a>
                  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                  </a>
                </div>          
                     
                  </div>
              </div>
          </div>
      </div>
  `
})
$('.carousel').carousel({
  interval: 1000
})
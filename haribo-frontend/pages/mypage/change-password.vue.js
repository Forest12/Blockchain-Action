/**
 * 화면 명 : 개인정보 수정
 */

var myChangePasswordView = Vue.component('MyChangePasswordView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="비밀번호 변경" description="비밀번호를 변경 할 수 있습니다."></v-breadcrumb>
            <div class="container">
                <v-mypage-nav></v-mypage-nav>
                <div class="row">
                    <div class="col-md-6 mx-auto mt-5">
                        <div class="card">
                            <div class="card-header">비밀번호 변경</div>
                            <div class="card-body">
                                <div class="form-group">
                                    <label id="oldPassword">이전 비밀번호</label>
                                    <input id="oldPassword" type="password" class="form-control" placeholder="이전 비밀번호" v-model="input.oldPassword">
                                </div>
                                <div class="form-group">
                                    <label id="newPassword">신규 비밀번호</label>
                                    <input id="newPassword" type="password" class="form-control" placeholder="신규 비밀번호" v-model="input.newPassword">
                                </div>
                                <div class="form-group">
                                    <label id="newPasswordConfirm">신규 비밀번호 확인</label>
                                    <input id="newPasswordConfirm" type="password" class="form-control" placeholder="신규 비밀번호 확인" v-model="input.newPasswordConfirm">
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <button class="btn btn-sm btn-primary" v-on:click="update">개인정보 수정</button>
                                    </div>
                                    <div class="col-md-6 text-right">
                                        <button class="btn btn-sm btn-outline-secondary" v-on:click="goBack">이전으로 돌아가기</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data(){
        return {
            user: {
                id: 0,
                email: "",
                name: "",
                password: ""
            },
            input: {
                oldPassword: "",
                newPassword: "",
                newPasswordConfirm: ""
            },
            sharedStates: store.state
        }
    },
    methods: {
        update: function(){
            // 비밀번호가 회원의 비밀번호와 일치하는지 비교한다.
            if(this.user.password !== this.input.oldPassword){
                // alert("입력하신 비밀번호가 일치하지 않습니다.");
                swal({
                    title: "Password Not Correct",
                    text: "입력하신 비밀번호가 일치하지 않습니다.",
                    icon: "warning",
                });
                document.getElementById("oldPassword").focus();
                return;
            }

            // 비밀번호를 공백으로 입력했는지 확인한다.
            if(this.input.newPassword === "") {
                // alert("신규 비밀번호를 입력해주세요.");
                swal({
                    title: "New Password",
                    text: "신규 비밀번호를 입력해주세요.",
                    icon: "warning",
                });
                document.getElementById("newPassword").focus();
                return;
            }

            // 신규비밀번호와 신규비밀번호 확인이 일치하지 않는 경우를 확인한다.
            if(this.input.newPassword !== this.input.newPasswordConfirm) {
                // alert("신규 비밀번호와 신규 비밀번호 확인이 일치하지 않습니다.");
                swal({
                    title: "New Password Not Correct",
                    text: "신규 비밀번호와 신규 비밀번호 확인이 일치하지 않습니다.",
                    icon: "warning",
                });
                document.getElementById("newPasswordConfirm").focus();
                return;
            }

            userService.update({
                "email": this.user.email,
                "username": this.user.name, 
                "password": this.input.newPassword // 신규 비밀번호
            }, function(data){
                // alert("비밀번호가 변경되었습니다.");
                swal({
                    title: "Updated New Password",
                    text: "비밀번호가 변경되었습니다.",
                    icon: "success",
                });
                this.$router.go(-1);
            });
        },
        goBack: function(){
            this.$router.go(-1);
        }
    },
    mounted: function(){
        var scope = this;

        userService.findById(this.sharedStates.user.id, function(data){
            scope.user.id = data["id"];
            scope.user.email = data["email"];
            scope.user.name = data["username"];
            scope.user.password = data["password"];
        });
    }
})
function startLogin() {
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
            FB.api('/me', function(response) {
                console.log("/me");
                console.log(response);
            });
        } else if (response.status === 'not_authorized') {
            alert("Aplicativo não autorizado!");
        } else {
            login();
        }
    });
}

function login() {
    FB.login(function(response) {
        if (response.authResponse) {
            console.log("AuthResponse");
            $.post("/social/facebook_register", {
                facebookId: response.authResponse.userID,
                facebookAccessToken: response.authResponse.accessToken,
                facebookUsername: "",
                customerId: gup("id")
            }, function(t) {
                console.log(t);
            });
            //console.log(response);
            startLogin();
        } else {
            alert("A requisição foi cancelada!");
        }
    }, {
    });
}
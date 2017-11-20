//
//  Rigel - 03/03/2017
//  ALTERACAO DE VERSAO DO APLICATIVO DEVE SER FEITA no face_prepare.js
//
var EbelleFacebook = function() {
	var self = this;
	this.getUserInfo = function() {
		var defered = Q.defer();
		FB.getLoginStatus(function(response) {
			if (response.status === 'connected') {
				FB.api('/me', function(response) {
					//console.log("/me");
					//console.log(response);
					defered.resolve(response);
				});
			} else if (response.status === 'not_authorized') {
				defered.reject('Não autorizado');
			} else {
				defered.reject('precisa de login');
			}
		});
		return defered.promise;
	};
	this.tryLogin = function() {
		var defered = Q.defer();
		FB.login(function(response) {
			if (response.authResponse) {
				defered.resolve(response.authResponse);
			} else {
				defered.reject('');
			}
		}, {
			scope: 'email'
		});
		return defered.promise;
	};

	this.getLoginWithUserInfo = function(){
		return self.tryLogin().then(function(authResponse){
			return self.getUserInfo().then(function(userInfor){
				return {authResponse : authResponse, userInfor: userInfor};
			});
		});
	};


	this.getUserByFacebook = function(){
		return self.getLoginWithUserInfo().then(function(faceInfo){
			return self.getUserByIdFacebook(faceInfo);
		});
	};

	this.getUserByIdFacebook = function(facebookInfo){
		var defered = Q.defer();
		$.ajax({ type: 'post' ,url : "/social/user_by_facebook", data: {
			facebookId: facebookInfo.authResponse.userID,
			facebookAccessToken: facebookInfo.authResponse.accessToken,
		}}).done(function(t) {
			defered.resolve(eval(t));
		}).fail(function(e){
			defered.reject(e);
		});
		return defered.promise;
	};
	this.registerIdFacebook = function(facebookInfo){
		var defered = Q.defer();
		$.ajax({ type: 'post' ,url : "/security/facebook_register_to_user", data: {
			facebookId: facebookInfo.authResponse.userID,
			facebookAccessToken: facebookInfo.authResponse.accessToken,
		}}).done(function(t) {
			defered.resolve(eval(t));
		}).fail(function(e){
			defered.reject(e);
		});
		return defered.promise;
	};	
};
var Q = require('q');
var jenkinsapi = require('jenkins-api');
var jenkins = jenkinsapi.init("http://admin:rika775072@ci.transitobh.com.br/");
var rp = require('request-promise');

var BuildInfo = function(name, port) {
	var self = this;
	this.waitToHttpSeriveIsRuning = function(){
		return waitSystemStart("http://transitobh.com.br:"+port);
	};
	var waitSystemStart = function(url, deferR, n){
		var times = n || 0;
		var defer = deferR || Q.defer();
		if(times >= 180){//3 minutes
			defer.reject("Give ou aftet : "+times+" times");
		}
		rp(url).then(function(r){
			defer.resolve(true);
		}).catch(function(){
			setTimeout(function(){
				waitSystemStart(url, defer, ++times);
			}, 1000);
		});
		return defer.promise;
	};
	this.jobName = function() {
		return 'run_e-belle-' + name;
	}
	this.getBuildInfo = function() {
		var defer = Q.defer();
		jenkins.last_build_info(this.jobName(), function(err, data) {
			if (err) {
				defer.reject(data);
			}
			defer.resolve(data);
		});
		return defer.promise;
	};
	var waitToStart = function(deferR, n) {
		var times = n || 0;
		var defer = deferR || Q.defer();
		if (n >= 10) {
			defer.reject("Give up after :" + n);
		}
		setTimeout(function() {
			self.isRuning().then(function(r) {
				if (r) {
					defer.resolve(r);
				} else {
					waitToStart(defer, ++times);
				}
			});
		}, 500);
		return defer.promise;
	};
	this.stop = function() {
		var defer = Q.defer();
		self.getBuildInfo().then(function(data) {
			try{
			jenkins.stop_build(self.jobName(), data.number, function(e, r) {
				defer.resolve(r);
			});
			}catch(e){
				console.log(e);
			}
		});
		return defer.promise;
	};
	this.start = function() {
		var defer = Q.defer();
		jenkins.build(this.jobName(), function(err, data) {
			waitToStart().then(function(){
				console.log('waitToHttpSeriveIsRuning');
				return self.waitToHttpSeriveIsRuning();
			}).then(function(a) {
				console.log('Runing');
				defer.resolve(a);
			}, function(e) {
				defer.resolve(e);
			});
		});
		return defer.promise;
	};
	this.isRuning = function() {
		return this.getBuildInfo().then(function(data) {
			return data.building;
		});
	};
}

module.exports = BuildInfo
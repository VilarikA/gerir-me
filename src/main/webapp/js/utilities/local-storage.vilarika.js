(function(){

	"use strict";

	angular
		.module("LocalStorageModule", [])
		.service("LocalStorage", [Service]);

	function Service()
	{
		this.set = function set(key, value)
		{
			value = JSON.stringify(value);
			localStorage.setItem(key, value);
		};

		this.get = function get(key)
		{
			var value = localStorage.getItem(key);
			return JSON.parse(value);
		};
	}
})();
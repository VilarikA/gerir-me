/**
 * Loading Overlay is a little component which creates a 
 * transparent div and a message.
 * 
 * It is useful for loading cases, where the user can see 
 * that there is something loading.
 */

(function(){

	"use strict";

	function LoadingOverlay()
	{
		var self = this;
		this.$loadingOverlay = null;
		this.$loadingText = null;

		(function initialize()
		{
			createElements();
			applyStyles();
		})();

		function createElements()
		{
			self.$loadingOverlay = $('<div class="loading-overlay"></div>');
			self.$loadingText = $('<span class="text"></span>');

			self.$loadingOverlay.appendTo("body");
			self.$loadingOverlay.append(self.$loadingText);
		}

		function applyStyles()
		{
			self.$loadingOverlay.css({
				"display": "none",
				"position": "fixed",
				"top": 0,
				"left": 0,
				"bottom": 0,
				"right": 0,
				"background-color": "rgba(0, 0, 0, 0.6)"
			});

			self.$loadingText.css({
				"color": "white",
				"display": "inline-block",
				"position": "fixed",
				"left": "50%",
				"top": "50%",
				"transform": "translate(-50%, -50%)"
			});
		}

		function show(message)
		{
			setText(message || "Carregando. Aguarde...");
			showOverlay();
		}

		function setText(text)
		{
			self.$loadingText.text(text);
		}

		function showOverlay()
		{
			self.$loadingOverlay.css("display", "block");
		}

		function hide()
		{
			hideOverlay();
		}

		function hideOverlay()
		{
			self.$loadingOverlay.css("display", "none");
		}

		function removeSuspensionPoints(text)
		{
			var lastThreeCharacters = text.substr(text.length - 3);
			var textWithoutLastThreeCharacters = text.substr(0, text.length - 3);
			if(lastThreeCharacters == "...")
				return textWithoutLastThreeCharacters;

			return text;
		}

		/**
		 * Public methods
		 */
		return {
			show: show,
			hide: hide
		};
	}

	$(document).ready(function(){
		window.Vilarika = window.Vilarika || {};
		window.Vilarika.Request = window.Vilarika.Request || {};
		window.Vilarika.Request.LoadingOverlay = window.Vilarika.Request.LoadingOverlay || new LoadingOverlay();
	});
})();
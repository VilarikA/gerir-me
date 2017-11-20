var I18nManager = {
	lang: 'ebelle',
	words: Languages,
	changeLang: function(lang) {
		I18nManager.lang = lang;
		I18nManager.applyLang();
	},
	i18n: function(word, words, lang) {
		var wordTrl = false,
			langWords = false;
		if (words[lang]) {
			langWords = words[lang];
		} else if (words['default']) {
			langWords = words['default'];
		}
		if (langWords[word]) {
			wordTrl = langWords[word];
		}
		return wordTrl || word;
	},
	applyLang: function(context) {
		$("[data-i18n]", context || document).each(function() {
			$(this).html(I18nManager.i18n($(this).attr('data-i18n'), I18nManager.words, I18nManager.lang));
		});
	}
};

var toGerirme = function() {
	I18nManager.changeLang("gerirme");
	$('title').html('gerir-me ' + $('title').html());
	$('.system-name').html('gerir-me');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_gerirme.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_gerirme.png');
	$('.body').css('background-image', 'url("/images/capa_gerirme.jpg")');
};
var toEgrex = function() {
	I18nManager.changeLang("egrex");
	$('title').html('e-grex ' + $('title').html());
	$('.system-name').html('e-grex');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_egrex.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_egrex.png');
	$('.body').css('background-image', 'url("/images/capa_egrex.jpg")');
};
var toEsmile = function() {
	I18nManager.changeLang("esmile");
	$('title').html('e-smile ' + $('title').html());
	$('.system-name').html('e-smile');
	$('.brand img').attr('width', '16');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_esmile.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_esmile.png');
	$('.body').css('background-image', 'url("/images/capa_esmile.jpg")');
};
var toEdoctus = function() {
	I18nManager.changeLang("edoctus");
	$('title').html('e-doctus ' + $('title').html());
	$('.system-name').html('e-doctus');
	$('.brand img').attr('width', '16');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_edoctus.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_edoctus.png');
	$('.body').css('background-image', 'url("/images/capa_edoctus.jpg")');
};
var toEphysio = function() {
	I18nManager.changeLang("ephysio");
	$('title').html('e-physio ' + $('title').html());
	$('.system-name').html('e-physio');
	$('.brand img').attr('width', '16');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_ephysio.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_ephysio.png');
	$('.body').css('background-image', 'url("/images/capa_ephysio.jpg")');
};
var toEbellepet = function() {
	I18nManager.changeLang("ebellepet");
	$('title').html('e-bellepet ' + $('title').html());
	$('.system-name').html('e-bellepet');
	$('.brand img').attr('width', '16');
	$("a[href='/pricing']").attr('href', '/pricing_ephysio');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_ebellepet.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_ebellepet.png');
	$("img[src='/images/favicon.ico']").attr('src', '/images/logo_ftr_ebellepet.ico');
	$('.body').css('background-image', 'url("/images/capa_ebellepet.jpg")');
};
var toEbelle = function() {
	I18nManager.changeLang("ebelle");
	$('title').html('e-belle ' + $('title').html());
	$('.system-name').html('e-belle');
	$('.brand img').attr('width', '16');
	$("img[src='/images/logo.png']").attr('src', '/images/logo_ftr_ebelle.png');
	$("img[src='/images/web.jpg']").attr('src', '/images/logo_ftr_ebelle.png');
	$("img[src='/images/favicon.ico']").attr('src', '/images/logo_ftr_ebelle.ico');
	//$('body').css('background-image','url("/images/capa_ebelle.jpg")');
};
$(function(){
	if (document.location.href.indexOf("gerir") != -1) {
		toGerirme();
	} else if (document.location.href.indexOf("egrex") != -1) {
		toEgrex();
	} else if ((document.location.href.indexOf("esmile") != -1) || (document.location.href.indexOf("e-smile") != -1)) {
		toEsmile();
	} else if (document.location.href.indexOf("edoctus") != -1) {
		toEdoctus();
	} else if ((document.location.href.indexOf("efisio") != -1) || (document.location.href.indexOf("ephysio") != -1)) {
		toEphysio();
	} else if (document.location.href.indexOf("ebellepet") != -1) {
		toEbellepet();
	} else {
		toEbelle();
	}	
});


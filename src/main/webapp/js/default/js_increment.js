//Number Util
Number.prototype.roundup= function(dec){
	if(this.toFixed(dec) < this)
    	return window.parseFloat(this.toFixed(dec))+(1/(Math.pow(10,dec)));
    else
    	return window.parseFloat(this.toFixed(dec));
}

Number.prototype.formatMoney = function(c, d, t){
var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "," : d, t = t == undefined ? "." : t, s = n < 0 ? "-" : "", i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
   return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
 };

String.prototype.replaceAll = function(search, replace) {
	//if replace is null, return original string otherwise it will
	//replace search string with 'undefined'.
	if (!replace)
		return this;

	return this.replace(new RegExp(search, 'g'), replace);
};
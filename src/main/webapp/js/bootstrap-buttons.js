!function(b){function c(f,h){var i="disabled",e=b(f),g=e.data();h=h+"Text";g.resetText||e.data("resetText",e.html());e.html(g[h]||b.fn.button.defaults[h]);setTimeout(function(){h=="loadingText"?e.addClass(i).attr(i,i):e.removeClass(i).removeAttr(i)},0)}function a(d){b(d).toggleClass("active")}b.fn.button=function(d){return this.each(function(){if(d=="toggle"){return a(this)}d&&c(this,d)})};b.fn.button.defaults={loadingText:"loading..."};b(function(){b("body").delegate(".btn[data-toggle]","click",function(){b(this).button("toggle")})})}(window.jQuery||window.ender);
$(function() {


	var selectFirst = function(x) {
		var result = $(".select2-highlighted:first");
		if (result.length == 0 && x < 10) {
			setTimeout(function() {
				selectFirst(x + 1)
			}, 500);
		} else {
			if (result.length > 0) {
				result.click();
				$('.process_product_action').click();
				setTimeout(function() {
					$('.process_product_field').select2('open');
				}, 300);
			}
		}
	};
	var lastKeyIsControl = false;
	$(document).keydown(function(e) {
		if (lastKeyIsControl && e.keyCode === 74 && $(e.target).parent().is('.select2-search')) {
			e.preventDefault();
			setTimeout(function() {
				selectFirst(0)
			}, 300);
		}
		lastKeyIsControl = e.keyCode === 17;
		if (lastKeyIsControl) {
			setTimeout(function() {
				lastKeyIsControl = false;
			}, 100);
		}
	});
});
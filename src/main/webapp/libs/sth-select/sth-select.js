"use strict";

/**
 * SthOverlay should be an external component. Many components 
 * created by us uses a type of overlay, and it should be added 
 * to the DOM only once.
 */

(function () {

	function SthOverlay() {

		var _$overlay = null;

		/**
   * Constructor.
   * 
   * Creates the overlay only once.
   */
		(function create() {

			if (_isAlreadyCreated()) {
				_$overlay = $(".sth-overlay");
				return;
			}

			_$overlay = $('<div class="sth-overlay"></div>');
			_$overlay.appendTo($("body"));
		})();

		/**
   * Checks if overlay is already inserted on the DOM.
   */
		function _isAlreadyCreated() {
			var alreadyExistent = $(".sth-overlay");
			return alreadyExistent && alreadyExistent.length > 0;
		}

		/**
   * Shows the overlay.
   */
		function show() {
			_$overlay.fadeIn(500);
		}

		/**
   * Hides the overlay.
   */
		function hide() {
			_$overlay.fadeOut(500);
		}

		return {
			show: show,
			hide: hide
		};
	}

	window.SthOverlay = window.SthOverlay || SthOverlay;
})();
"use strict";

(function () {

	function SthSelectPopup() {

		var _$popup = null;
		var _$title = null;
		var _$content = null;
		var _$overlay = null;
		var _onSelectCallback = null;
		var _qntityOfItems = 0;

		/**
   * Max of height (in pixels) that the popup can 
   * assume when open.
   */
		var MAX_HEIGHT = 500;

		/**
   * Constructor.
   * Creates the popup section element in the DOM.
   * 
   * The section is created only once. Several calls 
   * does not have effect.
   */
		(function create() {

			_$overlay = new window.SthOverlay();

			if (isAlreadyInDOM()) {
				_$popup = $(".sth-select-popup");
				_$title = $(".sth-select-title");
				_$content = $(".sth-select-content");
				return;
			}

			_$popup = $('<section class="sth-select-popup"></section>');
			_$title = $('<div class="sth-select-title"></div>');
			_$content = $('<div class="sth-select-content"></div>');

			_$popup.append(_$title).append(_$content).appendTo($("body"));
		})();

		/**
   * Checks if the popup is already inserted in DOM.
   * It prevents many insertions and performance loss.
   */
		function isAlreadyInDOM() {
			var $alreadyExistent = $(".sth-select-popup");
			return $alreadyExistent && $alreadyExistent.length > 0;
		}

		/**
   * Shows the popup on the screen.
   */
		function show() {
			_$overlay.show();

			var height = _calculatePopupHeight();
			_$popup.animate({ height: height }, 500);
		}

		/**
   * Calculates pop-up's height based on 
   * number of added items.
   */
		function _calculatePopupHeight() {
			var singleItemHeight = _$content.find(".sth-select-item").first().outerHeight();

			var qntityOfItems = _qntityOfItems;
			var allItemsHeight = singleItemHeight * qntityOfItems;
			var titleHeight = _$title.outerHeight();

			var contentHeight = allItemsHeight + titleHeight;
			return contentHeight < MAX_HEIGHT ? contentHeight : MAX_HEIGHT;
		}

		/**
   * Hides the popup on the screen.
   */
		function hide() {
			_$overlay.hide();
			_$popup.animate({ height: 0 }, 500);
		}

		/**
   * Add an item.
   */
		function addItem(item, autoRender) {
			autoRender = autoRender || true;

			var text = item.text;
			var $listItem = $('<div class="sth-select-item">' + text + '</div>');

			if (autoRender) _$content.append($listItem);

			return $listItem;
		}

		/**
   * Set items which will be added into the list.
   * 
   * #addItems() uses #addItem(), but renders all 
   * added items at once for better performance.
   */
		function setItems(items) {
			// Clear old items
			_clear();

			// Save quantity of items added (useful for some tricks)
			_qntityOfItems = items.length;

			// Add each item into the list
			var $options = $([]);
			$.each(items, function (_, item) {
				var $listItem = addItem(item, false);

				$options = $options.add($listItem);

				$listItem.click(function () {
					_onSelectCallback(item);
					hide();
				});
			});

			// Append items into the DOM (and renders it)
			_$content.append($options);

			// Set the list's height, applying scroll when needed
			var popupHeight = _calculatePopupHeight();
			var titleHeight = _$title.outerHeight();
			_$content.outerHeight(popupHeight - titleHeight);
		}

		/**
   * Clear (removes from DOM) all elements on the list.
   */
		function _clear() {
			_$content.empty();
		}

		/**
   * Event handler which calls a callback when an item 
   * is selected.
   */
		function onSelect(callback) {
			_onSelectCallback = callback;
		}

		/**
   * Sets the popup's title. 
   */
		function setTitle(title) {
			_$title.text(title);
		}

		return {
			show: show,
			hide: hide,
			addItem: addItem,
			setItems: setItems,
			onSelect: onSelect,
			setTitle: setTitle
		};
	}

	window.SthSelect = window.SthSelect || {};
	window.SthSelect.SthSelectPopup = SthSelectPopup;
})();
"use strict";

/*
 * Dependencies
 */

var $ = window.jQuery;

/*
 * Constructor
 */
(function () {

	$.fn.SthSelect = function SthSelect(properties) {

		var _$originalSelect = null;
		var _$popup = null;
		var _$fakeSelect = null;
		var _properties = {};
		var _values = [];

		(function initialize($this) {
			_$originalSelect = $this;
			_properties = buildDefault(properties);
			_$popup = new window.SthSelect.SthSelectPopup();
			_$fakeSelect = fudgeSelect($this, properties);

			_$popup.onSelect(applySelectedValue);

			_$fakeSelect.click(function () {
				_values = extractValues($this);

				_$popup.setTitle(_properties.title);
				_$popup.setItems(_values);
				_$popup.show();
			});
		})($(this));

		function buildDefault(properties) {
			return $.extend({
				title: "Select an option",
				placeholder: "Choose an option",
				autoSize: false
			}, properties);
		}

		function extractValues($this) {
			var values = [];
			$this.find("option").each(function () {
				var $option = $(this);
				var content = { value: $option.val(), text: $option.text() };
				values.push(content);
			});

			return values;
		}

		function fudgeSelect($select, properties) {
			$select.hide();

			var $fakeSelect = $('<div class="sth-select"></div>');
			var $fakeSelectText = $('<span class="sth-select-text"></span>');
			var $fakeSelectArrow = $('<span class="sth-select-arrow"></span>');

			$fakeSelectText.text(properties.placeholder);
			$fakeSelect.append($fakeSelectText);
			$fakeSelect.append($fakeSelectArrow);

			if (!properties.autoSize) $fakeSelect.addClass("fixed-width");

			$select.after($fakeSelect);

			return $fakeSelect;
		}

		function applySelectedValue(selectedValue) {
			var value = selectedValue.value;
			_$originalSelect.val(value);

			var text = selectedValue.text;
			_$fakeSelect.find(".sth-select-text").text(text);
		}
	};

	window.SthSelect = window.SthSelect || {};
	window.SthSelect.init = window.SthSelect.init || SthSelect;
})();

/*
 * Load all elements which use the component by HTML attributes API
 */
$(document).ready(function loadFromHtmlAPI(){
	var $elements = $("select[sth-select]");

	$elements.each(function () {
		var $element = $(this);
		var title = $element.attr("sth-select-title");
		var placeholder = $element.attr("sth-select-placeholder");
		var autoSize = $element.attr("sth-select-autosize");

		$element.SthSelect({
			title: title,
			placeholder: placeholder,
			autoSize: boolFromString(autoSize)
		});
	});

	function boolFromString(string) {
		return string == "true";
	}
});
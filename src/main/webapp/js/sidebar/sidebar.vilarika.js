/**
 * Sidebar component created by VilarikA's team.
 * It uses an accordion effect to produce a hierarchical menu.
 **/

(function(){

	function SidebarComponent(rootSelector)
	{
		this.$root = null;
		this.$rootUl = null;
		this.$liList = [];

		var SUBITEM_LEFT_SPACE = 25;

		var self = this;

		(function initialize()
		{
			try {
				areConstructorParamsValid();
			} catch(error) {
				throw new Error(error);
			}

			populateInstanceProperties();
			loadLiParentElements();
		})();

		function areConstructorParamsValid()
		{
			var $element = $(rootSelector);

			if( ! $element || $element.length < 1 )
				throw new Error("SidebarComponent: Given element doesn't exist.");
		}

		function populateInstanceProperties()
		{
			self.$root = $(rootSelector);
			self.$rootUl = self.$root.find("ul.menu").first();
		}

		function loadLiParentElements()
		{
			var $liElements = self.$rootUl.find("li.parent");
			$liElements.each(function(index)
			{
				var $liElement = $(this);
				var $ulElement = $liElement.find("ul.menu").first();

				addLiElementInTheList( $liElement, $ulElement );
				addClickListener( $liElement, $ulElement );
				hideSubmenu( $liElement );
				addLeftSpacesOnSubMenus( $ulElement );
			});
		}

		function addLiElementInTheList($liElement, $ulElement)
		{
			self.$liList.push({
				$li: $liElement,
				$childUl: $ulElement
			});
		}

		function addClickListener($liElement, $ulElement)
		{
			$liElement.click(function(){
				toggleSubmenu($liElement);
			});
		}

		function toggleSubmenu($element)
		{
			if($element.hasClass("open")) hideSubmenu($element);
			else showSubmenu($element);
		}

		function showSubmenu($element)
		{
			$element.addClass("open");
		}

		function hideSubmenu($element)
		{
			$element.removeClass("open");
		}

		function addLeftSpacesOnSubMenus($ulElement)
		{
			$ulElement.find("> li").each(function(){
				var $childLi = $(this);
				var $childA = $childLi.find("> a");

				// parseInt() removes "px" and returns only the number
				var alreadyExistentPadding = parseInt($childA.css("padding-left"));

				var leftPaddingSize = (alreadyExistentPadding + SUBITEM_LEFT_SPACE);
				$childA.css("padding-left", leftPaddingSize + "px");
			});
		}
	}

	window.Vilarika = window.Vilarika || {};
	window.Vilarika.Component = window.Vilarika.Component || {};
	window.Vilarika.Component.Sidebar = SidebarComponent;

})();
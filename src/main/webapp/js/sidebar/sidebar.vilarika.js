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

		var SUBITEM_LEFT_SPACE = 20;

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
				hideUlElement( $ulElement );
				addLeftSpacesOnSubMenus( $ulElement, (index + 1));
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
				toggleUlElement($ulElement);
			});
		}

		function toggleUlElement($ulElement)
		{
			var height = parseInt($ulElement.css("height"));

			if(height == 0) showUlElement($ulElement);
			else hideUlElement($ulElement);
		}

		function showUlElement($ulElement)
		{
			$ulElement.css("height", "auto");
		}

		function hideUlElement($ulElement)
		{
			$ulElement.css("height", 0);
		}

		function addLeftSpacesOnSubMenus($ulElement, hierarchicalLevel)
		{
			$ulElement.find("> li").each(function(){
				var $childLi = $(this);
				var $childA = $childLi.find("> a");

				// parseInt() removes "px" and returns only the number
				var alreadyExistentPadding = parseInt($childA.css("padding-left"));

				var leftPaddingSize = (alreadyExistentPadding) + (hierarchicalLevel * SUBITEM_LEFT_SPACE);
				$childA.css("padding-left", leftPaddingSize + "px");
			});
		}
	}

	window.Vilarika = window.Vilarika || {};
	window.Vilarika.Component = window.Vilarika.Component || {};
	window.Vilarika.Component.Sidebar = SidebarComponent;

})();
/**
 * Sidebar component created by VilarikA's team.
 * It uses an accordion effect to produce a hierarchical menu.
 **/

(function(){

	function SidebarComponent(
		rootSelector, 
		menuButtonSelector, 
		contentWrapperSelector
	)
	{
		this.$root = null;
		this.$rootUl = null;
		this.$liList = [];
		this.$menuButton = null;
		this.$contentWrapper = null;

		var SUBITEM_LEFT_SPACE = 25;
		var SIDEBAR_WIDTH_PX = 230;
		var SIDEBAR_CLOSED_CLASS = "sidebar-closed";
		var ITEM_OPEN_CLASS = "item-open";

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
			addMenuButtonListener();
		})();

		function areConstructorParamsValid()
		{
			var $element = $(rootSelector);
			if( ! $element || $element.length < 1 )
				throw new Error("SidebarComponent: Given root element selector doesn't exist.");
		
			var $menuButton = $(menuButtonSelector);
			if ( ! $menuButton || $menuButton.length < 1)
				throw new Error("SidebarComponent: Given menu button selector doesn't exist.");

			var $contentWrapper = $(contentWrapperSelector);
			if( ! $contentWrapper || $contentWrapper.length < 1)
				throw new Error("SidebarComponent: Given content wrapper selector doesn't exist.");
		}

		function populateInstanceProperties()
		{
			self.$root = $(rootSelector);
			self.$rootUl = self.$root.find("ul.menu").first();
			self.$menuButton = $(menuButtonSelector);
			self.$contentWrapper = $(contentWrapperSelector);
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
				if(isSidebarClosed())
					openSidebar();
				
				toggleSubmenu($liElement);
			});
		}

		function toggleSubmenu($element)
		{
			if($element.hasClass(ITEM_OPEN_CLASS)) hideSubmenu($element);
			else showSubmenu($element);
		}

		function showSubmenu($element)
		{
			$element.addClass(ITEM_OPEN_CLASS);
		}

		function hideSubmenu($element)
		{
			$element.removeClass(ITEM_OPEN_CLASS);
		}

		function closeSubmenus()
		{
			self.$root.find("li.item.parent").each(function(){
				$(this).removeClass(ITEM_OPEN_CLASS);
			});
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

		function addMenuButtonListener()
		{
			self.$menuButton.click(function()
			{
				toggleSidebar();
			});
		}

		function toggleSidebar()
		{
			if(isSidebarClosed()) openSidebar();
			else closeSidebar();
		}

		function isSidebarClosed()
		{
			return self.$root.hasClass(SIDEBAR_CLOSED_CLASS);
		}

		function openSidebar()
		{
			self.$root.removeClass(SIDEBAR_CLOSED_CLASS);
			self.$contentWrapper.removeClass(SIDEBAR_CLOSED_CLASS);
		}

		function closeSidebar()
		{
			self.$root.addClass(SIDEBAR_CLOSED_CLASS);
			self.$contentWrapper.addClass(SIDEBAR_CLOSED_CLASS);

			closeSubmenus();
		}

		return {
			open: openSidebar,
			close: closeSidebar,
			closeSubmenus: closeSubmenus 
		};
	}

	window.Vilarika = window.Vilarika || {};
	window.Vilarika.Component = window.Vilarika.Component || {};
	window.Vilarika.Component.Sidebar = SidebarComponent;

})();
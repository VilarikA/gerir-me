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
		this.$listParentLi = [];

		this.$menuButton = null;
		this.$contentWrapper = null;

		var SUBITEM_LEFT_SPACE = 30;
		var SIDEBAR_WIDTH_PX = 230;
		var SIDEBAR_CLOSED_CLASS = "sidebar-closed";
		var ITEM_OPEN_CLASS = "item-open";

		var self = this;

		(function initialize()
		{
			if( ! rootSelector || ! menuButtonSelector || ! contentWrapperSelector )
				throw new Error("Constructor parameters are invalid.");

			self.$root = loadRoot();
			self.$listParentLi = loadListParentLi(self.$root);
			self.$menuButton = loadMenuButton();
			self.$contentWrapper = loadContentWrapper();
			self.$menuList = loadMenuList();

			populateMenuList();
			hideSubmenus();
			addSubItemsClickListener();
			addSubItemsLeftPadding(self.$root.find("> ul.menu"));

			self.$menuButton.click(toggleSidebar);
		})();

		function loadRoot()
		{
			return loadElement(rootSelector);
		}

		function loadListParentLi($root)
		{
			return $root.find("li.item.parent");
		}

		function loadMenuButton()
		{
			return loadElement(menuButtonSelector);
		}

		function loadContentWrapper()
		{
			return loadElement(contentWrapperSelector);
		}

		function loadMenuList()
		{
			return loadElement("[x-menu-list]");
		}

		function loadElement(selector, exceptionMessage)
		{
			var $element = $(selector);
			if( ! $element || $element.length < 1 )
				throw new Error(exceptionMessage);

			return $element;
		}

		function hideSubmenus()
		{
			self.$listParentLi.find("ul.menu").hide();
			self.$listParentLi.removeClass("item-open");
		}

		function addSubItemsClickListener()
		{
			var $lis = self.$root.find("li.item");
			$lis.each(function()
			{
				var $li = $(this);

				$li.click(function()
				{
					if(isSidebarClosed())
						openSidebar();

					if( $li.hasClass("parent") )
					{
						$(this).toggleClass("item-open");
						$(this).find("> ul.menu").slideToggle(300);
					}
				});
			});
		}

		function addSubItemsSlideEffect()
		{
			self.$listParentLi.each(function(){
				$(this).click(function(){
					$(this).toggleClass("item-open");
					$(this).find("> ul.menu").slideToggle(300);
				});
			});
		}

		function addSubItemsLeftPadding($itemsParent)
		{
			$itemsParent.find("> li.item.parent").each(function(){
				var $li = $(this);

				$li.find("> ul.menu > li.item > a").each(function(){
					$a = $(this);

					var existentPadding = parseInt($a.css("padding-left"));
					var leftPadding = (existentPadding + SUBITEM_LEFT_SPACE);
					$a.css("padding-left", leftPadding + "px");
				});

				//addSubItemsLeftPadding($li.find("> ul.menu"));
			});
		}

		function populateMenuList($ulParent, listItems)
		{
			$ulParent = $ulParent || self.$menuList;
			listItems = listItems || JSON.parse( localStorage.getItem("menus") ) || [];

			if( listItems.length < 1 )
				return true;

			var $liItems = [];
			
			listItems.map(function(item){
				
				var url = item.url || "#";
				var label = item.label || "";
				var children = item.children || [];
				var parentClass = children.length > 1 ? "parent" : "";

				var $item = $(
					'<li class="item ' + parentClass + '">' +
					'	<a href="' + url + '">' +
					'		<span class="item-label">' + label + '</span>' +
					'	</a>' + 
					'</li>'
				);

				if(children.length > 0){
					$ulChild = $('<ul class="menu"></ul>');
					populateMenuList($ulChild, children);

					$item.append($ulChild);
				}

				$liItems.push( $item );
			});

			$ulParent.append($liItems);
		}

		function toggleSidebar()
		{
			if( isSidebarClosed() ){
				openSidebar();
			} else { 
				closeSidebar();
				hideSubmenus();
			}
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
		}

		return {
			open: openSidebar,
			close: closeSidebar 
		};
	}

	window.Vilarika = window.Vilarika || {};
	window.Vilarika.Component = window.Vilarika.Component || {};
	window.Vilarika.Component.Sidebar = SidebarComponent;

})();
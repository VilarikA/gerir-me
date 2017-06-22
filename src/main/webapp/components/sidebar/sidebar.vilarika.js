/**
 * Sidebar component created by VilarikA's team.
 * It uses an accordion effect to produce a hierarchical menu.
 **/

(function(){

	function SidebarComponent()
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
			populateSidebar();

			self.$root = loadElement("[x-vr-sidebar]");
			self.$listParentLi = self.$root.find("li.item.parent");
			self.$menuButton = loadElement("[x-vr-sidebar-button]");
			self.$contentWrapper = loadElement("[x-vr-content-wrapper]");

			hideSubmenus();
			addSubItemsLeftPadding(self.$root.find("> ul.menu"));

			self.$menuButton.click(toggleSidebar);

			self.$root.find("li.item").click(function(){
				toggleSubmenu( $(this) );
			});
		})();

		/**
		 * Load an element from DOM structure and validates if 
		 * it exists.
		 * 
		 * @param {*} selector 
		 * @param {*} exceptionMessage 
		 */
		function loadElement(selector, exceptionMessage)
		{
			exceptionMessage = exceptionMessage || "Not a valid DOM element";

			var $element = $(selector);
			if( ! $element || $element.length < 1 )
				throw new Error(exceptionMessage);

			return $element;
		}

		/**
		 * Hide all submenus which are open.
		 */
		function hideSubmenus()
		{
			self.$listParentLi.find("ul.menu").hide();
			self.$listParentLi.removeClass("item-open");
		}

		/**
		 * Add left space on each subitem, creating a hierarchical
		 * design.
		 * 
		 * @param {*}  
		 */
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
			});
		}

		/**
		 * Populate the sidebar with menu options stored in Local Storage.
		 * 
		 * @param {*}  
		 * @param {*} childrenItems 
		 */
		function populateSidebar($parentUl, childrenItems)
		{
			$parentUl = $parentUl || loadElement("[x-vr-sidebar-menu]");
			childrenItems = childrenItems || JSON.parse( localStorage.getItem("menus") ) || [];

			if( childrenItems.length < 1 )
				return;

			var $items = [];

			childrenItems.map(function(item){

				var $li = createLi(item);
				var $a = createA(item);
				var $label = createLabel(item);

				if( item.icon )
					$a.append( createIcon(item) );

				$a.append($label);
				$li.append($a);

				if( item.children && item.children.length > 0 ){
					var $ul = createUl();
					populateSidebar($ul, item.children);

					$li.append($ul);
				}

				$items.push($li);
			});

			$parentUl.append($items);

			function createLi(item)
			{
				var $li = $('<li class="item"></li>');
				if( item.children && item.children.length > 0 )
					$li.addClass("parent");

				return $li;
			}

			function createA(item)
			{
				var $a = $('<a></a>');
				if( item.url )
					$a.attr("href", item.url);

				return $a;
			}
			
			function createIcon(item)
			{
				return $('<i class="fa fa-' + item.icon + '"></i>');
			}

			function createLabel(item)
			{
				return $('<span class="item-label">' + item.label + '</span>');
			}

			function createUl()
			{
				return $('<ul class="menu"></ul>');
			}
		}

		/**
		 * Toggle the given parent's submenu.
		 * 
		 * @param {*} $clickedParent
		 */
		function toggleSubmenu($clickedParent)
		{
			if( isSidebarClosed() )
				openSidebar();

			if( $clickedParent.hasClass("parent") ){
				$clickedParent.toggleClass("item-open");
				$clickedParent.find("> ul.menu").slideToggle(300);
			}
		}

		/**
		 * Toggle sidebar's open state.
		 */
		function toggleSidebar()
		{
			isSidebarClosed() ?
				openSidebar() :
				closeSidebar();
		}

		/**
		 * Checks whether the sidebar is closed or not.
		 */
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

			hideSubmenus();
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
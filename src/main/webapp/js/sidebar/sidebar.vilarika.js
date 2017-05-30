/**
 * Sidebar component created by VilarikA's team.
 * It uses an accordion effect to produce a hierarchical menu.
 * 
 * @author Stanley Sathler <stanleysathlerpinto@gmail.com>
 * @requires jQuery 1+
 **/

(function(){

    function SidebarComponent(element)
    {
        this.$root;
        this.$rootUl;
        this.$liList = [];

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
            if( ! $(element) || $(element).length < 1 )
                throw new Error("Given element doesn't exist.");
        }

        function populateInstanceProperties()
        {
            self.$root = $(element);
            self.$rootUl = self.$root.find("ul.menu").first();
        }

        function loadLiParentElements()
        {
            var $liElements = self.$rootUl.find("li.parent");
            $liElements.each(function(index)
            {
                addLiElementInTheList( $(this) );
                addClickListener( index );
            });

            function addLiElementInTheList($liElement)
            {
                self.$liList.push({
                    $li: $liElement,
                    $childUl: $liElement.find("ul.menu").first()
                });
            }

            function addClickListener(index)
            {
                var $element = self.$liList[index];
                $element.$li.click(function(){
                    console.log("Clicked element's child ul:");
                    console.log($element.$childUl);
                });
            }
        }
    }

    window.VrSidebarComponent = SidebarComponent;

})();
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
        this.$element;
        this.$ulElements;
        var self = this;

        (function initialize()
        {
            try {
                areConstructorParamsValid();
            } catch(error) {
                throw new Error(error);
            }

            populateInstanceProperties();
            loadUlElementsHierarchically();
        })();

        function areConstructorParamsValid()
        {
            if( ! $(element) || $(element).length < 1 )
                throw new Error("Given element doesn't exist.");
        }

        function populateInstanceProperties()
        {
            self.$element = $(element);
        }

        function loadUlElementsHierarchically()
        {
            //
        }
    }

})();
/*
 * JS file created by VilarikA's team.
 * @author Stanley Sathler <stanleysathlerpinto@gmail.com>
 */

"use strict";

(function() 
{
    function CollapsibleMenu()
    {
        var BREAKPOINT = 768; // in pixels

        var self = this;
        self.__menuElement = null;
        self.__toggleButton = null;

        /*
         * Self-invoked function which initializes the script.
         */
        (function initialize()
        {
            if ( isScreenTooBig() )
                return false;

            loadMenuElement();
            if ( ! isThereMenuElement())
                throw new Error("The page must contain an element with id: main-menu");

            addToggleButton();
            hideMenu();
        })();

        /*
         * Checks if the screen is bigger than the defined breakpoint.
         */
        function isScreenTooBig()
        {
            return $(window).width() > BREAKPOINT;
        }

        /*
         * Loads the menu element from DOM and stores it as a property.
         */
        function loadMenuElement()
        {
            self.__menuElement = $("#main-menu");
        }

        /*
         * Checks wether the menu element could be loaded from DOM.
         */
        function isThereMenuElement()
        {
            return ( self.__menuElement || self.__menuElement.length > 0 );
        }

        /*
         * Add the toggle span where user clicks to open/close the menu element.
         */
        function addToggleButton()
        {
            self.__toggleButton = $(
                '<span class="toggle-button">' +
                '   Menu' +
                '</span'
            );

            $(self.__menuElement).prepend(self.__toggleButton);

            $(self.__toggleButton).click(function()
            {
                if(isMenuOpened()) hideMenu();
                else showMenu();
            });
        }

        /*
         * Checks wether the menu is open.
         */
        function isMenuOpen()
        {
            return $(self.__menuElement).css("display") !== "none";
        }

        /*
         * Hides the menu element.
         */
        function hideMenu()
        {
            $(self.__menuElement).hide();
        }

        /*
         * Shows the menu element.
         */
        function showMenu()
        {
            $(self.__menuElement).show();
        }
    }

    $(document).ready(function(){
        (new CollapsibleMenu());
    });
})();
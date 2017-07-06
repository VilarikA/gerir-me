/**
 * Scala returns a JSON with a ";" char at the end. So, JS's native 
 * JSON.parse() function does not work well.
 * 
 * Due that, we have to use eval(). This utility creates a better 
 * interface for that.
 */

(function()
{
	"use strict";

	function JSON()
	{
		function parse(jsonString)
		{
			var result;
			eval("result = " + jsonString);
			return result;
		}

		return {
			parse: parse
		};
	}

	window.Vilarika = window.Vilarika || {};
	window.Vilarika.Utilities = window.Vilarika.Utilities || {};
	window.Vilarika.Utilities.JSON = window.Vilarika.Utilities.JSON || new JSON();
})();

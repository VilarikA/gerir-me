var CalendarManager = {
	calendarFeatures : {
		useTreatmentClass : false
	},
	calendarPermitions : {
		newEvent : false,
		deleteEvent: false,
		moveEvent: false,
		editEvent: false
	},
	lastUsers : function(unit, users){
		var userKey = "users"+unit;
		if(unit && users){
			localStorage.setItem(userKey, JSON.stringify(users));
		}
		return JSON.parse(localStorage.getItem(userKey));
	},
	hasLastUsers : function(unit){
		if(unit){
			return localStorage.hasOwnProperty("users"+unit);
		}else{
			return [];
		}
		
	}
};
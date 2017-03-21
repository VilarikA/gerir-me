var convert = function (name, actualValue) {
        var notDate = function(name){
          return name == 'updatedby'
           || name == 'xxxxxx'
           ;
        };
        var isDate = function(name){
          return name == 'createdat'
           || name == 'birthday'
           || name == 'lastlogin'

           ;
        };
        
        var nameAux = name.toLowerCase();
        if (notDate(nameAux)) {
          return actualValue
        }
        if (actualValue == null) {
          return actualValue
        }
        if (nameAux.indexOf("date") != -1 || isDate (nameAux)) {
          if (actualValue != '') {
            var strAux = "";
            strAux = actualValue.toString();
            if (strAux.indexOf("0000") != -1) {
              var date = new Date(actualValue);
              actualValue = date.getTextWhen();
            } else {
              var date = new Date(actualValue*1000);
              actualValue = date.getTextWhen() + " as "+date.getHourBr();
            }
          }
        }
        return actualValue;
};
var DiffManager = function(users){
  var self = this;
  var dataFilter = {
    fields : ['updatedAt', 'createdAt', 'start', 'end', '_complete.createat'],
    is : function(name){
      //alert ("vaiii >>>>>> " + name)
      return this.fields.filter(function(pName){ return pName.toLowerCase() === name.toLowerCase(); }).length > 0;
    },
    parse : function(value){
      var date = new Date(value*1000);
      return date.getTextWhen() + " as "+date.getHourBr();
    }
  };
  var userFilter = {
    fields : ['user', 'createdBy', 'updatedBy'],
    is : function(name){
      return this.fields.filter(function(pName){ return pName === name; }).length > 0;
    },
    parse : function(value){
      var  user = users.filter(function(u){ return u.id == value; })[0];
      if(user){
        value = user.name;
      }
      return value;
    }
  };

  var reduces = [dataFilter, userFilter];

  this.toHtml = function(diff, notShowValidator){
    var html = "<table class='bordered-table zebra-striped'>";
    for(var i in diff){
      if(i.indexOf("_") != 0 && !notShowValidator(i)){
        var oldValue = "";
        if(diff._previos && diff._previos._complete){
          oldValue = diff._previos._complete[i] || "";
        }
        
        var actualValue = diff[i];
        var name = i;
/*
        alert ("vaiiii nnnnnnnn   " + i)
        var nameAux = name.toLowerCase();
        if (nameAux.indexOf("date") != -1) {
          var date = new Date(actualValue*1000);
          //alert (date.getTextWhen() + " as "+date.getHourBr());
          actualValue = date.getTextWhen() + " as "+date.getHourBr();
        }
*/
        actualValue = convert (name, actualValue)
        html += "<tr><td class='diff_property'><span>"+name+"<span></td><td class='diff_old'><span>"+oldValue+"<span></td><td>"+actualValue+"</td></tr>";
      }
    }
    html += "</table>";
    return html;
  };
  this.buildDiff = function(origin, dest){
    var out = {};
    for(var i in dest){
      if(origin[i] != dest[i]){
        out[i] = dest[i];
      }
    }
    out._complete = dest;
    out._previos = origin;
    return out;    
  };
  this.buildListDiff = function(diffs){
    var last = {};
    return diffs.map(function(item){
      var result = self.buildDiff(last , item);
      last = item;
      for( var i in result){
        for(var ri in reduces){
          var reduce = reduces[ri];
          if(reduce.is(i)){
            result[i] = reduce.parse(result[i]);
            break;
          }
        }
      }
      return result;
    });
  };
};

var module = angular.module('br',[]);
module.directive('br.phone', function() {
  return {
    require: 'ngModel',
    link: function ($scope, $element, attr, ngModel) {
      $element.unbind('input');
      $element.bind('input', function () {
        var text = $(this).val();
        text = text.replace(/[^\d]/g, '');
        if(text.length > 0){
          text = "(" + text;
            if(text.length > 3){
              text = [text.slice(0, 3), ") ", text.slice(3)].join('');  
    }
    if(text.length > 12){
      if (text.length > 13){
        text = [text.slice(0, 10), "-", text.slice(10)].join('');
      }else{
        text = [text.slice(0, 9), "-", text.slice(9)].join('');
      }
    }                 
    if (text.length > 15){
      text = text.substr(0,15);
    }
  }
  $scope.$apply(function () {
    ngModel.$setViewValue(text);
    $element.val(text);
  });
});
    }
  };
});
module.directive('br.localStorageField', function() {
  return {
    require: 'ngModel',
    link: function ($scope, $element, attr, ngModel) {
      var value = localStorage.getItem($element.attr('id'));
      $element.on('change',function(){
        localStorage.setItem($element.attr('id'), $element.val());
      });
      if(value){
        ngModel.$setViewValue(value);
        $element.val(value);
      }
    }
  };
});
module.directive('br.tab', function() {
  return {
    link: function ($scope, $element, attr) {
      $element.find('a').click(function(){
        var tabbable = $element.parent('.tabbable')
        $('li',tabbable).removeClass('active');
        $(this).parent().addClass('active');
        $(".tab-pane", tabbable).hide().removeClass('in').removeClass('active');
        var toShow = $(this).data('href');
        $(toShow).show().addClass('in').addClass('active');;
      });
    }
  };
});
module.directive('br.money', [function () {
  return {
    require: '?ngModel',
    link: function (scope, elem, attrs, ngModel) {
      ngModel.$parsers.unshift(function (viewValue) {
        elem.priceFormat({
          prefix: '',
          centsSeparator: '.',
          thousandsSeparator: ''
        });
        return parseFloat(elem[0].value);
      });
    }
  };
}]);
module.directive('br.datepicker', function() {
    var getDateBr = function (d){
      month = d.getMonth()+1;
      if(month <10 ){
        month = "0"+month;
      }
      day = d.getDate();
      if(day <10){
        day = "0"+day;
      }
      var date_str = day+"/"+month+"/"+d.getFullYear()
      return date_str;
    };  
    return {
        restrict: 'A',
        require : 'ngModel',
        link : function (scope, element, attrs, ngModelCtrl) {
          element.datepicker({
              dateFormat:'mm/dd/yy',
              onSelect:function (date) {
                  scope.$apply(function () {
                      ngModelCtrl.$setViewValue(date);
                      ngModelCtrl.$setModelValue(element.datepicker('getDate').getTime());
                  });
              }
          });          
          ngModelCtrl.$parsers.unshift(function (viewValue) {
            var dateArray  = viewValue.split('/');
            var toUsa = dateArray[1]+'-'+dateArray[0]+'-'+dateArray[2];
            return new Date(toUsa).getTime();
          });

          ngModelCtrl.$formatters.unshift(function (modelValue) {
            var result = modelValue;
            if(angular.isNumber(modelValue)){
              var date = new Date();
              date.setTime(modelValue);
              result = getDateBr(date);
            }
            return result;
          });          
        }
    }
});
module.directive('br.time', function() {
  return { 
    require: 'ngModel',
    link: function ($scope, $element, attr, ngModel) {
      $element.unbind('input');
      $element.bind('blur', function(){
         var text = $(this).val();
         if(text.length < 5){
            $(this).val('00:00');
          }
      });
      $element.bind('input', function () {
        var text = $(this).val();
        text = text.replace(/[^\d]/g, '');
        if(text.length > 0){
            if(text.length > 4){
              text = text.slice(0,4);
            }
            if(text.length > 2){
              text = [text.slice(0, 2), ":", text.slice(2)].join('');
            }
        }
        $scope.$apply(function () {
          ngModel.$setViewValue(text);
          $element.val(text);
        });
      });
    }
  };
});
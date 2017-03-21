angular.module('FotoCarrousel', [])
  .controller('FotoCarrouselCtrl', function ($scope, $http) {
    $scope.photos = [];
    $http.get('/media/list').then(function(response){
    	response = eval("$scope.photos="+response.data);
	});

    // initial image index
    $scope._index = 0;

    // if a current image is the same as requested image
    $scope.isActive = function (index) {
        return $scope._index === index;
    };

    // show prev image
    $scope.showPrev = function () {
        $scope._index = ($scope._index > 0) ? --$scope._index : $scope.photos.length - 1;
    };

    // show next image
    $scope.showNext = function () {
        $scope._index = ($scope._index < $scope.photos.length - 1) ? ++$scope._index : 0;
    };

    // show a certain image
    $scope.showPhoto = function (index) {
        $scope._index = index;
    };
    $scope.addImage = function(url){
        $("#body").jqteVal($("#body").val()+"<img src='"+url+"'/>");
    }    
});


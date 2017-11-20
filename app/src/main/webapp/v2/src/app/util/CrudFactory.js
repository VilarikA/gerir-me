var CrudFactory = function(params){
    var OVERRIDE = {'edit' : true, 'save' : true, '$scope' : true};
    var optionsActions = {
      save : {
        method:'POST',
        transformResponse : function(a){
         return Util.jsonParse(a);
        }
      },
      get : {
        transformResponse : function(a){
         return Util.jsonParse(a);
        }        
      },
      query : { 
        method : "GET",
        isArray:true, 
        transformResponse : function(a){
         return Util.jsonParse(a);
       }
     },
     'delete': {
        method:'GET',
      url : params.collectionUrl+"/delete"
      }
    };
  var options = {
    useRouteParameter : false,
    name : "",
    columns : [],
    defaultValues : {},
    url : "",
    collectionUrl : "",
    collectionParams : { query : "@query" },
    actions : {},
    onSelectObj : function(obj){},
    extend : function(){},
    userGridAction: false
  };
  angular.extend(options, params);
  angular.extend(optionsActions, options.actions);
      var controller = EbelleApp.controller(options.name,["$resource","$route", "$routeParams", "$location", "$scope","$q", "$http",function($resource, $route, $routeParams, $location,$scope, $q, $http){
          $scope.$route = $route;
          $scope.controllerName = options.name;
          $scope.$location = $location;
          $scope.$routeParams = $routeParams;
          $scope.columns = options.columns;
          $scope.userGridAction = options.userGridAction;
          if(options.extend){
            options.extend.$scope = $scope;
          }
          this.$scope = $scope;
          var runtimeCollectionParams = {};
          (function(){
            for(var prop in options.collectionParams){
              var val = options.collectionParams[prop];
              if(angular.isFunction(val)){
                val = val();
              }
              if(val){
                runtimeCollectionParams[prop] = val;
              }
            }
          }());
          var hasUrlCollection = options.collectionUrl !== "";
          var updateList = function(options){
              if(hasUrlCollection){
                $scope.list = crudCollections.query(options, function(){});
              }
          };
          var newRow = function(){
            var newObj = options.defaultValues;
            for(var prop in runtimeCollectionParams){
              if(!angular.isString(runtimeCollectionParams[prop]) || runtimeCollectionParams[prop].indexOf('@') !== 0){
                newObj[prop] = runtimeCollectionParams[prop];
              }
            }
            $scope.current = new crudCollections(newObj);
          };
          var crudCollections =  $resource(options.collectionUrl, runtimeCollectionParams, optionsActions);
          this.crudCollections = crudCollections;
          var crudResource = $resource(options.url+"/:id/",{id:'@id'},{update: { method: 'POST' },  get : {
                                          method:'GET',
                                          transformResponse : function(a){
                                           return Util.jsonParse(a);
                                          }        
                                      }});
          this.crudResource = crudResource;
          $scope.$watch('current', function(obj){
            options.onSelectObj(obj);
          });
          if(options.useRouteParameter && $routeParams.id){
            $scope.current = crudResource.get({"id" :  $routeParams.id});
          }else{
            updateList();
            newRow();
          }
          $scope.save = function(obj){
            var saveClojure = function(obj, callback, listUpdate){
              //obj = angular.copy(obj);
              if(obj){
                obj.$save().then(function(resp){
                  if(callback){
                    callback(resp);
                  }
                  if(listUpdate){
                    updateList();
                  }
                })['catch'](function(message){
                    alert(message.data);
                });
              }else{
                updateList();
              }
            };
            if(options.extend.save){
              options.extend.save(obj, saveClojure, $q, $http);
            }else{
              saveClojure(obj);
            }
          };

          $scope.edit = function(obj){
            var copyObj = angular.copy(obj);
            var editClojure = function(copyObj){
              $scope.current = copyObj;
            };
            if(options.extend.edit){
              options.extend.edit(copyObj, $scope.list, editClojure);
            }else{
              editClojure(copyObj);
            }
          };

          $scope.remove = function(obj){
           obj.$delete().then(function(){
            updateList();
          });
         };
        var extend = function(src){
            for(var method in src){
              if(!OVERRIDE[method]){
                $scope[method] = src[method]($scope, crudResource, crudCollections);
            }
          }
        };
        extend(options.extend);
    }]);
    return controller;
 };
'use strict';


var services = angular.module('offSaleApp',['ngResource',  'ui.select2']);

services.factory('OffsaleProductsFactory', function ($resource) {
    return $resource('/offsale/offsaleProducts', { }, {
        query: { method: 'GET', isArray: true, transformResponse: function(data){
        	console.log(data);
            return data;
        }},
        create: { method: 'POST' }
    })
});

services.factory('OffsaleProductFactory', function ($resource) {
    return $resource('/offsale/offsaleProducts/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});

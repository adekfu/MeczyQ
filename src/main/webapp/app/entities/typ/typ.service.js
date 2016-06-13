(function() {
    'use strict';
    angular
        .module('meczyQApp')
        .factory('Typ', Typ);

    Typ.$inject = ['$resource', 'DateUtils'];

    function Typ ($resource, DateUtils) {
        var resourceUrl =  'api/typs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.data = DateUtils.convertLocalDateFromServer(data.data);
                        data.dataEdycji = DateUtils.convertLocalDateFromServer(data.dataEdycji);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.data = DateUtils.convertLocalDateToServer(data.data);
                    data.dataEdycji = DateUtils.convertLocalDateToServer(data.dataEdycji);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.data = DateUtils.convertLocalDateToServer(data.data);
                    data.dataEdycji = DateUtils.convertLocalDateToServer(data.dataEdycji);
                    return angular.toJson(data);
                }
            }
        });
    }
})();

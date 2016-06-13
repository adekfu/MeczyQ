(function() {
    'use strict';
    angular
        .module('meczyQApp')
        .factory('Mecz', Mecz);

    Mecz.$inject = ['$resource', 'DateUtils'];

    function Mecz ($resource, DateUtils) {
        var resourceUrl =  'api/meczs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataMeczu = DateUtils.convertLocalDateFromServer(data.dataMeczu);
                        data.dataZamkniecia = DateUtils.convertLocalDateFromServer(data.dataZamkniecia);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dataMeczu = DateUtils.convertLocalDateToServer(data.dataMeczu);
                    data.dataZamkniecia = DateUtils.convertLocalDateToServer(data.dataZamkniecia);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dataMeczu = DateUtils.convertLocalDateToServer(data.dataMeczu);
                    data.dataZamkniecia = DateUtils.convertLocalDateToServer(data.dataZamkniecia);
                    return angular.toJson(data);
                }
            }
        });
    }
})();

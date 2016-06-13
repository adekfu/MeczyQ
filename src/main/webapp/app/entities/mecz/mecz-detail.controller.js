(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .controller('MeczDetailController', MeczDetailController);

    MeczDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Mecz', 'Typ'];

    function MeczDetailController($scope, $rootScope, $stateParams, entity, Mecz, Typ) {
        var vm = this;

        vm.mecz = entity;

        var unsubscribe = $rootScope.$on('meczyQApp:meczUpdate', function(event, result) {
            vm.mecz = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

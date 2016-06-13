(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .controller('TypDetailController', TypDetailController);

    TypDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Typ', 'Mecz'];

    function TypDetailController($scope, $rootScope, $stateParams, entity, Typ, Mecz) {
        var vm = this;

        vm.typ = entity;

        var unsubscribe = $rootScope.$on('meczyQApp:typUpdate', function(event, result) {
            vm.typ = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

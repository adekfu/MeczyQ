(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .controller('MeczDeleteController',MeczDeleteController);

    MeczDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mecz'];

    function MeczDeleteController($uibModalInstance, entity, Mecz) {
        var vm = this;

        vm.mecz = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mecz.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

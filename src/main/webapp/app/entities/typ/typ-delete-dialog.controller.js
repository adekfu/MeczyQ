(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .controller('TypDeleteController',TypDeleteController);

    TypDeleteController.$inject = ['$uibModalInstance', 'entity', 'Typ'];

    function TypDeleteController($uibModalInstance, entity, Typ) {
        var vm = this;

        vm.typ = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Typ.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

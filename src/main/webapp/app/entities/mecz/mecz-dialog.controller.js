(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .controller('MeczDialogController', MeczDialogController);

    MeczDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mecz', 'Typ'];

    function MeczDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mecz, Typ) {
        var vm = this;

        vm.mecz = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.typs = Typ.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mecz.id !== null) {
                Mecz.update(vm.mecz, onSaveSuccess, onSaveError);
            } else {
                Mecz.save(vm.mecz, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('meczyQApp:meczUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataMeczu = false;
        vm.datePickerOpenStatus.dataZamkniecia = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

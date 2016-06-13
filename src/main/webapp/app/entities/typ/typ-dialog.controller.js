(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .controller('TypDialogController', TypDialogController);

    TypDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Typ', 'Mecz'];

    function TypDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Typ, Mecz) {
        var vm = this;

        vm.typ = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.meczs = Mecz.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typ.id !== null) {
                Typ.update(vm.typ, onSaveSuccess, onSaveError);
            } else {
                Typ.save(vm.typ, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('meczyQApp:typUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.data = false;
        vm.datePickerOpenStatus.dataEdycji = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

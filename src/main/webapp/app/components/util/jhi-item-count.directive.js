(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info">' +
                    '{{(($ctrl.page-1) * 20)==0 ? 1:(($ctrl.page-1) * 20 + 1)}} - ' +
                    '{{($ctrl.page * 20) < $ctrl.queryCount ? ($ctrl.page * 20) : $ctrl.queryCount}} ' +
                    'z {{$ctrl.queryCount}} rekordów.' +
                '</div>',
        bindings: {
            page: '<',
            queryCount: '<total'
        }
    };

    angular
        .module('meczyQApp')
        .component('jhiItemCount', jhiItemCount);
})();

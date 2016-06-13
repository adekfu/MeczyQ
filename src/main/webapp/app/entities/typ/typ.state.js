(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('typ', {
            parent: 'entity',
            url: '/typ?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'meczyQApp.typ.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/typ/typs.html',
                    controller: 'TypController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typ');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('typ-detail', {
            parent: 'entity',
            url: '/typ/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'meczyQApp.typ.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/typ/typ-detail.html',
                    controller: 'TypDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typ');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Typ', function($stateParams, Typ) {
                    return Typ.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('typ.new', {
            parent: 'typ',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/typ/typ-dialog.html',
                    controller: 'TypDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imie: null,
                                nazwisko: null,
                                wynikDruzyna1: null,
                                wynikDruzyna2: null,
                                data: null,
                                dataEdycji: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('typ', null, { reload: true });
                }, function() {
                    $state.go('typ');
                });
            }]
        })
        .state('typ.edit', {
            parent: 'typ',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/typ/typ-dialog.html',
                    controller: 'TypDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Typ', function(Typ) {
                            return Typ.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('typ', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('typ.delete', {
            parent: 'typ',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/typ/typ-delete-dialog.html',
                    controller: 'TypDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Typ', function(Typ) {
                            return Typ.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('typ', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

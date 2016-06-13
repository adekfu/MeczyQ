(function() {
    'use strict';

    angular
        .module('meczyQApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mecz', {
            parent: 'entity',
            url: '/mecz?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'meczyQApp.mecz.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mecz/meczs.html',
                    controller: 'MeczController',
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
                    $translatePartialLoader.addPart('mecz');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mecz-detail', {
            parent: 'entity',
            url: '/mecz/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'meczyQApp.mecz.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mecz/mecz-detail.html',
                    controller: 'MeczDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mecz');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mecz', function($stateParams, Mecz) {
                    return Mecz.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('mecz.new', {
            parent: 'mecz',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mecz/mecz-dialog.html',
                    controller: 'MeczDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                druzyna1: null,
                                druzyna2: null,
                                wynikDruzyna1: null,
                                wynikDruzyna2: null,
                                dataMeczu: null,
                                dataZamkniecia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mecz', null, { reload: true });
                }, function() {
                    $state.go('mecz');
                });
            }]
        })
        .state('mecz.edit', {
            parent: 'mecz',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mecz/mecz-dialog.html',
                    controller: 'MeczDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mecz', function(Mecz) {
                            return Mecz.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mecz', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mecz.delete', {
            parent: 'mecz',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mecz/mecz-delete-dialog.html',
                    controller: 'MeczDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mecz', function(Mecz) {
                            return Mecz.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mecz', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

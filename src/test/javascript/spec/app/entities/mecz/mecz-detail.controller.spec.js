'use strict';

describe('Controller Tests', function() {

    describe('Mecz Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMecz, MockTyp;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMecz = jasmine.createSpy('MockMecz');
            MockTyp = jasmine.createSpy('MockTyp');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Mecz': MockMecz,
                'Typ': MockTyp
            };
            createController = function() {
                $injector.get('$controller')("MeczDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'meczyQApp:meczUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

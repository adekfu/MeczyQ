'use strict';

describe('Controller Tests', function() {

    describe('Typ Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTyp, MockMecz, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTyp = jasmine.createSpy('MockTyp');
            MockMecz = jasmine.createSpy('MockMecz');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Typ': MockTyp,
                'Mecz': MockMecz,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("TypDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'meczyQApp:typUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

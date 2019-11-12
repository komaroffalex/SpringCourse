var app = angular.module('app', ['ngRoute','ngResource']);
app.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/', {
            redirectTo: 'login'
        })
        .when('/login', {
            templateUrl: 'view/loginPage.html',
            controller: 'LoginController',
            controllerAs: 'loginCtrl'
        })
        .when('/administrator', {
            templateUrl: 'view/administratorPage.html',
            controller: 'AdminCtrl',
        })
        .otherwise(
            { redirectTo: '/'}
        );
}]);
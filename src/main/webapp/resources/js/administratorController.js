function AdministratorService($resource) {
    return $resource('rest/operator/:login/:reqType?OrID=:OrID&DrID=:DrID',
        {reqType: '@reqType',  login: '@login', OrID:'@OrID', DrID:'@DrID' });
}

function AdminCtrl($scope, $http, OperatorService, InfoShareService) {
    $scope.login = InfoShareService.getUser();
}

app
    .factory('AdministratorService', AdministratorService)
    .controller('AdminCtrl', AdminCtrl);
function InfoShareService() {
    var user = {};
    return {

        setUser: function (value) {
            user = value;
        },
        getUser: function () {
            return user;
        }
    };
}
function UserService($resource) {
    return $resource('rest/:userType/:login', {userType: '@userType',  login: '@login' });
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function LoginController($scope, $http, UserService, InfoShareService) {
    this.isRegister = false;
    this.signIn = function () {
        if (!$scope.login) {
            alert("Enter login");
        } else if (!$scope.passwd) {
            alert("Enter password");
        } else {
            $http.get('rest/' + $scope.userType + '/' + $scope.login + '/authenticate?passwd=' + $scope.passwd)
                .then(function (response) {
                    if (response.data.toString() === "true") {
                        InfoShareService.setUser($scope.login.toString());
                        window.location.href = '#/' + $scope.userType;
                    } else {
                        alert("Incorrect login or password");
                        $scope.login = "";
                        $scope.passwd = "";
                    }
                }, function (error) {
                    alert("Incorrect login or password");
                    $scope.login = "";
                    $scope.passwd = "";
                });
        }
    };

    this.registerUser = function () {
        if (isEmpty($scope.loginReg)) {
            alert("Enter the login");
        } else if (isEmpty($scope.name)) {
            alert("Enter the name");
        } else if (isEmpty($scope.password1)) {
            alert("Enter the password");
        } else if (isEmpty($scope.surname)) {
            alert("Enter the surname");
        } else if ($scope.password1 !== $scope.password2) {
            alert("Passwords should be equal");
        } else {
            var user = new UserService();
            user.login = $scope.loginReg;
            user.name = $scope.name;
            user.pwd = $scope.password1;
            user.$save({userType: $scope.userTypeReg, login: $scope.loginReg}, function () {
                $scope.loginReg = "";
                $scope.name = "";
                $scope.surname = "";
                $scope.phone = "";
                $scope.email = "";
                $scope.password1 = "";
                $scope.password2 = "";
                this.isRegister = false;
            }.bind(this), function (error) {
                alert(error.data.message);
            });
        }
    }.bind(this);

    this.setRegister = function () {
        this.isRegister = !this.isRegister;
    };

}

app
    .service('InfoShareService', InfoShareService)
    .factory('UserService', UserService)
    .controller('LoginController', LoginController);
var customerApp = angular.module("customerApp", ["ngRoute", "ui.bootstrap", "simplePagination","toastr"]);

customerApp.config(["$routeProvider", function($routeProvider){
    return $routeProvider.when("/",{
        redirectTo: "/customer"
    }).when("/customer",{
        templateUrl: "customer.html"
    })
}]);


customerApp.controller("customerController", function($scope, $http, $window, $location, Pagination,toastr){

    $http.get("http://localhost:8081/initialCustomer").success(function(data){$scope.customer=data;});
    $http.get("http://localhost:8081/initialCustomer").success(function(data){$scope.newCustomer=data;console.log($scope.newCustomer)});
    $http.get("http://localhost:8081/getAllCustomers").success(function (data) {
        $scope.customerList = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.customerList.length / $scope.pagination.perPage);
    });



    $scope.saveCustomer= function(){
            $http.post("http://localhost:8081/addCustomer",$scope.newCustomer).success(function(){
                toastr.success("New customer created");
                $http.get("http://localhost:8081/getAllCustomers").success(function (data) {
                    $scope.customerList = data;
                    $http.get("http://localhost:8081/initialCustomer").success(function(data){
                        $scope.newCustomer=data;});
                })
            })
                .error(function(){
                    toastr.error("Error");
                })
        };





    $scope.showEditModal = function(customerId){
        $http.get("http://localhost:8081/getCustomerById/"+customerId).success(function(data){
            $scope.customer = data;
            $scope.editCustomerModal = true;
        })
    };

    $scope.closeEditModal = function(){
        $scope.editCustomerModal = false;
    };

    $scope.editCustomer = function(customer){
            $http.post("http://localhost:8081/updateCustomer", customer).success(function () {
                $scope.editCustomerModal = false;
                toastr.success('Customer Updated');
                $http.get("http://localhost:8081/getAllCustomers").success(function (data) {
                    $scope.customerList = data;
                })
            })
                .error(function () {
                    toastr.error("Please enter the CORRECT First Name, Last Name and Email");
                })
        };

    $scope.deleteCustomer= function(id) {
        $scope.deleteCustomerID=id;
        $scope.deleteConfirmModal=true;
    };
    $scope.noDeleteModal=function() {
        $scope.deleteConfirmModal=false;
    };

    $scope.yesDeleteModal=function () {
        $http.get("http://localhost:8081/deleteCustomer/"+ $scope.deleteCustomerID).success(function () {
            $http.get("http://localhost:8081/getAllCustomers").success(function (data) {
                $scope.customerList = data;
                $scope.deleteConfirmModal=false;
                toastr.success('Customer Deleted');

            })

        })
    }

});

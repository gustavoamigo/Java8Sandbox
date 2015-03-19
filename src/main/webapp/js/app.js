/*global angular */

angular.module('endereco', ['ngRoute', 'ngMessages'])
    .config(function ($routeProvider) {
        'use strict';
        $routeProvider
                    .when('/', {
                        templateUrl: 'endereco-list.html',
                        controller: function($scope, $http, $location) {

                            this.getAll = function () {
                                $http.get('/Endereco/api/enderecos')
                                    .success((function(data) {
                                        $scope.list = data;
                                    }).bind(this));
                            };

                            $scope.delete = (function (id) {
                                $http.delete('/Endereco/api/enderecos/' + id)
                                        .success((function () {
                                            this.getAll();
                                        }).bind(this));
                                return false;
                            }).bind(this);

                            $scope.title = "List";
                            this.getAll();
                        }
                    })
                    .when('/new',  {
                        templateUrl: 'endereco-edit.html',
                        controller: function($scope, $http, $location) {
                            $scope.title = "New";
                            $scope.item = { id:0 };
                            
                            $scope.submit = function() {
                                $http.post('/Endereco/api/enderecos', $scope.item)
                                    .success((function () {
                                        $location.path("/").replace();
                                    }).bind(this))
                                    .error((function(data,status) {
                                        $scope.enderecoForm.$error.serverMessage = data.message;
                                    }).bind(this));
                            }
                        }
                    })
                    .when('/:id',  {
                        templateUrl: 'endereco-edit.html',
                        controller: function($scope, $http, $routeParams, $location) {
                            $scope.title = "Edit";
                            $http.get('/Endereco/api/enderecos/' + $routeParams.id )
                                .success((function(data) {
                                    $scope.item = data;
                                }).bind(this));
                            $scope.submit = function() {
                                $http.put('/Endereco/api/enderecos/' + $routeParams.id, $scope.item)
                                    .success((function () {
                                        $location.path("/").replace();
                                    }).bind(this))
                                    .error((function(data,status) {
                                        var field = data[0].path ? data[0].path.match(/\.([^.]*)$/)[1] : null;
                                        var msg = field? "Campo '" + field + "' : " + data[0].message : data[0].message;
                                        $scope.enderecoForm.$error.serverMessage = msg;
                                    }).bind(this));;
                            };
                        }
                    })
                    .otherwise({
                        redirectTo: '/'
                    });

    });

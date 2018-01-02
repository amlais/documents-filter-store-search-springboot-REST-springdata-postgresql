'use strict';
var App = angular.module("uploadApp", ['ui.router']);

App.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise('accueil');
	$stateProvider
		.state('accueil',{
			url: '/accueil',
			templateUrl : 'views/accueil.html',
			controller : 'accueilController'
		})
		
		.state('documents',{
			url: '/documents',
			templateUrl : 'views/documents.html',
			controller : 'docController'
		})
		
		.state('contact',{
			url: '/contact',
			templateUrl : 'views/contact.html',
			//controller : 'contactController'
		})});

App.factory('docService', ['$http', '$q', 'urls', function ($http, $q, urls) {

            var factory = {
                saveDoc: saveDoc,
                findDoc: findDoc
            };

            return factory;

            function saveDoc(file) {
                var deferred = $q.defer();
                var formData = new FormData();
                formData.append('file', file);

                $http.post(urls.DOC_URL+'upload', formData,{
                    transformRequest : angular.identity,
                    headers : {
                        'Content-Type' : undefined
                    }})
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            alert(errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            };

            function findDoc(docId) {
                var deferred = $q.defer();
                $http.get(urls.DOC_URL + '/'+docId)
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            alert(errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
			function deleteDoc(docID){
				var deferred = $q.defer();
				$http.put(urls.DOC_URL+'delete/'+docId)
					.then(
					function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            alert(errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
					);
				return deferred.promise;
			}
        }
    ]);
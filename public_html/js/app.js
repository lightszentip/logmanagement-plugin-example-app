var appLog = angular.module("LogManagement", []);

appLog.factory('logManagementService', function($http) {

	var logManagementServiceAPI = {};

	var urlShowLogger = "/logmanagement/showLogger";
	var urlChangeLogger = "/logmanagement/changeLogger";

	logManagementServiceAPI.getAllLogger = function(serverUrl) {
		var url = serverUrl + urlShowLogger + "?all=true";
		return $http.get(url);
	}

	logManagementServiceAPI.getExistLogger = function(serverUrl) {
		var url = serverUrl + urlShowLogger;
		return $http.get(url);
	}
	logManagementServiceAPI.changeLogger = function(serverUrl, logger, logLevel) {
		var url = serverUrl + urlChangeLogger + "?logger="+logger+"&logLevel="+logLevel;
		return $http.post(url);
	}
	return logManagementServiceAPI;
});

appLog.controller("LogController",
		function($scope, $http, logManagementService) {

			var serverUrl = "http://localhost:8080";

			$scope.loggerMap = [];
			$scope.loggerMapAll = [];

			$scope.logLevel = [];

			$scope.logLevels = [ "DEBUG", "TRACE", "INFO", "WARN", "ERROR" ];

			logManagementService.getExistLogger(serverUrl).success(
					function(data, status, headers, config) {
						angular.forEach(data, function(value, key) {
							$scope.logLevel[key] = value;
						});
						$scope.loggerMap = data;
					});
			
			logManagementService.getAllLogger(serverUrl).success(
					function(data, status, headers, config) {
						angular.forEach(data, function(value, key) {
							$scope.logLevel[key] = value;
						});
						$scope.loggerMapAll = data;
					});

			$scope.changeLogger = function(logger, isNew) {
				logManagementService.changeLogger(serverUrl,logger,$scope.logLevel[logger]);
				if(isNew) {
					$scope.loggerMap[logger] = $scope.logLevel[logger];
				}
			}
		});


appLog.controller("UserController",
		function($scope, $http, logManagementService) {
		
	var serverUrl = "http://localhost:8080";
	
	$scope.users = [];
	
	$scope.formInfo = {};
	
    $scope.saveData = function() {
    	$http.post(serverUrl+"/createuser?name="+$scope.formInfo.username).success(function(data, status, headers, config) {
    		$scope.formInfo.username="";
		});
    };
    
    $scope.refresh = function() {
    	$http.get(serverUrl+"/showusers").success(function(data, status, headers, config) {
    		$scope.users = data;
    	});
    };
    
    $scope.remove = function(userId) {
    	$http.delete(serverUrl+"/removeuser?userId="+userId).success(function(data, status, headers, config) {
    		alert(data);
    		$scope.refresh();
    	});
    };
});
module.exports = {
  setEnvironment: function (environment) {
    cordova.exec(null, null, "StoneSDK", "setEnvironment", [environment]);
  },
  device: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "device", []);
  },
  deviceSelected: function (arrayList, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "deviceSelected", [arrayList]);
  },
  transaction: function (amount, method, instalments, success_message, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "transaction", [amount, method, instalments, success_message]);
  },
  transactionCancel: function (transactionCode, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "transactionCancel", [transactionCode]);
  },
  transactionList: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "transactionList", []);
  },
  validation: function (stoneCodeList, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "validation", [stoneCodeList]);
  },
  tablesDownload: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "tablesDownload", []);
  },
  tablesUpdate: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "StoneSDK", "tablesUpdate", []);
  }
};

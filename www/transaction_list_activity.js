module.exports = {
  transactionListActivity: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "TransactionListActivity", "transactionListActivity", []);
  },
  transactionSelected: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "TransactionListActivity", "transactionSelected", []);
  }
};

module.exports = {
  transactionListActivity: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "TransactionListActivity", "transactionListActivity", []);
  }
};

module.exports = {
  transactionClicked: function (btnClicked, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "TransactionItemSelectedActivity", "transactionClicked", [btnClicked]);
  }
};

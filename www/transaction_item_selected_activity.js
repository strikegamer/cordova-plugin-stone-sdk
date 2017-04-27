module.exports = {
  transactionClicked: function (btnClicked, optSelected, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "TransactionItemSelectedActivity", "transactionClicked", [btnClicked, optSelected]);
  }
};

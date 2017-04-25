module.exports = {
    transactionActivity: function (objectStone, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "TransactionActivity", "transactionActivity", [objectStone]);
    }
};

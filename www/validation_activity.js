module.exports = {
    validationActivity: function (stoneCodeList, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ValidationActivity", "validationActivity", [stoneCodeList]);
    }
};

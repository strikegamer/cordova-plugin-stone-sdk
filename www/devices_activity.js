module.exports = {
    devicesActivity: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "DevicesActivity", "devicesActivity", []);
    },
    deviceSelected: function (arrayList, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "DevicesActivity", "deviceSelected", [arrayList]);
    }
};

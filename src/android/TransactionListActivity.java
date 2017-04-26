package br.com.stone.cordova.sdk;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.widget.Toast;

import stone.application.enums.ErrorsEnum;
import stone.application.enums.InstalmentTransactionEnum;
import stone.application.enums.TypeOfTransactionEnum;
import stone.application.interfaces.StoneCallbackInterface;
import stone.providers.LoadTablesProvider;
import stone.providers.TransactionProvider;
import stone.utils.GlobalInformations;
import stone.utils.Stone;
import stone.utils.StoneTransaction;

public class TransactionListActivity extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("transactionListActivity")) {


            return true;
        } else {
            return false;
        }
    }

}

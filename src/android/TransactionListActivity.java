package br.com.stone.cordova.sdk;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stone.application.interfaces.StoneCallbackInterface;
import stone.database.transaction.TransactionDAO;
import stone.database.transaction.TransactionObject;
import stone.providers.CancellationProvider;
import stone.providers.PrintProvider;
import stone.utils.GlobalInformations;
import stone.utils.PrintObject;

public class TransactionListActivity extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("transactionListActivity")) {

            // acessa todas as transacoes do banco de dados
            TransactionDAO transactionDAO = new TransactionDAO(TransactionListActivity.this.cordova.getActivity());

            // cria uma lista com todas as transacoes
            List<TransactionObject> transactionObjects = transactionDAO.getAllTransactionsOrderByIdDesc();

            // exibe todas as transações (neste caso valor e status) para o usuario
            JSONArray arrayList = new JSONArray();

            for (TransactionObject list : transactionObjects) {
                String id = String.valueOf(list.getIdFromBase());
                String amount = list.getAmount();
                String status = String.valueOf(list.getTransactionStatus());

                arrayList.put(id + "_" + amount + "_" + status);

            }
            callbackContext.success(arrayList);
            System.out.println("arrayList: " + arrayList);

            return true;
        } else if(action.equals("transactionClicked")) {

            String btnClicked = data.getString(0);
            System.out.println("btnClicked: " + btnClicked);

            return true;
        } else {
            return false;
        }
    }


}

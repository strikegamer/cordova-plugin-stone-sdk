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

public class TransactionActivity extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("transactionActivity")) {

            String object = data.getString(0);

            String objectStoneTransaction = data.getJSONObject(0).getString("amount");

            // Cria o objeto de transacao. Usar o "GlobalInformations.getPinpadFromListAt"
            // significa que devera estar conectado com ao menos um pinpad, pois o metodo
            // cria uma lista de conectados e conecta com quem estiver na posicao "0".
            StoneTransaction stoneTransaction = new StoneTransaction(Stone.getPinpadFromListAt(0));

            // A seguir deve-se popular o objeto.
            stoneTransaction.setAmount(objectStoneTransaction);
            stoneTransaction.setEmailClient(null);
            stoneTransaction.setRequestId(null);
            stoneTransaction.setUserModel(GlobalInformations.getUserModel(0));

            // Numero de parcelas
            String numberStoneTransaction = data.getJSONObject(0).getString("method");

            // Verifica a forma de pagamento selecionada.
            String methodStoneTransaction = data.getJSONObject(0).getString("instalments");

            if (methodStoneTransaction.equals("DEBIT")) {
                stoneTransaction.setTypeOfTransaction(TypeOfTransactionEnum.DEBIT);
            } else if (methodStoneTransaction.equals("CREDIT")) {
                // Informa a quantidade de parcelas.
                stoneTransaction.setInstalmentTransactionEnum(InstalmentTransactionEnum.valueOf(numberStoneTransaction));
                stoneTransaction.setTypeOfTransaction(TypeOfTransactionEnum.CREDIT);
            } else {
                System.out.println("Empty Payment Method");
            }

            // Processo para envio da transacao.
            final TransactionProvider provider = new TransactionProvider(TransactionActivity.this.cordova.getActivity(), stoneTransaction, GlobalInformations.getPinpadFromListAt(0));
            provider.setWorkInBackground(false);
            provider.setDialogMessage("Enviando..");
            provider.setDialogTitle("Aguarde");

            provider.setConnectionCallback(new StoneCallbackInterface() {
                public void onSuccess() {
                    Toast.makeText(TransactionActivity.this.cordova.getActivity(), "Transação enviada com sucesso e salva no banco. Para acessar, use o TransactionDAO.", Toast.LENGTH_SHORT).show();
                }

                public void onError() {
                    Toast.makeText(TransactionActivity.this.cordova.getActivity(), "Erro na transação", Toast.LENGTH_SHORT).show();
                    if (provider.theListHasError(ErrorsEnum.NEED_LOAD_TABLES) == true) { // code 20
                        LoadTablesProvider loadTablesProvider = new LoadTablesProvider(TransactionActivity.this.cordova.getActivity(), provider.getGcrRequestCommand(), GlobalInformations.getPinpadFromListAt(0));
                        loadTablesProvider.setDialogMessage("Subindo as tabelas");
                        loadTablesProvider.setWorkInBackground(false); // para dar feedback ao usuario ou nao.
                        loadTablesProvider.setConnectionCallback(new StoneCallbackInterface() {
                            public void onSuccess() {
                                Toast.makeText(TransactionActivity.this.cordova.getActivity(), "Sucesso.", Toast.LENGTH_SHORT).show();
                            }

                            public void onError() {
                                Toast.makeText(TransactionActivity.this.cordova.getActivity(), "Erro.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        loadTablesProvider.execute();
                    }
                }
            });

            provider.execute();

            return true;
        } else {
            return false;
        }
    }

}

package br.com.stone.cordova.sdk;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stone.application.interfaces.StoneCallbackInterface;
import stone.providers.CancellationProvider;
import stone.providers.PrintProvider;
import stone.utils.GlobalInformations;
import stone.utils.PrintObject;
import stone.utils.Stone;

public class TransactionItemSelectedActivity extends CordovaPlugin {

    @Override
        public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
            if (action.equals("transactionClicked")) {

                String btnClicked = data.getString(0);
                System.out.println("btnClicked: " + btnClicked);

                String optSelected = data.getString(1);
                System.out.println("optSelected: " + optSelected);

                if(btnClicked.equals("CANCEL")) {
                    System.out.println("Opção Selecionada Cancel");

                    // Pega o id da transação selecionada.
                    String[] parts = optSelected.split("_");

                    String idOptSelected = parts[0];
                    System.out.println("idOptSelected: " + idOptSelected);

                    //lógica do cancelamento
                    final int transacionId = 7;

                    final CancellationProvider cancellationProvider = new CancellationProvider(TransactionItemSelectedActivity.this.cordova.getActivity(), transacionId, Stone.getUserModel(0));
                    cancellationProvider.setWorkInBackground(false); // para dar feedback ao usuario ou nao.
                    //cancellationProvider.setDialogMessage("Cancelando...");
                    cancellationProvider.setConnectionCallback(new StoneCallbackInterface() { // chamada de retorno.
                        public void onSuccess() {
                            Toast.makeText(TransactionItemSelectedActivity.this.cordova.getActivity(), cancellationProvider.getMessageFromAuthorize(), Toast.LENGTH_SHORT).show();
                        }

                        public void onError() {
                            Toast.makeText(TransactionItemSelectedActivity.this.cordova.getActivity(), "Um erro ocorreu durante o cancelamento com a transacao de id: " + transacionId, Toast.LENGTH_SHORT).show();
                        }
                    });
                    cancellationProvider.execute();
                } else {
                    Toast.makeText(TransactionItemSelectedActivity.this.cordova.getActivity(), "Ocorreu um erro ", Toast.LENGTH_SHORT).show();
                }

                return true;
            } else {
                return false;
            }
        }

}

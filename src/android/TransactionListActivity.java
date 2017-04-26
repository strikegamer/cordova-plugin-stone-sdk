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
        } else if(action.equals("transactionListClickActivity")) {

            String btnClicked = data.getString(0);
            System.out.println("Test: " + btnClicked);

            if(btnClicked.equals("PRINT")) {
                try {
                    // lógica da impressão
                    List<PrintObject> listToPrint = new ArrayList<PrintObject>();
                    for (int i = 0; i < 10; i++) {
                        listToPrint.add(new PrintObject("Teste de impressão linha " + i, PrintObject.MEDIUM, PrintObject.CENTER));
                    }
                    // GlobalInformations.getPinpadFromListAt(0) eh o pinpad conectado, que esta na posicao zero.
                    final PrintProvider printProvider = new PrintProvider(TransactionListActivity.this.cordova.getActivity(), listToPrint, GlobalInformations.getPinpadFromListAt(0));
                    printProvider.setWorkInBackground(false);
                    printProvider.setDialogMessage("Imprimindo...");
                    printProvider.setConnectionCallback(new StoneCallbackInterface() {
                        public void onSuccess() {
                            Toast.makeText(TransactionListActivity.this.cordova.getActivity(), "Impressão realizada com sucesso", Toast.LENGTH_SHORT).show();
                        }

                        public void onError() {
                            Toast.makeText(TransactionListActivity.this.cordova.getActivity(), "Um erro ocorreu durante a impressão", Toast.LENGTH_SHORT).show();
                        }
                    });
                    printProvider.execute();
                } catch (IndexOutOfBoundsException outException) {
                    Toast.makeText(TransactionListActivity.this.cordova.getActivity(), "Conecte-se a um pinpad.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TransactionListActivity.this.cordova.getActivity(), "Houve um erro inesperado. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else if(btnClicked.equals("CANCEL")) {
                // lógica do cancelamento
                String arrayIdAtPositionZero = btnClicked;
                final int transacionId = Integer.parseInt(arrayIdAtPositionZero);

                final CancellationProvider cancellationProvider = new CancellationProvider(TransactionListActivity.this.cordova.getActivity(), transacionId, GlobalInformations.getUserModel(0));
                cancellationProvider.setWorkInBackground(false); // para dar feedback ao usuario ou nao.
                cancellationProvider.setDialogMessage("Cancelando...");
                cancellationProvider.setConnectionCallback(new StoneCallbackInterface() { // chamada de retorno.
                    public void onSuccess() {
                        Toast.makeText(TransactionListActivity.this.cordova.getActivity(), cancellationProvider.getMessageFromAuthorize(), Toast.LENGTH_SHORT).show();
                    }

                    public void onError() {
                        Toast.makeText(TransactionListActivity.this.cordova.getActivity(), "Um erro ocorreu durante o cancelamento com a transacao de id: " + transacionId, Toast.LENGTH_SHORT).show();
                    }
                });
                cancellationProvider.execute();
            } else {
                Toast.makeText(TransactionListActivity.this.cordova.getActivity(), "Ocorreu um erro ", Toast.LENGTH_SHORT).show();
            }

            return true;
        } else {
            return false;
        }
    }

}

package com.stone.sdk;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stone.application.StoneStart;
import stone.application.interfaces.StoneCallbackInterface;
import stone.providers.ActiveApplicationProvider;
import stone.user.UserModel;

public class ValidationActivity extends CordovaPlugin {

    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        /* Este deve ser, obrigatoriamente, o primeiro metodo
         * a ser chamado. E um metodo que trabalha com sessao.
         */
        List<UserModel> user = StoneStart.init(this.cordova.getActivity());

        // Se retornar nulo, voce provavelmente nao ativou a SDK ou as informacoes da Stone SDK foram excluidas.

        if (action.equals("validationActivity")  && user == null)  {

            List<String> stoneCodeList = new ArrayList<String>();

            // Adicione seu Stonecode abaixo, como string.
            stoneCodeList.add(data.getString(0)); // coloque seu Stone Code aqui

            final ActiveApplicationProvider activeApplicationProvider = new ActiveApplicationProvider(this.cordova.getActivity(), stoneCodeList);
            activeApplicationProvider.setDialogMessage("Ativando o aplicativo...");
            activeApplicationProvider.setDialogTitle("Aguarde");
            activeApplicationProvider.setActivity(this.cordova.getActivity());
            activeApplicationProvider.setWorkInBackground(false); // informa se este provider ira rodar em background ou nao
            activeApplicationProvider.setConnectionCallback(new StoneCallbackInterface() {

                /* Sempre que utilizar um provider, intancie esta Interface.
                 * Ela ira lhe informar se o provider foi executado com sucesso ou nao
                 */

                /* Metodo chamado se for executado sem erros */
                public void onSuccess() {
                    Toast.makeText(ValidationActivity.this.cordova.getActivity(), "Ativado com sucesso, iniciando o aplicativo", Toast.LENGTH_SHORT).show();
                }

                /* Metodo chamado caso ocorra alguma excecao */
                public void onError() {
                    Toast.makeText(ValidationActivity.this.cordova.getActivity(), "Erro na ativacao do aplicativo, verifique a lista de erros do provider", Toast.LENGTH_SHORT).show();
                }

            });
            activeApplicationProvider.execute();

            return true;
        } else if (action.equals("validationActivity")) {
            Toast.makeText(ValidationActivity.this.cordova.getActivity(), "StoneCode já cadastrado", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(ValidationActivity.this.cordova.getActivity(), "Erro", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}

package com.stone.sdk;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import java.util.Set;

import stone.application.interfaces.StoneCallbackInterface;
import stone.providers.BluetoothConnectionProvider;
import stone.utils.PinpadObject;

public class DevicesActivity extends CordovaPlugin {

    static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("devicesActivity")) {

            turnBluetoothOn();

            // Lista de Pinpads para passar para o BluetoothConnectionProvider.
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            JSONArray arrayList = new JSONArray();

            // Lista todos os dispositivos pareados.
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String name = device.getName();
                    String address = device.getAddress();
                    arrayList.put(name + "_" + address);
                }
                callbackContext.success(arrayList);
            }

            return true;
        } else if (action.equals("deviceSelected")) {

            // Pega o pinpad selecionado.
            String arrayList = data.getString(0);

            String[] parts = arrayList.split("_");

            String name = parts[0];
            String macAddress = parts[1];

            PinpadObject pinpad = new PinpadObject(name, macAddress, false);

            // Passa o pinpad selecionado para o provider de conexão bluetooth.
            BluetoothConnectionProvider bluetoothConnectionProvider = new BluetoothConnectionProvider(DevicesActivity.this.cordova.getActivity(), pinpad);
            bluetoothConnectionProvider.setDialogMessage("Criando conexao com o pinpad selecionado"); // Mensagem exibida do dialog.
            bluetoothConnectionProvider.setWorkInBackground(false); // Informa que haverá um feedback para o usuário.
            bluetoothConnectionProvider.setConnectionCallback(new StoneCallbackInterface() {

                public void onSuccess() {
                    Toast.makeText(DevicesActivity.this.cordova.getActivity(), "Pinpad conectado", Toast.LENGTH_SHORT).show();
                }

                public void onError() {
                    Toast.makeText(DevicesActivity.this.cordova.getActivity(), "Erro durante a conexao. Verifique a lista de erros do provider para mais informacoes", Toast.LENGTH_SHORT).show();
                }

            });
            bluetoothConnectionProvider.execute(); // Executa o provider de conexão bluetooth.

            return true;
        } else {
            return false;
        }

    }

    public void turnBluetoothOn() {
        try {
            mBluetoothAdapter.enable();
            do {
            } while (!mBluetoothAdapter.isEnabled());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.us.skyguardian.translock.BLE;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.us.skyguardian.translock.DB.AppDatabase;
import com.us.skyguardian.translock.DB.Historial.Historial;
import com.us.skyguardian.translock.DB.Historial.HistorialDao;
import com.us.skyguardian.translock.DB.Permiso.Permiso;
import com.us.skyguardian.translock.DB.Permiso.PermisoDao;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.LoginActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class BluetoothLeServices extends Service {

    /*****************************************
     *  CADENAS PARA ABRIR / CERRAR CANDADOS *
     *****************************************/
    public static final byte[] cadenajointech ={
            (byte)0xAA,(byte)0x0A,(byte)0x65,(byte)0x02,
            (byte)0x01,(byte)0x38,(byte)0x38,(byte)0x38,
            (byte)0x38,(byte)0x38,(byte)0x38,(byte)0x6C,
            (byte)0xAA
    };

    public static final byte[] cadenaharborlock1 ={
            (byte)0xF1,(byte)0x1F,(byte)0xFF,(byte)0xFF,
            (byte)0x03,(byte)0x00,(byte)0x00,(byte)0x0F,
            (byte)0x31,(byte)0x32,(byte)0x33,(byte)0x34,
            (byte)0x35,(byte)0x36,(byte)0x31,(byte)0x32
    };
    public  static final byte[] cadenaharborlock2 = {
            (byte)0x33,(byte)0x34,(byte)0x31,(byte)0x32,
            (byte)0x33,(byte)0x34,(byte)0x30,(byte)0xE7,
            (byte)0xF2,(byte)0x2F
    };

    public static final byte[] cadenaopentranslock = "PPON".getBytes();
    public static final byte[] cadenaclosetranslock = "POFF".getBytes();

    /***********************************
     *  UUIDs Y SERVICIOS DE CANDADOS  *
     **********************************/

    public final static String UUID_TRANSLOCK = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public final static String SERVICE_TRANSLOCK = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public final static String UUID_JOINTECH = "0000fff0-0000-1000-8000-00805f9b34fb";
    public final static String SERVICE_JOINTECH = "0000fff2-0000-1000-8000-00805f9b34fb";
    public final static String UUID_HARBORLOCK = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String SERVICE_HARBORLOCK = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";

    /**********************************
    *           ATRIBUTOS             *
     *********************************/
    private final static String TAG = "BLE";
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 2;
    public static final int APERTURA = 1;
    public static final int CIERRE = 3;
    private int idEvento = APERTURA;
    private int connectionState = STATE_DISCONNECTED;
    private boolean autoconnect = false;
    private boolean serviciosListos = false;
    private  Activity activity;
    private TextView estado;
    private int idCandado;
    private Button boton;
    private Handler handler;
    private boolean online;
    AppDatabase appDatabase;
    int userId;

    public BluetoothGattCallback getGattCallback(){
        return gattCallback;
    }
    // Various callback methods defined by the BLE API.


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public BluetoothLeServices (Fragment fragment, TextView estado, Button boton, int idCandado, boolean online) {
        this.activity = fragment.getActivity();
        this.estado = estado;
        this.boton = boton;
        handler = new Handler();
        this.idCandado = idCandado;
        this.online = online;

    }

    public BluetoothLeServices (Fragment fragment, TextView estado, Button boton, int idCandado, boolean online, AppDatabase appDatabase, int userId) {
        this.activity = fragment.getActivity();
        this.estado = estado;
        this.boton = boton;
        handler = new Handler();
        this.idCandado = idCandado;
        this.online = online;
        this.appDatabase = appDatabase;
        this.userId = userId;

    }

    public int getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(int connectionState) {
        this.connectionState = connectionState;
    }

    public boolean isAutoconnect() {
        return autoconnect;
    }

    public void setAutoconnect(boolean autoconnect) {
        this.autoconnect = autoconnect;
    }

    public boolean isServiciosListos() {
        return serviciosListos;
    }

    public void setServiciosListos(boolean serviciosListos) {
        this.serviciosListos = serviciosListos;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("BLE", "ESTATUS "+ newState);
            //Log.i("BLE", "1234");

            if (newState == BluetoothProfile.STATE_CONNECTED) {

                connectionState = STATE_CONNECTED;
                gatt.discoverServices();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                connectionState = STATE_DISCONNECTED;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        estado.setText("Desconectado\n Reconexión automática");
                        estado.setTextColor(Color.RED);
                        boton.setText("RECONECTANDO");
                    }
                });
                Log.i(TAG, "Disconnected from GATT server.");

                if(autoconnect) {
                    gatt.connect();
                } else {
                    gatt.close();
                }
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                Log.i(TAG, "Connecting GATT server.");
            } else {
                Log.i(TAG, "FALLO TOTAL.");
            }
        }

        @Override
        // New services discovered
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i("BLE", "entramos al callbakc servicediscovered");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                serviciosListos = true;
                Log.i("BLE", "success " + gatt.getDevice().getName());
                //broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        estado.setText("Conectado");
                        estado.setTextColor(Color.GREEN);
                        boton.setText("ABRIR");
                    }
                });
                Log.i(TAG, "Service discovered: " + gatt.getServices().toString());
            } else {
                serviciosListos = false;
                Log.i(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        // Result of a characteristic read operation
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("BLE", "LEEEMOS CHARACTERISTICAS");
            //Toast.makeText(getBaseContext(), "EN EL CALLBACK characteristics", Toast.LENGTH_LONG).show();
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "CHAR LEIDA: "+ new String (characteristic.getValue()));
                //broadcastUpdate(ACTION_GATT_CHARACTERISTICS_SUCCESS);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i("BLE", "characteristicas escritas " + status);
                if (online) {
                    String url = activity.getString(R.string.ip) + "skylock/historial/registrar/" + idCandado + "/" + idEvento;

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.i("BLE", "characteristicas escritas  0 GAIN");
                                    try {
                                        if (response.has("success")) {

                                            //Toast.makeText(activity, "APERTURA REGISTRADA", Toast.LENGTH_LONG).show();
                                            Log.i("BLE", "HISTORIAL APERTURA REGSITRADA");
                                        } else if (response.has("error")) {
                                            //Toast.makeText(activity, "ERROR AL REGISTRAR APERTURA", Toast.LENGTH_LONG).show();
                                            Log.i("BLE", response.getString("error"));
                                        }
                                    } catch (Exception e) {
                                        //Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
                                        Log.i("BLE", e.toString());
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("BLE", error.getMessage() + "aqui");
                                }
                            }
                    );
                    SingletonRequestQueue.getInstance(activity.getApplicationContext()).addToRequestQueue(request);
                } else {
                    Historial historial = new Historial();
                    historial.idCandado = idCandado;
                    historial.idEvento = idEvento;
                    String pattern = "yyyy-dd-MM HH:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String date = simpleDateFormat.format(new Date());
                    historial.fecha = date;
                    historial.idUsuario = userId;
                    new SetHistorial(appDatabase.historialDao()).execute(historial);
                }
            } else {
                Log.i("BLE", "error al abrir " + status);
            }
        }
    };

    public void writeCustomCharacteristic(BluetoothGatt gatt, BluetoothGattCharacteristic mWriteCharacteristic, byte[] value) {

        mWriteCharacteristic.setValue(value);
        if(gatt.writeCharacteristic(mWriteCharacteristic) == false){
            Log.i("BLE", "Failed to write characteristic");
        } else {
            Log.i("BLE", "SE MANDOOOOOOO!!!!");
        }
    }

    public void abrirCandado(BluetoothGatt gatt, String uuid, String service, byte[] values) {
        BluetoothGattService myserv = gatt.getService(UUID.fromString(uuid));
        writeCustomCharacteristic(gatt, myserv.getCharacteristic(UUID.fromString(service)), values);
        if (values == cadenaclosetranslock) {
            Toast.makeText(activity, "CERRANDO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "ABRIENDO", Toast.LENGTH_LONG).show();
        }
        Log.i("BLE", "my servicio" + myserv.getCharacteristic(UUID.fromString(SERVICE_JOINTECH)) + "***" + myserv.getUuid().toString());

    }

    public void abrirCandado(final BluetoothGatt gatt, String uuid, final String service, byte[] values, final byte[] values2) {
        final BluetoothGattService myserv = gatt.getService(UUID.fromString(uuid));
        writeCustomCharacteristic(gatt, myserv.getCharacteristic(UUID.fromString(service)), values);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                writeCustomCharacteristic(gatt, myserv.getCharacteristic(UUID.fromString(service)), values2);
            }
        }, 100);

        Toast.makeText(activity, "ABRIENDO", Toast.LENGTH_LONG).show();
    }

    private static class SetHistorial extends AsyncTask<Historial, Void, Void> {

        HistorialDao historialDao;

        public SetHistorial(HistorialDao historialDao) {

            this.historialDao = historialDao;
        }

        @Override
        protected Void doInBackground(Historial... historials) {
            historialDao.insert(historials[0]);
            return null;
        }
    }


}

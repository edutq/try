package com.us.skyguardian.translock.main.candados.candadoView;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.BLE.BluetoothLeServices;
import com.us.skyguardian.translock.BLE.LeDeviceListAdapter;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.usuarioHorarioVG;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/*

*/

/**
 * A simple {@link Fragment} subclass.
 */
public class candadoViewFragment extends Fragment {

    AppBarConfiguration appBarConfiguration;
    CandadoViewViewModel candadoViewViewModel;
    Button abrir;
    Button cerrar;
    TextView nombre;
    TextView identificador;
    LoadingDialog loadingDialog;


    private BluetoothAdapter bluetoothAdapter;
    private boolean mScanning = false;
    private Handler handler;
    BluetoothLeServices bluetoothLeServices;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 5000;
    private LeDeviceListAdapter leDeviceListAdapter;
    TextView statusCandado;
    ArrayList<usuarioHorarioVG> listaPermisos;
    BluetoothGatt gatt;
    BluetoothManager bluetoothManager;
    Usuario usuario;
    CandadoViewAdapter adapter;
    RecyclerView permisos;
    CardView tarjetaPermisos;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("BLE", "\nCREANDO\n");
        // Inflate the layout for this fragment
        usuario = Usuario.getUser();
        View root = inflater.inflate(R.layout.fragment_candadoview, container, false);
        candadoViewViewModel = ViewModelProviders.of(getActivity()).get(CandadoViewViewModel.class);
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_candado_host_fragment);
        statusCandado = root.findViewById(R.id.estado_candado);
        leDeviceListAdapter = new LeDeviceListAdapter();
        leDeviceListAdapter.clear();

        tarjetaPermisos = root.findViewById(R.id.tarjeta_permisos_candado);
        permisos = root.findViewById(R.id.lista_permisos_candado);
        permisos.setLayoutManager(new LinearLayoutManager(getContext()));

        abrir = root.findViewById(R.id.button_abrir_candado);
        cerrar = root.findViewById(R.id.button_cerrar_candado);
        bluetoothLeServices = new BluetoothLeServices(this, statusCandado, abrir, candadoViewViewModel.getIdCandado().getValue(), true);
        nombre = root.findViewById(R.id.nombre_vista_candado);
        identificador = root.findViewById(R.id.identificador);


        final String marca = candadoViewViewModel.getMarca().getValue();
        if (marca.equals(getString(R.string.TRANSLOCK))) {
            cerrar.setVisibility(View.VISIBLE);
            cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bluetoothLeServices.setIdEvento(BluetoothLeServices.CIERRE);
                    if(leDeviceListAdapter.getCount() == 1) {
                        if (bluetoothLeServices.getConnectionState() != 2) {
                            Toast.makeText(getActivity(), "NO ESTAS CONECTADO AL CANDADO", Toast.LENGTH_LONG).show();
                        } else {

                            if (bluetoothLeServices.getConnectionState() == BluetoothProfile.STATE_CONNECTED) {
                                if (bluetoothLeServices.isServiciosListos()) {

                                    bluetoothLeServices.abrirCandado(gatt,
                                            BluetoothLeServices.UUID_TRANSLOCK,
                                            BluetoothLeServices.SERVICE_TRANSLOCK,
                                            BluetoothLeServices.cadenaclosetranslock);

                                }
                            }
                        }
                    } else {
                        if (!mScanning) {
                            scanLeDevice(true);
                        }
                    }
                }
            });
        }
        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tienePermiso()) {
                    bluetoothLeServices.setIdEvento(BluetoothLeServices.APERTURA);
                    if(leDeviceListAdapter.getCount() == 1) {
                        if (bluetoothLeServices.getConnectionState() != 2) {
                            Toast.makeText(getActivity(), "NO ESTAS CONECTADO AL CANDADO", Toast.LENGTH_LONG).show();
                        } else {

                            if (bluetoothLeServices.getConnectionState() == BluetoothProfile.STATE_CONNECTED) {
                                if (bluetoothLeServices.isServiciosListos()) {
                                    if (marca.equals(getString(R.string.JOINTECH))) {
                                        bluetoothLeServices.abrirCandado(gatt,
                                                BluetoothLeServices.UUID_JOINTECH,
                                                BluetoothLeServices.SERVICE_JOINTECH,
                                                BluetoothLeServices.cadenajointech);
                                    } else if (marca.equals(getString(R.string.HARBORLOCK))) {
                                        bluetoothLeServices.abrirCandado(gatt,
                                                BluetoothLeServices.UUID_HARBORLOCK,
                                                BluetoothLeServices.SERVICE_HARBORLOCK,
                                                BluetoothLeServices.cadenaharborlock1,
                                                BluetoothLeServices.cadenaharborlock2);
                                    } else if (marca.equals(getString(R.string.TRANSLOCK))) {
                                        bluetoothLeServices.abrirCandado(gatt,
                                                BluetoothLeServices.UUID_TRANSLOCK,
                                                BluetoothLeServices.SERVICE_TRANSLOCK,
                                                BluetoothLeServices.cadenaopentranslock);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        if (!mScanning) {
                            scanLeDevice(true);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "NO TIENES PERMISO PARA ABRIR EL CANDADO", Toast.LENGTH_LONG).show();
                }

            }
        });

        candadoViewViewModel.getNombreCandado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                nombre.setText(s);
            }
        });
        candadoViewViewModel.getIdentificador().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                identificador.setText(s);
            }
        });

        //Toast.makeText(getActivity(), candadoViewViewModel.getImei().getValue(), Toast.LENGTH_LONG).show();


        handler = new Handler();



        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (!candadoViewViewModel.getAdmin().getValue()) {
            tarjetaPermisos.setVisibility(View.VISIBLE);
            String url = getString(R.string.ip)+"skylock/candado_usuario/ver_permisos/"+
                    usuario.getId()+"/"+
                    candadoViewViewModel.getIdCandado().getValue();

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject horario = response.getJSONObject(i);
                                    Log.i("BLE", horario.getString("id"));
                                    listaPermisos.add(new usuarioHorarioVG(horario.getInt("id"),
                                            horario.getString("fecha_inicio"),
                                            horario.getString("hora_inicio"),
                                            horario.getString("fecha_fin"),
                                            horario.getString("hora_fin")));

                                }

                                Log.i("BLE", listaPermisos.size()+" size lista");
                                adapter = new CandadoViewAdapter(listaPermisos);
                                permisos.setAdapter(adapter);

                                loadingDialog.isLoading(false);

                            } catch (Exception e) {
                                loadingDialog.isLoading(false);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingDialog.isLoading(false);
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );
            loadingDialog.isLoading(true);
            SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        }
        listaPermisos = new ArrayList<usuarioHorarioVG>();


        return root;

    }

    public boolean tienePermiso() {
        if (candadoViewViewModel.getAdmin().getValue()) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            for (usuarioHorarioVG horario : listaPermisos) {
                Date inicio = sdf.parse(horario.getFecha_inicio()+ " " + horario.getHora_inicio());
                Date fin = sdf.parse(horario.getFecha_fin() + " " + horario.getHora_fin());
                Date hoy = new Date();
                if (inicio.getTime() <= hoy.getTime() && hoy.getTime() <= fin.getTime()) {
                    return true;
                }
            }
        }
        catch (ParseException ex) {
            return false;
        }


        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("BLE", "\nPAUSANDO\n");
        handler.removeCallbacks(waitforBLE);
        if (mScanning) {
            scanLeDevice(false);
        }
        //bluetoothAdapter.disable();

        //bluetoothAdapter = null;
        bluetoothLeServices.setServiciosListos(false);
        bluetoothLeServices.setAutoconnect(false);
        leDeviceListAdapter.clear();

        if (gatt != null) {

            gatt.disconnect();
            //gatt.close();
            gatt = null;

        }

        //leDeviceListAdapter.clear();

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i("BLE", "\nRESUMIENDO\n");


        enableBt();
        if (gatt != null) {
            Log.i("BLE", "AQUI ESTAMOS");
        }

        if (leDeviceListAdapter.getCount() != 1) {
            if (bluetoothAdapter != null ){
                if (bluetoothAdapter.isEnabled()) {
                    scanLeDevice(true);
                }
            }


        }

    }



    // Device scan callback.
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan (final BluetoothDevice device, int rssi, byte[] scanRecord) {
            //Log.i("BLE", "CALL BACK CALLED");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Log.i("BLE", device.toString());
                if (device.getAddress() != null) {

                    if(device.getAddress().equals(candadoViewViewModel.getImei().getValue())) {
                        //Log.i("BLE", "EL ADDRESS DEL APARATO "+device.getAddress());
                        if (mScanning) {
                            scanLeDevice(false);
                        }
                        if(!leDeviceListAdapter.getList().contains(device)) {
                            if (leDeviceListAdapter.getList().size() > 0) {
                                leDeviceListAdapter.clear();
                            }
                            //Log.i("BLE", bluetoothAdapter.getState()+"");
                            //Log.i("BLE", bluetoothAdapter.isEnabled()+"");
                            leDeviceListAdapter.addDevice(device);
                            bluetoothLeServices.setAutoconnect(true);
                            gatt = device.connectGatt(getActivity(), true, bluetoothLeServices.getGattCallback());

                        }


                        leDeviceListAdapter.notifyDataSetChanged();
                    }
                }
                }
            });
        }

    };

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mScanning = true;
            statusCandado.setText("Buscando...");
            statusCandado.setTextColor(Color.BLUE);

            handler.postDelayed(waitforBLE, SCAN_PERIOD);
            bluetoothAdapter.startDiscovery();
            Log.i("BLE", "ESCANEANDO "+bluetoothAdapter.startLeScan(leScanCallback));
            //bluetoothAdapter.startLeScan(leScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.cancelDiscovery();
            bluetoothAdapter.stopLeScan(leScanCallback);
        }

    }

    public Runnable waitforBLE = new Runnable() {
        @Override
        public void run() {
            if(mScanning && leDeviceListAdapter.getCount()!=1 && bluetoothLeServices.getConnectionState() == BluetoothProfile.STATE_DISCONNECTED) {
                mScanning = false;
                bluetoothAdapter.stopLeScan(leScanCallback);
                statusCandado.setTextColor(Color.RED);
                statusCandado.setText("Desconectado\n El dispositvo no está en rango");
                //Log.i("BLE", leDeviceListAdapter.getList().toString());
               // Log.i("BLE", bluetoothAdapter.getState()+"");
                //Log.i("BLE", bluetoothAdapter.isEnabled()+"");
                //Log.i("BLE", gatt.toString());
                abrir.setText("Conectar");


            } else {
                if (leDeviceListAdapter.getCount()==1) {
                    BluetoothDevice device = leDeviceListAdapter.getDevice(0);
                    if (bluetoothLeServices.getConnectionState() == BluetoothProfile.STATE_CONNECTED) {

                        Log.i("BLE", "CONECTADO");
                        for (BluetoothGattService s : gatt.getServices())
                        {
                            //Log.i("BLE", s.getUuid().toString());
                        }
                    } else {
                        Log.i("BLE", "NO "+ bluetoothManager.getConnectionState(device, BluetoothProfile.GATT));

                    }
                }
            }
        }
    };

    public void enableBt(){
        if (bluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

}

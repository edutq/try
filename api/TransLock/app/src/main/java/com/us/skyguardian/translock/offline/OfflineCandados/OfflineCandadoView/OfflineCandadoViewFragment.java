package com.us.skyguardian.translock.offline.OfflineCandados.OfflineCandadoView;


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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.us.skyguardian.translock.BLE.BluetoothLeServices;
import com.us.skyguardian.translock.BLE.LeDeviceListAdapter;
import com.us.skyguardian.translock.DB.Permiso.Permiso;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.main.candados.candadoView.CandadoViewViewModel;
import com.us.skyguardian.translock.offline.OfflineViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineCandadoViewFragment extends Fragment {

    CandadoViewViewModel candadoViewViewModel;
    OfflineViewModel offlineViewModel;
    OfflinePermisosAdapter offlinePermisosAdapter;
    TextView nombre;
    TextView identificador;
    RecyclerView permisos;
    Button abrir;
    Button cerrar;
    ArrayList<Permiso> listaFiltrada;
    TextView statusCandado;
    /*****************
     * BLuetooth
     ****************/

    BluetoothLeServices bluetoothLeServices;
    private boolean mScanning = false;
    private Handler handler;
    BluetoothGatt gatt;
    BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private static final long SCAN_PERIOD = 5000;
    private LeDeviceListAdapter leDeviceListAdapter;


    public OfflineCandadoViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        candadoViewViewModel = ViewModelProviders.of(getActivity()).get(CandadoViewViewModel.class);
        offlineViewModel = ViewModelProviders.of(getActivity()).get(OfflineViewModel.class);
        View view = inflater.inflate(R.layout.fragment_offline_candado_view, container, false);
        CardView tarjetaPermisos = view.findViewById(R.id.tarjeta_permisos_candado_offline);
        listaFiltrada = new ArrayList<>();
        statusCandado = view.findViewById(R.id.estado_candado_offline);
        abrir = view.findViewById(R.id.button_abrir_candado_offline);
        cerrar = view.findViewById(R.id.button_cerrar_candado_offline);
        bluetoothLeServices = new BluetoothLeServices(this, statusCandado, abrir, candadoViewViewModel.getIdCandado().getValue(), false, offlineViewModel.getAppDatabase().getValue(), offlineViewModel.getUserId().getValue());

        leDeviceListAdapter = new LeDeviceListAdapter();
        leDeviceListAdapter.clear();



        if (candadoViewViewModel.getMarca().getValue().equals("3")) {
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
                    Toast.makeText(getActivity(), "TIENES PERMISO", Toast.LENGTH_LONG).show();
                    bluetoothLeServices.setIdEvento(BluetoothLeServices.APERTURA);
                    if(leDeviceListAdapter.getCount() == 1) {
                        if (bluetoothLeServices.getConnectionState() != 2) {
                            Toast.makeText(getActivity(), "NO ESTAS CONECTADO AL CANDADO", Toast.LENGTH_LONG).show();
                        } else {

                            if (bluetoothLeServices.getConnectionState() == BluetoothProfile.STATE_CONNECTED) {
                                if (bluetoothLeServices.isServiciosListos()) {
                                    if (candadoViewViewModel.getMarca().getValue().equals(getString(R.string.JOINTECH))) {
                                        bluetoothLeServices.abrirCandado(gatt,
                                                BluetoothLeServices.UUID_JOINTECH,
                                                BluetoothLeServices.SERVICE_JOINTECH,
                                                BluetoothLeServices.cadenajointech);
                                    } else if (candadoViewViewModel.getMarca().getValue().equals(getString(R.string.HARBORLOCK))) {
                                        bluetoothLeServices.abrirCandado(gatt,
                                                BluetoothLeServices.UUID_HARBORLOCK,
                                                BluetoothLeServices.SERVICE_HARBORLOCK,
                                                BluetoothLeServices.cadenaharborlock1,
                                                BluetoothLeServices.cadenaharborlock2);
                                    } else if (candadoViewViewModel.getMarca().getValue().equals(getString(R.string.TRANSLOCK))) {
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

        handler = new Handler();



        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();


        //-----------------

        if (!candadoViewViewModel.getAdmin().getValue()) {

            tarjetaPermisos.setVisibility(View.VISIBLE);

            permisos = view.findViewById(R.id.lista_permisos_candado_offline);
            permisos.setLayoutManager(new LinearLayoutManager(getContext()));
            for (Permiso permiso : offlineViewModel.getListaPermisosOffline().getValue()) {
                if (permiso.idCandado == candadoViewViewModel.getIdCandado().getValue()) {
                    listaFiltrada.add(permiso);
                }
            }
            offlinePermisosAdapter = new OfflinePermisosAdapter(listaFiltrada);
            permisos.setAdapter(offlinePermisosAdapter);

        }
        nombre = view.findViewById(R.id.nombre_vista_candado_offline);
        identificador = view.findViewById(R.id.identificador_offline);

        nombre.setText(candadoViewViewModel.getNombreCandado().getValue());
        identificador.setText(candadoViewViewModel.getIdentificador().getValue());
        return view;
    }

    public boolean tienePermiso() {
        if (candadoViewViewModel.getAdmin().getValue()) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            for (Permiso horario : listaFiltrada) {
                Date inicio = sdf.parse(horario.fechaInicio+ " " + horario.horaInicio);
                Date fin = sdf.parse(horario.fechaFin + " " + horario.horaFin);
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
    public void onDetach() {
        super.onDetach();

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
    }

    @Override
    public void onResume() {
        super.onResume();

        enableBt();
        if (gatt != null) {
            Log.i("BLE", "AQUI ESTAMOS");
        }

        if (leDeviceListAdapter.getCount() != 1) {

            scanLeDevice(true);

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

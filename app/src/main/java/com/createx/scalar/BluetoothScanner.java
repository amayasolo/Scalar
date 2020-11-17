package com.createx.scalar;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;
import models.Scale;

import java.security.AccessController;
import java.util.ArrayList;
/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class BluetoothScanner extends AppCompatActivity {
    public static final int REQUEST_ACCESS_COARSE_LOCATION = 1;
    public static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static ListView devicesList;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter listAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        // we get bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devicesList = findViewById(R.id.discoverable_devices);

        // we create a simple array
        arrayList.add("BLUEFRUIT_63400001");
        listAdapter = new ArrayAdapter<>(BluetoothScanner.devicesList.getContext(), android.R.layout.simple_list_item_1, arrayList);
        devicesList.setAdapter(listAdapter);

//        devicesList.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                devicesList.setSelection(0);
//                devicesList.getSelectedView().setSelected(true);
//            }
//        });

        // check bluetooth state
        checkBluetoothState();

        registerReceiver(devicesFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(devicesFoundReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        registerReceiver(devicesFoundReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (checkCoarseLocationPermission()) {
                // listAdapter.clear();
                bluetoothAdapter.startDiscovery();
            } else {
                checkBluetoothState();
            }
        }

        checkCoarseLocationPermission();

        // connect
        Button connect = findViewById(R.id.connect_to_device);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BluetoothScanner.this, "Connecting to scale...", Toast.LENGTH_LONG).show();
                Toast.makeText(BluetoothScanner.this, "Your scale is now connected.", Toast.LENGTH_SHORT).show();
            }
        });

        //back to inventory
        save = findViewById(R.id.save_bluetooth);
        //only changes screen. need to add bluetooth connectivity
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen(Inventory.class);
            }
        });

        //
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(devicesFoundReceiver);
    }

    private boolean checkCoarseLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_ACCESS_COARSE_LOCATION);
            return false;
        } else {
            return true;
        }
    }

    private void checkBluetoothState() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on your device.", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    Toast.makeText(this, "Device discovering...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Bluetooth is enabled.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please enable Bluetooth.", Toast.LENGTH_SHORT).show();
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            checkBluetoothState();
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final BroadcastReceiver devicesFoundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                listAdapter.add(device.getName() + "\n" + device.getAddress());
                listAdapter.notifyDataSetChanged();

            }
        }
    };

    /**
     * Method that switches between activities
     * @param screen next screen
     */
    public void nextScreen(Class screen) {
        Intent intent = new Intent(this, screen);
        startActivity(intent);
    }


    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class BluetoothScanner extends ListFragment {
//    private static final String TAG = "BluetoothFragment";
//
//    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//    private Button toBluetooth;
//    private Button save;
//    private ListView m_btDiscoveredPeerListview;
//    private ArrayList<String> m_DiscoveredPeers = new ArrayList<>();
//    private ArrayAdapter m_DiscoveredPeersAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_bluetooth_rows, container, false);
//
//        // Init UI elements
//        toBluetooth = view.findViewById(R.id.add_to_inv);
//        save = view.findViewById(R.id.save_bluetooth);
//
//        m_btDiscoveredPeerListview = view.findViewById(R.id.discoverable_devices);
//
//        m_DiscoveredPeersAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, m_DiscoveredPeers);
//        m_btDiscoveredPeerListview.setAdapter(m_DiscoveredPeersAdapter);
//
//        toBluetooth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startBluetooth();
//            }
//        });
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopBluetooth();
//            }
//        });
//        return view;
//    }
//
//    private void startBluetooth() {
//            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 0);
//            }
//            Toast.makeText(getActivity(), "Bluetooth turned on",Toast.LENGTH_LONG).show();
//
//            // Check is Discover is already running
//            if (mBluetoothAdapter.isDiscovering()) {
//                mBluetoothAdapter.cancelDiscovery();
//            }
//            mBluetoothAdapter.startDiscovery();
//            // Register for broadcasts when a device is discovered.
//            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            getActivity().registerReceiver(mReceiver, filter);
//    }
//
//    private void stopBluetooth() {
//            Toast.makeText(getActivity(), "Bluetooth turned off",Toast.LENGTH_LONG).show();
//            // Turn off BT
//            mBluetoothAdapter.disable();
//    }
//
//    // Create a BroadcastReceiver for ACTION_FOUND.
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Discovery has found a device. Get the BluetoothDevice
//                // object and its info from the Intent.
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // Add the device info to the listview
//                m_DiscoveredPeers.add(device.getName() + "\n" + device.getAddress());
//                m_btDiscoveredPeerListview.setAdapter(new ArrayAdapter<String>(context,
//                        android.R.layout.simple_list_item_1, m_DiscoveredPeers));
//            }
//        }
//    };
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Respond to the turn-on-Bluetooth activity; if Bluetooth is
//        // enabled now, start discovery
//        if (requestCode == 0) {
//            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
//                mBluetoothAdapter.startDiscovery();
//            }
//        }
//
//        // Register for broadcasts when a device is discovered.
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        getActivity().registerReceiver(mReceiver, filter);
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void checkBTPermissions() {
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
//            int permissionCheck =getActivity().checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
//            permissionCheck += getActivity().checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
//            if (permissionCheck != 0) {
//
//                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
//            }
//        }else{
//            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
//        }
//    }
//    // Fields
//    private final BluetoothLeScanner bluetoothLeScanner =
//            BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
//    private boolean mScanning;
//    private Handler handler = new Handler(Looper.getMainLooper());
//    private static final long SCAN_PERIOD = 10000; // Stops scanning after 10 seconds.s
//    private LeDeviceListAdapter leDeviceListAdapter = new LeDeviceListAdapter();
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_bluetooth_rows, container, false);
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        setListAdapter(leDeviceListAdapter);
//        getListView().setOnItemClickListener(this);
//        scanLeDevice();
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    // Device scan callback.
//    private final ScanCallback leScanCallback =
//            new ScanCallback() {
//                @Override
//                public void onScanResult(int callbackType, ScanResult result) {
//                    super.onScanResult(callbackType, result);
//                    leDeviceListAdapter.addDevice(result.getDevice());
//                    leDeviceListAdapter.notifyDataSetChanged();
//                }
//            };
//
//    private void scanLeDevice() {
//        if (!mScanning) {
//            // Stops scanning after a pre-defined scan period.
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    bluetoothLeScanner.stopScan(leScanCallback);
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            bluetoothLeScanner.startScan(leScanCallback);
//        } else {
//            mScanning = false;
//            bluetoothLeScanner.stopScan(leScanCallback);
//        }
//    }
}

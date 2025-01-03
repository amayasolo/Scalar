package com.createx.scalar;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter for holding devices found through scanning.
 */
public class LeDeviceListAdapter extends BaseAdapter {

    // Adapter for holding devices found through scanning.

    private ArrayList<BluetoothDevice> mLeDevices;
    private LayoutInflater mInflator;
//	private Activity mContext;

    public LeDeviceListAdapter() {
        super();
//		mContext = c;
        mLeDevices = new ArrayList<BluetoothDevice>();
//		mInflator = mContext.getLayoutInflater();
    }

    public void addDevice(BluetoothDevice device) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    public int getCount() {
        return mLeDevices.size();
    }

    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.activity_bluetooth_rows, null);
            viewHolder = new ViewHolder();
            viewHolder.deviceAddress = view.findViewById(R.id.device_address);
            viewHolder.deviceName = view.findViewById(R.id.device_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        BluetoothDevice device = mLeDevices.get(position);
        final String deviceName = device.getName();
        if (deviceName != null && deviceName.length() > 0)
            viewHolder.deviceName.setText(deviceName);
        else
            viewHolder.deviceName.setText("Unknown Device");
        viewHolder.deviceAddress.setText(device.getAddress());
        return view;
    }

    public void notifyDataSetChanged() {
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
}

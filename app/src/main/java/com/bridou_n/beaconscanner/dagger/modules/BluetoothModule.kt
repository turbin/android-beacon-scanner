package com.bridou_n.beaconscanner.dagger.modules

import android.bluetooth.BluetoothAdapter
import android.content.Context

import com.bridou_n.beaconscanner.dagger.PerActivity

import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by bridou_n on 05/10/2016.
 */

@Module
class BluetoothModule {

    @Provides
    @PerActivity
    fun providesBluetoothAdapter(): BluetoothAdapter? {
        return BluetoothAdapter.getDefaultAdapter()
    }

    @Provides
    @PerActivity
    fun providesBeaconManager(ctx: Context): BeaconManager {
        val instance = BeaconManager.getInstanceForApplication(ctx)

        // BeaconManager setup
        instance.beaconParsers.add(BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"))
        // Detect the main identifier (UID) frame:
        instance.beaconParsers.add(BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"))
        // Detect the telemetry (TLM) frame:
        instance.beaconParsers.add(BeaconParser().setBeaconLayout("x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15"))
        // Detect the URL frame:
        instance.beaconParsers.add(BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-21v"))

        return instance
    }
}

/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.kura.bluetooth.le;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.eclipse.kura.KuraBluetoothDiscoveryException;
import org.osgi.annotation.versioning.ProviderType;

/**
 * BluetoothAdapter represents the physical Bluetooth adapter on the host machine (ex: hci0).
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 1.3
 */
@ProviderType
public interface BluetoothLeAdapter {

    /**
     * Search for a BLE device with the specified address. The method will perform a BLE discovery for at most timeout
     * seconds or until the device is found. It will return a Future instance and the discovered device can be
     * retrieved using the get() method. The get(long timeout, TimeUnit unit) is not supported and acts as the get()
     * method.
     * 
     * @param timeout
     *            timeout in seconds for device discovery
     * @param address
     *            MAC address of the BLE device
     * @return Future
     */
    public Future<BluetoothLeDevice> findDeviceByAddress(long timeout, String address);

    /**
     * Search for a BLE device with the specified name. The method will perform a BLE discovery for at most timeout
     * seconds or until the device is found. It will return a Future instance and the discovered device can be
     * retrieved using the get() method. The get(long timeout, TimeUnit unit) is not supported and acts as the get()
     * method.
     * 
     * @param timeout
     *            timeout in seconds for device discovery
     * @param name
     *            system name of the BLE device
     * @return Future
     */
    public Future<BluetoothLeDevice> findDeviceByName(long timeout, String name);

    /**
     * Search for a BLE device with the specified address. The method will perform a BLE discovery for at most timeout
     * seconds or until the device is found. When the device is found or the timeout is reached the consumer is used to
     * get the device.
     * 
     * @param timeout
     *            timeout in seconds for device discovery
     * @param address
     *            MAC address of the BLE device
     * @param consumer
     *            the consumer used to get the device
     */
    public void findDeviceByAddress(long timeout, String address, Consumer<BluetoothLeDevice> consumer);

    /**
     * Search for a BLE device with the specified name. The method will perform a BLE discovery for at most timeout
     * seconds or until the device is found. When the device is found or the timeout is reached the consumer is used to
     * get the device.
     * 
     * @param timeout
     *            timeout in seconds for device discovery
     * @param name
     *            system name of the BLE device
     * @param consumer
     *            the consumer used to get the device
     */
    public void findDeviceByName(long timeout, String name, Consumer<BluetoothLeDevice> consumer);

    /**
     * Search for BLE devices. The method will perform a BLE discovery for timeout seconds. It will return a Future
     * instance and the discovered devices can be retrieved using the get() method. The get(long timeout, TimeUnit unit)
     * is not supported and acts as the get() method.
     * 
     * @param timeout
     *            timeout in seconds for device discovery
     * @return Future
     */
    public Future<List<BluetoothLeDevice>> findDevices(long timeout);

    /**
     * Search for BLE devices. The method will perform a BLE discovery for timeout seconds. When the timeout is reached
     * the consumer is used to get the devices.
     * 
     * @param timeout
     *            timeout in seconds for device discovery
     * @param consumer
     *            the consumer used to get the device
     */
    public void findDevices(long timeout, Consumer<List<BluetoothLeDevice>> consumer);

    /**
     * Stops a BLE discovery.
     * 
     * @throws KuraBluetoothDiscoveryException
     */
    public void stopDiscovery() throws KuraBluetoothDiscoveryException;

    /**
     * Returns the hardware address of this adapter.
     * 
     * @return The hardware address of this adapter.
     */
    public String getAddress();

    /**
     * Returns the system name of this adapter.
     * 
     * @return The system name of this adapter.
     */
    public String getName();

    /**
     * Returns the interface name of this adapter.
     * 
     * @return The interface name of this adapter.
     */
    public String getInterfaceName();

    /**
     * Returns the local ID of the adapter.
     * 
     * @return The local ID of the adapter.
     */
    public String getModalias();

    /**
     * Returns the friendly name of this adapter.
     * 
     * @return The friendly name of this adapter, or NULL if not set.
     */
    public String getAlias();

    /**
     * Sets the friendly name of this adapter.
     * 
     */
    public void setAlias(String value);

    /**
     * Returns the Bluetooth class of the adapter.
     * 
     * @return The Bluetooth class of the adapter.
     */
    public long getBluetoothClass();

    /**
     * Returns the power state the adapter.
     * 
     * @return The power state of the adapter.
     */
    public boolean isPowered();

    /**
     * Sets the power state the adapter.
     */
    public void setPowered(boolean value);

    /**
     * Returns the discoverable state the adapter.
     * 
     * @return The discoverable state of the adapter.
     */
    public boolean isDiscoverable();

    /**
     * Sets the discoverable state the adapter.
     */
    public void setDiscoverable(boolean value);

    /**
     * Returns the discoverable timeout the adapter.
     * 
     * @return The discoverable timeout of the adapter.
     */
    public long getDiscoverableTimeout();

    /**
     * Sets the discoverable timeout the adapter. A value of 0 disables
     * the timeout.
     */
    public void setDiscoverableTimout(long value);

    /**
     * Returns the pairable state the adapter.
     * 
     * @return The pairable state of the adapter.
     */
    public boolean isPairable();

    /**
     * Sets the discoverable state the adapter.
     */
    public void setPairable(boolean value);

    /**
     * Returns the timeout in seconds after which pairable state turns off
     * automatically, 0 means never.
     * 
     * @return The pairable timeout of the adapter.
     */
    public long getPairableTimeout();

    /**
     * Sets the timeout after which pairable state turns off automatically, 0 means never.
     */
    public void setPairableTimeout(long value);

    /**
     * Returns the discovering state the adapter.
     * 
     * @return The discovering state of the adapter.
     */
    public boolean isDiscovering();

    /**
     * Returns the UUIDs of the adapter.
     * 
     * @return Array containing the UUIDs of the adapter.
     */
    public UUID[] getUUIDs();

}

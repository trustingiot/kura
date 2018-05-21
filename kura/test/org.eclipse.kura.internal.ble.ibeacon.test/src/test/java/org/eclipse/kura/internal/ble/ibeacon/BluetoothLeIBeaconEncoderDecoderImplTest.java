/*******************************************************************************
 * Copyright (c) 2018 Eurotech and/or its affiliates and others
 *
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   which accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.kura.internal.ble.ibeacon;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.UUID;

import org.eclipse.kura.ble.ibeacon.BluetoothLeIBeacon;
import org.junit.Test;

public class BluetoothLeIBeaconEncoderDecoderImplTest {

    @Test
    public void testBasicEncoder() {
        BluetoothLeIBeaconEncoderImpl svc = new BluetoothLeIBeaconEncoderImpl();

        assertEquals(BluetoothLeIBeacon.class, svc.getBeaconType());

        // nothing to check, here
        svc.activate(null);
        svc.deactivate(null);
    }

    @Test
    public void testBasicDecoder() {
        BluetoothLeIBeaconDecoderImpl svc = new BluetoothLeIBeaconDecoderImpl();

        assertEquals(BluetoothLeIBeacon.class, svc.getBeaconType());

        // nothing to check, here
        svc.activate(null);
        svc.deactivate(null);
    }

    @Test
    public void testEncodeDecode() {
        BluetoothLeIBeaconEncoderImpl encoder = new BluetoothLeIBeaconEncoderImpl();
        BluetoothLeIBeaconDecoderImpl decoder = new BluetoothLeIBeaconDecoderImpl();

        BluetoothLeIBeacon beacon = new BluetoothLeIBeacon();
        beacon.setBrEdrSupported(true);
        beacon.setLeBrController(true);
        beacon.setLeBrHost(true);
        beacon.setLeGeneral(true);
        beacon.setLeLimited(true);
        beacon.setMajor((short) 5);
        beacon.setMinor((short) 2);
        beacon.setRssi(0);
        beacon.setTxPower((short) 50);
        beacon.setUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        byte[] encoded = encoder.encode(beacon);

        byte[] data = Arrays.copyOfRange(encoded, 4, encoded.length - 1);
        data[0] = encoded[3]; // encoded[3] == flags && decoder#decode b[0] must be flags

        BluetoothLeIBeacon decoded = decoder.decode(data);

        compare(beacon, decoded);
    }

    @Test
    public void testEncode() {
        BluetoothLeIBeaconEncoderImpl encoder = new BluetoothLeIBeaconEncoderImpl();

        BluetoothLeIBeacon beacon = null;

        byte[] encoded = null;
        try {
            encoded = encoder.encode(beacon);
        } catch (NullPointerException e) {
            // expected
        }

        beacon = new BluetoothLeIBeacon();
        beacon.setBrEdrSupported(true);
        beacon.setLeBrController(true);
        beacon.setLeBrHost(true);
        beacon.setLeGeneral(true);
        beacon.setLeLimited(true);
        beacon.setMajor((short) 5);
        beacon.setMinor((short) 2);
        beacon.setRssi(10);
        beacon.setTxPower((short) 50);
        beacon.setUuid(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        encoded = encoder.encode(beacon);

        byte[] expected = { 30, 2, 1, 0x1F, 0x1A, (byte) 0xFF, 76, 0, 2, 21, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                17, 17, 17, 17, 17, 0, 5, 0, 2, 50, 0 };

        assertArrayEquals(expected, encoded);
    }

    @Test
    public void testDecode() {
        BluetoothLeIBeacon beacon = new BluetoothLeIBeacon();
        beacon.setBrEdrSupported(true);
        beacon.setLeBrController(true);
        beacon.setLeBrHost(true);
        beacon.setLeGeneral(true);
        beacon.setLeLimited(true);
        beacon.setMajor((short) 5);
        beacon.setMinor((short) 2);
        beacon.setTxPower((short) 50);
        beacon.setUuid(UUID.fromString("11111111-1111-1111-1111-111111111112"));

        byte[] encoded = { (byte) 0x1f, (byte) 0xff, 76, 0, 2, 21, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 0, 5, 0, 2, 50 };

        BluetoothLeIBeaconDecoderImpl decoder = new BluetoothLeIBeaconDecoderImpl();
        BluetoothLeIBeacon decoded = decoder.decode(encoded);

        compare(beacon, decoded);
    }

    @Test
    public void testDecodeStopAt0() {
        byte[] encoded = { (byte) 0x1f, 1, 76, 0, 2, 21, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 0, 5, 0, 2, 50 };

        BluetoothLeIBeaconDecoderImpl decoder = new BluetoothLeIBeaconDecoderImpl();
        BluetoothLeIBeacon decoded = decoder.decode(encoded);

        assertNull(decoded);
    }

    private void compare(BluetoothLeIBeacon beacon, BluetoothLeIBeacon decoded) {
        assertEquals(beacon.getMajor(), decoded.getMajor());
        assertEquals(beacon.getMinor(), decoded.getMinor());
        assertEquals(beacon.getRssi(), decoded.getRssi());
        assertEquals(beacon.getTxPower(), decoded.getTxPower());
        assertEquals(beacon.getUuid(), decoded.getUuid());
        assertEquals(beacon.isBrEdrSupported(), decoded.isBrEdrSupported());
        assertEquals(beacon.isLeBrController(), decoded.isLeBrController());
        assertEquals(beacon.isLeBrHost(), decoded.isLeBrHost());
        assertEquals(beacon.isLeGeneral(), decoded.isLeGeneral());
        assertEquals(beacon.isLeLimited(), decoded.isLeLimited());
    }

}

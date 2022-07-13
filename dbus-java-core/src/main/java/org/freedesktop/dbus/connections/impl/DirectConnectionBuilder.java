package org.freedesktop.dbus.connections.impl;

import java.nio.ByteOrder;

import org.freedesktop.dbus.connections.ReceivingService.ReceivingServiceConfig;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.messages.Message;

/**
 * Builder to create a new DirectConnection.
 *
 * @author hypfvieh
 * @version 4.1.0 - 2022-02-04
 */
public class DirectConnectionBuilder extends BaseConnectionBuilder<DirectConnectionBuilder> {

    private DirectConnectionBuilder(String _address) {
        super(DirectConnectionBuilder.class, _address);
    }

    /**
     * Use the given address to create the connection (e.g. used for remote TCP connected DBus daemons).
     *
     * @param _address address to use
     * @return this
     */
    public static DirectConnectionBuilder forAddress(String _address) {
        DirectConnectionBuilder instance = new DirectConnectionBuilder(_address);
        return instance;
    }

    /**
     * Create the new {@link DBusConnection}.
     *
     * @return {@link DBusConnection}
     * @throws DBusException when DBusConnection could not be opened
     */
    public DirectConnection build() throws DBusException {
        ReceivingServiceConfig cfg = buildThreadConfig();
        DirectConnection c = new DirectConnection(getTimeout(), getAddress(), cfg);
        c.setDisconnectCallback(getDisconnectCallback());
        c.setWeakReferences(isWeakReference());
        DirectConnection.setEndianness(getEndianess());
        return c;
    }

    /**
     * Get the default system endianness.
     *
     * @return LITTLE or BIG
     * @deprecated if required, use {@link BaseConnectionBuilder#getSystemEndianness()}
     */
    @Deprecated(forRemoval = true, since = "4.1.1")
    public static byte getSystemEndianness() {
       return ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ?
                Message.Endian.BIG
                : Message.Endian.LITTLE;
    }
}

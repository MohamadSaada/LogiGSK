/*
 * Copyright (C) 2017 Mohamad Saada
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Logi.GSeries.Libraries;

import java.nio.ByteBuffer;
import org.usb4java.BufferUtils;
import org.usb4java.ConfigDescriptor;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceHandle;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 * Note: This work has been converted to Java from C++, some modifications and 
 * additions have been done to the code
 * Original work can be found at https://github.com/MatMoul/g810-led
 * @author Mathieu Moulin(MatMoul), Mohamad Saada
 */
public class Keyboard {

    public class KeyAddress {

        public KeyAddressGroup addressGroup;
        byte id;
    }

    public class KeyColors {

        public byte red;
        public byte green;
        public byte blue;
    }

    public class KeyValue {

        KeyAddress key;
        KeyColors colors;
    }

    public enum KeyboardModel {
        Logi910Spectrum, Logi910Spark, Logi810Spectrum,
        Logi610Orion, Logi410AtlasSpectrum, NoLogiKeyboard,
        Logi910SpectrumUSQWERTY, Logi910SpectrumUKQWERTY,
        Logi910SparkUSQWERTY, Logi910SparkUKQWERTY,
        Logi810SpectrumUSQWERTY, Logi810SpectrumUKQWERTY,
        Logi610OrionUSQWERTY, Logi610OrionUKQWERTY,
        Logi410AtlasSpectrumUSQWERTY, Logi410AtlasSpectrumUKQWERTY
    };

    public enum KeyboardLayout {
        US_QWERTY, UK_QWERTY
    };

    public enum PowerOnEffect {
        ColourWave, FixedColour
    };

    public enum KeyAddressGroup {
        logo, gKeys, indicators, multimedia, keys
    };

    public enum Key { // 127 keys
        logo, badge,
        g1, g2, g3, g4, g5, g6, g7, g8, g9,
        caps, num, scroll, game, backlight,
        mute, play, stop, prev, next,
        f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
        shift_left, ctrl_left, win_left, alt_left, alt_right, win_right, ctrl_right, shift_right, menu,
        arrow_top, arrow_left, arrow_bottom, arrow_right,
        num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, num_0, num_dot, num_enter, num_plus, num_minus, num_asterisk, num_slash, num_lock,
        esc, scroll_lock,
        insert, del, home, end, page_up, page_down, print_screen, pause_break,
        n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,
        tab, caps_lock, space, backspace, enter,
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
        tilde, minus, equal,
        open_bracket, close_bracket, backslash,
        semicolon, quote, dollar,
        intl_backslash, comma, period, slash
    };
    
    public enum KeyG910UKQWERTY { // 116 keys
        logo, badge,
        g1, g2, g3, g4, g5, g6, g7, g8, g9,
        f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
        shift_left, ctrl_left, win_left, alt_left, alt_right, win_right, ctrl_right, shift_right, menu,
        arrow_top, arrow_left, arrow_bottom, arrow_right,
        num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, num_0, num_dot, num_enter, num_plus, num_minus, num_asterisk, num_slash, num_lock,
        esc, scroll_lock,
        insert, del, home, end, page_up, page_down, print_screen, pause_break,
        n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,
        tab, caps_lock, space, backspace, enter,
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
        tilde, minus, equal,
        open_bracket, close_bracket,
        semicolon, quote, dollar,
        intl_backslash, comma, period
    };
    
    public enum KeyG910USQWERTY { // 115 keys
        logo, badge,
        g1, g2, g3, g4, g5, g6, g7, g8, g9,
        f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
        shift_left, ctrl_left, win_left, alt_left, alt_right, win_right, ctrl_right, shift_right, menu,
        arrow_top, arrow_left, arrow_bottom, arrow_right,
        num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, num_0, num_dot, num_enter, num_plus, num_minus, num_asterisk, num_slash, num_lock,
        esc, scroll_lock,
        insert, del, home, end, page_up, page_down, print_screen, pause_break,
        n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,
        tab, caps_lock, space, backspace, enter,
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
        tilde, minus, equal,
        open_bracket, close_bracket, intl_backslash,
        semicolon, quote,
        comma, period, slash
    };
    
    public enum KeyG810USQWERTY { // 115 keys
        logo,
        caps, num, scroll, game, backlight,
        mute, play, stop, prev, next,
        f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
        shift_left, ctrl_left, win_left, alt_left, alt_right, win_right, ctrl_right, shift_right, menu,
        arrow_top, arrow_left, arrow_bottom, arrow_right,
        num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, num_0, num_dot, num_enter, num_plus, num_minus, num_asterisk, num_slash, num_lock,
        esc, scroll_lock,
        insert, del, home, end, page_up, page_down, print_screen, pause_break,
        n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,
        tab, caps_lock, space, backspace, enter,
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
        tilde, minus, equal,
        open_bracket, close_bracket, backslash,
        semicolon, quote,
        comma, period, slash
    };
    
    public enum KeyG610USQWERTY { // 115 keys
        logo,
        caps, num, scroll, game, backlight,
        mute, play, stop, prev, next,
        f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
        shift_left, ctrl_left, win_left, alt_left, alt_right, win_right, ctrl_right, shift_right, menu,
        arrow_top, arrow_left, arrow_bottom, arrow_right,
        num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, num_0, num_dot, num_enter, num_plus, num_minus, num_asterisk, num_slash, num_lock,
        esc, scroll_lock,
        insert, del, home, end, page_up, page_down, print_screen, pause_break,
        n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,
        tab, caps_lock, space, backspace, enter,
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
        tilde, minus, equal,
        open_bracket, close_bracket, backslash,
        semicolon, quote,
        comma, period, slash
    };
    
    public enum KeyG410USQWERTY { // 89 keys
        game, backlight,
        f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12,
        shift_left, ctrl_left, win_left, alt_left, alt_right, win_right, ctrl_right, shift_right, menu,
        arrow_top, arrow_left, arrow_bottom, arrow_right,
        esc, scroll_lock,
        insert, del, home, end, page_up, page_down, print_screen, pause_break,
        n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,
        tab, caps_lock, space, backspace, enter,
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
        tilde, minus, equal,
        open_bracket, close_bracket, backslash,
        semicolon, quote,
        comma, period, slash
    };
    
    public enum KeyGroup {
        logo, gKeys, indicators, multimedia, fkeys, modifiers, arrows, numeric, functions, keys
    };
    public KeyboardModel model = KeyboardModel.NoLogiKeyboard;
    public KeyboardModel[] modelGroup = null;
    public int ConnectedGKeyboards = 0;

    private boolean m_isAttached = false;
    private boolean m_isKernellDetached = false;
    private DeviceList devs;
    private DeviceHandle dev_handle;
    private Context ctx = null;

    boolean isAttached() {
        return m_isAttached;
    }

    boolean attach() {
        if (m_isAttached == true) {
            return false;
        }
        int r;
        r = LibUsb.init(ctx);
        if (r < 0) {
            return false;
        }

        devs = new DeviceList();
        int cnt = LibUsb.getDeviceList(ctx, devs);
        int chc = 0;
        if (cnt < 0) {
            return false;
        }
        short pid = 0;
        try {
            // Iterate over all devices and scan for the right one
            for (Device device : devs) {
                DeviceDescriptor descriptor = new DeviceDescriptor();
                ConfigDescriptor des = new ConfigDescriptor();
                cnt = LibUsb.getDeviceDescriptor(device, descriptor);
                chc = LibUsb.getActiveConfigDescriptor(device, des);
                if (cnt != LibUsb.SUCCESS) {
                    throw new LibUsbException("Unable to read device descriptor", cnt);
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15563) {//g910 Spectrum
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi910Spectrum;
                    ConnectedGKeyboards++;
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15573) {//g910 Spark
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi910Spark;
                    ConnectedGKeyboards++;
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15567) {//g810
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi810Spectrum;
                    ConnectedGKeyboards++;
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15561) {//g810
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi810Spectrum;
                    ConnectedGKeyboards++;
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15565) {//g610
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi610Orion;
                    ConnectedGKeyboards++;
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15560) {//g610
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi610Orion;
                    ConnectedGKeyboards++;
                }
                if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15568) {//g410
                    pid = descriptor.idProduct();
                    model = KeyboardModel.Logi410AtlasSpectrum;
                    ConnectedGKeyboards++;
                }
            }

            if (ConnectedGKeyboards > 1) {
                modelGroup = new KeyboardModel[ConnectedGKeyboards];
                ConnectedGKeyboards = 0;
                for (Device device : devs) {
                    DeviceDescriptor descriptor = new DeviceDescriptor();
                    cnt = LibUsb.getDeviceDescriptor(device, descriptor);
                    if (cnt != LibUsb.SUCCESS) {
                        throw new LibUsbException("Unable to read device descriptor", cnt);
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15563) {//g910
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi910Spectrum;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15573) {//g910
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi910Spark;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15567) {//g810
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi810Spectrum;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15561) {//g810
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi810Spectrum;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15565) {//g610
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi610Orion;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15560) {//g610
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi610Orion;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                    if (descriptor.idVendor() == 1133 && descriptor.idProduct() == -15568) {//g410
                        pid = descriptor.idProduct();
                        model = KeyboardModel.Logi410AtlasSpectrum;
                        modelGroup[ConnectedGKeyboards] = model;
                        ConnectedGKeyboards++;
                    }
                }
            }
        } finally {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(devs, true);
        }

        if (pid == 0) {
            LibUsb.exit(ctx);
            ctx = null;
            return false;
        }

        dev_handle = new DeviceHandle();
        dev_handle = LibUsb.openDeviceWithVidPid(ctx, (short) 0x046d, pid);
        if (dev_handle == null) {
            LibUsb.exit(ctx);
            ctx = null;
            return false;
        }

        if (LibUsb.kernelDriverActive(dev_handle, 1) == 1) {
            LibUsb.detachKernelDriver(dev_handle, 1);
            m_isKernellDetached = true;
        }

        r = LibUsb.claimInterface(dev_handle, 1);
        if (r < 0) {
            return false;
        }
        m_isAttached = true;
        return true;
    }

    boolean detach() {
        if (m_isAttached == false) {
            return false;
        }
        int r;
        r = LibUsb.releaseInterface(dev_handle, 1);
        if (r != 0) {
            return false;
        }

        if (m_isKernellDetached == true) {
            LibUsb.attachKernelDriver(dev_handle, 1);
            m_isKernellDetached = false;
        }
        LibUsb.close(dev_handle);
        dev_handle = null;
        LibUsb.exit(ctx);
        ctx = null;
        m_isAttached = false;
        return true;
    }

    boolean commit() {
        if (m_isAttached == false) {
            return false;
        }
        boolean retval = false;
        byte data[] = new byte[20];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                data[0] = (byte) 0x11;
                data[1] = (byte) 0xff;
                data[2] = (byte) 0x0f;
                data[3] = (byte) 0x5c;
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                data[0] = (byte) 0x11;
                data[1] = (byte) 0xff;
                data[2] = (byte) 0x0c;
                data[3] = (byte) 0x5a;
                break;
        }
        for (int i = 4; i < 20; i++) {
            data[i] = 0x00;
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        retval = sendDataInternal(buffer, 20);
        return retval;
    }

    public static Keyboard.KeyboardModel[] getKeyboardModel() {
        Keyboard.KeyboardModel[] model = null;
        Keyboard lg_kbd = new Keyboard();
        lg_kbd.attach();
        if (lg_kbd.ConnectedGKeyboards != 0) {
            model = new Keyboard.KeyboardModel[lg_kbd.ConnectedGKeyboards];
            if (lg_kbd.ConnectedGKeyboards <= 1) {
                model[0] = lg_kbd.model;
            } else {
                int currentModel = 0;
                for (int i = 0; i < lg_kbd.ConnectedGKeyboards; i++) {
                    model[currentModel] = lg_kbd.model;
                    currentModel++;
                }
            }
        } else {
            model = new Keyboard.KeyboardModel[1];
            model[0] = Keyboard.KeyboardModel.NoLogiKeyboard;
        }
        lg_kbd.detach();
        return model;
    }

    KeyAddress getKeyAddress(Key key) {
        KeyAddress keyAddress = new KeyAddress();
        switch (key) {
            case logo:
                keyAddress.addressGroup = KeyAddressGroup.logo;
                keyAddress.id = (byte) 0x01;
                break;
            case badge:
                keyAddress.addressGroup = KeyAddressGroup.logo;
                keyAddress.id = (byte) 0x02;
                break;
            case g1:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x01;
                break;
            case g2:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x02;
                break;
            case g3:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x03;
                break;
            case g4:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x04;
                break;
            case g5:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x05;
                break;
            case g6:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x06;
                break;
            case g7:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x07;
                break;
            case g8:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x08;
                break;
            case g9:
                keyAddress.addressGroup = KeyAddressGroup.gKeys;
                keyAddress.id = (byte) 0x09;
                break;
            case backlight:
                keyAddress.addressGroup = KeyAddressGroup.indicators;
                keyAddress.id = (byte) 0x01;
                break;
            case game:
                keyAddress.addressGroup = KeyAddressGroup.indicators;
                keyAddress.id = (byte) 0x02;
                break;
            case caps:
                keyAddress.addressGroup = KeyAddressGroup.indicators;
                keyAddress.id = (byte) 0x03;
                break;
            case scroll:
                keyAddress.addressGroup = KeyAddressGroup.indicators;
                keyAddress.id = (byte) 0x04;
                break;
            case num:
                keyAddress.addressGroup = KeyAddressGroup.indicators;
                keyAddress.id = (byte) 0x05;
                break;
            case next:
                keyAddress.addressGroup = KeyAddressGroup.multimedia;
                keyAddress.id = (byte) 0xb5;
                break;
            case prev:
                keyAddress.addressGroup = KeyAddressGroup.multimedia;
                keyAddress.id = (byte) 0xb6;
                break;
            case stop:
                keyAddress.addressGroup = KeyAddressGroup.multimedia;
                keyAddress.id = (byte) 0xb7;
                break;
            case play:
                keyAddress.addressGroup = KeyAddressGroup.multimedia;
                keyAddress.id = (byte) 0xcd;
                break;
            case mute:
                keyAddress.addressGroup = KeyAddressGroup.multimedia;
                keyAddress.id = (byte) 0xe2;
                break;
            default:
                keyAddress.addressGroup = KeyAddressGroup.keys;
                switch (key) {
                    case a:
                        keyAddress.id = (byte) 0x04;
                        break;
                    case b:
                        keyAddress.id = (byte) 0x05;
                        break;
                    case c:
                        keyAddress.id = (byte) 0x06;
                        break;
                    case d:
                        keyAddress.id = (byte) 0x07;
                        break;
                    case e:
                        keyAddress.id = (byte) 0x08;
                        break;
                    case f:
                        keyAddress.id = (byte) 0x09;
                        break;
                    case g:
                        keyAddress.id = (byte) 0x0a;
                        break;
                    case h:
                        keyAddress.id = (byte) 0x0b;
                        break;
                    case i:
                        keyAddress.id = (byte) 0x0c;
                        break;
                    case j:
                        keyAddress.id = (byte) 0x0d;
                        break;
                    case k:
                        keyAddress.id = (byte) 0x0e;
                        break;
                    case l:
                        keyAddress.id = (byte) 0x0f;
                        break;
                    case m:
                        keyAddress.id = (byte) 0x10;
                        break;
                    case n:
                        keyAddress.id = (byte) 0x11;
                        break;
                    case o:
                        keyAddress.id = (byte) 0x12;
                        break;
                    case p:
                        keyAddress.id = (byte) 0x13;
                        break;
                    case q:
                        keyAddress.id = (byte) 0x14;
                        break;
                    case r:
                        keyAddress.id = (byte) 0x15;
                        break;
                    case s:
                        keyAddress.id = (byte) 0x16;
                        break;
                    case t:
                        keyAddress.id = (byte) 0x17;
                        break;
                    case u:
                        keyAddress.id = (byte) 0x18;
                        break;
                    case v:
                        keyAddress.id = (byte) 0x19;
                        break;
                    case w:
                        keyAddress.id = (byte) 0x1a;
                        break;
                    case x:
                        keyAddress.id = (byte) 0x1b;
                        break;
                    case z:
                        keyAddress.id = (byte) 0x1c;
                        break;
                    case y:
                        keyAddress.id = (byte) 0x1d;
                        break;
                    case n1:
                        keyAddress.id = (byte) 0x1e;
                        break;
                    case n2:
                        keyAddress.id = (byte) 0x1f;
                        break;
                    case n3:
                        keyAddress.id = (byte) 0x20;
                        break;
                    case n4:
                        keyAddress.id = (byte) 0x21;
                        break;
                    case n5:
                        keyAddress.id = (byte) 0x22;
                        break;
                    case n6:
                        keyAddress.id = (byte) 0x23;
                        break;
                    case n7:
                        keyAddress.id = (byte) 0x24;
                        break;
                    case n8:
                        keyAddress.id = (byte) 0x25;
                        break;
                    case n9:
                        keyAddress.id = (byte) 0x26;
                        break;
                    case n0:
                        keyAddress.id = (byte) 0x27;
                        break;
                    case enter:
                        keyAddress.id = (byte) 0x28;
                        break;
                    case esc:
                        keyAddress.id = (byte) 0x29;
                        break;
                    case backspace:
                        keyAddress.id = (byte) 0x2a;
                        break;
                    case tab:
                        keyAddress.id = (byte) 0x2b;
                        break;
                    case space:
                        keyAddress.id = (byte) 0x2c;
                        break;
                    case minus:
                        keyAddress.id = (byte) 0x2d;
                        break;  // *
                    case equal:
                        keyAddress.id = (byte) 0x2e;
                        break;
                    case open_bracket:
                        keyAddress.id = (byte) 0x2f;
                        break;
                    case close_bracket:
                        keyAddress.id = (byte) 0x30;
                        break;
                    case backslash:
                        keyAddress.id = (byte) 0x31;
                        break;
                    case dollar:
                        keyAddress.id = (byte) 0x32;
                        break;
                    case semicolon:
                        keyAddress.id = (byte) 0x33;
                        break;
                    case quote:
                        keyAddress.id = (byte) 0x34;
                        break;
                    case tilde:
                        keyAddress.id = (byte) 0x35;
                        break;
                    case comma:
                        keyAddress.id = (byte) 0x36;
                        break;
                    case period:
                        keyAddress.id = (byte) 0x37;
                        break;
                    case slash:
                        keyAddress.id = (byte) 0x38;
                        break;
                    case caps_lock:
                        keyAddress.id = (byte) 0x39;
                        break;
                    case f1:
                        keyAddress.id = (byte) 0x3a;
                        break;
                    case f2:
                        keyAddress.id = (byte) 0x3b;
                        break;
                    case f3:
                        keyAddress.id = (byte) 0x3c;
                        break;
                    case f4:
                        keyAddress.id = (byte) 0x3d;
                        break;
                    case f5:
                        keyAddress.id = (byte) 0x3e;
                        break;
                    case f6:
                        keyAddress.id = (byte) 0x3f;
                        break;
                    case f7:
                        keyAddress.id = (byte) 0x40;
                        break;
                    case f8:
                        keyAddress.id = (byte) 0x41;
                        break;
                    case f9:
                        keyAddress.id = (byte) 0x42;
                        break;
                    case f10:
                        keyAddress.id = (byte) 0x43;
                        break;
                    case f11:
                        keyAddress.id = (byte) 0x44;
                        break;
                    case f12:
                        keyAddress.id = (byte) 0x45;
                        break;
                    case print_screen:
                        keyAddress.id = (byte) 0x46;
                        break;
                    case scroll_lock:
                        keyAddress.id = (byte) 0x47;
                        break;
                    case pause_break:
                        keyAddress.id = (byte) 0x48;
                        break;
                    case insert:
                        keyAddress.id = (byte) 0x49;
                        break;
                    case home:
                        keyAddress.id = (byte) 0x4a;
                        break;
                    case page_up:
                        keyAddress.id = (byte) 0x4b;
                        break;
                    case del:
                        keyAddress.id = (byte) 0x4c;
                        break;
                    case end:
                        keyAddress.id = (byte) 0x4d;
                        break;
                    case page_down:
                        keyAddress.id = (byte) 0x4e;
                        break;
                    case arrow_right:
                        keyAddress.id = (byte) 0x4f;
                        break;
                    case arrow_left:
                        keyAddress.id = (byte) 0x50;
                        break;
                    case arrow_bottom:
                        keyAddress.id = (byte) 0x51;
                        break;
                    case arrow_top:
                        keyAddress.id = (byte) 0x52;
                        break;
                    case num_lock:
                        keyAddress.id = (byte) 0x53;
                        break;
                    case num_slash:
                        keyAddress.id = (byte) 0x54;
                        break;
                    case num_asterisk:
                        keyAddress.id = (byte) 0x55;
                        break;
                    case num_minus:
                        keyAddress.id = (byte) 0x56;
                        break;
                    case num_plus:
                        keyAddress.id = (byte) 0x57;
                        break;
                    case num_enter:
                        keyAddress.id = (byte) 0x58;
                        break;
                    case num_1:
                        keyAddress.id = (byte) 0x59;
                        break;
                    case num_2:
                        keyAddress.id = (byte) 0x5a;
                        break;
                    case num_3:
                        keyAddress.id = (byte) 0x5b;
                        break;
                    case num_4:
                        keyAddress.id = (byte) 0x5c;
                        break;
                    case num_5:
                        keyAddress.id = (byte) 0x5d;
                        break;
                    case num_6:
                        keyAddress.id = (byte) 0x5e;
                        break;
                    case num_7:
                        keyAddress.id = (byte) 0x5f;
                        break;
                    case num_8:
                        keyAddress.id = (byte) 0x60;
                        break;
                    case num_9:
                        keyAddress.id = (byte) 0x61;
                        break;
                    case num_0:
                        keyAddress.id = (byte) 0x62;
                        break;
                    case num_dot:
                        keyAddress.id = (byte) 0x63;
                        break;
                    case intl_backslash:
                        keyAddress.id = (byte) 0x64;
                        break;
                    case menu:
                        keyAddress.id = (byte) 0x65;
                        break;
                    case ctrl_left:
                        keyAddress.id = (byte) 0xe0;
                        break;
                    case shift_left:
                        keyAddress.id = (byte) 0xe1;
                        break;
                    case alt_left:
                        keyAddress.id = (byte) 0xe2;
                        break;
                    case win_left:
                        keyAddress.id = (byte) 0xe3;
                        break;
                    case ctrl_right:
                        keyAddress.id = (byte) 0xe4;
                        break;
                    case shift_right:
                        keyAddress.id = (byte) 0xe5;
                        break;
                    case alt_right:
                        keyAddress.id = (byte) 0xe6;
                        break;
                    case win_right:
                        keyAddress.id = (byte) 0xe7;
                        break;
                    default:
                        break;
                }
                break;
        }
        return keyAddress;
    }

    PowerOnEffect parsePowerOnEffect(String effect) {
        PowerOnEffect powerOnEffect;
        if (effect == "ColourWave") {
            powerOnEffect = PowerOnEffect.ColourWave;
        } else if (effect == "FixedColour") {
            powerOnEffect = PowerOnEffect.FixedColour;
        } else {
            powerOnEffect = PowerOnEffect.FixedColour;
        }
        return powerOnEffect;
    }

    KeyAddress parseKey(String key) {
        key = key.toLowerCase();
        Key parsedKey;
        if (key.equals("logo")) {
            parsedKey = Key.logo;
        } else if (key.equals("badge")) {
            parsedKey = Key.badge;
        } else if (key.equals("g1")) {
            parsedKey = Key.g1;
        } else if (key.equals("g2")) {
            parsedKey = Key.g2;
        } else if (key.equals("g3")) {
            parsedKey = Key.g3;
        } else if (key.equals("g4")) {
            parsedKey = Key.g4;
        } else if (key.equals("g5")) {
            parsedKey = Key.g5;
        } else if (key.equals("g6")) {
            parsedKey = Key.g6;
        } else if (key.equals("g7")) {
            parsedKey = Key.g7;
        } else if (key.equals("g8")) {
            parsedKey = Key.g8;
        } else if (key.equals("g9")) {
            parsedKey = Key.g9;
        } else if (key.equals("back_light") || key.equals("backlight") || key.equals("light")) {
            parsedKey = Key.backlight;
        } else if (key.equals("game_mode") || key.equals("gamemode") || key.equals("game")) {
            parsedKey = Key.game;
        } else if (key.equals("caps_indicator") || key.equals("capsindicator") || key.equals("caps")) {
            parsedKey = Key.caps;
        } else if (key.equals("scroll_indicator") || key.equals("scrollindicator") || key.equals("scroll")) {
            parsedKey = Key.scroll;
        } else if (key.equals("num_indicator") || key.equals("numindicator") || key.equals("num")) {
            parsedKey = Key.num;
        } else if (key.equals("next")) {
            parsedKey = Key.next;
        } else if (key.equals("prev") || key.equals("previous")) {
            parsedKey = Key.prev;
        } else if (key.equals("stop")) {
            parsedKey = Key.stop;
        } else if (key.equals("play_pause") || key.equals("playpause") || key.equals("play")) {
            parsedKey = Key.play;
        } else if (key.equals("mute")) {
            parsedKey = Key.mute;
        } else if (key.equals("a")) {
            parsedKey = Key.a;
        } else if (key.equals("b")) {
            parsedKey = Key.b;
        } else if (key.equals("c")) {
            parsedKey = Key.c;
        } else if (key.equals("d")) {
            parsedKey = Key.d;
        } else if (key.equals("e")) {
            parsedKey = Key.e;
        } else if (key.equals("f")) {
            parsedKey = Key.f;
        } else if (key.equals("g")) {
            parsedKey = Key.g;
        } else if (key.equals("h")) {
            parsedKey = Key.h;
        } else if (key.equals("i")) {
            parsedKey = Key.i;
        } else if (key.equals("j")) {
            parsedKey = Key.j;
        } else if (key.equals("k")) {
            parsedKey = Key.k;
        } else if (key.equals("l")) {
            parsedKey = Key.l;
        } else if (key.equals("m")) {
            parsedKey = Key.m;
        } else if (key.equals("n")) {
            parsedKey = Key.n;
        } else if (key.equals("o")) {
            parsedKey = Key.o;
        } else if (key.equals("p")) {
            parsedKey = Key.p;
        } else if (key.equals("q")) {
            parsedKey = Key.q;
        } else if (key.equals("r")) {
            parsedKey = Key.r;
        } else if (key.equals("s")) {
            parsedKey = Key.s;
        } else if (key.equals("t")) {
            parsedKey = Key.t;
        } else if (key.equals("u")) {
            parsedKey = Key.u;
        } else if (key.equals("v")) {
            parsedKey = Key.v;
        } else if (key.equals("w")) {
            parsedKey = Key.w;
        } else if (key.equals("x")) {
            parsedKey = Key.x;
        } else if (key.equals("z")) {
            parsedKey = Key.z;
        } else if (key.equals("y")) {
            parsedKey = Key.y;
        } else if (key.equals("1") || key.equals("one")) {
            parsedKey = Key.n1;
        } else if (key.equals("2") || key.equals("two")) {
            parsedKey = Key.n2;
        } else if (key.equals("3") || key.equals("three")) {
            parsedKey = Key.n3;
        } else if (key.equals("4") || key.equals("four")) {
            parsedKey = Key.n4;
        } else if (key.equals("5") || key.equals("five")) {
            parsedKey = Key.n5;
        } else if (key.equals("6") || key.equals("six")) {
            parsedKey = Key.n6;
        } else if (key.equals("7") || key.equals("seven")) {
            parsedKey = Key.n7;
        } else if (key.equals("8") || key.equals("eight")) {
            parsedKey = Key.n8;
        } else if (key.equals("9") || key.equals("nine")) {
            parsedKey = Key.n9;
        } else if (key.equals("0") || key.equals("zero")) {
            parsedKey = Key.n0;
        } else if (key.equals("enter")) {
            parsedKey = Key.enter;
        } else if (key.equals("esc") || key.equals("escape")) {
            parsedKey = Key.esc;
        } else if (key.equals("back") || key.equals("backspace")) {
            parsedKey = Key.backspace;
        } else if (key.equals("tab")) {
            parsedKey = Key.tab;
        } else if (key.equals("space")) {
            parsedKey = Key.space;
        } else if (key.equals("tilde") || key.equals("~")) {
            parsedKey = Key.tilde;
        } else if (key.equals("minus") || key.equals("-")) {
            parsedKey = Key.minus;
        } else if (key.equals("equal") || key.equals("=")) {
            parsedKey = Key.equal;
        } else if (key.equals("open_bracket") || key.equals("[")) {
            parsedKey = Key.open_bracket;
        } else if (key.equals("close_bracket") || key.equals("]")) {
            parsedKey = Key.close_bracket;
        } else if (key.equals("backslash") || key.equals("\\")) {
            parsedKey = Key.backslash;
        } else if (key.equals("semicolon") || key.equals(";")) {
            parsedKey = Key.semicolon;
        } else if (key.equals("quote") || key.equals("\"")) {
            parsedKey = Key.quote;
        } else if (key.equals("dollar") || key.equals("$")) {
            parsedKey = Key.dollar;
        } else if (key.equals("comma") || key.equals(",")) {
            parsedKey = Key.comma;
        } else if (key.equals("period") || key.equals(".")) {
            parsedKey = Key.period;
        } else if (key.equals("slash") || key.equals("/")) {
            parsedKey = Key.slash;
        } else if (key.equals("caps_lock") || key.equals("capslock")) {
            parsedKey = Key.caps_lock;
        } else if (key.equals("f1")) {
            parsedKey = Key.f1;
        } else if (key.equals("f2")) {
            parsedKey = Key.f2;
        } else if (key.equals("f3")) {
            parsedKey = Key.f3;
        } else if (key.equals("f4")) {
            parsedKey = Key.f4;
        } else if (key.equals("f5")) {
            parsedKey = Key.f5;
        } else if (key.equals("f6")) {
            parsedKey = Key.f6;
        } else if (key.equals("f7")) {
            parsedKey = Key.f7;
        } else if (key.equals("f8")) {
            parsedKey = Key.f8;
        } else if (key.equals("f9")) {
            parsedKey = Key.f9;
        } else if (key.equals("f10")) {
            parsedKey = Key.f10;
        } else if (key.equals("f11")) {
            parsedKey = Key.f11;
        } else if (key.equals("f12")) {
            parsedKey = Key.f12;
        } else if (key.equals("print_screen") || key.equals("printscreen") || key.equals("printscr") || key.equals("print")) {
            parsedKey = Key.print_screen;
        } else if (key.equals("scroll_lock") || key.equals("scrolllock")) {
            parsedKey = Key.scroll_lock;
        } else if (key.equals("pause_break") || key.equals("pausebreak") || key.equals("pause") || key.equals("break")) {
            parsedKey = Key.pause_break;
        } else if (key.equals("insert") || key.equals("ins")) {
            parsedKey = Key.insert;
        } else if (key.equals("home")) {
            parsedKey = Key.home;
        } else if (key.equals("page_up") || key.equals("pageup")) {
            parsedKey = Key.page_up;
        } else if (key.equals("delete") || key.equals("del")) {
            parsedKey = Key.del;
        } else if (key.equals("end")) {
            parsedKey = Key.end;
        } else if (key.equals("page_down") || key.equals("pagedown")) {
            parsedKey = Key.page_down;
        } else if (key.equals("arrow_right") || key.equals("arrowright") || key.equals("right")) {
            parsedKey = Key.arrow_right;
        } else if (key.equals("arrow_left") || key.equals("arrowleft") || key.equals("left")) {
            parsedKey = Key.arrow_left;
        } else if (key.equals("arrow_bottom") || key.equals("arrowbottom") || key.equals("bottom")) {
            parsedKey = Key.arrow_bottom;
        } else if (key.equals("arrow_top") || key.equals("arrowtop") || key.equals("top")) {
            parsedKey = Key.arrow_top;
        } else if (key.equals("num_lock") || key.equals("numlock")) {
            parsedKey = Key.num_lock;
        } else if (key.equals("num/") || key.equals("num_slash") || key.equals("numslash")) {
            parsedKey = Key.num_slash;
        } else if (key.equals("num*") || key.equals("num_asterisk") || key.equals("numasterisk")) {
            parsedKey = Key.num_asterisk;
        } else if (key.equals("num-") || key.equals("num_minus") || key.equals("numminus")) {
            parsedKey = Key.num_minus;
        } else if (key.equals("num+") || key.equals("num_plus") || key.equals("numplus")) {
            parsedKey = Key.num_plus;
        } else if (key.equals("numenter")) {
            parsedKey = Key.num_enter;
        } else if (key.equals("num1") || key.equals("num_1")) {
            parsedKey = Key.num_1;
        } else if (key.equals("num2") || key.equals("num_2")) {
            parsedKey = Key.num_2;
        } else if (key.equals("num3") || key.equals("num_3")) {
            parsedKey = Key.num_3;
        } else if (key.equals("num4") || key.equals("num_4")) {
            parsedKey = Key.num_4;
        } else if (key.equals("num5") || key.equals("num_5")) {
            parsedKey = Key.num_5;
        } else if (key.equals("num6") || key.equals("num_6")) {
            parsedKey = Key.num_6;
        } else if (key.equals("num7") || key.equals("num_7")) {
            parsedKey = Key.num_7;
        } else if (key.equals("num8") || key.equals("num_8")) {
            parsedKey = Key.num_8;
        } else if (key.equals("num9") || key.equals("num_9")) {
            parsedKey = Key.num_9;
        } else if (key.equals("num0") || key.equals("num_0")) {
            parsedKey = Key.num_0;
        } else if (key.equals("num.") || key.equals("num_period") || key.equals("numperiod")) {
            parsedKey = Key.num_dot;
        } else if (key.equals("intl_backslash") || key.equals("<")) {
            parsedKey = Key.intl_backslash;
        } else if (key.equals("menu")) {
            parsedKey = Key.menu;
        } else if (key.equals("ctrl_left") || key.equals("ctrlleft") || key.equals("ctrll")) {
            parsedKey = Key.ctrl_left;
        } else if (key.equals("shift_left") || key.equals("shiftleft") || key.equals("shiftl")) {
            parsedKey = Key.shift_left;
        } else if (key.equals("alt_left") || key.equals("altleft") || key.equals("altl")) {
            parsedKey = Key.alt_left;
        } else if (key.equals("win_left") || key.equals("winleft") || key.equals("winl")) {
            parsedKey = Key.win_left;
        } else if (key.equals("meta_left") || key.equals("metaleft") || key.equals("metal")) {
            parsedKey = Key.win_left;
        } else if (key.equals("ctrl_right") || key.equals("ctrlright") || key.equals("ctrlr")) {
            parsedKey = Key.ctrl_right;
        } else if (key.equals("shift_right") || key.equals("shiftright") || key.equals("shiftr")) {
            parsedKey = Key.shift_right;
        } else if (key.equals("alt_right") || key.equals("altright") || key.equals("altr") || key.equals("altgr")) {
            parsedKey = Key.alt_right;
        } else if (key.equals("win_right") || key.equals("winright") || key.equals("winr")) {
            parsedKey = Key.win_right;
        } else if (key.equals("meta_right") || key.equals("metaright") || key.equals("metar")) {
            parsedKey = Key.win_right;
        } else {
            parsedKey = Key.n1;
        };
        return getKeyAddress(parsedKey);
    }

    KeyGroup parseKeyGroup(String key) {
        if (key.equals("logo")) {
            return KeyGroup.logo;
        } else if (key.equals("gKeys")) {
            return KeyGroup.gKeys;
        } else if (key.equals("indicators")) {
            return KeyGroup.indicators;
        } else if (key.equals("multimedia")) {
            return KeyGroup.multimedia;
        } else if (key.equals("fkeys")) {
            return KeyGroup.fkeys;
        } else if (key.equals("modifiers")) {
            return KeyGroup.modifiers;
        } else if (key.equals("arrows")) {
            return KeyGroup.arrows;
        } else if (key.equals("numeric")) {
            return KeyGroup.numeric;
        } else if (key.equals("functions")) {
            return KeyGroup.functions;
        } else if (key.equals("keys")) {
            return KeyGroup.keys;
        } else {
            return KeyGroup.keys;
        }
    }

    KeyColors parseColor(String color) {
        KeyColors colors = new KeyColors();
        if (color.length() == 2) {
            color = color + "0000";  // For G610
        }
        if (color.length() != 6) {
            color = "ffffff";
        }
        colors.red = (byte) Integer.parseInt(color.substring(0, 2), 16);
        colors.green = (byte) Integer.parseInt(color.substring(2, 4), 16);
        colors.blue = (byte) Integer.parseInt(color.substring(4, 6), 16);
        return colors;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    boolean sendDataInternal(ByteBuffer data, int data_size) {
        if (m_isAttached == false) {
            return false;
        }
        int r;
        if (data_size > 20) {
            r = LibUsb.controlTransfer(dev_handle, (byte) 0x21, (byte) 0x09, (short) 0x0212, (short) 1, data, 2000);
        } else {
            r = LibUsb.controlTransfer(dev_handle, (byte) 0x21, (byte) 0x09, (short) 0x0211, (short) 1, data, 2000);
        }
        try {
            // to sleep 0.001 of a second
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        if (r < 0) {
            return false;
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(64);
        r = LibUsb.interruptTransfer(dev_handle, (byte) 0x82, buffer, BufferUtils.allocateIntBuffer(), 1);
        return true;
    }

    byte[] populateAddressGroupInternal(KeyAddressGroup addressGroup, int data_size) {
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                switch (addressGroup) {
                    case logo:
                        data[0] = (byte) 0x11;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x10;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x02;  // Base address
                        break;
                    case indicators:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x40;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    case multimedia:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x02;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    case keys:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x01;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x0e;  // Base address
                        break;
                    case gKeys:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x04;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x09;  // Base address
                        break;
                    default:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x01;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x0e;  // Base address
                }
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                switch (addressGroup) {
                    case logo:
                        data[0] = (byte) 0x11;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x10;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x01;  // Base address
                        break;
                    case indicators:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x40;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    case multimedia:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x02;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    case keys:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x02;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    default:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x02;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                }
            default:
                switch (addressGroup) {
                    case logo:
                        data[0] = (byte) 0x11;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x10;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x02;  // Base address
                        break;
                    case indicators:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x40;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    case multimedia:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0c;  // Base address
                        data[3] = (byte) 0x3a;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x02;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x05;  // Base address
                        break;
                    case keys:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x01;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x0e;  // Base address
                        break;
                    case gKeys:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x04;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x09;  // Base address
                        break;
                    default:
                        data[0] = (byte) 0x12;  // Base address
                        data[1] = (byte) 0xff;  // Base address
                        data[2] = (byte) 0x0f;  // Base address
                        data[3] = (byte) 0x3c;  // Base address
                        data[4] = (byte) 0x00;  // Base address
                        data[5] = (byte) 0x01;  // Base address
                        data[6] = (byte) 0x00;  // Base address
                        data[7] = (byte) 0x0e;  // Base address
                }
        }
        return data;
    }

    boolean setKeysInternal(KeyAddressGroup addressGroup, KeyValue keyValues[], int keyValueCount) {
        boolean retval = false;
        byte[] data;
        int data_size;
        if (addressGroup == KeyAddressGroup.logo) {
            data_size = 20;
            data = new byte[data_size];
            data = populateAddressGroupInternal(addressGroup, data_size);
            data[8] = keyValues[0].key.id;
            data[9] = keyValues[0].colors.red;
            data[10] = keyValues[0].colors.green;
            data[11] = keyValues[0].colors.blue;
            data[12] = keyValues[1].key.id;
            data[13] = keyValues[1].colors.red;
            data[14] = keyValues[1].colors.green;
            data[15] = keyValues[1].colors.blue;
            for (int i = 16; i < data_size; i++) {
                data[i] = (byte) 0x00;
            }
        } else {
            data_size = 64;
            data = new byte[data_size];
            data = populateAddressGroupInternal(addressGroup, data_size);
            int maxKeyValueCount = (data_size - 8) / 4;
            if (keyValueCount > maxKeyValueCount) {
                keyValueCount = maxKeyValueCount;
            }
            for (int i = 0; i < maxKeyValueCount; i++) {
                if ((i < keyValueCount) && (keyValues[i] != null)) {
                    data[8 + i * 4 + 0] = (byte) keyValues[i].key.id;
                    data[8 + i * 4 + 1] = (byte) keyValues[i].colors.red;
                    data[8 + i * 4 + 2] = (byte) keyValues[i].colors.green;
                    data[8 + i * 4 + 3] = (byte) keyValues[i].colors.blue;
                } else {
                    data[8 + i * 4 + 0] = (byte) 0x00;
                    data[8 + i * 4 + 1] = (byte) 0x00;
                    data[8 + i * 4 + 2] = (byte) 0x00;
                    data[8 + i * 4 + 3] = (byte) 0x00;
                }
            }
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        retval = sendDataInternal(buffer, data_size);
        return retval;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    boolean setPowerOnEffect(PowerOnEffect powerOnEffect) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x5e;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x01;  // Base address
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x5a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x01;  // Base address
                break;
            default:
                break;
        }

        switch (powerOnEffect) {
            case ColourWave:
                data[6] = (byte) 0x01;
                break;
            case FixedColour:
                data[6] = (byte) 0x02;
                break;
        }
        for (int i = 7; i < data_size; i++) {
            data[i] = (byte) 0x00;
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        retval = sendDataInternal(buffer, data_size);
        return retval;
    }

    boolean setKey(KeyValue keyValue) {
        boolean retval = false;
        byte[] data;
        int data_size;
        if (keyValue.key.addressGroup == KeyAddressGroup.logo) {
            data_size = 20;
            data = new byte[data_size];
            data = populateAddressGroupInternal(keyValue.key.addressGroup, data_size);
        } else {
            data_size = 64;
            data = new byte[data_size];
            data = populateAddressGroupInternal(keyValue.key.addressGroup, data_size);
        }
        data[8] = (byte) keyValue.key.id;
        data[9] = (byte) keyValue.colors.red;
        data[10] = (byte) keyValue.colors.green;
        data[11] = (byte) keyValue.colors.blue;
        for (int i = 12; i < data_size; i++) {
            data[i] = (byte) 0x00;
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        retval = sendDataInternal(buffer, data_size);
        return retval;
    }

    boolean setKey(Key key, KeyColors colors) {
        KeyValue keyValue = new KeyValue();
        keyValue.key = getKeyAddress(key);
        keyValue.colors = colors;
        return setKey(keyValue);
    }

    boolean setKeys(String[] keys, KeyColors colors) {
        KeyValue[] keyValues = new KeyValue[keys.length];
        for (int i = 0; i < keys.length; i++) {
            keyValues[i] = new KeyValue();
            keyValues[i].colors = new KeyColors();
            keyValues[i].key = getKeyAddress(Key.valueOf(keys[i]));
            keyValues[i].colors = colors;
        }
        setKeys(keyValues, keys.length);
        return true;
    }

    boolean setKeys(KeyValue keyValue[], int keyValueCount) {
        KeyValue[] logo = new KeyValue[5];
        int logoCount = 0;
        KeyValue[] gKeys = new KeyValue[9];
        int gKeysCount = 0;
        KeyValue[] indicators = new KeyValue[25];
        int indicatorsCount = 0;
        KeyValue[] multimedia = new KeyValue[25];
        int multimediaCount = 0;
        KeyValue[] keys = new KeyValue[200];
        int keysCount = 0;
        for (int i = 0; i < keyValueCount; i++) {
            if (keyValue[i].key.addressGroup == KeyAddressGroup.logo) {
                logo[logoCount] = keyValue[i];
                logoCount++;
            } else if (keyValue[i].key.addressGroup == KeyAddressGroup.gKeys) {
                gKeys[gKeysCount] = keyValue[i];
                gKeysCount++;
            } else if (keyValue[i].key.addressGroup == KeyAddressGroup.indicators) {
                indicators[indicatorsCount] = keyValue[i];
                indicatorsCount++;
            } else if (keyValue[i].key.addressGroup == KeyAddressGroup.multimedia) {
                multimedia[multimediaCount] = keyValue[i];
                multimediaCount++;
            } else if (keyValue[i].key.addressGroup == KeyAddressGroup.keys) {
                keys[keysCount] = keyValue[i];
                keysCount++;
            }
        }

        if (logoCount > 0) {
            setKeysInternal(KeyAddressGroup.logo, logo, logoCount);
        }
        if (gKeysCount > 0) {
            setKeysInternal(KeyAddressGroup.gKeys, gKeys, gKeysCount);
        }
        if (indicatorsCount > 0) {
            setKeysInternal(KeyAddressGroup.indicators, indicators, indicatorsCount);
        }
        if (multimediaCount > 0) {
            setKeysInternal(KeyAddressGroup.multimedia, multimedia, multimediaCount);
        }

        if (keysCount > 0) {
            int maxKeyValueCount = 14;
            for (int i = 0; i < keysCount; i = i + maxKeyValueCount) {
                KeyValue[] keysBlock = new KeyValue[maxKeyValueCount];
                int keysBlockCount = 0;
                for (int j = 0; j < maxKeyValueCount; j++) {
                    keysBlock[j] = keys[i + j];
                    keysBlockCount++;
                }
                setKeysInternal(KeyAddressGroup.keys, keysBlock, keysBlockCount);
            }
        }
        return true;
    }

    boolean setAllKeys(KeyColors colors) {
        KeyValue[] keyValues = new KeyValue[127];
        for (int i = 0; i < 127; i++) {
            keyValues[i] = new KeyValue();
            keyValues[i].colors = new KeyColors();
            keyValues[i].key = getKeyAddress(Key.values()[i]);
            keyValues[i].colors = colors;
        }
        setKeys(keyValues, 127);
        return true;
    }

    boolean setGroupKeys(KeyGroup keyGroup, KeyColors colors) {
        KeyValue[] keyValues = new KeyValue[54];
        int keyValuesCount = 0;
        switch (keyGroup) {
            case logo:
                for (int i = 0; i < 2; i++) {
                    keyValues[i] = new KeyValue();
                    keyValues[i].colors = new KeyColors();
                    keyValues[i].key = getKeyAddress(Key.values()[i]);
                    keyValues[i].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case gKeys:
                for (int i = 2; i < 11; i++) {
                    keyValues[i - 2] = new KeyValue();
                    keyValues[i - 2].colors = new KeyColors();
                    keyValues[i - 2].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 2].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case indicators:
                for (int i = 11; i < 16; i++) {
                    keyValues[i - 11] = new KeyValue();
                    keyValues[i - 11].colors = new KeyColors();
                    keyValues[i - 11].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 11].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case multimedia:
                for (int i = 16; i < 21; i++) {
                    keyValues[i - 16] = new KeyValue();
                    keyValues[i - 16].colors = new KeyColors();
                    keyValues[i - 16].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 16].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case fkeys:
                for (int i = 21; i < 33; i++) {
                    keyValues[i - 21] = new KeyValue();
                    keyValues[i - 21].colors = new KeyColors();
                    keyValues[i - 21].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 21].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case modifiers:
                for (int i = 33; i < 42; i++) {
                    keyValues[i - 33] = new KeyValue();
                    keyValues[i - 33].colors = new KeyColors();
                    keyValues[i - 33].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 33].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case arrows:
                for (int i = 42; i < 46; i++) {
                    keyValues[i - 42] = new KeyValue();
                    keyValues[i - 42].colors = new KeyColors();
                    keyValues[i - 42].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 42].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case numeric:
                for (int i = 46; i < 63; i++) {
                    keyValues[i - 46] = new KeyValue();
                    keyValues[i - 46].colors = new KeyColors();
                    keyValues[i - 46].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 46].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case functions:
                for (int i = 63; i < 73; i++) {
                    keyValues[i - 63] = new KeyValue();
                    keyValues[i - 63].colors = new KeyColors();
                    keyValues[i - 63].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 63].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
            case keys:
                for (int i = 73; i < 127; i++) {
                    keyValues[i - 73] = new KeyValue();
                    keyValues[i - 73].colors = new KeyColors();
                    keyValues[i - 73].key = getKeyAddress(Key.values()[i]);
                    keyValues[i - 73].colors = colors;
                    keyValuesCount++;
                }
                setKeys(keyValues, keyValuesCount);
                break;
        }
        return true;
    }

    void setProfile(String[] profile) {
        KeyValue[] keyValues = new KeyValue[127];
        for (int i = 0; i < 127; i++) {
            keyValues[i] = new KeyValue();
            keyValues[i].colors = new KeyColors();
            keyValues[i].key = getKeyAddress(Key.values()[i]);
            keyValues[i].colors = parseColor(profile[i]);
        }
        setKeys(keyValues, 127);
    }

    void setProfile(String[][] profile) {
        KeyValue[] keyValues = new KeyValue[profile[0].length];
        for (int i = 0; i < profile[0].length; i++) {
            keyValues[i] = new KeyValue();
            keyValues[i].colors = new KeyColors();
            keyValues[i].key = getKeyAddress(Key.valueOf(profile[0][i]));
            keyValues[i].colors = parseColor(profile[1][i]);
        }
        setKeys(keyValues, profile[0].length);
    }

    boolean setFXColor(KeyColors colors) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x01;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) 0x02;
                for (int i = 10; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3c;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x01;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) 0x02;
                for (int i = 10; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(data.length);
        buffer1.put(data);
        retval = sendDataInternal(buffer1, data_size);
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x01;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) 0x02;
                for (int i = 10; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3c;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x01;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) 0x02;
                for (int i = 10; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(data.length);
        buffer2.put(data);
        retval = sendDataInternal(buffer2, data_size);
        return retval;
    }

    boolean setFXBreathing(KeyColors colors, int speed) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x02;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) speed;  // Speed
                data[10] = (byte) 0xe8;  // ???
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x64;
                for (int i = 13; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3c;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x02;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) speed;  // Speed
                data[10] = (byte) 0x10;  // ???
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x64;
                for (int i = 13; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(data.length);
        buffer1.put(data);
        retval = sendDataInternal(buffer1, data_size);

        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x02;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) speed;  // Speed
                data[10] = (byte) 0xe8;  // ???
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x64;
                for (int i = 13; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3c;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x02;  // Base address
                data[6] = colors.red;
                data[7] = colors.green;
                data[8] = colors.blue;
                data[9] = (byte) speed;  // Speed
                data[10] = (byte) 0x10;  // ???
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x64;
                for (int i = 13; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(data.length);
        buffer2.put(data);
        retval = sendDataInternal(buffer2, data_size);
        return retval;
    }

    boolean setFXHWave(int speed) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];

        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x04;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) 0x00;
                data[12] = (byte) 0xe8;
                data[13] = (byte) 0x01;
                data[14] = (byte) 0x64;
                data[15] = (byte) speed; // Speed
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3b;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x04;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x88;
                data[13] = (byte) 0x01;
                data[14] = (byte) 0x64;
                data[15] = (byte) speed; // Speed
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(data.length);
        buffer1.put(data);
        retval = sendDataInternal(buffer1, data_size);

        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0xe8;
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                data[15] = (byte) 0x00;
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3b;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0x88;
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                data[15] = (byte) 0x00;
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(data.length);
        buffer2.put(data);
        retval = sendDataInternal(buffer2, data_size);
        return retval;
    }

    boolean setFXVWave(int speed) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x04;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) 0x00;
                data[12] = (byte) 0xe8;
                data[13] = (byte) 0x02;
                data[14] = (byte) 0x64;
                data[15] = (byte) speed; // Speed
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3b;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x04;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x88;
                data[13] = (byte) 0x02;
                data[14] = (byte) 0x64;
                data[15] = (byte) speed; // Speed
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(data.length);
        buffer1.put(data);
        retval = sendDataInternal(buffer1, data_size);

        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0xe8;
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                data[15] = (byte) 0x00;
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3b;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0x88;
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                data[15] = (byte) 0x00;
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(data.length);
        buffer2.put(data);
        retval = sendDataInternal(buffer2, data_size);
        return retval;
    }

    boolean setFXCWave(int speed) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x04;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x62;
                data[13] = (byte) 0x03;
                data[14] = (byte) 0x64;
                data[15] = (byte) speed; // Speed
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3b;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x04;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) 0x00;
                data[12] = (byte) 0x88;
                data[13] = (byte) 0x03;
                data[14] = (byte) 0x64;
                data[15] = (byte) speed; // Speed
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(data.length);
        buffer1.put(data);
        retval = sendDataInternal(buffer1, data_size);

        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0x62;
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                data[15] = (byte) 0x00;
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3b;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0x88;
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                data[15] = (byte) 0x00;
                for (int i = 16; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(data.length);
        buffer2.put(data);
        retval = sendDataInternal(buffer2, data_size);
        return retval;
    }

    boolean setFXColorCycle(int speed) {
        boolean retval = false;
        int data_size = 20;
        byte[] data = new byte[data_size];
        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0xe8;  // ???
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                for (int i = 15; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Keys
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3c;  // Base address
                data[4] = (byte) 0x00;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0x88;  // ???
                data[13] = (byte) 0x00;
                data[14] = (byte) 0x64;
                for (int i = 15; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(data.length);
        buffer1.put(data);
        retval = sendDataInternal(buffer1, data_size);

        switch (model) {
            case Logi910Spectrum:
            case Logi910Spark:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x10;  // Base address
                data[3] = (byte) 0x3a;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0xe8;  // ???
                data[13] = (byte) 0x64;
                data[14] = (byte) 0x00;
                for (int i = 15; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
            case Logi810Spectrum:
            case Logi610Orion:
            case Logi410AtlasSpectrum:
                // Logo
                data[0] = (byte) 0x11;  // Base address
                data[1] = (byte) 0xff;  // Base address
                data[2] = (byte) 0x0d;  // Base address
                data[3] = (byte) 0x3c;  // Base address
                data[4] = (byte) 0x01;  // Base address
                data[5] = (byte) 0x03;  // Base address
                data[6] = (byte) 0x00;
                data[7] = (byte) 0x00;
                data[8] = (byte) 0x00;
                data[9] = (byte) 0x00;
                data[10] = (byte) 0x00;
                data[11] = (byte) speed; // Speed
                data[12] = (byte) 0x88;  // ???
                data[13] = (byte) 0x00;
                data[14] = (byte) 0x64;
                for (int i = 15; i < data_size; i++) {
                    data[i] = (byte) 0x00;
                }
                break;
        }
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(data.length);
        buffer2.put(data);
        retval = sendDataInternal(buffer2, data_size);
        return retval;
    }
}

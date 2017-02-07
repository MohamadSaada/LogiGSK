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
package Logi.GSeries.Keyboard;

import Logi.GSeries.Libraries.Encryption;
import Logi.GSeries.Libraries.IOOperations;
import Logi.GSeries.Libraries.Keyboard;
import Logi.GSeries.Libraries.Settings;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.HotplugCallback;
import org.usb4java.HotplugCallbackHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Arrays;

/**
 *
 * @author Mohamad Saada
 */
public class LogiGSK {

    public static MainJFrame mainJFrame;
    public static String frameType = "Disconnected";
    public static String frameSubType = "US_QWERTY";
    public static final String[] keyboardsProductNumbers
            = new String[]{"c335", "c32b", "c331", "c337", "c330", "c333", "c338"};
    public static G910SpectrumUSQWERTY mFrameG910SpectrumUSQWERTY;
    public static G910SpectrumUKQWERTY mFrameG910SpectrumUKQWERTY;
    public static G910SparkUSQWERTY mFrameG910SparkUSQWERTY;
    public static G910SparkUKQWERTY mFrameG910SparkUKQWERTY;
    public static G810SpectrumUSQWERTY mFrameG810SpectrumUSQWERTY;
    //public static G810SpectrumUKQWERTY mFrameG810SpectrumUKQWERTY;
    public static G610OrionUSQWERTY mFrameG610OrionUSQWERTY;
    //public static G610OrionUKQWERTY mFrameG610OrionUKQWERTY;
    public static G410AtlasSpectrumUSQWERTY mFrameG410AtlasSpectrumUSQWERTY;
    //public static G410AtlasSpectrumUKQWERTY mFrameG410AtlasSpectrumUKQWERTY;
    public static EmptyJInternalFrame mFrameEmpty;// = new MainFrameG910SpectrumUKQWERTY();
    public static NotConnectedJInternalFrame mFrameNotConnected;
    public static String serviceStatus = "Running";

    static class EventHandlingThread extends Thread {

        /**
         * If thread should abort.
         */
        private volatile boolean abort;

        /**
         * Aborts the event handling thread.
         */
        public void abort() {
            this.abort = true;
        }

        @Override
        public void run() {
            while (!this.abort) {
                // Let libusb handle pending events. This blocks until events
                // have been handled, a hotplug callback has been deregistered
                // or the specified time of 1 second (Specified in
                // Microseconds) has passed.
                int result = LibUsb.handleEventsTimeout(null, 1000000);
                if (result != LibUsb.SUCCESS) {
                    throw new LibUsbException("Unable to handle events", result);
                }
            }
        }
    }

    /**
     * The hotplug callback handler
     */
    static class Callback implements HotplugCallback {

        @Override
        public int processEvent(Context context, Device device, int event,
                Object userData) {
            DeviceDescriptor descriptor = new DeviceDescriptor();
            int result = LibUsb.getDeviceDescriptor(device, descriptor);
            if (result != LibUsb.SUCCESS) {
                throw new LibUsbException("Unable to read device descriptor",
                        result);
            }
            Keyboard.KeyboardModel[] keyboardModel = Keyboard.getKeyboardModel();
            //problem arises if we had two logitech g keyboard connected and one got disconnected
            if ((keyboardModel.length > 1) || (keyboardModel[0] != Keyboard.KeyboardModel.NoLogiKeyboard)) { //check if there is any keyboard connected
                if ((event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED) && (descriptor.idVendor() == 0x046d)
                        && Arrays.asList(keyboardsProductNumbers).contains(String.format("%04x", descriptor.idProduct()))) {
                    if (frameType.equals("Disconnected")) {
                        System.out.format("%s: %04x:%04x%n",
                                event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED
                                        ? "Connected" : "Disconnected",
                                descriptor.idVendor(), descriptor.idProduct());
                        if (keyboardModel.length > 1) {
                            //display dialog msg to indicate that program only supports one keyboard connected at a time
                            return 0;
                        }
                        switch (keyboardModel[0]) {
                            case Logi910Spectrum:
                                frameType = "G910Spectrum";
                                switch (frameSubType) {
                                    case "US_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG910SpectrumUSQWERTY = new G910SpectrumUSQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spectrum US");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG910SpectrumUSQWERTY);
                                        mFrameG910SpectrumUSQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SpectrumUSQWERTY, mFrameG910SpectrumUSQWERTY);
                                        checkMenuOptions();
                                        break;
                                    case "UK_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG910SpectrumUKQWERTY = new G910SpectrumUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spectrum UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG910SpectrumUKQWERTY);
                                        mFrameG910SpectrumUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SpectrumUKQWERTY, mFrameG910SpectrumUKQWERTY);
                                        checkMenuOptions();
                                        break;
                                    default:
                                        //display dialog to choose from supported layouts for this keyboard model
                                        break;
                                }
                                break;
                            case Logi910Spark:
                                frameType = "G910Spark";
                                switch (frameSubType) {
                                    case "US_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG910SparkUSQWERTY = new G910SparkUSQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spark US");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG910SparkUSQWERTY);
                                        mFrameG910SparkUSQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SparkUSQWERTY, mFrameG910SparkUSQWERTY);
                                        checkMenuOptions();
                                        break;
                                    case "UK_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG910SparkUKQWERTY = new G910SparkUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spark UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG910SparkUKQWERTY);
                                        mFrameG910SparkUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SparkUKQWERTY, mFrameG910SparkUKQWERTY);
                                        checkMenuOptions();
                                        break;
                                    default:
                                        //display dialog to choose from supported layouts for this keyboard model
                                        break;
                                }
                                break;
                            case Logi810Spectrum:
                                frameType = "G810Spectrum";
                                switch (frameSubType) {
                                    case "US_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG810SpectrumUSQWERTY = new G810SpectrumUSQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G810 Spectrum US");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG810SpectrumUSQWERTY);
                                        mFrameG810SpectrumUSQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi810SpectrumUSQWERTY, mFrameG810SpectrumUSQWERTY);
                                        checkMenuOptions();
                                        break;
                                    case "UK_QWERTY":
                                        /*pauseServiceOperations();
                                        mFrameG810SpectrumUKQWERTY = new G810SpectrumUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G810 Spectrum UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG810SpectrumUKQWERTY);
                                        mFrameG810SpectrumUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi810SpectrumUKQWERTY, mFrameG810SpectrumUKQWERTY);
                                        checkMenuOptions();*/
                                        break;
                                    default:
                                        //display dialog to choose from supported layouts for this keyboard model
                                        break;
                                }
                                break;
                            case Logi610Orion:
                                frameType = "G610Orion";
                                switch (frameSubType) {
                                    case "US_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG610OrionUSQWERTY = new G610OrionUSQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G610 Orion US");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG610OrionUSQWERTY);
                                        mFrameG610OrionUSQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi610OrionUSQWERTY, mFrameG610OrionUSQWERTY);
                                        checkMenuOptions();
                                        break;
                                    case "UK_QWERTY":
                                        /*pauseServiceOperations();
                                        mFrameG610OrionUKQWERTY = new G610OrionUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G610 Orion UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG610OrionUKQWERTY);
                                        mFrameG610OrionUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi610OrionUKQWERTY, mFrameG610OrionUKQWERTY);
                                        checkMenuOptions();*/
                                        break;
                                    default:
                                        //display dialog to choose from supported layouts for this keyboard model
                                        break;
                                }
                                break;
                            case Logi410AtlasSpectrum:
                                frameType = "G410AtlasSpectrum";
                                switch (frameSubType) {
                                    case "US_QWERTY":
                                        pauseServiceOperations();
                                        mFrameG410AtlasSpectrumUSQWERTY = new G410AtlasSpectrumUSQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G410 Atlas Spectrum US");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG410AtlasSpectrumUSQWERTY);
                                        mFrameG410AtlasSpectrumUSQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY, mFrameG410AtlasSpectrumUSQWERTY);
                                        checkMenuOptions();
                                        break;
                                    case "UK_QWERTY":
                                        /*pauseServiceOperations();
                                        mFrameG410AtlasSpectrumUKQWERTY = new G410AtlasSpectrumUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G410 Atlas Spectrum UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG410AtlasSpectrumUKQWERTY);
                                        mFrameG410AtlasSpectrumUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUKQWERTY, mFrameG410AtlasSpectrumUKQWERTY);
                                        checkMenuOptions();*/
                                        break;
                                    default:
                                        //display dialog to choose from supported layouts for this keyboard model
                                        break;
                                }
                                break;
                            case NoLogiKeyboard:
                                frameType = "Disconnected";
                                mFrameNotConnected = new NotConnectedJInternalFrame();
                                mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                                mFrameNotConnected.setVisible(true);
                                mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                                 {
                                    try {
                                        mFrameNotConnected.setMaximum(true);
                                    } catch (PropertyVetoException ex) {
                                        Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;
                            default:
                                frameType = "Disconnected";
                                mFrameNotConnected = new NotConnectedJInternalFrame();
                                mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                                mFrameNotConnected.setVisible(true);
                                mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                                 {
                                    try {
                                        mFrameNotConnected.setMaximum(true);
                                    } catch (PropertyVetoException ex) {
                                        Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;
                        }
                    } else {
                        //display dialog that indicates that only one keyboard can be supported at a time
                    }
                }
            } else if ((event == LibUsb.HOTPLUG_EVENT_DEVICE_LEFT) && (keyboardModel.length == 1) && (keyboardModel[0] == Keyboard.KeyboardModel.NoLogiKeyboard)) {
                if (!frameType.equals("Disconnected")) {
                    switch (frameType) {
                        case "G910Spectrum":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG910SpectrumUSQWERTY != null) && (mFrameG910SpectrumUSQWERTY.isVisible())) {
                                        mFrameG910SpectrumUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SpectrumUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SpectrumUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    if ((mFrameG910SpectrumUKQWERTY != null) && (mFrameG910SpectrumUKQWERTY.isVisible())) {
                                        mFrameG910SpectrumUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SpectrumUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SpectrumUKQWERTY.dispose();
                                    }
                                    break;
                            }
                            break;
                        case "G910Spark":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG910SparkUSQWERTY != null) && (mFrameG910SparkUSQWERTY.isVisible())) {
                                        mFrameG910SparkUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SparkUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SparkUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    if ((mFrameG910SparkUKQWERTY != null) && (mFrameG910SparkUKQWERTY.isVisible())) {
                                        mFrameG910SparkUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SparkUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SparkUKQWERTY.dispose();
                                    }
                                    break;
                            }
                            break;
                        case "G810Spectrum":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG810SpectrumUSQWERTY != null) && (mFrameG810SpectrumUSQWERTY.isVisible())) {
                                        mFrameG810SpectrumUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG810SpectrumUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG810SpectrumUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    /*if ((mFrameG810SpectrumUKQWERTY != null) && (mFrameG810SpectrumUKQWERTY.isVisible())) {
                                        mFrameG810SpectrumUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG810SpectrumUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG810SpectrumUKQWERTY.dispose();
                                    }*/
                                    break;
                            }
                            break;
                        case "G610Orion":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG610OrionUSQWERTY != null) && (mFrameG610OrionUSQWERTY.isVisible())) {
                                        mFrameG610OrionUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG610OrionUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG610OrionUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    /*if ((mFrameG610OrionUKQWERTY != null) && (mFrameG610OrionUKQWERTY.isVisible())) {
                                        mFrameG610OrionUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG610OrionUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG610OrionUKQWERTY.dispose();
                                    }*/
                                    break;
                            }
                            break;
                        case "G410AtlasSpectrum":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG410AtlasSpectrumUSQWERTY != null) && (mFrameG410AtlasSpectrumUSQWERTY.isVisible())) {
                                        mFrameG410AtlasSpectrumUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG410AtlasSpectrumUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG410AtlasSpectrumUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    /*if ((mFrameG410AtlasSpectrumUKQWERTY != null) && (mFrameG410AtlasSpectrumUKQWERTY.isVisible())) {
                                        mFrameG410AtlasSpectrumUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG410AtlasSpectrumUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG410AtlasSpectrumUKQWERTY.dispose();
                                    }*/
                                    break;
                            }
                            break;
                        default:
                            frameType = "Disconnected";
                            break;
                    }
                    frameType = "Disconnected";
                    mFrameNotConnected = new NotConnectedJInternalFrame();
                    mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                    mFrameNotConnected.setVisible(true);
                    mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                    {
                        try {
                            mFrameNotConnected.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                        mFrameEmpty.setVisible(false);
                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                        mFrameEmpty.dispose();
                    }
                    if (mFrameNotConnected == null) {
                        frameType = "Disconnected";
                        mFrameNotConnected = new NotConnectedJInternalFrame();
                        mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                        mFrameNotConnected.setVisible(true);
                        mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                        {
                            try {
                                mFrameNotConnected.setMaximum(true);
                            } catch (PropertyVetoException ex) {
                                Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            } else if ((keyboardModel.length == 1) && (keyboardModel[0] == Keyboard.KeyboardModel.NoLogiKeyboard)) {
                if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                    mFrameEmpty.setVisible(false);
                    mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                    mFrameEmpty.dispose();
                }
                if (mFrameNotConnected == null) {
                    frameType = "Disconnected";
                    mFrameNotConnected = new NotConnectedJInternalFrame();
                    mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                    mFrameNotConnected.setVisible(true);
                    mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                    {
                        try {
                            mFrameNotConnected.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            return 0;
        }
    }

    private static boolean lockInstance(final String lockFile) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception e) {
                            System.out.println("Unable to remove lock file: " + lockFile);
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            System.out.println("Unable to create and/or lock file: " + lockFile);
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!lockInstance(IOOperations.getLocalDataDirectoryPath() + "lock")) {
            System.exit(0);
        }
        // Initialize the libusb context
        mainJFrame = new MainJFrame();
        mainJFrame.setTitle("LogiGSK Configration Tool");
        mainJFrame.setIconImage((new javax.swing.ImageIcon(MainJFrame.class.getResource("/Logi/GSeries/Keyboard/Resources/Images/GSK_Icon.png")).getImage()));
        mainJFrame.setVisible(true);
        mainJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainJFrame.setTitle("LogiGSK Configration Tool - Loading...");
        mFrameEmpty = new EmptyJInternalFrame();
        mFrameEmpty.setVisible(true);
        mainJFrame.jDesktopPaneMain.add(mFrameEmpty);
        {
            try {
                mFrameEmpty.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        frameSubType = getSetKeyboardLayout();
        if (frameSubType == null) {
            initiateFirstTimeSettings();
            frameSubType = getSetKeyboardLayout();
        }

        int result = LibUsb.init(null);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to initialize libusb", result);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (serviceStatus.equals("Paused")) {
                    resumeServiceOperations();
                }
            }
        }));

        // Check if hotplug is available
        if (!LibUsb.hasCapability(LibUsb.CAP_HAS_HOTPLUG) && false) {
            System.err.println("libusb doesn't support hotplug on this system");
            //System.exit(1);
            Keyboard.KeyboardModel[] keyboardModel = Keyboard.getKeyboardModel();
            //problem arises if we had two logitech g keyboard connected and one got disconnected
            if ((keyboardModel.length > 1) || (keyboardModel[0] != Keyboard.KeyboardModel.NoLogiKeyboard)) { //check if there is any keyboard connected
                if (frameType.equals("Disconnected")) {
                    if (keyboardModel.length > 1) {
                        //display dialog msg to indicate that program only supports one keyboard connected at a time
                        return;
                    }
                    switch (keyboardModel[0]) {
                        case Logi910Spectrum:
                            frameType = "G910Spectrum";
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG910SpectrumUSQWERTY = new G910SpectrumUSQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spectrum US");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG910SpectrumUSQWERTY);
                                    mFrameG910SpectrumUSQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SpectrumUSQWERTY, mFrameG910SpectrumUSQWERTY);
                                    checkMenuOptions();
                                    break;
                                case "UK_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG910SpectrumUKQWERTY = new G910SpectrumUKQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spectrum UK");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG910SpectrumUKQWERTY);
                                    mFrameG910SpectrumUKQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SpectrumUKQWERTY, mFrameG910SpectrumUKQWERTY);
                                    checkMenuOptions();
                                    break;
                                default:
                                    //display dialog to choose from supported layouts for this keyboard model
                                    break;
                            }
                            break;
                        case Logi910Spark:
                            frameType = "G910Spark";
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG910SparkUSQWERTY = new G910SparkUSQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spark US");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG910SparkUSQWERTY);
                                    mFrameG910SparkUSQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SparkUSQWERTY, mFrameG910SparkUSQWERTY);
                                    checkMenuOptions();
                                    break;
                                case "UK_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG910SparkUKQWERTY = new G910SparkUKQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G910 Spark UK");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG910SparkUKQWERTY);
                                    mFrameG910SparkUKQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi910SparkUKQWERTY, mFrameG910SparkUKQWERTY);
                                    checkMenuOptions();
                                    break;
                                default:
                                    //display dialog to choose from supported layouts for this keyboard model
                                    break;
                            }
                            break;
                        case Logi810Spectrum:
                            frameType = "G810Spectrum";
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG810SpectrumUSQWERTY = new G810SpectrumUSQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G810 Spectrum US");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG810SpectrumUSQWERTY);
                                    mFrameG810SpectrumUSQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi810SpectrumUSQWERTY, mFrameG810SpectrumUSQWERTY);
                                    checkMenuOptions();
                                    break;
                                case "UK_QWERTY":
                                    /*pauseServiceOperations();
                                        mFrameG810SpectrumUKQWERTY = new G810SpectrumUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G810 Spectrum UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG810SpectrumUKQWERTY);
                                        mFrameG810SpectrumUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi810SpectrumUKQWERTY, mFrameG810SpectrumUKQWERTY);
                                        checkMenuOptions();*/
                                    break;
                                default:
                                    //display dialog to choose from supported layouts for this keyboard model
                                    break;
                            }
                            break;
                        case Logi610Orion:
                            frameType = "G610Orion";
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG610OrionUSQWERTY = new G610OrionUSQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G610 Orion US");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG610OrionUSQWERTY);
                                    mFrameG610OrionUSQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi610OrionUSQWERTY, mFrameG610OrionUSQWERTY);
                                    checkMenuOptions();
                                    break;
                                case "UK_QWERTY":
                                    /*pauseServiceOperations();
                                        mFrameG610OrionUKQWERTY = new G610OrionUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G610 Orion UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG610OrionUKQWERTY);
                                        mFrameG610OrionUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi610OrionUKQWERTY, mFrameG610OrionUKQWERTY);
                                        checkMenuOptions();*/
                                    break;
                                default:
                                    //display dialog to choose from supported layouts for this keyboard model
                                    break;
                            }
                            break;
                        case Logi410AtlasSpectrum:
                            frameType = "G410AtlasSpectrum";
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    pauseServiceOperations();
                                    mFrameG410AtlasSpectrumUSQWERTY = new G410AtlasSpectrumUSQWERTY();
                                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                        mFrameEmpty.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                        mFrameEmpty.dispose();
                                    }
                                    if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                        mFrameNotConnected.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                        mFrameNotConnected.dispose();
                                    }
                                    mainJFrame.setTitle("LogiGSK Configration Tool - G410 Atlas Spectrum US");
                                    mainJFrame.jDesktopPaneMain.add(mFrameG410AtlasSpectrumUSQWERTY);
                                    mFrameG410AtlasSpectrumUSQWERTY.setVisible(true);
                                    mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY, mFrameG410AtlasSpectrumUSQWERTY);
                                    checkMenuOptions();
                                    break;
                                case "UK_QWERTY":
                                    /*pauseServiceOperations();
                                        mFrameG410AtlasSpectrumUKQWERTY = new G410AtlasSpectrumUKQWERTY();
                                        if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                                            mFrameEmpty.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                                            mFrameEmpty.dispose();
                                        }
                                        if ((mFrameNotConnected != null) && (mFrameNotConnected.isVisible())) {
                                            mFrameNotConnected.setVisible(false);
                                            mainJFrame.jDesktopPaneMain.remove(mFrameNotConnected);
                                            mFrameNotConnected.dispose();
                                        }
                                        mainJFrame.setTitle("LogiGSK Configration Tool - G410 Atlas Spectrum UK");
                                        mainJFrame.jDesktopPaneMain.add(mFrameG410AtlasSpectrumUKQWERTY);
                                        mFrameG410AtlasSpectrumUKQWERTY.setVisible(true);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUKQWERTY, mFrameG410AtlasSpectrumUKQWERTY);
                                        checkMenuOptions();*/
                                    break;
                                default:
                                    //display dialog to choose from supported layouts for this keyboard model
                                    break;
                            }
                            break;
                        case NoLogiKeyboard:
                            frameType = "Disconnected";
                            mFrameNotConnected = new NotConnectedJInternalFrame();
                            mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                            mFrameNotConnected.setVisible(true);
                            mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                             {
                                try {
                                    mFrameNotConnected.setMaximum(true);
                                } catch (PropertyVetoException ex) {
                                    Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        default:
                            frameType = "Disconnected";
                            mFrameNotConnected = new NotConnectedJInternalFrame();
                            mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                            mFrameNotConnected.setVisible(true);
                            mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                             {
                                try {
                                    mFrameNotConnected.setMaximum(true);
                                } catch (PropertyVetoException ex) {
                                    Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                    }
                } else {
                    //display dialog that indicates that only one keyboard can be supported at a time
                }
            } else if ((keyboardModel.length == 1) && (keyboardModel[0] == Keyboard.KeyboardModel.NoLogiKeyboard)) {
                if (!frameType.equals("Disconnected")) {
                    switch (frameType) {
                        case "G910Spectrum":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG910SpectrumUSQWERTY != null) && (mFrameG910SpectrumUSQWERTY.isVisible())) {
                                        mFrameG910SpectrumUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SpectrumUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SpectrumUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    if ((mFrameG910SpectrumUKQWERTY != null) && (mFrameG910SpectrumUKQWERTY.isVisible())) {
                                        mFrameG910SpectrumUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SpectrumUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SpectrumUKQWERTY.dispose();
                                    }
                                    break;
                            }
                            break;
                        case "G910Spark":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG910SparkUSQWERTY != null) && (mFrameG910SparkUSQWERTY.isVisible())) {
                                        mFrameG910SparkUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SparkUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SparkUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    if ((mFrameG910SparkUKQWERTY != null) && (mFrameG910SparkUKQWERTY.isVisible())) {
                                        mFrameG910SparkUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG910SparkUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG910SparkUKQWERTY.dispose();
                                    }
                                    break;
                            }
                            break;
                        case "G810Spectrum":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG810SpectrumUSQWERTY != null) && (mFrameG810SpectrumUSQWERTY.isVisible())) {
                                        mFrameG810SpectrumUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG810SpectrumUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG810SpectrumUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    /*if ((mFrameG810SpectrumUKQWERTY != null) && (mFrameG810SpectrumUKQWERTY.isVisible())) {
                                        mFrameG810SpectrumUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG810SpectrumUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG810SpectrumUKQWERTY.dispose();
                                    }*/
                                    break;
                            }
                            break;
                        case "G610Orion":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG610OrionUSQWERTY != null) && (mFrameG610OrionUSQWERTY.isVisible())) {
                                        mFrameG610OrionUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG610OrionUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG610OrionUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    /*if ((mFrameG610OrionUKQWERTY != null) && (mFrameG610OrionUKQWERTY.isVisible())) {
                                        mFrameG610OrionUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG610OrionUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG610OrionUKQWERTY.dispose();
                                    }*/
                                    break;
                            }
                            break;
                        case "G410AtlasSpectrum":
                            switch (frameSubType) {
                                case "US_QWERTY":
                                    if ((mFrameG410AtlasSpectrumUSQWERTY != null) && (mFrameG410AtlasSpectrumUSQWERTY.isVisible())) {
                                        mFrameG410AtlasSpectrumUSQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG410AtlasSpectrumUSQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG410AtlasSpectrumUSQWERTY.dispose();
                                    }
                                    break;
                                case "UK_QWERTY":
                                    /*if ((mFrameG410AtlasSpectrumUKQWERTY != null) && (mFrameG410AtlasSpectrumUKQWERTY.isVisible())) {
                                        mFrameG410AtlasSpectrumUKQWERTY.setVisible(false);
                                        mainJFrame.jDesktopPaneMain.remove(mFrameG410AtlasSpectrumUKQWERTY);
                                        mainJFrame.setKeyboardFrameAndModel(Keyboard.KeyboardModel.NoLogiKeyboard, null);
                                        checkMenuOptions();
                                        mFrameG410AtlasSpectrumUKQWERTY.dispose();
                                    }*/
                                    break;
                            }
                            break;
                        default:
                            frameType = "Disconnected";
                            break;
                    }
                    frameType = "Disconnected";
                    mFrameNotConnected = new NotConnectedJInternalFrame();
                    mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                    mFrameNotConnected.setVisible(true);
                    mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                    {
                        try {
                            mFrameNotConnected.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                        mFrameEmpty.setVisible(false);
                        mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                        mFrameEmpty.dispose();
                    }
                    if (mFrameNotConnected == null) {
                        frameType = "Disconnected";
                        mFrameNotConnected = new NotConnectedJInternalFrame();
                        mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                        mFrameNotConnected.setVisible(true);
                        mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                        {
                            try {
                                mFrameNotConnected.setMaximum(true);
                            } catch (PropertyVetoException ex) {
                                Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            } else {
                if ((mFrameEmpty != null) && (mFrameEmpty.isVisible())) {
                    mFrameEmpty.setVisible(false);
                    mainJFrame.jDesktopPaneMain.remove(mFrameEmpty);
                    mFrameEmpty.dispose();
                }
                if (mFrameNotConnected == null) {
                    frameType = "Disconnected";
                    mFrameNotConnected = new NotConnectedJInternalFrame();
                    mainJFrame.setTitle("LogiGSK Configration Tool - No Keyboard Found");
                    mFrameNotConnected.setVisible(true);
                    mainJFrame.jDesktopPaneMain.add(mFrameNotConnected);
                    {
                        try {
                            mFrameNotConnected.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } else {
            // Start the event handling thread
            LogiGSK.EventHandlingThread thread = new LogiGSK.EventHandlingThread();
            thread.start();

            // Register the hotplug callback
            HotplugCallbackHandle callbackHandle = new HotplugCallbackHandle();
            result = LibUsb.hotplugRegisterCallback(null,
                    LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED
                    | LibUsb.HOTPLUG_EVENT_DEVICE_LEFT,
                    LibUsb.HOTPLUG_ENUMERATE,
                    LibUsb.HOTPLUG_MATCH_ANY,
                    LibUsb.HOTPLUG_MATCH_ANY,
                    LibUsb.HOTPLUG_MATCH_ANY,
                    new LogiGSK.Callback(), null, callbackHandle);
            if (result != LibUsb.SUCCESS) {
                throw new LibUsbException("Unable to register hotplug callback",
                        result);
            }
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    thread.abort();
                    LibUsb.hotplugDeregisterCallback(null, callbackHandle);
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // Deinitialize the libusb context
                    LibUsb.exit(null);
                }
            }));
        }
    }

    private static void checkMenuOptions() {
        if (mainJFrame.getKeyboardFrame() == null) {
            mainJFrame.jMenuItemLoadProfile.setVisible(false);
            mainJFrame.jMenuItemSaveProfile.setVisible(false);
            mainJFrame.jMenuItemSetProfile.setVisible(false);
            mainJFrame.jMenuItemResetProfile.setVisible(false);
            mainJFrame.jSeparatorLoadSaveSetReset.setVisible(false);
            mainJFrame.jMenuItemLoadProfile.setEnabled(false);
            mainJFrame.jMenuItemSaveProfile.setEnabled(false);
            mainJFrame.jMenuItemSetProfile.setEnabled(false);
            mainJFrame.jMenuItemResetProfile.setEnabled(false);
            return;
        }
        Settings settings = IOOperations.loadCurrentSettingsObjectFromFile();
        if (settings.getShowProfileOptionsFileMenu()) {
            mainJFrame.jMenuItemLoadProfile.setVisible(true);
            mainJFrame.jMenuItemSaveProfile.setVisible(true);
            mainJFrame.jMenuItemSetProfile.setVisible(true);
            mainJFrame.jMenuItemResetProfile.setVisible(true);
            mainJFrame.jSeparatorLoadSaveSetReset.setVisible(true);
            mainJFrame.jMenuItemLoadProfile.setEnabled(true);
            mainJFrame.jMenuItemSaveProfile.setEnabled(true);
            mainJFrame.jMenuItemSetProfile.setEnabled(true);
            mainJFrame.jMenuItemResetProfile.setEnabled(true);
        } else {
            mainJFrame.jMenuItemLoadProfile.setVisible(false);
            mainJFrame.jMenuItemSaveProfile.setVisible(false);
            mainJFrame.jMenuItemSetProfile.setVisible(false);
            mainJFrame.jMenuItemResetProfile.setVisible(false);
            mainJFrame.jSeparatorLoadSaveSetReset.setVisible(false);
            mainJFrame.jMenuItemLoadProfile.setEnabled(false);
            mainJFrame.jMenuItemSaveProfile.setEnabled(false);
            mainJFrame.jMenuItemSetProfile.setEnabled(false);
            mainJFrame.jMenuItemResetProfile.setEnabled(false);
        }
        if (settings.getShowImportExportOptionsFileMenu()) {
            mainJFrame.jMenuItemImportSettings.setVisible(true);
            mainJFrame.jMenuItemExportSettings.setVisible(true);
            mainJFrame.jSeparatorImportExport.setVisible(true);
            mainJFrame.jMenuItemImportSettings.setEnabled(true);
            mainJFrame.jMenuItemExportSettings.setEnabled(true);
        } else {
            mainJFrame.jMenuItemImportSettings.setVisible(false);
            mainJFrame.jMenuItemExportSettings.setVisible(false);
            mainJFrame.jSeparatorImportExport.setVisible(false);
            mainJFrame.jMenuItemImportSettings.setEnabled(false);
            mainJFrame.jMenuItemExportSettings.setEnabled(false);
        }

    }

    private static Keyboard.KeyboardModel[] getKeyboardModel() {
        return Keyboard.getKeyboardModel();
    }

    private static void pauseServiceOperations() {
        Settings settings;
        if (IOOperations.currentSettingsExist()) {
            settings = IOOperations.loadCurrentSettingsObjectFromFile();

        } else {
            settings = new Settings();

        }
        String[] loginParams = new String[2];
        loginParams[0] = settings.getUsername();
        loginParams[1] = new String(settings.getPassword());
        String[] command = new String[5];
        command[0] = "false";
        command[1] = settings.getUsername();
        command[2] = new String(settings.getPassword());
        command[3] = "pause";
        command[4] = Encryption.convertToSemiColonDelimited(loginParams);
        String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
        CommunicationClient client = new CommunicationClient(settings.getPort());
        try {
            String[] response = client.sendCommand(param);
            if (Boolean.parseBoolean(response[0])) {
                serviceStatus = "Paused";
            }
        } catch (IOException ex) {
            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void resumeServiceOperations() {
        Settings settings;
        if (IOOperations.currentSettingsExist()) {
            settings = IOOperations.loadCurrentSettingsObjectFromFile();

        } else {
            settings = new Settings();

        }
        String[] loginParams = new String[2];
        loginParams[0] = settings.getUsername();
        loginParams[1] = new String(settings.getPassword());
        String[] command = new String[5];
        command[0] = "false";
        command[1] = settings.getUsername();
        command[2] = new String(settings.getPassword());
        command[3] = "resume";
        command[4] = Encryption.convertToSemiColonDelimited(loginParams);
        String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
        CommunicationClient client = new CommunicationClient(settings.getPort());
        try {
            String[] response = client.sendCommand(param);
            if (Boolean.parseBoolean(response[0])) {
                serviceStatus = "Running";
            }
        } catch (IOException ex) {
            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getSetKeyboardLayout() {
        String keyboardLayout = null;
        if (IOOperations.currentSettingsExist()) {
            Settings settings = IOOperations.loadCurrentSettingsObjectFromFile();
            keyboardLayout = settings.getLayout().name();
        }
        return keyboardLayout;
    }

    private static void initiateFirstTimeSettings() {
        KeyboardLayoutJDialog keyboardLayoutDialog = new KeyboardLayoutJDialog(mainJFrame, true);
        keyboardLayoutDialog.setLocationRelativeTo(null);
        keyboardLayoutDialog.setVisible(true);
        String keyboardLayout = keyboardLayoutDialog.getSelectedKeyboardLayout();
        keyboardLayoutDialog.dispose();
        Settings settings = new Settings();
        settings.setLayout(Keyboard.KeyboardLayout.valueOf(keyboardLayout));
        settings.generatePassword();
        settings.generateEncryptionPassword();
        IOOperations.saveCurrentSettings(settings);
        boolean serviceRestarted = false;
        int times = 0;
        while (!serviceRestarted) {
            serviceRestarted = IOOperations.checkService();
            try {
                times++;
                Thread.sleep(1000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex1);
            }
            if (times > 10) {
                serviceRestarted = true;
                System.out.println("Failed to contact service after initial setup, please make sure service is running.");
            }
        }
    }
}

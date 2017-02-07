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
package Logi.GSeries.Service;

import Logi.GSeries.Libraries.GKeyboard;
import Logi.GSeries.Libraries.Encryption;
import Logi.GSeries.Libraries.IOOperations;
import Logi.GSeries.Libraries.Effects;
import Logi.GSeries.Libraries.Settings;
import Logi.GSeries.Libraries.StarEffect;
import Logi.GSeries.Libraries.Keyboard;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.HotplugCallback;
import org.usb4java.HotplugCallbackHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.PropertyConfigurator;
import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import javax.swing.JOptionPane;

import org.apache.commons.daemon.*;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author Mohamad Saada
 */
public class LogiGSKService implements Daemon {

    public static final String[] keyboardsProductNumbers
            = new String[]{"c335", "c32b", "c331", "c337", "c330", "c333", "c338"};
    public static String usbCurrently = "unplugged";
    public static boolean systemTrayVisible = false;
    public static Process applicationProcess = null;
    private static boolean running = true;
    static final Logger logger = Logger.getLogger(LogiGSKService.class.getName());
    public static final int FILE_SIZE = 1024 * 1024 * 15;
    private static boolean paused = false;
    static GKeyboard gKeyboard = null;
    private static boolean reloading = false;
    private static String status = "Running";
    public static final URL GSK_Icon = LogiGSKService.class.getResource("/Logi/GSeries/Service/GSK_Icon.png");
    public static SystemTray systemTray = null;
    private ActionListener callbackOpen;
    private ActionListener callbackAbout;
    public static HotPlugSetupThread hotPlugSetupThread = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SystemTray.DEBUG = false;

        Settings settings;
        if (IOOperations.currentSettingsExist()) {
            settings = IOOperations.loadCurrentSettingsObjectFromFile();
        } else {
            settings = new Settings();
        }

        LogiGSKService l = new LogiGSKService();
        if (settings.getShowSystemTray()) {
            l.showSystemTray();
        } else {
            l.hideSystemTray();
        }
        l.begin();
        try {
            String dataFolderPath = IOOperations.getLocalDataDirectoryPath();
            File dataFolder = new File(dataFolderPath);
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }
            String logFolderPath = IOOperations.getLogDirectoryPath();
            File logFolder = new File(logFolderPath);
            if (!logFolder.exists()) {
                logFolder.mkdir();
            }
            FileHandler fileHandler = new FileHandler(logFolderPath + "LogiGSK.log", FILE_SIZE, 3);
            fileHandler.setLevel(Level.ALL);
            logger.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int clientNumber = 0;
        ServerSocket listener = null;
        PropertyConfigurator.configure(LogiGSKService.class.getResource("/Logi/GSeries/Service/log4j.properties"));
        reloading = true;
        boolean firstTime = true;
        while (reloading) {
            listener = null;
            if (reloading && !firstTime) {
                if (IOOperations.currentSettingsExist()) {
                    settings = IOOperations.loadCurrentSettingsObjectFromFile();
                } else {
                    settings = new Settings();
                }
            }
            firstTime = false;
            reloading = false;
            running = true;
            try {
                listener = new ServerSocket(settings.getPort(), 0, InetAddress.getByName(null));
                while (running) {
                    new Manager(listener.accept(), clientNumber++, logger).start();
                }
            } catch (IOException ex) {
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (listener != null) {
                    try {
                        listener.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    void begin() {
        hotPlugSetupThread = new HotPlugSetupThread();
        hotPlugSetupThread.start();
    }

    @Override
    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        System.out.println("initializing ...");
    }

    @Override
    public void start() throws Exception {
        System.out.println("starting ...");
        main(null);
    }

    private static void reload() {
        System.out.println("reloading ...");
        reloading = true;
        running = false;
    }

    private static void restart() {
        System.out.println("restarting ...");
        reloading = true;
        running = false;
        hotPlugSetupThread.abort();
        new LogiGSKService().begin();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stopping ...");
        running = false;
    }

    @Override
    public void destroy() {
        System.out.println("done.");
    }

    private void pauseOperations() {
        stopGKeyboardEffects();
    }

    private void resumeOperations() {
        gKeyboard = new GKeyboard(Keyboard.getKeyboardModel()[0]);
        gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(Keyboard.getKeyboardModel()[0]);
        if (gKeyboard.getEffect() != null) {
            resetEffect();
        } else {
            loadColoursToPhysicalKeyboard(gKeyboard.getKeyColourMap());
        }
    }

    //resuming effects from saved gKeyboard object
    private void resetEffect() {
        switch (gKeyboard.getEffect().CurrentEffect) {
            case NoEffect:
                stopGKeyboardEffects();
                break;
            case FixedColour:
                gKeyboard.setFXColor(gKeyboard.getEffect().FixedColour);
                break;
            case Breathing:
                gKeyboard.setFXBreathing(gKeyboard.getEffect().BreathingColour, gKeyboard.getEffect().BreathingSpeed);
                break;
            case ColourWave:
                switch (gKeyboard.getEffect().ColourWave) {
                    case Horizontal:
                        gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Horizontal;
                        gKeyboard.setFXHWave(gKeyboard.getEffect().ColourWaveSpeed);
                        break;
                    case Vertical:
                        gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Vertical;
                        gKeyboard.setFXVWave(gKeyboard.getEffect().ColourWaveSpeed);
                        break;
                    case CentreOut:
                        gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.CentreOut;
                        gKeyboard.setFXCWave(gKeyboard.getEffect().ColourWaveSpeed);
                        break;
                }
                break;
            case ColourCycle:
                gKeyboard.setFXColorCycle(gKeyboard.getEffect().ColourCycleSpeed);
                break;
            case Stars:
                gKeyboard.setAllKeys(gKeyboard.getEffect().SkyColour, true);
                gKeyboard.setGroupKeys("logo", gKeyboard.getEffect().StarColour, true);
                startStarEffect(gKeyboard.getEffect().StarColour, gKeyboard.getEffect().SkyColour);
                break;
            case Lightning:
                break;
            case KeyPress:
                break;
            default:
                break;
        }
    }

    //start star effect thread
    private void startStarEffect(String starColour, String skyColour) {
        gKeyboard.setStarEffect(new StarEffect());
        gKeyboard.getStarEffect().setKeyboardModel(Keyboard.KeyboardModel.Logi910SpectrumUKQWERTY);
        gKeyboard.getStarEffect().setStarSpeed(2000);
        gKeyboard.getStarEffect().setStarColour(starColour);
        gKeyboard.getStarEffect().setSkyColour(skyColour);
        gKeyboard.getStarEffect().start();
    }

    public void showSystemTray() {
        if ((systemTray != null) && !systemTrayVisible) {
            systemTray.setEnabled(true);
            systemTrayVisible = true;
            return;
        }
        
        try {
            systemTray = SystemTray.getNative();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (systemTray != null) {
            try {
                systemTray.setImage(GSK_Icon);
            } catch (Exception e) {
                System.err.println("Problem setting system tray icon.");
            }
            systemTrayVisible = true;
            callbackOpen = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final MenuItem entry = (MenuItem) e.getSource();
                    try {
                        CommandLine commandLine = new CommandLine("java");
                        commandLine.addArgument("-jar");
                        String jarPath = new URI(LogiGSKService.class.getProtectionDomain()
                                .getCodeSource()
                                .getLocation()
                                .getPath()).getPath();
                        commandLine.addArgument(jarPath, false);
                        DefaultExecutor executor = new DefaultExecutor();
                        executor.setExitValue(1);
                        executor.execute(commandLine, new DefaultExecuteResultHandler());
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            callbackAbout = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final MenuItem entry = (MenuItem) e.getSource();
                    JOptionPane.showMessageDialog(null, "<HTML>LogiGSK V1.0</HTML>", "LogiGSK", JOptionPane.INFORMATION_MESSAGE);
                }
            };

            Menu mainMenu = systemTray.getMenu();

            MenuItem openEntry = new MenuItem("Open", new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final MenuItem entry = (MenuItem) e.getSource();
                    entry.setCallback(callbackOpen);
                    try {
                        CommandLine commandLine = new CommandLine("java");
                        commandLine.addArgument("-jar");
                        String jarPath = new URI(LogiGSKService.class.getProtectionDomain()
                                .getCodeSource()
                                .getLocation()
                                .getPath()).getPath();
                        commandLine.addArgument(jarPath, false);
                        DefaultExecutor executor = new DefaultExecutor();
                        executor.setExitValue(1);
                        executor.execute(commandLine, new DefaultExecuteResultHandler());
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            openEntry.setShortcut('O');
            mainMenu.add(openEntry);
            mainMenu.add(new Separator());

            MenuItem aboutEntry = new MenuItem("About", new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final MenuItem entry = (MenuItem) e.getSource();
                    JOptionPane.showMessageDialog(null, "<HTML>LogiGSK V1.0</HTML>", "LogiGSK", JOptionPane.INFORMATION_MESSAGE);
                    entry.setCallback(callbackAbout);
                }
            });
            openEntry.setShortcut('A');
            mainMenu.add(aboutEntry);
            /*} else {
                System.err.println("System tray is null!");
            }*/
        }
    }

    public void hideSystemTray() {
        //systemTray.shutdown();
        if (systemTray != null) {
            systemTray.setEnabled(false);
            systemTrayVisible = false;
        }
    }

    private static boolean isReallyHeadless() {
        if (GraphicsEnvironment.isHeadless()) {
            //return true;
        }
        try {
            GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
            return screenDevices == null || screenDevices.length == 0;
        } catch (HeadlessException e) {
            System.err.print(e);
            return true;
        }
    }

    public String execToString(String command) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine commandline = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandline);
        return (outputStream.toString());
    }

    //stop star effect thread
    private void stopStarEffect() {
        gKeyboard.getStarEffect().terminate();
        try {
            gKeyboard.getStarEffect().join();
        } catch (InterruptedException ex) {
            Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
        }
        gKeyboard.setStarEffect(null);
    }

    //stopping currently running effect
    private void stopGKeyboardEffects() {
        if ((gKeyboard != null) && (gKeyboard.getEffect() != null)) {
            if (gKeyboard.getEffect().CurrentEffect != Effects.Type.NoEffect) {
                switch (gKeyboard.getEffect().CurrentEffect) {
                    case Stars:
                        if (gKeyboard.getStarEffect() != null) {
                            gKeyboard.getStarEffect().terminate();
                            try {
                                gKeyboard.getStarEffect().join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            gKeyboard.setStarEffect(null);
                            gKeyboard.setAllKeys("000000", true);
                            gKeyboard.getEffect().StarColour = null;
                            gKeyboard.getEffect().SkyColour = null;
                        }
                        break;
                    case Breathing:
                        gKeyboard.setAllKeys("000000", true);
                        gKeyboard.getEffect().BreathingColour = null;
                        gKeyboard.getEffect().BreathingSpeed = 0;
                        break;
                    case FixedColour:
                        gKeyboard.getEffect().FixedColour = null;
                        gKeyboard.setAllKeys("000000", true);
                        break;
                    case ColourCycle:
                        gKeyboard.setAllKeys("000000", true);
                        gKeyboard.getEffect().ColourCycleSpeed = 0;
                        break;
                    case ColourWave:
                        gKeyboard.setAllKeys("000000", true);
                        gKeyboard.getEffect().ColourWave = null;
                        gKeyboard.getEffect().ColourWaveSpeed = 0;
                        break;
                    case KeyPress:
                        break;
                    case Lightning:
                        break;
                }
            }
            gKeyboard.setEffect(null);
            gKeyboard.setAllKeys("000000", true);
        }
    }

    private void loadColoursToPhysicalKeyboard(String[][] colours) {
        gKeyboard.setProfile(colours);
    }

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
    class Callback implements HotplugCallback {

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
            if ((keyboardModel.length > 1) || (keyboardModel[0] != Keyboard.KeyboardModel.NoLogiKeyboard)) { //check if there is any keyboard connected
                if ((event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED) && (descriptor.idVendor() == 0x046d)
                        && Arrays.asList(keyboardsProductNumbers).contains(String.format("%04x", descriptor.idProduct()))) {
                    System.out.format("%s: %04x:%04x%n",
                            event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED
                                    ? "Connected" : "Disconnected",
                            descriptor.idVendor(), descriptor.idProduct());
                    if (keyboardModel.length > 1) {
                        //display dialog msg to indicate that program only supports one keyboard connected at a time
                        System.err.println("Only one keyboard is allowed at a time.");
                        return 0;
                    }
                    if (usbCurrently.equals("unplugged")) {
                        usbCurrently = "plugged";
                        if (status.equals("Running")) {
                            switch (keyboardModel[0]) {
                                case Logi910Spectrum:
                                case Logi910Spark:
                                case Logi810Spectrum:
                                case Logi610Orion:
                                case Logi410AtlasSpectrum:
                                    LogiGSKService.gKeyboard = new GKeyboard(keyboardModel[0]);
                                    LogiGSKService.gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(keyboardModel[0]);
                                    if (LogiGSKService.gKeyboard.getEffect() != null) {
                                        resetEffect();
                                    } else {
                                        loadColoursToPhysicalKeyboard(gKeyboard.getKeyColourMap());
                                    }
                                    break;
                                case NoLogiKeyboard:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            } else if ((event == LibUsb.HOTPLUG_EVENT_DEVICE_LEFT) && (keyboardModel.length == 1) && (keyboardModel[0] == Keyboard.KeyboardModel.NoLogiKeyboard)) {
                usbCurrently = "unplugged";
                stopGKeyboardEffects();

            } else if ((keyboardModel.length == 1) && (keyboardModel[0] == Keyboard.KeyboardModel.NoLogiKeyboard)) {
                usbCurrently = "unplugged";
                System.out.println("No connected Keyboard");
                stopGKeyboardEffects();
            }
            return 0;
        }
    }

    class HotPlugSetupThread extends Thread {

        private LogiGSKService.EventHandlingThread thread = null;

        /**
         * Aborts the event handling thread.
         */
        public void abort() {
            if (thread != null) {
                thread.abort();
            }
        }

        @Override
        public void run() {
            // Initialize the libusb context
            int result = LibUsb.init(null);
            if (result != LibUsb.SUCCESS) {
                throw new LibUsbException("Unable to initialize libusb", result);
            }

            // Check if hotplug is available
            if (!LibUsb.hasCapability(LibUsb.CAP_HAS_HOTPLUG)) {
                System.err.println("libusb doesn't support hotplug on this system");
                //System.exit(1);

                Keyboard.KeyboardModel[] keyboardModel = Keyboard.getKeyboardModel();
                if ((keyboardModel.length > 1) || (keyboardModel[0] != Keyboard.KeyboardModel.NoLogiKeyboard)) { //check if there is any keyboard connected
                    System.out.println("Device loaded.");
                    if (keyboardModel.length > 1) {
                        //display dialog msg to indicate that program only supports one keyboard connected at a time
                        System.err.println("Only one keyboard is allowed at a time.");
                        return;
                    }
                    if (usbCurrently.equals("unplugged")) {
                        usbCurrently = "plugged";
                        if (status.equals("Running")) {
                            switch (keyboardModel[0]) {
                                case Logi910Spectrum:
                                case Logi910Spark:
                                case Logi810Spectrum:
                                case Logi610Orion:
                                case Logi410AtlasSpectrum:
                                    LogiGSKService.gKeyboard = new GKeyboard(keyboardModel[0]);
                                    LogiGSKService.gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(keyboardModel[0]);
                                    if (LogiGSKService.gKeyboard.getEffect() != null) {
                                        resetEffect();
                                    } else {
                                        loadColoursToPhysicalKeyboard(gKeyboard.getKeyColourMap());
                                    }
                                    break;
                                case NoLogiKeyboard:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                } else if ((keyboardModel.length == 1) && (keyboardModel[0] == Keyboard.KeyboardModel.NoLogiKeyboard)) {
                    usbCurrently = "unplugged";
                    stopGKeyboardEffects();
                }
            } else {

                // Start the event handling thread
                thread = new LogiGSKService.EventHandlingThread();
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
                        new LogiGSKService.Callback(), null, callbackHandle);
                if (result != LibUsb.SUCCESS) {
                    throw new LibUsbException("Unable to register hotplug callback",
                            result);
                }
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        thread.abort();
                        LibUsb.hotplugDeregisterCallback(null, callbackHandle);
                        // Deinitialize the libusb context
                        LibUsb.exit(null);
                    }
                }));
            }
        }
    }

    private static class Manager extends Thread {

        public static Logger logger = Logger.getLogger(Manager.class.getName());
        private final Socket socket;
        private final int clientNumber;
        String DecryptedCommand = "";

        public Manager(Socket socket, int clientNumber, Logger Logger) {
            try {
                logger = Logger;
            } catch (Exception ex) {
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            }
            this.socket = socket;
            this.clientNumber = clientNumber;
            Logger.getLogger(LogiGSKService.class.getName()).log(Level.INFO, "New connection with client# {0} at {1}", new Object[]{clientNumber, socket});
        }

        /**
         * Handling External Requests
         */
        @Override
        public void run() {
            try {
                boolean commandRunning = true;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Get messages from the client, line by line; return them
                // capitalized
                while (commandRunning) {
                    String input = in.readLine();
                    String response;
                    response = executeCommand(input);
                    Logger.getLogger(LogiGSKService.class.getName()).log(Level.INFO, "Response: {0}", response);
                    out.println(response);
                    commandRunning = false;
                }
            } catch (IOException e) {
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, "Error handling client# {0}: {1}", new Object[]{clientNumber, e.getMessage()});
            } catch (Exception e) {
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, "Error handling client# {0}: {1}", new Object[]{clientNumber, e.getMessage()});
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, "Couldn't close a socket, what's going on?");
                }
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.INFO, "Connection with client# {0} closed", clientNumber);
            }
        }

        private String executeCommand(String Command) {
            Settings settings;
            if (IOOperations.currentSettingsExist()) {
                settings = IOOperations.loadCurrentSettingsObjectFromFile();
            } else {
                settings = new Settings();
            }
            String encryptionPassword = new String(settings.getEncryptionPassword());
            try {
                String decryptedCommand = Encryption.decrypt(Command, encryptionPassword);
                if (decryptedCommand.equals("Error")) {
                    return Encryption.prepareEncryptedReturn("Error", "Decryption error", encryptionPassword);
                }
                if (!decryptedCommand.contains(",")) {
                    return Encryption.prepareEncryptedReturn("Error", "Command error", encryptionPassword);
                }
                this.DecryptedCommand = decryptedCommand;
                //Logger.getLogger(LogiGSKService.class.getName()).log(Level.INFO, "Command is: {0}", decryptedCommand);
                String[] command = decryptedCommand.split(",");
                String[] parameters;
                if (command[4].contains(";")) {
                    parameters = command[4].split(";");
                } else {
                    parameters = new String[1];
                    parameters[0] = command[4];
                }
                String email = command[1];
                String password = command[2];
                String[] login = login(email, password);
                if (!Boolean.parseBoolean(login[0])) {
                    return Encryption.prepareEncryptedReturn(login[0], login[1], encryptionPassword);
                }
                String method = command[3];
                switch (method) {
                    case "pause":
                        LogiGSKService.status = "Paused";
                        new LogiGSKService().pauseOperations();
                        System.out.println("LogiGSK service operations paused.");
                        return Encryption.prepareEncryptedReturn("true", "", encryptionPassword);
                    case "resume":
                        LogiGSKService.status = "Running";
                        new LogiGSKService().resumeOperations();
                        System.out.println("LogiGSK service operations resumed.");
                        return Encryption.prepareEncryptedReturn("true", "", encryptionPassword);
                    case "reload":
                        new LogiGSKService().reload();
                        System.out.println("LogiGSK service operations reloaded.");
                        return Encryption.prepareEncryptedReturn("true", "", encryptionPassword);
                    case "OK":
                        //System.out.println("LogiGSK service operations OK.");
                        return Encryption.prepareEncryptedReturn("true", "", encryptionPassword);
                    case "systemtray":
                        try {
                            if ((Boolean.valueOf(parameters[2])) && !LogiGSKService.systemTrayVisible) {
                                System.out.println("Showing system tray");
                                new LogiGSKService().showSystemTray();
                            } else if ((!Boolean.valueOf(parameters[2])) && LogiGSKService.systemTrayVisible) {
                                System.out.println("Hiding system tray");
                                new LogiGSKService().hideSystemTray();
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getStackTrace());
                        }
                        return Encryption.prepareEncryptedReturn("true", "", encryptionPassword);
                    default:
                        return Encryption.prepareEncryptedReturn("Error", "Command not found", encryptionPassword);
                }
            } catch (NumberFormatException ex) {
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, "Error executing command: {0}. {1}", new Object[]{DecryptedCommand, ex.getMessage()});
                return Encryption.prepareEncryptedReturn("Error", "Message: " + ex.getMessage(), encryptionPassword);
            } catch (Exception ex) {
                Logger.getLogger(LogiGSKService.class.getName()).log(Level.SEVERE, "Error executing command: {0}. {1}", new Object[]{DecryptedCommand, ex.getMessage()});
                return Encryption.prepareEncryptedReturn("Error", "Message: " + ex.getMessage(), encryptionPassword);
            }
        }

        private String[] login(String username, String password) {
            Settings settings;
            if (IOOperations.currentSettingsExist()) {
                settings = IOOperations.loadCurrentSettingsObjectFromFile();
            } else {
                settings = new Settings();
            }
            String[] reply = new String[2];
            if (username.equals(settings.getUsername()) && password.equals(new String(settings.getPassword()))) {
                reply[0] = "true";
                reply[1] = "true";
            }
            return reply;
        }
    }
}

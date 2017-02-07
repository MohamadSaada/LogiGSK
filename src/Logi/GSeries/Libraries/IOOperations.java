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

import Logi.GSeries.Keyboard.CommunicationClient;
import Logi.GSeries.Keyboard.LogiGSK;
import Logi.GSeries.Libraries.Settings;
import Logi.GSeries.Libraries.Effects;
import Logi.GSeries.Libraries.GKeyboard;
import Logi.GSeries.Libraries.Keyboard;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mohamad Saada
 */
public class IOOperations {

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //          GKeyboard functions            //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //copy GKeyboard object
    public static GKeyboard copyGKeyboard(GKeyboard gkeyboard) {
        String[][] tmpKeyColourMap = new String[gkeyboard.getKeyColourMap().length][];
        GKeyboard gKeyboardCopy = new GKeyboard(gkeyboard.getModel());
        //gKeyboardCopy.setKeyColourMap(new String[gkeyboard.getKeyColourMap().length][]);
        gKeyboardCopy.setCurrentCategory(gkeyboard.getCurrentCategory());
        gKeyboardCopy.setKeyCount(gkeyboard.getKeyCount());
        gKeyboardCopy.setModel(gkeyboard.getModel());
        gKeyboardCopy.setStartupEffect(gkeyboard.getStartupEffect());
        for (int i = 0; i < gkeyboard.getKeyColourMap().length; i++) {
            tmpKeyColourMap[i] = new String[gkeyboard.getKeyColourMap()[0].length];
            for (int j = 0; j < gkeyboard.getKeyColourMap()[i].length; j++) {
                tmpKeyColourMap[i][j] = gkeyboard.getKeyColourMap()[i][j];
            }
        }
        gKeyboardCopy.setKeyColourMap(tmpKeyColourMap);
        if (gkeyboard.getEffect() != null) {
            gKeyboardCopy.setEffect(new Effects());
            gKeyboardCopy.getEffect().BreathingColour = gkeyboard.getEffect().BreathingColour;
            gKeyboardCopy.getEffect().BreathingSpeed = gkeyboard.getEffect().BreathingSpeed;
            gKeyboardCopy.getEffect().CurrentEffect = Effects.Type.valueOf(gkeyboard.getEffect().CurrentEffect.toString());
            gKeyboardCopy.getEffect().FixedColour = gkeyboard.getEffect().FixedColour;
            gKeyboardCopy.getEffect().ColourCycleSpeed = gkeyboard.getEffect().ColourCycleSpeed;
            gKeyboardCopy.getEffect().ColourWave = gkeyboard.getEffect().ColourWave;
            gKeyboardCopy.getEffect().ColourWaveSpeed = gkeyboard.getEffect().ColourWaveSpeed;
            gKeyboardCopy.getEffect().KeyPressedColour = gkeyboard.getEffect().KeyPressedColour;
            gKeyboardCopy.getEffect().LightningColour = gkeyboard.getEffect().LightningColour;
            gKeyboardCopy.getEffect().SkyColour = gkeyboard.getEffect().SkyColour;
            gKeyboardCopy.getEffect().StarColour = gkeyboard.getEffect().StarColour;
        }
        return gKeyboardCopy;
    }

    //load currently set GKeyboard object from current profile object file
    public static GKeyboard loadCurrentKeyboardObjectFromFile(Keyboard.KeyboardModel model) {
        FileInputStream streamIn = null;
        GKeyboard gkeyboard = null;
        try {
            String currentProfilePath = getCurrentProfilePath();
            File currentProfile = new File(currentProfilePath);
            if (currentProfile.exists()) {
                streamIn = new FileInputStream(currentProfilePath);
                ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
                gkeyboard = (GKeyboard) objectinputstream.readObject();
            } else {
                gkeyboard = new GKeyboard(model);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (streamIn != null) {
                    streamIn.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (gkeyboard == null) {
                gkeyboard = new GKeyboard(model);
            }
        }
        return gkeyboard;
    }

    //set current keyboard object to current keyboard object file
    public static void setCurrentProfile(GKeyboard gKeyboard) {
        FileOutputStream fout = null;
        try {
            String outputFolder = getLocalConfigDirectoryPath();
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            //gKeyboard.KeyColourMap = convertG910ColourMapToStandardKeyColourMap(getColoursFromVirtualKeyboard());
            GKeyboard writingKeyboard = copyGKeyboard(gKeyboard);
            fout = new FileOutputStream(outputFolder + "cp" + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(writingKeyboard);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //save current keyboard object to file
    public static void saveProfile(GKeyboard gKeyboard) {
        FileOutputStream fout = null;
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //In response to a button click:
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                if (fileChooser.getSelectedFile().exists()) {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to override existing file?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        //gKeyboard.KeyColourMap = convertG910ColourMapToStandardKeyColourMap(getColoursFromVirtualKeyboard());
                        GKeyboard writingKeyboard = copyGKeyboard(gKeyboard);
                        fout = new FileOutputStream(fileChooser.getSelectedFile());
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        oos.writeObject(writingKeyboard);
                    }
                } else {
                    //gKeyboard.KeyColourMap = convertG910ColourMapToStandardKeyColourMap(getColoursFromVirtualKeyboard());
                    GKeyboard writingKeyboard = copyGKeyboard(gKeyboard);
                    fout = new FileOutputStream(fileChooser.getSelectedFile());
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(writingKeyboard);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //load profile from GKeyboard object file
    public static GKeyboard loadProfile(Keyboard.KeyboardModel model) {
        GKeyboard gKeyboard = null;
        //Create a file chooser
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //In response to a button click:
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            FileInputStream streamIn = null;
            try {
                File currentProfile = fileChooser.getSelectedFile();
                if (currentProfile.exists()) {
                    streamIn = new FileInputStream(fileChooser.getSelectedFile());
                    ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
                    //stopGKeyboardEffects(true);
                    gKeyboard = new GKeyboard(model);
                    gKeyboard = (GKeyboard) objectinputstream.readObject();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    streamIn.close();
                } catch (IOException ex) {
                    Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return gKeyboard;
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //            Generic functions            //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //returns the index of element in an array
    public static int getIndexOf(int[] intArray, int element) {
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] == element) {
                return i;
            }
        }
        return -1;
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //     Colours functions used by many      //
    //                classes                  //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //get hex string representation of a Color object
    public static String getHexStringFromColour(Color colour) {
        String hexColour = String.format("%02x%02x%02x",
                colour.getRed(),
                colour.getGreen(),
                colour.getBlue());
        return hexColour;
    }

    //get the Color object from a hex string representation of a colour
    public static Color getColourFromHexString(String colourHex) {
        return new Color(
                Integer.valueOf(colourHex.substring(0, 2), 16),
                Integer.valueOf(colourHex.substring(2, 4), 16),
                Integer.valueOf(colourHex.substring(4, 6), 16));
    }
    
    //get the Grayscale Color object from a normal colour (the luminosity formula was used)
    public static Color getGrayColourFromColour(Color colour) {
        int grayscale = (int)(0.3 * colour.getRed() + 0.59 * colour.getGreen() + 0.11 * colour.getBlue());
        return new Color(grayscale, grayscale, grayscale);
    }
    
    //get the Grayscale Color object from a hex string representation of a colour
    public static Color getGrayColourFromHexString(String colourHex) {
        int grayscale = (int)(
                0.3 * Integer.valueOf(colourHex.substring(0, 2), 16) + 
                0.59 * Integer.valueOf(colourHex.substring(2, 4), 16) + 
                0.11 * Integer.valueOf(colourHex.substring(4, 6), 16));
        return new Color(grayscale, grayscale, grayscale);
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //         Path related functions          //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //get current operating system
    public static String getOS() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            return "Windows";
        }
        if (OS.contains("mac")) {
            return "Mac";
        }
        if (OS.contains("nux")) {
            return "Linux";
        }
        return "Linux";
    }

    //get the local save path for current profile file
    public static String getCurrentProfilePath() {
        return getLocalConfigDirectoryPath() + "cp.ser";
    }

    //get the local save path for settings file
    public static String getCurrentSettingsPath() {
        return getLocalConfigDirectoryPath() + "cs.ser";
    }

    //get the local path for the directory where software configuration is saved
    public static String getLocalConfigDirectoryPath() {
        String fileSeperator = System.getProperty("file.separator");
        String saveDirectoryPath = null;
        switch (getOS()) {
            case "Linux":
                saveDirectoryPath = System.getenv("XDG_CONFIG_HOME");
                if (saveDirectoryPath == null) {
                    saveDirectoryPath = System.getProperty("user.home") + fileSeperator + ".config" + fileSeperator;
                }
                break;
            case "Windows":
                saveDirectoryPath = System.getenv("AppData") + fileSeperator;
                break;
            case "Mac":
                saveDirectoryPath = System.getProperty("user.home") + fileSeperator + "Library" + fileSeperator + "Application Support" + fileSeperator;
                break;
            default:
                break;
        }
        saveDirectoryPath += "LogiGSK" + fileSeperator;
        return saveDirectoryPath;
    }

    //get the local path for the directory where software data is saved
    public static String getLocalDataDirectoryPath() {
        String fileSeperator = System.getProperty("file.separator");
        String saveDirectoryPath = null;
        switch (getOS()) {
            case "Linux":
                saveDirectoryPath = System.getenv("XDG_DATA_HOME");
                if (saveDirectoryPath == null) {
                    saveDirectoryPath = System.getProperty("user.home") + fileSeperator + ".local" + fileSeperator + "share" + fileSeperator;
                }
                break;
            case "Windows":
                saveDirectoryPath = System.getenv("AppData") + fileSeperator;
                break;
            case "Mac":
                saveDirectoryPath = System.getProperty("user.home") + fileSeperator + "Library" + fileSeperator + "Application Support" + fileSeperator;
                break;
            default:
                break;
        }
        saveDirectoryPath += "LogiGSK" + fileSeperator;
        return saveDirectoryPath;
    }

    //get the local path for the directory where software log data is saved
    public static String getLogDirectoryPath() {
        String fileSeperator = System.getProperty("file.separator");
        String saveDirectoryPath = getLocalDataDirectoryPath() + fileSeperator + "Log" + fileSeperator;
        return saveDirectoryPath;
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //           Settings functions            //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //checks if current settings file exists
    public static boolean currentSettingsExist() {
        String currentSettingsPath = getCurrentSettingsPath();
        File currentSettings = new File(currentSettingsPath);
        if (currentSettings.exists()) {
            return true;
        } else {
            return false;
        }
    }

    //copy settings object
    public static Settings copySettings(Settings settings) {
        Settings settingsCopy = new Settings();
        settingsCopy.setLayout(settings.getLayout());
        settingsCopy.setShowProfileOptionsFileMenu(settings.getShowProfileOptionsFileMenu());
        settingsCopy.setShowImportExportOptionsFileMenu(settings.getShowImportExportOptionsFileMenu());
        settingsCopy.setShowSystemTray(settings.getShowSystemTray());
        settingsCopy.setPort(settings.getPort());
        settingsCopy.setUsername(settings.getUsername());
        settingsCopy.setPassword(settings.getPassword());
        settingsCopy.setEncryptionPassword(settings.getEncryptionPassword());
        return settingsCopy;
    }

    //load currently set settings object from current settings object file
    public static Settings loadCurrentSettingsObjectFromFile() {
        FileInputStream streamIn = null;
        Settings settings = null;
        try {
            String currentSettingsPath = getCurrentSettingsPath();
            File currentSettings = new File(currentSettingsPath);
            if (currentSettings.exists()) {
                streamIn = new FileInputStream(currentSettingsPath);
                ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
                settings = (Settings) objectinputstream.readObject();
            } else {
                settings = new Settings();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (streamIn != null) {
                    streamIn.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (settings == null) {
                settings = new Settings();
            }
        }
        return settings;
    }

    //save current settings object to current settings object file
    public static void saveCurrentSettings(Settings settings) {
        FileOutputStream fout = null;
        try {
            String outputFolder = getLocalConfigDirectoryPath();
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Settings writingSettings = copySettings(settings);
            fout = new FileOutputStream(outputFolder + "cs" + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(writingSettings);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //save current settings object to file
    public static void exportSettings(Settings settings) {
        FileOutputStream fout = null;
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //In response to a button click:
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                if (fileChooser.getSelectedFile().exists()) {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to override existing file?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        Settings writingSettings = copySettings(settings);
                        fout = new FileOutputStream(fileChooser.getSelectedFile());
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        oos.writeObject(writingSettings);
                    }
                } else {
                    Settings writingSettings = copySettings(settings);
                    fout = new FileOutputStream(fileChooser.getSelectedFile());
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(writingSettings);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //load settings from settings object file
    public static Settings importSettings() {
        Settings settings = null;
        //Create a file chooser
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //In response to a button click:
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            FileInputStream streamIn = null;
            try {
                File currentProfile = fileChooser.getSelectedFile();
                if (currentProfile.exists()) {
                    streamIn = new FileInputStream(fileChooser.getSelectedFile());
                    ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
                    settings = new Settings();
                    settings = (Settings) objectinputstream.readObject();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "msg", "Title", JOptionPane.WARNING_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    streamIn.close();
                } catch (IOException ex) {
                    Logger.getLogger(IOOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return settings;
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //           Service functions             //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //check if service can be contacted on settings from the current settings file
    public static boolean checkService() {
        boolean everythingOK = false;
        Settings settings;
        if (currentSettingsExist()) {
            settings = loadCurrentSettingsObjectFromFile();
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
        command[3] = "OK";
        command[4] = Encryption.convertToSemiColonDelimited(loginParams);
        String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
        CommunicationClient client = new CommunicationClient(settings.getPort());
        try {
            String[] response = client.sendCommand(param);
            if (Boolean.parseBoolean(response[0])) {
                everythingOK = true;
            }
        } catch (IOException ex) {
            //System.out.println("message: " + ex.getMessage());
            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return everythingOK;
    }

    //check if service can be contacted on settings from the current settings file with
    //a specific port
    public static boolean checkService(int port) {
        boolean everythingOK = false;
        Settings settings;
        if (currentSettingsExist()) {
            settings = loadCurrentSettingsObjectFromFile();
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
        command[3] = "OK";
        command[4] = Encryption.convertToSemiColonDelimited(loginParams);
        String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
        CommunicationClient client = new CommunicationClient(port);
        try {
            String[] response = client.sendCommand(param);
            if (Boolean.parseBoolean(response[0])) {
                everythingOK = true;
            }
        } catch (IOException ex) {
            //System.out.println("message: " + ex.getMessage());
            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return everythingOK;
    }

    //check if service can be contacted on given settings
    public static boolean checkService(int port, String username, String password, String encryptionPassword) {
        boolean everythingOK = false;
        String[] loginParams = new String[2];
        loginParams[0] = username;
        loginParams[1] = password;
        String[] command = new String[5];
        command[0] = "false";
        command[1] = username;
        command[2] = password;
        command[3] = "OK";
        command[4] = Encryption.convertToSemiColonDelimited(loginParams);
        String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), encryptionPassword);
        CommunicationClient client = new CommunicationClient(port);
        try {
            String[] response = client.sendCommand(param);
            if (Boolean.parseBoolean(response[0])) {
                everythingOK = true;
            }
        } catch (IOException ex) {
            //System.out.println("message: " + ex.getMessage());
            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return everythingOK;
    }
}

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

import Logi.GSeries.Libraries.Keyboard;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

/**
 *
 * @author Mohamad Saada
 */
public class Settings implements Serializable {

    private boolean showProfileOptionsFileMenu = true;
    private boolean showImportExportOptionsFileMenu = true;
    private boolean showSystemTray = true;
    private Keyboard.KeyboardLayout layout = Keyboard.KeyboardLayout.US_QWERTY;
    private int port = 9898;
    private String username = "Mohamad";
    private char[] password = new char[]{'=', '$', '$', 'A', 'K', 'C', 'y', '^', 'p',
        '7', 'X', 'g', 'Y', '+', '+', '!', 'Y', 'n', 'g', 'h', 'B', '8', 'g', 'w'};
    private char[] encryptionPassword = {'$', 'u', 'p', 'a', 'm', '[', 'C', '@',
         '2', 'e', 'E', 'p', '"', 'p', '?', 'H'};
    //allowedCharacters is not used, just kept it here in case
    private char[] allowedCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
         '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']',
         '^', '_', '`', '{', '|', '}', '~'};

    public boolean getShowProfileOptionsFileMenu() {
        return this.showProfileOptionsFileMenu;
    }

    public void setShowProfileOptionsFileMenu(boolean showProfileOptionsFileMenu) {
        this.showProfileOptionsFileMenu = showProfileOptionsFileMenu;
    }

    public boolean getShowImportExportOptionsFileMenu() {
        return this.showImportExportOptionsFileMenu;
    }

    public void setShowImportExportOptionsFileMenu(boolean showImportExportOptionsFileMenu) {
        this.showImportExportOptionsFileMenu = showImportExportOptionsFileMenu;
    }
    
    public boolean getShowSystemTray() {
        return this.showSystemTray;
    }

    public void setShowSystemTray(boolean showSystemTray) {
        this.showSystemTray = showSystemTray;
    }

    public Keyboard.KeyboardLayout getLayout() {
        return this.layout;
    }

    public void setLayout(Keyboard.KeyboardLayout layout) {
        this.layout = layout;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public char[] getEncryptionPassword() {
        return this.encryptionPassword;
    }

    public void setEncryptionPassword(char[] encryptionPassword) {
        this.encryptionPassword = encryptionPassword;
    }

    public void generatePassword() {
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
             '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']',
             '^', '_', '`', '{', '|', '}', '~'};
        List<CharacterRule> rules = Arrays.asList(
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() {
                        return "INSUFFICIENT_SPECIAL";
                    }

                    @Override
                    public String getCharacters() {
                        return new String(special);
                    }
                }, 1));

        PasswordGenerator generator = new PasswordGenerator();
        // Generated password is 16 characters long, which complies with policy
        String password = generator.generatePassword(16, rules);
        this.password = password.toCharArray();
    }

    public void generateEncryptionPassword() {
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
             '+', ',', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']',
             '^', '_', '`', '{', '|', '}', '~'};
        List<CharacterRule> rules = Arrays.asList(
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() {
                        return "INSUFFICIENT_SPECIAL";
                    }

                    @Override
                    public String getCharacters() {
                        return new String(special);
                    }
                }, 1));

        PasswordGenerator generator = new PasswordGenerator();
        // Generated password is 16 characters long, which complies with policy
        String password = generator.generatePassword(16, rules);
        this.encryptionPassword = password.toCharArray();
    }

}

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

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Mohamad Saada
 */
public class Encryption {

    public static String encrypt(String decryptedString, String password) {
        try {
            // build the initialization vector (randomly).
            SecureRandom random = new SecureRandom();
            byte initialVector[] = new byte[16];
            //generate random 16 byte IV AES is always 16bytes
            random.nextBytes(initialVector);
            IvParameterSpec ivspec = new IvParameterSpec(initialVector);
            SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
            byte[] encrypted = cipher.doFinal(decryptedString.getBytes());
            byte[] encryptedWithIV = new byte[encrypted.length + initialVector.length];
            System.arraycopy(encrypted, 0, encryptedWithIV, 0, encrypted.length);
            System.arraycopy(initialVector, 0, encryptedWithIV, encrypted.length, initialVector.length);
            return Base64.encodeBase64String(encryptedWithIV);
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }

    public static String decrypt(String encryptedString, String password) {
        try {
            byte[] encryptedWithIV = Base64.decodeBase64(encryptedString);
            byte initialVector[] = new byte[16];
            byte[] encrypted = new byte[encryptedWithIV.length - initialVector.length];
            System.arraycopy(encryptedWithIV, 0, encrypted, 0, encrypted.length);
            System.arraycopy(encryptedWithIV, encrypted.length, initialVector, 0, initialVector.length);
            IvParameterSpec ivspec = new IvParameterSpec(initialVector);
            SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original);
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }

    public static String prepareEncryptedReturn(String type, String message, String password) {
        String[] returnMessage = new String[2];
        returnMessage[0] = type;
        returnMessage[1] = message;
        String encryptedResponse = encrypt(convertToCommaDelimited(returnMessage), password);
        return encryptedResponse;
    }

    public static String[] prepareReturn(String type, String message) {
        String[] returnMessage = new String[2];
        returnMessage[0] = type;
        returnMessage[1] = message;
        return returnMessage;
    }

    public static String convertToCommaDelimited(String[] list) {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }

    public static String convertToSemiColonDelimited(String[] list) {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(';');
            }
        }
        return ret.toString();
    }

}

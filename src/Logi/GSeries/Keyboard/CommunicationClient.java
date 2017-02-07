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

/**
 *
 * @author Mohamad Saada
 */
import Logi.GSeries.Libraries.Encryption;
import Logi.GSeries.Libraries.IOOperations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client for sending commands over to the Thermostat Manager application
 */
public class CommunicationClient {

    private BufferedReader in;
    private PrintWriter out;
    private boolean continueReceiving = true;
    static Logger logger = Logger.getLogger(CommunicationClient.class.getName());
    private int port = 9898;

    /**
     * Constructs the client
     */
    public CommunicationClient(int port) {
        this.port = port;
    }

    /**
     * Sends command to the Thermostat Manager application
     */
    public String[] sendCommand(String command) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(null), port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String encryptedResponse = "";
        String[] response;
        out.println(command);
        String encryptionPassword = new String(IOOperations.loadCurrentSettingsObjectFromFile().getEncryptionPassword());
        try {
            encryptedResponse = in.readLine();
            response = Encryption.decrypt(encryptedResponse, encryptionPassword).split(",");
        } catch (IOException ex) {
            response = new String[2];
            response[0] = "Error";
            response[1] = "Web Message: " + ex.getMessage();
            encryptedResponse = Encryption.encrypt(Encryption.convertToCommaDelimited(response), encryptionPassword);
            logger.log(Level.SEVERE, "Error sending command: " + Encryption.decrypt(command, encryptionPassword) + ". with the following error message: " + ex);
        } catch (Exception ex) {
            response = new String[2];
            response[0] = "Error";
            response[1] = "Web Message: " + ex.getMessage();
            encryptedResponse = Encryption.encrypt(Encryption.convertToCommaDelimited(response), encryptionPassword);
            logger.log(Level.SEVERE, "Error sending command: " + Encryption.decrypt(command, encryptionPassword) + ". with the following error message: " + ex);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Couldn't close a socket, what's going on?");
            }
            logger.log(Level.INFO, "Connection with Logi G Keyboard service closed.");
        }
        System.out.println(response);
        return response;
    }
}

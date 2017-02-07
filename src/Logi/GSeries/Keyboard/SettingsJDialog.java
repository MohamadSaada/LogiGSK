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
import Logi.GSeries.Libraries.Settings;
import Logi.GSeries.Libraries.Keyboard;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.passay.*;

/**
 *
 * @author Mohamad Saada
 */
public class SettingsJDialog extends javax.swing.JDialog {

    Settings settings = null;
    boolean firstTime = true;

    /**
     * Creates new form SettingsJDialog
     */
    public SettingsJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(MainJFrame.class.getResource("/Logi/GSeries/Keyboard/Resources/Images/Settings_White.png")).getImage());
        jTreeSettingsMainOptionsList.setSelectionPath(new TreePath(((DefaultMutableTreeNode) jTreeSettingsMainOptionsList.getModel().getRoot()).getFirstLeaf().getPath()));

        // Listen for changes in the text
        jTextFieldSettingsServiceNetworkPort.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkPortValidity();
            }

            public void removeUpdate(DocumentEvent e) {
                checkPortValidity();
            }

            public void insertUpdate(DocumentEvent e) {
                checkPortValidity();
            }
        });

        jTextFieldSettingsServiceCredentialsUsername.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkUsernameValidity();
            }

            public void removeUpdate(DocumentEvent e) {
                checkUsernameValidity();
            }

            public void insertUpdate(DocumentEvent e) {
                checkUsernameValidity();
            }
        });

        jPasswordFieldSettingsServiceCredentialsPassword.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkCredentialsPasswordValidity();
            }

            public void removeUpdate(DocumentEvent e) {
                checkCredentialsPasswordValidity();
            }

            public void insertUpdate(DocumentEvent e) {
                checkCredentialsPasswordValidity();
            }
        });

        jPasswordFieldSettingsServiceEncryptionPassword.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkEncryptionPasswordValidity();
            }

            public void removeUpdate(DocumentEvent e) {
                checkEncryptionPasswordValidity();
            }

            public void insertUpdate(DocumentEvent e) {
                checkEncryptionPasswordValidity();
            }
        });

        settings = IOOperations.loadCurrentSettingsObjectFromFile();

        boolean serviceRunning = IOOperations.checkService(settings.getPort());

        if (serviceRunning) {
            jLabelSettingsServiceServiceInfo.setForeground(Color.GREEN);
            jLabelSettingsServiceServiceInfo.setText("Running on port " + settings.getPort());
        } else {
            jLabelSettingsServiceServiceInfo.setForeground(Color.RED);
            jLabelSettingsServiceServiceInfo.setText("Not running on port " + settings.getPort());
        }

        jCheckBoxProfileButtonsFileMenu.setSelected(settings.getShowProfileOptionsFileMenu());
        jCheckBoxSettingsImportExportFileMenu.setSelected(settings.getShowImportExportOptionsFileMenu());
        jCheckBoxSettingsSystemTray.setSelected(settings.getShowSystemTray());
        jComboBoxSettingsKeyboardLayout.setSelectedItem(settings.getLayout().name());
        jTextFieldSettingsServiceNetworkPort.setText(String.valueOf(settings.getPort()));
        jTextFieldSettingsServiceCredentialsUsername.setText(settings.getUsername());
        jPasswordFieldSettingsServiceCredentialsPassword.setText(new String(settings.getPassword()));
        jPasswordFieldSettingsServiceEncryptionPassword.setText(new String(settings.getEncryptionPassword()));
        firstTime = false;
        refreshApply();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPaneSettings = new javax.swing.JScrollPane();
        jTreeSettingsMainOptionsList = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jPanelGeneral = new javax.swing.JPanel();
        jLabelSettingsGeneralGeneral = new javax.swing.JLabel();
        jCheckBoxProfileButtonsFileMenu = new javax.swing.JCheckBox();
        jCheckBoxSettingsImportExportFileMenu = new javax.swing.JCheckBox();
        jCheckBoxSettingsSystemTray = new javax.swing.JCheckBox();
        jPanelKeyboard = new javax.swing.JPanel();
        jComboBoxSettingsKeyboardLayout = new javax.swing.JComboBox<>();
        jLabelSettingsKeyboardLayout = new javax.swing.JLabel();
        jPanelService = new javax.swing.JPanel();
        jLabelSettingsServiceService = new javax.swing.JLabel();
        jLabelSettingsServiceServiceInfo = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabelSettingsServiceNetwork = new javax.swing.JLabel();
        jLabelSettingsServiceNetworkPort = new javax.swing.JLabel();
        jTextFieldSettingsServiceNetworkPort = new javax.swing.JTextField();
        jLabelSettingsServiceNetworkPortWarning = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelSettingsServiceCredentials = new javax.swing.JLabel();
        jLabelSettingsServiceCredentialsUsername = new javax.swing.JLabel();
        jTextFieldSettingsServiceCredentialsUsername = new javax.swing.JTextField();
        jLabelSettingsServiceCredentialsUsernameWarning = new javax.swing.JLabel();
        jLabelSettingsServiceCredentialsPassword = new javax.swing.JLabel();
        jPasswordFieldSettingsServiceCredentialsPassword = new javax.swing.JPasswordField();
        jLabelSettingsServiceCredentialsPasswordWarning = new javax.swing.JLabel();
        jButtonSettingsServiceCredentialsPasswordGenerator = new javax.swing.JButton();
        jButtonSettingsServiceCredentialsShowPasswordQR = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabelSettingsServiceEncryption = new javax.swing.JLabel();
        jLabelSettingsServiceEncryptionPassword = new javax.swing.JLabel();
        jPasswordFieldSettingsServiceEncryptionPassword = new javax.swing.JPasswordField();
        jLabelSettingsServiceEncryptionPasswordWarning = new javax.swing.JLabel();
        jButtonSettingsServiceEncryptionPasswordGenerator = new javax.swing.JButton();
        jButtonSettingsServiceEncryptionShowPasswordQR = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonApply = new javax.swing.JButton();
        jButtonOK = new javax.swing.JButton();
        jButtonExport = new javax.swing.JButton();
        jButtonImport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(620, 490));

        jTreeSettingsMainOptionsList.setBackground(new java.awt.Color(255, 255, 255));
        jTreeSettingsMainOptionsList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTreeSettingsMainOptionsList.setForeground(new java.awt.Color(0, 0, 0));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Settings");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("General");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Keyboard");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Service");
        treeNode1.add(treeNode2);
        jTreeSettingsMainOptionsList.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeSettingsMainOptionsList.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeSettingsMainOptionsListValueChanged(evt);
            }
        });
        jScrollPaneSettings.setViewportView(jTreeSettingsMainOptionsList);

        jPanel3.setLayout(new java.awt.CardLayout());

        jPanelGeneral.setBackground(new java.awt.Color(255, 255, 255));
        jPanelGeneral.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelGeneral.setMinimumSize(new java.awt.Dimension(430, 363));
        jPanelGeneral.setPreferredSize(new java.awt.Dimension(430, 363));

        jLabelSettingsGeneralGeneral.setText("General");

        jCheckBoxProfileButtonsFileMenu.setText("Add profile load and save buttons to file menu");
        jCheckBoxProfileButtonsFileMenu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxProfileButtonsFileMenuStateChanged(evt);
            }
        });

        jCheckBoxSettingsImportExportFileMenu.setText("Add settings import and export to file menu");
        jCheckBoxSettingsImportExportFileMenu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxSettingsImportExportFileMenuStateChanged(evt);
            }
        });

        jCheckBoxSettingsSystemTray.setText("show system tray icon");
        jCheckBoxSettingsSystemTray.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxSettingsSystemTrayItemStateChanged(evt);
            }
        });
        jCheckBoxSettingsSystemTray.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxSettingsSystemTrayStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelGeneralLayout = new javax.swing.GroupLayout(jPanelGeneral);
        jPanelGeneral.setLayout(jPanelGeneralLayout);
        jPanelGeneralLayout.setHorizontalGroup(
            jPanelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSettingsGeneralGeneral)
                    .addComponent(jCheckBoxProfileButtonsFileMenu)
                    .addComponent(jCheckBoxSettingsImportExportFileMenu)
                    .addComponent(jCheckBoxSettingsSystemTray))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanelGeneralLayout.setVerticalGroup(
            jPanelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeneralLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabelSettingsGeneralGeneral)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxProfileButtonsFileMenu)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxSettingsImportExportFileMenu)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxSettingsSystemTray)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        jPanel3.add(jPanelGeneral, "card2");

        jPanelKeyboard.setBackground(new java.awt.Color(255, 255, 255));
        jPanelKeyboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelKeyboard.setMinimumSize(new java.awt.Dimension(430, 363));
        jPanelKeyboard.setPreferredSize(new java.awt.Dimension(430, 363));

        jComboBoxSettingsKeyboardLayout.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "US_QWERTY", "UK_QWERTY" }));
        jComboBoxSettingsKeyboardLayout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSettingsKeyboardLayoutActionPerformed(evt);
            }
        });

        jLabelSettingsKeyboardLayout.setText("Layout");

        javax.swing.GroupLayout jPanelKeyboardLayout = new javax.swing.GroupLayout(jPanelKeyboard);
        jPanelKeyboard.setLayout(jPanelKeyboardLayout);
        jPanelKeyboardLayout.setHorizontalGroup(
            jPanelKeyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKeyboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSettingsKeyboardLayout)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxSettingsKeyboardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelKeyboardLayout.setVerticalGroup(
            jPanelKeyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKeyboardLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelKeyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSettingsKeyboardLayout)
                    .addComponent(jComboBoxSettingsKeyboardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(318, Short.MAX_VALUE))
        );

        jPanel3.add(jPanelKeyboard, "card3");

        jPanelService.setBackground(new java.awt.Color(255, 255, 255));
        jPanelService.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelService.setMinimumSize(new java.awt.Dimension(430, 363));
        jPanelService.setPreferredSize(new java.awt.Dimension(430, 363));
        jPanelService.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSettingsServiceService.setText("Service:");
        jPanelService.add(jLabelSettingsServiceService, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 80, 20));

        jLabelSettingsServiceServiceInfo.setForeground(java.awt.Color.green);
        jLabelSettingsServiceServiceInfo.setText("Running on port ");
        jPanelService.add(jLabelSettingsServiceServiceInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 16, 220, 20));
        jPanelService.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 410, 10));

        jLabelSettingsServiceNetwork.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSettingsServiceNetwork.setText("Network");
        jPanelService.add(jLabelSettingsServiceNetwork, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabelSettingsServiceNetworkPort.setText("Port:");
        jPanelService.add(jLabelSettingsServiceNetworkPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, -1, -1));

        jTextFieldSettingsServiceNetworkPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSettingsServiceNetworkPort.setText("9898");
        jTextFieldSettingsServiceNetworkPort.setMaximumSize(new java.awt.Dimension(70, 23));
        jTextFieldSettingsServiceNetworkPort.setMinimumSize(new java.awt.Dimension(70, 23));
        jTextFieldSettingsServiceNetworkPort.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanelService.add(jTextFieldSettingsServiceNetworkPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 70, -1));

        jLabelSettingsServiceNetworkPortWarning.setForeground(java.awt.Color.red);
        jLabelSettingsServiceNetworkPortWarning.setMaximumSize(new java.awt.Dimension(150, 20));
        jLabelSettingsServiceNetworkPortWarning.setMinimumSize(new java.awt.Dimension(150, 20));
        jLabelSettingsServiceNetworkPortWarning.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanelService.add(jLabelSettingsServiceNetworkPortWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 93, 170, 20));
        jPanelService.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 414, 10));

        jLabelSettingsServiceCredentials.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSettingsServiceCredentials.setText("Credentials");
        jPanelService.add(jLabelSettingsServiceCredentials, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabelSettingsServiceCredentialsUsername.setText("Username:");
        jPanelService.add(jLabelSettingsServiceCredentialsUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 182, -1, -1));

        jTextFieldSettingsServiceCredentialsUsername.setText("Mohamad");
        jPanelService.add(jTextFieldSettingsServiceCredentialsUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 124, -1));

        jLabelSettingsServiceCredentialsUsernameWarning.setForeground(java.awt.Color.red);
        jLabelSettingsServiceCredentialsUsernameWarning.setMaximumSize(new java.awt.Dimension(150, 20));
        jLabelSettingsServiceCredentialsUsernameWarning.setMinimumSize(new java.awt.Dimension(150, 20));
        jLabelSettingsServiceCredentialsUsernameWarning.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanelService.add(jLabelSettingsServiceCredentialsUsernameWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 182, 170, 20));

        jLabelSettingsServiceCredentialsPassword.setText("Password:");
        jPanelService.add(jLabelSettingsServiceCredentialsPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 212, -1, -1));

        jPasswordFieldSettingsServiceCredentialsPassword.setText("=$$AKCy^p7XgY++!YnghB8gw");
        jPanelService.add(jPasswordFieldSettingsServiceCredentialsPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 124, -1));

        jLabelSettingsServiceCredentialsPasswordWarning.setForeground(java.awt.Color.red);
        jPanelService.add(jLabelSettingsServiceCredentialsPasswordWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 170, 20));

        jButtonSettingsServiceCredentialsPasswordGenerator.setText("Gen Pass");
        jButtonSettingsServiceCredentialsPasswordGenerator.setToolTipText("Generate password for encryption");
        jButtonSettingsServiceCredentialsPasswordGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettingsServiceCredentialsPasswordGeneratorActionPerformed(evt);
            }
        });
        jPanelService.add(jButtonSettingsServiceCredentialsPasswordGenerator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jButtonSettingsServiceCredentialsShowPasswordQR.setText("QR Code");
        jButtonSettingsServiceCredentialsShowPasswordQR.setToolTipText("Show encryption password QR Code to scan with mobile app");
        jButtonSettingsServiceCredentialsShowPasswordQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettingsServiceCredentialsShowPasswordQRActionPerformed(evt);
            }
        });
        jPanelService.add(jButtonSettingsServiceCredentialsShowPasswordQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, -1, -1));
        jPanelService.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 414, 10));

        jLabelSettingsServiceEncryption.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSettingsServiceEncryption.setText("Encryption");
        jPanelService.add(jLabelSettingsServiceEncryption, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        jLabelSettingsServiceEncryptionPassword.setText("Password:");
        jLabelSettingsServiceEncryptionPassword.setToolTipText("Encryption Password");
        jPanelService.add(jLabelSettingsServiceEncryptionPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 335, -1, -1));

        jPasswordFieldSettingsServiceEncryptionPassword.setText("$upam[C@2eEp\\\"p?H");
        jPanelService.add(jPasswordFieldSettingsServiceEncryptionPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 333, 120, -1));

        jLabelSettingsServiceEncryptionPasswordWarning.setForeground(java.awt.Color.red);
        jPanelService.add(jLabelSettingsServiceEncryptionPasswordWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 335, 170, 20));

        jButtonSettingsServiceEncryptionPasswordGenerator.setText("Gen Pass");
        jButtonSettingsServiceEncryptionPasswordGenerator.setToolTipText("Generate password for encryption");
        jButtonSettingsServiceEncryptionPasswordGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettingsServiceEncryptionPasswordGeneratorActionPerformed(evt);
            }
        });
        jPanelService.add(jButtonSettingsServiceEncryptionPasswordGenerator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jButtonSettingsServiceEncryptionShowPasswordQR.setText("QR Code");
        jButtonSettingsServiceEncryptionShowPasswordQR.setToolTipText("Show encryption password QR Code to scan with mobile app");
        jButtonSettingsServiceEncryptionShowPasswordQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettingsServiceEncryptionShowPasswordQRActionPerformed(evt);
            }
        });
        jPanelService.add(jButtonSettingsServiceEncryptionShowPasswordQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, -1, -1));

        jPanel3.add(jPanelService, "card4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonApply.setText("Apply");
        jButtonApply.setEnabled(false);
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jButtonExport.setText("Export..");
        jButtonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportActionPerformed(evt);
            }
        });

        jButtonImport.setText("Import..");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonExport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonApply)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonApply)
                    .addComponent(jButtonOK)
                    .addComponent(jButtonExport)
                    .addComponent(jButtonImport))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonSettingsServiceEncryptionPasswordGeneratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsServiceEncryptionPasswordGeneratorActionPerformed
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
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
        jPasswordFieldSettingsServiceEncryptionPassword.setText(password);
        refreshApply();
    }//GEN-LAST:event_jButtonSettingsServiceEncryptionPasswordGeneratorActionPerformed

    private void jTreeSettingsMainOptionsListValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeSettingsMainOptionsListValueChanged
        String selection = ((DefaultMutableTreeNode) jTreeSettingsMainOptionsList.getLastSelectedPathComponent()).toString();
        switch (selection) {
            case "General":
                disableAlljLayeredPaneSettingsChildren();
                jPanelGeneral.setEnabled(true);
                jPanelGeneral.setVisible(true);
                break;
            case "Keyboard":
                disableAlljLayeredPaneSettingsChildren();
                jPanelKeyboard.setEnabled(true);
                jPanelKeyboard.setVisible(true);

                break;
            case "Service":
                disableAlljLayeredPaneSettingsChildren();
                jPanelService.setEnabled(true);
                jPanelService.setVisible(true);
                break;
        }
    }//GEN-LAST:event_jTreeSettingsMainOptionsListValueChanged

    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed
        Settings settings = IOOperations.loadCurrentSettingsObjectFromFile();
        int port = Integer.parseInt(jTextFieldSettingsServiceNetworkPort.getText());
        if (isPortValid(port) && (port != settings.getPort())) {
            int oldPort = settings.getPort();
            boolean serviceRunning = false;
            serviceRunning = IOOperations.checkService(oldPort);
            settings.setPort(port);
            IOOperations.saveCurrentSettings(settings);
            if (serviceRunning) {
                String[] loginParams = new String[2];
                loginParams[0] = settings.getUsername();
                loginParams[1] = new String(settings.getPassword());
                String[] command = new String[5];
                command[0] = "false";
                command[1] = settings.getUsername();
                command[2] = new String(settings.getPassword());
                command[3] = "reload";
                command[4] = Encryption.convertToSemiColonDelimited(loginParams);
                String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
                CommunicationClient client = new CommunicationClient(oldPort);
                String[] response = null;
                try {
                    response = client.sendCommand(param);
                } catch (IOException ex) {
                    Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
                }
                if ((response != null) && (!Boolean.parseBoolean(response[0]))) {
                    settings.setPort(oldPort);
                    IOOperations.saveCurrentSettings(settings);
                    return;
                } else {
                    //show dialog problem saving port
                    JOptionPane.showMessageDialog(null, "Problem saving port");
                }
            }
        }
        String credentialsUsername = jTextFieldSettingsServiceCredentialsUsername.getText();
        if ((isUsernameValid(credentialsUsername) && (!credentialsUsername.equals(settings.getUsername())))) {
            settings.setUsername(credentialsUsername);
        }
        char[] credentialsPassword = jPasswordFieldSettingsServiceCredentialsPassword.getPassword();
        if ((isCredentialsPasswordValid(credentialsPassword)) && (!(new String(credentialsPassword)).equals(new String(settings.getPassword())))) {
            settings.setPassword(credentialsPassword);
        }
        char[] encryptionPassword = jPasswordFieldSettingsServiceEncryptionPassword.getPassword();
        if ((isEncryptionPasswordValid(encryptionPassword)) && (!(new String(encryptionPassword)).equals(new String(settings.getEncryptionPassword())))) {
            settings.setEncryptionPassword(encryptionPassword);
        }
        settings.setShowProfileOptionsFileMenu(jCheckBoxProfileButtonsFileMenu.isSelected());
        if (settings.getShowProfileOptionsFileMenu()) {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorLoadSaveSetReset.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setEnabled(true);
        } else {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorLoadSaveSetReset.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setEnabled(false);
        }
        settings.setShowImportExportOptionsFileMenu(jCheckBoxSettingsImportExportFileMenu.isSelected());
        if (settings.getShowImportExportOptionsFileMenu()) {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorImportExport.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setEnabled(true);
        } else {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorImportExport.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setEnabled(false);
        }
        settings.setShowSystemTray(jCheckBoxSettingsSystemTray.isSelected());
        if (settings.getShowSystemTray()) {
            showSystemTray("true");
        } else {
            showSystemTray("false");
        }
        settings.setLayout(Keyboard.KeyboardLayout.valueOf(jComboBoxSettingsKeyboardLayout.getSelectedItem().toString()));
        IOOperations.saveCurrentSettings(settings);
        refreshApply();
    }//GEN-LAST:event_jButtonApplyActionPerformed

    private void jButtonSettingsServiceCredentialsPasswordGeneratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsServiceCredentialsPasswordGeneratorActionPerformed
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
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
        jPasswordFieldSettingsServiceCredentialsPassword.setText(password);
        refreshApply();
    }//GEN-LAST:event_jButtonSettingsServiceCredentialsPasswordGeneratorActionPerformed

    private void jButtonSettingsServiceCredentialsShowPasswordQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsServiceCredentialsShowPasswordQRActionPerformed
        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");
        byte[] b = null;
        try {
            ByteBuffer inputBuffer = ByteBuffer.wrap(new String(jPasswordFieldSettingsServiceCredentialsPassword.getPassword()).getBytes());

            // decode UTF-8
            CharBuffer data1 = utf8charset.decode(inputBuffer);

            // encode ISO-8559-1
            ByteBuffer bbuf = iso88591charset.encode(data1);
            b = bbuf.array();
            String data;
            data = new String(b, "ISO-8859-1");
            // get a byte matrix for the data
            BitMatrix matrix = null;
            int h = 200;
            int w = 200;
            com.google.zxing.Writer writer = new QRCodeWriter();
            matrix = writer.encode(data,
                    com.google.zxing.BarcodeFormat.QR_CODE, w, h);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setTitle("Credentials Password QR Code");
            dialog.add(new JLabel(new ImageIcon(bufferedImage)));
            dialog.pack();
            dialog.setLocationByPlatform(true);
            dialog.setVisible(true);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SettingsJDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (com.google.zxing.WriterException ex) {
            Logger.getLogger(SettingsJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSettingsServiceCredentialsShowPasswordQRActionPerformed

    private void jButtonSettingsServiceEncryptionShowPasswordQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsServiceEncryptionShowPasswordQRActionPerformed
        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");
        byte[] b = null;
        try {
            ByteBuffer inputBuffer = ByteBuffer.wrap(new String(jPasswordFieldSettingsServiceEncryptionPassword.getPassword()).getBytes());

            // decode UTF-8
            CharBuffer data1 = utf8charset.decode(inputBuffer);

            // encode ISO-8559-1
            ByteBuffer bbuf = iso88591charset.encode(data1);
            b = bbuf.array();
            String data;
            data = new String(b, "ISO-8859-1");
            // get a byte matrix for the data
            BitMatrix matrix = null;
            int h = 200;
            int w = 200;
            com.google.zxing.Writer writer = new QRCodeWriter();
            matrix = writer.encode(data,
                    com.google.zxing.BarcodeFormat.QR_CODE, w, h);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setTitle("Encryption Password QR Code");
            dialog.add(new JLabel(new ImageIcon(bufferedImage)));
            dialog.pack();
            dialog.setLocationByPlatform(true);
            dialog.setVisible(true);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SettingsJDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (com.google.zxing.WriterException ex) {
            Logger.getLogger(SettingsJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSettingsServiceEncryptionShowPasswordQRActionPerformed

    private void jCheckBoxProfileButtonsFileMenuStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxProfileButtonsFileMenuStateChanged
        //refreshApply();
    }//GEN-LAST:event_jCheckBoxProfileButtonsFileMenuStateChanged

    private void jCheckBoxSettingsImportExportFileMenuStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxSettingsImportExportFileMenuStateChanged
        //refreshApply();
    }//GEN-LAST:event_jCheckBoxSettingsImportExportFileMenuStateChanged

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        if (checkForChanges()) {
            applyChanges();
        }
        refreshApply();
        this.dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportActionPerformed
        if (checkForChanges()) {
            JOptionPane.showMessageDialog(null, "Apply changes before export", "Apply Changes", JOptionPane.WARNING_MESSAGE);
        } else {
            Settings settings = IOOperations.loadCurrentSettingsObjectFromFile();
            IOOperations.saveCurrentSettings(settings);
        }
    }//GEN-LAST:event_jButtonExportActionPerformed

    private void jComboBoxSettingsKeyboardLayoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSettingsKeyboardLayoutActionPerformed
        refreshApply();
    }//GEN-LAST:event_jComboBoxSettingsKeyboardLayoutActionPerformed

    private void jCheckBoxSettingsSystemTrayStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxSettingsSystemTrayStateChanged

    }//GEN-LAST:event_jCheckBoxSettingsSystemTrayStateChanged

    private void jCheckBoxSettingsSystemTrayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxSettingsSystemTrayItemStateChanged
        refreshApply();
    }//GEN-LAST:event_jCheckBoxSettingsSystemTrayItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SettingsJDialog dialog = new SettingsJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonExport;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonSettingsServiceCredentialsPasswordGenerator;
    private javax.swing.JButton jButtonSettingsServiceCredentialsShowPasswordQR;
    private javax.swing.JButton jButtonSettingsServiceEncryptionPasswordGenerator;
    private javax.swing.JButton jButtonSettingsServiceEncryptionShowPasswordQR;
    private javax.swing.JCheckBox jCheckBoxProfileButtonsFileMenu;
    private javax.swing.JCheckBox jCheckBoxSettingsImportExportFileMenu;
    private javax.swing.JCheckBox jCheckBoxSettingsSystemTray;
    private javax.swing.JComboBox<String> jComboBoxSettingsKeyboardLayout;
    private javax.swing.JLabel jLabelSettingsGeneralGeneral;
    private javax.swing.JLabel jLabelSettingsKeyboardLayout;
    private javax.swing.JLabel jLabelSettingsServiceCredentials;
    private javax.swing.JLabel jLabelSettingsServiceCredentialsPassword;
    private javax.swing.JLabel jLabelSettingsServiceCredentialsPasswordWarning;
    private javax.swing.JLabel jLabelSettingsServiceCredentialsUsername;
    private javax.swing.JLabel jLabelSettingsServiceCredentialsUsernameWarning;
    private javax.swing.JLabel jLabelSettingsServiceEncryption;
    private javax.swing.JLabel jLabelSettingsServiceEncryptionPassword;
    private javax.swing.JLabel jLabelSettingsServiceEncryptionPasswordWarning;
    private javax.swing.JLabel jLabelSettingsServiceNetwork;
    private javax.swing.JLabel jLabelSettingsServiceNetworkPort;
    private javax.swing.JLabel jLabelSettingsServiceNetworkPortWarning;
    private javax.swing.JLabel jLabelSettingsServiceService;
    private javax.swing.JLabel jLabelSettingsServiceServiceInfo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelGeneral;
    private javax.swing.JPanel jPanelKeyboard;
    private javax.swing.JPanel jPanelService;
    private javax.swing.JPasswordField jPasswordFieldSettingsServiceCredentialsPassword;
    private javax.swing.JPasswordField jPasswordFieldSettingsServiceEncryptionPassword;
    private javax.swing.JScrollPane jScrollPaneSettings;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jTextFieldSettingsServiceCredentialsUsername;
    private javax.swing.JTextField jTextFieldSettingsServiceNetworkPort;
    private javax.swing.JTree jTreeSettingsMainOptionsList;
    // End of variables declaration//GEN-END:variables

    public void checkPortValidity() {
        if (suitablePortNumber(jTextFieldSettingsServiceNetworkPort.getText())) {
            int port = Integer.parseInt(jTextFieldSettingsServiceNetworkPort.getText());
            if (port != getCurrentPort()) {
                jLabelSettingsServiceNetworkPortWarning.setText("");
                jLabelSettingsServiceNetworkPortWarning.setToolTipText("");
                if (!portInUse(port)) {
                    jButtonApply.setEnabled(true);
                } else if (!portInUseByService(port)) {
                    jLabelSettingsServiceNetworkPortWarning.setText("port already in use!");
                    jLabelSettingsServiceNetworkPortWarning.setToolTipText("port already used by another process");
                }
            } else {
                if (!IOOperations.checkService(port)) {
                    if (portInUse(port)) {
                        jLabelSettingsServiceNetworkPortWarning.setText("port already in use!");
                        jLabelSettingsServiceNetworkPortWarning.setToolTipText("port already used by another process");
                    } else {
                        jLabelSettingsServiceNetworkPortWarning.setText("");
                        jLabelSettingsServiceNetworkPortWarning.setToolTipText("");
                    }
                } else {
                    jLabelSettingsServiceNetworkPortWarning.setText("");
                    jLabelSettingsServiceNetworkPortWarning.setToolTipText("");
                }
                return;
            }
        } else {
            jLabelSettingsServiceNetworkPortWarning.setText("invalid port number!");
            jLabelSettingsServiceNetworkPortWarning.setToolTipText("port number should be a value between 1 and 65535");
        }
    }

    //checks if the given port is valid
    public boolean isPortValid(int port) {
        boolean validity = false;
        if (suitablePortNumber(port)) {
            if (port != getCurrentPort()) {
                if (!portInUse(port)) {
                    validity = true;
                }
            } else {
                if (!IOOperations.checkService(port)) {
                    if (!portInUse(port)) {
                        validity = true;
                    }
                } else {
                    validity = true;
                }
            }
        }
        return validity;
    }

    //checks if the username in the username text field is valid
    public void checkUsernameValidity() {
        String username = jTextFieldSettingsServiceCredentialsUsername.getText();
        boolean valid = (username != null) && username.matches("[A-Za-z0-9_]+") && (username.length() >= 5);
        if (valid) {
            jLabelSettingsServiceCredentialsUsernameWarning.setVisible(false);
            jLabelSettingsServiceCredentialsUsernameWarning.setText("");
            jLabelSettingsServiceCredentialsUsernameWarning.setToolTipText("");
            jButtonApply.setEnabled(true);
        } else {
            jLabelSettingsServiceCredentialsUsernameWarning.setVisible(true);
            jLabelSettingsServiceCredentialsUsernameWarning.setText("invalid username!");
            jLabelSettingsServiceCredentialsUsernameWarning.setToolTipText("<html>username must follow these rules:<br>"
                    + "only letters , digits and _ are allowed</html>");
        }
    }

    //checks if the given username is valid
    public boolean isUsernameValid(String username) {
        boolean valid = (username != null) && username.matches("[A-Za-z0-9_]+") && (username.length() >= 5);
        return valid;
    }

    //checks if the credentials password in the jpasswordfield is valid
    public void checkCredentialsPasswordValidity() {
        char[] allowedCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // length between 8 and 16 characters
                new LengthRule(8, 64),
                new AllowedCharacterRule(allowedCharacters),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() {
                        return "INSUFFICIENT_SPECIAL";
                    }

                    @Override
                    public String getCharacters() {
                        return new String(special);
                    }
                }, 1),
                // no whitespace
                new WhitespaceRule()));

        final char[] password = jPasswordFieldSettingsServiceCredentialsPassword.getPassword();
        RuleResult result = validator.validate(new PasswordData(new String(password)));

        if (result.isValid()) {
            jLabelSettingsServiceCredentialsPasswordWarning.setVisible(false);
            jLabelSettingsServiceCredentialsPasswordWarning.setText("");
            jLabelSettingsServiceCredentialsPasswordWarning.setToolTipText("");
            jPasswordFieldSettingsServiceCredentialsPassword.setToolTipText("");
            jButtonApply.setEnabled(true);
        } else {
            jLabelSettingsServiceCredentialsPasswordWarning.setVisible(true);
            jLabelSettingsServiceCredentialsPasswordWarning.setText("invalid password!");
            String error = "<html>Invalid password:<br><ul>";
            for (String msg : validator.getMessages(result)) {
                error += "<li>" + msg + "</li>";
            }
            error += "</ul></html>";
            jLabelSettingsServiceCredentialsPasswordWarning.setToolTipText(error);
            jPasswordFieldSettingsServiceCredentialsPassword.setToolTipText(error);
        }
    }

    //checks if the given credentials password is valid
    public boolean isCredentialsPasswordValid(char[] password) {
        char[] allowedCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // length between 8 and 16 characters
                new LengthRule(8, 64),
                new AllowedCharacterRule(allowedCharacters),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() {
                        return "INSUFFICIENT_SPECIAL";
                    }

                    @Override
                    public String getCharacters() {
                        return new String(special);
                    }
                }, 1),
                // no whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(new String(password)));
        return result.isValid();
    }

    //checks if the encryption password in the jpasswordfield is valid
    public void checkEncryptionPasswordValidity() {
        char[] allowedCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // length between 8 and 16 characters
                new LengthRule(8, 64),
                new AllowedCharacterRule(allowedCharacters),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() {
                        return "INSUFFICIENT_SPECIAL";
                    }

                    @Override
                    public String getCharacters() {
                        return new String(special);
                    }
                }, 1),
                // no whitespace
                new WhitespaceRule()));

        final char[] password = jPasswordFieldSettingsServiceEncryptionPassword.getPassword();
        RuleResult result = validator.validate(new PasswordData(new String(password)));

        if (result.isValid()) {
            jLabelSettingsServiceEncryptionPasswordWarning.setVisible(false);
            jLabelSettingsServiceEncryptionPasswordWarning.setText("");
            jLabelSettingsServiceEncryptionPasswordWarning.setToolTipText("");
            jPasswordFieldSettingsServiceEncryptionPassword.setToolTipText("");
            jButtonApply.setEnabled(true);
        } else {
            jLabelSettingsServiceEncryptionPasswordWarning.setVisible(true);
            jLabelSettingsServiceEncryptionPasswordWarning.setText("invalid password!");
            String error = "<html>Invalid password:<br><ul>";
            for (String msg : validator.getMessages(result)) {
                error += "<li>" + msg + "</li>";
            }
            error += "</ul></html>";
            jLabelSettingsServiceEncryptionPasswordWarning.setToolTipText(error);
            jPasswordFieldSettingsServiceEncryptionPassword.setToolTipText(error);
        }
    }

    //checks if the given encryption password is valid
    public boolean isEncryptionPasswordValid(char[] password) {
        char[] allowedCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // length between 8 and 16 characters
                new LengthRule(8, 64),
                new AllowedCharacterRule(allowedCharacters),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(new CharacterData() {
                    @Override
                    public String getErrorCode() {
                        return "INSUFFICIENT_SPECIAL";
                    }

                    @Override
                    public String getCharacters() {
                        return new String(special);
                    }
                }, 1),
                // no whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(new String(password)));
        return result.isValid();
    }

    //checks if the given string port is within the valid range
    private boolean suitablePortNumber(String port) {
        try {
            boolean f = ((Integer.parseInt(port) > 0) && (Integer.parseInt(port) <= 65535));
            return f;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    //checks if the given integer port is within the valid range
    private boolean suitablePortNumber(int port) {
        try {
            boolean f = ((port > 0) && (port <= 65535));
            return f;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    //checks if the given integer port is currently used
    public static boolean portInUse(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return false;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }
        return true;
    }

    //checks if the given integer port is currently used by the service
    private boolean portInUseByService(int port) {
        return IOOperations.checkService(port);
    }

    //returns current set port as an integer
    private int getCurrentPort() {
        int port = 9898;
        if (settings != null) {
            port = settings.getPort();
        }
        return port;
    }

    //disables all panes in setting pane and makes them invisible
    private void disableAlljLayeredPaneSettingsChildren() {
        jPanelGeneral.setEnabled(false);
        jPanelGeneral.setVisible(false);
        jPanelKeyboard.setEnabled(false);
        jPanelKeyboard.setVisible(false);
        jPanelService.setEnabled(false);
        jPanelService.setVisible(false);
    }

    //checks all fields in the settings dialog for any changes
    private boolean checkForChanges() {
        boolean changed = false;
        Settings settings = IOOperations.loadCurrentSettingsObjectFromFile();
        int port = Integer.parseInt(jTextFieldSettingsServiceNetworkPort.getText());
        if (isPortValid(port) && (port != settings.getPort())) {
            changed = true;
        }
        String credentialsUsername = jTextFieldSettingsServiceCredentialsUsername.getText();
        if ((isUsernameValid(credentialsUsername) && (!credentialsUsername.equals(settings.getUsername())))) {
            changed = true;
        }
        char[] credentialsPassword = jPasswordFieldSettingsServiceCredentialsPassword.getPassword();
        if ((isCredentialsPasswordValid(credentialsPassword)) && (!(new String(credentialsPassword)).equals(new String(settings.getPassword())))) {
            changed = true;
        }
        char[] encryptionPassword = jPasswordFieldSettingsServiceEncryptionPassword.getPassword();
        if ((isEncryptionPasswordValid(encryptionPassword)) && (!(new String(encryptionPassword)).equals(new String(settings.getEncryptionPassword())))) {
            changed = true;
        }
        boolean showProfileMenuOptionsFileMenu = jCheckBoxProfileButtonsFileMenu.isSelected();
        if (showProfileMenuOptionsFileMenu != settings.getShowProfileOptionsFileMenu()) {
            changed = true;
        }
        boolean showImportExportOptionsFileMenu = jCheckBoxSettingsImportExportFileMenu.isSelected();
        if (showImportExportOptionsFileMenu != settings.getShowImportExportOptionsFileMenu()) {
            changed = true;
        }
        boolean showSystemTray = jCheckBoxSettingsSystemTray.isSelected();
        if (showSystemTray != settings.getShowSystemTray()) {
            changed = true;
        }
        if (!jComboBoxSettingsKeyboardLayout.getSelectedItem().toString().equals(settings.getLayout().toString())) {
            changed = true;
        }
        return changed;
    }

    //saves all changes to the current settings file
    private void applyChanges() {
        Settings settings = IOOperations.loadCurrentSettingsObjectFromFile();
        int port = Integer.parseInt(jTextFieldSettingsServiceNetworkPort.getText());
        if (isPortValid(port) && (port != settings.getPort())) {
            int oldPort = settings.getPort();
            settings.setPort(port);
            IOOperations.saveCurrentSettings(settings);
            String[] loginParams = new String[2];
            loginParams[0] = settings.getUsername();
            loginParams[1] = new String(settings.getPassword());
            String[] command = new String[5];
            command[0] = "false";
            command[1] = settings.getUsername();
            command[2] = new String(settings.getPassword());
            command[3] = "reload";
            command[4] = Encryption.convertToSemiColonDelimited(loginParams);
            String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
            CommunicationClient client = new CommunicationClient(oldPort);
            String[] response = null;
            try {
                response = client.sendCommand(param);
            } catch (IOException ex) {
                Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
            }
            if ((response != null) && (!Boolean.parseBoolean(response[0]))) {
                settings.setPort(oldPort);
                IOOperations.saveCurrentSettings(settings);
                return;
            }//show dialog problem saving port
        }
        String credentialsUsername = jTextFieldSettingsServiceCredentialsUsername.getText();
        if ((isUsernameValid(credentialsUsername) && (!credentialsUsername.equals(settings.getUsername())))) {
            settings.setUsername(credentialsUsername);
        }
        char[] credentialsPassword = jPasswordFieldSettingsServiceCredentialsPassword.getPassword();
        if ((isCredentialsPasswordValid(credentialsPassword)) && (!(new String(credentialsPassword)).equals(new String(settings.getPassword())))) {
            settings.setPassword(credentialsPassword);
        }
        char[] encryptionPassword = jPasswordFieldSettingsServiceEncryptionPassword.getPassword();
        if ((isEncryptionPasswordValid(encryptionPassword)) && (!(new String(encryptionPassword)).equals(new String(settings.getEncryptionPassword())))) {
            settings.setEncryptionPassword(encryptionPassword);
        }
        settings.setShowProfileOptionsFileMenu(jCheckBoxProfileButtonsFileMenu.isSelected());
        if (settings.getShowProfileOptionsFileMenu()) {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorLoadSaveSetReset.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setEnabled(true);
        } else {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorLoadSaveSetReset.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemLoadProfile.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSaveProfile.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemSetProfile.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemResetProfile.setEnabled(false);
        }
        settings.setShowImportExportOptionsFileMenu(jCheckBoxSettingsImportExportFileMenu.isSelected());
        if (settings.getShowImportExportOptionsFileMenu()) {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorImportExport.setVisible(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setEnabled(true);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setEnabled(true);
        } else {
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jSeparatorImportExport.setVisible(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemImportSettings.setEnabled(false);
            ((MainJFrame) SwingUtilities.getWindowAncestor(this)).jMenuItemExportSettings.setEnabled(false);
        }
        if (settings.getShowSystemTray()) {
            showSystemTray("true");
        } else {
            showSystemTray("false");
        }
        settings.setLayout(Keyboard.KeyboardLayout.valueOf(jComboBoxSettingsKeyboardLayout.getSelectedItem().toString()));
        IOOperations.saveCurrentSettings(settings);
        jButtonApply.setEnabled(false);
    }

    //refreshes apply button according to the existence of any changes
    private void refreshApply() {
        if (firstTime) {
            return;
        }
        jButtonApply.setEnabled(checkForChanges());
    }

    private void showSystemTray(String showTray) {
        Settings settings;
        if (IOOperations.currentSettingsExist()) {
            settings = IOOperations.loadCurrentSettingsObjectFromFile();
        } else {
            settings = new Settings();
        }
        String[] params = new String[3];
        params[0] = settings.getUsername();
        params[1] = new String(settings.getPassword());
        params[2] = showTray;
        String[] command = new String[5];
        command[0] = "false";
        command[1] = settings.getUsername();
        command[2] = new String(settings.getPassword());
        command[3] = "systemtray";
        command[4] = Encryption.convertToSemiColonDelimited(params);
        String param = Encryption.encrypt(Encryption.convertToCommaDelimited(command), new String(settings.getEncryptionPassword()));
        CommunicationClient client = new CommunicationClient(settings.getPort());
        try {
            String[] response = client.sendCommand(param);
        } catch (IOException ex) {
            Logger.getLogger(LogiGSK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

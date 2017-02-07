/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package Logi.GSeries.Keyboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import javax.accessibility.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * The standard color swatch chooser.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with future Swing
 * releases. The current serialization support is appropriate for short term
 * storage or RMI between applications running the same version of Swing. As of
 * 1.4, support for long term storage of all JavaBeans&trade; has been added to
 * the <code>java.beans</code> package. Please see
 * {@link java.beans.XMLEncoder}.
 *
 * @author Steve Wilson
 */
class GrayScaleSwatchChooserPanel extends AbstractColorChooserPanel {

    SwatchPanel swatchPanel;
    RecentSwatchPanel recentSwatchPanel;
    MouseListener mainSwatchListener;
    MouseListener recentSwatchListener;
    private KeyListener mainSwatchKeyListener;
    private KeyListener recentSwatchKeyListener;

    public GrayScaleSwatchChooserPanel() {
        super();
        setInheritsPopupMenu(true);
    }

    public String getDisplayName() {
        return UIManager.getString("ColorChooser.swatchesNameText", getLocale());
    }

    /**
     * Provides a hint to the look and feel as to the <code>KeyEvent.VK</code>
     * constant that can be used as a mnemonic to access the panel. A return
     * value <= 0 indicates there is no mnemonic. <p>
     * The return value here is a hint, it is ultimately up to the look and feel
     * to honor the return value in some meaningful way.
     * <p>
     * This implementation looks up the value from the default
     * <code>ColorChooser.swatchesMnemonic</code>, or if it isn't available (or
     * not an <code>Integer</code>) returns -1. The lookup for the default is
     * done through the <code>UIManager</code>:
     * <code>UIManager.get("ColorChooser.swatchesMnemonic");</code>.
     *
     * @return KeyEvent.VK constant identifying the mnemonic; <= 0 for no
     * mnemonic @see #getDisplayedMnemonicI ndex @since 1.4
     */
    public int getMnemonic() {
        return getInt("ColorChooser.swatchesMnemonic", -1);
    }

    /**
     * Provides a hint to the look and feel as to the index of the character in
     * <code>getDisplayName</code> that should be visually identified as the
     * mnemonic. The look and feel should only use this if
     * <code>getMnemonic</code> returns a value > 0.
     * <p>
     * The return value here is a hint, it is ultimately up to the look and feel
     * to honor the return value in some meaningful way. For example, a look and
     * feel may wish to render each <code>AbstractColorChooserPanel</code> in a
     * <code>JTabbedPane</code>, and further use this return value to underline
     * a character in the <code>getDisplayName</code>.
     * <p>
     * This implementation looks up the value from the default
     * <code>ColorChooser.rgbDisplayedMnemonicIndex</code>, or if it isn't
     * available (or not an <code>Integer</code>) returns -1. The lookup for the
     * default is done through the <code>UIManager</code>:
     * <code>UIManager.get("ColorChooser.swatchesDisplayedMnemonicIndex");</code>.
     *
     * @return Character index to render mnemonic for; -1 to provide no visual
     * identifier for this panel.
     * @see #getMnemonic
     * @since 1.4
     */
    public int getDisplayedMnemonicIndex() {
        return getInt("ColorChooser.swatchesDisplayedMnemonicIndex", -1);
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }

    /**
     * The background color, foreground color, and font are already set to the
     * defaults from the defaults table before this method is called.
     */
    public void installChooserPanel(JColorChooser enclosingChooser) {
        super.installChooserPanel(enclosingChooser);
    }

    static int getInt(Object key, int defaultValue) {
        Object value = UIManager.get(key);

        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException nfe) {
            }
        }
        return defaultValue;
    }

    protected void buildChooser() {

        String recentStr = UIManager.getString("ColorChooser.swatchesRecentText", getLocale());

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel superHolder = new JPanel(gb);

        swatchPanel = new MainSwatchPanel();
        swatchPanel.putClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
                getDisplayName());
        swatchPanel.setInheritsPopupMenu(true);

        recentSwatchPanel = new RecentSwatchPanel();
        recentSwatchPanel.putClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
                recentStr);

        mainSwatchKeyListener = new MainSwatchKeyListener();
        mainSwatchListener = new MainSwatchListener();
        swatchPanel.addMouseListener(mainSwatchListener);
        swatchPanel.addKeyListener(mainSwatchKeyListener);
        recentSwatchListener = new RecentSwatchListener();
        recentSwatchKeyListener = new RecentSwatchKeyListener();
        recentSwatchPanel.addMouseListener(recentSwatchListener);
        recentSwatchPanel.addKeyListener(recentSwatchKeyListener);

        JPanel mainHolder = new JPanel(new BorderLayout());
        Border border = new CompoundBorder(new LineBorder(Color.black),
                new LineBorder(Color.white));
        mainHolder.setBorder(border);
        mainHolder.add(swatchPanel, BorderLayout.CENTER);

        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        Insets oldInsets = gbc.insets;
        gbc.insets = new Insets(0, 0, 0, 10);
        superHolder.add(mainHolder, gbc);
        gbc.insets = oldInsets;

        recentSwatchPanel.setInheritsPopupMenu(true);
        JPanel recentHolder = new JPanel(new BorderLayout());
        recentHolder.setBorder(border);
        recentHolder.setInheritsPopupMenu(true);
        recentHolder.add(recentSwatchPanel, BorderLayout.CENTER);

        JLabel l = new JLabel(recentStr);
        l.setLabelFor(recentSwatchPanel);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weighty = 1.0;
        superHolder.add(l, gbc);

        gbc.weighty = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 0, 2);
        superHolder.add(recentHolder, gbc);
        superHolder.setInheritsPopupMenu(true);

        add(superHolder);
    }

    public void uninstallChooserPanel(JColorChooser enclosingChooser) {
        super.uninstallChooserPanel(enclosingChooser);
        swatchPanel.removeMouseListener(mainSwatchListener);
        swatchPanel.removeKeyListener(mainSwatchKeyListener);
        recentSwatchPanel.removeMouseListener(recentSwatchListener);
        recentSwatchPanel.removeKeyListener(recentSwatchKeyListener);

        swatchPanel = null;
        recentSwatchPanel = null;
        mainSwatchListener = null;
        mainSwatchKeyListener = null;
        recentSwatchListener = null;
        recentSwatchKeyListener = null;

        removeAll();  // strip out all the sub-components
    }

    public void updateChooser() {

    }

    private class RecentSwatchKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                Color color = recentSwatchPanel.getSelectedColor();
                getColorSelectionModel().setSelectedColor(color);
            }
        }
    }

    private class MainSwatchKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                Color color = swatchPanel.getSelectedColor();
                getColorSelectionModel().setSelectedColor(color);
                recentSwatchPanel.setMostRecentColor(color);
            }
        }
    }

    class RecentSwatchListener extends MouseAdapter implements Serializable {

        public void mousePressed(MouseEvent e) {
            if (isEnabled()) {
                Color color = recentSwatchPanel.getColorForLocation(e.getX(), e.getY());
                recentSwatchPanel.setSelectedColorFromLocation(e.getX(), e.getY());
                getColorSelectionModel().setSelectedColor(color);
                recentSwatchPanel.requestFocusInWindow();
            }
        }
    }

    class MainSwatchListener extends MouseAdapter implements Serializable {

        public void mousePressed(MouseEvent e) {
            if (isEnabled()) {
                Color color = swatchPanel.getColorForLocation(e.getX(), e.getY());
                getColorSelectionModel().setSelectedColor(color);
                swatchPanel.setSelectedColorFromLocation(e.getX(), e.getY());
                recentSwatchPanel.setMostRecentColor(color);
                swatchPanel.requestFocusInWindow();
            }
        }
    }

}

class SwatchPanel extends JPanel {

    protected Color[] colors;
    protected Dimension swatchSize;
    protected Dimension numSwatches;
    protected Dimension gap;

    private int selRow;
    private int selCol;

    public SwatchPanel() {
        initValues();
        initColors();
        setToolTipText(""); // register for events
        setOpaque(true);
        setBackground(Color.white);
        setFocusable(true);
        setInheritsPopupMenu(true);

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                repaint();
            }

            public void focusLost(FocusEvent e) {
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int typed = e.getKeyCode();
                switch (typed) {
                    case KeyEvent.VK_UP:
                        if (selRow > 0) {
                            selRow--;
                            repaint();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (selRow < numSwatches.height - 1) {
                            selRow++;
                            repaint();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (selCol > 0 && SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
                            selCol--;
                            repaint();
                        } else if (selCol < numSwatches.width - 1
                                && !SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
                            selCol++;
                            repaint();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (selCol < numSwatches.width - 1
                                && SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
                            selCol++;
                            repaint();
                        } else if (selCol > 0 && !SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
                            selCol--;
                            repaint();
                        }
                        break;
                    case KeyEvent.VK_HOME:
                        selCol = 0;
                        selRow = 0;
                        repaint();
                        break;
                    case KeyEvent.VK_END:
                        selCol = numSwatches.width - 1;
                        selRow = numSwatches.height - 1;
                        repaint();
                        break;
                }
            }
        });
    }

    public Color getSelectedColor() {
        return getColorForCell(selCol, selRow);
    }

    protected void initValues() {

    }

    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int row = 0; row < numSwatches.height; row++) {
            int y = row * (swatchSize.height + gap.height);
            for (int column = 0; column < numSwatches.width; column++) {
                Color c = getColorForCell(column, row);
                g.setColor(c);
                int x;
                if (!this.getComponentOrientation().isLeftToRight()) {
                    x = (numSwatches.width - column - 1) * (swatchSize.width + gap.width);
                } else {
                    x = column * (swatchSize.width + gap.width);
                }
                g.fillRect(x, y, swatchSize.width, swatchSize.height);
                g.setColor(Color.black);
                g.drawLine(x + swatchSize.width - 1, y, x + swatchSize.width - 1, y + swatchSize.height - 1);
                g.drawLine(x, y + swatchSize.height - 1, x + swatchSize.width - 1, y + swatchSize.height - 1);

                if (selRow == row && selCol == column && this.isFocusOwner()) {
                    Color c2 = new Color(c.getRed() < 125 ? 255 : 0,
                            c.getGreen() < 125 ? 255 : 0,
                            c.getBlue() < 125 ? 255 : 0);
                    g.setColor(c2);

                    g.drawLine(x, y, x + swatchSize.width - 1, y);
                    g.drawLine(x, y, x, y + swatchSize.height - 1);
                    g.drawLine(x + swatchSize.width - 1, y, x + swatchSize.width - 1, y + swatchSize.height - 1);
                    g.drawLine(x, y + swatchSize.height - 1, x + swatchSize.width - 1, y + swatchSize.height - 1);
                    g.drawLine(x, y, x + swatchSize.width - 1, y + swatchSize.height - 1);
                    g.drawLine(x, y + swatchSize.height - 1, x + swatchSize.width - 1, y);
                }
            }
        }
    }

    public Dimension getPreferredSize() {
        int x = numSwatches.width * (swatchSize.width + gap.width) - 1;
        int y = numSwatches.height * (swatchSize.height + gap.height) - 1;
        return new Dimension(x, y);
    }

    protected void initColors() {

    }

    public String getToolTipText(MouseEvent e) {
        Color color = getColorForLocation(e.getX(), e.getY());
        return color.getRed() + ", " + color.getGreen() + ", " + color.getBlue();
    }

    public void setSelectedColorFromLocation(int x, int y) {
        if (!this.getComponentOrientation().isLeftToRight()) {
            selCol = numSwatches.width - x / (swatchSize.width + gap.width) - 1;
        } else {
            selCol = x / (swatchSize.width + gap.width);
        }
        selRow = y / (swatchSize.height + gap.height);
        repaint();
    }

    public Color getColorForLocation(int x, int y) {
        int column;
        if (!this.getComponentOrientation().isLeftToRight()) {
            column = numSwatches.width - x / (swatchSize.width + gap.width) - 1;
        } else {
            column = x / (swatchSize.width + gap.width);
        }
        int row = y / (swatchSize.height + gap.height);
        return getColorForCell(column, row);
    }

    private Color getColorForCell(int column, int row) {
        return colors[(row * numSwatches.width) + column]; // (STEVE) - change data orientation here
    }

}

class RecentSwatchPanel extends SwatchPanel {

    protected void initValues() {
        swatchSize = UIManager.getDimension("ColorChooser.swatchesRecentSwatchSize", getLocale());
        numSwatches = new Dimension(5, 7);
        gap = new Dimension(1, 1);
    }

    protected void initColors() {
        Color defaultRecentColor = UIManager.getColor("ColorChooser.swatchesDefaultRecentColor", getLocale());
        int numColors = numSwatches.width * numSwatches.height;

        colors = new Color[numColors];
        for (int i = 0; i < numColors; i++) {
            colors[i] = defaultRecentColor;
        }
    }

    public void setMostRecentColor(Color c) {

        System.arraycopy(colors, 0, colors, 1, colors.length - 1);
        colors[0] = c;
        repaint();
    }

}

class MainSwatchPanel extends SwatchPanel {

    protected void initValues() {
        swatchSize = UIManager.getDimension("ColorChooser.swatchesSwatchSize", getLocale());
        numSwatches = new Dimension(31, 9);
        gap = new Dimension(1, 1);
    }

    protected void initColors() {
        int[] rawValues = initRawValues();
        int numColors = rawValues.length / 3;

        colors = new Color[numColors];
        for (int i = 0; i < numColors; i++) {
            colors[i] = new Color(rawValues[(i * 3)], rawValues[(i * 3) + 1], rawValues[(i * 3) + 2]);
        }
    }

    private int[] initRawValues() {

        int[] rawValues = {
            255, 255, 255, // first row.
            240, 240, 240,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            210, 210, 210,
            225, 225, 225,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            219, 219, 219,
            249, 249, 249,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            234, 234, 234,
            204, 204, 204, // second row.
            224, 224, 224,
            194, 194, 194,
            164, 164, 164,
            164, 164, 164,
            164, 164, 164,
            164, 164, 164,
            164, 164, 164,
            164, 164, 164,
            164, 164, 164,
            180, 180, 180,
            195, 195, 195,
            189, 189, 189,
            184, 184, 184,
            184, 184, 184,
            184, 184, 184,
            184, 184, 184,
            184, 184, 184,
            184, 184, 184,
            184, 184, 184,
            214, 214, 214,
            244, 244, 244,
            228, 228, 228,
            213, 213, 213,
            213, 213, 213,
            213, 213, 213,
            213, 213, 213,
            213, 213, 213,
            213, 213, 213,
            213, 213, 213,
            219, 219, 219,
            204, 204, 204, // third row
            209, 209, 209,
            179, 179, 179,
            149, 149, 149,
            119, 119, 119,
            119, 119, 119,
            119, 119, 119,
            119, 119, 119,
            119, 119, 119,
            134, 134, 134,
            149, 149, 149,
            165, 165, 165,
            159, 159, 159,
            154, 154, 154,
            148, 148, 148,
            148, 148, 148,
            148, 148, 148,
            148, 148, 148,
            148, 148, 148,
            178, 178, 178,
            208, 208, 208,
            238, 238, 238,
            223, 223, 223,
            208, 208, 208,
            192, 192, 192,
            192, 192, 192,
            192, 192, 192,
            192, 192, 192,
            192, 192, 192,
            198, 198, 198,
            203, 203, 203,
            153, 153, 153, // fourth row
            194, 194, 194,
            164, 164, 164,
            134, 134, 134,
            104, 104, 104,
            73, 73, 73,
            73, 73, 73,
            73, 73, 73,
            89, 89, 89,
            104, 104, 104,
            119, 119, 119,
            135, 135, 135,
            129, 129, 129,
            123, 123, 123,
            118, 118, 118,
            112, 112, 112,
            112, 112, 112,
            112, 112, 112,
            142, 142, 142,
            172, 172, 172,
            202, 202, 202,
            233, 233, 233,
            217, 217, 217,
            202, 202, 202,
            187, 187, 187,
            171, 171, 171,
            171, 171, 171,
            171, 171, 171,
            177, 177, 177,
            183, 183, 183,
            188, 188, 188,
            153, 153, 153, // Fifth row
            179, 179, 179,
            148, 148, 148,
            118, 118, 118,
            88, 88, 88,
            58, 58, 58,
            28, 28, 28,
            43, 43, 43,
            59, 59, 59,
            74, 74, 74,
            89, 89, 89,
            105, 105, 105,
            99, 99, 99,
            93, 93, 93,
            88, 88, 88,
            82, 82, 82,
            77, 77, 77,
            107, 107, 107,
            137, 137, 137,
            167, 167, 167,
            197, 197, 197,
            227, 227, 227,
            212, 212, 212,
            196, 196, 196,
            181, 181, 181,
            166, 166, 166,
            150, 150, 150,
            156, 156, 156,
            162, 162, 162,
            167, 167, 167,
            173, 173, 173,
            102, 102, 102, // sixth row
            143, 143, 143,
            143, 143, 143,
            113, 113, 113,
            83, 83, 83,
            53, 53, 53,
            22, 22, 22,
            38, 38, 38,
            53, 53, 53,
            68, 68, 68,
            84, 84, 84,
            84, 84, 84,
            84, 84, 84,
            78, 78, 78,
            72, 72, 72,
            67, 67, 67,
            61, 61, 61,
            91, 91, 91,
            121, 121, 121,
            151, 151, 151,
            182, 182, 182,
            182, 182, 182,
            182, 182, 182,
            166, 166, 166,
            151, 151, 151,
            136, 136, 136,
            120, 120, 120,
            126, 126, 126,
            132, 132, 132,
            137, 137, 137,
            143, 143, 143,
            102, 102, 102, // seventh row
            107, 107, 107,
            107, 107, 107,
            107, 107, 107,
            77, 77, 77,
            47, 47, 47,
            17, 17, 17,
            32, 32, 32,
            47, 47, 47,
            63, 63, 63,
            63, 63, 63,
            63, 63, 63,
            63, 63, 63,
            63, 63, 63,
            57, 57, 57,
            52, 52, 52,
            46, 46, 46,
            76, 76, 76,
            106, 106, 106,
            136, 136, 136,
            136, 136, 136,
            136, 136, 136,
            136, 136, 136,
            136, 136, 136,
            121, 121, 121,
            106, 106, 106,
            90, 90, 90,
            96, 96, 96,
            101, 101, 101,
            107, 107, 107,
            107, 107, 107,
            51, 51, 51, // eigth row
            71, 71, 71,
            71, 71, 71,
            71, 71, 71,
            71, 71, 71,
            41, 41, 41,
            11, 11, 11,
            27, 27, 27,
            42, 42, 42,
            42, 42, 42,
            42, 42, 42,
            42, 42, 42,
            42, 42, 42,
            42, 42, 42,
            42, 42, 42,
            36, 36, 36,
            31, 31, 31,
            61, 61, 61,
            91, 91, 91,
            91, 91, 91,
            91, 91, 91,
            91, 91, 91,
            91, 91, 91,
            91, 91, 91,
            91, 91, 91,
            75, 75, 75,
            60, 60, 60,
            66, 66, 66,
            71, 71, 71,
            71, 71, 71,
            71, 71, 71,
            0, 0, 0, // ninth row
            36, 36, 36,
            36, 36, 36,
            36, 36, 36,
            36, 36, 36,
            36, 36, 36,
            6, 6, 6,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            21, 21, 21,
            15, 15, 15,
            45, 45, 45,
            45, 45, 45,
            45, 45, 45,
            45, 45, 45,
            45, 45, 45,
            45, 45, 45,
            45, 45, 45,
            45, 45, 45,
            30, 30, 30,
            36, 36, 36,
            36, 36, 36,
            36, 36, 36,
            36, 36, 36,
            51, 51, 51};
        return rawValues;
    }
}

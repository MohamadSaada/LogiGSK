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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

/**
 *
 * @author Mohamad Saada
 */
public class GrayScalePanel extends AbstractColorChooserPanel
        implements ChangeListener, ActionListener {

    JSlider scale;
    JTextField percentField;

    // Set up our list of grays. We'll assume we have all 256 possible shades,
    // and we'll do it when the class is loaded.
    static Color[] grays = new Color[256];

    static {
        for (int i = 0; i < 256; i++) {
            grays[i] = new Color(i, i, i);
        }
    }

    public GrayScalePanel() {
        setLayout(new GridLayout(0, 1));

        // Create the slider and attach us as a listener.
        scale = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
        scale.addChangeListener(this);

        // Set up our display for the chooser.
        add(new JLabel("Pick your shade of gray:", JLabel.CENTER));
        JPanel jp = new JPanel();
        jp.add(new JLabel("Black"));
        jp.add(scale);
        jp.add(new JLabel("White"));
        add(jp);

        JPanel jp2 = new JPanel();
        percentField = new JTextField(3);
        percentField.setHorizontalAlignment(SwingConstants.RIGHT);
        percentField.addActionListener(this);
        jp2.add(percentField);
        jp2.add(new JLabel("%"));
        add(jp2);
    }

    // We did this work in the constructor, so we can skip it here.
    protected void buildChooser() {
    }

    // Make sure the slider is in sync with the other panels.
    public void updateChooser() {
        Color c = getColorSelectionModel().getSelectedColor();
        scale.setValue(toGray(c));
    }

    protected int toGray(Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        // Grab the luminance the same way GIMP does.
        return (int) Math.round(0.3 * r + 0.59 * g + 0.11 * b);
    }

    // Pick a name for our tab in the chooser.
    public String getDisplayName() {
        return "Gray Scale";
    }

    // No need for an icon
    public Icon getSmallDisplayIcon() {
        return null;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }
    
    // Finally, update the selection model as our slider changes.
    public void stateChanged(ChangeEvent ce) {
        getColorSelectionModel().setSelectedColor(grays[scale.getValue()]);
        percentField.setText("" + (100 - (int) Math.round(scale.getValue() / 2.55)));
    }

    public void actionPerformed(ActionEvent ae) {
        int val = 100 - Integer.parseInt(ae.getActionCommand());
        getColorSelectionModel().setSelectedColor(grays[(int) (val * 2.55)]);
    }
}

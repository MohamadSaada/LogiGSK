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

import java.awt.Color;
import java.awt.Component;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author Mohamad Saada
 */
public class JColorChooserGrayScale extends JColorChooser {

    public static Color showDialog(Component component, String title,
            Color initial) {
        JColorChooser choose = new JColorChooser(initial);

        JDialog dialog = createDialog(component, title, true, choose, null, null);

        AbstractColorChooserPanel[] panels = choose.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
            choose.removeChooserPanel(accp);
        }
        GrayScaleSwatchChooserPanel grayScaleSwatchChooserPanelFreeStyle = new GrayScaleSwatchChooserPanel();
        GrayScalePanel grayScalePanelFreeStyle = new GrayScalePanel();
        choose.addChooserPanel(grayScaleSwatchChooserPanelFreeStyle);
        choose.addChooserPanel(grayScalePanelFreeStyle);

        dialog.getContentPane().add(choose);
        dialog.pack();
        dialog.setVisible(true);//.show();

        return choose.getColor();
    }

}

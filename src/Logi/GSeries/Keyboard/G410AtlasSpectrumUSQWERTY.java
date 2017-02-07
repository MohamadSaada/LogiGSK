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

import Logi.GSeries.Libraries.IOOperations;
import Logi.GSeries.Libraries.Effects;
import Logi.GSeries.Libraries.StarEffect;
import Logi.GSeries.Libraries.GKeyboard;
import Logi.GSeries.Libraries.Keyboard;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author Mohamad Saada
 */
public class G410AtlasSpectrumUSQWERTY extends javax.swing.JInternalFrame {

    private String selectedButtonText;
    private String selectedButton;
    private boolean EffectsRunning = false;
    private boolean breathingSpeedSliderManualChange = false;
    private boolean colourCycleSpeedSliderManualChange = false;
    private boolean colourWaveSpeedSliderManualChange = false;
    private boolean resettingEffect = false;
    private final int keyCount = 89;
    GKeyboard gKeyboard = null;
    private VirtualKeyboardBreathingThread breathingThread = null;
    private VirtualKeyboardColourCycleThread colourCycleThread = null;
    private VirtualKeyboardColourWaveThread colourWaveThread = null;

    /**
     * Creates new form NewJInternalFrame
     */
    public G410AtlasSpectrumUSQWERTY() {
        initComponents();

        //Remove title bar from this JInternalFrame (Cosmetic)
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

        //instantiate a GKeyboard object
        gKeyboard = new GKeyboard(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);

        //load current profile from file
        gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);

        //if profile is of an effect then reset effect, otherwise load colour profile
        if (gKeyboard.getEffect() != null) {
            //reset effect
            resetEffect();
        } else {
            //load colour profile
            loadColours(convertStandardKeyColourMapToG410ColourMap(gKeyboard.getKeyColourMap()));
            jSliderBrightness.setValue(getAverageBrightness());
            disableAlljLayeredPaneEffectsChildren();
        }

        //this is to reset the startup effect to what the user has chosen previously
        resetStartupEffect();

        //Free Style selected button colour change
        jColorChooserFreeStyle.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                stopGKeyboardEffects(true);
                String hexColour = String.format("%02x%02x%02x",
                        jColorChooserFreeStyle.getColor().getRed(),
                        jColorChooserFreeStyle.getColor().getGreen(),
                        jColorChooserFreeStyle.getColor().getBlue());

                if (selectedButton != null) {
                    switch (selectedButton) {
                        case "001":
                            jPanelColour001.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour001);
                            gKeyboard.setKey("esc", hexColour, true);
                            break;
                        case "002":
                            jPanelColour002.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour002);
                            gKeyboard.setKey("f1", hexColour, true);
                            break;
                        case "003":
                            jPanelColour003.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour003);
                            gKeyboard.setKey("f2", hexColour, true);
                            break;
                        case "004":
                            jPanelColour004.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour004);
                            gKeyboard.setKey("f3", hexColour, true);
                            break;
                        case "005":
                            jPanelColour005.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour005);
                            gKeyboard.setKey("f4", hexColour, true);
                            break;
                        case "006":
                            jPanelColour006.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour006);
                            gKeyboard.setKey("f5", hexColour, true);
                            break;
                        case "007":
                            jPanelColour007.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour007);
                            gKeyboard.setKey("f6", hexColour, true);
                            break;
                        case "008":
                            jPanelColour008.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour008);
                            gKeyboard.setKey("f7", hexColour, true);
                            break;
                        case "009":
                            jPanelColour009.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour009);
                            gKeyboard.setKey("f8", hexColour, true);
                            break;
                        case "010":
                            jPanelColour010.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour010);
                            gKeyboard.setKey("f9", hexColour, true);
                            break;
                        case "011":
                            jPanelColour011.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour011);
                            gKeyboard.setKey("f10", hexColour, true);
                            break;
                        case "012":
                            jPanelColour012.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour012);
                            gKeyboard.setKey("f11", hexColour, true);
                            break;
                        case "013":
                            jPanelColour013.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour013);
                            gKeyboard.setKey("f12", hexColour, true);
                            break;
                        case "014":
                            jPanelColour014.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour014);
                            gKeyboard.setKey("print_screen", hexColour, true);
                            break;
                        case "015":
                            jPanelColour015.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour015);
                            gKeyboard.setKey("scroll_lock", hexColour, true);
                            break;
                        case "016":
                            jPanelColour016.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour016);
                            gKeyboard.setKey("pause_break", hexColour, true);
                            break;
                        case "017":
                            jPanelColour017.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour017);
                            gKeyboard.setKey("tilde", hexColour, true);
                            break;
                        case "018":
                            jPanelColour018.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour018);
                            gKeyboard.setKey("1", hexColour, true);
                            break;
                        case "019":
                            jPanelColour019.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour019);
                            gKeyboard.setKey("2", hexColour, true);
                            break;
                        case "020":
                            jPanelColour020.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour020);
                            gKeyboard.setKey("3", hexColour, true);
                            break;
                        case "021":
                            jPanelColour021.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour021);
                            gKeyboard.setKey("4", hexColour, true);
                            break;
                        case "022":
                            jPanelColour022.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour022);
                            gKeyboard.setKey("5", hexColour, true);
                            break;
                        case "023":
                            jPanelColour023.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour023);
                            gKeyboard.setKey("6", hexColour, true);
                            break;
                        case "024":
                            jPanelColour024.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour024);
                            gKeyboard.setKey("7", hexColour, true);
                            break;
                        case "025":
                            jPanelColour025.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour025);
                            gKeyboard.setKey("8", hexColour, true);
                            break;
                        case "026":
                            jPanelColour026.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour026);
                            gKeyboard.setKey("9", hexColour, true);
                            break;
                        case "027":
                            jPanelColour027.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour027);
                            gKeyboard.setKey("0", hexColour, true);
                            break;
                        case "028":
                            jPanelColour028.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour028);
                            gKeyboard.setKey("minus", hexColour, true);
                            break;
                        case "029":
                            jPanelColour029.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour029);
                            gKeyboard.setKey("equal", hexColour, true);
                            break;
                        case "030":
                            jPanelColour030.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour030);
                            gKeyboard.setKey("backspace", hexColour, true);
                            break;
                        case "031":
                            jPanelColour031.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour031);
                            gKeyboard.setKey("insert", hexColour, true);
                            break;
                        case "032":
                            jPanelColour032.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour032);
                            gKeyboard.setKey("home", hexColour, true);
                            break;
                        case "033":
                            jPanelColour033.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour033);
                            gKeyboard.setKey("page_up", hexColour, true);
                            break;
                        case "034":
                            jPanelColour034.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour034);
                            gKeyboard.setKey("tab", hexColour, true);
                            break;
                        case "035":
                            jPanelColour035.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour035);
                            gKeyboard.setKey("q", hexColour, true);
                            break;
                        case "036":
                            jPanelColour036.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour036);
                            gKeyboard.setKey("w", hexColour, true);
                            break;
                        case "037":
                            jPanelColour037.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour037);
                            gKeyboard.setKey("e", hexColour, true);
                            break;
                        case "038":
                            jPanelColour038.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour038);
                            gKeyboard.setKey("r", hexColour, true);
                            break;
                        case "039":
                            jPanelColour039.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour039);
                            gKeyboard.setKey("t", hexColour, true);
                            break;
                        case "040":
                            jPanelColour040.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour040);
                            gKeyboard.setKey("y", hexColour, true);
                            break;
                        case "041":
                            jPanelColour041.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour041);
                            gKeyboard.setKey("u", hexColour, true);
                            break;
                        case "042":
                            jPanelColour042.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour042);
                            gKeyboard.setKey("i", hexColour, true);
                            break;
                        case "043":
                            jPanelColour043.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour043);
                            gKeyboard.setKey("o", hexColour, true);
                            break;
                        case "044":
                            jPanelColour044.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour044);
                            gKeyboard.setKey("p", hexColour, true);
                            break;
                        case "045":
                            jPanelColour045.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour045);
                            gKeyboard.setKey("open_bracket", hexColour, true);
                            break;
                        case "046":
                            jPanelColour046.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour046);
                            gKeyboard.setKey("close_bracket", hexColour, true);
                            break;
                        case "047":
                            jPanelColour047.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour047);
                            gKeyboard.setKey("backslash", hexColour, true);
                            break;
                        case "048":
                            jPanelColour048.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour048);
                            gKeyboard.setKey("del", hexColour, true);
                            break;
                        case "049":
                            jPanelColour049.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour049);
                            gKeyboard.setKey("end", hexColour, true);
                            break;
                        case "050":
                            jPanelColour050.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour050);
                            gKeyboard.setKey("page_down", hexColour, true);
                            break;
                        case "051":
                            jPanelColour051.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour051);
                            gKeyboard.setKey("caps_lock", hexColour, true);
                            break;
                        case "052":
                            jPanelColour052.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour052);
                            gKeyboard.setKey("a", hexColour, true);
                            break;
                        case "053":
                            jPanelColour053.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour053);
                            gKeyboard.setKey("s", hexColour, true);
                            break;
                        case "054":
                            jPanelColour054.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour054);
                            gKeyboard.setKey("d", hexColour, true);
                            break;
                        case "055":
                            jPanelColour055.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour055);
                            gKeyboard.setKey("f", hexColour, true);
                            break;
                        case "056":
                            jPanelColour056.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour056);
                            gKeyboard.setKey("g", hexColour, true);
                            break;
                        case "057":
                            jPanelColour057.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour057);
                            gKeyboard.setKey("h", hexColour, true);
                            break;
                        case "058":
                            jPanelColour058.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour058);
                            gKeyboard.setKey("j", hexColour, true);
                            break;
                        case "059":
                            jPanelColour059.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour059);
                            gKeyboard.setKey("k", hexColour, true);
                            break;
                        case "060":
                            jPanelColour060.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour060);
                            gKeyboard.setKey("l", hexColour, true);
                            break;
                        case "061":
                            jPanelColour061.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour061);
                            gKeyboard.setKey("semicolon", hexColour, true);
                            break;
                        case "062":
                            jPanelColour061.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour062);
                            gKeyboard.setKey("quote", hexColour, true);
                            break;
                        case "063":
                            jPanelColour063.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour063);
                            gKeyboard.setKey("enter", hexColour, true);
                            break;
                        case "064":
                            jPanelColour064.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour064);
                            gKeyboard.setKey("shift_left", hexColour, true);
                            break;
                        case "065":
                            jPanelColour065.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour065);
                            gKeyboard.setKey("z", hexColour, true);
                            break;
                        case "066":
                            jPanelColour066.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour066);
                            gKeyboard.setKey("x", hexColour, true);
                            break;
                        case "067":
                            jPanelColour067.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour067);
                            gKeyboard.setKey("c", hexColour, true);
                            break;
                        case "068":
                            jPanelColour068.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour068);
                            gKeyboard.setKey("v", hexColour, true);
                            break;
                        case "069":
                            jPanelColour069.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour069);
                            gKeyboard.setKey("b", hexColour, true);
                            break;
                        case "070":
                            jPanelColour070.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour070);
                            gKeyboard.setKey("n", hexColour, true);
                            break;
                        case "071":
                            jPanelColour071.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour071);
                            gKeyboard.setKey("m", hexColour, true);
                            break;
                        case "072":
                            jPanelColour072.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour072);
                            gKeyboard.setKey("comma", hexColour, true);
                            break;
                        case "073":
                            jPanelColour073.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour073);
                            gKeyboard.setKey("period", hexColour, true);
                            break;
                        case "074":
                            jPanelColour074.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour074);
                            gKeyboard.setKey("slash", hexColour, true);
                            break;
                        case "075":
                            jPanelColour075.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour075);
                            gKeyboard.setKey("shift_right", hexColour, true);
                            break;
                        case "076":
                            jPanelColour076.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour076);
                            gKeyboard.setKey("arrow_top", hexColour, true);
                            break;
                        case "077":
                            jPanelColour077.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour077);
                            gKeyboard.setKey("ctrl_left", hexColour, true);
                            break;
                        case "078":
                            jPanelColour078.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour078);
                            gKeyboard.setKey("win_left", hexColour, true);
                            break;
                        case "079":
                            jPanelColour079.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour079);
                            gKeyboard.setKey("alt_left", hexColour, true);
                            break;
                        case "080":
                            jPanelColour080.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour080);
                            gKeyboard.setKey("space", hexColour, true);
                            break;
                        case "081":
                            jPanelColour081.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour081);
                            gKeyboard.setKey("alt_right", hexColour, true);
                            break;
                        case "082":
                            jPanelColour082.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour082);
                            gKeyboard.setKey("win_right", hexColour, true);
                            break;
                        case "083":
                            jPanelColour083.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour083);
                            gKeyboard.setKey("menu", hexColour, true);
                            break;
                        case "084":
                            jPanelColour084.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour084);
                            gKeyboard.setKey("ctrl_right", hexColour, true);
                            break;
                        case "085":
                            jPanelColour085.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour085);
                            gKeyboard.setKey("arrow_left", hexColour, true);
                            break;
                        case "086":
                            jPanelColour086.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour086);
                            gKeyboard.setKey("arrow_bottom", hexColour, true);
                            break;
                        case "087":
                            jPanelColour087.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour087);
                            gKeyboard.setKey("arrow_right", hexColour, true);
                            break;
                        case "088":
                            jPanelColour088.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour088);
                            gKeyboard.setKey("game", hexColour, true);
                            break;
                        case "089":
                            jPanelColour089.setBackground(jColorChooserFreeStyle.getColor());
                            jLayeredPane1.moveToBack(jPanelColour089);
                            gKeyboard.setKey("backlight", hexColour, true);
                            break;
                    }
                }
            }
        });

        //Zones selected zone change colour
        jColorChooserZones.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                stopGKeyboardEffects(true);
                String hexColour = String.format("%02x%02x%02x",
                        jColorChooserZones.getColor().getRed(),
                        jColorChooserZones.getColor().getGreen(),
                        jColorChooserZones.getColor().getBlue());
                String selectedGroup = "";
                switch (jListZones.getSelectedValue()) {
                    case "Indicators":
                        jPanelColour088.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour088);
                        jPanelColour089.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour089);
                        break;
                    case "F Keys":
                        selectedGroup = "fkeys";
                        jPanelColour002.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour002);
                        jPanelColour003.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour003);
                        jPanelColour004.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour004);
                        jPanelColour005.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour005);
                        jPanelColour006.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour006);
                        jPanelColour007.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour007);
                        jPanelColour008.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour008);
                        jPanelColour009.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour009);
                        jPanelColour010.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour010);
                        jPanelColour011.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour011);
                        jPanelColour012.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour012);
                        jPanelColour013.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour013);
                        break;
                    case "Modifiers":
                        selectedGroup = "modifiers";
                        jPanelColour064.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour064);
                        jPanelColour077.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour077);
                        jPanelColour078.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour078);
                        jPanelColour079.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour079);
                        jPanelColour080.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour080);
                        jPanelColour081.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour081);
                        jPanelColour082.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour082);
                        jPanelColour083.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour083);
                        jPanelColour084.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour084);
                        jPanelColour075.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour075);
                        break;
                    case "Arrows":
                        selectedGroup = "arrows";
                        jPanelColour076.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour076);
                        jPanelColour085.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour085);
                        jPanelColour086.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour086);
                        jPanelColour087.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour087);
                        break;
                    case "WASD":
                        selectedGroup = "wasd";
                        jPanelColour036.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour036);
                        jPanelColour052.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour052);
                        jPanelColour053.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour053);
                        jPanelColour054.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour054);
                        break;
                    case "Functions":
                        selectedGroup = "functions";
                        jPanelColour001.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour001);
                        jPanelColour014.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour014);
                        jPanelColour015.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour015);
                        jPanelColour016.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour016);
                        jPanelColour031.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour031);
                        jPanelColour032.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour032);
                        jPanelColour033.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour033);
                        jPanelColour048.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour048);
                        jPanelColour049.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour049);
                        jPanelColour050.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour050);
                        break;
                    case "Keys":
                        selectedGroup = "keys";
                        jPanelColour017.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour017);
                        jPanelColour018.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour018);
                        jPanelColour019.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour019);
                        jPanelColour020.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour020);
                        jPanelColour021.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour021);
                        jPanelColour022.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour022);
                        jPanelColour023.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour023);
                        jPanelColour024.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour024);
                        jPanelColour025.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour025);
                        jPanelColour026.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour026);
                        jPanelColour027.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour027);
                        jPanelColour028.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour028);
                        jPanelColour029.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour029);
                        jPanelColour030.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour030);
                        jPanelColour034.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour034);
                        jPanelColour035.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour035);
                        jPanelColour036.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour036);
                        jPanelColour037.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour037);
                        jPanelColour038.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour038);
                        jPanelColour039.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour039);
                        jPanelColour040.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour040);
                        jPanelColour041.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour041);
                        jPanelColour042.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour042);
                        jPanelColour043.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour043);
                        jPanelColour044.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour044);
                        jPanelColour045.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour045);
                        jPanelColour046.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour046);
                        jPanelColour047.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour047);
                        jPanelColour051.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour051);
                        jPanelColour052.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour052);
                        jPanelColour053.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour053);
                        jPanelColour054.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour054);
                        jPanelColour055.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour055);
                        jPanelColour056.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour056);
                        jPanelColour057.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour057);
                        jPanelColour058.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour058);
                        jPanelColour059.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour059);
                        jPanelColour060.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour060);
                        jPanelColour061.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour061);
                        jPanelColour062.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour062);
                        jPanelColour063.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour063);
                        jPanelColour065.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour065);
                        jPanelColour066.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour066);
                        jPanelColour067.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour067);
                        jPanelColour068.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour068);
                        jPanelColour069.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour069);
                        jPanelColour070.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour070);
                        jPanelColour071.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour071);
                        jPanelColour072.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour072);
                        jPanelColour073.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour073);
                        jPanelColour074.setBackground(jColorChooserZones.getColor());
                        jLayeredPane1.moveToBack(jPanelColour074);
                        break;
                    default:
                        break;
                }
                if (selectedGroup.equals("")) {
                } else if (selectedGroup.equals("wasd")) {
                    String[] keys = {"w", "a", "s", "d"};
                    gKeyboard.setKeys(keys, hexColour, true);
                } else {
                    gKeyboard.setGroupKeys(selectedGroup, hexColour, true);
                }
            }
        });

        //shutdown hooks to get executed before program shutdown
        //this isn't really necessary as service will do this again once program is shutdown,
        //this piece of code is left from period before service was implemented
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                //stop effects                
                stopGKeyboardEffects(false);
                //load currently set profile from file
                gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                if (gKeyboard.getEffect() != null) {
                    //service will load profiles after program shutdown
                } else {
                    //load colour profile
                    loadColours(convertStandardKeyColourMapToG410ColourMap(gKeyboard.getKeyColourMap()));
                    jSliderBrightness.setValue(getAverageBrightness());
                    disableAlljLayeredPaneEffectsChildren();
                }
            }
        }));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelLighting = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelColour089 = new javax.swing.JPanel();
        jPanelColour088 = new javax.swing.JPanel();
        jPanelColour087 = new javax.swing.JPanel();
        jPanelColour086 = new javax.swing.JPanel();
        jPanelColour085 = new javax.swing.JPanel();
        jPanelColour084 = new javax.swing.JPanel();
        jPanelColour083 = new javax.swing.JPanel();
        jPanelColour082 = new javax.swing.JPanel();
        jPanelColour081 = new javax.swing.JPanel();
        jPanelColour080 = new javax.swing.JPanel();
        jPanelColour079 = new javax.swing.JPanel();
        jPanelColour078 = new javax.swing.JPanel();
        jPanelColour077 = new javax.swing.JPanel();
        jPanelColour076 = new javax.swing.JPanel();
        jPanelColour075 = new javax.swing.JPanel();
        jPanelColour074 = new javax.swing.JPanel();
        jPanelColour073 = new javax.swing.JPanel();
        jPanelColour072 = new javax.swing.JPanel();
        jPanelColour071 = new javax.swing.JPanel();
        jPanelColour070 = new javax.swing.JPanel();
        jPanelColour069 = new javax.swing.JPanel();
        jPanelColour068 = new javax.swing.JPanel();
        jPanelColour067 = new javax.swing.JPanel();
        jPanelColour066 = new javax.swing.JPanel();
        jPanelColour065 = new javax.swing.JPanel();
        jPanelColour064 = new javax.swing.JPanel();
        jPanelColour063 = new javax.swing.JPanel();
        jPanelColour062 = new javax.swing.JPanel();
        jPanelColour061 = new javax.swing.JPanel();
        jPanelColour060 = new javax.swing.JPanel();
        jPanelColour059 = new javax.swing.JPanel();
        jPanelColour058 = new javax.swing.JPanel();
        jPanelColour057 = new javax.swing.JPanel();
        jPanelColour056 = new javax.swing.JPanel();
        jPanelColour055 = new javax.swing.JPanel();
        jPanelColour054 = new javax.swing.JPanel();
        jPanelColour053 = new javax.swing.JPanel();
        jPanelColour052 = new javax.swing.JPanel();
        jPanelColour051 = new javax.swing.JPanel();
        jPanelColour050 = new javax.swing.JPanel();
        jPanelColour049 = new javax.swing.JPanel();
        jPanelColour048 = new javax.swing.JPanel();
        jPanelColour047 = new javax.swing.JPanel();
        jPanelColour046 = new javax.swing.JPanel();
        jPanelColour045 = new javax.swing.JPanel();
        jPanelColour044 = new javax.swing.JPanel();
        jPanelColour043 = new javax.swing.JPanel();
        jPanelColour042 = new javax.swing.JPanel();
        jPanelColour041 = new javax.swing.JPanel();
        jPanelColour040 = new javax.swing.JPanel();
        jPanelColour039 = new javax.swing.JPanel();
        jPanelColour038 = new javax.swing.JPanel();
        jPanelColour037 = new javax.swing.JPanel();
        jPanelColour036 = new javax.swing.JPanel();
        jPanelColour035 = new javax.swing.JPanel();
        jPanelColour034 = new javax.swing.JPanel();
        jPanelColour033 = new javax.swing.JPanel();
        jPanelColour032 = new javax.swing.JPanel();
        jPanelColour031 = new javax.swing.JPanel();
        jPanelColour030 = new javax.swing.JPanel();
        jPanelColour029 = new javax.swing.JPanel();
        jPanelColour028 = new javax.swing.JPanel();
        jPanelColour027 = new javax.swing.JPanel();
        jPanelColour026 = new javax.swing.JPanel();
        jPanelColour025 = new javax.swing.JPanel();
        jPanelColour024 = new javax.swing.JPanel();
        jPanelColour023 = new javax.swing.JPanel();
        jPanelColour022 = new javax.swing.JPanel();
        jPanelColour021 = new javax.swing.JPanel();
        jPanelColour020 = new javax.swing.JPanel();
        jPanelColour019 = new javax.swing.JPanel();
        jPanelColour018 = new javax.swing.JPanel();
        jPanelColour017 = new javax.swing.JPanel();
        jPanelColour016 = new javax.swing.JPanel();
        jPanelColour015 = new javax.swing.JPanel();
        jPanelColour014 = new javax.swing.JPanel();
        jPanelColour013 = new javax.swing.JPanel();
        jPanelColour012 = new javax.swing.JPanel();
        jPanelColour011 = new javax.swing.JPanel();
        jPanelColour010 = new javax.swing.JPanel();
        jPanelColour009 = new javax.swing.JPanel();
        jPanelColour008 = new javax.swing.JPanel();
        jPanelColour007 = new javax.swing.JPanel();
        jPanelColour006 = new javax.swing.JPanel();
        jPanelColour005 = new javax.swing.JPanel();
        jPanelColour004 = new javax.swing.JPanel();
        jPanelColour003 = new javax.swing.JPanel();
        jPanelColour002 = new javax.swing.JPanel();
        jPanelColour001 = new javax.swing.JPanel();
        jTabbedPaneLighting = new javax.swing.JTabbedPane();
        jPanelFreeStyle = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jColorChooserFreeStyle = new javax.swing.JColorChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabelSelectedButton = new javax.swing.JLabel();
        jPanelEffects = new javax.swing.JPanel();
        jComboBoxEffects = new javax.swing.JComboBox<>();
        jLayeredPaneEffects = new javax.swing.JLayeredPane();
        jPanelEffectsColourCycle = new javax.swing.JPanel();
        jLabelColourCycleEffectSpeed = new javax.swing.JLabel();
        jSliderColourCycleEffectSpeed = new javax.swing.JSlider();
        jPanelEffectsColourWave = new javax.swing.JPanel();
        jLabelColourWaveEffectSpeed = new javax.swing.JLabel();
        jSliderColourWaveEffectSpeed = new javax.swing.JSlider();
        jComboBoxColourWaveEffectType = new javax.swing.JComboBox<>();
        jLabelColourWaveEffectType = new javax.swing.JLabel();
        jPanelEffectsBreathing = new javax.swing.JPanel();
        jLabeleBreathingColour = new javax.swing.JLabel();
        jPanelBreathingColour = new javax.swing.JPanel();
        jLabelBreathingEffectSpeed = new javax.swing.JLabel();
        jSliderBreathingEffectSpeed = new javax.swing.JSlider();
        jPanelEffectsStar = new javax.swing.JPanel();
        jLabelStarColour = new javax.swing.JLabel();
        jLabelSkyColour = new javax.swing.JLabel();
        jPanelStarColour = new javax.swing.JPanel();
        jPanelSkyColour = new javax.swing.JPanel();
        jPanelEffectsFixedColour = new javax.swing.JPanel();
        jLabeleFixedColour = new javax.swing.JLabel();
        jPanelFixedColour = new javax.swing.JPanel();
        jPanelZones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListZones = new javax.swing.JList<>();
        jColorChooserZones = new javax.swing.JColorChooser();
        jPanelStartupEffect = new javax.swing.JPanel();
        jComboBoxStartupEffect = new javax.swing.JComboBox<>();
        jButtonLoadProfile = new javax.swing.JButton();
        jButtonSaveProfile = new javax.swing.JButton();
        jButtonSetProfile = new javax.swing.JButton();
        jSliderBrightness = new javax.swing.JSlider();
        jLabelBrightness = new javax.swing.JLabel();
        jButtonResetProfile = new javax.swing.JButton();

        setBorder(null);

        jPanelLighting.setMinimumSize(new java.awt.Dimension(1040, 450));

        jPanel5.setMaximumSize(new java.awt.Dimension(1026, 430));
        jPanel5.setMinimumSize(new java.awt.Dimension(1026, 430));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logi/GSeries/Keyboard/Resources/Images/G410_Atlas_Spectrum_US_QWERTY.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 1014, 400));

        jPanelColour089.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour089.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour089MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour089Layout = new javax.swing.GroupLayout(jPanelColour089);
        jPanelColour089.setLayout(jPanelColour089Layout);
        jPanelColour089Layout.setHorizontalGroup(
            jPanelColour089Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour089Layout.setVerticalGroup(
            jPanelColour089Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour089, new org.netbeans.lib.awtextra.AbsoluteConstraints(877, 15, 20, 20));

        jPanelColour088.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour088.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour088MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour088Layout = new javax.swing.GroupLayout(jPanelColour088);
        jPanelColour088.setLayout(jPanelColour088Layout);
        jPanelColour088Layout.setHorizontalGroup(
            jPanelColour088Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour088Layout.setVerticalGroup(
            jPanelColour088Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour088, new org.netbeans.lib.awtextra.AbsoluteConstraints(793, 15, 20, 20));

        jPanelColour087.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour087.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour087MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour087Layout = new javax.swing.GroupLayout(jPanelColour087);
        jPanelColour087.setLayout(jPanelColour087Layout);
        jPanelColour087Layout.setHorizontalGroup(
            jPanelColour087Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour087Layout.setVerticalGroup(
            jPanelColour087Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour087, new org.netbeans.lib.awtextra.AbsoluteConstraints(875, 280, 20, 25));

        jPanelColour086.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour086.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour086MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour086Layout = new javax.swing.GroupLayout(jPanelColour086);
        jPanelColour086.setLayout(jPanelColour086Layout);
        jPanelColour086Layout.setHorizontalGroup(
            jPanelColour086Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour086Layout.setVerticalGroup(
            jPanelColour086Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour086, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 279, 20, 25));

        jPanelColour085.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour085.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour085MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour085Layout = new javax.swing.GroupLayout(jPanelColour085);
        jPanelColour085.setLayout(jPanelColour085Layout);
        jPanelColour085Layout.setHorizontalGroup(
            jPanelColour085Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour085Layout.setVerticalGroup(
            jPanelColour085Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour085, new org.netbeans.lib.awtextra.AbsoluteConstraints(793, 278, 20, 25));

        jPanelColour084.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour084.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour084MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour084Layout = new javax.swing.GroupLayout(jPanelColour084);
        jPanelColour084.setLayout(jPanelColour084Layout);
        jPanelColour084Layout.setHorizontalGroup(
            jPanelColour084Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanelColour084Layout.setVerticalGroup(
            jPanelColour084Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour084, new org.netbeans.lib.awtextra.AbsoluteConstraints(722, 276, 40, 25));

        jPanelColour083.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour083.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour083MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour083Layout = new javax.swing.GroupLayout(jPanelColour083);
        jPanelColour083.setLayout(jPanelColour083Layout);
        jPanelColour083Layout.setHorizontalGroup(
            jPanelColour083Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour083Layout.setVerticalGroup(
            jPanelColour083Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour083, new org.netbeans.lib.awtextra.AbsoluteConstraints(672, 276, 25, 25));

        jPanelColour082.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour082.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour082MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour082Layout = new javax.swing.GroupLayout(jPanelColour082);
        jPanelColour082.setLayout(jPanelColour082Layout);
        jPanelColour082Layout.setHorizontalGroup(
            jPanelColour082Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour082Layout.setVerticalGroup(
            jPanelColour082Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour082, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 276, 25, 25));

        jPanelColour081.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour081.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour081MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour081Layout = new javax.swing.GroupLayout(jPanelColour081);
        jPanelColour081.setLayout(jPanelColour081Layout);
        jPanelColour081Layout.setHorizontalGroup(
            jPanelColour081Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour081Layout.setVerticalGroup(
            jPanelColour081Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour081, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 276, 25, 25));

        jPanelColour080.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour080.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour080MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour080Layout = new javax.swing.GroupLayout(jPanelColour080);
        jPanelColour080.setLayout(jPanelColour080Layout);
        jPanelColour080Layout.setHorizontalGroup(
            jPanelColour080Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );
        jPanelColour080Layout.setVerticalGroup(
            jPanelColour080Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour080, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 275, 170, 25));

        jPanelColour079.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour079.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour079MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour079Layout = new javax.swing.GroupLayout(jPanelColour079);
        jPanelColour079.setLayout(jPanelColour079Layout);
        jPanelColour079Layout.setHorizontalGroup(
            jPanelColour079Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour079Layout.setVerticalGroup(
            jPanelColour079Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour079, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 276, 25, 25));

        jPanelColour078.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour078.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour078MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour078Layout = new javax.swing.GroupLayout(jPanelColour078);
        jPanelColour078.setLayout(jPanelColour078Layout);
        jPanelColour078Layout.setHorizontalGroup(
            jPanelColour078Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour078Layout.setVerticalGroup(
            jPanelColour078Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour078, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 276, 25, 25));

        jPanelColour077.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour077.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour077MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour077Layout = new javax.swing.GroupLayout(jPanelColour077);
        jPanelColour077.setLayout(jPanelColour077Layout);
        jPanelColour077Layout.setHorizontalGroup(
            jPanelColour077Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanelColour077Layout.setVerticalGroup(
            jPanelColour077Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour077, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 274, 40, 25));

        jPanelColour076.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour076.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour076MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour076Layout = new javax.swing.GroupLayout(jPanelColour076);
        jPanelColour076.setLayout(jPanelColour076Layout);
        jPanelColour076Layout.setHorizontalGroup(
            jPanelColour076Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour076Layout.setVerticalGroup(
            jPanelColour076Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour076, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 234, 20, 25));

        jPanelColour075.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour075.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour075MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour075Layout = new javax.swing.GroupLayout(jPanelColour075);
        jPanelColour075.setLayout(jPanelColour075Layout);
        jPanelColour075Layout.setHorizontalGroup(
            jPanelColour075Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanelColour075Layout.setVerticalGroup(
            jPanelColour075Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour075, new org.netbeans.lib.awtextra.AbsoluteConstraints(678, 229, 80, 25));

        jPanelColour074.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour074.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour074MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour074Layout = new javax.swing.GroupLayout(jPanelColour074);
        jPanelColour074.setLayout(jPanelColour074Layout);
        jPanelColour074Layout.setHorizontalGroup(
            jPanelColour074Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour074Layout.setVerticalGroup(
            jPanelColour074Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour074, new org.netbeans.lib.awtextra.AbsoluteConstraints(633, 230, 20, 28));

        jPanelColour073.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour073.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour073MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour073Layout = new javax.swing.GroupLayout(jPanelColour073);
        jPanelColour073.setLayout(jPanelColour073Layout);
        jPanelColour073Layout.setHorizontalGroup(
            jPanelColour073Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour073Layout.setVerticalGroup(
            jPanelColour073Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour073, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 20, 28));

        jPanelColour072.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour072.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour072MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour072Layout = new javax.swing.GroupLayout(jPanelColour072);
        jPanelColour072.setLayout(jPanelColour072Layout);
        jPanelColour072Layout.setHorizontalGroup(
            jPanelColour072Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour072Layout.setVerticalGroup(
            jPanelColour072Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour072, new org.netbeans.lib.awtextra.AbsoluteConstraints(549, 230, 20, 28));

        jPanelColour071.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour071.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour071MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour071Layout = new javax.swing.GroupLayout(jPanelColour071);
        jPanelColour071.setLayout(jPanelColour071Layout);
        jPanelColour071Layout.setHorizontalGroup(
            jPanelColour071Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour071Layout.setVerticalGroup(
            jPanelColour071Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour071, new org.netbeans.lib.awtextra.AbsoluteConstraints(508, 230, 20, 25));

        jPanelColour070.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour070.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour070MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour070Layout = new javax.swing.GroupLayout(jPanelColour070);
        jPanelColour070.setLayout(jPanelColour070Layout);
        jPanelColour070Layout.setHorizontalGroup(
            jPanelColour070Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour070Layout.setVerticalGroup(
            jPanelColour070Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour070, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 230, 20, 25));

        jPanelColour069.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour069.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour069MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour069Layout = new javax.swing.GroupLayout(jPanelColour069);
        jPanelColour069.setLayout(jPanelColour069Layout);
        jPanelColour069Layout.setHorizontalGroup(
            jPanelColour069Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour069Layout.setVerticalGroup(
            jPanelColour069Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour069, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 230, 20, 25));

        jPanelColour068.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour068.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour068MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour068Layout = new javax.swing.GroupLayout(jPanelColour068);
        jPanelColour068.setLayout(jPanelColour068Layout);
        jPanelColour068Layout.setHorizontalGroup(
            jPanelColour068Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour068Layout.setVerticalGroup(
            jPanelColour068Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour068, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 230, 20, 25));

        jPanelColour067.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour067.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour067MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour067Layout = new javax.swing.GroupLayout(jPanelColour067);
        jPanelColour067.setLayout(jPanelColour067Layout);
        jPanelColour067Layout.setHorizontalGroup(
            jPanelColour067Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour067Layout.setVerticalGroup(
            jPanelColour067Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour067, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 230, 20, 25));

        jPanelColour066.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour066.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour066MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour066Layout = new javax.swing.GroupLayout(jPanelColour066);
        jPanelColour066.setLayout(jPanelColour066Layout);
        jPanelColour066Layout.setHorizontalGroup(
            jPanelColour066Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour066Layout.setVerticalGroup(
            jPanelColour066Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour066, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 230, 20, 25));

        jPanelColour065.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour065.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour065MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour065Layout = new javax.swing.GroupLayout(jPanelColour065);
        jPanelColour065.setLayout(jPanelColour065Layout);
        jPanelColour065Layout.setHorizontalGroup(
            jPanelColour065Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour065Layout.setVerticalGroup(
            jPanelColour065Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour065, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 230, 20, 25));

        jPanelColour064.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour064.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour064MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour064Layout = new javax.swing.GroupLayout(jPanelColour064);
        jPanelColour064.setLayout(jPanelColour064Layout);
        jPanelColour064Layout.setHorizontalGroup(
            jPanelColour064Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanelColour064Layout.setVerticalGroup(
            jPanelColour064Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour064, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 230, 50, 25));

        jPanelColour063.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour063.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour063MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour063Layout = new javax.swing.GroupLayout(jPanelColour063);
        jPanelColour063.setLayout(jPanelColour063Layout);
        jPanelColour063Layout.setHorizontalGroup(
            jPanelColour063Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanelColour063Layout.setVerticalGroup(
            jPanelColour063Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour063, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 187, 60, 25));

        jPanelColour062.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour062.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour062MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour062Layout = new javax.swing.GroupLayout(jPanelColour062);
        jPanelColour062.setLayout(jPanelColour062Layout);
        jPanelColour062Layout.setHorizontalGroup(
            jPanelColour062Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour062Layout.setVerticalGroup(
            jPanelColour062Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour062, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 187, 20, 28));

        jPanelColour061.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour061.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour061MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour061Layout = new javax.swing.GroupLayout(jPanelColour061);
        jPanelColour061.setLayout(jPanelColour061Layout);
        jPanelColour061Layout.setHorizontalGroup(
            jPanelColour061Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour061Layout.setVerticalGroup(
            jPanelColour061Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour061, new org.netbeans.lib.awtextra.AbsoluteConstraints(611, 187, 20, 28));

        jPanelColour060.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour060.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour060MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour060Layout = new javax.swing.GroupLayout(jPanelColour060);
        jPanelColour060.setLayout(jPanelColour060Layout);
        jPanelColour060Layout.setHorizontalGroup(
            jPanelColour060Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour060Layout.setVerticalGroup(
            jPanelColour060Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour060, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 188, 20, 25));

        jPanelColour059.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour059.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour059MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour059Layout = new javax.swing.GroupLayout(jPanelColour059);
        jPanelColour059.setLayout(jPanelColour059Layout);
        jPanelColour059Layout.setHorizontalGroup(
            jPanelColour059Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour059Layout.setVerticalGroup(
            jPanelColour059Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour059, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 188, 20, 25));

        jPanelColour058.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour058.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour058MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour058Layout = new javax.swing.GroupLayout(jPanelColour058);
        jPanelColour058.setLayout(jPanelColour058Layout);
        jPanelColour058Layout.setHorizontalGroup(
            jPanelColour058Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour058Layout.setVerticalGroup(
            jPanelColour058Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour058, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 188, 20, 25));

        jPanelColour057.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour057.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour057MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour057Layout = new javax.swing.GroupLayout(jPanelColour057);
        jPanelColour057.setLayout(jPanelColour057Layout);
        jPanelColour057Layout.setHorizontalGroup(
            jPanelColour057Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour057Layout.setVerticalGroup(
            jPanelColour057Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour057, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 188, 20, 25));

        jPanelColour056.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour056.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour056MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour056Layout = new javax.swing.GroupLayout(jPanelColour056);
        jPanelColour056.setLayout(jPanelColour056Layout);
        jPanelColour056Layout.setHorizontalGroup(
            jPanelColour056Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour056Layout.setVerticalGroup(
            jPanelColour056Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour056, new org.netbeans.lib.awtextra.AbsoluteConstraints(398, 188, 20, 25));

        jPanelColour055.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour055.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour055MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour055Layout = new javax.swing.GroupLayout(jPanelColour055);
        jPanelColour055.setLayout(jPanelColour055Layout);
        jPanelColour055Layout.setHorizontalGroup(
            jPanelColour055Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour055Layout.setVerticalGroup(
            jPanelColour055Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour055, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 188, 20, 25));

        jPanelColour054.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour054.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour054MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour054Layout = new javax.swing.GroupLayout(jPanelColour054);
        jPanelColour054.setLayout(jPanelColour054Layout);
        jPanelColour054Layout.setHorizontalGroup(
            jPanelColour054Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour054Layout.setVerticalGroup(
            jPanelColour054Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour054, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 188, 20, 25));

        jPanelColour053.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour053.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour053MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour053Layout = new javax.swing.GroupLayout(jPanelColour053);
        jPanelColour053.setLayout(jPanelColour053Layout);
        jPanelColour053Layout.setHorizontalGroup(
            jPanelColour053Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour053Layout.setVerticalGroup(
            jPanelColour053Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour053, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 188, 20, 25));

        jPanelColour052.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour052.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour052MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour052Layout = new javax.swing.GroupLayout(jPanelColour052);
        jPanelColour052.setLayout(jPanelColour052Layout);
        jPanelColour052Layout.setHorizontalGroup(
            jPanelColour052Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour052Layout.setVerticalGroup(
            jPanelColour052Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour052, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 188, 20, 25));

        jPanelColour051.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour051.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour051MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour051Layout = new javax.swing.GroupLayout(jPanelColour051);
        jPanelColour051.setLayout(jPanelColour051Layout);
        jPanelColour051Layout.setHorizontalGroup(
            jPanelColour051Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanelColour051Layout.setVerticalGroup(
            jPanelColour051Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour051, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 186, 40, 25));

        jPanelColour050.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour050.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour050MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour050Layout = new javax.swing.GroupLayout(jPanelColour050);
        jPanelColour050.setLayout(jPanelColour050Layout);
        jPanelColour050Layout.setHorizontalGroup(
            jPanelColour050Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour050Layout.setVerticalGroup(
            jPanelColour050Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour050, new org.netbeans.lib.awtextra.AbsoluteConstraints(873, 144, 25, 30));

        jPanelColour049.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour049.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour049MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour049Layout = new javax.swing.GroupLayout(jPanelColour049);
        jPanelColour049.setLayout(jPanelColour049Layout);
        jPanelColour049Layout.setHorizontalGroup(
            jPanelColour049Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour049Layout.setVerticalGroup(
            jPanelColour049Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour049, new org.netbeans.lib.awtextra.AbsoluteConstraints(832, 144, 25, 30));

        jPanelColour048.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour048.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour048MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour048Layout = new javax.swing.GroupLayout(jPanelColour048);
        jPanelColour048.setLayout(jPanelColour048Layout);
        jPanelColour048Layout.setHorizontalGroup(
            jPanelColour048Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour048Layout.setVerticalGroup(
            jPanelColour048Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour048, new org.netbeans.lib.awtextra.AbsoluteConstraints(792, 144, 25, 30));

        jPanelColour047.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour047.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour047MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour047Layout = new javax.swing.GroupLayout(jPanelColour047);
        jPanelColour047.setLayout(jPanelColour047Layout);
        jPanelColour047Layout.setHorizontalGroup(
            jPanelColour047Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanelColour047Layout.setVerticalGroup(
            jPanelColour047Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour047, new org.netbeans.lib.awtextra.AbsoluteConstraints(726, 142, 40, 30));

        jPanelColour046.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour046.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour046MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour046Layout = new javax.swing.GroupLayout(jPanelColour046);
        jPanelColour046.setLayout(jPanelColour046Layout);
        jPanelColour046Layout.setHorizontalGroup(
            jPanelColour046Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour046Layout.setVerticalGroup(
            jPanelColour046Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour046, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 142, 20, 30));

        jPanelColour045.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour045.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour045MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour045Layout = new javax.swing.GroupLayout(jPanelColour045);
        jPanelColour045.setLayout(jPanelColour045Layout);
        jPanelColour045Layout.setHorizontalGroup(
            jPanelColour045Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour045Layout.setVerticalGroup(
            jPanelColour045Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour045, new org.netbeans.lib.awtextra.AbsoluteConstraints(644, 142, 20, 30));

        jPanelColour044.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour044.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour044MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour044Layout = new javax.swing.GroupLayout(jPanelColour044);
        jPanelColour044.setLayout(jPanelColour044Layout);
        jPanelColour044Layout.setHorizontalGroup(
            jPanelColour044Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour044Layout.setVerticalGroup(
            jPanelColour044Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour044, new org.netbeans.lib.awtextra.AbsoluteConstraints(602, 144, 20, 25));

        jPanelColour043.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour043.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour043MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour043Layout = new javax.swing.GroupLayout(jPanelColour043);
        jPanelColour043.setLayout(jPanelColour043Layout);
        jPanelColour043Layout.setHorizontalGroup(
            jPanelColour043Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour043Layout.setVerticalGroup(
            jPanelColour043Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour043, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 144, 20, 25));

        jPanelColour042.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour042.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour042MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour042Layout = new javax.swing.GroupLayout(jPanelColour042);
        jPanelColour042.setLayout(jPanelColour042Layout);
        jPanelColour042Layout.setHorizontalGroup(
            jPanelColour042Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour042Layout.setVerticalGroup(
            jPanelColour042Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour042, new org.netbeans.lib.awtextra.AbsoluteConstraints(517, 144, 20, 25));

        jPanelColour041.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour041.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour041MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour041Layout = new javax.swing.GroupLayout(jPanelColour041);
        jPanelColour041.setLayout(jPanelColour041Layout);
        jPanelColour041Layout.setHorizontalGroup(
            jPanelColour041Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour041Layout.setVerticalGroup(
            jPanelColour041Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour041, new org.netbeans.lib.awtextra.AbsoluteConstraints(474, 144, 20, 25));

        jPanelColour040.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour040.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour040MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour040Layout = new javax.swing.GroupLayout(jPanelColour040);
        jPanelColour040.setLayout(jPanelColour040Layout);
        jPanelColour040Layout.setHorizontalGroup(
            jPanelColour040Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour040Layout.setVerticalGroup(
            jPanelColour040Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour040, new org.netbeans.lib.awtextra.AbsoluteConstraints(432, 144, 20, 25));

        jPanelColour039.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour039.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour039MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour039Layout = new javax.swing.GroupLayout(jPanelColour039);
        jPanelColour039.setLayout(jPanelColour039Layout);
        jPanelColour039Layout.setHorizontalGroup(
            jPanelColour039Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour039Layout.setVerticalGroup(
            jPanelColour039Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour039, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 144, 20, 25));

        jPanelColour038.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour038.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour038MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour038Layout = new javax.swing.GroupLayout(jPanelColour038);
        jPanelColour038.setLayout(jPanelColour038Layout);
        jPanelColour038Layout.setHorizontalGroup(
            jPanelColour038Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour038Layout.setVerticalGroup(
            jPanelColour038Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour038, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 144, 20, 25));

        jPanelColour037.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour037.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour037MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour037Layout = new javax.swing.GroupLayout(jPanelColour037);
        jPanelColour037.setLayout(jPanelColour037Layout);
        jPanelColour037Layout.setHorizontalGroup(
            jPanelColour037Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour037Layout.setVerticalGroup(
            jPanelColour037Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour037, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 144, 20, 25));

        jPanelColour036.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour036.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour036MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour036Layout = new javax.swing.GroupLayout(jPanelColour036);
        jPanelColour036.setLayout(jPanelColour036Layout);
        jPanelColour036Layout.setHorizontalGroup(
            jPanelColour036Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour036Layout.setVerticalGroup(
            jPanelColour036Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour036, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 144, 20, 25));

        jPanelColour035.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour035.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour035MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour035Layout = new javax.swing.GroupLayout(jPanelColour035);
        jPanelColour035.setLayout(jPanelColour035Layout);
        jPanelColour035Layout.setHorizontalGroup(
            jPanelColour035Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour035Layout.setVerticalGroup(
            jPanelColour035Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour035, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 143, 20, 25));

        jPanelColour034.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour034.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour034MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour034Layout = new javax.swing.GroupLayout(jPanelColour034);
        jPanelColour034.setLayout(jPanelColour034Layout);
        jPanelColour034Layout.setHorizontalGroup(
            jPanelColour034Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        jPanelColour034Layout.setVerticalGroup(
            jPanelColour034Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour034, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 142, 35, 30));

        jPanelColour033.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour033.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour033MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour033Layout = new javax.swing.GroupLayout(jPanelColour033);
        jPanelColour033.setLayout(jPanelColour033Layout);
        jPanelColour033Layout.setHorizontalGroup(
            jPanelColour033Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour033Layout.setVerticalGroup(
            jPanelColour033Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour033, new org.netbeans.lib.awtextra.AbsoluteConstraints(872, 100, 25, 30));

        jPanelColour032.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour032.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour032MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour032Layout = new javax.swing.GroupLayout(jPanelColour032);
        jPanelColour032.setLayout(jPanelColour032Layout);
        jPanelColour032Layout.setHorizontalGroup(
            jPanelColour032Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour032Layout.setVerticalGroup(
            jPanelColour032Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour032, new org.netbeans.lib.awtextra.AbsoluteConstraints(831, 100, 25, 30));

        jPanelColour031.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour031.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour031MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour031Layout = new javax.swing.GroupLayout(jPanelColour031);
        jPanelColour031.setLayout(jPanelColour031Layout);
        jPanelColour031Layout.setHorizontalGroup(
            jPanelColour031Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanelColour031Layout.setVerticalGroup(
            jPanelColour031Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour031, new org.netbeans.lib.awtextra.AbsoluteConstraints(792, 100, 25, 30));

        jPanelColour030.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour030.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour030MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour030Layout = new javax.swing.GroupLayout(jPanelColour030);
        jPanelColour030.setLayout(jPanelColour030Layout);
        jPanelColour030Layout.setHorizontalGroup(
            jPanelColour030Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanelColour030Layout.setVerticalGroup(
            jPanelColour030Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour030, new org.netbeans.lib.awtextra.AbsoluteConstraints(712, 98, 50, 30));

        jPanelColour029.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour029.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour029MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour029Layout = new javax.swing.GroupLayout(jPanelColour029);
        jPanelColour029.setLayout(jPanelColour029Layout);
        jPanelColour029Layout.setHorizontalGroup(
            jPanelColour029Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour029Layout.setVerticalGroup(
            jPanelColour029Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour029, new org.netbeans.lib.awtextra.AbsoluteConstraints(676, 99, 20, 30));

        jPanelColour028.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour028.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour028MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour028Layout = new javax.swing.GroupLayout(jPanelColour028);
        jPanelColour028.setLayout(jPanelColour028Layout);
        jPanelColour028Layout.setHorizontalGroup(
            jPanelColour028Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour028Layout.setVerticalGroup(
            jPanelColour028Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour028, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 99, 20, 30));

        jPanelColour027.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour027.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour027MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour027Layout = new javax.swing.GroupLayout(jPanelColour027);
        jPanelColour027.setLayout(jPanelColour027Layout);
        jPanelColour027Layout.setHorizontalGroup(
            jPanelColour027Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour027Layout.setVerticalGroup(
            jPanelColour027Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour027, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 99, 20, 30));

        jPanelColour026.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour026.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour026MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour026Layout = new javax.swing.GroupLayout(jPanelColour026);
        jPanelColour026.setLayout(jPanelColour026Layout);
        jPanelColour026Layout.setHorizontalGroup(
            jPanelColour026Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour026Layout.setVerticalGroup(
            jPanelColour026Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour026, new org.netbeans.lib.awtextra.AbsoluteConstraints(548, 99, 20, 30));

        jPanelColour025.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour025.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour025MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour025Layout = new javax.swing.GroupLayout(jPanelColour025);
        jPanelColour025.setLayout(jPanelColour025Layout);
        jPanelColour025Layout.setHorizontalGroup(
            jPanelColour025Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour025Layout.setVerticalGroup(
            jPanelColour025Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour025, new org.netbeans.lib.awtextra.AbsoluteConstraints(506, 99, 20, 30));

        jPanelColour024.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour024.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour024MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour024Layout = new javax.swing.GroupLayout(jPanelColour024);
        jPanelColour024.setLayout(jPanelColour024Layout);
        jPanelColour024Layout.setHorizontalGroup(
            jPanelColour024Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour024Layout.setVerticalGroup(
            jPanelColour024Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour024, new org.netbeans.lib.awtextra.AbsoluteConstraints(462, 99, 20, 30));

        jPanelColour023.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour023.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour023MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour023Layout = new javax.swing.GroupLayout(jPanelColour023);
        jPanelColour023.setLayout(jPanelColour023Layout);
        jPanelColour023Layout.setHorizontalGroup(
            jPanelColour023Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour023Layout.setVerticalGroup(
            jPanelColour023Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour023, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 99, 20, 30));

        jPanelColour022.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour022.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour022MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour022Layout = new javax.swing.GroupLayout(jPanelColour022);
        jPanelColour022.setLayout(jPanelColour022Layout);
        jPanelColour022Layout.setHorizontalGroup(
            jPanelColour022Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour022Layout.setVerticalGroup(
            jPanelColour022Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour022, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 99, 20, 30));

        jPanelColour021.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour021.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour021MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour021Layout = new javax.swing.GroupLayout(jPanelColour021);
        jPanelColour021.setLayout(jPanelColour021Layout);
        jPanelColour021Layout.setHorizontalGroup(
            jPanelColour021Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour021Layout.setVerticalGroup(
            jPanelColour021Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour021, new org.netbeans.lib.awtextra.AbsoluteConstraints(334, 99, 20, 30));

        jPanelColour020.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour020.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour020MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour020Layout = new javax.swing.GroupLayout(jPanelColour020);
        jPanelColour020.setLayout(jPanelColour020Layout);
        jPanelColour020Layout.setHorizontalGroup(
            jPanelColour020Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour020Layout.setVerticalGroup(
            jPanelColour020Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour020, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 99, 20, 30));

        jPanelColour019.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour019.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour019MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour019Layout = new javax.swing.GroupLayout(jPanelColour019);
        jPanelColour019.setLayout(jPanelColour019Layout);
        jPanelColour019Layout.setHorizontalGroup(
            jPanelColour019Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour019Layout.setVerticalGroup(
            jPanelColour019Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour019, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 99, 20, 30));

        jPanelColour018.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour018.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour018MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour018Layout = new javax.swing.GroupLayout(jPanelColour018);
        jPanelColour018.setLayout(jPanelColour018Layout);
        jPanelColour018Layout.setHorizontalGroup(
            jPanelColour018Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour018Layout.setVerticalGroup(
            jPanelColour018Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour018, new org.netbeans.lib.awtextra.AbsoluteConstraints(206, 99, 20, 30));

        jPanelColour017.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour017.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour017MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour017Layout = new javax.swing.GroupLayout(jPanelColour017);
        jPanelColour017.setLayout(jPanelColour017Layout);
        jPanelColour017Layout.setHorizontalGroup(
            jPanelColour017Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour017Layout.setVerticalGroup(
            jPanelColour017Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour017, new org.netbeans.lib.awtextra.AbsoluteConstraints(164, 98, 20, 30));

        jPanelColour016.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour016.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour016MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour016Layout = new javax.swing.GroupLayout(jPanelColour016);
        jPanelColour016.setLayout(jPanelColour016Layout);
        jPanelColour016Layout.setHorizontalGroup(
            jPanelColour016Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour016Layout.setVerticalGroup(
            jPanelColour016Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour016, new org.netbeans.lib.awtextra.AbsoluteConstraints(875, 56, 20, 25));

        jPanelColour015.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour015.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour015MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour015Layout = new javax.swing.GroupLayout(jPanelColour015);
        jPanelColour015.setLayout(jPanelColour015Layout);
        jPanelColour015Layout.setHorizontalGroup(
            jPanelColour015Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour015Layout.setVerticalGroup(
            jPanelColour015Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour015, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 56, 20, 25));

        jPanelColour014.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour014.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour014MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour014Layout = new javax.swing.GroupLayout(jPanelColour014);
        jPanelColour014.setLayout(jPanelColour014Layout);
        jPanelColour014Layout.setHorizontalGroup(
            jPanelColour014Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour014Layout.setVerticalGroup(
            jPanelColour014Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour014, new org.netbeans.lib.awtextra.AbsoluteConstraints(794, 56, 20, 25));

        jPanelColour013.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour013.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour013MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour013Layout = new javax.swing.GroupLayout(jPanelColour013);
        jPanelColour013.setLayout(jPanelColour013Layout);
        jPanelColour013Layout.setHorizontalGroup(
            jPanelColour013Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour013Layout.setVerticalGroup(
            jPanelColour013Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour013, new org.netbeans.lib.awtextra.AbsoluteConstraints(741, 56, 20, 25));

        jPanelColour012.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour012.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour012MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour012Layout = new javax.swing.GroupLayout(jPanelColour012);
        jPanelColour012.setLayout(jPanelColour012Layout);
        jPanelColour012Layout.setHorizontalGroup(
            jPanelColour012Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour012Layout.setVerticalGroup(
            jPanelColour012Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour012, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 56, 20, 25));

        jPanelColour011.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour011.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour011MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour011Layout = new javax.swing.GroupLayout(jPanelColour011);
        jPanelColour011.setLayout(jPanelColour011Layout);
        jPanelColour011Layout.setHorizontalGroup(
            jPanelColour011Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour011Layout.setVerticalGroup(
            jPanelColour011Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour011, new org.netbeans.lib.awtextra.AbsoluteConstraints(659, 56, 20, 25));

        jPanelColour010.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour010.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour010MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour010Layout = new javax.swing.GroupLayout(jPanelColour010);
        jPanelColour010.setLayout(jPanelColour010Layout);
        jPanelColour010Layout.setHorizontalGroup(
            jPanelColour010Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour010Layout.setVerticalGroup(
            jPanelColour010Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour010, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 56, 20, 25));

        jPanelColour009.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour009.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour009MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour009Layout = new javax.swing.GroupLayout(jPanelColour009);
        jPanelColour009.setLayout(jPanelColour009Layout);
        jPanelColour009Layout.setHorizontalGroup(
            jPanelColour009Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour009Layout.setVerticalGroup(
            jPanelColour009Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour009, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 56, 20, 25));

        jPanelColour008.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour008.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour008MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour008Layout = new javax.swing.GroupLayout(jPanelColour008);
        jPanelColour008.setLayout(jPanelColour008Layout);
        jPanelColour008Layout.setHorizontalGroup(
            jPanelColour008Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour008Layout.setVerticalGroup(
            jPanelColour008Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour008, new org.netbeans.lib.awtextra.AbsoluteConstraints(508, 56, 20, 25));

        jPanelColour007.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour007.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour007MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour007Layout = new javax.swing.GroupLayout(jPanelColour007);
        jPanelColour007.setLayout(jPanelColour007Layout);
        jPanelColour007Layout.setHorizontalGroup(
            jPanelColour007Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour007Layout.setVerticalGroup(
            jPanelColour007Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour007, new org.netbeans.lib.awtextra.AbsoluteConstraints(468, 56, 20, 25));

        jPanelColour006.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour006.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour006MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour006Layout = new javax.swing.GroupLayout(jPanelColour006);
        jPanelColour006.setLayout(jPanelColour006Layout);
        jPanelColour006Layout.setHorizontalGroup(
            jPanelColour006Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour006Layout.setVerticalGroup(
            jPanelColour006Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour006, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 56, 20, 25));

        jPanelColour005.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour005.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour005MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour005Layout = new javax.swing.GroupLayout(jPanelColour005);
        jPanelColour005.setLayout(jPanelColour005Layout);
        jPanelColour005Layout.setHorizontalGroup(
            jPanelColour005Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour005Layout.setVerticalGroup(
            jPanelColour005Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour005, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 56, 20, 25));

        jPanelColour004.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour004.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour004MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour004Layout = new javax.swing.GroupLayout(jPanelColour004);
        jPanelColour004.setLayout(jPanelColour004Layout);
        jPanelColour004Layout.setHorizontalGroup(
            jPanelColour004Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour004Layout.setVerticalGroup(
            jPanelColour004Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour004, new org.netbeans.lib.awtextra.AbsoluteConstraints(314, 56, 20, 25));

        jPanelColour003.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour003.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour003MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour003Layout = new javax.swing.GroupLayout(jPanelColour003);
        jPanelColour003.setLayout(jPanelColour003Layout);
        jPanelColour003Layout.setHorizontalGroup(
            jPanelColour003Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour003Layout.setVerticalGroup(
            jPanelColour003Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour003, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 56, 20, 25));

        jPanelColour002.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour002.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour002MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour002Layout = new javax.swing.GroupLayout(jPanelColour002);
        jPanelColour002.setLayout(jPanelColour002Layout);
        jPanelColour002Layout.setHorizontalGroup(
            jPanelColour002Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour002Layout.setVerticalGroup(
            jPanelColour002Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour002, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 56, 20, 25));

        jPanelColour001.setBackground(new java.awt.Color(255, 255, 255));
        jPanelColour001.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelColour001MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelColour001Layout = new javax.swing.GroupLayout(jPanelColour001);
        jPanelColour001.setLayout(jPanelColour001Layout);
        jPanelColour001Layout.setHorizontalGroup(
            jPanelColour001Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelColour001Layout.setVerticalGroup(
            jPanelColour001Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(jPanelColour001, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 56, 20, 25));

        jLayeredPane1.setLayer(jPanel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanelFreeStyle.setLayout(new java.awt.GridBagLayout());

        jPanel6.setMaximumSize(new java.awt.Dimension(32767, 435));
        jPanel6.setMinimumSize(new java.awt.Dimension(1032, 435));

        jPanel8.setPreferredSize(new java.awt.Dimension(1030, 247));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jColorChooserFreeStyle, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 101, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jColorChooserFreeStyle, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jLabel3.setText("Selected Button:");

        jLabelSelectedButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(27, 27, 27)
                        .addComponent(jLabelSelectedButton)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSelectedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addGap(154, 154, 154))
        );

        jPanelFreeStyle.add(jPanel6, new java.awt.GridBagConstraints());

        jTabbedPaneLighting.addTab("Free Style", jPanelFreeStyle);

        jComboBoxEffects.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Effect", "Fixed Colour", "Breathing", "Colour Wave", "Colour Cycle", "Stars", "Lightning", "Key Press" }));
        jComboBoxEffects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEffectsActionPerformed(evt);
            }
        });

        jLayeredPaneEffects.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelColourCycleEffectSpeed.setText("Speed");

        jSliderColourCycleEffectSpeed.setMajorTickSpacing(11);
        jSliderColourCycleEffectSpeed.setMaximum(10);
        jSliderColourCycleEffectSpeed.setMinorTickSpacing(11);
        jSliderColourCycleEffectSpeed.setToolTipText("");
        jSliderColourCycleEffectSpeed.setValue(5);
        jSliderColourCycleEffectSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderColourCycleEffectSpeedStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelEffectsColourCycleLayout = new javax.swing.GroupLayout(jPanelEffectsColourCycle);
        jPanelEffectsColourCycle.setLayout(jPanelEffectsColourCycleLayout);
        jPanelEffectsColourCycleLayout.setHorizontalGroup(
            jPanelEffectsColourCycleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsColourCycleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelColourCycleEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelEffectsColourCycleLayout.createSequentialGroup()
                .addComponent(jSliderColourCycleEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelEffectsColourCycleLayout.setVerticalGroup(
            jPanelEffectsColourCycleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsColourCycleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelColourCycleEffectSpeed)
                .addGap(18, 18, 18)
                .addComponent(jSliderColourCycleEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPaneEffects.add(jPanelEffectsColourCycle, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, -1));

        jLabelColourWaveEffectSpeed.setText("Speed");

        jSliderColourWaveEffectSpeed.setMajorTickSpacing(11);
        jSliderColourWaveEffectSpeed.setMaximum(10);
        jSliderColourWaveEffectSpeed.setMinorTickSpacing(11);
        jSliderColourWaveEffectSpeed.setToolTipText("");
        jSliderColourWaveEffectSpeed.setValue(5);
        jSliderColourWaveEffectSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderColourWaveEffectSpeedStateChanged(evt);
            }
        });

        jComboBoxColourWaveEffectType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Horizontal", "Vertical", "Centre Out" }));
        jComboBoxColourWaveEffectType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxColourWaveEffectTypeActionPerformed(evt);
            }
        });

        jLabelColourWaveEffectType.setText("Type");

        javax.swing.GroupLayout jPanelEffectsColourWaveLayout = new javax.swing.GroupLayout(jPanelEffectsColourWave);
        jPanelEffectsColourWave.setLayout(jPanelEffectsColourWaveLayout);
        jPanelEffectsColourWaveLayout.setHorizontalGroup(
            jPanelEffectsColourWaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsColourWaveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEffectsColourWaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEffectsColourWaveLayout.createSequentialGroup()
                        .addComponent(jComboBoxColourWaveEffectType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanelEffectsColourWaveLayout.createSequentialGroup()
                        .addGroup(jPanelEffectsColourWaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelColourWaveEffectType)
                            .addComponent(jLabelColourWaveEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelEffectsColourWaveLayout.createSequentialGroup()
                        .addComponent(jSliderColourWaveEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelEffectsColourWaveLayout.setVerticalGroup(
            jPanelEffectsColourWaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEffectsColourWaveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelColourWaveEffectType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxColourWaveEffectType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelColourWaveEffectSpeed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderColourWaveEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLayeredPaneEffects.add(jPanelEffectsColourWave, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 150, 130));

        jLabeleBreathingColour.setText("Colour");

        jPanelBreathingColour.setBackground(new java.awt.Color(255, 0, 255));
        jPanelBreathingColour.setMaximumSize(new java.awt.Dimension(20, 20));
        jPanelBreathingColour.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanelBreathingColour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelBreathingColourMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelBreathingColourLayout = new javax.swing.GroupLayout(jPanelBreathingColour);
        jPanelBreathingColour.setLayout(jPanelBreathingColourLayout);
        jPanelBreathingColourLayout.setHorizontalGroup(
            jPanelBreathingColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelBreathingColourLayout.setVerticalGroup(
            jPanelBreathingColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabelBreathingEffectSpeed.setText("Speed");

        jSliderBreathingEffectSpeed.setMajorTickSpacing(11);
        jSliderBreathingEffectSpeed.setMaximum(10);
        jSliderBreathingEffectSpeed.setMinorTickSpacing(11);
        jSliderBreathingEffectSpeed.setToolTipText("");
        jSliderBreathingEffectSpeed.setValue(5);
        jSliderBreathingEffectSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderBreathingEffectSpeedStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelEffectsBreathingLayout = new javax.swing.GroupLayout(jPanelEffectsBreathing);
        jPanelEffectsBreathing.setLayout(jPanelEffectsBreathingLayout);
        jPanelEffectsBreathingLayout.setHorizontalGroup(
            jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsBreathingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabeleBreathingColour)
                .addGap(18, 18, 18)
                .addComponent(jPanelBreathingColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
            .addGroup(jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelEffectsBreathingLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelBreathingEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(155, Short.MAX_VALUE)))
            .addGroup(jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelEffectsBreathingLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSliderBreathingEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanelEffectsBreathingLayout.setVerticalGroup(
            jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsBreathingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelBreathingColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabeleBreathingColour))
                .addContainerGap(64, Short.MAX_VALUE))
            .addGroup(jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelEffectsBreathingLayout.createSequentialGroup()
                    .addGap(44, 44, 44)
                    .addComponent(jLabelBreathingEffectSpeed)
                    .addContainerGap(31, Short.MAX_VALUE)))
            .addGroup(jPanelEffectsBreathingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEffectsBreathingLayout.createSequentialGroup()
                    .addContainerGap(68, Short.MAX_VALUE)
                    .addComponent(jSliderBreathingEffectSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jLayeredPaneEffects.add(jPanelEffectsBreathing, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 0, 220, 90));

        jLabelStarColour.setText("Star Colour");

        jLabelSkyColour.setText("Sky Colour");

        jPanelStarColour.setBackground(new java.awt.Color(255, 255, 0));
        jPanelStarColour.setMaximumSize(new java.awt.Dimension(20, 20));
        jPanelStarColour.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanelStarColour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelStarColourMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelStarColourLayout = new javax.swing.GroupLayout(jPanelStarColour);
        jPanelStarColour.setLayout(jPanelStarColourLayout);
        jPanelStarColourLayout.setHorizontalGroup(
            jPanelStarColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelStarColourLayout.setVerticalGroup(
            jPanelStarColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanelSkyColour.setBackground(new java.awt.Color(0, 0, 0));
        jPanelSkyColour.setMaximumSize(new java.awt.Dimension(20, 20));
        jPanelSkyColour.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanelSkyColour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelSkyColourMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelSkyColourLayout = new javax.swing.GroupLayout(jPanelSkyColour);
        jPanelSkyColour.setLayout(jPanelSkyColourLayout);
        jPanelSkyColourLayout.setHorizontalGroup(
            jPanelSkyColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelSkyColourLayout.setVerticalGroup(
            jPanelSkyColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelEffectsStarLayout = new javax.swing.GroupLayout(jPanelEffectsStar);
        jPanelEffectsStar.setLayout(jPanelEffectsStarLayout);
        jPanelEffectsStarLayout.setHorizontalGroup(
            jPanelEffectsStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEffectsStarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEffectsStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEffectsStarLayout.createSequentialGroup()
                        .addComponent(jLabelStarColour)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(jPanelEffectsStarLayout.createSequentialGroup()
                        .addComponent(jLabelSkyColour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanelEffectsStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSkyColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelStarColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanelEffectsStarLayout.setVerticalGroup(
            jPanelEffectsStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsStarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEffectsStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStarColour)
                    .addComponent(jPanelStarColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanelEffectsStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelSkyColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSkyColour))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPaneEffects.add(jPanelEffectsStar, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, -1));

        jLabeleFixedColour.setText("Colour");

        jPanelFixedColour.setBackground(new java.awt.Color(255, 0, 0));
        jPanelFixedColour.setMaximumSize(new java.awt.Dimension(20, 20));
        jPanelFixedColour.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanelFixedColour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelFixedColourMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelFixedColourLayout = new javax.swing.GroupLayout(jPanelFixedColour);
        jPanelFixedColour.setLayout(jPanelFixedColourLayout);
        jPanelFixedColourLayout.setHorizontalGroup(
            jPanelFixedColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanelFixedColourLayout.setVerticalGroup(
            jPanelFixedColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelEffectsFixedColourLayout = new javax.swing.GroupLayout(jPanelEffectsFixedColour);
        jPanelEffectsFixedColour.setLayout(jPanelEffectsFixedColourLayout);
        jPanelEffectsFixedColourLayout.setHorizontalGroup(
            jPanelEffectsFixedColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsFixedColourLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabeleFixedColour)
                .addGap(18, 18, 18)
                .addComponent(jPanelFixedColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEffectsFixedColourLayout.setVerticalGroup(
            jPanelEffectsFixedColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsFixedColourLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEffectsFixedColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelFixedColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabeleFixedColour))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPaneEffects.add(jPanelEffectsFixedColour, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 0, 130, -1));

        javax.swing.GroupLayout jPanelEffectsLayout = new javax.swing.GroupLayout(jPanelEffects);
        jPanelEffects.setLayout(jPanelEffectsLayout);
        jPanelEffectsLayout.setHorizontalGroup(
            jPanelEffectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxEffects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPaneEffects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(737, Short.MAX_VALUE))
        );
        jPanelEffectsLayout.setVerticalGroup(
            jPanelEffectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEffectsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEffectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxEffects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPaneEffects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        jTabbedPaneLighting.addTab("Effects", jPanelEffects);

        jListZones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Indicators", "F Keys", "Modifiers", "Arrows", "WASD", "Functions", "Keys" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListZones);

        javax.swing.GroupLayout jPanelZonesLayout = new javax.swing.GroupLayout(jPanelZones);
        jPanelZones.setLayout(jPanelZonesLayout);
        jPanelZonesLayout.setHorizontalGroup(
            jPanelZonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jColorChooserZones, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelZonesLayout.setVerticalGroup(
            jPanelZonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelZonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jColorChooserZones, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPaneLighting.addTab("Zones", jPanelZones);

        jComboBoxStartupEffect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Effect", "Colour Wave", "Fixed Colour" }));
        jComboBoxStartupEffect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStartupEffectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelStartupEffectLayout = new javax.swing.GroupLayout(jPanelStartupEffect);
        jPanelStartupEffect.setLayout(jPanelStartupEffectLayout);
        jPanelStartupEffectLayout.setHorizontalGroup(
            jPanelStartupEffectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStartupEffectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxStartupEffect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(987, Short.MAX_VALUE))
        );
        jPanelStartupEffectLayout.setVerticalGroup(
            jPanelStartupEffectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStartupEffectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxStartupEffect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(258, Short.MAX_VALUE))
        );

        jTabbedPaneLighting.addTab("Startup Effect", jPanelStartupEffect);

        jButtonLoadProfile.setText("Load profile");
        jButtonLoadProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadProfileActionPerformed(evt);
            }
        });

        jButtonSaveProfile.setText("Save profile");
        jButtonSaveProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveProfileActionPerformed(evt);
            }
        });

        jButtonSetProfile.setText("Set");
        jButtonSetProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetProfileActionPerformed(evt);
            }
        });

        jSliderBrightness.setMajorTickSpacing(10);
        jSliderBrightness.setSnapToTicks(true);
        jSliderBrightness.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderBrightnessStateChanged(evt);
            }
        });

        jLabelBrightness.setText("Brightness");

        jButtonResetProfile.setText("Reset");
        jButtonResetProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLightingLayout = new javax.swing.GroupLayout(jPanelLighting);
        jPanelLighting.setLayout(jPanelLightingLayout);
        jPanelLightingLayout.setHorizontalGroup(
            jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneLighting)
            .addGroup(jPanelLightingLayout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(jButtonLoadProfile)
                .addGap(18, 18, 18)
                .addComponent(jButtonSaveProfile)
                .addGap(18, 18, 18)
                .addComponent(jButtonSetProfile)
                .addGap(18, 18, 18)
                .addComponent(jButtonResetProfile)
                .addGap(18, 18, 18)
                .addGroup(jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderBrightness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBrightness))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLightingLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanelLightingLayout.setVerticalGroup(
            jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLightingLayout.createSequentialGroup()
                .addGap(413, 413, 413)
                .addComponent(jTabbedPaneLighting, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonLoadProfile)
                        .addComponent(jButtonSaveProfile)
                        .addComponent(jButtonSetProfile)
                        .addComponent(jButtonResetProfile))
                    .addGroup(jPanelLightingLayout.createSequentialGroup()
                        .addComponent(jLabelBrightness)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSliderBrightness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(jPanelLightingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelLightingLayout.createSequentialGroup()
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Lighting", jPanelLighting);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonResetProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetProfileActionPerformed
        stopGKeyboardEffects(true);
        gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
        if (gKeyboard.getEffect() != null) {
            resetEffect();
        } else {
            loadColours(convertStandardKeyColourMapToG410ColourMap(gKeyboard.getKeyColourMap()));
            jSliderBrightness.setValue(getAverageBrightness());
            disableAlljLayeredPaneEffectsChildren();
        }
    }//GEN-LAST:event_jButtonResetProfileActionPerformed

    private void jSliderBrightnessStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderBrightnessStateChanged
        if (!jSliderBrightness.getValueIsAdjusting()) {
            if ((jSliderBrightness.getValue() % 10) != 0) {
                return;
            }
            int brightness = jSliderBrightness.getValue();
            setBrightness(brightness);
        }
    }//GEN-LAST:event_jSliderBrightnessStateChanged

    private void jButtonSetProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetProfileActionPerformed
        gKeyboard.setKeyColourMap(convertG410ColourMapToStandardKeyColourMap(getColoursFromVirtualKeyboard()));
        IOOperations.setCurrentProfile(gKeyboard);
    }//GEN-LAST:event_jButtonSetProfileActionPerformed

    private void jButtonSaveProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveProfileActionPerformed
        gKeyboard.setKeyColourMap(convertG410ColourMapToStandardKeyColourMap(getColoursFromVirtualKeyboard()));
        IOOperations.saveProfile(gKeyboard);
    }//GEN-LAST:event_jButtonSaveProfileActionPerformed

    private void jButtonLoadProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadProfileActionPerformed
        GKeyboard tmpGKeyboard = IOOperations.loadProfile(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
        if (tmpGKeyboard != null) {
            stopGKeyboardEffects(true);
            gKeyboard = IOOperations.copyGKeyboard(tmpGKeyboard);
            if (gKeyboard.getEffect() != null) {
                resetEffect();
            } else {
                loadColours(convertStandardKeyColourMapToG410ColourMap(gKeyboard.getKeyColourMap()));
                jSliderBrightness.setValue(getAverageBrightness());
                disableAlljLayeredPaneEffectsChildren();
            }
            resetStartupEffect();
        }
    }//GEN-LAST:event_jButtonLoadProfileActionPerformed

    private void jPanelColour001MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour001MouseClicked
        selectedButton = "001";
        selectedButtonText = "ESC";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour001MouseClicked

    private void jPanelColour002MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour002MouseClicked
        selectedButton = "002";
        selectedButtonText = "F1";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour002MouseClicked

    private void jPanelColour003MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour003MouseClicked
        selectedButton = "003";
        selectedButtonText = "F2";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour003MouseClicked

    private void jPanelColour004MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour004MouseClicked
        selectedButton = "004";
        selectedButtonText = "F3";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour004MouseClicked

    private void jPanelColour005MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour005MouseClicked
        selectedButton = "005";
        selectedButtonText = "F4";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour005MouseClicked

    private void jPanelColour006MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour006MouseClicked
        selectedButton = "006";
        selectedButtonText = "F5";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour006MouseClicked

    private void jPanelColour007MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour007MouseClicked
        selectedButton = "007";
        selectedButtonText = "F6";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour007MouseClicked

    private void jPanelColour008MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour008MouseClicked
        selectedButton = "008";
        selectedButtonText = "F7";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour008MouseClicked

    private void jPanelColour009MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour009MouseClicked
        selectedButton = "009";
        selectedButtonText = "F8";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour009MouseClicked

    private void jPanelColour010MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour010MouseClicked
        selectedButton = "010";
        selectedButtonText = "F9";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour010MouseClicked

    private void jPanelColour011MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour011MouseClicked
        selectedButton = "011";
        selectedButtonText = "F10";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour011MouseClicked

    private void jPanelColour012MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour012MouseClicked
        selectedButton = "012";
        selectedButtonText = "F11";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour012MouseClicked

    private void jPanelColour013MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour013MouseClicked
        selectedButton = "013";
        selectedButtonText = "F12";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour013MouseClicked

    private void jPanelColour014MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour014MouseClicked
        selectedButton = "014";
        selectedButtonText = "PRINT_SCREEN";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour014MouseClicked

    private void jPanelColour015MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour015MouseClicked
        selectedButton = "015";
        selectedButtonText = "SCROLL_LOCK";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour015MouseClicked

    private void jPanelColour016MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour016MouseClicked
        selectedButton = "016";
        selectedButtonText = "PAUSE_BREAK";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour016MouseClicked

    private void jPanelColour017MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour017MouseClicked
        selectedButton = "017";
        selectedButtonText = "TILDE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour017MouseClicked

    private void jPanelColour018MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour018MouseClicked
        selectedButton = "018";
        selectedButtonText = "ONE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour018MouseClicked

    private void jPanelColour019MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour019MouseClicked
        selectedButton = "019";
        selectedButtonText = "TWO";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour019MouseClicked

    private void jPanelColour020MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour020MouseClicked
        selectedButton = "020";
        selectedButtonText = "THREE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour020MouseClicked

    private void jPanelColour021MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour021MouseClicked
        selectedButton = "021";
        selectedButtonText = "FOUR";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour021MouseClicked

    private void jPanelColour022MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour022MouseClicked
        selectedButton = "022";
        selectedButtonText = "FIVE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour022MouseClicked

    private void jPanelColour023MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour023MouseClicked
        selectedButton = "023";
        selectedButtonText = "SIX";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour023MouseClicked

    private void jPanelColour024MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour024MouseClicked
        selectedButton = "024";
        selectedButtonText = "SEVEN";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour024MouseClicked

    private void jPanelColour025MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour025MouseClicked
        selectedButton = "025";
        selectedButtonText = "EIGHT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour025MouseClicked

    private void jPanelColour026MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour026MouseClicked
        selectedButton = "026";
        selectedButtonText = "NINE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour026MouseClicked

    private void jPanelColour027MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour027MouseClicked
        selectedButton = "027";
        selectedButtonText = "ZERO";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour027MouseClicked

    private void jPanelColour028MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour028MouseClicked
        selectedButton = "028";
        selectedButtonText = "MINUS";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour028MouseClicked

    private void jPanelColour029MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour029MouseClicked
        selectedButton = "029";
        selectedButtonText = "EQUALS";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour029MouseClicked

    private void jPanelColour030MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour030MouseClicked
        selectedButton = "030";
        selectedButtonText = "BACKSPACE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour030MouseClicked

    private void jPanelColour031MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour031MouseClicked
        selectedButton = "031";
        selectedButtonText = "INSERT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour031MouseClicked

    private void jPanelColour032MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour032MouseClicked
        selectedButton = "032";
        selectedButtonText = "HOME";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour032MouseClicked

    private void jPanelColour033MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour033MouseClicked
        selectedButton = "033";
        selectedButtonText = "PAGE_UP";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour033MouseClicked

    private void jPanelColour034MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour034MouseClicked
        selectedButton = "034";
        selectedButtonText = "TAB";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour034MouseClicked

    private void jPanelColour035MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour035MouseClicked
        selectedButton = "035";
        selectedButtonText = "Q";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour035MouseClicked

    private void jPanelColour036MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour036MouseClicked
        selectedButton = "036";
        selectedButtonText = "W";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour036MouseClicked

    private void jPanelColour037MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour037MouseClicked
        selectedButton = "037";
        selectedButtonText = "E";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour037MouseClicked

    private void jPanelColour038MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour038MouseClicked
        selectedButton = "038";
        selectedButtonText = "R";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour038MouseClicked

    private void jPanelColour039MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour039MouseClicked
        selectedButton = "039";
        selectedButtonText = "T";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour039MouseClicked

    private void jPanelColour040MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour040MouseClicked
        selectedButton = "040";
        selectedButtonText = "Y";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour040MouseClicked

    private void jPanelColour041MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour041MouseClicked
        selectedButton = "041";
        selectedButtonText = "U";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour041MouseClicked

    private void jPanelColour042MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour042MouseClicked
        selectedButton = "042";
        selectedButtonText = "I";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour042MouseClicked

    private void jPanelColour043MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour043MouseClicked
        selectedButton = "043";
        selectedButtonText = "O";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour043MouseClicked

    private void jPanelColour044MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour044MouseClicked
        selectedButton = "044";
        selectedButtonText = "P";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour044MouseClicked

    private void jPanelColour045MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour045MouseClicked
        selectedButton = "045";
        selectedButtonText = "OPEN_BRACKET";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour045MouseClicked

    private void jPanelColour046MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour046MouseClicked
        selectedButton = "046";
        selectedButtonText = "CLOSE_BRACKET";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour046MouseClicked

    private void jPanelColour047MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour047MouseClicked
        selectedButton = "047";
        selectedButtonText = "BACKSLASH";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour047MouseClicked

    private void jPanelColour048MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour048MouseClicked
        selectedButton = "048";
        selectedButtonText = "KEYBOARD_DELETE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour048MouseClicked

    private void jPanelColour049MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour049MouseClicked
        selectedButton = "049";
        selectedButtonText = "END";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour049MouseClicked

    private void jPanelColour050MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour050MouseClicked
        selectedButton = "050";
        selectedButtonText = "PAGE_DOWN";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour050MouseClicked

    private void jPanelColour051MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour051MouseClicked
        selectedButton = "051";
        selectedButtonText = "CAPS_LOCK";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour051MouseClicked

    private void jPanelColour052MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour052MouseClicked
        selectedButton = "052";
        selectedButtonText = "A";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour052MouseClicked

    private void jPanelColour053MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour053MouseClicked
        selectedButton = "053";
        selectedButtonText = "S";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour053MouseClicked

    private void jPanelColour054MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour054MouseClicked
        selectedButton = "054";
        selectedButtonText = "D";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour054MouseClicked

    private void jPanelColour055MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour055MouseClicked
        selectedButton = "055";
        selectedButtonText = "F";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour055MouseClicked

    private void jPanelColour056MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour056MouseClicked
        selectedButton = "056";
        selectedButtonText = "G";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour056MouseClicked

    private void jPanelColour057MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour057MouseClicked
        selectedButton = "057";
        selectedButtonText = "H";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour057MouseClicked

    private void jPanelColour058MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour058MouseClicked
        selectedButton = "058";
        selectedButtonText = "J";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour058MouseClicked

    private void jPanelColour059MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour059MouseClicked
        selectedButton = "059";
        selectedButtonText = "K";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour059MouseClicked

    private void jPanelColour060MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour060MouseClicked
        selectedButton = "060";
        selectedButtonText = "L";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour060MouseClicked

    private void jPanelColour061MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour061MouseClicked
        selectedButton = "061";
        selectedButtonText = "SEMICOLON";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour061MouseClicked

    private void jPanelColour062MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour062MouseClicked
        selectedButton = "062";
        selectedButtonText = "APOSTROPHE";//UK Layout
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour062MouseClicked

    private void jPanelColour063MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour063MouseClicked
        selectedButton = "063";
        selectedButtonText = "Enter";//UK Layout
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour063MouseClicked

    private void jPanelColour064MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour064MouseClicked
        selectedButton = "064";
        selectedButtonText = "LEFT_SHIFT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour064MouseClicked

    private void jPanelColour065MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour065MouseClicked
        selectedButton = "065";
        selectedButtonText = "Z";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour065MouseClicked

    private void jPanelColour066MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour066MouseClicked
        selectedButton = "066";
        selectedButtonText = "X";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour066MouseClicked

    private void jPanelColour067MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour067MouseClicked
        selectedButton = "067";
        selectedButtonText = "C";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour067MouseClicked

    private void jPanelColour068MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour068MouseClicked
        selectedButton = "068";
        selectedButtonText = "V";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour068MouseClicked

    private void jPanelColour069MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour069MouseClicked
        selectedButton = "069";
        selectedButtonText = "B";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour069MouseClicked

    private void jPanelColour070MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour070MouseClicked
        selectedButton = "070";
        selectedButtonText = "N";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour070MouseClicked

    private void jPanelColour071MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour071MouseClicked
        selectedButton = "071";
        selectedButtonText = "M";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour071MouseClicked

    private void jPanelColour072MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour072MouseClicked
        selectedButton = "072";
        selectedButtonText = "COMMA";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour072MouseClicked

    private void jPanelColour073MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour073MouseClicked
        selectedButton = "073";
        selectedButtonText = "PERIOD";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour073MouseClicked

    private void jPanelColour074MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour074MouseClicked
        selectedButton = "074";
        selectedButtonText = "FORWARD_SLASH";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour074MouseClicked

    private void jPanelColour075MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour075MouseClicked
        selectedButton = "075";
        selectedButtonText = "RIGHT_SHIFT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour075MouseClicked

    private void jPanelColour077MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour077MouseClicked
        selectedButton = "077";
        selectedButtonText = "LEFT_CONTROL";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour077MouseClicked

    private void jPanelColour078MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour078MouseClicked
        selectedButton = "078";
        selectedButtonText = "LEFT_WINDOWS";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour078MouseClicked

    private void jPanelColour079MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour079MouseClicked
        selectedButton = "079";
        selectedButtonText = "LEFT_ALT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour079MouseClicked

    private void jPanelColour080MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour080MouseClicked
        selectedButton = "080";
        selectedButtonText = "SPACE";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour080MouseClicked

    private void jPanelColour081MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour081MouseClicked
        selectedButton = "081";
        selectedButtonText = "RIGHT_ALT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour081MouseClicked

    private void jPanelColour082MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour082MouseClicked
        selectedButton = "082";
        selectedButtonText = "RIGHT_WINDOWS";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour082MouseClicked

    private void jPanelColour083MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour083MouseClicked
        selectedButton = "083";
        selectedButtonText = "APPLICATION_SELECT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour083MouseClicked

    private void jPanelColour084MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour084MouseClicked
        selectedButton = "084";
        selectedButtonText = "RIGHT_CONTROL";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour084MouseClicked

    private void jPanelColour085MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour085MouseClicked
        selectedButton = "085";
        selectedButtonText = "ARROW_LEFT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour085MouseClicked

    private void jPanelColour086MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour086MouseClicked
        selectedButton = "086";
        selectedButtonText = "ARROW_DOWN";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour086MouseClicked

    private void jPanelColour087MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour087MouseClicked
        selectedButton = "087";
        selectedButtonText = "ARROW_RIGHT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour087MouseClicked

    private void jPanelColour076MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour076MouseClicked
        selectedButton = "076";
        selectedButtonText = "ARROW_UP";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour076MouseClicked

    private void jPanelColour088MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour088MouseClicked
        selectedButton = "088";
        selectedButtonText = "GAME";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour088MouseClicked

    private void jPanelColour089MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelColour089MouseClicked
        selectedButton = "089";
        selectedButtonText = "BACKLIGHT";
        jLabelSelectedButton.setText(selectedButtonText);
    }//GEN-LAST:event_jPanelColour089MouseClicked

    private void jComboBoxStartupEffectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStartupEffectActionPerformed
        int selectedIndex = jComboBoxStartupEffect.getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                gKeyboard.setStartupEffect(Effects.Type.NoEffect);
                break;
            case 1:
                gKeyboard.setStartupEffect(Effects.Type.ColourWave);
                gKeyboard.setStartupEffect("ColourWave");
                break;
            case 2:
                gKeyboard.setStartupEffect(Effects.Type.FixedColour);
                gKeyboard.setStartupEffect("FixedColour");
                break;
        }
    }//GEN-LAST:event_jComboBoxStartupEffectActionPerformed

    private void jPanelFixedColourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelFixedColourMouseClicked
        Color initialBackground = new Color(255, 0, 0);
        Color background = JColorChooser.showDialog(null, "Set Colour",
                initialBackground);
        String[] colours = new String[keyCount];
        String hexColour = "ff0000";
        if (background != null) {
            jPanelFixedColour.setBackground(background);
            gKeyboard.getEffect().FixedColour = IOOperations.getHexStringFromColour(background);
            for (int i = 0; i < colours.length; i++) {
                colours[i] = IOOperations.getHexStringFromColour(background);
            }
        } else {
            for (int i = 0; i < colours.length; i++) {
                colours[i] = hexColour;
            }
        }
        loadColoursToVirtualKeyboard(colours);
        gKeyboard.setFXColor(gKeyboard.getEffect().FixedColour);
    }//GEN-LAST:event_jPanelFixedColourMouseClicked

    private void jPanelSkyColourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSkyColourMouseClicked
        Color initialBackground = new Color(0, 0, 0);
        Color background = JColorChooser.showDialog(null, "Set Colour",
                initialBackground);
        if ((background != null) && (background != jPanelSkyColour.getBackground())) {
            stopStarEffect();
            gKeyboard.getEffect().SkyColour = IOOperations.getHexStringFromColour(background);
            gKeyboard.setAllKeys(gKeyboard.getEffect().SkyColour, true);
            jPanelSkyColour.setBackground(background);
            //jPanelColour115.setBackground(jPanelStarColour.getBackground());
            //jLayeredPane1.moveToBack(jPanelColour115);
            //gKeyboard.setGroupKeys("logo", gKeyboard.getEffect().StarColour, true);
            startStarEffect(gKeyboard.getEffect().StarColour, IOOperations.getHexStringFromColour(background));
        }
    }//GEN-LAST:event_jPanelSkyColourMouseClicked

    private void jPanelStarColourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelStarColourMouseClicked
        Color initialBackground = new Color(255, 255, 0);
        Color background = JColorChooser.showDialog(null, "Set Colour",
                initialBackground);
        if ((background != null) && (background != jPanelStarColour.getBackground())) {
            stopStarEffect();
            gKeyboard.getEffect().StarColour = IOOperations.getHexStringFromColour(background);
            gKeyboard.setAllKeys(gKeyboard.getEffect().SkyColour, true);
            jPanelStarColour.setBackground(background);
            //jPanelColour115.setBackground(background);
            //jLayeredPane1.moveToBack(jPanelColour115);
            //gKeyboard.setGroupKeys("logo", gKeyboard.getEffect().StarColour, true);
            startStarEffect(gKeyboard.getEffect().StarColour, gKeyboard.getEffect().SkyColour);
        }
    }//GEN-LAST:event_jPanelStarColourMouseClicked

    private void jSliderBreathingEffectSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderBreathingEffectSpeedStateChanged
        if (breathingSpeedSliderManualChange) {
            return;
        }
        if (!jSliderBreathingEffectSpeed.getValueIsAdjusting()) {
            int speed = getSpeedFromSlider(Effects.Type.Breathing, jSliderBreathingEffectSpeed.getValue());
            if (gKeyboard.getEffect() != null) {
                if (gKeyboard.getEffect().BreathingSpeed != speed) {
                    gKeyboard.getEffect().BreathingSpeed = speed;
                    if (breathingThread != null) {
                        breathingThread.terminate();
                        breathingThread = null;
                    }
                    gKeyboard.getEffect().BreathingColour = IOOperations.getHexStringFromColour(jPanelBreathingColour.getBackground());
                    breathingThread = new VirtualKeyboardBreathingThread();
                    breathingThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                    breathingThread.setSpeed(gKeyboard.getEffect().BreathingSpeed);
                    breathingThread.setBreathingColour(gKeyboard.getEffect().BreathingColour);
                    gKeyboard.setFXBreathing(gKeyboard.getEffect().BreathingColour, gKeyboard.getEffect().BreathingSpeed);
                    breathingThread.start();
                }
            }
        }
    }//GEN-LAST:event_jSliderBreathingEffectSpeedStateChanged

    private void jPanelBreathingColourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBreathingColourMouseClicked
        Color initialBackground = new Color(255, 0, 255);
        Color background = JColorChooser.showDialog(null, "Set Colour",
                initialBackground);
        if ((background != null) && (background != jPanelBreathingColour.getBackground())) {
            jPanelBreathingColour.setBackground(background);
            if (gKeyboard.getEffect() != null) {
                if (breathingThread != null) {
                    breathingThread.terminate();
                    breathingThread = null;
                }
                gKeyboard.getEffect().BreathingSpeed = getSpeedFromSlider(Effects.Type.Breathing, jSliderBreathingEffectSpeed.getValue());
                gKeyboard.getEffect().BreathingColour = IOOperations.getHexStringFromColour(jPanelBreathingColour.getBackground());
                breathingThread = new VirtualKeyboardBreathingThread();
                breathingThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                breathingThread.setSpeed(gKeyboard.getEffect().BreathingSpeed);
                breathingThread.setBreathingColour(gKeyboard.getEffect().BreathingColour);
                gKeyboard.setFXBreathing(gKeyboard.getEffect().BreathingColour, gKeyboard.getEffect().BreathingSpeed);
                breathingThread.start();
            }
        }
    }//GEN-LAST:event_jPanelBreathingColourMouseClicked

    private void jComboBoxColourWaveEffectTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxColourWaveEffectTypeActionPerformed
        int selectedIndex = jComboBoxColourWaveEffectType.getSelectedIndex();
        if (colourWaveThread != null) {
            colourWaveThread.terminate();
            colourWaveThread = null;
        }
        switch (selectedIndex) {
            case 0:
                gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Horizontal;
                colourWaveThread = new VirtualKeyboardColourWaveThread();
                colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                gKeyboard.setFXHWave(gKeyboard.getEffect().ColourWaveSpeed);
                colourWaveThread.start();
                break;
            case 1:
                gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Vertical;
                colourWaveThread = new VirtualKeyboardColourWaveThread();
                colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                gKeyboard.setFXVWave(gKeyboard.getEffect().ColourWaveSpeed);
                colourWaveThread.start();
                break;
            case 2:
                gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.CentreOut;
                colourWaveThread = new VirtualKeyboardColourWaveThread();
                colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                gKeyboard.setFXCWave(gKeyboard.getEffect().ColourWaveSpeed);
                colourWaveThread.start();
                break;
        }
    }//GEN-LAST:event_jComboBoxColourWaveEffectTypeActionPerformed

    private void jSliderColourWaveEffectSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderColourWaveEffectSpeedStateChanged
        if (colourWaveSpeedSliderManualChange) {
            return;
        }
        if (!jSliderColourWaveEffectSpeed.getValueIsAdjusting()) {
            int speed = getSpeedFromSlider(Effects.Type.ColourWave, jSliderColourWaveEffectSpeed.getValue());
            if (gKeyboard.getEffect() != null) {
                if (gKeyboard.getEffect().ColourWaveSpeed != speed) {
                    gKeyboard.getEffect().ColourWaveSpeed = speed;
                    int selectedIndex = jComboBoxColourWaveEffectType.getSelectedIndex();
                    if (colourWaveThread != null) {
                        colourWaveThread.terminate();
                        colourWaveThread = null;
                    }
                    switch (selectedIndex) {
                        case 0:
                            gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Horizontal;
                            colourWaveThread = new VirtualKeyboardColourWaveThread();
                            colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                            colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                            gKeyboard.setFXHWave(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.start();
                            break;
                        case 1:
                            gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Vertical;
                            colourWaveThread = new VirtualKeyboardColourWaveThread();
                            colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                            colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                            gKeyboard.setFXVWave(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.start();
                            break;
                        case 2:
                            gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.CentreOut;
                            colourWaveThread = new VirtualKeyboardColourWaveThread();
                            colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                            colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                            gKeyboard.setFXCWave(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.start();
                            break;
                    }
                }
            }
        }
    }//GEN-LAST:event_jSliderColourWaveEffectSpeedStateChanged

    private void jSliderColourCycleEffectSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderColourCycleEffectSpeedStateChanged
        if (colourCycleSpeedSliderManualChange) {
            return;
        }
        if (!jSliderColourCycleEffectSpeed.getValueIsAdjusting()) {
            int speed = getSpeedFromSlider(Effects.Type.ColourCycle, jSliderColourCycleEffectSpeed.getValue());
            if (gKeyboard.getEffect() != null) {
                if (gKeyboard.getEffect().ColourCycleSpeed != speed) {
                    gKeyboard.getEffect().ColourCycleSpeed = speed;
                    if (colourCycleThread != null) {
                        colourCycleThread.terminate();
                        colourCycleThread = null;
                    }
                    colourCycleThread = new VirtualKeyboardColourCycleThread();
                    colourCycleThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                    colourCycleThread.setSpeed(gKeyboard.getEffect().ColourCycleSpeed);
                    colourCycleThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                    gKeyboard.setFXColorCycle(gKeyboard.getEffect().ColourCycleSpeed);
                    colourCycleThread.start();
                }
            }
        }
    }//GEN-LAST:event_jSliderColourCycleEffectSpeedStateChanged

    private void jComboBoxEffectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEffectsActionPerformed
        int selectedIndex = jComboBoxEffects.getSelectedIndex();
        if (selectedIndex != 0) {
            EffectsRunning = true;
        } else {
            EffectsRunning = false;
        }
        if (!resettingEffect) {
            switch (selectedIndex) {
                case 0:
                    disableAlljLayeredPaneEffectsChildren();
                    stopGKeyboardEffects(false);
                    break;
                case 1:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(true);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsFixedColour);
                    jPanelEffectsFixedColour.setEnabled(true);
                    jPanelEffectsFixedColour.setVisible(true);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.FixedColour;
                    Color initialBackground = new Color(255, 0, 0);
                    gKeyboard.getEffect().FixedColour = IOOperations.getHexStringFromColour(initialBackground);
                    jPanelFixedColour.setBackground(initialBackground);
                    String[] colours = new String[keyCount];
                    for (int i = 0; i < colours.length; i++) {
                        colours[i] = gKeyboard.getEffect().FixedColour;
                    }
                    loadColoursToVirtualKeyboard(colours);
                    gKeyboard.setFXColor(gKeyboard.getEffect().FixedColour);
                    break;
                case 2:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsBreathing);
                    jPanelEffectsBreathing.setEnabled(true);
                    jPanelEffectsBreathing.setVisible(true);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.Breathing;
                    gKeyboard.getEffect().BreathingSpeed = getSpeedFromSlider(Effects.Type.Breathing, jSliderBreathingEffectSpeed.getValue());
                    Color initialBreathingColour = new Color(255, 0, 255);
                    jPanelBreathingColour.setBackground(initialBreathingColour);
                    gKeyboard.getEffect().BreathingColour = IOOperations.getHexStringFromColour(initialBreathingColour);
                    breathingThread = new VirtualKeyboardBreathingThread();
                    breathingThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                    breathingThread.setSpeed(gKeyboard.getEffect().BreathingSpeed);
                    breathingThread.setBreathingColour(gKeyboard.getEffect().BreathingColour);
                    gKeyboard.setFXBreathing(gKeyboard.getEffect().BreathingColour, gKeyboard.getEffect().BreathingSpeed);
                    breathingThread.start();
                    break;
                case 3:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsColourWave);
                    jPanelEffectsColourWave.setEnabled(true);
                    jPanelEffectsColourWave.setVisible(true);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.ColourWave;
                    gKeyboard.getEffect().ColourWaveSpeed = getSpeedFromSlider(Effects.Type.ColourWave, jSliderColourWaveEffectSpeed.getValue());
                    if (colourWaveThread != null) {
                        colourWaveThread.terminate();
                        colourWaveThread = null;
                    }
                    switch (jComboBoxColourWaveEffectType.getSelectedIndex()) {
                        case 0:
                            gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Horizontal;
                            colourWaveThread = new VirtualKeyboardColourWaveThread();
                            colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                            colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                            gKeyboard.setFXHWave(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.start();
                            break;
                        case 1:
                            gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.Vertical;
                            colourWaveThread = new VirtualKeyboardColourWaveThread();
                            colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                            colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                            gKeyboard.setFXVWave(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.start();
                            break;
                        case 2:
                            gKeyboard.getEffect().ColourWave = Effects.ColourWaveType.CentreOut;
                            colourWaveThread = new VirtualKeyboardColourWaveThread();
                            colourWaveThread.setKeyboardJpanels(getKeyboardColourWaveJPanels(gKeyboard.getEffect().ColourWave), jLayeredPane1);
                            colourWaveThread.setSpeed(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                            gKeyboard.setFXCWave(gKeyboard.getEffect().ColourWaveSpeed);
                            colourWaveThread.start();
                            break;
                    }
                    break;
                case 4:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsColourCycle);
                    jPanelEffectsColourCycle.setEnabled(true);
                    jPanelEffectsColourCycle.setVisible(true);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.ColourCycle;
                    gKeyboard.getEffect().ColourCycleSpeed = getSpeedFromSlider(Effects.Type.ColourCycle, jSliderColourCycleEffectSpeed.getValue());
                    colourCycleThread = new VirtualKeyboardColourCycleThread();
                    colourCycleThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                    colourCycleThread.setSpeed(gKeyboard.getEffect().ColourCycleSpeed);
                    colourCycleThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                    gKeyboard.setFXColorCycle(gKeyboard.getEffect().ColourCycleSpeed);
                    colourCycleThread.start();
                    break;
                case 5:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsStar);
                    jPanelEffectsStar.setEnabled(true);
                    jPanelEffectsStar.setVisible(true);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.Stars;
                    //gKeyboard.getEffect().Speed = 2000;
                    gKeyboard.getEffect().StarColour = "FFFF00";
                    gKeyboard.getEffect().SkyColour = "000000";
                    Color initialStar = new Color(255, 255, 0);
                    Color initialSky = new Color(0, 0, 0);
                    gKeyboard.setAllKeys(gKeyboard.getEffect().SkyColour, true);
                    jPanelStarColour.setBackground(initialStar);
                    jPanelSkyColour.setBackground(initialSky);
                    //jPanelColour115.setBackground(initialStar);
                    //jLayeredPane1.moveToBack(jPanelColour115);
                    //gKeyboard.setGroupKeys("logo", gKeyboard.getEffect().StarColour, true);
                    startStarEffect(gKeyboard.getEffect().StarColour, gKeyboard.getEffect().SkyColour);
                    break;
                case 6:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(false);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.Lightning;
                    break;
                case 7:
                    stopGKeyboardEffects(false);
                    jSliderBrightness.setEnabled(false);
                    gKeyboard.setEffect(new Effects());
                    gKeyboard.getEffect().CurrentEffect = Effects.Type.KeyPress;
                    break;
                default:
                    break;
            }
        } else {
            switch (selectedIndex) {
                case 0:
                    disableAlljLayeredPaneEffectsChildren();
                    stopGKeyboardEffects(false);
                    break;
                case 1:
                    jSliderBrightness.setEnabled(true);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsFixedColour);
                    jPanelEffectsFixedColour.setEnabled(true);
                    jPanelEffectsFixedColour.setVisible(true);
                    jPanelFixedColour.setBackground(IOOperations.getColourFromHexString(gKeyboard.getEffect().FixedColour));
                    String[] colours = new String[keyCount];
                    String hexColour = "FFFFFF";
                    if (gKeyboard.getEffect().FixedColour != null) {
                        for (int i = 0; i < colours.length; i++) {
                            colours[i] = gKeyboard.getEffect().FixedColour;
                        }
                    } else {
                        for (int i = 0; i < colours.length; i++) {
                            colours[i] = hexColour;
                        }
                    }
                    loadColoursToVirtualKeyboard(colours);
                    gKeyboard.setFXColor(gKeyboard.getEffect().FixedColour);
                    break;
                case 2:
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsBreathing);
                    jPanelEffectsBreathing.setEnabled(true);
                    jPanelEffectsBreathing.setVisible(true);
                    breathingSpeedSliderManualChange = true;
                    jSliderBreathingEffectSpeed.setValue(getSliderFromSpeed(Effects.Type.Breathing, gKeyboard.getEffect().BreathingSpeed));
                    breathingSpeedSliderManualChange = false;
                    jPanelBreathingColour.setBackground(IOOperations.getColourFromHexString(gKeyboard.getEffect().BreathingColour));
                    breathingThread = new VirtualKeyboardBreathingThread();
                    breathingThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                    breathingThread.setSpeed(gKeyboard.getEffect().BreathingSpeed);
                    breathingThread.setBreathingColour(gKeyboard.getEffect().BreathingColour);
                    gKeyboard.setFXBreathing(gKeyboard.getEffect().BreathingColour, gKeyboard.getEffect().BreathingSpeed);
                    breathingThread.start();
                    break;
                case 3:
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsColourWave);
                    jPanelEffectsColourWave.setEnabled(true);
                    jPanelEffectsColourWave.setVisible(true);
                    colourWaveSpeedSliderManualChange = true;
                    jSliderColourWaveEffectSpeed.setValue(getSliderFromSpeed(Effects.Type.ColourWave, gKeyboard.getEffect().ColourWaveSpeed));
                    colourWaveSpeedSliderManualChange = false;
                    switch (gKeyboard.getEffect().ColourWave) {
                        case Horizontal:
                            jComboBoxColourWaveEffectType.setSelectedItem("Horizontal");
                            break;
                        case Vertical:
                            jComboBoxColourWaveEffectType.setSelectedItem("Vertical");
                            break;
                        case CentreOut:
                            jComboBoxColourWaveEffectType.setSelectedItem("Centre Out");
                            break;
                    }
                    break;
                case 4:
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsColourCycle);
                    jPanelEffectsColourCycle.setEnabled(true);
                    jPanelEffectsColourCycle.setVisible(true);
                    colourCycleSpeedSliderManualChange = true;
                    jSliderColourCycleEffectSpeed.setValue(getSliderFromSpeed(Effects.Type.ColourCycle, gKeyboard.getEffect().ColourCycleSpeed));
                    colourCycleSpeedSliderManualChange = false;
                    colourCycleThread = new VirtualKeyboardColourCycleThread();
                    colourCycleThread.setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
                    colourCycleThread.setSpeed(gKeyboard.getEffect().ColourCycleSpeed);
                    gKeyboard.setFXColorCycle(gKeyboard.getEffect().ColourCycleSpeed);
                    colourCycleThread.setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
                    colourCycleThread.start();
                    break;
                case 5:
                    jSliderBrightness.setEnabled(false);
                    disableAlljLayeredPaneEffectsChildren();
                    jLayeredPaneEffects.moveToFront(jPanelEffectsStar);
                    jPanelEffectsStar.setEnabled(true);
                    jPanelEffectsStar.setVisible(true);
                    Color initialStar = IOOperations.getColourFromHexString(gKeyboard.getEffect().StarColour);
                    Color initialSky = IOOperations.getColourFromHexString(gKeyboard.getEffect().SkyColour);
                    gKeyboard.setAllKeys(gKeyboard.getEffect().SkyColour, true);
                    jPanelStarColour.setBackground(initialStar);
                    jPanelSkyColour.setBackground(initialSky);
                    //jPanelColour115.setBackground(initialStar);
                    //jLayeredPane1.moveToBack(jPanelColour115);
                    //gKeyboard.setGroupKeys("logo", gKeyboard.getEffect().StarColour, true);
                    startStarEffect(gKeyboard.getEffect().StarColour, gKeyboard.getEffect().SkyColour);
                    break;
                case 6:
                    jSliderBrightness.setEnabled(false);
                    break;
                case 7:
                    jSliderBrightness.setEnabled(false);
                    break;
                default:
                    break;
            }
            resettingEffect = false;
        }
    }//GEN-LAST:event_jComboBoxEffectsActionPerformed

    public void dispose() {
        stopGKeyboardEffects(false);
        //load currently set profile from file
        gKeyboard = IOOperations.loadCurrentKeyboardObjectFromFile(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
        if (gKeyboard.getEffect() != null) {
            //service will load profiles after program shutdown
        } else {
            //load colour profile
            loadColours(convertStandardKeyColourMapToG410ColourMap(gKeyboard.getKeyColourMap()));
            jSliderBrightness.setValue(getAverageBrightness());
            disableAlljLayeredPaneEffectsChildren();
        }
        super.dispose();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonLoadProfile;
    public javax.swing.JButton jButtonResetProfile;
    public javax.swing.JButton jButtonSaveProfile;
    public javax.swing.JButton jButtonSetProfile;
    private javax.swing.JColorChooser jColorChooserFreeStyle;
    private javax.swing.JColorChooser jColorChooserZones;
    private javax.swing.JComboBox<String> jComboBoxColourWaveEffectType;
    private javax.swing.JComboBox<String> jComboBoxEffects;
    private javax.swing.JComboBox<String> jComboBoxStartupEffect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelBreathingEffectSpeed;
    private javax.swing.JLabel jLabelBrightness;
    private javax.swing.JLabel jLabelColourCycleEffectSpeed;
    private javax.swing.JLabel jLabelColourWaveEffectSpeed;
    private javax.swing.JLabel jLabelColourWaveEffectType;
    private javax.swing.JLabel jLabelSelectedButton;
    private javax.swing.JLabel jLabelSkyColour;
    private javax.swing.JLabel jLabelStarColour;
    private javax.swing.JLabel jLabeleBreathingColour;
    private javax.swing.JLabel jLabeleFixedColour;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPaneEffects;
    private javax.swing.JList<String> jListZones;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelBreathingColour;
    public javax.swing.JPanel jPanelColour001;
    public javax.swing.JPanel jPanelColour002;
    public javax.swing.JPanel jPanelColour003;
    public javax.swing.JPanel jPanelColour004;
    public javax.swing.JPanel jPanelColour005;
    public javax.swing.JPanel jPanelColour006;
    public javax.swing.JPanel jPanelColour007;
    public javax.swing.JPanel jPanelColour008;
    public javax.swing.JPanel jPanelColour009;
    public javax.swing.JPanel jPanelColour010;
    public javax.swing.JPanel jPanelColour011;
    public javax.swing.JPanel jPanelColour012;
    public javax.swing.JPanel jPanelColour013;
    public javax.swing.JPanel jPanelColour014;
    public javax.swing.JPanel jPanelColour015;
    public javax.swing.JPanel jPanelColour016;
    public javax.swing.JPanel jPanelColour017;
    public javax.swing.JPanel jPanelColour018;
    public javax.swing.JPanel jPanelColour019;
    public javax.swing.JPanel jPanelColour020;
    public javax.swing.JPanel jPanelColour021;
    public javax.swing.JPanel jPanelColour022;
    public javax.swing.JPanel jPanelColour023;
    public javax.swing.JPanel jPanelColour024;
    public javax.swing.JPanel jPanelColour025;
    public javax.swing.JPanel jPanelColour026;
    public javax.swing.JPanel jPanelColour027;
    public javax.swing.JPanel jPanelColour028;
    public javax.swing.JPanel jPanelColour029;
    public javax.swing.JPanel jPanelColour030;
    public javax.swing.JPanel jPanelColour031;
    public javax.swing.JPanel jPanelColour032;
    public javax.swing.JPanel jPanelColour033;
    public javax.swing.JPanel jPanelColour034;
    public javax.swing.JPanel jPanelColour035;
    public javax.swing.JPanel jPanelColour036;
    public javax.swing.JPanel jPanelColour037;
    public javax.swing.JPanel jPanelColour038;
    public javax.swing.JPanel jPanelColour039;
    public javax.swing.JPanel jPanelColour040;
    public javax.swing.JPanel jPanelColour041;
    public javax.swing.JPanel jPanelColour042;
    public javax.swing.JPanel jPanelColour043;
    public javax.swing.JPanel jPanelColour044;
    public javax.swing.JPanel jPanelColour045;
    public javax.swing.JPanel jPanelColour046;
    public javax.swing.JPanel jPanelColour047;
    public javax.swing.JPanel jPanelColour048;
    public javax.swing.JPanel jPanelColour049;
    public javax.swing.JPanel jPanelColour050;
    public javax.swing.JPanel jPanelColour051;
    public javax.swing.JPanel jPanelColour052;
    public javax.swing.JPanel jPanelColour053;
    public javax.swing.JPanel jPanelColour054;
    public javax.swing.JPanel jPanelColour055;
    public javax.swing.JPanel jPanelColour056;
    public javax.swing.JPanel jPanelColour057;
    public javax.swing.JPanel jPanelColour058;
    public javax.swing.JPanel jPanelColour059;
    public javax.swing.JPanel jPanelColour060;
    public javax.swing.JPanel jPanelColour061;
    public javax.swing.JPanel jPanelColour062;
    public javax.swing.JPanel jPanelColour063;
    public javax.swing.JPanel jPanelColour064;
    public javax.swing.JPanel jPanelColour065;
    public javax.swing.JPanel jPanelColour066;
    public javax.swing.JPanel jPanelColour067;
    public javax.swing.JPanel jPanelColour068;
    public javax.swing.JPanel jPanelColour069;
    public javax.swing.JPanel jPanelColour070;
    public javax.swing.JPanel jPanelColour071;
    public javax.swing.JPanel jPanelColour072;
    public javax.swing.JPanel jPanelColour073;
    public javax.swing.JPanel jPanelColour074;
    public javax.swing.JPanel jPanelColour075;
    public javax.swing.JPanel jPanelColour076;
    public javax.swing.JPanel jPanelColour077;
    public javax.swing.JPanel jPanelColour078;
    public javax.swing.JPanel jPanelColour079;
    public javax.swing.JPanel jPanelColour080;
    public javax.swing.JPanel jPanelColour081;
    public javax.swing.JPanel jPanelColour082;
    public javax.swing.JPanel jPanelColour083;
    public javax.swing.JPanel jPanelColour084;
    public javax.swing.JPanel jPanelColour085;
    public javax.swing.JPanel jPanelColour086;
    public javax.swing.JPanel jPanelColour087;
    public javax.swing.JPanel jPanelColour088;
    public javax.swing.JPanel jPanelColour089;
    private javax.swing.JPanel jPanelEffects;
    private javax.swing.JPanel jPanelEffectsBreathing;
    private javax.swing.JPanel jPanelEffectsColourCycle;
    private javax.swing.JPanel jPanelEffectsColourWave;
    private javax.swing.JPanel jPanelEffectsFixedColour;
    private javax.swing.JPanel jPanelEffectsStar;
    private javax.swing.JPanel jPanelFixedColour;
    private javax.swing.JPanel jPanelFreeStyle;
    private javax.swing.JPanel jPanelLighting;
    private javax.swing.JPanel jPanelSkyColour;
    private javax.swing.JPanel jPanelStarColour;
    private javax.swing.JPanel jPanelStartupEffect;
    private javax.swing.JPanel jPanelZones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSliderBreathingEffectSpeed;
    private javax.swing.JSlider jSliderBrightness;
    private javax.swing.JSlider jSliderColourCycleEffectSpeed;
    private javax.swing.JSlider jSliderColourWaveEffectSpeed;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPaneLighting;
    // End of variables declaration//GEN-END:variables

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //       Effects related functions         //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //resuming effects from saved gKeyboard object
    private void resetEffect() {
        resettingEffect = true;
        jTabbedPaneLighting.setSelectedIndex(1);
        switch (gKeyboard.getEffect().CurrentEffect) {
            case NoEffect:
                jComboBoxEffects.setSelectedItem("Effect");
                break;
            case FixedColour:
                jComboBoxEffects.setSelectedItem("Fixed Colour");
                break;
            case Breathing:
                jComboBoxEffects.setSelectedItem("Breathing");
                break;
            case ColourWave:
                jComboBoxEffects.setSelectedItem("Colour Wave");
                break;
            case ColourCycle:
                jComboBoxEffects.setSelectedItem("Colour Cycle");
                break;
            case Stars:
                jComboBoxEffects.setSelectedItem("Stars");
                break;
            case Lightning:
                jComboBoxEffects.setSelectedItem("Lightning");
                break;
            case KeyPress:
                jComboBoxEffects.setSelectedItem("Key Press");
                break;
            default:
                jComboBoxEffects.setSelectedItem("Effect");
                break;
        }
    }

    //resetting startup effect combobox to the saved choice
    private void resetStartupEffect() {
        switch (gKeyboard.getStartupEffect()) {
            case NoEffect:
                jComboBoxStartupEffect.setSelectedItem("Effect");
                break;
            case ColourWave:
                jComboBoxStartupEffect.setSelectedItem("Colour Wave");
                break;
            case FixedColour:
                jComboBoxStartupEffect.setSelectedItem("Fixed Colour");
                break;
        }
    }

    //stopping currently running effect
    private void stopGKeyboardEffects(boolean resetIndex) {
        jSliderBrightness.setEnabled(true);
        if (gKeyboard.getEffect() != null) {
            if (gKeyboard.getEffect().CurrentEffect != Effects.Type.NoEffect) {
                switch (gKeyboard.getEffect().CurrentEffect) {
                    case Stars:
                        if (gKeyboard.getStarEffect() != null) {
                            gKeyboard.getStarEffect().terminate();
                            try {
                                gKeyboard.getStarEffect().join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(G410AtlasSpectrumUSQWERTY.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            gKeyboard.setStarEffect(null);
                            gKeyboard.setAllKeys("000000", true);
                            gKeyboard.getEffect().StarColour = null;
                            gKeyboard.getEffect().SkyColour = null;
                        }
                        break;
                    case Breathing:
                        if (breathingThread != null) {
                            breathingThread.terminate();
                            breathingThread = null;
                        }
                        gKeyboard.setAllKeys("000000", true);
                        gKeyboard.getEffect().BreathingColour = null;
                        gKeyboard.getEffect().BreathingSpeed = 0;
                        break;
                    case FixedColour:
                        gKeyboard.getEffect().FixedColour = null;
                        gKeyboard.setAllKeys("000000", true);
                        break;
                    case ColourCycle:
                        if (colourCycleThread != null) {
                            colourCycleThread.terminate();
                            colourCycleThread = null;
                        }
                        gKeyboard.setAllKeys("000000", true);
                        gKeyboard.getEffect().ColourCycleSpeed = 0;
                        break;
                    case ColourWave:
                        if (colourWaveThread != null) {
                            colourWaveThread.terminate();
                            colourWaveThread = null;
                        }
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
            String[] colours = new String[keyCount];
            for (int i = 0; i < keyCount; i++) {
                colours[i] = "000000";
            }
            loadColoursToVirtualKeyboard(colours);
        }
        if (resetIndex) {
            jComboBoxEffects.setSelectedIndex(0);
        }
    }

    //to organise effects jpanels, enable chossen effect jpanel and disable
    //the rest
    private void disableAlljLayeredPaneEffectsChildren() {
        jPanelEffectsFixedColour.setEnabled(false);
        jPanelEffectsFixedColour.setVisible(false);
        jPanelEffectsStar.setEnabled(false);
        jPanelEffectsStar.setVisible(false);
        jPanelEffectsBreathing.setEnabled(false);
        jPanelEffectsBreathing.setVisible(false);
        jPanelEffectsColourCycle.setEnabled(false);
        jPanelEffectsColourCycle.setVisible(false);
        jPanelEffectsColourWave.setEnabled(false);
        jPanelEffectsColourWave.setVisible(false);
    }

    //start star effect thread
    private void startStarEffect(String starColour, String skyColour) {
        gKeyboard.setStarEffect(new StarEffect());
        gKeyboard.getStarEffect().setKeyboardModel(Keyboard.KeyboardModel.Logi410AtlasSpectrumUSQWERTY);
        gKeyboard.getStarEffect().setStarSpeed(2000);
        gKeyboard.getStarEffect().setStarColour(starColour);
        gKeyboard.getStarEffect().setSkyColour(skyColour);
        gKeyboard.getStarEffect().setKeyboardJpanels(getKeyboardJPanels(), jLayeredPane1);
        gKeyboard.getStarEffect().start();
    }

    //stop star effect thread
    private void stopStarEffect() {
        gKeyboard.getStarEffect().terminate();
        try {
            gKeyboard.getStarEffect().join();
        } catch (InterruptedException ex) {
            Logger.getLogger(G410AtlasSpectrumUSQWERTY.class.getName()).log(Level.SEVERE, null, ex);
        }
        gKeyboard.setStarEffect(null);
    }

    //this function is used to pass virtual keyboard colour panels to
    // star effect thread
    private JPanel[] getKeyboardJPanels() {
        JPanel[] keyboardJPanel = new JPanel[keyCount];
        keyboardJPanel[0] = jPanelColour001;
        keyboardJPanel[1] = jPanelColour002;
        keyboardJPanel[2] = jPanelColour003;
        keyboardJPanel[3] = jPanelColour004;
        keyboardJPanel[4] = jPanelColour005;
        keyboardJPanel[5] = jPanelColour006;
        keyboardJPanel[6] = jPanelColour007;
        keyboardJPanel[7] = jPanelColour008;
        keyboardJPanel[8] = jPanelColour009;
        keyboardJPanel[9] = jPanelColour010;
        keyboardJPanel[10] = jPanelColour011;
        keyboardJPanel[11] = jPanelColour012;
        keyboardJPanel[12] = jPanelColour013;
        keyboardJPanel[13] = jPanelColour014;
        keyboardJPanel[14] = jPanelColour015;
        keyboardJPanel[15] = jPanelColour016;
        keyboardJPanel[16] = jPanelColour017;
        keyboardJPanel[17] = jPanelColour018;
        keyboardJPanel[18] = jPanelColour019;
        keyboardJPanel[19] = jPanelColour020;
        keyboardJPanel[20] = jPanelColour021;
        keyboardJPanel[21] = jPanelColour022;
        keyboardJPanel[22] = jPanelColour023;
        keyboardJPanel[23] = jPanelColour024;
        keyboardJPanel[24] = jPanelColour025;
        keyboardJPanel[25] = jPanelColour026;
        keyboardJPanel[26] = jPanelColour027;
        keyboardJPanel[27] = jPanelColour028;
        keyboardJPanel[28] = jPanelColour029;
        keyboardJPanel[29] = jPanelColour030;
        keyboardJPanel[30] = jPanelColour031;
        keyboardJPanel[31] = jPanelColour032;
        keyboardJPanel[32] = jPanelColour033;
        keyboardJPanel[33] = jPanelColour034;
        keyboardJPanel[34] = jPanelColour035;
        keyboardJPanel[35] = jPanelColour036;
        keyboardJPanel[36] = jPanelColour037;
        keyboardJPanel[37] = jPanelColour038;
        keyboardJPanel[38] = jPanelColour039;
        keyboardJPanel[39] = jPanelColour040;
        keyboardJPanel[40] = jPanelColour041;
        keyboardJPanel[41] = jPanelColour042;
        keyboardJPanel[42] = jPanelColour043;
        keyboardJPanel[43] = jPanelColour044;
        keyboardJPanel[44] = jPanelColour045;
        keyboardJPanel[45] = jPanelColour046;
        keyboardJPanel[46] = jPanelColour047;
        keyboardJPanel[47] = jPanelColour048;
        keyboardJPanel[48] = jPanelColour049;
        keyboardJPanel[49] = jPanelColour050;
        keyboardJPanel[50] = jPanelColour051;
        keyboardJPanel[51] = jPanelColour052;
        keyboardJPanel[52] = jPanelColour053;
        keyboardJPanel[53] = jPanelColour054;
        keyboardJPanel[54] = jPanelColour055;
        keyboardJPanel[55] = jPanelColour056;
        keyboardJPanel[56] = jPanelColour057;
        keyboardJPanel[57] = jPanelColour058;
        keyboardJPanel[58] = jPanelColour059;
        keyboardJPanel[59] = jPanelColour060;
        keyboardJPanel[60] = jPanelColour061;
        keyboardJPanel[61] = jPanelColour062;
        keyboardJPanel[62] = jPanelColour063;
        keyboardJPanel[63] = jPanelColour064;
        keyboardJPanel[64] = jPanelColour065;
        keyboardJPanel[65] = jPanelColour066;
        keyboardJPanel[66] = jPanelColour067;
        keyboardJPanel[67] = jPanelColour068;
        keyboardJPanel[68] = jPanelColour069;
        keyboardJPanel[69] = jPanelColour070;
        keyboardJPanel[70] = jPanelColour071;
        keyboardJPanel[71] = jPanelColour072;
        keyboardJPanel[72] = jPanelColour073;
        keyboardJPanel[73] = jPanelColour074;
        keyboardJPanel[74] = jPanelColour075;
        keyboardJPanel[75] = jPanelColour076;
        keyboardJPanel[76] = jPanelColour077;
        keyboardJPanel[77] = jPanelColour078;
        keyboardJPanel[78] = jPanelColour079;
        keyboardJPanel[79] = jPanelColour080;
        keyboardJPanel[80] = jPanelColour081;
        keyboardJPanel[81] = jPanelColour082;
        keyboardJPanel[82] = jPanelColour083;
        keyboardJPanel[83] = jPanelColour084;
        keyboardJPanel[84] = jPanelColour085;
        keyboardJPanel[85] = jPanelColour086;
        keyboardJPanel[86] = jPanelColour087;
        keyboardJPanel[87] = jPanelColour088;
        keyboardJPanel[88] = jPanelColour089;
        return keyboardJPanel;
    }

    //this function is used to pass virtual keyboard colour panels to
    // colour wave thread
    private JPanel[][] getKeyboardColourWaveJPanels(Effects.ColourWaveType type) {
        switch (type) {
            case Horizontal:
            default:
                JPanel[][] keyboardJPanelHorizontal = new JPanel[21][];
                keyboardJPanelHorizontal[0] = new JPanel[6];
                keyboardJPanelHorizontal[1] = new JPanel[5];
                keyboardJPanelHorizontal[2] = new JPanel[6];
                keyboardJPanelHorizontal[3] = new JPanel[5];
                keyboardJPanelHorizontal[4] = new JPanel[5];
                keyboardJPanelHorizontal[5] = new JPanel[4];
                keyboardJPanelHorizontal[6] = new JPanel[6];
                keyboardJPanelHorizontal[7] = new JPanel[5];
                keyboardJPanelHorizontal[8] = new JPanel[5];
                keyboardJPanelHorizontal[9] = new JPanel[6];
                keyboardJPanelHorizontal[10] = new JPanel[5];
                keyboardJPanelHorizontal[11] = new JPanel[6];
                keyboardJPanelHorizontal[12] = new JPanel[3];
                keyboardJPanelHorizontal[13] = new JPanel[7];
                keyboardJPanelHorizontal[14] = new JPanel[5];
                keyboardJPanelHorizontal[15] = new JPanel[5];
                keyboardJPanelHorizontal[16] = new JPanel[5];
                keyboardJPanelHorizontal[0][0] = jPanelColour001;
                keyboardJPanelHorizontal[0][1] = jPanelColour017;
                keyboardJPanelHorizontal[0][2] = jPanelColour034;
                keyboardJPanelHorizontal[0][3] = jPanelColour051;
                keyboardJPanelHorizontal[0][4] = jPanelColour064;
                keyboardJPanelHorizontal[0][5] = jPanelColour077;
                keyboardJPanelHorizontal[1][0] = jPanelColour002;
                keyboardJPanelHorizontal[1][1] = jPanelColour018;
                keyboardJPanelHorizontal[1][2] = jPanelColour035;
                keyboardJPanelHorizontal[1][3] = jPanelColour052;
                keyboardJPanelHorizontal[1][4] = jPanelColour078;
                keyboardJPanelHorizontal[2][0] = jPanelColour003;
                keyboardJPanelHorizontal[2][1] = jPanelColour019;
                keyboardJPanelHorizontal[2][2] = jPanelColour036;
                keyboardJPanelHorizontal[2][3] = jPanelColour053;
                keyboardJPanelHorizontal[2][4] = jPanelColour065;
                keyboardJPanelHorizontal[2][5] = jPanelColour079;
                keyboardJPanelHorizontal[3][0] = jPanelColour004;
                keyboardJPanelHorizontal[3][1] = jPanelColour020;
                keyboardJPanelHorizontal[3][2] = jPanelColour037;
                keyboardJPanelHorizontal[3][3] = jPanelColour054;
                keyboardJPanelHorizontal[3][4] = jPanelColour066;
                keyboardJPanelHorizontal[4][0] = jPanelColour005;
                keyboardJPanelHorizontal[4][1] = jPanelColour021;
                keyboardJPanelHorizontal[4][2] = jPanelColour038;
                keyboardJPanelHorizontal[4][3] = jPanelColour055;
                keyboardJPanelHorizontal[4][4] = jPanelColour067;
                keyboardJPanelHorizontal[5][0] = jPanelColour022;
                keyboardJPanelHorizontal[5][1] = jPanelColour039;
                keyboardJPanelHorizontal[5][2] = jPanelColour056;
                keyboardJPanelHorizontal[5][3] = jPanelColour068;
                keyboardJPanelHorizontal[6][0] = jPanelColour006;
                keyboardJPanelHorizontal[6][1] = jPanelColour023;
                keyboardJPanelHorizontal[6][2] = jPanelColour040;
                keyboardJPanelHorizontal[6][3] = jPanelColour057;
                keyboardJPanelHorizontal[6][4] = jPanelColour069;
                keyboardJPanelHorizontal[6][5] = jPanelColour080;
                keyboardJPanelHorizontal[7][0] = jPanelColour007;
                keyboardJPanelHorizontal[7][1] = jPanelColour024;
                keyboardJPanelHorizontal[7][2] = jPanelColour041;
                keyboardJPanelHorizontal[7][3] = jPanelColour058;
                keyboardJPanelHorizontal[7][4] = jPanelColour070;
                keyboardJPanelHorizontal[8][0] = jPanelColour008;
                keyboardJPanelHorizontal[8][1] = jPanelColour025;
                keyboardJPanelHorizontal[8][2] = jPanelColour042;
                keyboardJPanelHorizontal[8][3] = jPanelColour059;
                keyboardJPanelHorizontal[8][4] = jPanelColour071;
                keyboardJPanelHorizontal[9][0] = jPanelColour009;
                keyboardJPanelHorizontal[9][1] = jPanelColour026;
                keyboardJPanelHorizontal[9][2] = jPanelColour043;
                keyboardJPanelHorizontal[9][3] = jPanelColour060;
                keyboardJPanelHorizontal[9][4] = jPanelColour072;
                keyboardJPanelHorizontal[9][5] = jPanelColour081;
                keyboardJPanelHorizontal[10][0] = jPanelColour027;
                keyboardJPanelHorizontal[10][1] = jPanelColour044;
                keyboardJPanelHorizontal[10][2] = jPanelColour061;
                keyboardJPanelHorizontal[10][3] = jPanelColour073;
                keyboardJPanelHorizontal[10][4] = jPanelColour082;
                keyboardJPanelHorizontal[11][0] = jPanelColour010;
                keyboardJPanelHorizontal[11][1] = jPanelColour028;
                keyboardJPanelHorizontal[11][2] = jPanelColour045;
                keyboardJPanelHorizontal[11][3] = jPanelColour062;
                keyboardJPanelHorizontal[11][4] = jPanelColour074;
                keyboardJPanelHorizontal[11][5] = jPanelColour083;
                keyboardJPanelHorizontal[12][0] = jPanelColour011;
                keyboardJPanelHorizontal[12][1] = jPanelColour029;
                keyboardJPanelHorizontal[12][2] = jPanelColour046;
                keyboardJPanelHorizontal[13][0] = jPanelColour012;
                keyboardJPanelHorizontal[13][1] = jPanelColour013;
                keyboardJPanelHorizontal[13][2] = jPanelColour030;
                keyboardJPanelHorizontal[13][3] = jPanelColour047;
                keyboardJPanelHorizontal[13][4] = jPanelColour063;
                keyboardJPanelHorizontal[13][5] = jPanelColour075;
                keyboardJPanelHorizontal[13][6] = jPanelColour084;
                keyboardJPanelHorizontal[14][0] = jPanelColour088;
                keyboardJPanelHorizontal[14][1] = jPanelColour014;
                keyboardJPanelHorizontal[14][2] = jPanelColour031;
                keyboardJPanelHorizontal[14][3] = jPanelColour048;
                keyboardJPanelHorizontal[14][4] = jPanelColour085;
                keyboardJPanelHorizontal[15][0] = jPanelColour015;
                keyboardJPanelHorizontal[15][1] = jPanelColour032;
                keyboardJPanelHorizontal[15][2] = jPanelColour049;
                keyboardJPanelHorizontal[15][3] = jPanelColour087;
                keyboardJPanelHorizontal[15][4] = jPanelColour086;
                keyboardJPanelHorizontal[16][0] = jPanelColour089;
                keyboardJPanelHorizontal[16][1] = jPanelColour016;
                keyboardJPanelHorizontal[16][2] = jPanelColour033;
                keyboardJPanelHorizontal[16][3] = jPanelColour050;
                keyboardJPanelHorizontal[16][4] = jPanelColour087;
                return keyboardJPanelHorizontal;
            case Vertical:
                JPanel[][] keyboardJPanelVertical = new JPanel[8][];
                keyboardJPanelVertical[0] = new JPanel[2];
                keyboardJPanelVertical[1] = new JPanel[16];
                keyboardJPanelVertical[2] = new JPanel[17];
                keyboardJPanelVertical[3] = new JPanel[17];
                keyboardJPanelVertical[4] = new JPanel[13];
                keyboardJPanelVertical[5] = new JPanel[13];
                keyboardJPanelVertical[6] = new JPanel[11];
                keyboardJPanelVertical[0][0] = jPanelColour088;
                keyboardJPanelVertical[0][1] = jPanelColour089;
                keyboardJPanelVertical[1][0] = jPanelColour001;
                keyboardJPanelVertical[1][1] = jPanelColour002;
                keyboardJPanelVertical[1][2] = jPanelColour003;
                keyboardJPanelVertical[1][3] = jPanelColour004;
                keyboardJPanelVertical[1][4] = jPanelColour005;
                keyboardJPanelVertical[1][5] = jPanelColour006;
                keyboardJPanelVertical[1][6] = jPanelColour007;
                keyboardJPanelVertical[1][7] = jPanelColour008;
                keyboardJPanelVertical[1][8] = jPanelColour009;
                keyboardJPanelVertical[1][9] = jPanelColour010;
                keyboardJPanelVertical[1][10] = jPanelColour011;
                keyboardJPanelVertical[1][11] = jPanelColour012;
                keyboardJPanelVertical[1][12] = jPanelColour013;
                keyboardJPanelVertical[1][13] = jPanelColour014;
                keyboardJPanelVertical[1][14] = jPanelColour015;
                keyboardJPanelVertical[1][15] = jPanelColour016;
                keyboardJPanelVertical[2][0] = jPanelColour017;
                keyboardJPanelVertical[2][1] = jPanelColour018;
                keyboardJPanelVertical[2][2] = jPanelColour019;
                keyboardJPanelVertical[2][3] = jPanelColour020;
                keyboardJPanelVertical[2][4] = jPanelColour021;
                keyboardJPanelVertical[2][5] = jPanelColour022;
                keyboardJPanelVertical[2][6] = jPanelColour023;
                keyboardJPanelVertical[2][7] = jPanelColour024;
                keyboardJPanelVertical[2][8] = jPanelColour025;
                keyboardJPanelVertical[2][9] = jPanelColour026;
                keyboardJPanelVertical[2][10] = jPanelColour027;
                keyboardJPanelVertical[2][11] = jPanelColour028;
                keyboardJPanelVertical[2][12] = jPanelColour029;
                keyboardJPanelVertical[2][13] = jPanelColour030;
                keyboardJPanelVertical[2][14] = jPanelColour031;
                keyboardJPanelVertical[2][15] = jPanelColour032;
                keyboardJPanelVertical[2][16] = jPanelColour033;
                keyboardJPanelVertical[3][0] = jPanelColour034;
                keyboardJPanelVertical[3][1] = jPanelColour035;
                keyboardJPanelVertical[3][2] = jPanelColour036;
                keyboardJPanelVertical[3][3] = jPanelColour037;
                keyboardJPanelVertical[3][4] = jPanelColour038;
                keyboardJPanelVertical[3][5] = jPanelColour039;
                keyboardJPanelVertical[3][6] = jPanelColour040;
                keyboardJPanelVertical[3][7] = jPanelColour041;
                keyboardJPanelVertical[3][8] = jPanelColour042;
                keyboardJPanelVertical[3][9] = jPanelColour043;
                keyboardJPanelVertical[3][10] = jPanelColour044;
                keyboardJPanelVertical[3][11] = jPanelColour045;
                keyboardJPanelVertical[3][12] = jPanelColour046;
                keyboardJPanelVertical[3][13] = jPanelColour047;
                keyboardJPanelVertical[3][14] = jPanelColour048;
                keyboardJPanelVertical[3][15] = jPanelColour049;
                keyboardJPanelVertical[3][16] = jPanelColour050;
                keyboardJPanelVertical[4][0] = jPanelColour051;
                keyboardJPanelVertical[4][1] = jPanelColour052;
                keyboardJPanelVertical[4][2] = jPanelColour053;
                keyboardJPanelVertical[4][3] = jPanelColour054;
                keyboardJPanelVertical[4][4] = jPanelColour055;
                keyboardJPanelVertical[4][5] = jPanelColour056;
                keyboardJPanelVertical[4][6] = jPanelColour057;
                keyboardJPanelVertical[4][7] = jPanelColour058;
                keyboardJPanelVertical[4][8] = jPanelColour059;
                keyboardJPanelVertical[4][9] = jPanelColour060;
                keyboardJPanelVertical[4][10] = jPanelColour061;
                keyboardJPanelVertical[4][11] = jPanelColour062;
                keyboardJPanelVertical[4][12] = jPanelColour063;
                keyboardJPanelVertical[5][0] = jPanelColour064;
                keyboardJPanelVertical[5][1] = jPanelColour065;
                keyboardJPanelVertical[5][2] = jPanelColour066;
                keyboardJPanelVertical[5][3] = jPanelColour067;
                keyboardJPanelVertical[5][4] = jPanelColour068;
                keyboardJPanelVertical[5][5] = jPanelColour069;
                keyboardJPanelVertical[5][6] = jPanelColour070;
                keyboardJPanelVertical[5][7] = jPanelColour071;
                keyboardJPanelVertical[5][8] = jPanelColour072;
                keyboardJPanelVertical[5][9] = jPanelColour073;
                keyboardJPanelVertical[5][10] = jPanelColour074;
                keyboardJPanelVertical[5][11] = jPanelColour075;
                keyboardJPanelVertical[5][12] = jPanelColour076;
                keyboardJPanelVertical[6][0] = jPanelColour077;
                keyboardJPanelVertical[6][1] = jPanelColour078;
                keyboardJPanelVertical[6][2] = jPanelColour079;
                keyboardJPanelVertical[6][3] = jPanelColour080;
                keyboardJPanelVertical[6][4] = jPanelColour081;
                keyboardJPanelVertical[6][5] = jPanelColour082;
                keyboardJPanelVertical[6][6] = jPanelColour083;
                keyboardJPanelVertical[6][7] = jPanelColour084;
                keyboardJPanelVertical[6][8] = jPanelColour085;
                keyboardJPanelVertical[6][9] = jPanelColour086;
                keyboardJPanelVertical[6][10] = jPanelColour087;
                return keyboardJPanelVertical;
            case CentreOut:
                JPanel[][] keyboardJPanelCentreOut = new JPanel[11][];
                keyboardJPanelCentreOut[0] = new JPanel[1];
                keyboardJPanelCentreOut[1] = new JPanel[8];
                keyboardJPanelCentreOut[2] = new JPanel[11];
                keyboardJPanelCentreOut[3] = new JPanel[17];
                keyboardJPanelCentreOut[4] = new JPanel[12];
                keyboardJPanelCentreOut[5] = new JPanel[15];
                keyboardJPanelCentreOut[6] = new JPanel[10];
                keyboardJPanelCentreOut[7] = new JPanel[10];
                keyboardJPanelCentreOut[8] = new JPanel[5];
                keyboardJPanelCentreOut[0][0] = jPanelColour058;
                keyboardJPanelCentreOut[1][0] = jPanelColour059;
                keyboardJPanelCentreOut[1][1] = jPanelColour071;
                keyboardJPanelCentreOut[1][2] = jPanelColour070;
                keyboardJPanelCentreOut[1][3] = jPanelColour069;
                keyboardJPanelCentreOut[1][4] = jPanelColour057;
                keyboardJPanelCentreOut[1][5] = jPanelColour040;
                keyboardJPanelCentreOut[1][6] = jPanelColour041;
                keyboardJPanelCentreOut[1][7] = jPanelColour042;
                keyboardJPanelCentreOut[2][0] = jPanelColour043;
                keyboardJPanelCentreOut[2][1] = jPanelColour060;
                keyboardJPanelCentreOut[2][2] = jPanelColour072;
                keyboardJPanelCentreOut[2][3] = jPanelColour080;
                keyboardJPanelCentreOut[2][4] = jPanelColour068;
                keyboardJPanelCentreOut[2][5] = jPanelColour056;
                keyboardJPanelCentreOut[2][6] = jPanelColour039;
                keyboardJPanelCentreOut[2][7] = jPanelColour023;
                keyboardJPanelCentreOut[2][8] = jPanelColour024;
                keyboardJPanelCentreOut[2][9] = jPanelColour025;
                keyboardJPanelCentreOut[2][10] = jPanelColour026;
                keyboardJPanelCentreOut[3][0] = jPanelColour027;
                keyboardJPanelCentreOut[3][1] = jPanelColour044;
                keyboardJPanelCentreOut[3][2] = jPanelColour045;
                keyboardJPanelCentreOut[3][3] = jPanelColour061;
                keyboardJPanelCentreOut[3][4] = jPanelColour062;
                keyboardJPanelCentreOut[3][5] = jPanelColour073;
                keyboardJPanelCentreOut[3][6] = jPanelColour074;
                keyboardJPanelCentreOut[3][7] = jPanelColour082;
                keyboardJPanelCentreOut[3][8] = jPanelColour067;
                keyboardJPanelCentreOut[3][9] = jPanelColour055;
                keyboardJPanelCentreOut[3][10] = jPanelColour038;
                keyboardJPanelCentreOut[3][11] = jPanelColour022;
                keyboardJPanelCentreOut[3][12] = jPanelColour006;
                keyboardJPanelCentreOut[3][13] = jPanelColour007;
                keyboardJPanelCentreOut[3][14] = jPanelColour008;
                keyboardJPanelCentreOut[3][15] = jPanelColour009;
                keyboardJPanelCentreOut[3][16] = jPanelColour081;
                keyboardJPanelCentreOut[4][0] = jPanelColour010;
                keyboardJPanelCentreOut[4][1] = jPanelColour028;
                keyboardJPanelCentreOut[4][2] = jPanelColour029;
                keyboardJPanelCentreOut[4][3] = jPanelColour046;
                keyboardJPanelCentreOut[4][4] = jPanelColour083;
                keyboardJPanelCentreOut[4][5] = jPanelColour066;
                keyboardJPanelCentreOut[4][6] = jPanelColour053;
                keyboardJPanelCentreOut[4][7] = jPanelColour054;
                keyboardJPanelCentreOut[4][8] = jPanelColour037;
                keyboardJPanelCentreOut[4][9] = jPanelColour020;
                keyboardJPanelCentreOut[4][10] = jPanelColour021;
                keyboardJPanelCentreOut[4][11] = jPanelColour005;
                keyboardJPanelCentreOut[5][0] = jPanelColour030;
                keyboardJPanelCentreOut[5][1] = jPanelColour047;
                keyboardJPanelCentreOut[5][2] = jPanelColour063;
                keyboardJPanelCentreOut[5][3] = jPanelColour075;
                keyboardJPanelCentreOut[5][4] = jPanelColour084;
                keyboardJPanelCentreOut[5][5] = jPanelColour079;
                keyboardJPanelCentreOut[5][6] = jPanelColour065;
                keyboardJPanelCentreOut[5][7] = jPanelColour052;
                keyboardJPanelCentreOut[5][8] = jPanelColour036;
                keyboardJPanelCentreOut[5][9] = jPanelColour035;
                keyboardJPanelCentreOut[5][10] = jPanelColour019;
                keyboardJPanelCentreOut[5][11] = jPanelColour003;
                keyboardJPanelCentreOut[5][12] = jPanelColour004;
                keyboardJPanelCentreOut[5][13] = jPanelColour011;
                keyboardJPanelCentreOut[5][14] = jPanelColour012;
                keyboardJPanelCentreOut[6][0] = jPanelColour088;
                keyboardJPanelCentreOut[6][1] = jPanelColour014;
                keyboardJPanelCentreOut[6][2] = jPanelColour031;
                keyboardJPanelCentreOut[6][3] = jPanelColour048;
                keyboardJPanelCentreOut[6][4] = jPanelColour085;
                keyboardJPanelCentreOut[6][5] = jPanelColour078;
                keyboardJPanelCentreOut[6][6] = jPanelColour064;
                keyboardJPanelCentreOut[6][7] = jPanelColour018;
                keyboardJPanelCentreOut[6][8] = jPanelColour002;
                keyboardJPanelCentreOut[6][9] = jPanelColour013;
                keyboardJPanelCentreOut[7][0] = jPanelColour015;
                keyboardJPanelCentreOut[7][1] = jPanelColour032;
                keyboardJPanelCentreOut[7][2] = jPanelColour049;
                keyboardJPanelCentreOut[7][3] = jPanelColour076;
                keyboardJPanelCentreOut[7][4] = jPanelColour086;
                keyboardJPanelCentreOut[7][5] = jPanelColour077;
                keyboardJPanelCentreOut[7][6] = jPanelColour051;
                keyboardJPanelCentreOut[7][7] = jPanelColour034;
                keyboardJPanelCentreOut[7][8] = jPanelColour017;
                keyboardJPanelCentreOut[7][9] = jPanelColour001;
                keyboardJPanelCentreOut[8][0] = jPanelColour089;
                keyboardJPanelCentreOut[8][1] = jPanelColour016;
                keyboardJPanelCentreOut[8][2] = jPanelColour033;
                keyboardJPanelCentreOut[8][3] = jPanelColour050;
                keyboardJPanelCentreOut[8][4] = jPanelColour087;
                return keyboardJPanelCentreOut;
        }
    }

    //this is done to separate speed from slider and make it less confusing to
    //amend speed without affecting slider
    private int getSpeedFromSlider(Effects.Type effectType, int sliderValue) {
        int[] speedArray;
        switch (effectType) {
            case Breathing:
                speedArray = new int[]{64, 48, 32, 24, 16, 12, 8, 6, 4, 2, 0};
                break;
            case ColourCycle:
                speedArray = new int[]{64, 48, 32, 24, 16, 12, 8, 6, 4, 2, 1};
                break;
            case ColourWave:
            default:
                speedArray = new int[]{64, 48, 32, 20, 12, 8, 7, 6, 4, 2, 0};
                break;
        }
        return speedArray[sliderValue];
    }

    //this is done to separate speed from slider and make it less confusing to
    //amend speed without affecting slider
    private int getSliderFromSpeed(Effects.Type effectType, int speed) {
        int[] speedArray;
        switch (effectType) {
            case Breathing:
                speedArray = new int[]{64, 48, 32, 24, 16, 12, 8, 6, 4, 2, 0};
                return IOOperations.getIndexOf(speedArray, speed);
            case ColourCycle:
                speedArray = new int[]{64, 48, 32, 24, 16, 12, 8, 6, 4, 2, 1};
                return IOOperations.getIndexOf(speedArray, speed);
            case ColourWave:
            default:
                speedArray = new int[]{64, 48, 32, 20, 12, 8, 7, 6, 4, 2, 0};
                return IOOperations.getIndexOf(speedArray, speed);
        }
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //     Loading colours to Keyboards &      //
    //     Getting colours from keyboards &    //
    //         other related functions         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //load colours to both physical and virtual keyboards
    private void loadColours(String[] colours) {
        loadColoursToVirtualKeyboard(colours);
        loadColoursToPhysicalKeyboard(colours);
    }

    //load colours to virtual keyboard
    public void loadColoursToVirtualKeyboard(String[] colours) {
        jPanelColour001.setBackground(IOOperations.getColourFromHexString(colours[0]));
        jLayeredPane1.moveToBack(jPanelColour001);
        jPanelColour002.setBackground(IOOperations.getColourFromHexString(colours[1]));
        jLayeredPane1.moveToBack(jPanelColour002);
        jPanelColour003.setBackground(IOOperations.getColourFromHexString(colours[2]));
        jLayeredPane1.moveToBack(jPanelColour003);
        jPanelColour004.setBackground(IOOperations.getColourFromHexString(colours[3]));
        jLayeredPane1.moveToBack(jPanelColour004);
        jPanelColour005.setBackground(IOOperations.getColourFromHexString(colours[4]));
        jLayeredPane1.moveToBack(jPanelColour005);
        jPanelColour006.setBackground(IOOperations.getColourFromHexString(colours[5]));
        jLayeredPane1.moveToBack(jPanelColour006);
        jPanelColour007.setBackground(IOOperations.getColourFromHexString(colours[6]));
        jLayeredPane1.moveToBack(jPanelColour007);
        jPanelColour008.setBackground(IOOperations.getColourFromHexString(colours[7]));
        jLayeredPane1.moveToBack(jPanelColour008);
        jPanelColour009.setBackground(IOOperations.getColourFromHexString(colours[8]));
        jLayeredPane1.moveToBack(jPanelColour009);
        jPanelColour010.setBackground(IOOperations.getColourFromHexString(colours[9]));
        jLayeredPane1.moveToBack(jPanelColour010);
        jPanelColour011.setBackground(IOOperations.getColourFromHexString(colours[10]));
        jLayeredPane1.moveToBack(jPanelColour011);
        jPanelColour012.setBackground(IOOperations.getColourFromHexString(colours[11]));
        jLayeredPane1.moveToBack(jPanelColour012);
        jPanelColour013.setBackground(IOOperations.getColourFromHexString(colours[12]));
        jLayeredPane1.moveToBack(jPanelColour013);
        jPanelColour014.setBackground(IOOperations.getColourFromHexString(colours[13]));
        jLayeredPane1.moveToBack(jPanelColour014);
        jPanelColour015.setBackground(IOOperations.getColourFromHexString(colours[14]));
        jLayeredPane1.moveToBack(jPanelColour015);
        jPanelColour016.setBackground(IOOperations.getColourFromHexString(colours[15]));
        jLayeredPane1.moveToBack(jPanelColour016);
        jPanelColour017.setBackground(IOOperations.getColourFromHexString(colours[16]));
        jLayeredPane1.moveToBack(jPanelColour017);
        jPanelColour018.setBackground(IOOperations.getColourFromHexString(colours[17]));
        jLayeredPane1.moveToBack(jPanelColour018);
        jPanelColour019.setBackground(IOOperations.getColourFromHexString(colours[18]));
        jLayeredPane1.moveToBack(jPanelColour019);
        jPanelColour020.setBackground(IOOperations.getColourFromHexString(colours[19]));
        jLayeredPane1.moveToBack(jPanelColour020);
        jPanelColour021.setBackground(IOOperations.getColourFromHexString(colours[20]));
        jLayeredPane1.moveToBack(jPanelColour021);
        jPanelColour022.setBackground(IOOperations.getColourFromHexString(colours[21]));
        jLayeredPane1.moveToBack(jPanelColour022);
        jPanelColour023.setBackground(IOOperations.getColourFromHexString(colours[22]));
        jLayeredPane1.moveToBack(jPanelColour023);
        jPanelColour024.setBackground(IOOperations.getColourFromHexString(colours[23]));
        jLayeredPane1.moveToBack(jPanelColour024);
        jPanelColour025.setBackground(IOOperations.getColourFromHexString(colours[24]));
        jLayeredPane1.moveToBack(jPanelColour025);
        jPanelColour026.setBackground(IOOperations.getColourFromHexString(colours[25]));
        jLayeredPane1.moveToBack(jPanelColour026);
        jPanelColour027.setBackground(IOOperations.getColourFromHexString(colours[26]));
        jLayeredPane1.moveToBack(jPanelColour027);
        jPanelColour028.setBackground(IOOperations.getColourFromHexString(colours[27]));
        jLayeredPane1.moveToBack(jPanelColour028);
        jPanelColour029.setBackground(IOOperations.getColourFromHexString(colours[28]));
        jLayeredPane1.moveToBack(jPanelColour029);
        jPanelColour030.setBackground(IOOperations.getColourFromHexString(colours[29]));
        jLayeredPane1.moveToBack(jPanelColour030);
        jPanelColour031.setBackground(IOOperations.getColourFromHexString(colours[30]));
        jLayeredPane1.moveToBack(jPanelColour031);
        jPanelColour032.setBackground(IOOperations.getColourFromHexString(colours[31]));
        jLayeredPane1.moveToBack(jPanelColour032);
        jPanelColour033.setBackground(IOOperations.getColourFromHexString(colours[32]));
        jLayeredPane1.moveToBack(jPanelColour033);
        jPanelColour034.setBackground(IOOperations.getColourFromHexString(colours[33]));
        jLayeredPane1.moveToBack(jPanelColour034);
        jPanelColour035.setBackground(IOOperations.getColourFromHexString(colours[34]));
        jLayeredPane1.moveToBack(jPanelColour035);
        jPanelColour036.setBackground(IOOperations.getColourFromHexString(colours[35]));
        jLayeredPane1.moveToBack(jPanelColour036);
        jPanelColour037.setBackground(IOOperations.getColourFromHexString(colours[36]));
        jLayeredPane1.moveToBack(jPanelColour037);
        jPanelColour038.setBackground(IOOperations.getColourFromHexString(colours[37]));
        jLayeredPane1.moveToBack(jPanelColour038);
        jPanelColour039.setBackground(IOOperations.getColourFromHexString(colours[38]));
        jLayeredPane1.moveToBack(jPanelColour039);
        jPanelColour040.setBackground(IOOperations.getColourFromHexString(colours[39]));
        jLayeredPane1.moveToBack(jPanelColour040);
        jPanelColour041.setBackground(IOOperations.getColourFromHexString(colours[40]));
        jLayeredPane1.moveToBack(jPanelColour041);
        jPanelColour042.setBackground(IOOperations.getColourFromHexString(colours[41]));
        jLayeredPane1.moveToBack(jPanelColour042);
        jPanelColour043.setBackground(IOOperations.getColourFromHexString(colours[42]));
        jLayeredPane1.moveToBack(jPanelColour043);
        jPanelColour044.setBackground(IOOperations.getColourFromHexString(colours[43]));
        jLayeredPane1.moveToBack(jPanelColour044);
        jPanelColour045.setBackground(IOOperations.getColourFromHexString(colours[44]));
        jLayeredPane1.moveToBack(jPanelColour045);
        jPanelColour046.setBackground(IOOperations.getColourFromHexString(colours[45]));
        jLayeredPane1.moveToBack(jPanelColour046);
        jPanelColour047.setBackground(IOOperations.getColourFromHexString(colours[46]));
        jLayeredPane1.moveToBack(jPanelColour047);
        jPanelColour048.setBackground(IOOperations.getColourFromHexString(colours[47]));
        jLayeredPane1.moveToBack(jPanelColour048);
        jPanelColour049.setBackground(IOOperations.getColourFromHexString(colours[48]));
        jLayeredPane1.moveToBack(jPanelColour049);
        jPanelColour050.setBackground(IOOperations.getColourFromHexString(colours[49]));
        jLayeredPane1.moveToBack(jPanelColour050);
        jPanelColour051.setBackground(IOOperations.getColourFromHexString(colours[50]));
        jLayeredPane1.moveToBack(jPanelColour051);
        jPanelColour052.setBackground(IOOperations.getColourFromHexString(colours[51]));
        jLayeredPane1.moveToBack(jPanelColour052);
        jPanelColour053.setBackground(IOOperations.getColourFromHexString(colours[52]));
        jLayeredPane1.moveToBack(jPanelColour053);
        jPanelColour054.setBackground(IOOperations.getColourFromHexString(colours[53]));
        jLayeredPane1.moveToBack(jPanelColour054);
        jPanelColour055.setBackground(IOOperations.getColourFromHexString(colours[54]));
        jLayeredPane1.moveToBack(jPanelColour055);
        jPanelColour056.setBackground(IOOperations.getColourFromHexString(colours[55]));
        jLayeredPane1.moveToBack(jPanelColour056);
        jPanelColour057.setBackground(IOOperations.getColourFromHexString(colours[56]));
        jLayeredPane1.moveToBack(jPanelColour057);
        jPanelColour058.setBackground(IOOperations.getColourFromHexString(colours[57]));
        jLayeredPane1.moveToBack(jPanelColour058);
        jPanelColour059.setBackground(IOOperations.getColourFromHexString(colours[58]));
        jLayeredPane1.moveToBack(jPanelColour059);
        jPanelColour060.setBackground(IOOperations.getColourFromHexString(colours[59]));
        jLayeredPane1.moveToBack(jPanelColour060);
        jPanelColour061.setBackground(IOOperations.getColourFromHexString(colours[60]));
        jLayeredPane1.moveToBack(jPanelColour061);
        jPanelColour062.setBackground(IOOperations.getColourFromHexString(colours[61]));
        jLayeredPane1.moveToBack(jPanelColour062);
        jPanelColour063.setBackground(IOOperations.getColourFromHexString(colours[62]));
        jLayeredPane1.moveToBack(jPanelColour063);
        jPanelColour064.setBackground(IOOperations.getColourFromHexString(colours[63]));
        jLayeredPane1.moveToBack(jPanelColour064);
        jPanelColour065.setBackground(IOOperations.getColourFromHexString(colours[64]));
        jLayeredPane1.moveToBack(jPanelColour065);
        jPanelColour066.setBackground(IOOperations.getColourFromHexString(colours[65]));
        jLayeredPane1.moveToBack(jPanelColour066);
        jPanelColour067.setBackground(IOOperations.getColourFromHexString(colours[66]));
        jLayeredPane1.moveToBack(jPanelColour067);
        jPanelColour068.setBackground(IOOperations.getColourFromHexString(colours[67]));
        jLayeredPane1.moveToBack(jPanelColour068);
        jPanelColour069.setBackground(IOOperations.getColourFromHexString(colours[68]));
        jLayeredPane1.moveToBack(jPanelColour069);
        jPanelColour070.setBackground(IOOperations.getColourFromHexString(colours[69]));
        jLayeredPane1.moveToBack(jPanelColour070);
        jPanelColour071.setBackground(IOOperations.getColourFromHexString(colours[70]));
        jLayeredPane1.moveToBack(jPanelColour071);
        jPanelColour072.setBackground(IOOperations.getColourFromHexString(colours[71]));
        jLayeredPane1.moveToBack(jPanelColour072);
        jPanelColour073.setBackground(IOOperations.getColourFromHexString(colours[72]));
        jLayeredPane1.moveToBack(jPanelColour073);
        jPanelColour074.setBackground(IOOperations.getColourFromHexString(colours[73]));
        jLayeredPane1.moveToBack(jPanelColour074);
        jPanelColour075.setBackground(IOOperations.getColourFromHexString(colours[74]));
        jLayeredPane1.moveToBack(jPanelColour075);
        jPanelColour076.setBackground(IOOperations.getColourFromHexString(colours[75]));
        jLayeredPane1.moveToBack(jPanelColour076);
        jPanelColour077.setBackground(IOOperations.getColourFromHexString(colours[76]));
        jLayeredPane1.moveToBack(jPanelColour077);
        jPanelColour078.setBackground(IOOperations.getColourFromHexString(colours[77]));
        jLayeredPane1.moveToBack(jPanelColour078);
        jPanelColour079.setBackground(IOOperations.getColourFromHexString(colours[78]));
        jLayeredPane1.moveToBack(jPanelColour079);
        jPanelColour080.setBackground(IOOperations.getColourFromHexString(colours[79]));
        jLayeredPane1.moveToBack(jPanelColour080);
        jPanelColour081.setBackground(IOOperations.getColourFromHexString(colours[80]));
        jLayeredPane1.moveToBack(jPanelColour081);
        jPanelColour082.setBackground(IOOperations.getColourFromHexString(colours[81]));
        jLayeredPane1.moveToBack(jPanelColour082);
        jPanelColour083.setBackground(IOOperations.getColourFromHexString(colours[82]));
        jLayeredPane1.moveToBack(jPanelColour083);
        jPanelColour084.setBackground(IOOperations.getColourFromHexString(colours[83]));
        jLayeredPane1.moveToBack(jPanelColour084);
        jPanelColour085.setBackground(IOOperations.getColourFromHexString(colours[84]));
        jLayeredPane1.moveToBack(jPanelColour085);
        jPanelColour086.setBackground(IOOperations.getColourFromHexString(colours[85]));
        jLayeredPane1.moveToBack(jPanelColour086);
        jPanelColour087.setBackground(IOOperations.getColourFromHexString(colours[86]));
        jLayeredPane1.moveToBack(jPanelColour087);
        jPanelColour088.setBackground(IOOperations.getColourFromHexString(colours[87]));
        jLayeredPane1.moveToBack(jPanelColour088);
        jPanelColour089.setBackground(IOOperations.getColourFromHexString(colours[88]));
        jLayeredPane1.moveToBack(jPanelColour089);
    }

    //load colours to physical keyboard
    private void loadColoursToPhysicalKeyboard(String[] colours) {
        gKeyboard.setProfile(convertG410ColourMapToStandard(colours));
    }

    //get colours from virtual keyboard
    private String[] getColoursFromVirtualKeyboard() {
        String[] colours = new String[keyCount];
        colours[0] = IOOperations.getHexStringFromColour(jPanelColour001.getBackground());
        colours[1] = IOOperations.getHexStringFromColour(jPanelColour002.getBackground());
        colours[2] = IOOperations.getHexStringFromColour(jPanelColour003.getBackground());
        colours[3] = IOOperations.getHexStringFromColour(jPanelColour004.getBackground());
        colours[4] = IOOperations.getHexStringFromColour(jPanelColour005.getBackground());
        colours[5] = IOOperations.getHexStringFromColour(jPanelColour006.getBackground());
        colours[6] = IOOperations.getHexStringFromColour(jPanelColour007.getBackground());
        colours[7] = IOOperations.getHexStringFromColour(jPanelColour008.getBackground());
        colours[8] = IOOperations.getHexStringFromColour(jPanelColour009.getBackground());
        colours[9] = IOOperations.getHexStringFromColour(jPanelColour010.getBackground());
        colours[10] = IOOperations.getHexStringFromColour(jPanelColour011.getBackground());
        colours[11] = IOOperations.getHexStringFromColour(jPanelColour012.getBackground());
        colours[12] = IOOperations.getHexStringFromColour(jPanelColour013.getBackground());
        colours[13] = IOOperations.getHexStringFromColour(jPanelColour014.getBackground());
        colours[14] = IOOperations.getHexStringFromColour(jPanelColour015.getBackground());
        colours[15] = IOOperations.getHexStringFromColour(jPanelColour016.getBackground());
        colours[16] = IOOperations.getHexStringFromColour(jPanelColour017.getBackground());
        colours[17] = IOOperations.getHexStringFromColour(jPanelColour018.getBackground());
        colours[18] = IOOperations.getHexStringFromColour(jPanelColour019.getBackground());
        colours[19] = IOOperations.getHexStringFromColour(jPanelColour020.getBackground());
        colours[20] = IOOperations.getHexStringFromColour(jPanelColour021.getBackground());
        colours[21] = IOOperations.getHexStringFromColour(jPanelColour022.getBackground());
        colours[22] = IOOperations.getHexStringFromColour(jPanelColour023.getBackground());
        colours[23] = IOOperations.getHexStringFromColour(jPanelColour024.getBackground());
        colours[24] = IOOperations.getHexStringFromColour(jPanelColour025.getBackground());
        colours[25] = IOOperations.getHexStringFromColour(jPanelColour026.getBackground());
        colours[26] = IOOperations.getHexStringFromColour(jPanelColour027.getBackground());
        colours[27] = IOOperations.getHexStringFromColour(jPanelColour028.getBackground());
        colours[28] = IOOperations.getHexStringFromColour(jPanelColour029.getBackground());
        colours[29] = IOOperations.getHexStringFromColour(jPanelColour030.getBackground());
        colours[30] = IOOperations.getHexStringFromColour(jPanelColour031.getBackground());
        colours[31] = IOOperations.getHexStringFromColour(jPanelColour032.getBackground());
        colours[32] = IOOperations.getHexStringFromColour(jPanelColour033.getBackground());
        colours[33] = IOOperations.getHexStringFromColour(jPanelColour034.getBackground());
        colours[34] = IOOperations.getHexStringFromColour(jPanelColour035.getBackground());
        colours[35] = IOOperations.getHexStringFromColour(jPanelColour036.getBackground());
        colours[36] = IOOperations.getHexStringFromColour(jPanelColour037.getBackground());
        colours[37] = IOOperations.getHexStringFromColour(jPanelColour034.getBackground());
        colours[38] = IOOperations.getHexStringFromColour(jPanelColour035.getBackground());
        colours[39] = IOOperations.getHexStringFromColour(jPanelColour036.getBackground());
        colours[40] = IOOperations.getHexStringFromColour(jPanelColour037.getBackground());
        colours[41] = IOOperations.getHexStringFromColour(jPanelColour038.getBackground());
        colours[42] = IOOperations.getHexStringFromColour(jPanelColour039.getBackground());
        colours[43] = IOOperations.getHexStringFromColour(jPanelColour040.getBackground());
        colours[44] = IOOperations.getHexStringFromColour(jPanelColour041.getBackground());
        colours[45] = IOOperations.getHexStringFromColour(jPanelColour042.getBackground());
        colours[46] = IOOperations.getHexStringFromColour(jPanelColour043.getBackground());
        colours[47] = IOOperations.getHexStringFromColour(jPanelColour044.getBackground());
        colours[48] = IOOperations.getHexStringFromColour(jPanelColour045.getBackground());
        colours[49] = IOOperations.getHexStringFromColour(jPanelColour046.getBackground());
        colours[50] = IOOperations.getHexStringFromColour(jPanelColour047.getBackground());
        colours[51] = IOOperations.getHexStringFromColour(jPanelColour048.getBackground());
        colours[52] = IOOperations.getHexStringFromColour(jPanelColour049.getBackground());
        colours[53] = IOOperations.getHexStringFromColour(jPanelColour050.getBackground());
        colours[54] = IOOperations.getHexStringFromColour(jPanelColour055.getBackground());
        colours[55] = IOOperations.getHexStringFromColour(jPanelColour056.getBackground());
        colours[56] = IOOperations.getHexStringFromColour(jPanelColour057.getBackground());
        colours[57] = IOOperations.getHexStringFromColour(jPanelColour058.getBackground());
        colours[58] = IOOperations.getHexStringFromColour(jPanelColour051.getBackground());
        colours[59] = IOOperations.getHexStringFromColour(jPanelColour052.getBackground());
        colours[60] = IOOperations.getHexStringFromColour(jPanelColour053.getBackground());
        colours[61] = IOOperations.getHexStringFromColour(jPanelColour054.getBackground());
        colours[62] = IOOperations.getHexStringFromColour(jPanelColour055.getBackground());
        colours[63] = IOOperations.getHexStringFromColour(jPanelColour056.getBackground());
        colours[64] = IOOperations.getHexStringFromColour(jPanelColour057.getBackground());
        colours[65] = IOOperations.getHexStringFromColour(jPanelColour058.getBackground());
        colours[66] = IOOperations.getHexStringFromColour(jPanelColour059.getBackground());
        colours[67] = IOOperations.getHexStringFromColour(jPanelColour060.getBackground());
        colours[68] = IOOperations.getHexStringFromColour(jPanelColour061.getBackground());
        colours[69] = IOOperations.getHexStringFromColour(jPanelColour062.getBackground());
        colours[70] = IOOperations.getHexStringFromColour(jPanelColour063.getBackground());
        colours[71] = IOOperations.getHexStringFromColour(jPanelColour072.getBackground());
        colours[72] = IOOperations.getHexStringFromColour(jPanelColour073.getBackground());
        colours[73] = IOOperations.getHexStringFromColour(jPanelColour074.getBackground());
        colours[74] = IOOperations.getHexStringFromColour(jPanelColour064.getBackground());
        colours[75] = IOOperations.getHexStringFromColour(jPanelColour065.getBackground());
        colours[76] = IOOperations.getHexStringFromColour(jPanelColour066.getBackground());
        colours[77] = IOOperations.getHexStringFromColour(jPanelColour067.getBackground());
        colours[78] = IOOperations.getHexStringFromColour(jPanelColour068.getBackground());
        colours[79] = IOOperations.getHexStringFromColour(jPanelColour069.getBackground());
        colours[80] = IOOperations.getHexStringFromColour(jPanelColour070.getBackground());
        colours[81] = IOOperations.getHexStringFromColour(jPanelColour071.getBackground());
        colours[82] = IOOperations.getHexStringFromColour(jPanelColour072.getBackground());
        colours[83] = IOOperations.getHexStringFromColour(jPanelColour073.getBackground());
        colours[84] = IOOperations.getHexStringFromColour(jPanelColour074.getBackground());
        colours[85] = IOOperations.getHexStringFromColour(jPanelColour075.getBackground());
        colours[86] = IOOperations.getHexStringFromColour(jPanelColour087.getBackground());
        colours[87] = IOOperations.getHexStringFromColour(jPanelColour088.getBackground());
        colours[88] = IOOperations.getHexStringFromColour(jPanelColour089.getBackground());
        return colours;
    }

    //convert colour array to match standard array
    private String[] convertG410ColourMapToStandard(String[] colours) {
        String[] convertedColours = new String[127];
        convertedColours[0] = "000000";
        convertedColours[1] = "000000";
        convertedColours[2] = "000000";
        convertedColours[3] = "000000";
        convertedColours[4] = "000000";
        convertedColours[5] = "000000";
        convertedColours[6] = "000000";
        convertedColours[7] = "000000";
        convertedColours[8] = "000000";
        convertedColours[9] = "000000";
        convertedColours[10] = "000000";
        convertedColours[11] = "000000";
        convertedColours[12] = "000000";
        convertedColours[13] = "000000";
        convertedColours[14] = colours[87];
        convertedColours[15] = colours[88];
        convertedColours[16] = "000000";
        convertedColours[17] = "000000";
        convertedColours[18] = "000000";
        convertedColours[19] = "000000";
        convertedColours[20] = "000000";
        convertedColours[21] = colours[1];
        convertedColours[22] = colours[2];
        convertedColours[23] = colours[3];
        convertedColours[24] = colours[4];
        convertedColours[25] = colours[5];
        convertedColours[26] = colours[6];
        convertedColours[27] = colours[7];
        convertedColours[28] = colours[8];
        convertedColours[29] = colours[9];
        convertedColours[30] = colours[10];
        convertedColours[31] = colours[11];
        convertedColours[32] = colours[12];
        convertedColours[33] = colours[63];
        convertedColours[34] = colours[76];
        convertedColours[35] = colours[77];
        convertedColours[36] = colours[78];
        convertedColours[37] = colours[80];
        convertedColours[38] = colours[81];
        convertedColours[39] = colours[83];
        convertedColours[40] = colours[74];
        convertedColours[41] = colours[82];
        convertedColours[42] = colours[75];
        convertedColours[43] = colours[84];
        convertedColours[44] = colours[85];
        convertedColours[45] = colours[86];
        convertedColours[46] = "000000";
        convertedColours[47] = "000000";
        convertedColours[48] = "000000";
        convertedColours[49] = "000000";
        convertedColours[50] = "000000";
        convertedColours[51] = "000000";
        convertedColours[52] = "000000";
        convertedColours[53] = "000000";
        convertedColours[54] = "000000";
        convertedColours[55] = "000000";
        convertedColours[56] = "000000";
        convertedColours[57] = "000000";
        convertedColours[58] = "000000";
        convertedColours[59] = "000000";
        convertedColours[60] = "000000";
        convertedColours[61] = "000000";
        convertedColours[62] = "000000";
        convertedColours[63] = colours[0];
        convertedColours[64] = colours[14];
        convertedColours[65] = colours[30];
        convertedColours[66] = colours[47];
        convertedColours[67] = colours[31];
        convertedColours[68] = colours[48];
        convertedColours[69] = colours[32];
        convertedColours[70] = colours[49];
        convertedColours[71] = colours[13];
        convertedColours[72] = colours[15];
        convertedColours[73] = colours[17];
        convertedColours[74] = colours[18];
        convertedColours[75] = colours[19];
        convertedColours[76] = colours[20];
        convertedColours[77] = colours[21];
        convertedColours[78] = colours[22];
        convertedColours[79] = colours[23];
        convertedColours[80] = colours[24];
        convertedColours[81] = colours[25];
        convertedColours[82] = colours[26];
        convertedColours[83] = colours[33];
        convertedColours[84] = colours[50];
        convertedColours[85] = colours[79];
        convertedColours[86] = colours[29];
        convertedColours[87] = colours[62];
        convertedColours[88] = colours[51];
        convertedColours[89] = colours[68];
        convertedColours[90] = colours[66];
        convertedColours[91] = colours[53];
        convertedColours[92] = colours[36];
        convertedColours[93] = colours[54];
        convertedColours[94] = colours[55];
        convertedColours[95] = colours[56];
        convertedColours[96] = colours[41];
        convertedColours[97] = colours[57];
        convertedColours[98] = colours[58];
        convertedColours[99] = colours[59];
        convertedColours[100] = colours[70];
        convertedColours[101] = colours[69];
        convertedColours[102] = colours[42];
        convertedColours[103] = colours[43];
        convertedColours[104] = colours[34];
        convertedColours[105] = colours[37];
        convertedColours[106] = colours[52];
        convertedColours[107] = colours[38];
        convertedColours[108] = colours[40];
        convertedColours[109] = colours[67];
        convertedColours[110] = colours[35];
        convertedColours[111] = colours[65];
        convertedColours[112] = colours[39];
        convertedColours[113] = colours[64];
        convertedColours[114] = colours[16];
        convertedColours[115] = colours[27];
        convertedColours[116] = colours[28];
        convertedColours[117] = colours[44];
        convertedColours[118] = colours[45];
        convertedColours[119] = colours[46];
        convertedColours[120] = colours[60];
        convertedColours[121] = colours[61];
        convertedColours[122] = "000000";
        convertedColours[123] = "000000";
        convertedColours[124] = colours[71];
        convertedColours[125] = colours[72];
        convertedColours[126] = colours[73];
        return convertedColours;
    }

    //convert standard colour array to G410 colour array
    private String[] convertStandardColourMapToG410(String[] colours) {
        String[] convertedColours = new String[keyCount];
        convertedColours[87] = colours[14];
        convertedColours[88] = colours[15];
        convertedColours[1] = colours[21];
        convertedColours[2] = colours[22];
        convertedColours[3] = colours[23];
        convertedColours[4] = colours[24];
        convertedColours[5] = colours[25];
        convertedColours[6] = colours[26];
        convertedColours[7] = colours[27];
        convertedColours[8] = colours[28];
        convertedColours[9] = colours[29];
        convertedColours[10] = colours[30];
        convertedColours[11] = colours[31];
        convertedColours[12] = colours[32];
        convertedColours[63] = colours[33];
        convertedColours[76] = colours[34];
        convertedColours[77] = colours[35];
        convertedColours[78] = colours[36];
        convertedColours[80] = colours[37];
        convertedColours[81] = colours[38];
        convertedColours[83] = colours[39];
        convertedColours[74] = colours[40];
        convertedColours[82] = colours[41];
        convertedColours[75] = colours[42];
        convertedColours[84] = colours[43];
        convertedColours[85] = colours[44];
        convertedColours[86] = colours[45];
        convertedColours[0] = colours[63];
        convertedColours[14] = colours[64];
        convertedColours[30] = colours[65];
        convertedColours[47] = colours[66];
        convertedColours[31] = colours[67];
        convertedColours[48] = colours[68];
        convertedColours[32] = colours[69];
        convertedColours[49] = colours[70];
        convertedColours[13] = colours[71];
        convertedColours[15] = colours[72];
        convertedColours[17] = colours[73];
        convertedColours[18] = colours[74];
        convertedColours[19] = colours[75];
        convertedColours[20] = colours[76];
        convertedColours[21] = colours[77];
        convertedColours[22] = colours[78];
        convertedColours[23] = colours[79];
        convertedColours[24] = colours[80];
        convertedColours[25] = colours[81];
        convertedColours[26] = colours[82];
        convertedColours[33] = colours[83];
        convertedColours[50] = colours[84];
        convertedColours[79] = colours[85];
        convertedColours[29] = colours[86];
        convertedColours[62] = colours[87];
        convertedColours[51] = colours[88];
        convertedColours[68] = colours[89];
        convertedColours[66] = colours[90];
        convertedColours[53] = colours[91];
        convertedColours[36] = colours[92];
        convertedColours[54] = colours[93];
        convertedColours[55] = colours[94];
        convertedColours[56] = colours[95];
        convertedColours[41] = colours[96];
        convertedColours[57] = colours[97];
        convertedColours[58] = colours[98];
        convertedColours[59] = colours[99];
        convertedColours[70] = colours[100];
        convertedColours[69] = colours[101];
        convertedColours[42] = colours[102];
        convertedColours[43] = colours[103];
        convertedColours[34] = colours[104];
        convertedColours[37] = colours[105];
        convertedColours[52] = colours[106];
        convertedColours[38] = colours[107];
        convertedColours[40] = colours[108];
        convertedColours[67] = colours[109];
        convertedColours[35] = colours[110];
        convertedColours[65] = colours[111];
        convertedColours[39] = colours[112];
        convertedColours[64] = colours[113];
        convertedColours[16] = colours[114];
        convertedColours[27] = colours[115];
        convertedColours[28] = colours[116];
        convertedColours[44] = colours[117];
        convertedColours[45] = colours[118];
        convertedColours[46] = colours[119];
        convertedColours[60] = colours[120];
        convertedColours[61] = colours[121];
        convertedColours[71] = colours[124];
        convertedColours[72] = colours[125];
        convertedColours[73] = colours[126];
        return convertedColours;
    }

    //convert G410 colour array into a standard array for both colours and their keys
    private String[][] convertG410ColourMapToStandardKeyColourMap(String[] colours) {
        String[][] keyColourMap = new String[2][127];
        String[] convertedColours = new String[127];
        convertedColours = convertG410ColourMapToStandard(colours);
        for (int i = 0; i < 127; i++) {
            keyColourMap[0][i] = Keyboard.Key.values()[i].toString();
            keyColourMap[1][i] = convertedColours[i];
        }
        return keyColourMap;
    }

    //convert standard colour key array into a G410 colour array
    private String[] convertStandardKeyColourMapToG410ColourMap(String[][] keyColourMap) {
        String[] colourMap = new String[keyCount];
        String[] tempStandardColourMap = new String[127];
        for (int i = 0; i < 127; i++) {
            tempStandardColourMap[i] = keyColourMap[1][i];
        }
        colourMap = convertStandardColourMapToG410(tempStandardColourMap);
        return colourMap;
    }

    //calculate the average brightness of all set colours on keyboard,
    //this is done so that the brightness slider can be positioned as accurate
    //as possible
    private int getAverageBrightness() {
        int brightness = 50;
        String[] colours = new String[keyCount];
        colours = convertStandardKeyColourMapToG410ColourMap(gKeyboard.getKeyColourMap());
        int sum = 0;
        float[] hsb = new float[3];
        Color colour;
        for (int i = 0; i < keyCount; i++) {
            colour = IOOperations.getColourFromHexString(colours[i]);
            Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);
            sum += Math.round(100 * Double.parseDouble(new Float(hsb[2]).toString()));
        }
        brightness = sum / keyCount;
        return brightness;
    }

    //set brightness of the current profile
    private void setBrightness(int brightness) {
        String[] colours = new String[keyCount];
        colours = getColoursFromVirtualKeyboard();
        float[] hsb = new float[3];
        Color colour;
        for (int i = 0; i < keyCount; i++) {
            colour = IOOperations.getColourFromHexString(colours[i]);
            Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);
            if (brightness == 0) {
                hsb[2] = (float) 10 / 100;
            } else {
                hsb[2] = (float) brightness / 100;
            }
            colours[i] = String.format("%06X", (0xFFFFFF & Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])));
        }
        loadColours(colours);
    }
}

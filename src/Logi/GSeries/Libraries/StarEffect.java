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

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Mohamad Saada
 */
public class StarEffect extends Thread {

    private int keyCount = 0;
    public static Keyboard.KeyboardModel keyboardModel = Keyboard.KeyboardModel.NoLogiKeyboard;
    private int starSpeed = 2000;
    public static String skyColour = "000000";
    public static String starColour = "FFFF00";
    public static String[] colours;
    public static String[][] keyColourMap = new String[2][14];
    private Timer timer1 = null;
    private Timer star1 = null;
    private Timer star2 = null;
    private Timer star3 = null;
    private Timer star4 = null;
    private Timer star5 = null;
    private Timer star6 = null;
    private Timer star7 = null;
    private Timer star8 = null;
    private Timer star9 = null;
    private Timer star10 = null;
    private Timer star11 = null;
    private Timer star12 = null;
    private Timer star13 = null;
    private Timer star14 = null;
    private JPanel[] keyboardJPanels = null;
    private JLayeredPane jLayeredPane = null;
    public static boolean closing = false;

    public void run() {
        //Begin Thread
        System.out.println("Started star effect thread");
        closing = false;
        //Fill array with random elements so that it doesn't have empty elements
        //when sent to the keyboard
        keyColourMap[0][0] = "b";
        keyColourMap[1][0] = skyColour;
        keyColourMap[0][1] = "p";
        keyColourMap[1][1] = skyColour;
        keyColourMap[0][2] = "n1";
        keyColourMap[1][2] = skyColour;
        keyColourMap[0][3] = "n2";
        keyColourMap[1][3] = skyColour;
        keyColourMap[0][4] = "n3";
        keyColourMap[1][4] = skyColour;
        keyColourMap[0][5] = "n4";
        keyColourMap[1][5] = skyColour;
        keyColourMap[0][6] = "n5";
        keyColourMap[1][6] = skyColour;
        keyColourMap[0][7] = "n6";
        keyColourMap[1][7] = skyColour;
        keyColourMap[0][8] = "n7";
        keyColourMap[1][8] = skyColour;
        keyColourMap[0][9] = "n8";
        keyColourMap[1][9] = skyColour;
        keyColourMap[0][10] = "n9";
        keyColourMap[1][10] = skyColour;
        keyColourMap[0][11] = "n0";
        keyColourMap[1][11] = skyColour;
        keyColourMap[0][12] = "a";
        keyColourMap[1][12] = skyColour;
        keyColourMap[0][13] = "z";
        keyColourMap[1][13] = skyColour;

        TimerTask starTask1 = new StarTimerTask(1, keyCount, keyboardModel);
        TimerTask starTask2 = new StarTimerTask(2, keyCount, keyboardModel);
        TimerTask starTask3 = new StarTimerTask(3, keyCount, keyboardModel);
        TimerTask starTask4 = new StarTimerTask(4, keyCount, keyboardModel);
        TimerTask starTask5 = new StarTimerTask(5, keyCount, keyboardModel);
        TimerTask starTask6 = new StarTimerTask(6, keyCount, keyboardModel);
        TimerTask starTask7 = new StarTimerTask(7, keyCount, keyboardModel);
        TimerTask starTask8 = new StarTimerTask(8, keyCount, keyboardModel);
        TimerTask starTask9 = new StarTimerTask(9, keyCount, keyboardModel);
        TimerTask starTask10 = new StarTimerTask(10, keyCount, keyboardModel);
        TimerTask starTask11 = new StarTimerTask(11, keyCount, keyboardModel);
        TimerTask starTask12 = new StarTimerTask(12, keyCount, keyboardModel);
        TimerTask starTask13 = new StarTimerTask(13, keyCount, keyboardModel);
        TimerTask starTask14 = new StarTimerTask(14, keyCount, keyboardModel);

        star1 = new Timer();
        star2 = new Timer();
        star3 = new Timer();
        star4 = new Timer();
        star5 = new Timer();
        star6 = new Timer();
        star7 = new Timer();
        star8 = new Timer();
        star9 = new Timer();
        star10 = new Timer();
        star11 = new Timer();
        star12 = new Timer();
        star13 = new Timer();
        star14 = new Timer();

        star1.schedule(starTask1, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 3) + 3) * 1000 / 40);
        star2.schedule(starTask2, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star3.schedule(starTask3, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 3) + 3) * 1000 / 40);
        star4.schedule(starTask4, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star5.schedule(starTask5, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 3) + 3) * 1000 / 40);
        star6.schedule(starTask6, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star7.schedule(starTask7, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 3) + 3) * 1000 / 40);
        star8.schedule(starTask8, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star9.schedule(starTask9, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star10.schedule(starTask10, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star11.schedule(starTask11, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star12.schedule(starTask12, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star13.schedule(starTask13, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);
        star14.schedule(starTask14, ((int) (Math.random() * 1000)) * 10, ((int) (Math.random() * 2) + 3) * 1000 / 40);

        TimerTask timerTask1;
        if ((this.keyboardJPanels != null) && (jLayeredPane != null)) {
            timerTask1 = new WritingToKeyboardTimerTask(keyboardJPanels, jLayeredPane, keyboardModel);
        } else {
            timerTask1 = new WritingToKeyboardTimerTask();
        }
        timer1 = new Timer();
        timer1.schedule(timerTask1, 0, 1);
    }

    public void terminate() {
        //Inform the writer timer that thread is terminating so that
        //program doesn't shut while keyboard interface is still claimed
        closing = true;
        try {
            this.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(StarEffect.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (timer1 != null) {
            timer1.cancel();
            timer1.purge();
        }
        if (star1 != null) {
            star1.cancel();
            star1.purge();
        }
        if (star2 != null) {
            star2.cancel();
            star2.purge();
        }
        if (star3 != null) {
            star3.cancel();
            star3.purge();
        }
        if (star4 != null) {
            star4.cancel();
            star4.purge();
        }
        if (star5 != null) {
            star5.cancel();
            star5.purge();
        }
        if (star6 != null) {
            star6.cancel();
            star6.purge();
        }
        if (star7 != null) {
            star7.cancel();
            star7.purge();
        }
        if (star8 != null) {
            star8.cancel();
            star8.purge();
        }
        if (star9 != null) {
            star9.cancel();
            star9.purge();
        }
        if (star10 != null) {
            star10.cancel();
            star10.purge();
        }
        if (star11 != null) {
            star11.cancel();
            star11.purge();
        }
        if (star12 != null) {
            star12.cancel();
            star12.purge();
        }
        if (star13 != null) {
            star13.cancel();
            star13.purge();
        }
        if (star14 != null) {
            star14.cancel();
            star14.purge();
        }
        System.out.println("Ended star effect thread");
    }

    public void setStarColour(String starColour) {
        this.starColour = starColour;
    }

    public String getStarColour() {
        return starColour;
    }

    public void setSkyColour(String skyColour) {
        this.skyColour = skyColour;
    }

    public String getSkyColour() {
        return skyColour;
    }

    public void setKeyboardJpanels(JPanel[] jPanels, JLayeredPane jLayeredPane) {
        this.keyboardJPanels = jPanels;
        this.jLayeredPane = jLayeredPane;
    }

    public void setKeyboardModel(Keyboard.KeyboardModel model) {
        this.keyboardModel = model;
        switch (model) {
            case Logi910SpectrumUSQWERTY:
            case Logi910SparkUSQWERTY:
            case Logi910SpectrumUKQWERTY:
            case Logi910SparkUKQWERTY:
                this.keyCount = 116;
                colours = new String[116];
                for (int i = 0; i < 114; i++) {
                    colours[i] = skyColour;
                }
                colours[114] = starColour;
                colours[115] = starColour;
                break;
            case Logi810SpectrumUSQWERTY:
                this.keyCount = 115;
                colours = new String[115];
                for (int i = 0; i < 105; i++) {
                    colours[i] = skyColour;
                }
                for (int i = 108; i < 114; i++) {
                    colours[i] = skyColour;
                }
                colours[114] = starColour;
                break;
            case Logi810SpectrumUKQWERTY:
                break;
            case Logi610OrionUSQWERTY:
                this.keyCount = 115;
                colours = new String[115];
                for (int i = 0; i < 105; i++) {
                    colours[i] = skyColour;
                }
                for (int i = 108; i < 114; i++) {
                    colours[i] = skyColour;
                }
                colours[114] = starColour;
                break;
            case Logi610OrionUKQWERTY:
                break;
            case Logi410AtlasSpectrumUSQWERTY:
                this.keyCount = 89;
                colours = new String[89];
                for (int i = 0; i < 89; i++) {
                    colours[i] = skyColour;
                }
                break;
            case Logi410AtlasSpectrumUKQWERTY:
                break;
            default:
                break;
        }
    }

    public void setStarSpeed(int speed) {
        this.starSpeed = speed;
    }

}

/////////////////////////////////////////////
//                                         //
//                                         //
//                                         //
//            Star TimerTask               //
//                                         //
//                                         //
//                                         //
/////////////////////////////////////////////
class StarTimerTask extends TimerTask {

    int key = 0;
    int countDown = 0;
    boolean firstTime = true;
    int slotNumber = 0;
    int keyCount = 0;
    Keyboard.KeyboardModel keyboardModel = Keyboard.KeyboardModel.NoLogiKeyboard;

    StarTimerTask(int slotNumber, int keyCount, Keyboard.KeyboardModel model) {
        this.slotNumber = slotNumber - 1;
        this.keyCount = keyCount;
        this.keyboardModel = model;
    }

    @Override
    public void run() {
        if (firstTime) {
            firstTime = false;
            boolean searching;
            String choosenKey = "";
            searching = true;
            while (searching) {
                boolean matchFound = false;
                switch (keyboardModel) {
                    case Logi910SpectrumUSQWERTY:
                    case Logi910SparkUSQWERTY:
                        this.keyCount = 115;
                        choosenKey = Keyboard.KeyG910USQWERTY.values()[(int) (Math.random() * (Keyboard.KeyG910USQWERTY.values().length))].toString();
                        matchFound = false;
                        for (int j = 0; j < 14; j++) {
                            if (StarEffect.keyColourMap[0][j].equals(choosenKey) || choosenKey.equals("logo") || choosenKey.equals("badge")) {
                                matchFound = true;
                            }
                        }
                        if (!matchFound) {
                            searching = false;
                        }
                        break;
                    case Logi910SpectrumUKQWERTY:
                    case Logi910SparkUKQWERTY:
                        this.keyCount = 116;
                        choosenKey = Keyboard.KeyG910UKQWERTY.values()[(int) (Math.random() * (Keyboard.KeyG910UKQWERTY.values().length))].toString();
                        matchFound = false;
                        for (int j = 0; j < 14; j++) {
                            if (StarEffect.keyColourMap[0][j].equals(choosenKey) || choosenKey.equals("logo") || choosenKey.equals("badge")) {
                                matchFound = true;
                            }
                        }
                        if (!matchFound) {
                            searching = false;
                        }
                        break;
                    case Logi810SpectrumUSQWERTY:
                        this.keyCount = 115;
                        choosenKey = Keyboard.KeyG810USQWERTY.values()[(int) (Math.random() * (Keyboard.KeyG810USQWERTY.values().length))].toString();
                        matchFound = false;
                        for (int j = 0; j < 14; j++) {
                            if (StarEffect.keyColourMap[0][j].equals(choosenKey) || choosenKey.equals("logo") || choosenKey.equals("caps") || choosenKey.equals("num") || choosenKey.equals("scroll")) {
                                matchFound = true;
                            }
                        }
                        if (!matchFound) {
                            searching = false;
                        }
                        break;
                    case Logi810SpectrumUKQWERTY:
                        break;
                    case Logi610OrionUSQWERTY:
                        this.keyCount = 115;
                        choosenKey = Keyboard.KeyG610USQWERTY.values()[(int) (Math.random() * (Keyboard.KeyG610USQWERTY.values().length))].toString();
                        matchFound = false;
                        for (int j = 0; j < 14; j++) {
                            if (StarEffect.keyColourMap[0][j].equals(choosenKey) || choosenKey.equals("logo") || choosenKey.equals("caps") || choosenKey.equals("num") || choosenKey.equals("scroll")) {
                                matchFound = true;
                            }
                        }
                        if (!matchFound) {
                            searching = false;
                        }
                        break;
                    case Logi610OrionUKQWERTY:
                        break;
                    case Logi410AtlasSpectrumUSQWERTY:
                        this.keyCount = 89;
                        choosenKey = Keyboard.KeyG410USQWERTY.values()[(int) (Math.random() * (Keyboard.KeyG410USQWERTY.values().length))].toString();
                        matchFound = false;
                        for (int j = 0; j < 14; j++) {
                            if (StarEffect.keyColourMap[0][j].equals(choosenKey)) {
                                matchFound = true;
                            }
                        }
                        if (!matchFound) {
                            searching = false;
                        }
                        break;
                    case Logi410AtlasSpectrumUKQWERTY:
                        break;
                    default:
                        break;
                }
            }
            StarEffect.keyColourMap[0][slotNumber] = choosenKey;
        }
        if (countDown <= 20) {
            double ratio = (double) (countDown * 5) / (double) 100;
            int red = (int) Math.abs((ratio * getColourFromHexString(StarEffect.starColour).getRed()) + ((1 - ratio) * getColourFromHexString(StarEffect.skyColour).getRed()));
            int green = (int) Math.abs((ratio * getColourFromHexString(StarEffect.starColour).getGreen()) + ((1 - ratio) * getColourFromHexString(StarEffect.skyColour).getGreen()));
            int blue = (int) Math.abs((ratio * getColourFromHexString(StarEffect.starColour).getBlue()) + ((1 - ratio) * getColourFromHexString(StarEffect.skyColour).getBlue()));
            StarEffect.keyColourMap[1][slotNumber] = String.format("%02x%02x%02x", red, green, blue);
        } else {
            double ratio = (double) ((40 - countDown) * 5) / (double) 100;
            int red = (int) Math.abs((ratio * getColourFromHexString(StarEffect.starColour).getRed()) + ((1 - ratio) * getColourFromHexString(StarEffect.skyColour).getRed()));
            int green = (int) Math.abs((ratio * getColourFromHexString(StarEffect.starColour).getGreen()) + ((1 - ratio) * getColourFromHexString(StarEffect.skyColour).getGreen()));
            int blue = (int) Math.abs((ratio * getColourFromHexString(StarEffect.starColour).getBlue()) + ((1 - ratio) * getColourFromHexString(StarEffect.skyColour).getBlue()));
            StarEffect.keyColourMap[1][slotNumber] = String.format("%02x%02x%02x", red, green, blue);
        }
        if (countDown == 40) {
            countDown = 0;
            firstTime = true;
        } else {
            countDown++;
        }
    }

    private Color getColourFromHexString(String colourHex) {
        return new Color(
                Integer.valueOf(colourHex.substring(0, 2), 16),
                Integer.valueOf(colourHex.substring(2, 4), 16),
                Integer.valueOf(colourHex.substring(4, 6), 16));
    }
}

/////////////////////////////////////////////
//                                         //
//                                         //
//                                         //
//           Writing To Keyboard           //
//                TimerTask                //
//                                         //
//                                         //
//                                         //
/////////////////////////////////////////////
class WritingToKeyboardTimerTask extends TimerTask {

    private GKeyboard gKeyboard = new GKeyboard(StarEffect.keyboardModel);
    private int keyCount;
    private Keyboard.KeyboardModel keyboardModel = Keyboard.KeyboardModel.NoLogiKeyboard;
    JPanel[] keyboardJPanels = null;
    JLayeredPane jLayeredPane = null;

    WritingToKeyboardTimerTask() {
    }

    WritingToKeyboardTimerTask(JPanel[] keyboardJPanels, JLayeredPane jLayeredPane, Keyboard.KeyboardModel model) {
        this.keyboardJPanels = keyboardJPanels;
        this.jLayeredPane = jLayeredPane;
        this.keyboardModel = model;
    }

    @Override
    public void run() {
        if (!StarEffect.closing) {
            loadColoursToKeyboard(StarEffect.keyColourMap);
        }
    }

    private void loadColoursToKeyboard(String[][] keyColourMap) {
        String[] colours = new String[127];
        for (int i = 0; i < colours.length; i++) {
            colours[i] = StarEffect.skyColour;
        }
        colours[0] = StarEffect.starColour;
        colours[1] = StarEffect.starColour;
        for (int j = 0; j < 14; j++) {
            colours[Keyboard.Key.valueOf(keyColourMap[0][j]).ordinal()] = keyColourMap[1][j];
        }
        if ((this.keyboardJPanels != null) && (jLayeredPane != null)) {
            String[] convertedColours = null;
            switch (this.keyboardModel) {
                case Logi910SpectrumUSQWERTY:
                case Logi910SparkUSQWERTY:
                case Logi910SpectrumUKQWERTY:
                case Logi910SparkUKQWERTY:
                    this.keyCount = 116;
                    convertedColours = convertStandardColourMapToG910(colours);
                    break;
                case Logi810SpectrumUSQWERTY:
                    this.keyCount = 115;
                    convertedColours = convertStandardColourMapToG810USQWERTY(colours);
                    break;
                case Logi810SpectrumUKQWERTY:
                    break;
                case Logi610OrionUSQWERTY:
                    this.keyCount = 115;
                    convertedColours = convertStandardColourMapToG610USQWERTY(colours);
                    break;
                case Logi610OrionUKQWERTY:
                    break;
                case Logi410AtlasSpectrumUSQWERTY:
                    this.keyCount = 89;
                    convertedColours = convertStandardColourMapToG410USQWERTY(colours);
                    break;
                case Logi410AtlasSpectrumUKQWERTY:
                    break;
                default:
                    convertedColours = new String[127];
                    return;
            }
            if (convertedColours != null) {
                for (int i = 0; i < keyboardJPanels.length; i++) {
                    keyboardJPanels[i].setBackground(getColourFromHexString(convertedColours[i]));
                    jLayeredPane.moveToBack(keyboardJPanels[i]);
                }
            }
        }
        gKeyboard.setProfile(keyColourMap);
    }

    private Color getColourFromHexString(String colourHex) {
        return new Color(
                Integer.valueOf(colourHex.substring(0, 2), 16),
                Integer.valueOf(colourHex.substring(2, 4), 16),
                Integer.valueOf(colourHex.substring(4, 6), 16));
    }

    //convert standard colour array to G910 colour array
    private String[] convertStandardColourMapToG910(String[] colours) {
        String[] convertedColours = new String[116];
        convertedColours[114] = colours[0];
        convertedColours[115] = colours[1];
        convertedColours[105] = colours[2];
        convertedColours[106] = colours[3];
        convertedColours[107] = colours[4];
        convertedColours[108] = colours[5];
        convertedColours[109] = colours[6];
        convertedColours[110] = colours[7];
        convertedColours[111] = colours[8];
        convertedColours[112] = colours[9];
        convertedColours[113] = colours[10];
        convertedColours[58] = colours[11];
        convertedColours[33] = colours[12];
        convertedColours[14] = colours[13];
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
        convertedColours[74] = colours[33];
        convertedColours[92] = colours[34];
        convertedColours[93] = colours[35];
        convertedColours[94] = colours[36];
        convertedColours[96] = colours[37];
        convertedColours[97] = colours[38];
        convertedColours[99] = colours[39];
        convertedColours[86] = colours[40];
        convertedColours[98] = colours[41];
        convertedColours[87] = colours[42];
        convertedColours[100] = colours[43];
        convertedColours[101] = colours[44];
        convertedColours[102] = colours[45];
        convertedColours[88] = colours[46];
        convertedColours[89] = colours[47];
        convertedColours[90] = colours[48];
        convertedColours[71] = colours[49];
        convertedColours[72] = colours[50];
        convertedColours[73] = colours[51];
        convertedColours[54] = colours[52];
        convertedColours[55] = colours[53];
        convertedColours[56] = colours[54];
        convertedColours[103] = colours[55];
        convertedColours[104] = colours[56];
        convertedColours[91] = colours[57];
        convertedColours[57] = colours[58];
        convertedColours[36] = colours[59];
        convertedColours[35] = colours[60];
        convertedColours[34] = colours[61];
        convertedColours[33] = colours[62];
        convertedColours[0] = colours[63];
        convertedColours[14] = colours[64];
        convertedColours[30] = colours[65];
        convertedColours[51] = colours[66];
        convertedColours[31] = colours[67];
        convertedColours[52] = colours[68];
        convertedColours[32] = colours[69];
        convertedColours[53] = colours[70];
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
        convertedColours[37] = colours[83];
        convertedColours[58] = colours[84];
        convertedColours[95] = colours[85];
        convertedColours[29] = colours[86];
        convertedColours[50] = colours[87];
        convertedColours[59] = colours[88];
        convertedColours[80] = colours[89];
        convertedColours[78] = colours[90];
        convertedColours[61] = colours[91];
        convertedColours[40] = colours[92];
        convertedColours[62] = colours[93];
        convertedColours[63] = colours[94];
        convertedColours[64] = colours[95];
        convertedColours[45] = colours[96];
        convertedColours[65] = colours[97];
        convertedColours[66] = colours[98];
        convertedColours[67] = colours[99];
        convertedColours[82] = colours[100];
        convertedColours[81] = colours[101];
        convertedColours[46] = colours[102];
        convertedColours[47] = colours[103];
        convertedColours[38] = colours[104];
        convertedColours[41] = colours[105];
        convertedColours[60] = colours[106];
        convertedColours[42] = colours[107];
        convertedColours[44] = colours[108];
        convertedColours[79] = colours[109];
        convertedColours[39] = colours[110];
        convertedColours[77] = colours[111];
        convertedColours[43] = colours[112];
        convertedColours[76] = colours[113];
        convertedColours[16] = colours[114];
        convertedColours[27] = colours[115];
        convertedColours[28] = colours[116];
        convertedColours[48] = colours[117];
        convertedColours[49] = colours[118];
        convertedColours[68] = colours[120];
        convertedColours[69] = colours[121];
        convertedColours[70] = colours[122];
        convertedColours[75] = colours[123];
        convertedColours[83] = colours[124];
        convertedColours[84] = colours[125];
        convertedColours[85] = colours[126];
        return convertedColours;
    }

    //convert standard colour array to G810 US QWERTY colour array
    private String[] convertStandardColourMapToG810USQWERTY(String[] colours) {
        String[] convertedColours = new String[keyCount];
        convertedColours[114] = colours[0];
        convertedColours[105] = colours[11];
        convertedColours[104] = colours[12];
        convertedColours[106] = colours[13];
        convertedColours[107] = colours[14];
        convertedColours[108] = colours[15];
        convertedColours[109] = colours[16];
        convertedColours[110] = colours[17];
        convertedColours[111] = colours[18];
        convertedColours[112] = colours[19];
        convertedColours[113] = colours[20];
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
        convertedColours[74] = colours[33];
        convertedColours[91] = colours[34];
        convertedColours[92] = colours[35];
        convertedColours[93] = colours[36];
        convertedColours[95] = colours[37];
        convertedColours[96] = colours[38];
        convertedColours[98] = colours[39];
        convertedColours[85] = colours[40];
        convertedColours[97] = colours[41];
        convertedColours[86] = colours[42];
        convertedColours[99] = colours[43];
        convertedColours[100] = colours[44];
        convertedColours[101] = colours[45];
        convertedColours[87] = colours[46];
        convertedColours[88] = colours[47];
        convertedColours[89] = colours[48];
        convertedColours[71] = colours[49];
        convertedColours[72] = colours[50];
        convertedColours[73] = colours[51];
        convertedColours[54] = colours[52];
        convertedColours[55] = colours[53];
        convertedColours[56] = colours[54];
        convertedColours[102] = colours[55];
        convertedColours[103] = colours[56];
        convertedColours[90] = colours[57];
        convertedColours[57] = colours[58];
        convertedColours[36] = colours[59];
        convertedColours[35] = colours[60];
        convertedColours[34] = colours[61];
        convertedColours[33] = colours[62];
        convertedColours[0] = colours[63];
        convertedColours[14] = colours[64];
        convertedColours[30] = colours[65];
        convertedColours[51] = colours[66];
        convertedColours[31] = colours[67];
        convertedColours[52] = colours[68];
        convertedColours[32] = colours[69];
        convertedColours[53] = colours[70];
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
        convertedColours[37] = colours[83];
        convertedColours[58] = colours[84];
        convertedColours[94] = colours[85];
        convertedColours[29] = colours[86];
        convertedColours[70] = colours[87];
        convertedColours[59] = colours[88];
        convertedColours[79] = colours[89];
        convertedColours[77] = colours[90];
        convertedColours[61] = colours[91];
        convertedColours[40] = colours[92];
        convertedColours[62] = colours[93];
        convertedColours[63] = colours[94];
        convertedColours[64] = colours[95];
        convertedColours[45] = colours[96];
        convertedColours[65] = colours[97];
        convertedColours[66] = colours[98];
        convertedColours[67] = colours[99];
        convertedColours[81] = colours[100];
        convertedColours[80] = colours[101];
        convertedColours[46] = colours[102];
        convertedColours[47] = colours[103];
        convertedColours[38] = colours[104];
        convertedColours[41] = colours[105];
        convertedColours[60] = colours[106];
        convertedColours[42] = colours[107];
        convertedColours[44] = colours[108];
        convertedColours[78] = colours[109];
        convertedColours[39] = colours[110];
        convertedColours[76] = colours[111];
        convertedColours[43] = colours[112];
        convertedColours[75] = colours[113];
        convertedColours[16] = colours[114];
        convertedColours[27] = colours[115];
        convertedColours[28] = colours[116];
        convertedColours[48] = colours[117];
        convertedColours[49] = colours[118];
        convertedColours[50] = colours[119];
        convertedColours[68] = colours[120];
        convertedColours[69] = colours[121];
        convertedColours[82] = colours[124];
        convertedColours[83] = colours[125];
        convertedColours[84] = colours[126];
        return convertedColours;
    }

    //convert standard colour array to G610 US QWERTY colour array
    private String[] convertStandardColourMapToG610USQWERTY(String[] colours) {
        String[] convertedColours = new String[keyCount];
        convertedColours[114] = colours[0];
        convertedColours[105] = colours[11];
        convertedColours[104] = colours[12];
        convertedColours[106] = colours[13];
        convertedColours[107] = colours[14];
        convertedColours[108] = colours[15];
        convertedColours[109] = colours[16];
        convertedColours[110] = colours[17];
        convertedColours[111] = colours[18];
        convertedColours[112] = colours[19];
        convertedColours[113] = colours[20];
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
        convertedColours[74] = colours[33];
        convertedColours[91] = colours[34];
        convertedColours[92] = colours[35];
        convertedColours[93] = colours[36];
        convertedColours[95] = colours[37];
        convertedColours[96] = colours[38];
        convertedColours[98] = colours[39];
        convertedColours[85] = colours[40];
        convertedColours[97] = colours[41];
        convertedColours[86] = colours[42];
        convertedColours[99] = colours[43];
        convertedColours[100] = colours[44];
        convertedColours[101] = colours[45];
        convertedColours[87] = colours[46];
        convertedColours[88] = colours[47];
        convertedColours[89] = colours[48];
        convertedColours[71] = colours[49];
        convertedColours[72] = colours[50];
        convertedColours[73] = colours[51];
        convertedColours[54] = colours[52];
        convertedColours[55] = colours[53];
        convertedColours[56] = colours[54];
        convertedColours[102] = colours[55];
        convertedColours[103] = colours[56];
        convertedColours[90] = colours[57];
        convertedColours[57] = colours[58];
        convertedColours[36] = colours[59];
        convertedColours[35] = colours[60];
        convertedColours[34] = colours[61];
        convertedColours[33] = colours[62];
        convertedColours[0] = colours[63];
        convertedColours[14] = colours[64];
        convertedColours[30] = colours[65];
        convertedColours[51] = colours[66];
        convertedColours[31] = colours[67];
        convertedColours[52] = colours[68];
        convertedColours[32] = colours[69];
        convertedColours[53] = colours[70];
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
        convertedColours[37] = colours[83];
        convertedColours[58] = colours[84];
        convertedColours[94] = colours[85];
        convertedColours[29] = colours[86];
        convertedColours[70] = colours[87];
        convertedColours[59] = colours[88];
        convertedColours[79] = colours[89];
        convertedColours[77] = colours[90];
        convertedColours[61] = colours[91];
        convertedColours[40] = colours[92];
        convertedColours[62] = colours[93];
        convertedColours[63] = colours[94];
        convertedColours[64] = colours[95];
        convertedColours[45] = colours[96];
        convertedColours[65] = colours[97];
        convertedColours[66] = colours[98];
        convertedColours[67] = colours[99];
        convertedColours[81] = colours[100];
        convertedColours[80] = colours[101];
        convertedColours[46] = colours[102];
        convertedColours[47] = colours[103];
        convertedColours[38] = colours[104];
        convertedColours[41] = colours[105];
        convertedColours[60] = colours[106];
        convertedColours[42] = colours[107];
        convertedColours[44] = colours[108];
        convertedColours[78] = colours[109];
        convertedColours[39] = colours[110];
        convertedColours[76] = colours[111];
        convertedColours[43] = colours[112];
        convertedColours[75] = colours[113];
        convertedColours[16] = colours[114];
        convertedColours[27] = colours[115];
        convertedColours[28] = colours[116];
        convertedColours[48] = colours[117];
        convertedColours[49] = colours[118];
        convertedColours[50] = colours[119];
        convertedColours[68] = colours[120];
        convertedColours[69] = colours[121];
        convertedColours[82] = colours[124];
        convertedColours[83] = colours[125];
        convertedColours[84] = colours[126];
        return convertedColours;
    }
    
    //convert standard colour array to US QWERTY colour array
    private String[] convertStandardColourMapToG410USQWERTY(String[] colours) {
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
}

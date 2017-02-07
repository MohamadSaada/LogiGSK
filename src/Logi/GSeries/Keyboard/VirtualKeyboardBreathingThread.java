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

import Logi.GSeries.Libraries.Keyboard;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Mohamad Saada
 */
public class VirtualKeyboardBreathingThread extends Thread {

    private JPanel[] keyboardJPanels = null;
    private JLayeredPane jLayeredPane = null;
    public static Keyboard.KeyboardModel keyboardModel = Keyboard.KeyboardModel.NoLogiKeyboard;
    private int speed = 10;
    private Timer timer1 = null;
    private BrathingTimerTask task1 = null;
    private String breathingColour = "ff00ff";

    public void run() {
        //Begin Thread
        timer1 = new Timer();
        task1 = new BrathingTimerTask(breathingColour, keyboardJPanels, jLayeredPane);
        int repeat = (int) 4000 / speed;
        timer1.schedule(task1, 0, repeat);

    }

    public void terminate() {
        if (timer1 != null) {
            timer1.cancel();
            timer1.purge();
        }
    }

    public void setKeyboardJpanels(JPanel[] jPanels, JLayeredPane jLayeredPane) {
        this.keyboardJPanels = jPanels;
        this.jLayeredPane = jLayeredPane;
    }

    public void setSpeed(int speed) {
        System.out.println("Speed not: " + speed);
        int sliderPosition = getSliderFromSpeed(speed);
        System.out.println("Slider position: " + sliderPosition);
        int[] speedArray = new int[]{10, 20, 25, 30, 40, 50, 60, 70, 200, 250, 500};
        this.speed = speedArray[sliderPosition];
        System.out.println("Speed: " + this.speed);
    }

    public void setBreathingColour(String colour) {
        this.breathingColour = colour;
    }

    private int getSliderFromSpeed(int speed) {
        return getIndexOf(new int[]{64, 48, 32, 24, 16, 12, 8, 6, 4, 2, 0}, speed);
    }

    private int getIndexOf(int[] intArray, int element) {
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] == element) {
                return i;
            }
        }
        return -1;
    }

    public void setKeyboardModel(Keyboard.KeyboardModel model) {
        this.keyboardModel = model;
        switch (model) {
            case Logi910SpectrumUSQWERTY:
            case Logi910SparkUSQWERTY:
                break;
            case Logi910SpectrumUKQWERTY:
            case Logi910SparkUKQWERTY:
                break;
            case Logi810SpectrumUSQWERTY:
                break;
            case Logi810SpectrumUKQWERTY:
                break;
            case Logi610OrionUSQWERTY:
                break;
            case Logi610OrionUKQWERTY:
                break;
            case Logi410AtlasSpectrumUSQWERTY:
                break;
            case Logi410AtlasSpectrumUKQWERTY:
                break;
            default:
                break;
        }
    }
}

class BrathingTimerTask extends TimerTask {

    int key = 0;
    int countDown = 0;
    boolean firstTime = true;
    int slotNumber = 0;
    JPanel[] keyboardJPanels = null;
    JLayeredPane jLayeredPane = null;
    Color breathingColour = new Color(255, 0, 255);

    BrathingTimerTask(String color, JPanel[] keyboardJPanels, JLayeredPane jLayeredPane) {
        this.breathingColour = getColourFromHexString(color);
        this.keyboardJPanels = keyboardJPanels;
        this.jLayeredPane = jLayeredPane;
    }

    @Override
    public void run() {
        if (countDown <= 20) {
            double ratio = (double) (countDown * 5) / (double) 100;
            int red = (int) Math.abs(((1 - ratio) * Color.BLACK.getRed()) + (ratio * breathingColour.getRed()));
            int green = (int) Math.abs(((1 - ratio) * Color.BLACK.getGreen()) + (ratio * breathingColour.getGreen()));
            int blue = (int) Math.abs(((1 - ratio) * Color.BLACK.getBlue()) + (ratio * breathingColour.getBlue()));
            drawColour(new Color(red, green, blue));
        } else if (countDown <= 40) {
            double ratio = (double) ((countDown - 20) * 5) / (double) 100;
            int red = (int) Math.abs(((1 - ratio) * breathingColour.getRed()) + (ratio * Color.BLACK.getRed()));
            int green = (int) Math.abs(((1 - ratio) * breathingColour.getGreen()) + (ratio * Color.BLACK.getGreen()));
            int blue = (int) Math.abs(((1 - ratio) * breathingColour.getBlue()) + (ratio * Color.BLACK.getBlue()));
            drawColour(new Color(red, green, blue));
        }
        if (countDown == 40) {
            countDown = 0;
        } else {
            countDown++;
        }
    }

    private void drawColour(Color color) {
        for (int i = 0; i < keyboardJPanels.length; i++) {
            keyboardJPanels[i].setBackground(color);
            jLayeredPane.moveToBack(keyboardJPanels[i]);
        }
    }

    private Color getColourFromHexString(String colourHex) {
        return new Color(
                Integer.valueOf(colourHex.substring(0, 2), 16),
                Integer.valueOf(colourHex.substring(2, 4), 16),
                Integer.valueOf(colourHex.substring(4, 6), 16));
    }
}

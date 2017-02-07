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

import java.io.Serializable;

/**
 * Note: This work has been converted to Java from C++, some modifications and 
 * additions have been done to the code
 * Original work can be found at https://github.com/MatMoul/g810-led
 * @author MatMoul, Mohamad Saada
 */
public class GKeyboard implements Serializable {

    private Effects effect = null;
    private Keyboard.KeyboardModel model;
    private int keyCount = 0;
    private String[][] keyColourMap = new String[2][127];

    public enum Category {
        FreeStyle, Effects, Zones
    };
    private Category currentCategory = null;
    private StarEffect starEffect = null;
    private Effects.Type startupEffect = Effects.Type.NoEffect;

    //constructor
    public GKeyboard(Keyboard.KeyboardModel model) {
        initialiseKeyColourMap();
        this.model = model;
        switch (model) {
            case Logi910SpectrumUSQWERTY:
            case Logi910SparkUSQWERTY:
                initialiseG910USQWERTY();
                break;
            case Logi910SpectrumUKQWERTY:
            case Logi910SparkUKQWERTY:
                initialiseG910UKQWERTY();
                break;
            case Logi810SpectrumUSQWERTY:
                initialiseG810USQWERTY();
                break;
            case Logi810SpectrumUKQWERTY:
                initialiseG810UKQWERTY();
                break;
            case Logi610OrionUSQWERTY:
                initialiseG610USQWERTY();
                break;
            case Logi610OrionUKQWERTY:
                initialiseG610UKQWERTY();
                break;
            case Logi410AtlasSpectrumUSQWERTY:
                initialiseG410USQWERTY();
                break;
            case Logi410AtlasSpectrumUKQWERTY:
                initialiseG410UKQWERTY();
                break;
            default:
                break;
        }

    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //             initialisation              //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    void initialiseG910USQWERTY() {
        this.keyCount = 115;
    }

    void initialiseG910UKQWERTY() {
        this.keyCount = 116;
    }

    void initialiseG810USQWERTY() {
        this.keyCount = 115;
    }

    void initialiseG810UKQWERTY() {
        this.keyCount = 116;
    }

    void initialiseG610USQWERTY() {
        this.keyCount = 115;
    }

    void initialiseG610UKQWERTY() {
        this.keyCount = 116;
    }

    void initialiseG410USQWERTY() {
        this.keyCount = 116;
    }

    void initialiseG410UKQWERTY() {
        this.keyCount = 116;
    }

    private void initialiseKeyColourMap() {
        for (int i = 0; i < 127; i++) {
            this.keyColourMap[0][i] = Keyboard.Key.values()[i].toString();
            this.keyColourMap[1][i] = "000000";
        }
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //            getters & setters            //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    public Effects getEffect() {
        return this.effect;
    }

    public void setEffect(Effects effect) {
        this.effect = effect;
    }

    public Effects copyEffectObject(Effects effect) {
        Effects copyEffect = new Effects();
        copyEffect.BreathingColour = effect.BreathingColour;
        copyEffect.BreathingSpeed = effect.BreathingSpeed;
        copyEffect.ColourCycleSpeed = effect.ColourCycleSpeed;
        copyEffect.ColourWave = effect.ColourWave;
        copyEffect.ColourWaveSpeed = effect.ColourWaveSpeed;
        copyEffect.CurrentEffect = effect.CurrentEffect;
        copyEffect.FixedColour = effect.FixedColour;
        copyEffect.KeyPressedColour = effect.KeyPressedColour;
        copyEffect.LightningColour = effect.LightningColour;
        copyEffect.SkyColour = effect.SkyColour;
        copyEffect.StarColour = effect.StarColour;
        return copyEffect;
    }

    public Keyboard.KeyboardModel getModel() {
        return this.model;
    }

    public void setModel(Keyboard.KeyboardModel model) {
        this.model = model;
    }

    public int getKeyCount() {
        return this.keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public String[][] getKeyColourMap() {
        String[][] copyColourKeyMap = new String[this.keyColourMap.length][];
        for (int i = 0; i < this.keyColourMap.length; i++) {
            copyColourKeyMap[i] = new String[this.keyColourMap[i].length];
            System.arraycopy(this.keyColourMap[i], 0, copyColourKeyMap[i], 0,
                    this.keyColourMap[i].length);
        }
        return copyColourKeyMap;
    }

    public void setKeyColourMap(String[][] keyColourMap) {
        for (int i = 0; i < keyColourMap.length; i++) {
            System.arraycopy(keyColourMap[i], 0, this.keyColourMap[i], 0,
                    keyColourMap[i].length);
        }
    }

    public Category getCurrentCategory() {
        return this.currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public StarEffect getStarEffect() {
        return this.starEffect;
    }

    public void setStarEffect(StarEffect starEffect) {
        this.starEffect = starEffect;
    }

    public Effects.Type getStartupEffect() {
        return this.startupEffect;
    }

    public void setStartupEffect(Effects.Type startupEffect) {
        this.startupEffect = startupEffect;
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //      Physical keyboard functions        //
    //                                         //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    //commit changes to keyboard
    public void commit() {
        Keyboard lg_kbd = new Keyboard();
        lg_kbd.attach();
        lg_kbd.commit();
        lg_kbd.detach();
    }

    public void setStartupEffect(String effect) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.PowerOnEffect powerOnEffect;
        powerOnEffect = lg_kbd.parsePowerOnEffect(effect);
        lg_kbd.attach();
        lg_kbd.setPowerOnEffect(powerOnEffect);
        lg_kbd.commit();
        lg_kbd.detach();
    }

    //set a certain key with specific colour
    public void setKey(String key, String color, boolean commit) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyAddress keyAddress = lg_kbd.new KeyAddress();
        keyAddress = lg_kbd.parseKey(key);
        Keyboard.KeyColors colors = lg_kbd.new KeyColors();
        colors = lg_kbd.parseColor(color);
        Keyboard.KeyValue keyValue = lg_kbd.new KeyValue();
        keyValue.key = keyAddress;
        keyValue.colors = colors;
        lg_kbd.attach();
        lg_kbd.setKey(keyValue);
        if (commit == true) {
            lg_kbd.commit();
        }
        lg_kbd.detach();
    }

    //set all keys with a specific colour
    public void setAllKeys(String color, boolean commit) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors = lg_kbd.new KeyColors();
        colors = lg_kbd.parseColor(color);
        lg_kbd.attach();
        lg_kbd.setAllKeys(colors);
        if (commit == true) {
            lg_kbd.commit();
        }
        lg_kbd.detach();
    }

    //set a key group with a specific colour
    public void setGroupKeys(String groupKeys, String color, boolean commit) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyGroup keyGroup = lg_kbd.parseKeyGroup(groupKeys);
        keyGroup = lg_kbd.parseKeyGroup(groupKeys);
        Keyboard.KeyColors colors;
        colors = lg_kbd.parseColor(color);
        lg_kbd.attach();
        lg_kbd.setGroupKeys(keyGroup, colors);
        if (commit == true) {
            lg_kbd.commit();
        }
        lg_kbd.detach();
    }

    //set an array of keys with a specific colour
    public void setKeys(String[] keys, String color, boolean commit) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors;
        colors = lg_kbd.parseColor(color);
        lg_kbd.attach();
        lg_kbd.setKeys(keys, colors);
        if (commit == true) {
            lg_kbd.commit();
        }
        lg_kbd.detach();
    }

    //set array of specific colours, according to key order in Key enum in Keyboard
    public void setProfile(String[] profile) {
        Keyboard lg_kbd = new Keyboard();
        lg_kbd.attach();
        lg_kbd.setProfile(profile);
        lg_kbd.commit();
        lg_kbd.detach();
    }

    //set array of specific colours, according to adjacent keys in the array
    public void setProfile(String[][] profile) {
        Keyboard lg_kbd = new Keyboard();
        lg_kbd.attach();
        lg_kbd.setProfile(profile);
        lg_kbd.commit();
        lg_kbd.detach();
    }

    /////////////////////////////////////////////
    //                                         //
    //                                         //
    //                                         //
    //       Different built-in effects        //
    //               functions                 //
    //                                         //
    //                                         //
    /////////////////////////////////////////////
    public void setFXColor(String color) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors;
        colors = lg_kbd.parseColor(color);
        lg_kbd.attach();
        lg_kbd.setGroupKeys(Keyboard.KeyGroup.indicators, colors);
        lg_kbd.commit();
        lg_kbd.detach();
        lg_kbd.attach();
        lg_kbd.setFXColor(colors);
        lg_kbd.detach();
    }

    public void setFXBreathing(String color, int speed) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors;
        //short speedValue;
        colors = lg_kbd.parseColor(color);
        //speedValue = lg_kbd.parseSpeed(speed);
        lg_kbd.attach();
        lg_kbd.setGroupKeys(Keyboard.KeyGroup.indicators, colors);
        lg_kbd.commit();
        lg_kbd.detach();
        lg_kbd.attach();
        lg_kbd.setFXBreathing(colors, speed);
        lg_kbd.detach();
    }

    public void setFXColorCycle(int speed) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors = lg_kbd.new KeyColors();
        colors.red = (byte) 0xff;
        colors.green = (byte) 0xff;
        colors.blue = (byte) 0xff;
        lg_kbd.attach();
        lg_kbd.setGroupKeys(Keyboard.KeyGroup.indicators, colors);
        lg_kbd.commit();
        lg_kbd.detach();
        lg_kbd.attach();
        lg_kbd.setFXColorCycle(speed);
        lg_kbd.detach();
    }

    public void setFXHWave(int speed) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors = lg_kbd.new KeyColors();
        colors.red = (byte) 0xff;
        colors.green = (byte) 0xff;
        colors.blue = (byte) 0xff;
        lg_kbd.attach();
        lg_kbd.setGroupKeys(Keyboard.KeyGroup.indicators, colors);
        lg_kbd.commit();
        lg_kbd.detach();
        lg_kbd.attach();
        lg_kbd.setFXHWave(speed);
        lg_kbd.detach();
    }

    public void setFXVWave(int speed) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors = lg_kbd.new KeyColors();
        colors.red = (byte) 0xff;
        colors.green = (byte) 0xff;
        colors.blue = (byte) 0xff;
        lg_kbd.attach();
        lg_kbd.setGroupKeys(Keyboard.KeyGroup.indicators, colors);
        lg_kbd.commit();
        lg_kbd.detach();
        lg_kbd.attach();
        lg_kbd.setFXVWave(speed);
        lg_kbd.detach();
    }

    public void setFXCWave(int speed) {
        Keyboard lg_kbd = new Keyboard();
        Keyboard.KeyColors colors = lg_kbd.new KeyColors();
        colors.red = (byte) 0xff;
        colors.green = (byte) 0xff;
        colors.blue = (byte) 0xff;
        lg_kbd.attach();
        lg_kbd.setGroupKeys(Keyboard.KeyGroup.indicators, colors);
        lg_kbd.commit();
        lg_kbd.detach();
        lg_kbd.attach();
        lg_kbd.setFXCWave(speed);
        lg_kbd.detach();
    }
}

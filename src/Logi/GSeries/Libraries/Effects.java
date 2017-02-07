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
 *
 * @author Mohamad Saada
 */
public class Effects implements Serializable {

    public enum Type {
        NoEffect, FixedColour, Breathing, ColourWave, ColourCycle, Stars, Lightning, KeyPress
    };

    public enum ColourWaveType {
        Horizontal, Vertical, CentreOut
    };
    public Type CurrentEffect = Type.NoEffect;
    public ColourWaveType ColourWave = ColourWaveType.Horizontal;
    public String FixedColour = null;
    public String BreathingColour = null;
    public String StarColour = null;
    public String SkyColour = null;
    public String LightningColour = null;
    public String KeyPressedColour = null;
    public int BreathingSpeed = 0;
    public int ColourCycleSpeed = 0;
    public int ColourWaveSpeed = 0;
}

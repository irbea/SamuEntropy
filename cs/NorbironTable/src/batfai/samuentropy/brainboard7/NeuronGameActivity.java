/*
 * NeuronAnimActivity.java
 *
 * Norbiron Game
 * This is a case study for creating sprites for SamuEntropy/Brainboard.
 *
 * Copyright (C) 2016, Dr. Bátfai Norbert
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
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * 0.0.1, 2013.szept.29.
 */
package batfai.samuentropy.brainboard7;

import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 *
 * @author nbatfai
 */
public class NeuronGameActivity extends android.app.Activity {

    public static boolean restore = false;

    @Override
    protected void onPause()
    {
        super.onPause();

        android.content.SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        android.content.SharedPreferences.Editor editor = settings.edit();

        NorbironSurfaceView.saveData(editor);
    }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        for (int i = 0; i < NorbironSurfaceView.getNumBoxes(); i++) {
            savedInstanceState.putInt("x" + i, NorbironSurfaceView.getBox(i).getX());
            savedInstanceState.putInt("y" + i, NorbironSurfaceView.getBox(i).getY());
            savedInstanceState.putInt("db" + i, NorbironSurfaceView.getBox(i).numberOfNeurons);
            savedInstanceState.putInt("type" + i, NorbironSurfaceView.getBox(i).getCoverType());
            savedInstanceState.putBoolean("selected" + i, NorbironSurfaceView.getBox(i).getSelected());
            savedInstanceState.putBoolean("open" + i, NorbironSurfaceView.getBox(i).getCover());
        }

        savedInstanceState.putInt("size", NorbironSurfaceView.getNumBoxes());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restore = true;

        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; i < savedInstanceState.getInt("size"); i++) {
            int covertype = savedInstanceState.getInt("type"+i);
            NeuronBox box = (NeuronBox)Nodes.get(covertype).clone();
            box.setXY(savedInstanceState.getInt("x"+i), savedInstanceState.getInt("y"+i));
            box.numberOfNeurons = savedInstanceState.getInt("db"+i);
            box.setSelected(savedInstanceState.getBoolean("selected"+i));
            box.setCover(savedInstanceState.getBoolean("open"+i));
            NorbironSurfaceView.addBox(box);
        }
    }
}

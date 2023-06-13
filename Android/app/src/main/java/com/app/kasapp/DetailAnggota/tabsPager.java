package com.app.kasapp.DetailAnggota;

import com.app.kasapp.DetailAnggota.Blank2Fragment;
import com.app.kasapp.DetailAnggota.BlankFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by ASUS on 11/04/2018.
 */

public class tabsPager extends FragmentStatePagerAdapter {

    int tabCount;

    public tabsPager(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BlankFragment tabs1 = new BlankFragment();
                return tabs1;
            case 1:
                Blank2Fragment tabs2 = new Blank2Fragment();
                return tabs2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
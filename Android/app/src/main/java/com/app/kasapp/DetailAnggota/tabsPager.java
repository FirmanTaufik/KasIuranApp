package com.app.kasapp.DetailAnggota;

import com.app.kasapp.DetailAnggota.Blank2Fragment;
import com.app.kasapp.DetailAnggota.BlankFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


/**
 * Created by ASUS on 11/04/2018.
 */

public class tabsPager extends FragmentStatePagerAdapter {

    int tabCount;

    public tabsPager(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    private ArrayList<Fragment> fragments  =  new ArrayList<>();

    public  void  addFragment(Fragment fragment){
        fragments.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
        /*switch (position) {
            case 0:
                BlankFragment tabs1 = new BlankFragment();
                return tabs1;
            case 1:
                Blank2Fragment tabs2 = new Blank2Fragment();
                return tabs2;
            case 2:
                ProfileFragment tabs3 = new ProfileFragment();
                return tabs3;
            default:
                return null;
        }*/
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
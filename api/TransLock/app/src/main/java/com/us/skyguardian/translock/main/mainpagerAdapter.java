package com.us.skyguardian.translock.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.us.skyguardian.translock.main.candados.CandadosFragment;
import com.us.skyguardian.translock.main.compartidos.compartidosFragment;
import com.us.skyguardian.translock.main.configuracion.ConfiguracionFragment;
import com.us.skyguardian.translock.main.historial.historialFragment;

import java.util.ArrayList;

public class mainpagerAdapter extends FragmentPagerAdapter {

    ArrayList<BaseFragment> fragments;

    public mainpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public mainpagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

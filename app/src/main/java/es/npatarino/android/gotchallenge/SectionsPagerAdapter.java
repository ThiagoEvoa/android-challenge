package es.npatarino.android.gotchallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    SectionsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new GoTListFragment();
        } else {
            return new GoTHousesListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Characters";
            case 1:
                return "Houses";
        }
        return null;
    }
}

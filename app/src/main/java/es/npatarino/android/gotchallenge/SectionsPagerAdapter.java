package es.npatarino.android.gotchallenge;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context myContext;

    SectionsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.myContext = context;
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
                return myContext.getResources().getString(R.string.tab_character);
            case 1:
                return myContext.getResources().getString(R.string.tab_house);
        }
        return null;
    }
}

package studioidan.com.parsetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.studioidan.popapplibrary.CPM;

import entities.Keys;
import fragments.Fr_1;
import fragments.Fr_2;
import fragments.Fr_3;
import fragments.Fr_4;


public class FirstTime extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time);
        CPM.putBoolean(Keys.firstTime, false, FirstTime.this);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new Fr_1();
                    case 1:
                        return new Fr_2();
                    case 2:
                        return new Fr_3();
                    case 3:
                        return new Fr_4();

                    default:
                        return null;
                }
            }
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.rashi);
                    case 1:
                        return getString(R.string.avodot);
                    case 2:
                        return getString(R.string.barim);
                    case 3:
                        return getString(R.string.amsheh);

                    default:
                        return "";
                }
            }
            @Override
            public int getCount() {
                return 4;
            }
        });
    }
}

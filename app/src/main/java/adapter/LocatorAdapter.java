package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import firstmob.firstbank.com.firstagent.BranchesMap;

/**
 * Created by hp1 on 21-01-2015.
 */
public class LocatorAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public LocatorAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Bundle b  = new Bundle();
            String pos = "1";
            b.putString("typee", pos);
            BranchesMap tab1 = new BranchesMap();
            tab1.setArguments(b);
            return tab1;
        }
        else  if(position == 1)          // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Bundle b  = new Bundle();
            String pos = "2";
            b.putString("typee", pos);
            BranchesMap tab2 = new BranchesMap();
            tab2.setArguments(b);

            return tab2;
        }
        else  if(position == 2)          // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {

            Bundle b  = new Bundle();
            String pos = "3";
            b.putString("typee", pos);
            BranchesMap tab4 = new BranchesMap();
            tab4.setArguments(b);


            return tab4;
        }
        else{
            Bundle b  = new Bundle();
            String pos = "4";
            b.putString("typee", pos);
            BranchesMap tab3 = new BranchesMap();
            tab3.setArguments(b);

            return tab3;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
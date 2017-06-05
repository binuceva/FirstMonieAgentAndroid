package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import firstmob.firstbank.com.firstagent.ActivitiesFrag;
import firstmob.firstbank.com.firstagent.LayoutTwo;
import firstmob.firstbank.com.firstagent.MyHomeFrag;
import firstmob.firstbank.com.firstagent.OffersFrag;

/**
 * Created by hp1 on 21-01-2015.
 */
public class FHomeAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public FHomeAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {

            MyHomeFrag tab1 = new MyHomeFrag();

            return tab1;
        }
        else  if(position == 1)          // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {

            ActivitiesFrag tab2 = new ActivitiesFrag();


            return tab2;
        }
        else  if(position == 2)          // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {

            OffersFrag tab2 = new OffersFrag();


            return tab2;
        }
        else{

            LayoutTwo tab3 = new LayoutTwo();


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
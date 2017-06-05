package adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import firstmob.firstbank.com.firstagent.NewsFeed;

/**
 * Created by hp1 on 21-01-2015.
 */
public class  ViewPagerAdapter extends FragmentStatePagerAdapter {

    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    List<String> Titles;
    List<String> planetsList2;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,  List<String> Titles , int mNumbOfTabsumb,List<String> planetsList2) {
        super(fm);

        this.Titles = Titles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.planetsList2 = planetsList2;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
Fragment frag = null;
        if(position == 0) // if the position is 0 we are returning the First tab
        {
        frag =  getFrag(position);
        }
        else if (position == 1)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 2)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 3)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 4)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 5)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 6)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 7)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 8)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 9)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }

        else if (position == 10)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 11)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 12)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else if (position == 13)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
        else         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag =  getFrag(position);
        }
return  frag;

    }


    public Fragment getFrag(int pos){
        Fragment tabc = null;
        String bn = planetsList2.get(pos);
        tabc = new NewsFeed();

        return tabc;
    }
    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles.get(position);
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }}
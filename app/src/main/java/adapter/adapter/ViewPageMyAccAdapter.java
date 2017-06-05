package adapter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import firstmob.firstbank.com.firstagent.Discounts;
import firstmob.firstbank.com.firstagent.PhnBackLay;
import firstmob.firstbank.com.firstagent.RingLay;

public class ViewPageMyAccAdapter extends FragmentPagerAdapter  {
    protected static final String[] CONTENT = new String[] { "This", "Is", "A", "Test", };
    protected static final int[] ICONS = new int[] {

    };

    private int mCount = 3;

    public ViewPageMyAccAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) // if the position is 0 we are returning the First tab
        {
            PhnBackLay tab1 = new PhnBackLay();
            return tab1;
        } else if (position == 1) // if the position is 0 we are returning the First tab
        {
            RingLay tab5 = new RingLay();
            return tab5;
        } else          // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Discounts tab6 = new Discounts();
            return tab6;
        }

      /*  if (position == 0)
            return new RootFragment();
        else if (position == 1){
            return new RootRing();
    }else{
            return new RootDisc();
    }*/


      /*  return TestFragment.newInstance(CONTENT[position % CONTENT.length]);*/
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return ViewPageMyAccAdapter.CONTENT[position % CONTENT.length];
    }


    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
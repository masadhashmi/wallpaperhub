package wallpaperscube.pakpublishers.com.wallpapers.Adapters;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import wallpaperscube.pakpublishers.com.wallpapers.CatagoriesFragment;
import wallpaperscube.pakpublishers.com.wallpapers.CatagoryWallpapers;
import wallpaperscube.pakpublishers.com.wallpapers.FirstFragment;
import wallpaperscube.pakpublishers.com.wallpapers.WallpapersFragment;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryInfo;

/**
 * Created by Asad on 8/19/2016.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    public ArrayList<Fragment> PageFragments=null;
    public static ArrayList<String> TabContents=null;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        PageFragments=new ArrayList<Fragment>();
        PageFragments.add(CatagoriesFragment.newInstance(0,"Catagories"));



        PageFragments.add(WallpapersFragment.newInstance(1, "Recent Wallpapers","getRecentWallpapers"));
        PageFragments.add(FirstFragment.newInstance(1, "Page # 3"));
        PageFragments.add(FirstFragment.newInstance(1, "Page # 4"));

    }

    public static   void initiateHeaderContents()
    {
        TabContents=new ArrayList<String>();
        TabContents.add("CATAGORIES");
        TabContents.add("RECENT");
        TabContents.add("NEW ARRIVALS");
        TabContents.add("MOST POPULAR");


    }

    // Returns total number of pages
    @Override
    public int getCount() {


        return TabContents.size();
    }



    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
            return PageFragments.get(position);

    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return TabContents.get(position);
    }

}

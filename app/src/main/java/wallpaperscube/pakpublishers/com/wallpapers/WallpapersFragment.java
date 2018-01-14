package wallpaperscube.pakpublishers.com.wallpapers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import wallpaperscube.pakpublishers.com.wallpapers.Adapters.Catagories_ArrayAdapter;
import wallpaperscube.pakpublishers.com.wallpapers.Adapters.WallpapersGridView_BaseAdapter;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryDetail;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryInfo;

/**
 * Created by Asad on 8/19/2016.
 */
public class WallpapersFragment extends Fragment {
    private String title;
    private int page;

    private String MathoudName="";

    Handler CurrentCatagoriesFetcherHandle=null;
    WallpapersGridView_BaseAdapter WallpapersCustomGridViewAdapter=null;
    GridView gridView_CatagoryWallpapers=null;
    ArrayList<CatagoryDetail> Wallpapers;

    public void setMethoudName(String methoudName)
    {
        MathoudName=methoudName;

    }

    // newInstance constructor for creating fragment with arguments
    public static WallpapersFragment newInstance(int page, String title,String methoudName) {
        WallpapersFragment fragmentFirst = new WallpapersFragment();
        fragmentFirst.setMethoudName(methoudName);
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);

        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Status","here..");
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MathoudName= MathoudName+"";

        CurrentCatagoriesFetcherHandle=new Handler()
        {
            @Override
            public void handleMessage(Message m) {
                int k=0;
                switch (m.what)
                {
                    case 1: {

                        SoapObject Data = (SoapObject) m.obj;
                        if (Data.toString().length() > 15) {
                            SoapObject DataObject = (SoapObject) Data.getProperty(0);
                            Boolean HasError = true;
                            try {
                                String ErrorStatus = (DataObject.getProperty("ErrorStatus").toString().toLowerCase().contains("anytype")) ? "" : DataObject.getProperty("ErrorStatus").toString();
                                String ErrorMessage = (DataObject.getProperty("ErrorMessage").toString().toLowerCase().contains("anytype")) ? "" : DataObject.getProperty("ErrorMessage").toString();
                            } catch (Exception e) {
                            }
                            DataObject = (SoapObject) DataObject.getProperty("DataObject");

                            Wallpapers = new ArrayList<CatagoryDetail>();
                            for (int i = 0; i < DataObject.getPropertyCount(); i++) {
                                SoapObject DataRow = (SoapObject) DataObject.getProperty(i);
                                CatagoryDetail pc = new CatagoryDetail();
                                try {
                                    pc.setID((DataRow.getProperty("ID").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("ID").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCatagoryid((DataRow.getProperty("catagoryid").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("catagoryid").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setDescription((DataRow.getProperty("description").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("description").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCreationtime((DataRow.getProperty("creationtime").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("creationtime").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setDeleted((DataRow.getProperty("deleted").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("deleted").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCatagoryfolder((DataRow.getProperty("catagoryfoldername").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("catagoryfoldername").toString());
                                } catch (Exception e) {
                                }

                                try {

                                    String BaseURL = WallpapersFragment.this.getContext().getResources().getString(R.string.URL_BaseURL);
                                    String BaseDirectoryURL = WallpapersFragment.this.getContext().getResources().getString(R.string.URL_ImageBaseDirectoryURL);
                                    String ItemPath = (DataRow.getProperty("itempath").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("itempath").toString();
                                    pc.setItempath(BaseURL + "/" + BaseDirectoryURL + "/" + pc.getCatagoryfolder() + "/" + ItemPath);
                                    Log.e("Image URL:", pc.getItempath());

                                } catch (Exception e) {
                                }
                                //pc.setCatagoryfolder("test");
                                Wallpapers.add(pc);
                            }

                            WallpapersCustomGridViewAdapter = new WallpapersGridView_BaseAdapter(WallpapersFragment.this.getContext(), Wallpapers);
                            gridView_CatagoryWallpapers.setAdapter(WallpapersCustomGridViewAdapter);
                            WallpapersCustomGridViewAdapter.notifyDataSetChanged();
                        }
                    }
                    case -1:
                    {
                        Toast.makeText(WallpapersFragment.this.getContext(),"Check Network Connection.",Toast.LENGTH_LONG);
                    }
                }

            }


        };

    try
    {
        String SoapAction = this.getContext().getResources().getString(R.string.URL_Service_SoapAction);
        String URL_Service_NameSpace = this.getContext().getResources().getString(R.string.URL_Service_NameSpace);
        String ServiceURL = this.getContext().getResources().getString(R.string.URL_Service_Url);
        WebConnection CatagoriesFetcher = new WebConnection(CurrentCatagoriesFetcherHandle);
        CatagoriesFetcher.setURL(ServiceURL);
        CatagoriesFetcher.setHeaderDetails(SoapAction + ""+MathoudName, ""+MathoudName, URL_Service_NameSpace);
        //CatagoriesFetcher.addProperty("catagoryid", "23");
        CatagoriesFetcher.setWhats(-1, 1);
        CatagoriesFetcher.Start();
    }catch(Exception er)
    {
        Log.e("LoadingError",""+er.toString());

    }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            WallpapersCustomGridViewAdapter = new WallpapersGridView_BaseAdapter(WallpapersFragment.this.getContext(), Wallpapers);
            gridView_CatagoryWallpapers.setAdapter(WallpapersCustomGridViewAdapter);
            WallpapersCustomGridViewAdapter.notifyDataSetChanged();
        }catch (Exception er){}
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStateBundle) {

        View view=null;
        view = inflater.inflate(R.layout.activity_catagory_wallpapers, container, false);
        gridView_CatagoryWallpapers=(GridView)view.findViewById(R.id.gridView_CatagoryWallpapers);
        return view;
    }
}
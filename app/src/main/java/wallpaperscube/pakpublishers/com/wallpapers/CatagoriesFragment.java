package wallpaperscube.pakpublishers.com.wallpapers;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import wallpaperscube.pakpublishers.com.wallpapers.Adapters.Catagories_ArrayAdapter;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryInfo;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.XmlParser;

/**
 * Created by Asad on 8/19/2016.
 */
public class CatagoriesFragment extends Fragment {
    private String title;
    private int page;
    ListView listView_Catagories;
     Handler CurrentCatagoriesFetcherHandle=null;
    Catagories_ArrayAdapter CatagoriesAdapter=null;
    // newInstance constructor for creating fragment with arguments
    public static CatagoriesFragment  newInstance(int page, String title) {
        CatagoriesFragment  fragmentFirst = new CatagoriesFragment ();
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



        CurrentCatagoriesFetcherHandle=new Handler()
        {
            @Override
            public void handleMessage(Message m) {
                switch (m.what)
                {
                    case 1: {
                        ArrayList<CatagoryInfo> WallCatagories;
                        SoapObject Data = (SoapObject) m.obj;
                        if (Data.toString().length() > 15) {
                            SoapObject DataObject = (SoapObject) Data.getProperty(0);
                            Boolean HasError = true;
                            try {
                                Boolean ErrorStatus = Boolean.parseBoolean ((DataObject.getProperty("ErrorStatus").toString().toLowerCase().contains("anytype")) ? "" : DataObject.getProperty("ErrorStatus").toString());
                                String ErrorMessage = (DataObject.getProperty("ErrorMessage").toString().toLowerCase().contains("anytype")) ? "" : DataObject.getProperty("ErrorMessage").toString();
                                if(ErrorStatus)
                                {
                                    Log.e("Server Error",ErrorMessage);
                                    Toast.makeText(CatagoriesFragment.this.getContext(), ErrorMessage, Toast.LENGTH_LONG).show();
                                    return;
                                }
                            } catch (Exception e) {
                            }
                            DataObject = (SoapObject) DataObject.getProperty("DataObject");

                            WallCatagories = new ArrayList<CatagoryInfo>();
                            for (int i = 0; i < DataObject.getPropertyCount(); i++) {
                                SoapObject DataRow = (SoapObject) DataObject.getProperty(i);
                                CatagoryInfo pc = new CatagoryInfo();
                                try {
                                    pc.setID((DataRow.getProperty("ID").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("ID").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCatagoryfolder((DataRow.getProperty("catagoryfolder").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("catagoryfolder").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCatagoryname((DataRow.getProperty("catagoryname").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("catagoryname").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCount((DataRow.getProperty("count").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("count").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setCreationupdatedtime((DataRow.getProperty("creationupdatedtime").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("creationupdatedtime").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setDeleted((DataRow.getProperty("deleted").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("deleted").toString());
                                } catch (Exception e) {
                                }
                                try {
                                    pc.setDescription((DataRow.getProperty("description").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("description").toString());
                                } catch (Exception e) {
                                }
                                try {

                                    String BaseURL = getContext().getResources().getString(R.string.URL_BaseURL);
                                    String BaseDirectoryURL = getContext().getResources().getString(R.string.URL_ImageBaseDirectoryURL);
                                    String CatagoryThumbImageName = getContext().getResources().getString(R.string.URL_CatagoryThumbnailImageName);
                                    pc.setImageURL(BaseURL + "/" + BaseDirectoryURL + "/" + pc.getCatagoryfolder() + "/" + CatagoryThumbImageName);
                                    Log.e("Image URL:", pc.getImageURL());

                                } catch (Exception e) {
                                }

                                WallCatagories.add(pc);
                            }

                            CatagoriesAdapter = new Catagories_ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, WallCatagories, this);
                            listView_Catagories.setAdapter(CatagoriesAdapter);
                            CatagoriesAdapter.notifyDataSetChanged();
                        }
                    }
                    case -1:
                    {
                        Toast.makeText(getContext(),"Check Network Connection.",Toast.LENGTH_LONG);
                    }
                }

                }


        };


        String SoapAction= getContext().getResources().getString(R.string.URL_Service_SoapAction);
        String URL_Service_NameSpace= getContext().getResources().getString(R.string.URL_Service_NameSpace);
        String ServiceURL= getContext().getResources().getString(R.string.URL_Service_Url);
        WebConnection CatagoriesFetcher=new WebConnection(CurrentCatagoriesFetcherHandle);
        CatagoriesFetcher.setURL(ServiceURL);
        CatagoriesFetcher.setHeaderDetails(SoapAction+"getWallpaperCatagories","getWallpaperCatagories", URL_Service_NameSpace);
        CatagoriesFetcher.setWhats(-1, 1);
        CatagoriesFetcher.Start();

    }

    @Override
    public void onResume() {
        super.onResume();
        CatagoriesAdapter = new Catagories_ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Catagories_ArrayAdapter.myArrayList, CurrentCatagoriesFetcherHandle);
        listView_Catagories.setAdapter(CatagoriesAdapter);
        CatagoriesAdapter.notifyDataSetChanged();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStateBundle) {
        Log.e("status","Showing");
        View view=null;
        view = inflater.inflate(R.layout.catagories_layout, container, false);
        listView_Catagories=(ListView) view.findViewById(R.id.listView_Catagories);
        return view;
    }
}
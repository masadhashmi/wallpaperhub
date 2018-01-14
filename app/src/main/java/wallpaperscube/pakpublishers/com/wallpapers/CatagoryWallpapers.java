package wallpaperscube.pakpublishers.com.wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import wallpaperscube.pakpublishers.com.wallpapers.Adapters.Catagories_ArrayAdapter;
import wallpaperscube.pakpublishers.com.wallpapers.Adapters.WallpapersGridView_BaseAdapter;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryDetail;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryInfo;

public class CatagoryWallpapers extends AppCompatActivity {

     CatagoryInfo Currentcat=null;
    Handler CurrentCatagoriesFetcherHandle=null;
    WallpapersGridView_BaseAdapter WallpapersCustomGridViewAdapter=null;
    GridView gridView_CatagoryWallpapers=null;
    ImageButton ImageButton_BackButton=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_wallpapers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent CurrentIntent=getIntent();

     Currentcat=new CatagoryInfo();
        Currentcat.setCatagoryname(CurrentIntent.getStringExtra("catagoryname"));
        Currentcat.setImageURL(CurrentIntent.getStringExtra("catagoryimageurl"));
        Currentcat.setDescription(CurrentIntent.getStringExtra("catagorydescription"));
        Currentcat.setCatagoryfolder(CurrentIntent.getStringExtra("catagoryfolder"));
        Currentcat.setID(CurrentIntent.getStringExtra("catagoryid"));
        Currentcat.setCount(CurrentIntent.getStringExtra("catagorycount"));

        Log.e("Catagory Name:",Currentcat.getCatagoryname());

        gridView_CatagoryWallpapers=(GridView)findViewById(R.id.gridView_CatagoryWallpapers);
        //WallpapersGridView_BaseAdapter GridAdp=new WallpapersGridView_BaseAdapter(getApplicationContext(),)

        ImageButton_BackButton=(ImageButton)findViewById(R.id.ImageButton_BackButton);
        ImageButton_BackButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                CatagoryWallpapers.this.finish();

            }
        });

        CurrentCatagoriesFetcherHandle=new Handler()
        {
            @Override
            public void handleMessage(Message m) {
                switch (m.what)
                {
                    case 1: {
                        ArrayList<CatagoryDetail> Wallpapers;
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

                                    String BaseURL = getApplicationContext().getResources().getString(R.string.URL_BaseURL);
                                    String BaseDirectoryURL = getApplicationContext().getResources().getString(R.string.URL_ImageBaseDirectoryURL);
                                    String ItemPath = (DataRow.getProperty("itempath").toString().toLowerCase().contains("anytype")) ? "" : DataRow.getProperty("itempath").toString();
                                    pc.setItempath(BaseURL + "/" + BaseDirectoryURL + "/" + Currentcat.getCatagoryfolder() + "/" + ItemPath);
                                    Log.e("Image URL:", pc.getItempath());

                                } catch (Exception e) {
                                }
                                pc.setCatagoryfolder(Currentcat.getCatagoryname());
                                Wallpapers.add(pc);
                            }

                            WallpapersCustomGridViewAdapter = new WallpapersGridView_BaseAdapter(getBaseContext(), Wallpapers);
                            gridView_CatagoryWallpapers.setAdapter(WallpapersCustomGridViewAdapter);
                            WallpapersCustomGridViewAdapter.notifyDataSetChanged();
                        }
                    }
                    case -1:
                    {
                        Toast.makeText(getApplicationContext(),"Check Network Connection.",Toast.LENGTH_LONG);
                    }
                }

            }


        };


        String SoapAction=this.getApplicationContext().getResources().getString(R.string.URL_Service_SoapAction);
        String URL_Service_NameSpace= getApplicationContext().getResources().getString(R.string.URL_Service_NameSpace);
        String ServiceURL= getApplicationContext().getResources().getString(R.string.URL_Service_Url);
        WebConnection CatagoriesFetcher=new WebConnection(CurrentCatagoriesFetcherHandle);
        CatagoriesFetcher.setURL(ServiceURL);
        CatagoriesFetcher.setHeaderDetails(SoapAction+"getWallpapersOfCatagory","getWallpapersOfCatagory", URL_Service_NameSpace);
        CatagoriesFetcher.addProperty("catagoryid",Currentcat.getID());
        CatagoriesFetcher.setWhats(-1, 1);
        CatagoriesFetcher.Start();

    }

}

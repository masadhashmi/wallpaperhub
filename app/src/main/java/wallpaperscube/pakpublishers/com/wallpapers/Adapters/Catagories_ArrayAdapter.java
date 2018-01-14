package wallpaperscube.pakpublishers.com.wallpapers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wallpaperscube.pakpublishers.com.wallpapers.CatagoryWallpapers;
import wallpaperscube.pakpublishers.com.wallpapers.R;
import wallpaperscube.pakpublishers.com.wallpapers.Utils.ImageLoader;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryInfo;

/**
 * Created by Asad on 8/19/2016.
 */
public class Catagories_ArrayAdapter  extends ArrayAdapter<CatagoryInfo> {

    public static ArrayList<CatagoryInfo> myArrayList=new ArrayList<CatagoryInfo>();

    public static CatagoryInfo Selected=null;
    Context contx=null;
    Handler ReferenceHandler=null;
    public ImageLoader imageLoader;


    public Catagories_ArrayAdapter(Context context, int textViewResourceId,ArrayList<CatagoryInfo> items, Handler h) {
        super(context, textViewResourceId, items);
        contx=context;
        myArrayList=items;
        ReferenceHandler=h;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }
    public void ClearList()
    {
        myArrayList.clear();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int pos=position;

        View v = convertView;

        if (v == null ) {
            LayoutInflater vi = (LayoutInflater)contx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.catagoryitem_listitem_layout, null);
        }


        TextView textView_CatagoryTitle=(TextView)v.findViewById(R.id.textView_CatagoryTitle);
        TextView textView_CatagoryCount=(TextView)v.findViewById(R.id.textView_CatagoryCount);
        ImageView imageView_CatagoryImage=(ImageView)v.findViewById(R.id.imageView_CatagoryImage);


        if(textView_CatagoryTitle!=null)
        {
            textView_CatagoryTitle.setText(myArrayList.get(pos).catagoryname);
        }
        if(textView_CatagoryCount!=null)
        {
            textView_CatagoryCount.setText(myArrayList.get(pos).count+"");
        }
        if(imageView_CatagoryImage!=null)
        {

            imageLoader.DisplayImage(myArrayList.get(position).getImageURL(), imageView_CatagoryImage);
        }
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(contx, CatagoryWallpapers.class);

                CatagoryInfo Currentcat=myArrayList.get(pos);
                myIntent.putExtra("catagoryname", Currentcat.getCatagoryname()); //Optional parameters
                myIntent.putExtra("catagoryfolder", Currentcat.getCatagoryfolder()); //Optional parameters
                myIntent.putExtra("catagoryid", Currentcat.getID()); //Optional parameters
                myIntent.putExtra("catagorycount", Currentcat.getCount()); //Optional parameters
                myIntent.putExtra("catagorydescription", Currentcat.getDescription()); //Optional parameters
                myIntent.putExtra("catagoryimageurl", Currentcat.getImageURL()); //Optional parameters
                contx.startActivity(myIntent);

            }
        });
        return v;
    }

}


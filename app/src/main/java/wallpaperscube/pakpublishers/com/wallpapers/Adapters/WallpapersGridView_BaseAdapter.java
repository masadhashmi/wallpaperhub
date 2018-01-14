package wallpaperscube.pakpublishers.com.wallpapers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wallpaperscube.pakpublishers.com.wallpapers.ActivitySingleWallpaper;
import wallpaperscube.pakpublishers.com.wallpapers.CatagoryWallpapers;
import wallpaperscube.pakpublishers.com.wallpapers.R;
import wallpaperscube.pakpublishers.com.wallpapers.Utils.ImageLoader;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryDetail;

/**
 * Created by Asad on 8/21/2016.
 */
public class WallpapersGridView_BaseAdapter extends BaseAdapter {

    ArrayList<CatagoryDetail> result=null;
    Context context;
    public ImageLoader imageLoader;

    public WallpapersGridView_BaseAdapter(Context mainActivity, ArrayList<CatagoryDetail> prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;

        imageLoader = new ImageLoader(context.getApplicationContext());

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(result!=null) {
            return result.size();
        }else{return  0;}
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final int pos=position;

        View v = convertView;

        if (v == null ) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.gridview_listitem, null);
        }

        ImageView imageView_WallpaperImage=(ImageView)v.findViewById(R.id.ImageView_Wallpaper);

        if(imageView_WallpaperImage!=null)
        {
            imageLoader.DisplayImage(result.get(position).getItempath(), imageView_WallpaperImage);
            imageView_WallpaperImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    try {
                        Intent i = new Intent(context, ActivitySingleWallpaper.class);
                        CatagoryDetail CurrentCatDetail = result.get(pos);
                        i.putExtra("Catagoryid", CurrentCatDetail.getCatagoryid());
                        i.putExtra("Description", CurrentCatDetail.getDescription());
                        i.putExtra("Creationtime", CurrentCatDetail.getCreationtime());
                        i.putExtra("Deleted", CurrentCatDetail.getDeleted());
                        i.putExtra("ID", CurrentCatDetail.getID());
                        i.putExtra("Itempath", CurrentCatDetail.getItempath());
                        i.putExtra("catagoryfolder", CurrentCatDetail.getCatagoryfolder());

                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }catch (Exception e)
                    {
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }



        return v;
    }

}

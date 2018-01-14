package wallpaperscube.pakpublishers.com.wallpapers;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import wallpaperscube.pakpublishers.com.wallpapers.Utils.ImageLoader;
import wallpaperscube.pakpublishers.com.wallpapers.business_objects.CatagoryDetail;

public class ActivitySingleWallpaper extends AppCompatActivity {

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;

    CollapsingToolbarLayout toolbar_layout=null;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_single_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent CurrentIntent=getIntent();
       final CatagoryDetail CurrentCatDetail=new CatagoryDetail();
        CurrentCatDetail.setCatagoryid(CurrentIntent.getStringExtra("Catagoryid"));
        CurrentCatDetail.setDescription(CurrentIntent.getStringExtra("Description"));
        CurrentCatDetail.setCreationtime(CurrentIntent.getStringExtra("Creationtime"));
        CurrentCatDetail.setDeleted(CurrentIntent.getStringExtra("Deleted"));
        CurrentCatDetail.setID(CurrentIntent.getStringExtra("ID"));
        CurrentCatDetail.setItempath(CurrentIntent.getStringExtra("Itempath"));
        CurrentCatDetail.setCatagoryfolder(CurrentIntent.getStringExtra("catagoryfolder"));

        final String DownloadDirectory=getResources().getString(R.string.DownloadWallpaperDirectoryName);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(! verifyStoragePermissions(ActivitySingleWallpaper.this))
                {
                    return;
                }
                /*String url = CurrentCatDetail.getItempath();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/


                Toast.makeText(ActivitySingleWallpaper.this, "Download Started.", Toast.LENGTH_SHORT).show();





// Start a lengthy operation in a background thread
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {

                                //File myDir = ActivitySingleWallpaper.this.getExternalFilesDir("/"+DownloadDirectory);
                                File myDir=new File(System.getenv("EXTERNAL_STORAGE")+"/DCIM/"+DownloadDirectory);//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                myDir.mkdir();
                                Log.e("ExtPubDir",myDir.getAbsolutePath());
                                //String root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                                //File myDir =ActivitySingleWallpaper.this.getDir("/"+DownloadDirectory,Context.MODE_PRIVATE);// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                                //File myDir = new File(root );
                                myDir.mkdirs();
                                Calendar c = Calendar.getInstance();
                                String ImageName="Wallpaper_"+c.get(Calendar.YEAR)+""+c.get(Calendar.MONTH)+""+c.get(Calendar.DAY_OF_MONTH)+""+c.get(Calendar.HOUR)+""+c.get(Calendar.MINUTE)+""+ c.get(Calendar.SECOND)+".jpeg";


                                String ImageAbsolutePath= myDir.getAbsolutePath()+"/"+ImageName;






                                mNotifyManager =
                                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                mBuilder = new NotificationCompat.Builder(ActivitySingleWallpaper.this);
                                mBuilder.setContentTitle("Picture Download")
                                        .setContentText("Download in progress")
                                        .setSmallIcon(R.drawable.download)

                                ;


                                int id = 1;

                                mBuilder.setProgress(100, 10, false);
                                mNotifyManager.notify(id, mBuilder.build());

                                Bitmap DownloadedWallpaper= ImageLoader.getBitmapFromURL(CurrentCatDetail.getItempath());

                                mBuilder.setProgress(100, 50, false);
                                mNotifyManager.notify(id, mBuilder.build());


                                mBuilder.setProgress(100, 80, false);
                                mNotifyManager.notify(id, mBuilder.build());

                                File file = new File(myDir, ImageName);
                               // Log.i(TAG, "" + file);
                                if (file.exists ()) file.delete ();

                                try {
                                     file.createNewFile();
                                    FileOutputStream out = new FileOutputStream(file);

                                    DownloadedWallpaper.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                    out.flush();
                                    out.close();
                                    mBuilder.setProgress(100, 100, false);
                                    mNotifyManager.notify(id, mBuilder.build());


                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    Uri ImageURi=Uri.fromFile(file);
                                    intent.setDataAndType(ImageURi,"image/*");//.parse("file://" + ImageAbsolutePath), "image/*");

                                   // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(myDir)));
                                    PendingIntent pendingIntent = PendingIntent.getActivity(ActivitySingleWallpaper.this, 0,intent ,0);
                                    mBuilder.addAction(R.drawable.download,
                                            "",
                                            pendingIntent);

                                    mBuilder.setContentText("Download complete")
                                            // Removes the progress bar
                                            .setProgress(0,0,false);

                                  //  startActivity(intent,new Bundle());
                                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(myDir)));


                                } catch (Exception e) {
                                    e.printStackTrace();

                                    mBuilder.setContentText("Download Failed!")
                                            // Removes the progress bar
                                            .setProgress(0,0,false);
                                }


                                mNotifyManager.notify(id, mBuilder.build());
                            }
                        }
// Starts the thread by calling the run() method in its Runnable
                ).start();

                Handler h=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if(msg.what==1){

                        }
                        else
                        {

                            Toast.makeText(ActivitySingleWallpaper.this, "Failed To Download, Please Check Internet Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                };



            }
        });


        TextView textView_WallpaperDescription=(TextView)findViewById(R.id.textView_WallpaperDescription);
        textView_WallpaperDescription.setText(CurrentCatDetail.getDescription());

        TextView textView_WallpaperCatagory=(TextView)findViewById(R.id.textView_WallpaperCatagory);
        textView_WallpaperCatagory.setText("Catagory:"+CurrentCatDetail.getCatagoryfolder());

        Button Button_EenlargeWallpaper=(Button)findViewById(R.id.Button_EenlargeWallpaper);
        Button_EenlargeWallpaper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        Button Button_ShareWallpaper=(Button)findViewById(R.id.Button_ShareWallpaper);
        Button_ShareWallpaper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        toolbar_layout =(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
toolbar_layout.setTitle(CurrentCatDetail.getID());

        final Handler H=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bitmap b=(Bitmap)msg.obj;
                Drawable drawable = new BitmapDrawable(getResources(), b);
                toolbar_layout.setBackground(drawable);
            }
        };
        new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    Bitmap bitmap = ImageLoader.getBitmapFromURL(CurrentCatDetail.getItempath());
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    Message m=new Message();
                    m.obj=bitmap;
                    H.sendMessage(m);
                }catch (Exception er)
                {}
            }
        }.start();
    }
    public  Boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return  false;
        }
        else {return true;}
    }

}

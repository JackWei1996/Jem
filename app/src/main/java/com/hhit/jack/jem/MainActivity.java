package com.hhit.jack.jem;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hhit.jack.jem.adapter.YuepuAdapter;
import com.hhit.jack.jem.db.User;
import com.hhit.jack.jem.entity.Yuepu;
import com.hhit.jack.jem.util.ToastUtil;

import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    /**
     * 初始化
     */
    private Button loginout;
    private DrawerLayout mDrawerLayout;
    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout swipeRefresh;
    public static final int TACK_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    NavigationView navView;
    ImageView imageView;
    View headerView;
    List<User> users;
    private List<Yuepu> yuepuList = new ArrayList<>();
    private YuepuAdapter adapter;

    /**
     * 乐谱信息
     */
    private Yuepu[] yuepus = {
            new Yuepu("吉他乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("钢琴乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("二胡乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("爱你一万年乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("哈哈哈乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("通天塔乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("测试乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("呀呀呀乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("你好乐谱", R.mipmap.bellaslullabypg2),
            new Yuepu("呵呵呵乐谱", R.mipmap.bellaslullabypg2)
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void initYuepu() {
        yuepuList.clear();
        for (int i=0; i<50; i++) {
            Random random = new Random();
            int index = random.nextInt(yuepus.length);
            yuepuList.add(yuepus[index]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
//Toolbar设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
//处理头里面的控件
        headerView = navView.getHeaderView(0);
        imageView = (ImageView) headerView.findViewById(R.id.icon_image);
        //View headerView1 = navView.getHeaderView(1);
        TextView name = (TextView) headerView.findViewById(R.id.username);
        //View headerView2 = navView.getHeaderView(2);
        TextView motoo = (TextView) headerView.findViewById(R.id.motto);

//初始化头像和名字
        users = DataSupport.findAll(User.class);
        if (users.size() <= 1) {
            imageView.setImageResource(R.drawable.music);
            name.setText(users.get(0).getName());
        }
//初始化签名
        String url = users.get(0).getImage();
        String mottoString = users.get(0).getSign();
        if (url != null){
            imageView.setImageURI(Uri.fromFile(new File(url)));
        }
        if (mottoString != null) {
            motoo.setText(mottoString);
        }

//打开用户信息录入
        motoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });


//更换头像
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtil.showToast(MainActivity.this, "点击了我的头像");
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else {
                    openAlbum();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);
        }
//menu事件
        navView.setCheckedItem(R.id.collect);
        navView.getHeaderView(R.id.icon_image);
        navView.setNavigationItemSelectedListener(new NavigationView.
            OnNavigationItemSelectedListener(){
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              //mDrawerLayout.closeDrawers();
              switch (item.getItemId()) {
//退出事件
                  case R.id.exit: {
                      users = DataSupport.findAll(User.class);
                      for (User user : users) {
                          user.setStat(false);
                          user.save();
                      }
                      Intent intent = new Intent("com.example.jack.broadcastbeastpractice.FORCE_OFFLINE");
                      sendBroadcast(intent);
                  }break;
//更换头像事件
                  case R.id.photo: {

                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else {
                        openAlbum();
                        }

                  }break;
//收藏事件
                  case R.id.collect: {
                      Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                      startActivity(intent);
                  }break;

                  case R.id.share: {
                      if (ContextCompat.checkSelfPermission(MainActivity.this,
                              Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                          ActivityCompat.requestPermissions(MainActivity.this,
                                  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                      }else {
                          openAlbum();
                      }
                  }break;

              }
              return true;
          }
         });

        initYuepu();    //初始化乐谱

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new YuepuAdapter(yuepuList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }
//刷新
    private void refreshFruits() {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initYuepu();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    //-------------------------------------------------------//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TACK_PHOTO:
                if (resultCode == RESULT_OK) {
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                        users = DataSupport.findAll(User.class);
                        users.get(0).setImage(bitmap.toString());
                        users.get(0).save();
 //Log.e(TAG, "onActivityResult: "+users.get(0).getImage() );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "error!!!!!!!!!!!!",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型Uri，直接获取图片路径即可。
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }

    private  void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            //Log.e(TAG, "+++++++++displayImage: "+imagePath.toString() );
            imageView.setImageBitmap(bitmap);
            users = DataSupport.findAll(User.class);
            users.get(0).setImage(imagePath.toString());
            users.get(0).save();
        } else {
            Toast.makeText(this, "faied to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}

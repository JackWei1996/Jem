package com.hhit.jack.jem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.hhit.jack.jem.db.YuepuCollect;
import com.hhit.jack.jem.entity.Yuepu;
import com.hhit.jack.jem.util.ToastUtil;

import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_yuepu)
public class YuepuActivity extends BaseActivity{

    private static final String TAG = "YuepuActivity";
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    //List<YuepuCollect> yuepuCollects;
    String fruitName;
    int fruitImageId;
    String fruitContent;
    PhotoView mImg1;
    PhotoView mImg2;
    Info mRectF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_yuepu);
        //yuepuCollects = DataSupport.findAll(YuepuCollect.class);

        final Intent intent = getIntent();
        fruitName = intent.getStringExtra(FRUIT_NAME);
        fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        mImg1 = (PhotoView) findViewById(R.id.fruit_image_view);
//        mImg2 = (PhotoView) findViewById(R.id.img2);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        PhotoView fruitImageView = (PhotoView) findViewById(R.id.fruit_image_view);///
        TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        collapsingToolbar.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);    ///
        fruitContent = generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);

        fruitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(YuepuActivity.this, ShowImageActivity.class);
                intent1.putExtra(YuepuActivity.FRUIT_NAME, fruitName);
                intent1.putExtra(YuepuActivity.FRUIT_IMAGE_ID, fruitImageId);
                startActivity(intent1);
            }
        });

    }

//收藏按钮
    @Event(R.id.fba)
    private void collect(View view) {
        YuepuCollect yuepuCollect = new YuepuCollect(fruitName, fruitImageId, fruitContent);
        yuepuCollect.save();
        Log.e(TAG, "collect: "+yuepuCollect.getName() );
        //Log.e(TAG, "--------------collect: " + fruitName );
        
        ToastUtil.showToast(YuepuActivity.this, "收藏成功");
    }


    private String generateFruitContent(String fruiName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruiName);
        }
        return  fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

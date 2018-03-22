package com.hhit.jack.jem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bm.library.PhotoView;

public class ShowImageActivity extends AppCompatActivity {

    private static final String TAG = "YuepuActivity";
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    //List<YuepuCollect> yuepuCollects;
    String fruitName;
    int fruitImageId;
    private PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        imageView = (PhotoView) findViewById(R.id.img1);

        final Intent intent = getIntent();
        fruitName = intent.getStringExtra(FRUIT_NAME);
        fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);

        imageView.setImageResource(fruitImageId);
    }
}

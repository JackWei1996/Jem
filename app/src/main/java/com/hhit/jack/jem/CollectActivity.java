package com.hhit.jack.jem;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhit.jack.jem.adapter.YuepuAdapter;
import com.hhit.jack.jem.db.YuepuCollect;
import com.hhit.jack.jem.entity.Yuepu;
import com.hhit.jack.jem.util.ToastUtil;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_collect)
public class CollectActivity extends BaseActivity{

    private static final String TAG = "CollectActivity";
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    List<YuepuCollect> yuepuCollects;
    String fruitName;
    int fruitImageId;
    String fruitContent;
    private List<Yuepu> yuepuList = new ArrayList<>();
    private YuepuAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connector.getDatabase();
        yuepuCollects = DataSupport.findAll(YuepuCollect.class);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new YuepuAdapter(yuepuList);
        recyclerView.setAdapter(adapter);

        int i = 0;
        Log.e(TAG, "+++++++++++++++++++onCreate: " + yuepuCollects.size() );
        for (YuepuCollect yuepuCollect : yuepuCollects) {
            Yuepu yuepu = new Yuepu();
            yuepu.setName(yuepuCollect.getName());
            yuepu.setTitle(yuepuCollect.getTitle());
            yuepu.setImageId(yuepuCollect.getImageId());
            yuepuList.add(yuepu);
        }
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
                        for (YuepuCollect yuepuCollect : yuepuCollects) {
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
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

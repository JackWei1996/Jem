package com.hhit.jack.jem.db;

import com.hhit.jack.jem.entity.Yuepu;

import org.litepal.crud.DataSupport;

/**
 * Created by 19604 on 3/19/2018.
 */

public class YuepuCollect extends DataSupport{
    private String name;
    private int imageId;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public YuepuCollect(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public YuepuCollect(String name, int imageId,String title) {
        this.name = name;
        this.imageId = imageId;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

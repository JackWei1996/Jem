package com.hhit.jack.jem.entity;

/**
 * Created by 19604 on 3/17/2018.
 */

public class Yuepu {
    private String name;
    private int imageId;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Yuepu() {

    }

    public Yuepu(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public Yuepu(String name, int imageId, String title) {
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

package com.example.myapplication;

public class Model {
    private String name;
    private int img;
    public Model(String name, int img){
        this.img=img;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

package com.github.liyp.mina.demo.imageserver;

public class ImageResponse {
    private int id;
    private String result;

    public ImageResponse() {
    }

    public ImageResponse(int id, String result) {
        super();
        this.id = id;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}

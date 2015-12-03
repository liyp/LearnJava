package com.github.liyp.mina.demo.imageserver;

import java.awt.image.BufferedImage;

public class ImageRequest {
    private int id;
    private String type;
    private BufferedImage image;

    public ImageRequest() {
    }

    public ImageRequest(int id, String type, BufferedImage image) {
        this.id = id;
        this.type = type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}

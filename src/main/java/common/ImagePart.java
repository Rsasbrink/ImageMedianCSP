package common;

import java.io.Serializable;

public class ImagePart implements Serializable {
    int offsetHeight;
    int height;
    int width;

    public ImagePart(int offsetHeight, int height, int width) {
        this.offsetHeight = offsetHeight;
        this.height = height;
        this.width = width;
    }

    public int getOffsetHeight() {
        return offsetHeight;
    }

    public void setOffsetHeight(int offsetHeight) {
        this.offsetHeight = offsetHeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

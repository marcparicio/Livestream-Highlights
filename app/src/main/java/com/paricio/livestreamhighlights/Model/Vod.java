package com.paricio.livestreamhighlights.Model;


import java.io.Serializable;

public class Vod implements Serializable {

    private String id;
    private String url;
    private long offset;
    private String preview_image_url;

    public Vod() {

    }

    public Vod(String id, String url, long offset, String preview_image_url) {
        this.id = id;
        this.url = url;
        this.offset = offset;
        this.preview_image_url = preview_image_url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public long getOffset() {
        return offset;
    }

    public String getPreview_image_url() {
        return preview_image_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setPreview_image_url(String preview_image_url) {
        this.preview_image_url = preview_image_url;
    }
}

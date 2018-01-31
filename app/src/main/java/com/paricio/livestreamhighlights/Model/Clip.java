package com.paricio.livestreamhighlights.Model;



public class Clip {

    private String slug;
    private String tracking_id;
    private String url;
    private String embed_url;
    private String embed_html;
    private Broadcaster broadcaster;
    private Curator curator;
    private Vod vod;
    private String broadcast_id;
    private String game;
    private String language;
    private String title;
    private long views;
    private float duration;
    private String created_at;
    private Thumbnails thumbnails;

    public Clip() {

    }

    public Clip(String slug, String tracking_id, String url, String embed_url, String embed_html, Broadcaster broadcaster,
                Curator curator, Vod vod, String broadcast_id, String game, String language, String title, long views, float duration, String created_at,
                Thumbnails thumbnails) {
        this.slug = slug;
        this.tracking_id = tracking_id;
        this.url = url;
        this.embed_url = embed_url;
        this.embed_html = embed_html;
        this.broadcaster = broadcaster;
        this.curator = curator;
        this.vod = vod;
        this.broadcast_id = broadcast_id;
        this.game = game;
        this.language = language;
        this.title = title;
        this.views = views;
        this.duration = duration;
        this.created_at = created_at;
        this.thumbnails = thumbnails;
    }

    public String getSlug() {
        return slug;
    }

    public String getTracking_id() {
        return tracking_id;
    }

    public String getUrl() {
        return url;
    }

    public String getEmbed_url() {
        return embed_url;
    }

    public String getEmbed_html() {
        return embed_html;
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    public Curator getCurator() {
        return curator;
    }

    public Vod getVod() {
        return vod;
    }

    public String getBroadcast_id() {
        return broadcast_id;
    }

    public String getGame() {
        return game;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public long getViews() {
        return views;
    }

    public float getDuration() {
        return duration;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEmbed_url(String embed_url) {
        this.embed_url = embed_url;
    }

    public void setEmbed_html(String embed_html) {
        this.embed_html = embed_html;
    }

    public void setBroadcaster(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void setCurator(Curator curator) {
        this.curator = curator;
    }

    public void setVod(Vod vod) {
        this.vod = vod;
    }

    public void setBroadcast_id(String broadcast_id) {
        this.broadcast_id = broadcast_id;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}
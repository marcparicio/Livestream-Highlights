package com.paricio.livestreamhighlights.Model;

public class Curator {

    private String id;
    private String name;
    private String display_name;
    private String channel_url;
    private String logo;

    public Curator() {

    }

    public Curator(String id, String name, String display_name, String channel_url, String logo) {
        this.id = id;
        this.name = name;
        this.display_name = display_name;
        this.channel_url = channel_url;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getChannel_url() {
        return channel_url;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setChannel_url(String channel_url) {
        this.channel_url = channel_url;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}

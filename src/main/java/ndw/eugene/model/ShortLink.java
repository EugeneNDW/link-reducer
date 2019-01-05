package ndw.eugene.model;

public class ShortLink {

    private String link;
    private String linkWithoutPrefix;

    public ShortLink() {
    }

    public ShortLink(String controllerPrefix, String link) {
        this.linkWithoutPrefix = link;
        this.link = controllerPrefix+link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkWithoutPrefix() {
        return linkWithoutPrefix;
    }
}

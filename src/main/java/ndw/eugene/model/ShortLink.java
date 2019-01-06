package ndw.eugene.model;

public class ShortLink {

    private String link;

    public ShortLink() {
    }

    public ShortLink(String link) {
        this.link = link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString(){
        return link;
    }
}

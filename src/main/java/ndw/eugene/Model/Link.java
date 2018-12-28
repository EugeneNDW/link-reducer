package ndw.eugene.model;

public class Link {
    private ShortLink link;
    private OriginalLink original;
    private Statistics stat;
    private int rank;

    public Link() {
        this.stat = new Statistics();
    }

    public ShortLink getLink() {
        return link;
    }

    public void setLink(ShortLink link) {
        this.link = link;
    }

    public OriginalLink getOriginal() {
        return original;
    }

    public void setOriginal(OriginalLink original) {
        this.original = original;
    }

    public Statistics getStat() {
        return stat;
    }

    public void countRedirect(){
        stat.increase();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

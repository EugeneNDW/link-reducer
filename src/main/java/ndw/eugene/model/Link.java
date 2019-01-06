package ndw.eugene.model;

public class Link {

    private String prefix;
    private ShortLink link;
    private OriginalLink original;
    private Statistics stat;
    private int rank;

    public Link() {
        this.stat = new Statistics();
    }

    public String getLink() {
        return prefix+link;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getShortLink(){
        return link.getLink();
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getViews() {
        return stat.getCounter();
    }

    public void countRedirect(){

        stat.increase();

    }
}

package ndw.eugene.DTO;

import ndw.eugene.model.Link;

public class LinkDTO {

    private String link;
    private String original;
    private int rank;
    private int count;

    public LinkDTO(Link source) {

        this.link = source.getLink().getLink();
        this.original = source.getOriginal().getOriginal();
        this.rank = source.getRank();
        this.count = source.getStat().getCounter();

    }

    public String getLink() {
        return link;
    }

    public String getOriginal() {
        return original;
    }

    public int getRank() {
        return rank;
    }

    public int getCount() {
        return count;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

package ndw.eugene.dto;

import ndw.eugene.model.Link;

public class LinkDto {

    private String link;
    private String original;
    private int rank;
    private int count;

    public LinkDto(Link source) {
        this.link = source.getLink();
        this.original = source.getOriginal();
        this.rank = source.getRank();
        this.count = source.getViews();
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

package ndw.eugene.dto;

import ndw.eugene.model.Link;

public class ShortLinkDto {
    private String link;

    public ShortLinkDto(Link link) {
        this.link = link.getLink();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

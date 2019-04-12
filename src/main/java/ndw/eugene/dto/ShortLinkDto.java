package ndw.eugene.dto;

import ndw.eugene.model.Link;

public class ShortLinkDTO {
    private String link;

    public ShortLinkDTO(Link link) {
        this.link = link.getLink();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

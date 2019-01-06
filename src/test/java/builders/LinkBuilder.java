package builders;

import ndw.eugene.model.Link;
import ndw.eugene.model.OriginalLink;
import ndw.eugene.model.ShortLink;

public class LinkBuilder {

    private Link link;

    public static LinkBuilder original(String link){

        return new LinkBuilder()
                .originalLink(link)
                .shortLink("defaultShort")
                .rank(-1);

    }

    private LinkBuilder originalLink(String originalLink){

        this.link = new Link();
        this.link.setOriginal(new OriginalLink(originalLink));

        return this;

    }

    public LinkBuilder shortLink(String shortLink){

        this.link.setLink(new ShortLink("/l/",shortLink));

        return this;

    }

    public LinkBuilder rank(int rank){

        this.link.setRank(rank);

        return this;

    }

    public LinkBuilder views(int views){

        for (int i=0; i<views; i++){
            this.link.countRedirect();
        }

        return this;

    }

    public Link build(){
        return link;
    }

}

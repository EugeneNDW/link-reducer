package builders;

import ndw.eugene.model.Link;

public class LinkBuilder {

  private Link link;

  public static LinkBuilder original(String link) {

    return new LinkBuilder()
              .originalLink(link)
              .identifier("defaultShort")
              .rank(-1);
  }

  public LinkBuilder controllerPrefix(String controllerPrefix) {
    this.link.setPrefix(controllerPrefix);

    return this;
  }

  public LinkBuilder identifier(String identifier) {
    this.link.setIdentifier(identifier);

    return this;
  }

  public LinkBuilder rank(int rank) {
    this.link.setRank(rank);

    return this;
  }

  public LinkBuilder views(int views) {
    for (int i = 0; i < views; i++) {
      this.link.countRedirect();
    }
    return this;
  }

  public Link build(){
    return link;
  }

  private LinkBuilder originalLink(String originalLink) {
    this.link = new Link();
    this.link.setOriginal(originalLink);

    return this;
  }
}

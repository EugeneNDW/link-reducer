package ndw.eugene.model;

public class Link {

  private String identifier;
  private String prefix;
  private String original;
  private Statistics stat;
  private int rank;

  public Link() {
    this.stat = new Statistics();
  }

  public String getLink() {
    return prefix + identifier;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getOriginal() {
    return original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getViews() {
    return stat.getViewsCounter();
  }

  public void countRedirect() {
    stat.increaseViewCounter();
  }
}

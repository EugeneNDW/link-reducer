package ndw.eugene.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

  private ReduceService reduceService;
  private Store store;
  private Pattern validLink = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#"
          + "/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

  @Autowired
  public LinkService(ReduceService reduceService, Store linkStore) {
    this.reduceService = reduceService;
    this.store = linkStore;
  }

  public void registerLinkInService(Link original) {
    checkNullLink(original);
    ifLinkInvalidThrowException(original);

    String key = reduceService.reduce(original.getOriginal());
    original.setIdentifier(key);
    store.saveLink(original);
  }

  public String getLinkForRedirect(String identifier) {
    Link l = store.getLink(identifier);

    return l.getOriginal();
  }

  private boolean isMatch(Pattern p, String s) {
    Matcher m = p.matcher(s);
    return m.matches();
  }

  private void ifLinkInvalidThrowException(Link original) {
    if (!isMatch(validLink,original.getOriginal())) {
      throw new IllegalArgumentException("link:" + original.getOriginal() + " is invalid");
    }
  }

  private void checkNullLink(Link link) {
    if (link.getOriginal() == null) {
      throw new IllegalArgumentException("invalid json format");
    }
  }
}

package ndw.eugene.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ndw.eugene.model.Link;
import org.springframework.stereotype.Repository;

@Repository
public class CollectionStore implements Store {
  //todo состояние гонки, почтитать как тестить, как решать, Transactional Spring
  private Map<String, Link> store;

  public CollectionStore() {
    store = new HashMap<>();
  }

  @Override
  public void saveLink(Link link) {
    store.put(link.getIdentifier(), link);
  }

  @Override
    public Link getLink(String identifier) {
    Link result = store.get(identifier);
    if (result == null) {
      throw new LinkNotFoundException("there is no link with such id in the store");
    }
    return result;
  }

  @Override
  public List<Link> getAllLinks() {
    return new ArrayList<>(store.values());
  }

  public Set<String> getKeys() {
    return store.keySet();
  }
}

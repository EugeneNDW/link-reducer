package ndw.eugene.repository;

import java.util.List;
import java.util.Set;
import ndw.eugene.model.Link;

public interface Store {
  void saveLink(Link link);

  Link getLink(String identifier);

  List<Link> getAllLinks();

  Set<String> getKeys();
}

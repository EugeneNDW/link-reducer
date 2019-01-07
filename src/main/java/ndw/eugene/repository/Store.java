package ndw.eugene.repository;

import ndw.eugene.model.Link;

import java.util.List;
import java.util.Set;

public interface Store {
    void saveLink(Link link);
    Link getLink(String identifier);
    List<Link> getAllLinks();
    Set<String> getKeys();
}

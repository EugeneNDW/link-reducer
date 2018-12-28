package ndw.eugene.repository;

import ndw.eugene.model.Link;

import java.util.List;

public interface LinkStore {
    void saveLink(String identifier, Link link);
    Link getLink(String identifier);
    List<Link> getAllLinks();
}

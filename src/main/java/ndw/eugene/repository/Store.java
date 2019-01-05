package ndw.eugene.repository;

import ndw.eugene.model.Link;
import ndw.eugene.model.OriginalLink;
import ndw.eugene.model.ShortLink;

import java.util.List;
import java.util.Set;

public interface Store {

    void saveLink(ShortLink link, OriginalLink original);
    Link getLink(String identifier);
    List<Link> getAllLinks();
    Set<String> getKeys();

}

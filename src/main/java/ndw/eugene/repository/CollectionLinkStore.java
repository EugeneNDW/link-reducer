package ndw.eugene.repository;

import ndw.eugene.model.Link;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CollectionLinkStore implements LinkStore {

    private Map<String, Link> store;

    public CollectionLinkStore() {
        store = new HashMap<>();
    }

    @Override
    public void saveLink(String identifier, Link link){
        store.put(identifier, link);
    }

    @Override
    public Link getLink(String identifier){
        return store.get(identifier);
    }

    @Override
    public List<Link> getAllLinks(){
        return new ArrayList<>(store.values());
    }
}

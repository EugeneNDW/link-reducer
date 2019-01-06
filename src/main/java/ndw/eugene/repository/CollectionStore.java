package ndw.eugene.repository;

import ndw.eugene.model.Link;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CollectionStore implements Store {

    private Map<String, Link> store;

    public CollectionStore() {
        store = new HashMap<>();
    }

    @Override
    public void saveLink(Link l){
        store.put(l.getShortLink(), l);

    }

    @Override
    public Link getLink(String identifier){
        Link result = store.get(identifier);
        if(result==null){
            throw new LinkNotFoundException("there is no link with such id in the store");
        }
        return result;
    }

    //todo пусть сортировка будет здесь
    @Override
    public List<Link> getAllLinks(){
        return new ArrayList<>(store.values());
    }

    public Set<String> getKeys(){
        return store.keySet();
    }
}

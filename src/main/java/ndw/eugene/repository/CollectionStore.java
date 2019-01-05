package ndw.eugene.repository;

import ndw.eugene.model.Link;
import ndw.eugene.model.OriginalLink;
import ndw.eugene.model.ShortLink;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CollectionStore implements Store {

    private Map<String, Link> store;

    public CollectionStore() {
        store = new HashMap<>();
    }

    @Override
    public void saveLink(ShortLink link, OriginalLink original){
        //todo переписать на приём целой модели
        Link l = new Link();
        l.setOriginal(original);
        l.setLink(link);
        store.put(link.getLinkWithoutPrefix(), l);

    }

    //todo найти все места вызова (для теста этих мест)
    @Override
    public Link getLink(String identifier){
        Link result = store.get(identifier);
        if(result==null){
            throw new LinkNotFoundException("there is no link in the store");
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

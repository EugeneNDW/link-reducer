package ndw.eugene.services;

import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    private ReduceService reduceService;
    private Store store;

    @Autowired
    public LinkService(ReduceService reduceService, Store linkStore) {
        this.reduceService = reduceService;
        this.store = linkStore;
    }

    public void registerLinkInService(Link original){
        String key = reduceService.reduce(original.getOriginal());
        original.setIdentifier(key);
        store.saveLink(original);
    }

    public String getLinkForRedirect(String identifier){
        Link l = store.getLink(identifier);

        return l.getOriginal();
    }
}

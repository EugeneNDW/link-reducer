package ndw.eugene.services;

import ndw.eugene.model.Link;
import ndw.eugene.model.OriginalLink;
import ndw.eugene.model.ShortLink;
import ndw.eugene.repository.LinkStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    private static final String LINK_CONTROLLER_PREFIX = "/l/";

    private ReduceService rs;
    private LinkStore store;

    @Autowired
    public LinkService(ReduceService reduceService, LinkStore linkStore) {
        this.rs = reduceService;
        this.store = linkStore;
    }

    public ShortLink addLinkToStore(OriginalLink original){

        Link link = new Link();
        link.setOriginal(original);

        String key = rs.reduce(original.getOriginal());
        ShortLink sl = new ShortLink(LINK_CONTROLLER_PREFIX + key);
        link.setLink(sl);

        store.saveLink(key, link);

        return link.getLink();
    }

    public OriginalLink getRawLinkByShort(String shortLink){
        Link l = store.getLink(shortLink);
        l.countRedirect();
        return l.getOriginal();
    }
}

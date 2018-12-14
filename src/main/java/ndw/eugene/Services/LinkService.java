package ndw.eugene.Services;

import ndw.eugene.Model.OriginalLink;
import ndw.eugene.Model.ShortLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkService {

    private static final String PREFIX = "/l/";
    private ReduceService rs;
    private Map<String, OriginalLink> store;

    @Autowired
    public LinkService(ReduceService reduceService) {
        this.rs = reduceService;
        this.store = new HashMap<>();
    }

    public ShortLink addLinkToStore(OriginalLink original){
        String key = rs.reduce(original.getOriginal());
        store.put(key, original);
        return new ShortLink(PREFIX + key);
    }

    public OriginalLink getRawLinkByShort(String shortLink){
        return store.get(shortLink);
    }
}

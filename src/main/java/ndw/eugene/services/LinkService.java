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

    public String generateShortLink(Link original){
        //todo реализовать валидацию
        linkRedirectValidation(original.getOriginal());

        String key = reduceService.reduce(original.getOriginal());
        original.setIdentifier(key);
        store.saveLink(original);

        return original.getLink();

    }

    public String getLinkForRedirect(String identifier){

        Link l = store.getLink(identifier);

        return l.getOriginal();

    }

    private void linkRedirectValidation(String originalLink){

        if(!isRedirectable(originalLink)){
            throw new IllegalArgumentException();
        }

    }

    private boolean isRedirectable(String originalLink){

        return true;
        //todo найти проверку линка на переходимость(RegExp или Попытка перехода) https://stackoverflow.com/questions/161738/what-is-the-best-regular-expression-to-check-if-a-string-is-a-valid-url
    }
}

package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import ndw.eugene.model.OriginalLink;
import ndw.eugene.model.ShortLink;
import ndw.eugene.repository.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LinkServiceTest {

    private ReduceService reduceService;
    private Store store;
    private LinkService service;

    @BeforeEach
    void init(){

        this.reduceService = createReduceServiceMock();
        this.store = createLinkStoreMock() ;
        this.service = new LinkService(reduceService, store);

    }
    @Test
    void generateShortLink(){

        OriginalLink originalLink = create("http://example.com");

        ShortLink shortLink = service.generateShortLink(originalLink);

        verify(store).saveLink(shortLink, originalLink);
        verify(reduceService).reduce(originalLink.getOriginal());

    }

    @Test
    void getLinkToRedirect_linkInTheStore(){

        Link linkToStore = LinkBuilder.link("long").shortLink("short").rank(1).views(100500).build();
        addLinkToStore(linkToStore);

        Link linkFromStore = store.getLink("short");

        assertEquals(linkToStore, linkFromStore);
        //todo заменить на поведение
    }

    private ReduceService createReduceServiceMock(){

        ReduceService rs = mock(ReduceService.class);
        when(rs.reduce(anyString())).thenReturn("a","b","c","d", "e");

        return rs;

    }

    private Store createLinkStoreMock(){
        return mock(Store.class);
    }

    private OriginalLink create(String original){
        return new OriginalLink(original);
    }

    private void addLinkToStore(Link link){
        when(store.getLink(link.getLink().getLinkWithoutPrefix())).thenReturn(link);
    }

}
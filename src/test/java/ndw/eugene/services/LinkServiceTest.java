package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import ndw.eugene.model.OriginalLink;
import ndw.eugene.model.ShortLink;
import ndw.eugene.repository.LinkNotFoundException;
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
        this.store = createStoreMock() ;
        this.service = new LinkService(reduceService, store);

    }
    @Test
    void generateShortLink(){

        Link link = LinkBuilder.original("http://example.com").build();
        OriginalLink originalLink = link.getOriginal();
        ShortLink shortLink = service.generateShortLink(originalLink, "/l/");
        link.setLink(shortLink);

        //todo добавить equals в Link

        verify(store).saveLink(link);
        verify(reduceService).reduce(originalLink.getOriginal());

    }

    @Test
    void getLinkToRedirect_linkInTheStore(){

        Link linkToStore = LinkBuilder.original("long").shortLink("short").rank(1).views(100500).build();
        addLinkToStore(linkToStore);

        service.getLinkForRedirect("short");

        verify(store).getLink("short");
    }

    private ReduceService createReduceServiceMock(){

        ReduceService rs = mock(ReduceService.class);
        when(rs.reduce(anyString())).thenReturn("a","b","c","d", "e");

        return rs;

    }

    private Store createStoreMock(){
        return mock(Store.class);
    }

    private void addLinkToStore(Link link){
        when(store.getLink(link.getShortLink())).thenReturn(link);
    }

}
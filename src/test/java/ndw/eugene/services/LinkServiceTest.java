package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LinkServiceTest {

    private ReduceService reduceService;
    private Store store;
    private LinkService linkService;

    @BeforeEach
    void init(){
        this.reduceService = createReduceServiceMock();
        this.store = createStoreMock() ;
        this.linkService = new LinkService(reduceService, store);
    }

    @Test
    void generateShortLink(){
        Link link = LinkBuilder.original("http://example.com").build();
        String shortLink = linkService.generateShortLink(link);
        link.setIdentifier(shortLink);

        verify(store).saveLink(link);
        verify(reduceService).reduce(link.getOriginal());
    }

    @Test
    void getLinkToRedirect_linkInTheStore(){
        Link linkToStore = LinkBuilder.original("long").identifier("short").rank(1).views(100500).build();
        addLinkToStore(linkToStore);

        linkService.getLinkForRedirect("short");

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
        when(store.getLink(link.getIdentifier())).thenReturn(link);
    }
}
package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import ndw.eugene.repository.LinkNotFoundException;
import ndw.eugene.repository.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    private Store store;
    private StatisticsService statisticsService;

    @BeforeEach
    void init(){
     this.store = createStoreMock();
     this.statisticsService = new StatisticsService(store);
    }

    @Test
    void getStatisticsById_linkInStore(){
        String originalForTestLink = "http://example.com";
        String identifier = "FCcn";
        Link linkForTest = LinkBuilder.original(originalForTestLink).identifier(identifier).build();

        List<Link> linksToStore = new ArrayList<>();
        linksToStore.add(linkForTest);

        addLinkToStore(linkForTest);
        addListOfLinksToStore(linksToStore);

        Link result = statisticsService.getStatisticsById(linkForTest.getIdentifier());

        assertEquals(originalForTestLink, result.getOriginal());
    }

    @Test
    void getStatisticsById_linkNotInStore(){
        initEmptyStore();

        assertThrows(LinkNotFoundException.class,
                        ()->{statisticsService.getStatisticsById("stat");});

        verify(store).getLink(anyString());
    }

    @Test
    void getPage_defaultValues(){
        int defaultPage = 1;
        int defaultCount = 100;
        int moreThanHundred = 150;

        List<Link> linksToStore = createListWithNLinks(moreThanHundred);
        addListOfLinksToStore(linksToStore);

        List<Link> defaultSizePage = statisticsService.getPage(defaultPage,defaultCount);

        assertEquals(100, defaultSizePage.size());
    }

    @Test
    void getPage_emptyStore(){
        initEmptyStore();

        List pageFromEmptyStore = statisticsService.getPage(1,5);

        assertTrue(pageFromEmptyStore.isEmpty());
    }

    @Test
    void getPage_startsOutOfBound(){
        List<Link> linksToStore = createListWithNLinks(10);
        addListOfLinksToStore(linksToStore);

        List pageOutOfBound = statisticsService.getPage(50,10);

        assertTrue(pageOutOfBound.isEmpty());
    }

    @Test
    void getPage_endsOutOfBound(){
        List<Link> linksToStore = createListWithNLinks(10);
        addListOfLinksToStore(linksToStore);

        List linksOnLastPage = statisticsService.getPage(2,9);

        assertEquals(1,linksOnLastPage.size());
        assertEquals(linksToStore.get(9),linksOnLastPage.get(0));
    }

    @Test
    void getPage_moreThanHundredRecordsOnPage(){
        int moreThanHundred = 150;
        List<Link> linksToStore = createListWithNLinks(moreThanHundred);
        addListOfLinksToStore(linksToStore);

        List<Link> maxSizePage = statisticsService.getPage(1,moreThanHundred);

        assertEquals(100,maxSizePage.size());
    }

    @Test
    void countRedirect_linkInStore(){
        int numberOfViews = 0;
        String identifier = "FCcn";
        String originalLink = "http://example.com";
        Link linkInStore = LinkBuilder.original(originalLink).identifier(identifier).views(numberOfViews).build();
        addLinkToStore(linkInStore);

        statisticsService.countRedirect(identifier);

        assertEquals(numberOfViews+1, linkInStore.getViews());
    }

    @Test
    void countRedirect_linkNotInStore(){
        initEmptyStore();

        assertThrows(LinkNotFoundException.class,()->{ statisticsService.countRedirect("any-link");});
        verify(store).getLink("any-link");
    }


    private Store createStoreMock(){
        return mock(Store.class);
    }

    private void addLinkToStore(Link link){
        when(store.getLink(link.getIdentifier())).thenReturn(link);
    }

    private void initEmptyStore(){
        List<Link> linksInTheStore = new ArrayList<>();
        addListOfLinksToStore(linksInTheStore);
        when(store.getLink(anyString())).thenThrow(LinkNotFoundException.class);
    }

    private void addListOfLinksToStore(List<Link> linkList){
        when(store.getAllLinks()).thenReturn(linkList);
    }

    private List<Link> createListWithNLinks(int numberOfLinks){
        List<Link> dataSet = new ArrayList<>();

        for(int i=0; i<numberOfLinks; i++){
            Link l = LinkBuilder.original("http://example.com/"+i).identifier("ex"+i).views(i).build();
            dataSet.add(l);
        }

        return dataSet;
    }
}

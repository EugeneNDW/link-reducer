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
    void getStatById_linkInStore(){

        Link linkForTest = LinkBuilder.original("http://test.com").build();

        List<Link> linksToStore = new ArrayList<>();
        linksToStore.add(linkForTest);

        addLinkToStore(linkForTest);
        addListOfLinksToStore(linksToStore);

        Link result = statisticsService.getStatById(linkForTest.getShortLink());

        assertEquals("http://test.com", result.getOriginal().getOriginal());
    }

    @Test
    void getStatById_linkNotInStore(){

        initEmptyStore();

        assertThrows(LinkNotFoundException.class,
                        ()->{statisticsService.getStatById("stat");});

        verify(store).getLink(anyString());

    }

    @Test
    void getPage_defaultValues(){

        int defaultPage = 1;
        int defaultCount = 100;
        int moreThanHundred = 150;

        List<Link> linksToStore = createListWithNLinks(moreThanHundred);
        addListOfLinksToStore(linksToStore);

        List<Link> defaultSizePage = statisticsService.getAll(defaultPage,defaultCount);

        assertEquals(100, defaultSizePage.size());
    }

    @Test
    void getPage_emptyStore(){

        List<Link> linksToStore = new ArrayList<>();
        addListOfLinksToStore(linksToStore);

        List pageFromEmptyStore = statisticsService.getAll(1,5);

        assertTrue(pageFromEmptyStore.isEmpty());
    }

    @Test
    void getPage_startsOutOfBound(){

        List<Link> linksToStore = createListWithNLinks(10);
        addListOfLinksToStore(linksToStore);

        List pageOutOfBound = statisticsService.getAll(50,10);

        assertTrue(pageOutOfBound.isEmpty());
    }

    @Test
    void getPage_endsOutOfBound(){

        List<Link> linksToStore = createListWithNLinks(10);
        addListOfLinksToStore(linksToStore);

        List linksOnLastPage = statisticsService.getAll(2,9);

        assertEquals(1,linksOnLastPage.size());
        assertEquals(linksToStore.get(9),linksOnLastPage.get(0));
    }

    @Test
    void getPage_moreThanHundredRecordsOnPage(){

        int moreThanHundred = 150;
        List<Link> linksToStore = createListWithNLinks(moreThanHundred);
        addListOfLinksToStore(linksToStore);

        List<Link> maxSizePage = statisticsService.getAll(1,moreThanHundred);

        assertEquals(100,maxSizePage.size());

    }
    @Test
    void countRedirect_linkInStore(){
        int numberOfViews = 0;
        String shortLink = "exam";
        Link linkInStore = LinkBuilder.original("http://example.com").shortLink(shortLink).views(numberOfViews).build();
        addLinkToStore(linkInStore);

        statisticsService.countRedirect(shortLink);

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
        when(store.getLink(link.getShortLink())).thenReturn(link);
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
            Link l = LinkBuilder.original("http://example.com/"+i).shortLink("ex"+i).views(i).build();
            dataSet.add(l);
        }

        return dataSet;
    }

}

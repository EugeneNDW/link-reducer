package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
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
     this.store = createLinkStoreMock();
     this.statisticsService = new StatisticsService(store);
    }

    @Test
    void getSingleRecord_existsInStore(){

        Link linkForTest = LinkBuilder.link("http://test.com").build();

        List<Link> linksToStore = new ArrayList<>();
        linksToStore.add(linkForTest);

        addLinkToStore(linkForTest);
        addListOfLinksToStore(linksToStore);

        Link result = statisticsService.getStatById(linkForTest.getLink().getLinkWithoutPrefix());

        assertEquals("http://test.com", result.getOriginal().getOriginal());
    }

    @Test
    void getSingleRecord_doesNotExistInStore(){

        List<Link> linksToStore = new ArrayList<>();
        addListOfLinksToStore(linksToStore);

        Link recordWithIdNotInTheStore = statisticsService.getStatById("id not in the store");

        assertNull(recordWithIdNotInTheStore);
    }

    //тест метода, где мы получаем страницу
    // кейс получение обычной страницы записей
    // кейс получение страницы -> изменение просмотров -> получение страницы с переставленными записями
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
    void getPage_afterRecordsPositionChanged(){

    }

    @Test
    void getPage_moreThanHundredRecordsOnPage(){

        int moreThanHundred = 150;
        List<Link> linksToStore = createListWithNLinks(moreThanHundred);
        addListOfLinksToStore(linksToStore);

        List<Link> maxSizePage = statisticsService.getAll(1,moreThanHundred);

        assertEquals(100,maxSizePage.size());
    }

    private Store createLinkStoreMock(){
        return mock(Store.class);
    }

    private void addLinkToStore(Link link){
        when(store.getLink(link.getLink().getLinkWithoutPrefix())).thenReturn(link);
    }

    private void addListOfLinksToStore(List<Link> linkList){
        when(store.getAllLinks()).thenReturn(linkList);
    }

    private List<Link> createListWithNLinks(int numberOfLinks){

        List<Link> dataSet = new ArrayList<>();

        for(int i=0; i<numberOfLinks; i++){
            Link l = LinkBuilder.link("http://example.com/"+i).shortLink("ex"+i).views(i).build();
            dataSet.add(l);
        }

        return dataSet;
    }

    //todo тесты для учёта статистики
}
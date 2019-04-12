package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LinkServiceTest {

  private ReduceService reduceService;
  private Store store;
  private LinkService linkService;
  private StatisticsService statisticsService;

  @BeforeEach
  void init() {
    this.reduceService = createReduceServiceMock();
    this.store = createStoreMock() ;
    this.statisticsService = createStatisticsService();
    this.linkService = new LinkService(reduceService, store, statisticsService);
  }

  @Test
  void generateShortLink() {
    Link link = LinkBuilder.original("http://example.com").build();
    linkService.registerLinkInService(link, "/l/");

    verify(store).saveLink(link);
    verify(reduceService).reduce(link.getOriginal());
  }

  @Test
  void getLinkToRedirect_linkInTheStore() {
    String original = "long";
    String identifier = "short";
    Link linkToStore = LinkBuilder.original(original)
                                  .identifier(identifier)
                                  .rank(1)
                                  .views(100500)
                                  .build();
    addLinkToStore(linkToStore);

    assertEquals(original,linkService.getLinkForRedirect(identifier));

    verify(statisticsService).countRedirect(identifier);
    verify(store).getLink(identifier);
  }

  private ReduceService createReduceServiceMock() {
    ReduceService rs = mock(ReduceService.class);
    when(rs.reduce(anyString())).thenReturn("a","b","c","d", "e");

    return rs;
  }

  private Store createStoreMock() {
    return mock(Store.class);
  }

  private StatisticsService createStatisticsService() {
      return mock(StatisticsService.class);
  }

  private void addLinkToStore(Link link) {
    when(store.getLink(link.getIdentifier())).thenReturn(link);
  }
}
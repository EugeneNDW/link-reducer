package ndw.eugene.repository;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoreTest {

  private Store store;

  @BeforeEach
  void setUp() {
        this.store = new CollectionStore();
    }

  @Test
  void getLink_linkInTheStore(){
    String originalLink = "http://example.com";
    String identifier = "FCcn";
    Link linkToStore = LinkBuilder.original(originalLink).identifier(identifier).build();
    store.saveLink(linkToStore);

    Link linkFromStore = store.getLink(linkToStore.getIdentifier());

    assertEquals(linkToStore,linkFromStore);
  }

  @Test
  void getLink_linkNotInTheStore(){
    String idNotInTheStore = "link-not-in-the-store";

    Throwable exception = assertThrows(LinkNotFoundException.class,()->{store.getLink(idNotInTheStore);});

    assertEquals("there is no link with such id in the store", exception.getMessage());
  }
}
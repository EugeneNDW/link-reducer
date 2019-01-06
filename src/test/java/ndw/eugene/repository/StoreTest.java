package ndw.eugene.repository;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreTest {

    private Store store;

    @BeforeEach
    void setUp() {
        this.store = new CollectionStore();
    }

    @Test
    void getLink_linkInTheStore(){

        Link linkToStore = LinkBuilder.original("http://example.com").shortLink("FCcn").build();
        store.saveLink(linkToStore);

        Link linkFromStore = store.getLink(linkToStore.getShortLink());

        assertEquals(linkToStore,linkFromStore);

    }

    @Test
    void getLink_linkNotInTheStore(){

        String idNotInTheStore = "link-not-in-the-store";

        Throwable exception = assertThrows(LinkNotFoundException.class,()->{store.getLink(idNotInTheStore);});

        assertEquals("there is no such link in the store", exception.getMessage());

    }

}
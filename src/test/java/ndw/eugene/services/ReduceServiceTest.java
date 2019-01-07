package ndw.eugene.services;

import builders.LinkBuilder;
import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class ReduceServiceTest {

    private ReduceService reduceService;
    private Store store;

    @BeforeEach
    void setUp() {
        this.store = createStoreMock();
        this.reduceService = new ReduceService(store);
    }

    @Test
    void generate_linkNotInTheStore(){
        String original = "http://example.com";
        String expectedKey = "FCcn";
        Link link = LinkBuilder.original(original).identifier(expectedKey).build();

        Set<String> keys = new HashSet<>();
        addKeysToStore(keys);

        String actualKey = reduceService.reduce(link.getOriginal());

        assertEquals(expectedKey,actualKey);
    }

    @Test
    void generate_linkAlreadyInTheStore(){
        String original = "http://example.com";
        String expectedKey = "FCcn";

        Link link = LinkBuilder.original(original).identifier(expectedKey).build();

        Set<String> keys = new HashSet<>();
        keys.add(expectedKey);
        addKeysToStore(keys);
        addLinkToStore(link);

        String actualKey = reduceService.reduce(link.getOriginal());

        verify(store).getLink(expectedKey);
        assertEquals(expectedKey, actualKey);
    }

    @Test
    void generate_collision_linkNotInTheStore(){
        String original = "http://example.com";
        String keyForOriginal = "FCcn";
        String expectedKey = "FCco";

        Set<String> keys = new HashSet<>();
        keys.add(keyForOriginal);
        addKeysToStore(keys);

        Link collisionLink = LinkBuilder.original("http://notexample.com").identifier(keyForOriginal).build();
        addLinkToStore(collisionLink);

        String actualKey = reduceService.reduce(original);

        assertEquals(expectedKey,actualKey);
    }

    @Test
    void generate_collision_linkAlreadyInTheStore(){
        String original = "http://example.com";
        String keyForOriginal = "FCcn";
        String nextKey = "FCco";
        String anotherNextKey = "FCcp";
        String expectedKey = "FCcq";

        Set<String> keys = new HashSet<>();
        keys.add(keyForOriginal);
        keys.add(nextKey);
        keys.add(anotherNextKey);
        keys.add(expectedKey);
        addKeysToStore(keys);

        Link collisionLink = LinkBuilder.original("http://notexample.com").identifier(keyForOriginal).build();
        Link nextAfterCollision = LinkBuilder.original("http://nextafter.ru").identifier(nextKey).build();
        Link nextAfterNext = LinkBuilder.original("http://nextafternext.org").identifier(anotherNextKey).build();
        Link testingLink = LinkBuilder.original(original).identifier(expectedKey).build();

        addLinkToStore(collisionLink);
        addLinkToStore(nextAfterCollision);
        addLinkToStore(nextAfterNext);
        addLinkToStore(testingLink);

        String actualKey = reduceService.reduce(original);

        assertEquals(expectedKey, actualKey);
    }

    private Store createStoreMock(){
        return mock(Store.class);
    }

    private void addKeysToStore(Set<String> keys){
        when(store.getKeys()).thenReturn(keys);
    }

    private void addLinkToStore(Link link){
        when(store.getLink(link.getIdentifier())).thenReturn(link);
    }
}
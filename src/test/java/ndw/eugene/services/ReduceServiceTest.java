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

    //кейсы для метода generate
    //ссылка, которой нет в хранилище -> оригинальный ключ
    @Test
    void generate_linkNotInTheStore(){
        //init
        String original = "http://example.com";
        String expectedKey = "FCcn";
        Link link = LinkBuilder.original(original).shortLink(expectedKey).build();

        Set<String> keys = new HashSet<>();
        addKeysToStore(keys);

        //execute
        String actualKey = reduceService.reduce(link.getOriginal().getOriginal());

        //verify
        assertEquals(expectedKey,actualKey);

    }

    //ссылка, которая есть в хранилище ->ключ с которым ссылка уже записана
    @Test
    void generate_linkAlreadyInTheStore(){
        //init
        String original = "http://example.com";
        String expectedKey = "FCcn"; //ключ посчитан вручную из хэша

        Link link = LinkBuilder.original(original).shortLink(expectedKey).build();

        Set<String> keys = new HashSet<>();
        keys.add(expectedKey);
        addKeysToStore(keys);
        addLinkToStore(link);

        //execute
        String actualKey = reduceService.reduce(link.getOriginal().getOriginal());

        //verify
        verify(store).getLink(expectedKey);
        assertEquals(expectedKey, actualKey);
    }

    //ссылка, которой нет в хранилище, но она вызывает коллизию -> ключ с помощью открытой адресации
    @Test
    void generate_collision_linkNotInTheStore(){
        //init
        String original = "http://example.com";
        String keyForOrignal = "FCcn";
        String expectedKey = "FCco";

        Set<String> keys = new HashSet<>();
        keys.add(keyForOrignal);
        addKeysToStore(keys);

        Link collisionLink = LinkBuilder.original("http://notexample.com").shortLink(keyForOrignal).build();
        addLinkToStore(collisionLink);

        //execute
        String actualKey = reduceService.reduce(original);

        //verify
        assertEquals(expectedKey,actualKey);
    }

    //ссылка, которая есть в хранилище, но лежит по другому адресу ->доступ по ключу с открытой адресацией
    @Test
    void generate_collision_linkAlreadyInTheStore(){
        //init
        String original = "http://example.com";
        String keyForOrignal = "FCcn";
        String nextKey = "FCco";
        String anotherNextKey = "FCcp";
        String expectedKey = "FCcq";

        Set<String> keys = new HashSet<>();
        keys.add(keyForOrignal);
        keys.add(nextKey);
        keys.add(anotherNextKey);
        keys.add(expectedKey);
        addKeysToStore(keys);

        Link collisionLink = LinkBuilder.original("http://notexample.com").shortLink(keyForOrignal).build();
        Link nextAfterCollision = LinkBuilder.original("http://nextafter.ru").shortLink(nextKey).build();
        Link nextAfterNext = LinkBuilder.original("http://nextafternext.org").shortLink(anotherNextKey).build();
        Link testingLink = LinkBuilder.original(original).shortLink(expectedKey).build();

        addLinkToStore(collisionLink);
        addLinkToStore(nextAfterCollision);
        addLinkToStore(nextAfterNext);
        addLinkToStore(testingLink);

        //execute
        String actualKey = reduceService.reduce(original);

        //verify
        assertEquals(expectedKey, actualKey);
    }

    private Store createStoreMock(){
        return mock(Store.class);
    }

    private void addKeysToStore(Set<String> keys){
        when(store.getKeys()).thenReturn(keys);
    }

    private void addLinkToStore(Link link){
        when(store.getLink(link.getShortLink())).thenReturn(link);
    }

    //todo рефактор, если хватит времени
}
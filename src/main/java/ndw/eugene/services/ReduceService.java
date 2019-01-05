package ndw.eugene.services;

import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReduceService {

    private Store store;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char[] ALPHABET_ARRAY = ALPHABET.toCharArray();
        //todo решение коллизий при помощи метода открытой адресации?(поиск следующего свободного ключа) https://ru.wikipedia.org/wiki/Хеш-таблица#Открытая_адресация
        //todo тесты11

    @Autowired
    public ReduceService(Store store) {
        this.store = store;
    }

    public String reduce(String original){

        return generateId(original);

    }


    private String generateId(String original){

        int hash = Math.abs(original.hashCode());
        int[] dividedId = divideId(hash);
        String key = encodeIdToString(dividedId);
        Set<String> keyStore = store.getKeys();

        while(keyStore.contains(key)){

            Link linkFromStore = store.getLink(key);

            if (linkFromStore.getOriginal().getOriginal().equals(original)) {
                return key;
            } else {
                key = getNextKey(key);
            }

        }

        return key;

    }

    private String getNextKey(String currentKey){
        int[] dividedKey = decodeIdFromString(currentKey);

        for(int i = dividedKey.length-1; i >= 0; i--){
            if(dividedKey[i]>=ALPHABET_ARRAY.length-1){
                dividedKey[i]=0;
            } else {
                dividedKey[i]++;
                return encodeIdToString(dividedKey);
            }
        }

        return encodeIdToString(dividedKey);
    }

    private int[] decodeIdFromString(String key){

        char[] chars = key.toCharArray();

        StringBuilder result = new StringBuilder();
        for(char ch:chars){
            for(int i = 0; i< ALPHABET_ARRAY.length; i++){
                if(ch== ALPHABET_ARRAY[i]){
                    if(i<10){
                        result.append(0);
                    }
                    result.append(i);
                }
            }
        }

        int k = Integer.parseInt(result.toString());

        return divideId(k);

    }

    private String encodeIdToString(int[] dividedId){

        StringBuilder result = new StringBuilder();

        for(int a:dividedId){

            if(a>= ALPHABET_ARRAY.length){
                a = a% ALPHABET_ARRAY.length;
            }

            result.append(ALPHABET_ARRAY[a]);
        }

        return result.toString();

    }

    private int[] divideId(int key){

        int[] res = new int[4];

        res[3] = key%100;
        res[2] = (key/100)%100;
        res[1] = (key/10000)%100;
        res[0] = (key/1000000)%100;

        return res;

    }
}

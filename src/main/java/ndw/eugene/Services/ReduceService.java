package ndw.eugene.Services;


import org.springframework.stereotype.Service;

@Service
public class ReduceService {

    public String reduce(String rawLink){
        return Integer.toString(rawLink.hashCode());
    }

}

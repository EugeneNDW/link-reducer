package ndw.eugene.controllers;

import ndw.eugene.DTO.LinkDTO;
import ndw.eugene.model.Link;
import ndw.eugene.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{shortLink}")
    public LinkDTO getStatistics(@PathVariable("shortLink") String identifier){
        Link result = statisticsService.getStatisticsById(identifier);

        return toLinkDTO(result);
    }

    @GetMapping("")
    public List<LinkDTO> getAllStatistics(@RequestParam(value = "page", defaultValue = "1")int page,
                                          @RequestParam(value = "count", defaultValue = "100")int count){
        List<Link> result = statisticsService.getPage(page, count);

        return transformToListOfDTOs(result);
    }

    private List<LinkDTO> transformToListOfDTOs(List<Link> list){
        List<LinkDTO> result = new ArrayList<>();
        for(int i = 0; i<list.size();i++){
            result.add(new LinkDTO(list.get(i)));
        }

        return result;
    }

    private LinkDTO toLinkDTO(Link link){
        LinkDTO linkDTO = new LinkDTO(link);

        return linkDTO;
    }
}

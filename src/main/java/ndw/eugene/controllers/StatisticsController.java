package ndw.eugene.controllers;

import ndw.eugene.LinkDTO;
import ndw.eugene.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public LinkDTO getStatistics(@PathVariable("shortLink") String link){
        return statisticsService.getStatById(link);
    }

    @GetMapping("")
    public List<LinkDTO> getAllStatistics(@RequestParam(value = "page", defaultValue = "1")int page,
                                          @RequestParam(value = "count", defaultValue = "100")int count){
        return statisticsService.getAll(page, count);
    }
}

package ndw.eugene.controllers;

import java.util.ArrayList;
import java.util.List;
import ndw.eugene.dto.LinkDto;
import ndw.eugene.model.Link;
import ndw.eugene.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

  private StatisticsService statisticsService;

  @Autowired
  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping("/{shortLink}")
  public LinkDto getStatistics(@PathVariable("shortLink") String identifier) {
    Link result = statisticsService.getStatisticsById(identifier);

    return toLinkDto(result);
  }

  @GetMapping("")
  public List<LinkDto> getAllStatistics(@RequestParam(value = "page", defaultValue = "1")int page,
                                        @RequestParam(value = "count", defaultValue = "100")int count) {
    List<Link> result = statisticsService.getPage(page, count);

    return transformToListOfDtos(result);
  }
  //todo вынесение логики в контроллер

  private List<LinkDto> transformToListOfDtos(List<Link> list) {
    List<LinkDto> result = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      result.add(new LinkDto(list.get(i)));
    }

    return result;
  }

  private LinkDto toLinkDto(Link link) {
    LinkDto linkDto = new LinkDto(link);

    return linkDto;
  }
}

package ndw.eugene.controllers;

import ndw.eugene.dto.ShortLinkDto;
import ndw.eugene.model.Link;
import ndw.eugene.services.LinkService;
import ndw.eugene.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/l")
public class LinkController {

  private static final String CONTROLLER_PREFIX = "/l/";

  private LinkService linkService;
  private StatisticsService statisticsService;

  @Autowired
  public LinkController(LinkService linkService, StatisticsService statisticsService) {
    this.linkService = linkService;
    this.statisticsService = statisticsService;
  }

  @PostMapping("/generate")
  @ResponseBody
  public ShortLinkDto generateReducedLink(@RequestBody Link link) {
    //todo отсутствует обработка принятия пустого JSON
    link.setPrefix(CONTROLLER_PREFIX); //todo вынесение логики в контроллер
    linkService.registerLinkInService(link);

    return toShortLinkDto(link);
  }

  @GetMapping("/{shortLink}")
  public String redirectToOriginalLink(@PathVariable("shortLink") String shortLink) {
    statisticsService.countRedirect(shortLink); //todo вынесение логики  контроллер

    return "redirect:" + linkService.getLinkForRedirect(shortLink);
  }

  private ShortLinkDto toShortLinkDto(Link link) {
    return new ShortLinkDto(link);
  } //todo вынесение логики в контроллер
}

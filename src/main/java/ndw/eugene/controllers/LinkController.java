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

  @Autowired
  public LinkController(LinkService linkService) {
    this.linkService = linkService;
  }

  @PostMapping("/generate")
  @ResponseBody
  public ShortLinkDto generateReducedLink(@RequestBody Link link) {
    linkService.registerLinkInService(link, CONTROLLER_PREFIX);

    return toShortLinkDto(link);
  }

  @GetMapping("/{shortLink}")
  public String redirectToOriginalLink(@PathVariable("shortLink") String shortLink) {
    return "redirect:" + linkService.getLinkForRedirect(shortLink);
  }

  private ShortLinkDto toShortLinkDto(Link link) {
    return new ShortLinkDto(link);
  }
}

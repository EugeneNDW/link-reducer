package ndw.eugene.controllers;

import ndw.eugene.DTO.ShortLinkDTO;
import ndw.eugene.model.Link;
import ndw.eugene.services.LinkService;
import ndw.eugene.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ShortLinkDTO generateReducedLink(@RequestBody Link link){
        link.setPrefix(CONTROLLER_PREFIX);
        linkService.registerLinkInService(link);

        return toShortLinkDTO(link);
    }

    @GetMapping("/{shortLink}")
    public String redirectToOriginalLink(@PathVariable("shortLink") String shortLink){
        statisticsService.countRedirect(shortLink);

        return "redirect:" + linkService.getLinkForRedirect(shortLink);
    }

    private ShortLinkDTO toShortLinkDTO(Link link){
        return new ShortLinkDTO(link);
    }
}
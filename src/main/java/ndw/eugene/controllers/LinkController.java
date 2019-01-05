package ndw.eugene.controllers;

import ndw.eugene.model.OriginalLink;
import ndw.eugene.repository.LinkNotFoundException;
import ndw.eugene.services.LinkService;
import ndw.eugene.model.ShortLink;
import ndw.eugene.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/l")
public class LinkController {

    private LinkService linkService;
    private StatisticsService statisticsService;

    @Autowired
    public LinkController(LinkService linkService, StatisticsService statisticsService) {

        this.linkService = linkService;
        this.statisticsService = statisticsService;

    }

    @PostMapping("/generate")
    @ResponseBody
    public ShortLink generateReducedLink(@RequestBody OriginalLink link){
        return linkService.generateShortLink(link);
    }

    @GetMapping("/{shortLink}")
    public String redirectToOriginalLink(@PathVariable("shortLink") String shortLink){

        statisticsService.countRedirect(shortLink);

        return "redirect:" + linkService.getLinkForRedirect(shortLink).getOriginal();

    }

    @ExceptionHandler(LinkNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity handle(){
        return HttpEntity.EMPTY;
    }

}


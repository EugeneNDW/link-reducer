package ndw.eugene.Controllers;

import ndw.eugene.Model.OriginalLink;
import ndw.eugene.Services.LinkService;
import ndw.eugene.Model.ShortLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/l")
public class LinkController {

    private LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/generate")
    @ResponseBody
    public ShortLink generateReducedLink(@RequestBody OriginalLink link){
        System.out.println(link.getOriginal());
        return linkService.addLinkToStore(link);
    }

    @GetMapping("/{shortLink}")
    public String redirectToOriginalLink(@PathVariable("shortLink") String s){
        return "redirect:" + linkService.getRawLinkByShort(s).getOriginal();
    }
}


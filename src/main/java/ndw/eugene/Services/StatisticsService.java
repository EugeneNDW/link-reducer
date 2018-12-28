package ndw.eugene.services;

import ndw.eugene.LinkDTO;
import ndw.eugene.model.Link;
import ndw.eugene.repository.LinkStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class StatisticsService {

    private LinkStore linkStore;

    private Comparator<Link> comparator = (l1, l2)->{
        if(l1.getStat().getCounter()>l2.getStat().getCounter()){
            return -1;
        } else if(l1.getStat().getCounter()==l2.getStat().getCounter()){
            return 0;
        }
        return 1;
    };

    @Autowired
    public StatisticsService(LinkStore store) {
        this.linkStore = store;
    }

    public LinkDTO getStatById(String shortLink){
        List<Link> sortedListOfLinks = getSortedListOfLinks();
        int rank = sortedListOfLinks.indexOf(linkStore.getLink(shortLink));

        return toLinkDTO(sortedListOfLinks.get(rank), rank+1);
    }

    public List<LinkDTO> getAll(int page, int count){
        List<Link> sortedListOfLinks = getSortedListOfLinks();
        List<Link> currentPage = getPageContent(sortedListOfLinks, page, count);

        return transformToListOfDTOs(currentPage);
    }

    private LinkDTO toLinkDTO(Link link, int rank){
        LinkDTO linkDTO = new LinkDTO(link);
        linkDTO.setRank(rank);

        return linkDTO;
    }

    private List<Link> getSortedListOfLinks(){
        List<Link> list = linkStore.getAllLinks();
        list.sort(comparator);

        for(int i=0; i<list.size(); i++){
            list.get(i).setRank(i+1);
        }

        return list;
    }

    private List<LinkDTO> transformToListOfDTOs(List<Link> list){
        List<LinkDTO> result = new ArrayList<>();
        for(int i = 0; i<list.size();i++){
            result.add(new LinkDTO(list.get(i)));
        }

        return result;
    }

    private List<Link> getPageContent(List<Link> list, int page, int count) {
        int start = getStartPosition(page, count, list.size());
        int end = getEndPosition(page, count, list.size());

        return list.subList(start, end);
    }

    private int getStartPosition(int page, int count, int size){
        int probPosition = calculateStartPosition(page, count);
        return probPosition > size? size : probPosition;
    }


    private int getEndPosition(int page, int count, int size){
        int probPosition = calculateEndPosition(page, count);
        return probPosition > size? size : probPosition;
    }

    private int calculateStartPosition(int page, int count) {
        return (page - 1) * count;
    }

    private int calculateEndPosition(int page, int count){
        return page*count;
    }
}

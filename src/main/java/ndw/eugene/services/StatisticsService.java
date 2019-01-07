package ndw.eugene.services;

import ndw.eugene.model.Link;
import ndw.eugene.repository.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class StatisticsService {

    private Store linkStore;

    @Autowired
    public StatisticsService(Store store) {
        this.linkStore = store;
    }

    public Link getStatById(String identifier){

        List<Link> sortedListOfLinks = getSortedListOfLinks();

        Link link = linkStore.getLink(identifier);

        int rank = sortedListOfLinks.indexOf(link);
        Link l = sortedListOfLinks.get(rank);
        l.setRank(rank+1);

        return l;

    }

    public List<Link> getAll(int page, int count){
        List<Link> sortedListOfLinks = getSortedListOfLinks();
        List<Link> currentPage = getPageContent(sortedListOfLinks, page, count);

        return currentPage;
    }

    public void countRedirect(String identifier){
        Link l = linkStore.getLink(identifier);
        l.countRedirect();
    }


    private List<Link> getSortedListOfLinks(){
        List<Link> list = linkStore.getAllLinks();
        list.sort(linkComparator);

        for(int i=0; i<list.size(); i++){
            list.get(i).setRank(i+1);
        }

        return list;
    }

    private List<Link> getPageContent(List<Link> list, int page, int count) {
        int validCount = getCountInsideBorders(count);
        int start = getStartPosition(page, validCount, list.size());
        int end = getEndPosition(page, validCount, list.size());

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

    private int getCountInsideBorders(int count){
        if (count<1){
            return 1;
        }

        if (count>100){
            return 100;
        }

        return count;
    }

    private Comparator<Link> linkComparator = (l1, l2)->{
        if(l1.getStat().getCounter()>l2.getStat().getCounter()){

            return -1;
        } else if(l1.getStat().getCounter()==l2.getStat().getCounter()){

            return 0;
        }

        return 1;
    };
}

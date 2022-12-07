package searchengine;

import java.util.Comparator;

public class PageComparator implements Comparator<Page> { // sort the list of pages in alphabetical order

    // take the countmap from task 5 , rankingAlgorith - instead of alphabetical
    // order.
    @Override

    /*
     * Compares each page and returns them in ascending order
     */

    public int compare(Page o1, Page o2) {

        String firstString_o1 = o1.getURL();

        String firstString_o2 = o2.getURL();

        return firstString_o1.compareTo(firstString_o2);
    }

}

// //first attempt at task 5 - ranking algorithm. // frequency of change order

// public static List<Page> rankingAlgorithm(List<Page> searchResult) {

// List<String[]> pairList = new ArrayList<>();

// for (String subSearch : subSearches) {
// String[] searchWords = subSearch.split("%20");
// pairList.add(searchWords);
// }

// for (Page page : searchResult) {
// Map<String, Integer> countMap = new HashMap<>();
// for (String[] strings : pairList) {
// for (String string : strings) {
// int wordsPerPage = Collections.frequency(page.getWords(), string);
// countMap.put(string, wordsPerPage);
// }
// }
// }
// return searchResult;
// }

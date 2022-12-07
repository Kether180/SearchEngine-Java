package searchengine;

import java.util.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;

/**
 * make the description of the class here
 * 
 * @see javadoc
 *      https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/
 * 
 * @author simol, gege, madbe, elsb
 * @version 2022.11.23
 * @param - explains the input parameters of the method
 * @return - the expected result of the method
 */

public class SearchEngine {

  private static List<Page> result; // every object is a webpage
  private static InvertedIndex index;
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private static List<HashSet<Page>> setOfPages;
  private static String[] subSearches;

  static {

    index = new InvertedIndex();

  }

  /**
   * 
   * @param sortedlist - Takes a list of pages as input.
   * @Description
   *              Compares each page of the list and ranks them in ascending order
   */

  public static void sortResults(List<Page> sortedlist) {

    Collections.sort(sortedlist, new PageComparator());

  }

  /**
   * 
   * @param io - takes a HttpExchange io and creates a string
   * @return returns the string created from IO
   */

  public static String getSearchTerm(HttpExchange io) {

    String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];

    return searchTerm;
  }

  /**
   * 
   * @param websites - takes a hashSet of pages as input
   *
   * @Description converts a hashset of pages into a map with the inverted index
   *              methods
   *              and maps words to a number of webpages
   * 
   */

  public static void makeIndex(HashSet<Page> websites) {

    index.makeInvertedIndex(websites);

  }

  /**
   * 
   * @param searchTerm
   * @return returns a list of pages
   * @Description
   *              Uses the searchterm string as a key in the inverted index to
   *              lookup the values / webpages and stores them in a set of pages
   * 
   *              afterwards the list of pages is sorted in ascending order.
   */

  // public static List<Page> getSearchResults(String searchTerm) { // -Simon

  // result = new ArrayList<>();

  // HashSet<Page> SetofPages = index.lookup(searchTerm);

  // for (Page page : SetofPages) {

  // result.add(page);

  // }

  // sortResults(result);

  // return result;
  // }

  public static List<Page> getSearchResults(String searchTerm) { // Task 4

    result = new ArrayList<>();

    String[] subSearches = searchTerm.toLowerCase().split("%20or%20"); // split the searchterm , string array different
                                                                       // searchterm

    HashSet<Page> uniqueSearchList = new HashSet<>(); // to avoid duplicates in search

    for (String subsearch : subSearches) {

      // Task 4 multiple search words START
      String[] searchWords = subsearch.split("%20");

      setOfPages = new ArrayList<>(); //

      for (String string : searchWords) {
        setOfPages.add(index.lookup(string)); // method from inverted index

      }

      // Retain overlapping values from each search
      HashSet<Page> overlappingValues = setOfPages.get(0); // created a hashset - setting up to the setOfPages - first
                                                           // list of words ( president USA )
      for (HashSet<Page> set : setOfPages) { // For each page
        overlappingValues.retainAll(set); // keep all and compare overlapping values with the set

      }
      // Task 4 multiple search words END

      // converts overlappingvalue to Arraylist<Pages>
      uniqueSearchList.addAll(overlappingValues); // created a hashset of pages, added all overlaping values - list of
                                                  // all pages that has all the search in it
    }

    result = new ArrayList<Page>(uniqueSearchList); // hashset of pages turned into an array of pages -
    // instead of hashset of pages, it is easier because we working with array, and
    // the rest of the code
    // can read it , we can't return a hashset of pages but we can do an arraylist
    // of pages.
    // sortResults(result);

    return result; // use the result for task 5 , task 5 takes the searchresult as a parameter.
  }

  /**
   * 
   * @param io - takes a HttpExchange io and creates a string
   * @return - returns a list of strings
   * 
   * @Description
   * 
   *              Uses the input IO and uses searches the inverted index to lookup
   *              a key according to the IO string
   *              and returns a list of pages mapped to the key.
   *              Afterwards each found page is then put into the a list called
   *              response, which the method will return
   * 
   */

  // first attempt at task 5 - ranking algorithm.
  public static List<Page> rankingAlgorithm(List<Page> searchResult) {

    List<String[]> pairList = new ArrayList<>();

    for (String subSearch : subSearches) {
      String[] searchWords = subSearch.split("%20");
      pairList.add(searchWords);
    }

    for (Page page : searchResult) {
      Map<String, Integer> countMap = new HashMap<>();
      for (String[] strings : pairList) {
        for (String string : strings) {
          int wordsPerPage = Collections.frequency(page.getWords(), string);
          countMap.put(string, wordsPerPage);
        }
      }
    }
    return searchResult;
  }

  public static List<String> searchHandler(HttpExchange io) { // renamed to handleSearch

    List<String> response = new ArrayList<>();

    for (Page page : getSearchResults(getSearchTerm(io))) {

      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",

          page.getWords().get(0).substring(6), page.getWords().get(1)));

    }

    return response;

  }

  /**
   * 
   * @param response - takes a list of strings as input
   * @return returns a converted list of strings as a byte array
   */

  public static byte[] responseToByteArray(List<String> response) {

    byte[] bytes = response.toString().getBytes(CHARSET);

    return bytes;

  }

}

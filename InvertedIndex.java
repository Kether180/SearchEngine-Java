package searchengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * make description of class here
 * 
 * @see javadoc
 *      https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/
 * 
 * @author simol, gega, madbe, elsb
 * @version 2022.11.23
 * @param - explains the input parameters of the method
 * @return - the expected result of the method
 */

public class InvertedIndex {

    // use more abstract classes in implementation for future use

    private Map<String, HashSet<Page>> index;

    public InvertedIndex() {

        index = new HashMap<>();

    }

    /**
     * 
     * @param searchterm - takes a parameter input string which will be used as a
     *                   searchterm
     * @return - return they values of the key - the searchterm will be the key as a
     *         lookup into the values mapped to the key
     *         if the key is not present in the map, it will return an empty map
     */

    public HashSet<Page> lookup(String searchterm) {

        return index.getOrDefault(searchterm, new HashSet<>());

    }

    /**
     * 
     * @param websites - takes a parameter input hashSet of pages
     * 
     * @Description
     *              Makes an inverted index via a hashmap.
     * 
     *              Takes a set of pages as parameter
     * 
     *              For each page in the set of pages.
     * 
     *              Gets the words from each page and put it as key in the map with
     *              the page as value.
     * 
     *              IF The word already exist, and another page contains the same
     *              word
     *              - add the page to the set mapped to the existing key
     */

    public void makeInvertedIndex(HashSet<Page> websites) {

        for (Page newPage : websites) {

            for (String word : newPage.getWords()) {
                HashSet<Page> existingList = new HashSet<>();

                if (index.containsKey(word)) {

                    index.get(word).add(newPage);

                } else {

                    existingList.add(newPage);

                    index.put(word, existingList);
                }

            }

        }

    }

}

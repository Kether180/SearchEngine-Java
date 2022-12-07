package searchengine;

import java.util.*;

/**
 * Make the description of the class here
 * 
 * @see javadoc
 *      https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/
 * 
 * @author simol, gega, madbe, elsb
 * @version 2022.11.23
 * @param - explains the input parameters of the method
 * @return - the expected result of the method
 */

public class Page {

    private List<String> page;

    public Page() {

        page = new ArrayList<>();

    }

    /*
     * adds wanted strings to the list that will contain a website
     */

    public void addPage(String line) {

        page.add(line);

    }

    /*
     * returns the list of strings from the websites
     */

    public List<String> getWords() {

        return page;
    }

    /*
     * This method returns the URL from the website
     */

    public String getURL() {

        String pageurl = "";

        for (String string : page) {

            if (string.startsWith("*PAGE")) {

                pageurl = string;

            }
        }

        return pageurl;

    }
}

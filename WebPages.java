package searchengine;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

public class WebPages {

  private static List<List<String>> storingRawPages;
  private static HashSet<Page> webpages;
  private static List<String> filecontent;
  private static FileReader reader;

  static {

    storingRawPages = new ArrayList<>();
    webpages = new HashSet<>();
    filecontent = new ArrayList<>();
    reader = new FileReader();

  }

  /**
   * 
   * @param list - takes a list containing lists of strings
   * @Description
   *              Takes each list from the input list and each list from that
   *              specific list will be put into a page object
   *              afterwards the page object will be put into a hashSet containing
   *              all the pages
   * 
   */

  public static void addWebPages(List<List<String>> list) {

    for (List<String> rawPage : list) {

      Page page = new Page();

      for (String string : rawPage) {

        page.addPage(string);

      }

      webpages.add(page);

    }

  }

  /**
   * 
   * @param linenumber - takes an integer
   * @return returns true if the criteria of a webpage is uphold: A webpages must
   *         consist of af weblink, a title and at least one word
   *         returns false is the criterias is not met
   * 
   */

  public static boolean isWebPage(int linenumber) { // Task 2

    String targetString = filecontent.get(linenumber);

    String nextString = filecontent.get(linenumber + 1);

    int lastWord = targetString.lastIndexOf('/');

    String wordsAfterSlash = targetString.substring(lastWord + 1, targetString.length()).replace("_", "\\s");

    String linefromfile = filecontent.get(linenumber);

    if (linefromfile.startsWith("*PAGE") && wordsAfterSlash.equals(nextString) &&
        !filecontent.get(linenumber + 2).startsWith("*PAGE")) {

      return true;

    }

    return false;

  }

  /*
   * Add the lines from file to a list containing each website
   */
  /**
   * 
   * @param filename - takes a filename from a path
   * @throws IOException
   * @Description
   * 
   *              Reads filecontent from a filepath and divides the content into a
   *              list of webpages.
   *              Each webpage is then put into the addPages method, which will
   *              put each website into a set of pages.
   */

  public static void readFile(String filename) throws IOException {

    reader.addFileContent(filename);

    filecontent = reader.getFile();

    int pagenumber = 0;
    int lastpagenumber = 0;

    // Containing no try catch, because we have control over the input stream

    for (int linenumber = 0; linenumber < filecontent.size() - 1; linenumber++) {

      if (isWebPage(linenumber)) {

        storingRawPages.add(filecontent.subList(pagenumber, linenumber));

        pagenumber = linenumber;

      } else if (linenumber == filecontent.size() - 2) {

        lastpagenumber = filecontent.size();

        storingRawPages.add(filecontent.subList(pagenumber, lastpagenumber));

      }

    }

    addWebPages(storingRawPages);

  }

  /**
   * 
   * @return returns the set containing page objects
   */

  public static HashSet<Page> getWebpages() {

    return webpages;

  }

}

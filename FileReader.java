package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileReader class is used to read files from a path
 * The objects will contain the strings obtained from the file in a list.
 * 
 * @see javadoc
 *      https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/
 * 
 * @author simol, gega, madbe, elsb
 * @version 2022.11.23
 * @param - explains the input parameters of the method
 * @return - the expected result of the method
 */

public class FileReader {

    private List<String> listfromfile;

    public FileReader() {

        listfromfile = new ArrayList<>();

    }

    /**
     * 
     * @param filename - takes a file as a parameter
     * 
     *                 reads the file into a list containing the file strings
     * 
     * @throws IOException
     */
    public void addFileContent(String filename) throws IOException { // talk with simon about it :)

        String file = Files.readString(Paths.get(filename)).strip();

        listfromfile = Files.readAllLines(Paths.get(file));

    }

    /**
     * 
     * @return returns the a list of Strings
     * @throws IOException
     */

    public List<String> getFile() throws IOException { // talk with simon about it :)

        return listfromfile;

    }

}

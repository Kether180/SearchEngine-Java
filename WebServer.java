package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * A webserver class....
 * 
 * @see javadoc
 *      https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/
 * 
 * @author simol, gega, madbe, elsb
 * @version 2022.11.23
 * @param - explains the input parameters of the method
 * @return - the expected result of the method
 */

public class WebServer {

  private static final int PORT = 8080;
  private static final int BACKLOG = 0;
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  protected HttpServer server;

  public WebServer(int port) throws IOException {

    WebPages.readFile("config.txt");
    SearchEngine.makeIndex(WebPages.getWebpages());

    server = HttpServer.create(new InetSocketAddress(port), BACKLOG); // created a websocket server

    server.createContext("/", io -> respond(io, 200, "text/html", getWebFile("web/index.html")));
    server.createContext("/search", io -> handleSearch(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getWebFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getWebFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getWebFile("web/style.css")));
    server.start();

    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");

  }

  /**
   * 
   * @param io takes a HttpExchange input
   * @Descriptoin
   *              Takes the io and converts it into a string. The string will be
   *              used as a key in the inverted index which has all the websites
   *              mapped to words.
   *              if the input string is a key inside the map, the websites mapped
   *              to the key will be returned and put into a list. The list is
   *              then converted into a byte array
   *              as the respond method uses the bytes to create webcontent.
   * 
   */

  public void handleSearch(HttpExchange io) {

    List<String> IOresults = new ArrayList<>();

    IOresults = SearchEngine.searchHandler(io);

    respond(io, 200, "application/json", SearchEngine.responseToByteArray(IOresults));

  }

  public byte[] getWebFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  public void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }

  public static int getPort() {
    return PORT;
  }

  public static void main(String[] args) throws IOException {

    new WebServer(WebServer.getPort());

  }
}

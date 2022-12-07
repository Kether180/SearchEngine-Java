# Search Engine

## Task 1 Refactoring

## Task 2 : Modifying the Basic Search Engine

This is a warmup task to get your familiar with the code.

• Modify the program such that if no web pages match the query, the program outputs

“No web page contains the query word.”

• Modify the reading of the data files such that it only creates a web page if both the
title is set and the word list contains at least one word.

word1 fsite1;1; site1;2; : : : ; site1;m1g
word2 fsite2;1; site2;2; : : : ; site2;m2g
...
wordn fsiten;1; siten;2; : : : ; siten;mng

Figure : Abstract view of an inverted index for a database of web pages containing n words
in total. Each word is associated with a list (represented as f: : :g) of web pages that contain
the word. In our notation, word “wordi” is contained in exactly mi web pages.

## Task 3: Faster Queries using an InvertedIndex

This task will make the query algorithm more eficient by providing a better way to search
for web pages containing a certain word.

The prototype searches very ineficiently: It iterates through the list of web pages and collects
those that contain the query word. An inverted index3 allows for better performance
by maintaining a mapping between words and a list of web page objects that contain these
words. See Figure 2 for an example.

• Implement an inverted index, e.g., using a java.util.Map.
• It must be easy to specify which data file the index is to be created from, either
through a constructor or a separate helper method. This should “populate” the index
step-by-step to end up with a representation that has the structure depicted in
Figure 2.

• Your class must have a separate method to lookup a given search term, so that the
index can be tested (and benchmarked).

• Add the implementation to your search engine, to answer queries in the WebServer.
That means, instead of looping over the list of web pages, build the inverted index at
the start of the program, and only search the index to find the web pages that contain
the input word.

## Task 4 : Refined Queries

This task will allow your search engine to work with more complex queries, as it is common
in a search engine.

# Note

This task will be much easier when you make yourself familiar with the methods
provided by Java for String objects, including methods for Sets. And do not forget
regular expressions!

First, look at the following example:
Example
The query
President USA OR Queen Denmark OR Chancellor Germany
should return all web pages “containing” at least one of the following word sets:

(i) President USA
(ii) Queen Denmark
(iii) Chancellor Germany

Here, “containing” means that all of these words can be found on the web page (but
they do not have to occur adjacent to each other).

More specifically, your search engine has to provide the following functionality for a query
string str:

MultipleWords If str has the form4

w1w2 : : :wn,

i.e., it contains n words split by a blank “ ”, a web page matches the query string if and
only if it contains these n words. (However, we do not require that they are adjacent
to each other or in order.

Merging via OR Suppose that str can be decomposed into parts split by the keyword “OR” as follows:

w1;1 : : :w1;m1ORw2;1 : : :w2;m2OR : : :ORwn;1 : : :wn;mn

A web page matches the query if there exists an index i between 1 and n such that
the text on the web page contains each word wi;1, wi;2, . . . , wi;mi .

This means that at least one of these word sequences has the “Multiple Words” property from above.
Advice: Start slowly and extend the functionality step-wise. Try to keep your code extendable.
For example, you could implement these features in the following steps:

(i) Support queries “w1w2 : : :”, without the “OR”.
(ii) Support a single “OR” in the query.
(iii) Support multiple “OR” words in the query.

If you have not gone back to Task 1 yet, you should probably do this by now. Think about
a good architecture for your query engine.

It is recommended to put all the logic in its own
class, for example QueryHandler with one public method getMatchingWebPages(String
query). In there,first, decompose the query into its components. For a single word, the
query still retrieves lists of web pages from the inverted index.

For the multiple words feature, a collection of lists must be checked for web pages that appear in all lists. The “OR”
feature is easiest implemented by merging the results of multiple word queries. Make sure
to remove duplicates, i.e., a web page is only allowed to be part of the result at most once!
Note that the query handling should be done in a separate class. It uses the InvertedIndex,
but does not change it!

## Task 5: Ranking Algorithms

In this task you will improve the results of your search engine by providing ways to rank a
list of web pages with regard to their importance for a specific query.

The idea is simple: If a web page S contains a word w, we compute a number s(w; S) (the
“score”) that shows how important this word is on the web page; the higher the number,
the more important the word. Have a look at Wikipedia6 to see the descriptions of some
well-known approaches.

Example
Assume the query is

President USA OR Queen Denmark OR Chancellor Germany

Further assumewe come up with the following scores of thewords on oneweb pages
(they are made up here):

President USA Queen Denmark Chancellor Germany

12 12 1 26 2 3

Computation of score:

• each part (split by OR): add scores together
President USA Queen Denmark Chancellor Germany
24 27 5
• Final score for web page with respect to query: take the maximum!
• score for this web page for the query is 27

1. You will need to come up with a suitable abstraction over the concept of a scoring
   method, so you can swap between different scoring methods, without changing too
   much code. Do not worry if you do not get it right on the first try; that’s what
   refactoring is for.

2. Implement the term frequency score to fit your abstraction.

3. Implement the term frequency-inverse document score to fit your abstraction.

4. Modify your program such that each web page that matches the query is assigned a
   score. For a multiple-word query, the score of the web page is the sum of its score
   values for all words in the query. For an OR between two multiple-word queries, the
   score of the web page is the maximum of the scores in these two queries.

5. Modify your program such that the list of web pages that match a query is sorted by
   their score to the query in descending order.

6. Provide queries and their results that show differences in the result quality that is
   obtained by using these two score functions. Give a short description of how they
   differ.

Challenge ( if you have time )

Implement the Okapi BM25 ranking algorithm.

(https://en.wikipedia.org/wiki/Okapi_BM25)

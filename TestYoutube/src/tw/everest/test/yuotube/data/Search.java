package tw.everest.test.yuotube.data;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import tw.everest.test.yuotube.Auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Search {

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */
    private static final String PROPERTIES_FILENAME = "/resources/youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    /**
     * Initialize a YouTube object to search for videos on YouTube. Then
     * display the name and thumbnail image of each video in the result set.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
        // Read the developer key from the properties file.
        Properties properties = new Properties();
        try {
            InputStream in = Search.class.getResourceAsStream( PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            // Prompt the user to enter a query term.
            String queryTerm = "Sweets Run 甜蜜路跑";//getInputQuery();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the Google Developers Console for
            // non-authenticated requests. See:
            // https://console.developers.google.com/
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            //search.setQ(queryTerm);
            search.setChannelId("UCsHh-i-ms6kVMAfsCN9WPuA");

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");//video channel playlist

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,id/channelId,"
            		+ "snippet/publishedAt,snippet/description,snippet/title,"
            		+ "snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            search.setRegionCode("TW");
            search.setRelevanceLanguage("zh-Hant");

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), queryTerm);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /*
     * Prompt the user to enter a query term and return the user-specified term.
     */
    private static String getInputQuery() throws IOException {

        String inputQuery = "";

        System.out.print("Please enter a search term: ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        inputQuery = bReader.readLine();

        if (inputQuery.length() < 1) {
            // Use the string "YouTube Developers Live" as a default.
            inputQuery = "YouTube Developers Live";
        }
        return inputQuery;
    }

    /*
     * Prints out all results in the Iterator. For each result, print the
     * title, video ID, and thumbnail.
     *
     * @param iteratorSearchResults Iterator of SearchResults to print
     *
     * @param query Search query (String)
     */
    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + "  for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult single = iteratorSearchResults.next();                                    
            ResourceId rId = single.getId();            

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = single.getSnippet().getThumbnails().getDefault();

                System.out.println(" Video Id: " + rId.getVideoId());
                System.out.println(" Title: " + single.getSnippet().getTitle());//description
                System.out.println(" Desc: " + single.getSnippet().getDescription());
                System.out.println(" Date: " + single.getSnippet().getPublishedAt());
                System.out.println(" Thumbnail size: " + single.getSnippet().getThumbnails().size()); 
                System.out.println(" Thumbnail: " + thumbnail.getUrl());              
                System.out.println("\n-------------------------------------------------------------\n");
            }else{
            	  System.out.println(" Id: " + rId.getChannelId());
                  System.out.println(" Title: " + single.getSnippet().getTitle());
                  System.out.println("\n");
            }
        }
    }
}

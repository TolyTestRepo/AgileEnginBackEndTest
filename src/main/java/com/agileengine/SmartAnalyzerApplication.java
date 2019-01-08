package com.agileengine;

import com.agileengine.service.SearchService;
import org.jsoup.nodes.Element;

import java.io.File;

public class SmartAnalyzerApplication {
    private static final String DEFAULT_SEARCH_ELEMENT = "make-everything-ok-button";

    public static void main(String[] args) {
        final SearchService searchService = new SearchService();
        final File originalFile;
        final File otherSampleFile;
        final String searchElement;

        if (args.length == 2 || args.length == 3) {
            originalFile = new File(args[0]);
            otherSampleFile = new File(args[1]);
            searchElement = args.length == 3 ? args[2] : DEFAULT_SEARCH_ELEMENT;
        } else {
            throw new IllegalArgumentException("Incorrect number of arguments! Input parameters should be: " +
                    "<input_origin_file_path> <input_other_sample_file_path> <id_of_search_element>");
        }
        final Element similarElement = searchService.findSimilarElement(originalFile, otherSampleFile, searchElement);
        if (similarElement != null) {
            System.out.println("Analyzer found similar element:" + similarElement);
            System.out.println("Path to this element:" + similarElement.cssSelector());
        } else {
            System.out.println("Can't find similar element!");
        }

    }
}

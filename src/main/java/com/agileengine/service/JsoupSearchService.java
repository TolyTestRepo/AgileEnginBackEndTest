package com.agileengine.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class JsoupSearchService {
    private static String CHARSET_NAME = "utf8";

    static Optional<Element> findElementById(final File htmlFile, final String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    static Optional<Elements> findElementsByTag(final File htmlFile, final String tagName){
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementsByTag(tagName));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

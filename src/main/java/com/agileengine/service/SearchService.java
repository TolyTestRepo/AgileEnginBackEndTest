package com.agileengine.service;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchService {

    public Element findSimilarElement(final File originalFile, final File otherSampleFile, final String searchElement) {
        final Optional<Element> originalOptionalElement = JsoupSearchService.findElementById(originalFile, searchElement);
        final Element originalElement = originalOptionalElement.orElseThrow(() -> new NoSuchElementException("Element " + searchElement + " not found in " +
                "origin file(" + originalFile.getAbsolutePath() + "), please check entered variables!"));
        final Optional<Elements> sameTagOptionalElements = JsoupSearchService.
                findElementsByTag(otherSampleFile, originalElement.tagName());
        final Elements sameTagElements = sameTagOptionalElements.orElseThrow(() -> new NoSuchElementException("Can't find similar element in " + otherSampleFile.getName()));

        return searchNeededElement(originalElement, sameTagElements);
    }

    private static Element searchNeededElement(final Element originalElement, final Elements sameTagElements) {
        final List<Attribute> originalAttributes = originalElement.attributes().asList();
        final AtomicReference<Element> resultElement = new AtomicReference<>();
        final long[] bestIntersection = {0};
        sameTagElements.forEach(e -> {
                    final List<Attribute> sameElements = e.attributes().asList();
                    final long intersection = originalAttributes.stream().filter(sameElements::contains).count();
                    if (intersection > bestIntersection[0]) {
                        bestIntersection[0] = intersection;
                        resultElement.set(e);
                    }
                }
        );
        return resultElement.get();
    }
}

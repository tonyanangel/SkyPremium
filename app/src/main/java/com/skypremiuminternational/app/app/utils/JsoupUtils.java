package com.skypremiuminternational.app.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by angebagui on 06/08/2016.
 */

public class JsoupUtils {


  public static boolean isParagraph(Element element) {
    return element.tagName().equals("p");
  }

  public static boolean isIFrame(Element element) {
    return element.tagName().equals("iframe");
  }

  public static Elements getImageElements(Element element) {
    Elements elements = new Elements();
    if (element.tagName().equals("img")) {
      elements.add(element);
      return elements;
    }
    if ((element.getElementsByTag("img") != null
        && element.getElementsByTag("img").size() > 0)) {
      return element.getElementsByTag("img");
    }
    return elements;
  }

  public static boolean isImage(Element element) {
    return element.tagName().equals("img");
  }

  public static boolean isHeader(Element element) {
    return element.tagName().equals("h");
  }

  public static boolean isBlockquote(Element element) {
    return element.tagName().equals("blockquote");
  }

  public static boolean isDiv(Element element) {
    return element.tagName().equals("div");
  }

  public static boolean hasChildren(Element element) {
    return element.children().size() > 0;
  }


  public static List<String> findAllVideoLinks(String content) {
    final List<String> links = new ArrayList<>();
    final Document document = Jsoup.parse(content);
    Elements medias = document.select("[src]");
    for (Element element : medias) {
      if (element.tagName().equals("iframe")) {
        links.add(element.attr("abs:src"));
      } else {

      }
    }
    return links;
  }

  public static List<String> findAllImagesLinks(String content) {
    final List<String> links = new ArrayList<>();
    final Document document = Jsoup.parse(content);
    Elements medias = document.select("[src]");
    for (Element element : medias) {
      if (element.tagName().equals("img")) {
        links.add(element.attr("abs:src"));
      } else {

      }
    }
    return links;
  }
}

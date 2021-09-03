package space.forloop.airnzspider.services;

import org.jsoup.nodes.Document;

public interface SeleniumService {

  void crawl();

  void clickArrow();

  void completeSearchForm();

  void close();

  Document getPageSource();
}

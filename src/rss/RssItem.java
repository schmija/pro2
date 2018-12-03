package rss;

import model.FeedItem;

public class RssItem {

    private FeedItem feedItem; //zdroj ze kterého pochází
    private String title, link, description, pubDate;

    //<pubDate>Mon, 26 Nov 2018 08:10:36 +0100</pubDate>

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public FeedItem getFeedItem() {
        return feedItem;
    }

    public void setFeedItem(FeedItem feedItem) {
        this.feedItem = feedItem;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s", title, link, description);
    }
}

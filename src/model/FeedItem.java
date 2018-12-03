package model;

public class FeedItem {

    String url;
    long addedMillis;
    boolean shouldShow;
    String alias;

    private FeedItem() {
    }

    public FeedItem(String url) {
        this.url = url;
        this.shouldShow = true;
        this.addedMillis = System.currentTimeMillis();
        this.alias = "Alias todo";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getAddedMillis() {
        return addedMillis;
    }

    public void setAddedMillis(long addedMillis) {
        this.addedMillis = addedMillis;
    }

    public boolean isShouldShow() {
        return shouldShow;
    }

    public void setShouldShow(boolean shouldShow) {
        this.shouldShow = shouldShow;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s",
                url, addedMillis, shouldShow, alias);
    }

    public static FeedItem parseFromCSV(String line) {
        // fixme ošetřit počet hodnot
        String[] values = line.split(";");
        FeedItem feedItem = new FeedItem();
        feedItem.setUrl(values[0]);
        feedItem.setAddedMillis(Long.parseLong(values[1]));
        feedItem.setShouldShow(Boolean.parseBoolean(values[2]));
        feedItem.setAlias(values[3]);
        return feedItem;
    }
}

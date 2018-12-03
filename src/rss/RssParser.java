package rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssParser {

    InputStream inputStream;

    public RssParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<RssItem> parseItems() {
        List<RssItem> items = new ArrayList<>();
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = saxParserFactory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {

                RssItem item;
                boolean title, link, description, pubDate;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {
                        item = new RssItem();
                    }
                    if (qName.equalsIgnoreCase("title") && item != null) {
                        title = true;
                    }
                    if (qName.equalsIgnoreCase("link") && item != null) {
                        link = true;
                    }
                    if (qName.equalsIgnoreCase("description") && item != null) {
                        description = true;
                    }
                    if (qName.equalsIgnoreCase("pubdate") && item != null){
                        pubDate = true;
                    }
                    super.startElement(uri, localName, qName, attributes);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {
                        items.add(item);
                    }
                    super.endElement(uri, localName, qName);
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (title) {
                        item.setTitle(new String(ch, start, length));
                        title = false;
                    }
                    if (link) {
                        item.setLink(new String(ch, start, length));
                        link = false;
                    }
                    if (description) {
                        item.setDescription(new String(ch, start, length));
                        description = false;
                    }
                    if (pubDate){
                        item.setPubDate(new String(ch, start, length));
                        pubDate = false;
                    }
                    super.characters(ch, start, length);
                }
            };

            parser.parse(inputStream, handler);

        } catch (Exception e) {
            //ignored
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return items;
        }

        //http://www.eurofotbal.cz/feed/rss/premier-league/
    }
}

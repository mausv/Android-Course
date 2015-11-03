package com.mauriciosilva.idbrss;

import android.app.Application;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Mauricio on 11/3/2015.
 */
public class ParseNews {
    private String xmlData;
    private ArrayList<News> news;

    public ParseNews(String xmlData) {
        this.xmlData = xmlData;
        this.news = new ArrayList<>();
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public boolean process() {
        boolean status = true;
        News currentNews = null;
        boolean inItem = false;
        String textValue = "";

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if(tagName.equalsIgnoreCase("item")){
                            inItem = true;
                            currentNews = new News();
                        }

                        break;

                    case XmlPullParser.TEXT:

                        textValue = xpp.getText();

                        break;

                    case XmlPullParser.END_TAG:

                        if(inItem) {

                            if (tagName.equalsIgnoreCase("item")) {
                                news.add(currentNews);
                                inItem = false;
                            } else if (tagName.equalsIgnoreCase("title")){
                                currentNews.setName(textValue);
                            } else if (tagName.equalsIgnoreCase("link")) {
                                currentNews.setLink(textValue);
                            } else if (tagName.equalsIgnoreCase("dc:creator")) {
                                currentNews.setAuthor(textValue);
                            }

                        }

                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            Log.d("ParseNews", e.getMessage());
        }

        for(News newsC : news) {
            Log.d("ParseApplications", "************");
            Log.d("ParseApplications", "Name: " + newsC.getName());
            Log.d("ParseApplications", "Artist: " + newsC.getAuthor());
            Log.d("ParseApplications", "Release Date: " + newsC.getLink());
        }


        return status;
    }
}

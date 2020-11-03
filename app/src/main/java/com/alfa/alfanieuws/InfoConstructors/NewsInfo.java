package com.alfa.alfanieuws.InfoConstructors;

public class NewsInfo {
    protected int news_id;
    protected String news_title;
    protected String news_text;
    protected String news_date;
    protected byte[] news_picture;


    public NewsInfo(int news_id, String news_title, String news_text, String news_date, byte[] news_picture) {
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_text = news_text;
        this.news_date = news_date;
        this.news_picture = news_picture;
    }

    public int getNews_id() {
        return news_id;
    }

    public String getNews_title() {
        return news_title;
    }


    public String getNews_date() {
        return news_date;
    }

    public byte[] getNews_picture() {
        return news_picture;
    }

    public String getNews_text() {
        return news_text;
    }
}

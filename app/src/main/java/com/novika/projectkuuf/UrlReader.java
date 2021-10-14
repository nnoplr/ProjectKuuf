package com.novika.projectkuuf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReader {

    private static String result;

    public static String main(String[] args) throws Exception
    {
        String urlString = "https://api.jsonbin.io/b/5eb51c6947a2266b1474d701/7";
        URL url = new URL(urlString);

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        result = url.toString();

        return null;
    }

    public String getUrlReader() {
        return result;
    }
}
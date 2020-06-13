package com.example.androidhandin.Networking;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class QuoteApi extends AsyncTask<Void, Void, String> {

    private String message;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpResponse<String> response = Unirest.get("https://quotes.rest/qod?language=en")
                    .asString();
            Gson gson = new Gson();
            Response jsonResponse = gson.fromJson(response.getBody(), Response.class);
            String quote = jsonResponse.getContents().getQuotes().get(0).getQuote();
            String author = jsonResponse.getContents().getQuotes().get(0).getAuthor();
            if (author == null) {
                author = "Unknown";
            }
            String finalFormat = quote + " ~ " + author;
            return finalFormat;

        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Amount of requests per hour exceeded. You can make only 10 requests per hour");
            return null;
        }
    }
}

package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.quotable.io/random?minLength=100"))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("API Call confirmation:\n");
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Headers: " + response.headers().allValues("content-type"));
            System.out.println("Body: " + response.body() + "\n");

            System.out.println("Quote of the Day:\n");

            String quote = response.body();

            int contentBeginning = quote.indexOf("content") + 10;
            int authorBeginning = quote.indexOf("author") + 9;
            int contentEnd = authorBeginning - 12;
            int authorEnd = quote.indexOf("tags") - 3;

            String content = quote.substring(contentBeginning, contentEnd);
            String author = quote.substring(authorBeginning, authorEnd);

            System.out.println(content + " - " + author);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
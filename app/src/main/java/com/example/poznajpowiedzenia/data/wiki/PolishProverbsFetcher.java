package com.example.poznajpowiedzenia.data.wiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PolishProverbsFetcher {

    public static List<Proverb> fetchFromWiki() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> proverbs = new ArrayList<>();
        List<String> proverbsLinkList = fetchProverbsLinkList();
        List<Proverb> proverbsList = new ArrayList<>();
        for (int i = 0; i < proverbsLinkList.size(); i++) {
            String proverbWebsiteSource = getWebsiteSource("https://pl.wiktionary.org" + proverbsLinkList.get(i));
            String proverbTitle = readProverbTitle(proverbWebsiteSource);
            String proverbMeaning = readProverbMeaning(proverbWebsiteSource);
            proverbsList.add(new Proverb(proverbTitle, proverbMeaning));
            proverbs.add(objectMapper.writeValueAsString(new Proverb(proverbTitle, proverbMeaning)));
        }
        return proverbsList;
    }

        private static List<String> fetchProverbsLinkList() throws IOException {
            String content = getWebsiteSource("https://pl.wiktionary.org/wiki/Kategoria:Polskie_przys%C5%82owia");
            int startingIndex = content.indexOf("<div class=\"mw-category-group\">")  + "<div class=\"mw-category-group\">".length();
            int endingIndex = content.indexOf("</div>(poprzednia strona)", startingIndex);
            String rawProverbLink = content.substring(startingIndex, endingIndex);
            List<String> links = new ArrayList<>();
            boolean skipFirst = true;
            while(true)
            {
                int startingIndexLink = rawProverbLink.indexOf("<a href=\"")  + "<a href=\"".length();
                if (startingIndexLink == (-1 + "<a href=\"".length())) {
                    break;
                }
                int endingIndexLink = rawProverbLink.indexOf("\" title", startingIndexLink);
                if (endingIndexLink == -1) {
                    break;
                }
                if (skipFirst) {
                    skipFirst = false;
                    rawProverbLink = rawProverbLink.substring(endingIndexLink);
                    continue;
                }
                links.add(rawProverbLink.substring(startingIndexLink, endingIndexLink));
                rawProverbLink = rawProverbLink.substring(endingIndexLink);
            }
            return links;
        }

    private static String readProverbMeaning(String proverbWebsiteSource) {
        int startingIndex = proverbWebsiteSource.indexOf("<dd>(1.1)")  + "<dd>(1.1)".length();
        int endingIndex = proverbWebsiteSource.indexOf("</dd>", startingIndex);
        String meaning = proverbWebsiteSource.substring(startingIndex, endingIndex);
        boolean isValid = false;
        List<Character> meaningFinal = new ArrayList<>();
        for (int i = 0; i < meaning.length();i++) {
            if (meaning.charAt(i) == '<' || meaning.charAt(i) == '&') {
                isValid = false;
            }
            if (isValid) {
                meaningFinal.add(meaning.charAt(i));
            }
            if (meaning.charAt(i) == '>') {
                isValid = true;
            }
        }
        return meaningFinal.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private static String readProverbTitle(String proverbWebsiteSource) {
        int startingIndex = proverbWebsiteSource.indexOf("<title>")  + "<title>".length();
        int endingIndex = proverbWebsiteSource.indexOf(" – Wikisłownik, wolny słownik wielojęzyczny", startingIndex);
        return proverbWebsiteSource.substring(startingIndex, endingIndex);
    }

    private static String getWebsiteSource(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}

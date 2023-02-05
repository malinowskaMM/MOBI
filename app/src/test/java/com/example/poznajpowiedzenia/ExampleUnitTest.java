package com.example.poznajpowiedzenia;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.poznajpowiedzenia.data.wiki.PolishProverbsFetcher;

import java.io.IOException;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException {
        List<String> result = PolishProverbsFetcher.fetchFromWiki();
        System.out.printf(result.toString());
        assertEquals(4, 2 + 2);
    }
}
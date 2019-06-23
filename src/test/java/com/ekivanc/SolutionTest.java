package com.ekivanc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.ekivanc.parser.*;
import com.ekivanc.util.Constants;
import com.ekivanc.util.ResourceFile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Unit test for simple Solution
 */
public class SolutionTest 
{

    @Rule
    public ResourceFile googleSearchResourceFile = new ResourceFile("/GoogleSampleResultPage.html");

    @Rule
    public ResourceFile scriptLibResourceFile = new ResourceFile("/JSLibrarySamplePage.html");

    HtmlParserFactory parserFactory;

    @Before
    public void init() {
        parserFactory  = new HtmlParserFactory();

    }

    /**
     * Type check for Google Parser
     */
    @Test
    public void parserGoogleTypeTest()
    {
        HtmlParser googleParser = parserFactory.create(ParserType.GOOGLE);
        assertTrue (googleParser instanceof Parser4Google);
    }

    /**
     * Type check for JScript Parser
     */
    @Test
    public void parserScriptTypeTest() {
        HtmlParser scriptParser = parserFactory.create(ParserType.SCRIPT);
        assertTrue (scriptParser instanceof Parser4JScript);
    }


    @Test
    public void googleSearchResultParserTest() throws IOException {
        final String sampleURL=  "https://onedio.com/testler";
        String googleSampleResultPage = googleSearchResourceFile.getContent();
        Parser4Google scriptParser = (Parser4Google) parserFactory.create(ParserType.GOOGLE);
        List<String> parsedResult = scriptParser.parse(googleSampleResultPage, Constants.PATTERN_GOOGLE_PAGE_RESULT_URL_START.getValue(),  Constants.PATTERN_GOOGLE_PAGE_RESULT_URL_END.getValue());
        assertTrue(parsedResult.size()>0);
        assertTrue(parsedResult.get(0).equals(sampleURL));
    }


    @Test
    public void JScriptLibraryParserTest() throws IOException {
        final String sampleScriptLib=  "gtm.js";
        final int jscriptLibraryNum = 23;

        String scriptSamplePage = scriptLibResourceFile.getContent();
        Parser4JScript scriptParser = (Parser4JScript) parserFactory.create(ParserType.SCRIPT);
        List<String> parsedResult = scriptParser.parse(scriptSamplePage, Constants.PATTERN_JS_PAGE_RESULT_URL_START.getValue(), Constants.PATTERN_JS_PAGE_RESULT_URL_END.getValue());
        assertTrue(parsedResult.size() == jscriptLibraryNum );
        assertTrue(parsedResult.get(0).equals(sampleScriptLib));
    }

    /**
     * checks if URL is in correct regex format
     */
    @Test
    public void getGoogleSearchURLTest()
    {
        String testUrl = Solution.getGoogleSearchURL("test");
        assertTrue(testUrl.matches("(https?://)([^:^/]*)(:\\d*)?(.*)?"));
    }

    @Test
    public void getGooglePageSourceCodeTest() throws InterruptedException, ExecutionException {
        String testUrl =  Solution.getGoogleSearchURL("smth2Search");
        String googlePageSourceCode = Solution.getGooglePageSourceCode(testUrl);
        assertNotNull(googlePageSourceCode);
    }



}

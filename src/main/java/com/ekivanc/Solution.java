package com.ekivanc;

import com.ekivanc.provider.PageSourceProvider;
import com.ekivanc.entity.LibraryCrawler;
import com.ekivanc.parser.HtmlParser;
import com.ekivanc.parser.HtmlParserFactory;
import com.ekivanc.parser.ParserType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.ekivanc.util.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * 
 * @author ekivanc
 */
public class Solution {

	private static final Logger logger = LogManager.getLogger(Solution.class);

	// Actually there is a google search rest API provided by Google,
	// but because the assessment is based on developing a crawler, I choose this
	// way.
	public static void main(String[] args) throws Exception {
		
		/**
		 * TASK-0: Read a string (search term) from standard input
		 */
		final String searchWord = readFromConsole();
		
		final String searchUrl = getGoogleSearchURL(searchWord);
		
		/**
		 * TASK-1: Get a Google result page for the Search Term
		 */
		String googlePageSourceCode = getGooglePageSourceCode(searchUrl);

		// Abstract factory pattern is used for populating different parsers
		HtmlParserFactory parserFactory = new HtmlParserFactory();
		HtmlParser googleParser = parserFactory.create(ParserType.GOOGLE);

		/**
		 * TASK-2: Extract main result links from the page
		 */
		List<String> googleResultpages = googleParser.parse(googlePageSourceCode, Constants.PATTERN_GOOGLE_PAGE_RESULT_URL_START.getValue(), Constants.PATTERN_GOOGLE_PAGE_RESULT_URL_END.getValue());

		/**
		 * Extracted Pages are here!
		 */
		// System.out.println("here are web pages");
		// googleResultpages.forEach(System.out::println);
		// System.out.println("web pages finished");

		/**
		 * For each page create a new LibraryCrawler which is Callable
		 */
		List<LibraryCrawler> scriptCrawlers = new ArrayList<>();
		googleResultpages.forEach(pageUrl -> scriptCrawlers.add(new LibraryCrawler(pageUrl)));

		// No need to define number of threads thanks to newWorkStealingPool
		// newWorkStealingPool already creates a work-stealing thread pool using all
		// available processors as its target parallelism level.
		ExecutorService executor = Executors.newWorkStealingPool();
		System.out.println("Preparing popular JSLibraries:");
		/**
		 * TASK-3: Download the respective pages and extract the names of used JAVASCRIPT libraries
		 */
		// I intentionally used invokeAll instead of CompletionService
		// because I want to return a list of Futures results when all complete.
		Map<String, Long> crawlingResults = executor.invokeAll(scriptCrawlers).parallelStream().map(future -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {

				/**
				 * A better logger config should be done..
				 */
				logger.error(e.getMessage());


				// The throwing of the InterruptedException CLEARS the interrupted state of the Thread
				// Restore interrupted state for higher level classes & handlers..
				Thread.currentThread().interrupt();


				// TODO Auto-generated catch block
			}
			return new ArrayList<String>();
		}).flatMap(List::stream).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		// shut down the executor 
		executor.shutdown();

		// Actually I can sort the result while collecting the crawlingResults in two lines above
		// but I just want it to be more readable for this assessment
		Map<String, Long> sortedCrawlingResults = sortPageResultOutput(crawlingResults);

		/**
		 * TASK-4: Print top 5 most used libraries to standard output
		 */
		System.out.println("Here is top 5 most used libraries:");
		// Actually I can Limit the result while sorting in two lines above
		// but I just want it to be more readable..
		sortedCrawlingResults.entrySet().stream()
				.forEach(m -> System.out.println(m.getKey() + "->" + m.getValue()+" times used."));
	}

	private static Map<String, Long> sortPageResultOutput(Map<String, Long> crawlingResults) {
		return crawlingResults.entrySet().parallelStream()
				.sorted((p1, p2) -> p1.getValue().compareTo(p2.getValue()) * -1).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}

	static String getGooglePageSourceCode(final String searchUrl) throws InterruptedException, ExecutionException {
		// The Purpose of this CALLABLE is:
		// Printing a loader effect with dots(...) to the screen
		// until one single HTTP Get request finalized.
		final Callable<String> googlePageSourceGetterTask = () -> {
			// Get page HTML as String
			final PageSourceProvider googlePageSourceProvider = PageSourceProvider.populate();
			return googlePageSourceProvider.getPageSource(searchUrl);
		};

		// Single Thread Executor for single HTTP call
		ExecutorService googleSearchExec = Executors.newSingleThreadExecutor();
		Future<String> future4GoogleSearch = googleSearchExec.submit(googlePageSourceGetterTask);
		// Never forget to shut down the executer
		googleSearchExec.shutdown();

		// Loading Simulation
		System.out.print("Searching..");
		while (!future4GoogleSearch.isDone()) {
			System.out.print(".");
			Thread.sleep(150);
		}
		System.out.println(".");
		System.out.println("Google Search Succeeded!");
		 // Get a Google result page for the search term
		return future4GoogleSearch.get();
	}

	// prepare search URL for google
	// I created a combination of Enum constants and Config.prop file together for this assessment 
	// which is not best practice but handful for this assessment
	static String getGoogleSearchURL(final String searchWord) {
		return Constants.GOOGLE_PAGE_URL.getValue() + searchWord + "&num="
				+ Constants.GOOGLE_SEARCH_RESULT_NUMBER.getValue();
	}

	private static String readFromConsole() {
		System.out.print("Please enter a search word: ");
		Scanner scanner = new Scanner(System.in);
		String tmpSearchWord = scanner.next();
		/**
		 * TODO: INPUT SHOULD BE VALIDATED HERE!!
		 */
		scanner.close();
		return tmpSearchWord;
	}
}

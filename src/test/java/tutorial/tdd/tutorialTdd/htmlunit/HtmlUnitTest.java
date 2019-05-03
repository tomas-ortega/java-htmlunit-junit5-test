package tutorial.tdd.tutorialTdd.htmlunit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.logging.Level;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HtmlUnitTest {
	
	@BeforeAll
	public static void deactivateLog() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
	}
	
	@Test
	@DisplayName("Search amazon books, select a single book and check pricing")
	public void submitForm() throws Exception {
		try {
			final WebClient webClient = new WebClient();
			String bookPrice = null;
			
			final HtmlPage page1 = webClient.getPage("http://www.amazon.es");
			
	        final HtmlForm form = page1.getFormByName("site-search");

	        final HtmlSubmitInput button = form.getInputByValue("Ir");
	        
	        final HtmlTextInput textField = form.getInputByName("field-keywords");

	        textField.type("albert camus el extranjero");

	        final HtmlPage booksResultPage = button.click();
	        
	        List<HtmlAnchor> nodeList = booksResultPage.getByXPath("//a[contains(@class, 'a-link-normal a-text-normal')]");
	        
	        	HtmlPage singleBookPage = nodeList.get(0).click();

				DomElement priceElement = singleBookPage.getFirstByXPath("//span[contains(@class, 'a-size-medium a-color-price offer-price a-text-normal')]");
				
				bookPrice = priceElement.getChildNodes().get(0).getNodeValue();
				
				assertTrue(bookPrice.equals("EUR 9,02"));
			
		} catch (Exception error) {
			throw error;
		}
	}

	@Test
	@DisplayName("Test webpage content some text in title page & search html element")
	public void homePage() throws Exception {
	    try {
	    	
	    	final WebClient webClient = new WebClient();
	    	
	        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
	        
	        assertTrue(page.getTitleText().contains("Welcome"));

	        final String pageAsXml = page.asXml();
	        
	        assertTrue(pageAsXml.contains("<div class=\"container-fluid\">"));

	        final String pageAsText = page.asText();
	        assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
	    } catch (Exception error) {
	    	throw error;
	    }
	}
}

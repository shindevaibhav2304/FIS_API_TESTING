import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoinDeskAPITest {

    private static final String API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    private String getAPIResponse() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private boolean containsKey(String response, String key) {
        return response.contains("\"" + key + "\"");
    }

    private String extractDescription(String response, String currency) {
        String searchKey = "\"" + currency + "\"";
        int currencyIndex = response.indexOf(searchKey);
        if (currencyIndex == -1) return "";

        int descriptionIndex = response.indexOf("\"description\"", currencyIndex);
        if (descriptionIndex == -1) return "";

        int colonIndex = response.indexOf(":", descriptionIndex);
        int quoteStart = response.indexOf('"', colonIndex + 1);
        int quoteEnd = response.indexOf('"', quoteStart + 1);

        if (quoteStart == -1 || quoteEnd == -1) return "";

        return response.substring(quoteStart + 1, quoteEnd);
    }

    public void testBPIPresence() throws Exception {
        String response = getAPIResponse();

        if (!containsKey(response, "USD")) {
            throw new AssertionError("USD not found in response");
        }
        if (!containsKey(response, "GBP")) {
            throw new AssertionError("GBP not found in response");
        }
        if (!containsKey(response, "EUR")) {
            throw new AssertionError("EUR not found in response");
        }
    }

    public void testGBPDescription() throws Exception {
        String response = getAPIResponse();
        String description = extractDescription(response, "GBP");

        if (!"British Pound Sterling".equals(description)) {
            throw new AssertionError("GBP description does not match: " + description);
        }

    }

    public static void main(String[] args) throws Exception {
        CoinDeskAPITest tester = new CoinDeskAPITest();
        try {
            tester.testBPIPresence();
            tester.testGBPDescription();
            System.out.println("All tests passed.");
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage()); 
            e.printStackTrace();
        }

    }
}

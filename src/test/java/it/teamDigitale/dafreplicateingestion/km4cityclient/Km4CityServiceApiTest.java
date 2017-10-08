/**
 * 
 */
package it.teamDigitale.dafreplicateingestion.km4cityclient;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

/**
 * @author alessandro
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.yml")
@RestClientTest(Km4CityServiceApi.class)
public class Km4CityServiceApiTest {
	
	@Autowired
	private Km4CityServiceApi serviceApi;
	
	@Autowired
	private Config config;
	
	@Autowired
    private MockRestServiceServer server;
	
//    @TestConfiguration
//    static class Km4CityServiceApiTestContextConfiguration {
//  
//        @Bean
//        public Km4CityServiceApi serviceApi() {
//            return new Km4CityServiceApi();
//        }
//    }
//    
    @Before
    public void setup() throws Exception {
        String responseString = "{ \\\"Service\\\":\\r\\n{\\\"type\\\": \\\"FeatureCollection\\\",\\r\\n\\\"features\\\": [\\r\\n\\r\\n{  \\\"geometry\\\": {\\r\\n    \\\"type\\\": \\\"Point\\\",\\r\\n   \\\"coordinates\\\": [ 11.2495, 43.7759 ]\\r\\n},\\r\\n\\\"type\\\": \\\"Feature\\\",\\r\\n\\\"properties\\\": {\\r\\n   \\\"name\\\": \\\"Garage La Stazione Spa\\\",\\r\\n   \\\"typeLabel\\\": \\\"Car park\\\",\\r\\n   \\\"serviceType\\\": \\\"TransferServiceAndRenting_Car_park\\\",\\r\\n   \\\"phone\\\": \\\"055284784\\\",\\r\\n   \\\"fax\\\": \\\"\\\",\\r\\n   \\\"website\\\": \\\"\\\",\\r\\n   \\\"province\\\": \\\"FI\\\",\\r\\n   \\\"city\\\": \\\"FIRENZE\\\",\\r\\n   \\\"cap\\\": \\\"50123\\\",\\r\\n   \\\"email\\\": \\\"\\\",\\r\\n   \\\"linkDBpedia\\\": [],\\r\\n   \\\"note\\\": \\\"\\\",\\r\\n   \\\"description\\\": \\\"\\\",\\r\\n   \\\"description2\\\": \\\"\\\",\\r\\n   \\\"multimedia\\\": \\\"\\\",\\r\\n   \\\"serviceUri\\\": \\\"http:\\/\\/www.disit.org\\/km4city\\/resource\\/RT04801702315PO\\\",\\r\\n   \\\"address\\\": \\\"PIAZZA DELLA STAZIONE\\\", \\\"civic\\\": \\\"3A\\\",\\r\\n   \\\"wktGeometry\\\": \\\"\\\",\\r\\n   \\\"photos\\\": [],\\r\\n   \\\"photoThumbs\\\": [],\\r\\n   \\\"photoOrigs\\\": [],\\r\\n   \\\"avgStars\\\": 0.0,\\r\\n   \\\"starsCount\\\": 0,\\r\\n   \\\"comments\\\": []},\\r\\n\\\"id\\\": 1\\r\\n}\\r\\n] }\\r\\n,\\\"realtime\\\": \\r\\n{ \\\"head\\\": {\\\"parkingArea\\\":[ \\\"Garage La Stazione Spa\\\"],\\\"vars\\\":[ \\\"capacity\\\", \\\"freeParkingLots\\\",\\\"occupiedParkingLots\\\",\\\"occupancy\\\",\\\"updating\\\"]},\\r\\n\\\"results\\\": {\\r\\n\\\"bindings\\\": [\\r\\n{\\\"capacity\\\": {\\\"value\\\": \\\"617\\\"  },\\\"freeParkingLots\\\": { \\\"value\\\": \\\"0\\\"  },\\\"occupiedParkingLots\\\": {\\\"value\\\": \\\"0\\\"  },\\\"occupancy\\\": {\\\"value\\\": \\\"0.0\\\"  },\\\"status\\\": {\\\"value\\\": \\\"carParkClosed\\\"  },\\\"updating\\\": {\\\"value\\\": \\\"2016-05-27T12:57:00+02:00\\\"  }\\r\\n}\\r\\n]}}\\r\\n}";  
        this.server.expect(requestTo("http://servicemap.disit.org/WebAppGrafo/api/v1/WebAppGrafo/api/v1/?serviceUri=http://www.disit.org/km4city/resource/RT04801702315PO"))
          .andRespond(withSuccess(responseString, MediaType.APPLICATION_JSON));
    }
    
//	@Test
//	public void getServiceGivenServiceUri() {
//		//for(String service : config.getParkings()) {
//		JSONObject jsonResponse = serviceApi.consume(config.getParkings().get(0));
//		//}
//	}
	
    @Test
    public void whenCallingGetUserDetails_thenClientMakesCorrectCall() 
      throws Exception {
 
        JSONObject jsonResponse = this.serviceApi.consume(config.getParkings().get(0));
 
        assertEquals("Garage La Stazione Spa", jsonResponse
        		.getJSONObject("realtime")
        		.getJSONObject("head")
        		.getJSONArray("parkingArea").get(0));
    }
	
	@Configuration
	@EnableConfigurationProperties
	@ConfigurationProperties(prefix = "km4city.services")
	static class Config {
		private List<String> parkings;
		
		public List<String> getParkings() {
			return parkings;
		}

		public void setParkings(List<String> parkings) {
			this.parkings = parkings;
		}

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertyPlaceholder() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}
}

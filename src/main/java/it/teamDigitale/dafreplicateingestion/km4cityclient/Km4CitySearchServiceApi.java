/**
 * 
 */
package it.teamDigitale.dafreplicateingestion.km4cityclient;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author alessandro
 *
 */
public class Km4CitySearchServiceApi extends AbstractKm4CityApiRestClient {
	
	public JsonNode consume(Map<String, String> parameters) {
		try {
			
			ResponseEntity<String> response = super.restTemplate.getForEntity(
					baseUrl,
					String.class,
					parameters);
			JsonNode jsonBody = super.mapper.readTree(response.getBody());
			
			LOGGER.debug(response.getBody());	
			return jsonBody;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

package it.teamDigitale.dafreplicateingestion.km4cityclient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class Km4CityServiceApi extends AbstractKm4CityApiRestClient {

	public JsonNode consume(String serviceUri) {
		try {
			
			ResponseEntity<String> response = super.restTemplate.getForEntity(
					baseUrl + "?serviceUri=" + serviceUri,
					String.class);
			JsonNode jsonBody = super.mapper.readTree(response.getBody());
			
			LOGGER.debug(response.getBody());	
			return jsonBody;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

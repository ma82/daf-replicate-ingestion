package it.teamDigitale.dafreplicateingestion.km4cityclient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Km4CityServiceApi extends AbstractKm4CityApiRestClient {

	public ResponseEntity<String> consume(String serviceUri) {
		try {
			ResponseEntity<String> response = this.restTemplate.getForEntity(
					baseUrl + "?serviceUri=" + serviceUri,
					String.class);
			LOGGER.debug(response.getBody());
			return response;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

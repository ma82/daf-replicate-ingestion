package it.italia.developers.dafreplicateingestion.km4cityclient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Km4CityServiceSearchApi extends AbstractKm4CityApiRestClient {

	public ResponseEntity<String> consume() {
		try {
			ResponseEntity<String> response = this.restTemplate.getForEntity(
					baseUrl + "?selection=43.7756;11.2490&categories=Accommodation&maxDists=0.2&lang=it&format=json",
					String.class);
			LOGGER.debug(response.getBody());
			return response;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

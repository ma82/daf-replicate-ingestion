/**
 * 
 */
package it.italia.developers.dafreplicateingestion.ingestion;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author aletundo
 *
 */
@Service
public class ApiGeneric {
	
	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private RestTemplate restTemplate;
	@Value("${km4city.base_url}")
	private String baseUrl;
	
	public ApiGeneric() {
		this.restTemplate = new RestTemplate();
	}
	
	public ResponseEntity<String> consume() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl + "?selection=43.7756;11.2490&categories=Accommodation&maxDists=0.2&lang=it&format=json", String.class);
		LOG.info(response.getBody());
		return response;
	}
	
	  @PostConstruct
	  public void init(){
	     this.consume();
	  }
}

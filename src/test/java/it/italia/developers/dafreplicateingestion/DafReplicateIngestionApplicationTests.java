package it.italia.developers.dafreplicateingestion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import it.italia.developers.dafreplicateingestion.km4cityclient.Km4CityServiceSearchApi;
import it.italia.developers.dafreplicateingestion.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DafReplicateIngestionApplicationTests {

	@Autowired
	private Sender sender;
	
	@Autowired
	private Km4CityServiceSearchApi searchServiceApi;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "dummy.t");

	@Before
	public void setup() throws Exception {
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
		}
	}

	@Test
	public void testSender() throws Exception {
		ResponseEntity<String> response = searchServiceApi.consume();
		ListenableFuture<SendResult<String, String>> sendResult = sender.send(response.getBody());
		assertNotNull(sendResult.get().getProducerRecord());
		assertEquals("dummy.t", sendResult.get().getProducerRecord().topic());
		assertNotNull(sendResult.get().getProducerRecord().value());
	}
}

package it.teamDigitale.dafreplicateingestion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;

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

import it.teamDigitale.avro.Event;
import it.teamDigitale.dafreplicateingestion.km4cityclient.Km4CityServiceApi;
import it.teamDigitale.dafreplicateingestion.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DafReplicateIngestionApplicationTests {

	@Autowired
	private Sender sender;
	
//	@Autowired
//	private Km4CityServiceApi serviceApi;

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
//		String serviceUri = "http://fuffa";
//		ResponseEntity<String> response = serviceApi.consume(serviceUri);
		Event event = Event.newBuilder()
				.setEventTypeId(1)
				.setTs(new Date().getTime())
				.setLocation("Ciao")
				.setHost("http://localhost")
				.setService("http://localhost/service")
				.setAttributes(new HashMap<CharSequence, CharSequence>())
				.build();
		ListenableFuture<SendResult<String, Event>> sendResult = sender.send(event);
		assertNotNull(sendResult.get().getProducerRecord());
		assertEquals("dummy.t", sendResult.get().getProducerRecord().topic());
		assertNotNull(sendResult.get().getProducerRecord().value());
	}
}

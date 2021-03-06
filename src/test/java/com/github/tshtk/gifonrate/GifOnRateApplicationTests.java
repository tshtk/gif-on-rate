package com.github.tshtk.gifonrate;

import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GifOnRateApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void shouldReturnImage() {
		Tika tika = new Tika();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		String url = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/gifs/")
			.queryParam("currency", "RUB")
			.toUriString();

		ResponseEntity<byte[]> response = restTemplate
			.exchange(url, HttpMethod.GET, httpEntity, byte[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_GIF);
		assertThat(tika.detect(response.getBody())).isEqualTo(MediaType.IMAGE_GIF.toString());
	}
}

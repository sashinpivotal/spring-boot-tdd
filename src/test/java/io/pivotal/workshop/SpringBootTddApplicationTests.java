package io.pivotal.workshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootTddApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void getCar_should_return_201_and_location_field_when_car_is_posted() throws Exception {
		ResponseEntity<Car> carResponseEntity = testRestTemplate.postForEntity("/cars", new Car("prius", "hybrid"), Car.class);
		assertThat(carResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(carResponseEntity.getHeaders().getLocation()).hasPath("/cars/prius");
	}

	@Test
	public void getCar_should_return_http_200_given_valid_url() throws Exception {

		// Add a test car before testing
		testRestTemplate.postForEntity("/cars", new Car("prius", "hybrid"), Car.class);

		ResponseEntity<Car> carResponseEntity = testRestTemplate.getForEntity("/cars/prius", Car.class);
		assertThat(carResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(carResponseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(carResponseEntity.getBody().getName()).isEqualTo("prius1");
		assertThat(carResponseEntity.getBody().getType()).isEqualTo("hybrid1");
	}

	@Test
	public void getCar_should_return_http_404_given_invalid_url() throws Exception {

		ResponseEntity<Car> carResponseEntity = testRestTemplate.getForEntity("/cars/junk", Car.class);
		assertThat(carResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}

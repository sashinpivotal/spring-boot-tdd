package io.pivotal.workshop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTddApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void should_return_http_response_with_car_detail() throws Exception {

		ResponseEntity<Car> carResponseEntity = testRestTemplate.getForEntity("/cars/prius", Car.class);
		assertThat(carResponseEntity.getBody().getName()).isEqualTo("prius");
		assertThat(carResponseEntity.getBody().getType()).isEqualTo("hybrid");
		assertThat(carResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}

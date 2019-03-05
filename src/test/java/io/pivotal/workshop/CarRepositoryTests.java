package io.pivotal.workshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByName_should_return_car_given_valid_car() throws Exception {
        // arrange
        testEntityManager.persistAndFlush(new Car("camry", "regular"));
        testEntityManager.clear();

        // act and assert
        Car car = carRepository.findByName("camry");
        assertThat(car).isEqualTo(new Car("camry", "regular"));

        // verify
    }

    @Test
    public void findByName_should_return_null_given_invalid_car() throws Exception {
        Car car = carRepository.findByName("camry");
        assertThat(car).isEqualTo(null);
    }
}

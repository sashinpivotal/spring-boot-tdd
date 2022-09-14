package io.pivotal.workshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositorySliceTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByName_should_return_car_from_database() throws Exception {

        // arrange
        testEntityManager.persistAndFlush(new Car("camry", "regular"));
        testEntityManager.clear();

        // act and assert
        Car car = carRepository.findByName("camry");
        assertThat(car).isEqualTo(new Car(1L,"camry", "regular"));

        // verify
    }

    @Test
    public void findByName_should_return_null_when_database_does_not_have_it() throws Exception {
        Car car = carRepository.findByName("camry");
        assertThat(car).isEqualTo(null);
    }
}

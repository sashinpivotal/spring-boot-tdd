package io.pivotal.workshop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByName_should_return_car() throws Exception {
        testEntityManager.persistAndFlush(new Car("camry", "regular"));

        Car car = carRepository.findByName("camry");
        Assertions.assertThat(car.getName()).isEqualTo("camry");
    }
}

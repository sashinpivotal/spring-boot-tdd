package io.pivotal.workshop;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringBootTddApplication.class})
public class CarServiceUsingSpringContextTests {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void getCarDetails_should_return_car_details_given_correct_car_name() throws Exception {

        // arrange
        given(carRepository.findByName(anyString())).willReturn(new Car("prius", "hybrid"));

        // act and assert
        Car prius = carService.getCarDetails("prius");
        assertThat(prius.getName()).isEqualTo("prius");
        assertThat(prius.getType()).isEqualTo("hybrid1");

        // verify
        verify(carRepository).findByName(anyString());
    }

    @Test
    public void getCarDetails_should_return_CarNotFoundException_given_wrong_car_name_junit5() throws Exception {

        // arrange
        given(carRepository.findByName(anyString())).willReturn(null);

        // act and assert
        assertThrows(CarNotFoundException.class, () -> {
            carService.getCarDetails("invalid");
        });

        // verify
        verify(carRepository).findByName(anyString());
    }

}
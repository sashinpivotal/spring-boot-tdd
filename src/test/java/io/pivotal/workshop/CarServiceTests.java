package io.pivotal.workshop;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() throws Exception {
        carService = new CarService(carRepository);
    }

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

    @Test
    void addCar_should_return_the_same_Car_given_valid_Car_to_add() {

        // arrange
        given(carRepository.save(new Car("prius", "hybrid"))).willReturn(new Car("prius", "hybrid"));

        // act and assert
        Car car = carService.addCar(new Car("prius", "hybrid"));
        assertThat(car).isEqualTo(car);

        // verify
        verify(carRepository).save(new Car("prius", "hybrid"));
    }
}
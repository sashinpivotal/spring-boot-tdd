package io.pivotal.workshop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes={SpringBootTddApplication.class})
@RunWith(MockitoJUnitRunner.class)
public class CarServiceTests {

//    @Autowired
    private CarService carService;

//    @MockBean
    @Mock
    private CarRepository carRepository;

    @Before
    public void setUp() throws Exception {
        carService = new CarService(carRepository);
    }

    @Test
    public void getCarDetails_should_return_car_details_given_correct_car_name() throws Exception {

        given(carRepository.findByName(anyString())).willReturn(new Car("prius", "hybrid"));

        Car prius = carService.getCarDetails("prius");
        assertThat(prius.getName()).isEqualTo("prius");
        assertThat(prius.getType()).isEqualTo("hybrid");

        verify(carRepository).findByName(anyString());
    }

    @Test(expected = CarNotFoundException.class)
    public void getCarDetails_should_return_CarNotFoundException_given_worng_car_name() throws Exception {

        given(carRepository.findByName(anyString())).willReturn(null);

        carService.getCarDetails("prius");

        verify(carRepository).findByName(anyString());
    }

}
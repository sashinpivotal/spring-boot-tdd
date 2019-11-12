package io.pivotal.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// In order to use Spring caching, we need Spring context
@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes={SpringBootTddApplication.class})
public class CarServiceCachingTests {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void getCarDetails_caching_should_return_cached_item() throws Exception {

        given(carRepository.findByName("prius")).willReturn(new Car("prius", "hybrid"));

        carService.getCarDetails("prius");
        carService.getCarDetails("prius");

        verify(carRepository, times(1)).findByName("prius");
    }
}

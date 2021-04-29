package io.pivotal.workshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// In order to use Spring caching, we need Spring context
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={SpringBootTddApplication.class, CarServiceCache.class})
public class CarServiceCachingSliceTests {

    @Autowired
    private CarServiceCache carService;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("car-cache").clear();
    }

    @Test
    public void getCarDetails_should_use_caching_when_called_twice() throws Exception {

        // arrange
        given(carRepository.findByName(anyString())).willReturn(new Car("prius", "hybrid"));

        // act and assert
        carService.getCarDetails("prius");
        carService.getCarDetails("prius");

        // verify
        verify(carRepository, times(1)).findByName(anyString());
    }
}

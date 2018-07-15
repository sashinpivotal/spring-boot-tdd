package io.pivotal.workshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void getCar_should_return_car_defail_given_car_name() throws Exception {

        given(carService.getCarDetails(anyString())).willReturn(new Car("hyundai", "hybrid"));

        mockMvc.perform(get("/cars/hyundai"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("name").value("hyundai"))
               .andExpect(jsonPath("type").value("hybrid"));

        verify(carService, times(1)).getCarDetails("hyundai");
    }

    @Test
    public void getCar_should_return_http_404_given_wrong_car_name() throws Exception {
        given(carService.getCarDetails(anyString())).willThrow(new CarNotFoundException());

        mockMvc.perform(get("/cars/bogus"))
               .andExpect(status().isNotFound());

        verify(carService).getCarDetails("bogus");
    }

}

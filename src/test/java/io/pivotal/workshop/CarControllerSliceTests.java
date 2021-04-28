package io.pivotal.workshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerSliceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void getCar_should_return_http_200_given_valid_car_name() throws Exception {

        // arrange - train your mock
        given(carService.getCarDetails(anyString())).willReturn(new Car("hyundai", "hybrid"));

        // act & assert
        mockMvc.perform(get("/cars/hyundai"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("name").value("hyundai1"))
               .andExpect(jsonPath("type").value("hybrid"));

        // verify that dependency is invoked
        verify(carService, times(1)).getCarDetails(anyString());
    }

    @Test
    public void getCar_should_return_http_404_given_invalid_car_name() throws Exception {

        // arrange
        given(carService.getCarDetails(anyString())).willThrow(new CarNotFoundException());

        // act & assert
        mockMvc.perform(get("/cars/bogus"))
               .andExpect(status().isNotFound());

        // verify
        verify(carService).getCarDetails(anyString());
    }

    @Test
    public void addCar_should_return_http_201_given_a_car() throws Exception {

        // arrange
        given(carService.addCarDetails(new Car("test", "test"))).willReturn(new Car("test", "test"));

        // act and assert
        Car car = new Car("test", "test");

        mockMvc.perform(post("/cars")
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(car))
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());

        // verify
        verify(carService).addCarDetails(new Car("test", "test"));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

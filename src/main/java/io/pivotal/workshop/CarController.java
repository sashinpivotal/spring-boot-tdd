package io.pivotal.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {

        this.carService = carService;
    }

    @GetMapping("/{name}")
    public Car getCar(@PathVariable String name){

        Car car = carService.getCarDetails(name);

        // Dummy controller logic we want to unit-test
        car.setName(car.getName() +"1");

        return car;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addCar(@RequestBody Car car) {

        carService.addCarDetails(car);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                             .path("/{id}")
                                             .buildAndExpand(car.getName())
                                             .toUri();

        return ResponseEntity.created(uri).build();
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handleException(CarNotFoundException e) {
//
//    }

}

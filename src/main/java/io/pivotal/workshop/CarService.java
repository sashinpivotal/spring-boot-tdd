package io.pivotal.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Cacheable("car-cache")
    public Car getCarDetails(String name) {
         Car car = carRepository.findByName(name);
         if (car == null){
             throw new CarNotFoundException();
         }
         return car;
    }
}

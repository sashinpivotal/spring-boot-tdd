package io.pivotal.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@EnableCaching
@Service
public class CarServiceCache {

    private CarRepository carRepository;

    @Autowired
    public CarServiceCache(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Cacheable("car-cache")
    public Car getCarDetails(String name) {
         Car car = carRepository.findByName(name);
         if (car == null){
             throw new CarNotFoundException();
         }

         // Dummy business logic we want to unit-test
         car.setType(car.getType() + "1");

         return car;
    }

    public Car addCarDetails(Car car) {
        return carRepository.save(car);
    }
}

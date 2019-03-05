# spring-boot-tdd

This is based on the [Test-Driven Development with Spring Boot talk given in SpringOne 2017](https://www.youtube.com/watch?v=s9vt6UJiHg4)

-   Test-driven development (TDD) is a software development process 
    that relies on the repetition of a very short development cycle 
    of Red, Green, Refactor

-   Start a new "Web" Spring Boot project from [Spring Boot Initialzr](http://start.spring.io/)
    
## Write MVC slice unit testing

### Functionality to be tested

-   The `CarController`, given a correct car name, i.e. "/cars/prius", 
    should return success (200) and Car data in JSON format
    in the response body
    
-   The `CarController`, given a non-existent car name, i.e. "/cars/nocar",
    should return failure (404)
    
-   (Optional) The `CarController`, given a new car, should add it
    to the system and return success (201) with response body containing
    newly added car
    
### Tips

-   Use `@WebMvcTest`, which automatically configures `MockMvc` object
   
-   Given that it is a unit testing, you should mock `CarService`
    object - think about which one to use, `@Mock` or `@MockBean`
   
-   In Spring, all exceptions should be `RunTimeException`
    
-   Observe how much time this test takes and see if Tomcat gets started or not

## Write Service slice unit testing

### Functionality to be tested

-   The `CarService's` `getCarDetails` method, given a correct car
    name, "prius", should return Car object
    
-   The method, given a non-existent car name, "nocar", should 
    throw `CarNotFoundException`
   
-   (Optional) The `CarService's` `addCar(Car car)` method should
    add it to the system and return added Car
    
### Tips

-   Since this is a unit testing, you should mock `CarRepository`
    object
    
-   Start with testing with Spring using `@RunWith(SpringRunner.class)`
    (using Spring testing framework) and then replace it with 
    `@RunWith(MockitoJUnitRunner.class)` and think about which one is 
    a better practice
    
### Trouble-shooting

-   If you experience the following error, it is because you have not
    handle the case where CarRepository's findByCar(..) method
    returns null.
    

    ```
    java.lang.AssertionError: Expected exception: com.example.demotdd5.CarNotFoundException

	...
    org.h2.jdbc.JdbcSQLException: Database is already closed (to disable automatic closing at VM shutdown, add ";DB_CLOSE_ON_EXIT=FALSE" to the db URL) [90121-197]
	at org.h2.message.DbException.getJdbcSQLException(DbException.java:357)
	at org.h2.message.DbException.get(DbException.java:179)
	at org.h2.message.DbException.get(DbException.java:155)
    ```

## Write JPA Repository slice unit testing

### Functionality to be tested

-   The `CarRepository's` `findByName(..)` method should return 
    a Car given a valid car name
    
-   (Optional) The `CarRepository's` `save(..)` method should return
    a Car give a Car object
    
### Tips
    
-   Use `@DataJpaTest` annotation, which provides `TestEntityManager`

## Write Service slice caching testing

### Functionality to be tested

-   The `CarService's` `getCarDetails(..)` method should return an item
    from a cache

### Tips

-   The way you can test the caching behavior is to call `CarService's` `getCarDetails(..)` 
    method twice and then see if it called `CarRepository's` `findByName(..)`
    method once

## Write integration testing

### Functionality to be tested

-   The application, when accessed with "/cars/prius", 
    should return success (200) 
    
    -  Assert that return status is 200
    -  Assert that content type returned MediaType.APPLICATION_JSON_UTF8
    -  Assert that the body returns Car, name and type of which
       are "prius" and "hybrid" respectively
 
### Tips      
-   Use `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`
-   Observe how much this test takes, also observe that this test starts Tomcat
    
## Use Junit 5

### Functionality to be done

-   If you write the test code in JUnit 4, replace some of those
    using JUnit 5
    
### Tips

-   Spring Boot test starter already supports JUnit 5

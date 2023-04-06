# TransPerfect Coding Challenge

## Exercise statement

You are given a RESTful endpoint `/movies` that returns a list of movies in JSON format.
Your task is to create a Spring Boot application that consumes this endpoint and exposes a new endpoint `/recommendations` that returns a list of recommended movies based on the user's preferences.

The `/recommendations` endpoint should accept a query parameter genre that contains the user's preferred movie genre.
The endpoint should return a list of movies that belong to the specified genre, sorted by release year in descending order.

The movie model is defined as follows:

```java
public class Movie {
    private Long id;
    private String title;
    private String genre;
    private int releaseYear;
    private String director;

    // getters and setters
}
```

## Running the project

**The project requires Java 17 or greater to be run.**

The `MovieControllerTest` class contains test cases that assert the project fulfills the requirements.

To run the tests:

On Unix-based systems
```shell
./gradlew test
```

On Windows
```shell
.\gradlew test
```

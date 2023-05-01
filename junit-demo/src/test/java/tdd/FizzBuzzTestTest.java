package tdd;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTestTest {

    FizzBuzz fizzBuzz;

     /*
     number is divisible by 3, print Fizz
     number is divisible by 5, print Buzz
     number is divisible by 3, 6, print FizzBuzz
     */

    @Test
    @DisplayName("number is divisible by 3")
    @Order(1)
    void divisibleByTree() {
        String expected = "Fizz";
        int num = 3;

        assertEquals(expected, fizzBuzz.compute(num), "Should be return Fizz");
    }

    @Test
    @DisplayName("number is divisible by 5")
    @Order(2)
    void divisibleByFive() {
        String expected = "Buzz";
        int num = 5;

        assertEquals(expected, fizzBuzz.compute(num), "Should be return Buzz");
    }

    @Test
    @DisplayName("number is divisible by 3 and 5")
    @Order(3)
    void divisibleByTreeAndFive() {
        String expected = "FizzBuzz";
        int num = 15;

        assertEquals(expected, fizzBuzz.compute(num), "Should be return FizzBuzz");
    }

    @Test
    @DisplayName("number is not divisible by 3 or 5")
    @Order(4)
    void notDivisibleByTreeOrFive() {
        int num = 1;
        String expected = String.valueOf(num);

        assertEquals(expected, fizzBuzz.compute(num), "Should be return " + num);
    }

}
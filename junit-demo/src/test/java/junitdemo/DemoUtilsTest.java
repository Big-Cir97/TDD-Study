package junitdemo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.security.spec.RSAOtherPrimeInfo;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {

    DemoUtils demo;

    @BeforeAll
    static void setUpBeforeAll() {
        System.out.println("execute @BeforeAll");
    }

    @BeforeEach
    void setUpTest() {
        demo = new DemoUtils();
        System.out.println("Running @BeforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Running @AfterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("execute @AfterAll");
    }

    @Test
    @DisplayName("EqualsAndNotEquals Test")
    @Order(1)
    @EnabledOnOs(OS.WINDOWS)
    void equalsAndNotEquals() {
        // given
        // DemoUtils demo = new DemoUtils();

        // when
        int a = 2;
        int b = 4;

        // then
        assertEquals(6, demo.add(a, b), "a + b = 6");
        assertNotEquals(7, demo.add(a, b), "a + b != 6");
    }

    @Test
    @DisplayName("NullAndNotNull Test")
    void nullAndNotNull() {
        // given
        // DemoUtils demo = new DemoUtils();

        // when
        String str1 = null;
        String str2 = "not Null";

        // then
        assertNull(demo.checkNull(str1), "should be null");
        assertNotNull(demo.checkNull(str2), "should be not null");
    }

    @Test
    @DisplayName("Check Same or Not Same")
    void sameAndNotSame() {
        // given
        // when
        String str1 = "test1";
        String str2 = "test2";

        // then
        assertSame(str1, getObj(str1), "same obj");
        assertNotSame(str1, getObj(str2), "Not same obj");
    }

    @Test
    @DisplayName("Check True or False")
    void trueAndFalse() {
        // given
        // when
        int case1 = 20;
        int case2 = 10;

        // then
        assertTrue(condition(case1, case2), "should return true");
        assertFalse(condition(case2, case1), "should return false");
    }

    @Test
    @DisplayName("Array Equals")
    void arrayEquals() {
        // given
        // demo

        // when
        String[] alpaArray = {"A", "B", "C"};
        String[] alpaArray2 = {"a", "b", "c"};

        // then
        assertArrayEquals(alpaArray, demo.getAlpabet(), "should be same");
        assertArrayEquals(alpaArray2, demo.getAlpabet(), "should be same");     // failed
    }

    @Test
    @DisplayName("Iterable Equals")
    void iterableEquals() {
        // given

        // when
        List<String> testIter = List.of("framework", "junit", "test");
        List<String> testIter2 = List.of("framework", "mock", "test");

        // then
        assertIterableEquals(testIter, demo.getStrList(), "should be same actual list");
        assertIterableEquals(testIter2, demo.getStrList(), "should be same actual list");       // failed
    }

    @Test
    @DisplayName("linesMatch")
    void linesMatch() {
        // given

        // when
        List<String> testLine = List.of("framework", "junit", "test");
        List<String> testLine2 = List.of("framework", "mock", "test");

        // then
        assertLinesMatch(testLine, demo.getStrList(), "Lines should match");
        assertLinesMatch(testLine2, demo.getStrList(), "Lines should match");       // failed
    }

    @Test
    @DisplayName("Check Throws")
    void testThrowsAndNotThrow() {
        // given

        // when
        String test = "type match";
        Integer test2 = 5;

        // then
        assertThrows(Exception.class, () -> { throwsException(test); }, "should be Exception");
        assertThrows(Exception.class, () -> { throwsException(test2); }, "should be Exception");    // failed
        assertDoesNotThrow(() -> { throwsException(test); }, "should not throw exception");
        assertDoesNotThrow(() -> { throwsException(test2); }, "should not throw exception");        // failed
    }

    @Test
    @DisplayName("Timeout")
    void testTimeout() {
        // given

        // when
        Duration time = Duration.ofSeconds(3);

        // then
        assertTimeoutPreemptively(time, () -> demo.checkTimeout(2), "checkTimeout Method should be execute in 3 seconds");
        assertTimeoutPreemptively(time, () -> demo.checkTimeout(4), "checkTimeout Method should be execute in 3 seconds");      // failed
    }

    private static Object getObj(Object obj) {
        if (obj instanceof String) {
            return obj;
        }
        return obj;
    }

    private static boolean condition(int n, int m) {
        if (n > m) return true;
        return false;
    }

    private static String throwsException(Object obj) throws Exception {
        if (obj instanceof String) {
            throw new Exception("Type Mismatch ");
        }

        return "equal type";
    }
}
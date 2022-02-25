package lab4.task1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MyIntTest {
    @Test
    void createNewMyInt() {
        ArrayList<String> expectedPayLoad = new ArrayList<>();
        expectedPayLoad.add("1");
        expectedPayLoad.add("5");
        expectedPayLoad.add("4");

        MyInt testValue;

        testValue = new MyInt(154);
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);

        testValue = new MyInt("154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt("0154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt("00000154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt("+154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt("+0154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt("+00000154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);

        testValue = new MyInt(-154);
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);

        testValue = new MyInt("-154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);


        testValue = new MyInt("-0154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);


        testValue = new MyInt("-00000154");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);


        expectedPayLoad = "21474836479999999999999"
                .chars()
                .mapToObj(x -> Character
                        .toString((char) x)
                )
                .collect(
                        Collectors.toCollection(ArrayList::new)
                );

        testValue = new MyInt("-21474836479999999999999");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);

        expectedPayLoad = new ArrayList<>();
        expectedPayLoad.add("0");
        testValue = new MyInt("0");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


    }

    @Test
    void createNewMyIntWithException() throws IllegalArgumentException {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt("-0000 0154"));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt("-"));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt("+"));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(""));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(new String[]{}));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(new String[]{"+"}));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(new String[]{"-"}));
        assertNotNull(thrown.getMessage());


        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(new String[]{"1", " ", "1"}));
        assertNotNull(thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(new String[]{"+", "+", "1"}));
        assertNotNull(thrown.getMessage());


        thrown = assertThrows(IllegalArgumentException.class, () -> new MyInt(new String[]{"+", "", "1"}));
        assertNotNull(thrown.getMessage());


    }

    @Test
    void createNewMyIntByteArray() {
        ArrayList<String> expectedPayLoad = new ArrayList<>();
        expectedPayLoad.add("1");
        expectedPayLoad.add("5");
        expectedPayLoad.add("4");


        MyInt testValue;

        testValue = new MyInt(new String[]{"+", "1", "5", "4"});
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt(new String[]{"+", "0", "0", "1", "5", "4"});
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);


        testValue = new MyInt(new String[]{"-", "1", "5", "4"});
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);

        testValue = new MyInt(new String[]{"-", "0", "0", "1", "5", "4"});
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);

        expectedPayLoad = new ArrayList<>();
        expectedPayLoad.add("0");
        testValue = new MyInt("0000");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.POSITIVE);

        testValue = new MyInt("-0000");
        assertEquals(testValue.getCopyPayload(), expectedPayLoad);
        assertEquals(testValue.getSign(), SignEnum.NEGATIVE);
    }


    @Test
    void normalize() {
        ArrayList<String> expectedPayLoad = new ArrayList<>(Arrays.asList(
                "0",
                "0",
                "1",
                "5",
                "4"
        ));


        MyInt testValue;

        testValue = new MyInt("154");
        MyInt normalize = testValue.normalize(expectedPayLoad.size());
        assertEquals(normalize.getCopyPayload(), expectedPayLoad);


    }


    @Test
    void add() {
        MyInt first = new MyInt(-99);
        MyInt second = new MyInt(-20);

        MyInt result = first.add(second);
        ArrayList<String> payLoad = result.getCopyPayload();
        ArrayList<String> expectedPayLoad = new ArrayList<>(Arrays.asList("1", "1", "9"));
        assertEquals(payLoad, expectedPayLoad);
        assertEquals(result.getSign(), SignEnum.NEGATIVE);


        first = new MyInt(-99);
        second = new MyInt(20);

        result = first.add(second);
        payLoad = result.getCopyPayload();
        expectedPayLoad = new ArrayList<>(Arrays.asList("7", "9"));
        assertEquals(payLoad, expectedPayLoad);
        assertEquals(result.getSign(), SignEnum.NEGATIVE);


        first = new MyInt(99);
        second = new MyInt(-20);

        result = first.add(second);
        payLoad = result.getCopyPayload();
        expectedPayLoad = new ArrayList<>(Arrays.asList("7", "9"));
        assertEquals(payLoad, expectedPayLoad);
        assertEquals(result.getSign(), SignEnum.POSITIVE);


        first = new MyInt(99);
        second = new MyInt(20);

        result = first.add(second);
        payLoad = result.getCopyPayload();
        expectedPayLoad = new ArrayList<>(Arrays.asList("1", "1", "9"));
        assertEquals(payLoad, expectedPayLoad);
        assertEquals(result.getSign(), SignEnum.POSITIVE);


        first = new MyInt(0);
        second = new MyInt(0);

        result = first.add(second);
        payLoad = result.getCopyPayload();
        expectedPayLoad = new ArrayList<>(Collections.singletonList("0"));
        assertEquals(payLoad, expectedPayLoad);
        assertEquals(result.getSign(), SignEnum.POSITIVE);

    }

    @Test
    void subtract() {
        MyInt first = new MyInt(11001);
        MyInt second = new MyInt(193);
        MyInt result = first.subtract(second);

        assertEquals(result.toString(), "MyInt{+10808}");


        first = new MyInt(-9);
        second = new MyInt(6);
        result = first.subtract(second);

        assertEquals(result.toString(), "MyInt{-15}");


        first = new MyInt(9);
        second = new MyInt(-6);
        result = first.subtract(second);

        assertEquals(result.toString(), "MyInt{-15}");


        first = new MyInt(-9);
        second = new MyInt(-6);
        result = first.subtract(second);

        assertEquals(result.toString(), "MyInt{-3}");


        first = new MyInt(0);
        second = new MyInt(0);
        result = first.subtract(second);

        assertEquals(result.toString(), "MyInt{+0}");


    }


    @Test
    void max() {
        MyInt first = new MyInt(-99);
        MyInt second = new MyInt(-20);
        MyInt result = MyInt.max(first, second);

        assertEquals(result.toString(), "MyInt{-20}");


        first = new MyInt(-99);
        second = new MyInt(20);
        result = MyInt.max(first, second);

        assertEquals(result.toString(), "MyInt{+20}");


        first = new MyInt(99);
        second = new MyInt(20);
        result = MyInt.max(first, second);

        assertEquals(result.toString(), "MyInt{+99}");

        first = new MyInt(0);
        second = new MyInt(0);
        result = MyInt.max(first, second);

        assertEquals(result, new MyInt(0));
    }

    @Test
    void abs() {
        MyInt first = new MyInt(-99);
        MyInt result = MyInt.abs(first);

        assertEquals(result, new MyInt(99));


        first = new MyInt(99);
        result = MyInt.abs(first);

        assertEquals(result, new MyInt(99));


        first = new MyInt(0);
        result = MyInt.abs(first);

        assertEquals(result, new MyInt(0));


    }

    @Test
    void compareTo() {
        MyInt first = new MyInt(-99);
        MyInt second = new MyInt(-20);
        int result = first.compareTo(second);

        assertEquals(result, -1);


        first = new MyInt(-99);
        second = new MyInt(20);
        result = first.compareTo(second);

        assertEquals(result, -1);


        first = new MyInt(99);
        second = new MyInt(20);
        result = first.compareTo(second);

        assertEquals(result, 1);


        first = new MyInt(99);
        second = new MyInt(99);
        result = first.compareTo(second);

        assertEquals(result, 0);


        first = new MyInt(12);
        second = new MyInt(4);
        result = first.compareTo(second);

        assertEquals(result, 1);

    }

    @Test
    void min() {
        MyInt first = new MyInt(-99);
        MyInt second = new MyInt(-20);
        MyInt result = MyInt.min(first, second);

        assertEquals(result.toString(), "MyInt{-99}");


        first = new MyInt(-99);
        second = new MyInt(20);
        result = MyInt.min(first, second);

        assertEquals(result.toString(), "MyInt{-99}");


        first = new MyInt(99);
        second = new MyInt(20);
        result = MyInt.min(first, second);

        assertEquals(result.toString(), "MyInt{+20}");


        first = new MyInt(-99);
        second = new MyInt(0);
        result = MyInt.min(first, second);

        assertEquals(result.toString(), "MyInt{-99}");


        first = new MyInt(99);
        second = new MyInt(0);
        result = MyInt.min(first, second);

        assertEquals(result, new MyInt(0));


        first = new MyInt(0);
        second = new MyInt(0);
        result = MyInt.min(first, second);

        assertEquals(result, new MyInt(0));


        first = new MyInt(12);
        second = new MyInt(4);
        result = MyInt.min(first, second);

        assertEquals(result, new MyInt(4));
    }

    @Test
    void getLongValue() {
        MyInt first = new MyInt(-99);
        long result = first.getLongValue();

        assertEquals(result, (long) -99);


        first = new MyInt(99);
        result = first.getLongValue();

        assertEquals(result, (long) 99);


        first = new MyInt("+" + "99999999999" + Long.MAX_VALUE);
        result = first.getLongValue();

        assertEquals(result, Long.MAX_VALUE);


        first = new MyInt("-" + "99999999999" + String.valueOf(Long.MIN_VALUE).substring(1));
        result = first.getLongValue();

        assertEquals(result, Long.MIN_VALUE);
    }

    @Test
    void testEquals() {
        MyInt first = new MyInt(99);
        MyInt second = new MyInt(99);
        assertEquals(first, second);

        first = new MyInt(-99);
        second = new MyInt(99);
        assertNotEquals(first, second);
    }

    @Test
    void testToString() {
        MyInt first = new MyInt(-99);
        assertEquals(first.toString(), "MyInt{-99}");

        first = new MyInt(99);
        assertEquals(first.toString(), "MyInt{+99}");

        first = new MyInt(+99);
        assertEquals(first.toString(), "MyInt{+99}");

    }

    @Test
    void gcd() {
        MyInt gcd;
        gcd = MyInt.gcd(new MyInt(0), new MyInt(0));
        assertEquals(new MyInt(0), gcd);

        gcd = MyInt.gcd(new MyInt(4), new MyInt(2));
        assertEquals(new MyInt(2), gcd);

        gcd = MyInt.gcd(new MyInt(16), new MyInt(4));
        assertEquals(new MyInt(4), gcd);


        gcd = MyInt.gcd(new MyInt(11), new MyInt(3));
        assertEquals(new MyInt(1), gcd);

        gcd = MyInt.gcd(new MyInt(123), new MyInt(4));
        assertEquals(new MyInt(1), gcd);


    }

    @Test
    void multiply() {
        MyInt firstNumber = new MyInt(0);
        MyInt secondNumber = new MyInt(0);
        MyInt result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(0), result);

        firstNumber = new MyInt(10);
        secondNumber = new MyInt(2);
        result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(20), result);


        firstNumber = new MyInt(-71777);
        secondNumber = new MyInt(8);
        result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(-574216), result);


        firstNumber = new MyInt(-10);
        secondNumber = new MyInt(2);
        result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(-20), result);


        firstNumber = new MyInt(71777);
        secondNumber = new MyInt(-8);
        result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(-574216), result);


        firstNumber = new MyInt(10);
        secondNumber = new MyInt(-2);
        result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(-20), result);


        firstNumber = new MyInt(90);
        secondNumber = new MyInt(2);
        result = MyInt.multiply(firstNumber, secondNumber);

        assertEquals(new MyInt(180), result);


        firstNumber = new MyInt(90);
        secondNumber = new MyInt(1);

        result = MyInt.multiply(firstNumber, secondNumber);
        assertEquals(new MyInt(90), result);


    }


    @Test
    void divide() {
        MyInt firstNumber = new MyInt(0);
        MyInt secondNumber = new MyInt(1);
        MyInt result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(0), result);


        Throwable thrown = assertThrows(ArithmeticException.class, () -> MyInt.divide(new MyInt(0), new MyInt(0)));
        assertNotNull(thrown.getMessage());


        firstNumber = new MyInt(3);
        secondNumber = new MyInt(3);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(1), result);


        firstNumber = new MyInt(90);
        secondNumber = new MyInt(90);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(1), result);


        firstNumber = new MyInt(909);
        secondNumber = new MyInt(3);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(303), result);


        firstNumber = new MyInt(20);
        secondNumber = new MyInt(3);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(6), result);


        firstNumber = new MyInt(21);
        secondNumber = new MyInt(3);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(7), result);


        firstNumber = new MyInt(-21);
        secondNumber = new MyInt(3);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(-7), result);


        firstNumber = new MyInt(21);
        secondNumber = new MyInt(-3);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(-7), result);


        firstNumber = new MyInt(9);
        secondNumber = new MyInt(20);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(0), result);


        firstNumber = new MyInt(-9);
        secondNumber = new MyInt(20);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(0), result);


        firstNumber = new MyInt(-9000);
        secondNumber = new MyInt(90);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(-100), result);


        firstNumber = new MyInt(9000);
        secondNumber = new MyInt(90);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(100), result);


        firstNumber = new MyInt(99);
        secondNumber = new MyInt(9);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(11), result);


        firstNumber = new MyInt(979);
        secondNumber = new MyInt(90);
        result = MyInt.divide(firstNumber, secondNumber);

        assertEquals(new MyInt(10), result);


    }

}
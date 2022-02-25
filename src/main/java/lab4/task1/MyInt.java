package lab4.task1;


import java.util.*;
import java.util.regex.Pattern;


public class MyInt {
    private final Pattern pattern = Pattern.compile("(-|\\+)?\\d+(\\.\\d+)?");


    private ArrayList<String> payLoad;
    private SignEnum sign;

    private MyInt(ArrayList<String> payLoad, SignEnum sign) {
        this.payLoad = new ArrayList<>(payLoad);
        this.sign = sign;
    }

    public MyInt(int number) {
        String numberString = String.valueOf(number);
        createPayloadAndSign(parsePayloadFromString(numberString));
    }


    public MyInt(String numberString) {
        createPayloadAndSign(parsePayloadFromString(numberString));
    }

    public MyInt(String[] byteArrayNumber) {
        String numberString = String.join("", byteArrayNumber);

        if (Arrays.asList(byteArrayNumber).contains(null)
                || Arrays.asList(byteArrayNumber).contains(""))
            throw new IllegalArgumentException("array should be optional sign and sequence number or sequence number only without spaces or empty ");

        createPayloadAndSign(parsePayloadFromString(numberString));

    }


    private void createPayloadAndSign(ArrayList<String> parsePayloadFromString) {
        if (parsePayloadFromString.get(0).equals("+")) this.sign = SignEnum.POSITIVE;
        else if (parsePayloadFromString.get(0).equals("-")) this.sign = SignEnum.NEGATIVE;
        else {
            throw new IllegalArgumentException("error get sign from parsing data");
        }
        this.payLoad = new ArrayList<>(parsePayloadFromString.subList(1, parsePayloadFromString.size()));
    }

    private ArrayList<String> parsePayloadFromString(String numberString) {
        final ArrayList<String> data = new ArrayList<>();
        if (numberString == null || numberString.isEmpty())
            throw new IllegalArgumentException("string should be not empty");
        else if (!pattern.matcher(numberString).matches()) {
            throw new IllegalArgumentException("string should be optional sign and sequence number or sequence number only without spaces ");
        } else {
            if (numberString.charAt(0) == '-'
                    || numberString.charAt(0) == '+') {
                if (numberString.length() == 1) throw new IllegalArgumentException("string should be not only sign");
                else {
                    data.add(String.valueOf(numberString.charAt(0)));
                    numberString = numberString.substring(1);
                }
            } else {
                data.add("+");
            }
            for (char item : numberString.toCharArray()) {
                data.add(String.valueOf(item));
            }


        }
        ArrayList<String> dataWithoutLeadZeros = new ArrayList<>();
        dataWithoutLeadZeros.add(data.get(0));
        dataWithoutLeadZeros.addAll(removeLeadingZeros(data.subList(1, data.size())));

        return dataWithoutLeadZeros;
    }

    private static ArrayList<String> removeLeadingZeros(List<String> payLoad) {
        ArrayList<String> dataWithoutLeadZeros = new ArrayList<>();
        if (payLoad.get(0).equals("0")) {
            for (int i = 0; i < payLoad.size(); i++) {
                if (!payLoad.get(i).equals("0")) {
                    dataWithoutLeadZeros.addAll(
                            payLoad.subList(i, payLoad.size())
                    );
                    break;
                }

            }
        } else {
            dataWithoutLeadZeros = new ArrayList<>(payLoad);
        }
        if (dataWithoutLeadZeros.size() == 0) dataWithoutLeadZeros.add("0");
        return dataWithoutLeadZeros;
    }

    public MyInt normalize(int size) {
        final ArrayList<String> changedPayload = new ArrayList<>(this.payLoad);
        if (changedPayload.size() > size) {
            throw new IllegalArgumentException("the normalization size must be greater than the data size of the number");
        }
        while (changedPayload.size() != size) changedPayload.add(0, "0");
        return new MyInt(changedPayload, this.sign);
    }


    public MyInt add(MyInt number) {
        MyInt firstNumber = new MyInt(new ArrayList<>(this.getCopyPayload()), this.sign);
        MyInt secondNumber = new MyInt(new ArrayList<>(number.getCopyPayload()), number.sign);

        int maxSize = Math.max(firstNumber.payLoad.size(), secondNumber.payLoad.size());
        if (firstNumber.payLoad.size() != maxSize) firstNumber = firstNumber.normalize(maxSize);
        if (secondNumber.payLoad.size() != maxSize) secondNumber = secondNumber.normalize(maxSize);

        ArrayList<String> firstNumberPayloadCopy = new ArrayList<>(firstNumber.payLoad);
        ArrayList<String> secondNumberPayloadCopy = new ArrayList<>(secondNumber.payLoad);
        ArrayList<String> answer = new ArrayList<>();

        if (this.sign.equals(secondNumber.sign)) {
            int overflow = 0;
            while (true) {
                if (firstNumberPayloadCopy.size() == 0 && secondNumberPayloadCopy.size() == 0) {
                    if (overflow != 0)
                        answer.addAll(0, new ArrayList<>(Arrays.asList(String.valueOf(overflow).split(""))));
                    break;
                }

                int firstNumberPos = Integer.parseInt(firstNumberPayloadCopy.get(firstNumberPayloadCopy.size() - 1));
                int secondNumberPos = Integer.parseInt(secondNumberPayloadCopy.get(secondNumberPayloadCopy.size() - 1));
                firstNumberPayloadCopy.remove(firstNumberPayloadCopy.size() - 1);
                secondNumberPayloadCopy.remove(secondNumberPayloadCopy.size() - 1);

                int result = firstNumberPos + secondNumberPos + overflow;
                if (result > 9) {
                    String strOverflow = String.valueOf(result);
                    answer.add(0, strOverflow.substring(strOverflow.length() - 1));
                    strOverflow = strOverflow.substring(0, strOverflow.length() - 1);
                    if (strOverflow.isEmpty()) {
                        overflow = 0;
                    } else {
                        overflow = Integer.parseInt(strOverflow);
                    }
                } else {
                    answer.add(0, String.valueOf(result));
                }
            }
            return new MyInt(removeLeadingZeros(answer), firstNumber.sign);

        } else {
            MyInt findMaxValue = MyInt.max(new MyInt(firstNumber.getCopyPayload(), SignEnum.POSITIVE), new MyInt(secondNumber.getCopyPayload(), SignEnum.POSITIVE));
            MyInt temp;
            if (!firstNumber.payLoad.equals(findMaxValue.payLoad)) {
                temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }
            MyInt result = new MyInt(firstNumber.getCopyPayload(), SignEnum.POSITIVE).subtract(new MyInt(secondNumber.getCopyPayload(), SignEnum.POSITIVE));
            return new MyInt(removeLeadingZeros(result.getCopyPayload()), firstNumber.sign);
        }

    }


    public MyInt subtract(MyInt number) {
        MyInt firstNumber = new MyInt(new ArrayList<>(this.payLoad), this.sign);
        MyInt secondNumber = new MyInt(new ArrayList<>(number.payLoad), number.sign);

        if (firstNumber.equals(new MyInt(0)) && secondNumber.equals(new MyInt(0))) return new MyInt(0);

        int maxSize = Math.max(this.payLoad.size(), secondNumber.payLoad.size());
        if (this.payLoad.size() != maxSize) firstNumber = firstNumber.normalize(maxSize);
        if (secondNumber.payLoad.size() != maxSize) secondNumber = secondNumber.normalize(maxSize);

        ArrayList<String> firstNumberPayloadCopy = new ArrayList<>(firstNumber.payLoad);
        ArrayList<String> secondNumberPayloadCopy = new ArrayList<>(secondNumber.payLoad);
        ArrayList<String> answer = new ArrayList<>();

        if (firstNumber.sign.equals(SignEnum.NEGATIVE) && secondNumber.sign.equals(SignEnum.NEGATIVE)) {
            return new MyInt(firstNumber.getCopyPayload(), SignEnum.NEGATIVE).add(new MyInt(secondNumber.getCopyPayload(), SignEnum.POSITIVE));

        } else if (firstNumber.sign.equals(SignEnum.POSITIVE) && secondNumber.sign.equals(SignEnum.POSITIVE)) {
            SignEnum sign;

            MyInt findMaxValue = MyInt.max(firstNumber, secondNumber);
            MyInt temp;
            if (firstNumber.equals(findMaxValue)) {
                sign = SignEnum.POSITIVE;
            } else {
                temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
                sign = SignEnum.NEGATIVE;
            }

            while (firstNumberPayloadCopy.size() != 0 || secondNumberPayloadCopy.size() != 0) {

                int firstNumberPos = Integer.parseInt(firstNumberPayloadCopy.get(firstNumberPayloadCopy.size() - 1));
                int secondNumberPos = Integer.parseInt(secondNumberPayloadCopy.get(secondNumberPayloadCopy.size() - 1));

                int result = firstNumberPos - secondNumberPos;

                if (result < 0) {
                    firstNumberPayloadCopy = getNewPayloadTakeFromHigherRanks(firstNumberPayloadCopy, -1);
                    firstNumberPos = Integer.parseInt(firstNumberPayloadCopy.get(firstNumberPayloadCopy.size() - 1));
                    result = firstNumberPos - secondNumberPos;
                }
                answer.add(0, String.valueOf(result));
                firstNumberPayloadCopy.remove(firstNumberPayloadCopy.size() - 1);
                secondNumberPayloadCopy.remove(secondNumberPayloadCopy.size() - 1);
            }
            return new MyInt(removeLeadingZeros(answer), firstNumber.sign);
        } else {
            MyInt result = new MyInt(firstNumber.getCopyPayload(), SignEnum.POSITIVE).add(new MyInt(secondNumber.getCopyPayload(), SignEnum.POSITIVE));
            return new MyInt(removeLeadingZeros(result.getCopyPayload()), SignEnum.NEGATIVE);
        }

    }

    private ArrayList<String> getNewPayloadTakeFromHigherRanks(ArrayList<String> payloadData, int currentPos) {
        int indexPos = payloadData.size() + currentPos;

        String currentNumber = payloadData.get(indexPos);
        if (currentPos != -1) {
            if (currentNumber.equals("0")) {
                payloadData = getNewPayloadTakeFromHigherRanks(payloadData, --currentPos);
            } else {
                payloadData.set(indexPos, String.valueOf(
                        Integer.parseInt(currentNumber) - 1)
                );
                return payloadData;
            }
        }

        if (currentPos == -1) {
            payloadData.set(indexPos, String.valueOf(
                    Integer.parseInt(currentNumber) + 10)
            );
            payloadData = getNewPayloadTakeFromHigherRanks(payloadData, --currentPos);
        } else {
            payloadData.set(indexPos, String.valueOf(9));
        }
        return payloadData;
    }

    public static MyInt max(MyInt numberOne, MyInt numberTwo) {
        MyInt firstNumber = new MyInt(new ArrayList<>(numberOne.payLoad), numberOne.sign);
        MyInt secondNumber = new MyInt(new ArrayList<>(numberTwo.payLoad), numberTwo.sign);


        //TODO how refactor this? or use Optional
        MyInt maxValue = null;
        if (numberOne.equals(new MyInt(0)) && numberTwo.equals(new MyInt(0))) return new MyInt(0);

        if (!firstNumber.sign.equals(secondNumber.sign)) {
            if (firstNumber.sign.equals(SignEnum.POSITIVE)) return firstNumber;
            if (secondNumber.sign.equals(SignEnum.POSITIVE)) return secondNumber;

        } else if (firstNumber.sign.equals(SignEnum.POSITIVE)) {
            int maxSize = Math.max(firstNumber.payLoad.size(), secondNumber.payLoad.size());
            if (firstNumber.payLoad.size() != maxSize) firstNumber = firstNumber.normalize(maxSize);
            if (secondNumber.payLoad.size() != maxSize) secondNumber = secondNumber.normalize(maxSize);

            for (int i = 0; i < firstNumber.payLoad.size(); i++) {
                int currentFirstNum = Integer.parseInt(firstNumber.payLoad.get(i));
                int currentSecondNum = Integer.parseInt(secondNumber.payLoad.get(i));
                if (currentFirstNum > currentSecondNum) {
                    maxValue = firstNumber;
                    break;
                } else if (currentFirstNum < currentSecondNum) {
                    maxValue = secondNumber;
                    break;

                }
            }


        } else {
            int maxSize = Math.max(firstNumber.payLoad.size(), secondNumber.payLoad.size());
            if (firstNumber.payLoad.size() != maxSize) firstNumber = firstNumber.normalize(maxSize);
            if (secondNumber.payLoad.size() != maxSize) secondNumber = secondNumber.normalize(maxSize);

            for (int i = 0; i < firstNumber.payLoad.size(); i++) {
                int currentFirstNum = Integer.parseInt(firstNumber.payLoad.get(i));
                int currentSecondNum = Integer.parseInt(secondNumber.payLoad.get(i));
                if (currentFirstNum > currentSecondNum) {
                    maxValue = secondNumber;
                    break;

                } else if (currentFirstNum < currentSecondNum) {
                    maxValue = firstNumber;
                    break;

                }
            }

        }

        return maxValue;
    }

    public static MyInt min(MyInt numberOne, MyInt numberTwo) {
        MyInt firstNumber = new MyInt(new ArrayList<>(numberOne.payLoad), numberOne.sign);
        MyInt secondNumber = new MyInt(new ArrayList<>(numberTwo.payLoad), numberTwo.sign);

        if (!firstNumber.sign.equals(secondNumber.sign)) {
            if (firstNumber.sign.equals(SignEnum.NEGATIVE)) return firstNumber;
            else return secondNumber;
        } else {

            if (firstNumber.sign.equals(SignEnum.POSITIVE)) {
                MyInt max = MyInt.max(firstNumber, secondNumber);
                if (firstNumber.equals(max)) return secondNumber;
                else return firstNumber;
            } else {

                MyInt absFirst = MyInt.abs(firstNumber);
                MyInt max = MyInt.max(absFirst, MyInt.abs(secondNumber));
                if (absFirst.equals(max)) return firstNumber;
                else return secondNumber;
            }
        }

    }


    public static MyInt abs(MyInt number) {

        return new MyInt(number.getCopyPayload(), SignEnum.POSITIVE);
    }

    public int compareTo(MyInt number) {
        if (this.equals(number)) return 0;
        else if (this.equals(MyInt.max(this, number)))
            return 1;
        else return -1;

    }

    public long getLongValue() {
        int longMaxSize = String.valueOf(Long.MAX_VALUE).length();
        String sign = this.sign.equals(SignEnum.POSITIVE) ? "+" : "-";

        if (payLoad.size() > longMaxSize) {
            String result = String.join("", this.payLoad.subList(payLoad.size() - longMaxSize, payLoad.size()));
            result = sign + result;
            return new Long(result);
        }
        String result = String.join("", this.payLoad);
        result = sign + result;
        return new Long(result);
    }


    public ArrayList<String> getCopyPayload() {
        return new ArrayList<>(payLoad);
    }

    public SignEnum getSign() {
        return sign;
    }

    /**
     * firstNumber devide secondNumber
     **/
    public static MyInt divide(MyInt firstNumber, MyInt secondNumber) throws ArithmeticException {


        if (secondNumber.equals(new MyInt(0))) throw new ArithmeticException("zero division error");
        if (firstNumber.equals(new MyInt(0))) return new MyInt(0);

        SignEnum resultSign = firstNumber.sign.equals(SignEnum.NEGATIVE) || secondNumber.sign.equals(SignEnum.NEGATIVE)
                ? SignEnum.NEGATIVE : SignEnum.POSITIVE;
        firstNumber = MyInt.abs(firstNumber);
        secondNumber = MyInt.abs(secondNumber);

        ArrayList<String> answer = new ArrayList<>();
        if (firstNumber.equals(secondNumber)) return new MyInt(new MyInt(1).getCopyPayload(), resultSign);
        if (firstNumber.compareTo(secondNumber) == -1) return new MyInt(0);


        //Prepare first divide number
        String[] prepareNumberToDivide = firstlyPrepareNumberToDivide(firstNumber, secondNumber).split(" ");
        StringBuilder nowOperationNumber = new StringBuilder(prepareNumberToDivide[0]);
        String residue = prepareNumberToDivide.length > 1 ? prepareNumberToDivide[1] : "";

        int divider = adjustTheDivider(nowOperationNumber.toString(), secondNumber);
        answer.add(String.valueOf(divider));

        String resultAfterFirstDivide = String.join(
                "",
                new MyInt(nowOperationNumber.toString())
                        .subtract(MyInt.multiply(secondNumber, new MyInt(divider)))
                        .payLoad
        );
        nowOperationNumber = new StringBuilder(resultAfterFirstDivide.equals("0") ? "" : resultAfterFirstDivide);

        StringBuilder residueSb = new StringBuilder(residue);
        while (true) {
            if (residueSb.toString().length() == 1) {
                if (new MyInt(nowOperationNumber + residueSb.toString()).compareTo(secondNumber) == -1) {
                    answer.add("0");
                    residueSb = new StringBuilder();
                    break;
                } else {
                    divider = adjustTheDivider(residueSb.toString(), secondNumber);
                    answer.add(String.valueOf(divider));
                    residueSb = new StringBuilder();
                    break;
                }
            }


            for (int i = 0; i < residueSb.toString().length(); i++) {
                nowOperationNumber.append(residueSb.charAt(i));
                residueSb.delete(i, i + 1);

                int compareTo = new MyInt(nowOperationNumber.toString()).compareTo(secondNumber);
                if (compareTo != -1) {
                    divider = adjustTheDivider(nowOperationNumber.toString(), secondNumber);
                    answer.add(String.valueOf(divider));
                    resultAfterFirstDivide = String.join(
                            "",
                            new MyInt(nowOperationNumber.toString())
                                    .subtract(MyInt.multiply(secondNumber, new MyInt(divider)))
                                    .payLoad
                    );
                    nowOperationNumber = new StringBuilder(resultAfterFirstDivide.equals("0") ? "" : resultAfterFirstDivide);
                    break;
                }

                answer.add(String.valueOf(0));
            }

            if (residueSb.toString().length() == 0 ) {
                break;
            }


        }


        return new MyInt(removeLeadingZeros(answer), resultSign);
    }


    private static int adjustTheDivider(String nowOperationNumber, MyInt secondNumber) {
        MyInt resultNumberToDivide = new MyInt(nowOperationNumber);
        for (int i = 2; i < 10; i++) {
            MyInt multiplyResult = MyInt.multiply(secondNumber, new MyInt(i));
            int compareTo = multiplyResult.compareTo(resultNumberToDivide);
            if (compareTo == 1) {
                return --i;
            }
        }
        return 0;
    }

    private static String firstlyPrepareNumberToDivide(MyInt firstNumber, MyInt secondNumber) {
        String residue = String.join("", firstNumber.payLoad);
        String nowOperationNumber = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < residue.length(); i++) {
            sb.append(residue.charAt(i));
            nowOperationNumber = sb.toString();
            int compereCondition = new MyInt(nowOperationNumber).compareTo(secondNumber);
            if (compereCondition == 1 || compereCondition == 0) {
                if (i != residue.length() - 1) {
                    nowOperationNumber = sb.toString();
                    residue = String.join("", residue.substring(i + 1));
                } else {
                    residue = "";
                }
                break;
            }
        }
        return String.format("%s %s", nowOperationNumber, residue);
    }

    public static MyInt gcd(MyInt a, MyInt b) {
        a = abs(a);
        b = abs(b);
        MyInt max, min;
        max = MyInt.max(a, b);
        min = MyInt.min(a, b);
        a = max;
        b = min;
        if (a.equals(new MyInt(0)) || b.equals(new MyInt(0))) return new MyInt(0);
        while (!a.equals(b)) {
            if (a.compareTo(b) == 1)
                a = a.subtract(b);
            else
                b = b.subtract(a);

        }
        return a;
    }

    private static ArrayList<String> simpleMultiply(MyInt number, int numberToMultiply) {
        ArrayList<String> payLoad = number.getCopyPayload();
        ArrayList<String> answer = new ArrayList<>();
        Collections.reverse(payLoad);
        int overflow = 0;
        for (String i : payLoad) {
            int firstNumberPos = Integer.parseInt(i);
            int result = firstNumberPos * numberToMultiply + overflow;
            if (result > 9) {
                String strOverflow = String.valueOf(result);
                answer.add(0, strOverflow.substring(strOverflow.length() - 1));
                strOverflow = strOverflow.substring(0, strOverflow.length() - 1);
                if (strOverflow.isEmpty()) {
                    overflow = 0;
                } else {
                    overflow = Integer.parseInt(strOverflow);
                }
            } else {
                overflow = 0;
                answer.add(0, String.valueOf(result));
            }
        }
        if (overflow != 0) answer.add(0, String.valueOf(overflow));
        return answer;

    }

    public static MyInt multiply(MyInt firstNumber, MyInt secondNumber) {
        HashMap<Integer, ArrayList<String>> data = new HashMap<>();
        for (int i = 0; i < secondNumber.payLoad.size(); i++) {
            int numberToMultiply = Integer.parseInt(secondNumber.payLoad.get(i));
            data.put(i, simpleMultiply(firstNumber, numberToMultiply));
        }
        for (Map.Entry<Integer, ArrayList<String>> entry : data.entrySet()) {
            ArrayList<String> value = entry.getValue();
            value.addAll(Collections.nCopies(entry.getKey(), "0"));
        }
        MyInt result = new MyInt(0);
        for (ArrayList<String> payLoad : data.values()) {
            result = result.add(new MyInt(payLoad, SignEnum.POSITIVE));
        }
        SignEnum resultSign = firstNumber.sign.equals(SignEnum.NEGATIVE) || secondNumber.sign.equals(SignEnum.NEGATIVE)
                ? SignEnum.NEGATIVE : SignEnum.POSITIVE;
        return new MyInt(removeLeadingZeros(result.getCopyPayload()), resultSign);
    }

    @Override
    public String toString() {
        return "MyInt{" +
                (sign.equals(SignEnum.POSITIVE) ? "+" : "-") +
                String.join("", payLoad) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyInt myInt = (MyInt) o;

        if (!removeLeadingZeros(getCopyPayload()).equals(removeLeadingZeros(myInt.getCopyPayload()))) return false;
        if (this.payLoad.size() == 1 && this.payLoad.get(0).equals("0")
                && myInt.payLoad.size() == 1 && myInt.payLoad.get(0).equals("0"))
            return true;
        return sign == myInt.sign;
    }

    @Override
    public int hashCode() {
        int result = payLoad.hashCode();
        result = 31 * result + sign.hashCode();
        return result;
    }
}

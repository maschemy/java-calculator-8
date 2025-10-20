package calculator;

import java.util.regex.Pattern;
import camp.nextstep.edu.missionutils.Console;

public class Application {

    public static void main(String[] args) {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        try {
            int result = addNumbers(input);
            System.out.println("결과 : " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 입력입니다: " + e.getMessage());
        }
    }

    private static int addNumbers(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        if (input.startsWith("//") && input.contains("\\n") && !input.contains("\n")) {
            input = input.replace("\\n", "\n");
        }

        String numbers = input;
        String delimiterRegex = ",|:";

        if (input.startsWith("//")) {
            int newlineIndex = input.indexOf('\n');
            if (newlineIndex == -1) {
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다. 예) //;\\n1;2;3");
            }

            String custom = input.substring(2, newlineIndex);
            if (custom.isEmpty()) {
                throw new IllegalArgumentException("커스텀 구분자가 비어 있습니다.");
            }

            delimiterRegex = Pattern.quote(custom) + "|,|:";
            numbers = input.substring(newlineIndex + 1);
        }

        String[] tokens = numbers.split(delimiterRegex);
        int sum = 0;

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            int num;
            try {
                num = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
            }

            if (num < 0) {
                throw new IllegalArgumentException("잘못된 입력입니다 : " + num);
            }
            sum += num;
        }

        return sum;
    }
}

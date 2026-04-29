package numbers;

import java.util.function.LongPredicate;

public enum Property {
    EVEN(Property::isEven),
    ODD(n -> !isEven(n)),
    BUZZ(Property::isBuzz),
    DUCK(Property::isDuck),
    PALINDROMIC(Property::isPalindrome),
    GAPFUL(Property::isGapful),
    SPY(Property::isSpy),
    SQUARE(Property::isSquare),
    SUNNY(Property::isSunny),
    JUMPING(Property::isJumping),
    HAPPY(Property::isHappy),
    SAD(n -> !isHappy(n));


    private final LongPredicate predicate;

    Property(LongPredicate predicate) {
        this.predicate = predicate;
    }

    public boolean test(long n) {
        return predicate.test(n);
    }

    public String displayName() {
        return name().toLowerCase();
    }

    private static boolean isDuck(long num) {
        while (num > 0) {
            if (num % 10 == 0) {
                return true;
            }
            num /= 10;
        }
        return false;
    }

    private static boolean isPalindrome(long num) {
        if (num % 10 == 0) {
            return false;
        }
        long copy = num;
        long reversedHalf = 0;
        while (reversedHalf < copy) {
            reversedHalf = reversedHalf * 10 + (copy % 10);
            copy /= 10;
        }
        return (reversedHalf == copy) || (reversedHalf / 10 == copy);
    }

    private static boolean isBuzz(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    private static boolean isEven(long num) {
        return (num % 2 == 0);
    }

    private static boolean isGapful(long num) {
        if (num / 100 == 0) {
            return false;
        }

        long copy = num;
        int lastDigit = (int) (num % 10);

        while (copy > 0) {
            copy /= 10;
        }
        int firstDigit = (int) copy;
        int divisor = firstDigit * 10 + lastDigit;

        return num % divisor == 0;
    }

    private static boolean isSpy(long num) {
        long copy = num;
        int sum = 0;
        long product = 1;
        while (copy > 0) {
            int digit = (int) (copy % 10);
            sum += digit;
            product *= digit;
            copy /= 10;
        }

        return sum == product;
    }

    private static boolean isSquare(long num) {
        long root = (long) Math.sqrt(num);
        return root * root == num;
    }

    private static boolean isSunny(long num) {
        return isSquare(num + 1);
    }

    private static boolean isJumping(long num) {
        long copy = num;
        int previous = (int) (copy % 10);
        copy /= 10;
        while (copy > 0) {
            int current = (int) (copy % 10);
            if (Math.abs(previous - current) != 1) {
                return false;
            }
            previous = current;
            copy /= 10;
        }
        return true;
    }

    private static boolean isHappy(long num) {
        while (num != 1 && num != 4) {
            num = calculateHappy(num);
        }

        return num == 1;
    }

    private static long calculateHappy(long num) {
        long sum = 0;

        while (num > 0) {
            int digit = (int) (num % 10);
            sum += digit * digit;
            num /= 10;
        }

        return sum;
    }
}

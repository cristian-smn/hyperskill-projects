another function for a slightly different generator(just numbers
and can t begin with zero):
//        boolean[] usedDigit = new boolean[10];
//        while (sb.length() < codeLength) {
//            int digit = random.nextInt(10);
//            if (!usedDigit[digit] && !(digit == 0 && sb.isEmpty())) {
//                usedDigit[digit] = true;
//                sb.append(digit);
//            }
//        }
//        return sb.toString();
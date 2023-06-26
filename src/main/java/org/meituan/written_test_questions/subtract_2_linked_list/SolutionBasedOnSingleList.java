package org.meituan.written_test_questions.subtract_2_linked_list;


import java.util.Objects;
import java.util.logging.Logger;

public class SolutionBasedOnSingleList implements Subtract2LinkedList {
    Logger log = Logger.getLogger(SolutionBasedOnSingleList.class.getName());

    static final class SingleList implements LinkedList, Results {
        private SingleList next;
        private final int value;

        private boolean nativeResults;

        SingleList(int value) {
            this.value = value;
        }

        @Override
        public String formatTheLinkedListAsHumanReadableString() {
            return "当前链表表示的数据为: " +  listToNumber(this);
        }


        public void setNativeResults(boolean nativeResults) {
            this.nativeResults = nativeResults;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (SingleList) obj;
            return Objects.equals(this.next, that.next) &&
                    this.value == that.value;
        }

        @Override
        public String toString() {
            return "SingleList[" +
                    "next=" + next + ", " +
                    "value=" + value + ']';
        }

        public SingleList next() {
            return next;
        }

        public int value() {
            return value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(next, value);
        }
    }


    /**
     * 递归
     */
    public SingleList createList(char[] chars, int currentIndex, SingleList root, SingleList parentNode) {
        if (currentIndex >= chars.length) {
            return root;
        }

        char numberInChar = chars[currentIndex];
        int number = Character.getNumericValue(numberInChar);
        SingleList next;
        if (parentNode == null) {
            next = new SingleList(number);
        } else {
            next = new SingleList(number);
            parentNode.next = next;
        }

        return createList(chars, currentIndex + 1, root == null ? next : root, next);
    }


    @Override
    public LinkedList[] create2List(Long number1, Long number2) {
        if (number1 < 0 || number2 < 0) {
            throw new RuntimeException("The input should greater than or equals to 0");
        }

        char[] i1 = Long.toString(number1).toCharArray();
        SingleList o1 = createList(i1, 0, null, null);

        char[] i2 = Long.toString(number2).toCharArray();
        SingleList o2 = createList(i2, 0, null, null);
        return new LinkedList[]{o1, o2};
    }

    private static StringBuilder toChars(SingleList next, StringBuilder rs) {
        if (next == null) {
            return rs;
        }

        rs.append(next.value);

        return toChars(next.next, rs);
    }

    private static Long listToNumber(SingleList list) {
        StringBuilder sb = new StringBuilder();
        StringBuilder theBuilder = toChars(list, sb);
        Long number =  Long.parseLong(theBuilder.toString());
        return list.nativeResults ? -number : number;
    }

    @Override
    public Results subtracting(LinkedList i1, LinkedList i2) {
        Long o1 = listToNumber((SingleList) i1);
        Long o2 = listToNumber((SingleList) i2);
        long output = o1 - o2;
        SingleList rs = createList(Long.toString(Math.abs(output)).toCharArray(), 0, null, null);
        rs.setNativeResults(output < 0);
        return rs;
    }
}

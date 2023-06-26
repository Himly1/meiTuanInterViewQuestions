package org.meituan.written_test_questions.subtract_2_linked_list;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;


class SolutionBasedOnSingleListTest {
    private final Logger log = Logger.getLogger(SolutionBasedOnSingleListTest.class.getName());

    SolutionBasedOnSingleList solution = new SolutionBasedOnSingleList();
    @Test
    void createList() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(123L,456L);
        SolutionBasedOnSingleList.SingleList o1 = (SolutionBasedOnSingleList.SingleList) list[0];
        SolutionBasedOnSingleList.SingleList o2 = (SolutionBasedOnSingleList.SingleList) list[1];
        assert o1.value() == 1 && o1.next().value() == 2 && o1.next().next().value() == 3;
        assert o2.value() == 4 && o2.next().value() == 5 && o2.next().next().value() == 6;
    }


    @Test
    void subtractingOnNonZeroNumbers() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(456L, 123L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        assert subtract.value() == 3 && subtract.next().value() == 3 && subtract.next().next().value() == 3;
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: 333");
    }

    @Test
    void subtractingOnLeftNumberIsZero() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(0L, 123L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        log.info("subtract? " + subtract.toString());
        assert subtract.value() == 1 && subtract.next().value() == 2 && subtract.next().next().value() == 3;
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: -123");
    }

    @Test
    void subtractingOnRightNumberIsZero() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(123L, 0L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        assert subtract.value() == 1 && subtract.next().value() == 2 && subtract.next().next().value() == 3;
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: 123");
    }

    @Test
    void subtractingOnLeftNumberGreaterThanRight() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(123L, 456L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        assert subtract.value() == 3 && subtract.next().value() == 3 && subtract.next().next().value() == 3;
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: -333");
    }

    @Test
    void subtractingOnLeftNumberLessThanRight() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(456L, 123L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        assert subtract.value() == 3 && subtract.next().value() == 3 && subtract.next().next().value() == 3;
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: 333");
    }

    @Test
    void subtractingOnLeftAndRightEquals() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(456L, 456L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        assert subtract.value() == 0 && subtract.next() == null;
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: 0");
    }

    @Test
    void leftNumberAndRightBothShouldGraterThanOrEqualsToZero() {
        try {
            solution.create2List(Long.MIN_VALUE, 456L);
        }catch (RuntimeException ex) {
            assert ex.getMessage().equals("The input should greater than or equals to 0");
        }
    }

    @Test
    void ensureTheResultsIsRightOnMAXVALUE() {
        Subtract2LinkedList.LinkedList[] list = solution.create2List(Long.MAX_VALUE, 456L);
        SolutionBasedOnSingleList.SingleList subtract = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list[0], list[1]);
        assert subtract.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: " + (Long.MAX_VALUE - 456L));

        Subtract2LinkedList.LinkedList[] list2 = solution.create2List(456L, Long.MAX_VALUE);
        SolutionBasedOnSingleList.SingleList subtract2 = (SolutionBasedOnSingleList.SingleList) solution.subtracting(list2[0], list2[1]);
        assert subtract2.formatTheLinkedListAsHumanReadableString().equals("当前链表表示的数据为: " + ((456L - Long.MAX_VALUE)));
    }
}
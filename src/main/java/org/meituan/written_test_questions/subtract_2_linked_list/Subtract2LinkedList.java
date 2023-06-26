package org.meituan.written_test_questions.subtract_2_linked_list;

/**
 * 给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照顺序的方式存储的，并且每个节点只能存储一位数字。
 * 请你将两个数相减，并以相同形式返回一个表示相减结果的链表。
 * 你可以假设
 * 1）除了数字 0 之外，这两个数都不会以 0开头。
 * 2）给定的第一个数字一定比第二个数字大。
 * 举例：
 * 输入：l1 = [9,8,7], l2 = [5,1,2]
 * 输出：[4,7,5]
 * 解释：987-512 = 475.
 */
public interface Subtract2LinkedList {
    interface LinkedList {
        String formatTheLinkedListAsHumanReadableString();
    }

    interface Results {
        String formatTheLinkedListAsHumanReadableString();
    }

    /**
     * 通过数字创建链表
     * @param number1 i1
     * @param number2 i2
     * @return lists
     */
    LinkedList[] create2List(Long number1, Long number2);

    Results subtracting(LinkedList i1, LinkedList i2);
}
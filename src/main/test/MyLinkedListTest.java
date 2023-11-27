import org.aston.MyArrayList;
import org.aston.MyLinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

class MyLinkedListTest {

    MyLinkedList<Integer> myLinkedList;

    @BeforeEach
    public void beforeEach() {
        myLinkedList = new MyLinkedList<>();
    }

    @Test
    void testAddAndGetElement() {
        for (int i = 0; i < 100; i++) {
            myLinkedList.add(i);
        }

        for (int i = 0; i < 100; i++) {
            int tmp = myLinkedList.get(i);
            Assertions.assertEquals(tmp, i);
        }

        Assertions.assertEquals(100, myLinkedList.size());
    }

    @Test
    void testAddByIndex() {
        for (int i = 0; i < 100; i++) {
            myLinkedList.add(i);
        }

        myLinkedList.add(999, 11);
        myLinkedList.add(999, 22);
        myLinkedList.add(999, 33);
        myLinkedList.add(999, 44);
        myLinkedList.add(999, 55);

        for (int i = 0; i < 100; i++) {
            System.out.println(myLinkedList.get(i));
        }

        Assertions.assertEquals(999, myLinkedList.get(11));
        Assertions.assertEquals(11, myLinkedList.get(12));
        Assertions.assertEquals(999, myLinkedList.get(22));
        Assertions.assertEquals(21, myLinkedList.get(23));
        Assertions.assertEquals(999, myLinkedList.get(33));
        Assertions.assertEquals(31, myLinkedList.get(34));
        Assertions.assertEquals(999, myLinkedList.get(44));
        Assertions.assertEquals(41, myLinkedList.get(45));
        Assertions.assertEquals(999, myLinkedList.get(55));
        Assertions.assertEquals(51, myLinkedList.get(56));
    }


    @Test
    void testSort() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            myLinkedList.add(random.nextInt(100));
        }

        myLinkedList.sort(Integer::compareTo);

        for (int i = 1; i < 10; i++) {
            Assertions.assertTrue(myLinkedList.get(i) > myLinkedList.get(i - 1));
        }
    }
}
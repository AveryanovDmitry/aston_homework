import org.aston.MyArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

class MyArrayListTest {

    MyArrayList<Integer> myArrayList;

    @BeforeEach
    public void beforeEach() {
        myArrayList = new MyArrayList<>();
    }

    @Test
    void testAddAndGetElement() {
        for (int i = 0; i < 100; i++) {
            myArrayList.add(i);
        }

        for (int i = 0; i < 100; i++) {
            int tmp = myArrayList.get(i);
            Assertions.assertEquals(tmp, i);
        }

        Assertions.assertEquals(100, myArrayList.size());
    }

    @Test
    void testAddByIndex() {
        for (int i = 0; i < 100; i++) {
            myArrayList.add(i);
        }

        myArrayList.add(999, 11);
        myArrayList.add(999, 22);
        myArrayList.add(999, 33);
        myArrayList.add(999, 44);
        myArrayList.add(999, 55);

        Assertions.assertEquals(999, myArrayList.get(11));
        Assertions.assertEquals(11, myArrayList.get(12));
        Assertions.assertEquals(999, myArrayList.get(22));
        Assertions.assertEquals(21, myArrayList.get(23));
        Assertions.assertEquals(999, myArrayList.get(33));
        Assertions.assertEquals(31, myArrayList.get(34));
        Assertions.assertEquals(999, myArrayList.get(44));
        Assertions.assertEquals(41, myArrayList.get(45));
        Assertions.assertEquals(999, myArrayList.get(55));
        Assertions.assertEquals(51, myArrayList.get(56));
    }


    @Test
    void testSort() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            myArrayList.add(random.nextInt(100));
        }

        myArrayList.sort(Integer::compareTo);

        for (int i = 1; i < 10; i++) {
            Assertions.assertTrue(myArrayList.get(i) > myArrayList.get(i - 1));
        }
    }
}

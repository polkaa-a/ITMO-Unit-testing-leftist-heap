import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class LeftistHeapTest {

    public LeftistHeap<Integer> leftistHeap;

    @BeforeEach
    public void setUp(){
        leftistHeap = new LeftistHeap<>();
    }

    @Test
    public void leftHeapCorrectInitialization(){
        assertNull(leftistHeap.getRoot());
    }

    @ParameterizedTest
    @ValueSource(ints = {
            18, -5
    })
    public void insertWhenIsEmpty(Integer key){
        leftistHeap.insert(key);
        assertNotNull(leftistHeap.getRoot());
        assertNull(leftistHeap.getRoot().getLeftChild());
        assertNull(leftistHeap.getRoot().getRightChild());
        assertEquals(0, leftistHeap.getRoot().getNullPathLength());
        assertEquals(key, leftistHeap.getRoot().getKey());
    }

    @Test
    public void mergeRoot1IsNull(){
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        LeftistHeap<Integer> secondHeap = new LeftistHeap<>();

        Integer root2 = 1;
        secondHeap.insert(root2);

        assertEquals(root2, firstHeap.merge(secondHeap).getKey());
    }

    @Test
    public void mergeRoot2IsNull(){
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        LeftistHeap<Integer> secondHeap = new LeftistHeap<>();

        Integer root1 = 1;
        firstHeap.insert(root1);

        assertEquals(root1, firstHeap.merge(secondHeap).getKey());
    }

    @Test
    public void mergeRoot1LessThanRoot2(){
        //check that returned root have root1 key
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        LeftistHeap<Integer> secondHeap = new LeftistHeap<>();

        Integer root1 = 1;
        Integer root2 = 8;

        firstHeap.insert(root1);
        secondHeap.insert(root2);

        assertEquals(root1, firstHeap.merge(secondHeap).getKey());
    }

    @Test
    public void mergeRoo1MoreThanRoot2(){
        //check that returned root have root2 key
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        LeftistHeap<Integer> secondHeap = new LeftistHeap<>();

        Integer root1 = 16;
        Integer root2 = 8;

        firstHeap.insert(root1);
        secondHeap.insert(root2);

        assertEquals(root2, firstHeap.merge(secondHeap).getKey());
    }

    @Test
    public void mergeRoot1LessThanRoot2Root1RightChildIsNullNPLToZero(){
        //check thar root.npl is 0
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        LeftistHeap<Integer> secondHeap = new LeftistHeap<>();

        Integer root1 = 2;
        Integer leftChild = 3;

        firstHeap.insert(root1);
        firstHeap.insert(leftChild);

        assertEquals(0, firstHeap.merge(secondHeap).getNullPathLength());
    }

    @ParameterizedTest
    @CsvSource({
            "10,12,14,11,12",
            "101,210,211,103,210"
    })
    public void mergeRoot1LessThanRoot2AndRoot1LeftChildNPLIsLessThanRight(int root, int left, int right, int newRight, int lastNode){
        //check that right and left children were swapped

        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();

        firstHeap.insert(root);
        firstHeap.insert(left);
        firstHeap.insert(right);
        firstHeap.insert(newRight);

        Integer leftChild = firstHeap.getRoot().getLeftChild().getKey();
        Integer rightChild = firstHeap.getRoot().getRightChild().getKey();

        firstHeap.insert(lastNode);

        Integer newLeftChild = firstHeap.getRoot().getLeftChild().getKey();
        Integer newRightChild = firstHeap.getRoot().getRightChild().getKey();

        assertEquals(leftChild, newRightChild);
        assertEquals(rightChild, newLeftChild);

    }

    @ParameterizedTest
    @CsvSource({
            "100,120,2147483646,-2147483647",
            "1,2,3,4",
            "0,-12298675,11,98532"
    })
    public void boundaryIntegerInputs(int node1, int node2, int node3, int node4){
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        LeftistHeap<Integer> secondHeap = new LeftistHeap<>();

        Integer max = Integer.MAX_VALUE;
        Integer min = Integer.MIN_VALUE;

        firstHeap.insert(node1);
        firstHeap.insert(node2);
        firstHeap.insert(node3);
        firstHeap.insert(node4);

        secondHeap.insert(node1);
        secondHeap.insert(node2);
        secondHeap.insert(node3);
        secondHeap.insert(node4);

        firstHeap.insert(max);
        secondHeap.insert(min);

        for (int i = 0; i < 4; i++){
            firstHeap.remove();
        }

        assertEquals(max, firstHeap.getRoot().getKey());
        assertEquals(min, secondHeap.getRoot().getKey());
    }

    @Test
    public void removeRootIsNull(){
        //check that method returns null
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();
        assertNull(firstHeap.remove());
    }

    @Test
    public void removeRootNotNull(){
        //check that min child is root now
        //check that method returns key of removed root

        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();

        Integer[] nodes = {16, 20, 22};
        for (Integer node : nodes) {
            firstHeap.insert(node);
        }

        assertEquals(nodes[0], firstHeap.remove());
        assertEquals(nodes[1], firstHeap.getRoot().getKey());

    }

    @Test
    public void clearHeapWithNodes(){
        LeftistHeap<Integer> firstHeap = new LeftistHeap<>();

        Integer root = 0;
        firstHeap.insert(root);
        firstHeap.clear();
        assertNull(firstHeap.getRoot());
    }

}

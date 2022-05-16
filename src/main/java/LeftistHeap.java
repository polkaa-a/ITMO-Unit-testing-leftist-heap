public class LeftistHeap<T extends Comparable<T>> {

    private LeftistNode<T> root;

    public LeftistNode<T> getRoot() {
        return root;
    }

    public class LeftistNode<T extends Comparable<T>> {
        private final T key;
        private int nullPathLength;
        private LeftistNode<T> leftChild;
        private LeftistNode<T> rightChild;

        LeftistNode<T> getLeftChild(){ return leftChild;}
        LeftistNode<T> getRightChild(){ return rightChild;}

        int getNullPathLength(){return nullPathLength;}
        T getKey(){return key;}

        LeftistNode(T key, LeftistNode<T> leftChild, LeftistNode<T> rightChild) {
            this.key = key;
            this.nullPathLength = 0;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }

    public LeftistHeap() {
        root = null;
    }

    private LeftistNode<T> merge(LeftistNode<T> root1, LeftistNode<T> root2) {
        if(root1 == null) return root2;
        if(root2 == null) return root1;

        //check if root1 < root2 -- swap them
        //cause root1 later is a main root
        if(root1.key.compareTo(root2.key) > 0) {
            LeftistNode<T> tmp = root1;
            root1 = root2;
            root2 = tmp;
        }

        root1.rightChild = merge(root1.rightChild, root2);

        //rule of leftist heap
        //swapping right and left parts
        if (root1.leftChild == null || root1.leftChild.nullPathLength < root1.rightChild.nullPathLength) {
            LeftistNode<T> tmp = root1.leftChild;
            root1.leftChild = root1.rightChild;
            root1.rightChild = tmp;
        }

        if (root1.rightChild == null) root1.nullPathLength = 0;
        else root1.nullPathLength =
                (root1.leftChild.nullPathLength > root1.rightChild.nullPathLength)
                ? (root1.rightChild.nullPathLength + 1)
                : (root1.leftChild.nullPathLength + 1);

        return root1;
    }

    public LeftistNode<T> merge(LeftistHeap<T> other) {
        return this.root = merge(this.root, other.root);
    }

    public void insert(T key) {
        LeftistNode<T> node = new LeftistNode<>(key, null, null);
        this.root = merge(this.root, node);
    }

    public T remove() {
        if (this.root == null) return null;

        T key = this.root.key;
        LeftistNode<T> l = this.root.leftChild;
        LeftistNode<T> r = this.root.rightChild;
        this.root = null;
        this.root = merge(l, r);
        return key;
    }

    private void destroy(LeftistNode<T> heap) {
        if (heap == null) return;
        if (heap.leftChild != null) destroy(heap.leftChild);
        if (heap.rightChild != null) destroy(heap.rightChild);
        heap = null;
    }

    public void clear() {
        destroy(root);
        root = null;
    }

}

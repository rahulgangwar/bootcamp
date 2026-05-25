import java.util.ArrayList;

// Min Heap Implementation
public class HeapImplementation {

    public static void main(String[] args) {
        HeapImplementation minHeap = new HeapImplementation();

        minHeap.insert(10);
        minHeap.insert(4);
        minHeap.insert(15);
        minHeap.insert(20);
        minHeap.insert(0);
        minHeap.insert(8);

        minHeap.printHeap(); // Should maintain heap property
    }

    private ArrayList<Integer> heap;

    public HeapImplementation() {
        heap = new ArrayList<>();
    }

    // Get parent index
    private int parent(int index) {
        return (index - 1) / 2;
    }

    // Get left child index
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    // Get right child index
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    // Insert a new element and heapify
    public void insert(int value) {
        heap.add(value); // Add at the end
        heapifyUp(heap.size() - 1); // Heapify from last index
    }

    // Heapify upwards after insertion
    private void heapifyUp(int index) {
        while (index > 0 && heap.get(index) < heap.get(parent(index))) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    // Swap elements at two indices
    private void swap(int i, int j) {
        int tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    // Print the heap
    public void printHeap() {
        System.out.println(heap);
    }

    // Peek at the root element
    public int peek() {
        if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
        return heap.get(0);
    }
}

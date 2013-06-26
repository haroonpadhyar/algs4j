import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Nabeel Ali Memon
 */
public final class Article_1_1 {
  public interface Bag<Item> extends Iterable<Item> {
    void add(Item item);
    boolean isEmpty();
    int size();
  }

  public interface Queue<Item> extends Iterable<Item> {
    void enqueue(Item item);
    Item dequeue();
    boolean isEmpty();
    int size();
  }

  public interface Stack<Item> extends Iterable<Item> {
    void push(Item item);
    Item pop();
    boolean isEmpty();
    int size();
  }

  public static class ResizingArrayStack<Item> implements Stack<Item> {
    Item[] internalStore;
    int currentIndex;
    int modCount;

    public ResizingArrayStack() {
      this(10);
    }

    public ResizingArrayStack(int defaultCapacity) {
      this.internalStore = (Item[]) new Object[defaultCapacity];
      currentIndex = 0;
      modCount = 0;
    }

    void ensureCapacity() {
      if (currentIndex == (internalStore.length - 1)) {
        Item[] expandedStore = (Item[]) new Object[currentIndex * 2];
        for (int i = 0; i < internalStore.length; i++) {
          expandedStore[i] = internalStore[i];
        }
        internalStore = expandedStore;
        modCount++;
      }
    }

    @Override public void push(Item item) {
      Objects.requireNonNull(item);
      ensureCapacity();
      internalStore[currentIndex] = item;
      currentIndex += 1;
      modCount++;
    }

    @Override public Item pop() {
      if (currentIndex == 0) {
        throw new IndexOutOfBoundsException("Nothing to pop");
      }
      currentIndex -= 1;
      Item poppedItem = internalStore[currentIndex];
      internalStore[currentIndex] = null;
      modCount++;
      return poppedItem;
    }

    @Override public boolean isEmpty() {
      return currentIndex == 0;
    }

    @Override public int size() {
      return currentIndex;
    }

    @Override public Iterator<Item> iterator() {
      return new Iterator<Item>(){
        int collectionModCount = modCount;
        int iterationIndex = currentIndex - 1;

        @Override public boolean hasNext() {
          return iterationIndex >= 0;
        }

        @Override public Item next() {
          if (collectionModCount != modCount) {
            throw new ConcurrentModificationException();
          }
          return internalStore[iterationIndex--];
        }

        @Override public void remove() {
          throw new UnsupportedOperationException("not yet implemented");
        }
      };
    }
  }
}

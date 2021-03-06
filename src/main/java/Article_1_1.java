import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Nabeel Ali Memon
 */
public final class Article_1_1 {
  static class LinkedNode<Item> {
    LinkedNode<Item> next;
    LinkedNode<Item> prev;
    Item data;

    LinkedNode() {}

    LinkedNode(LinkedNode<Item> prev, Item data, LinkedNode<Item> next) {
      this.prev = prev;
      this.data = data;
      this.next = next;
    }
  }

  public interface List<Item> extends Iterable<Item> {
    void add(Item item);
    void add(int index, Item item);
    Item remove(int index);
    void remove(Item item);
    Item get(int index);
    boolean isEmpty();
    int size();
  }

  public static class LinkedList<Item> implements List<Item> {
    LinkedNode<Item> head;
    LinkedNode<Item> tail;
    int size;
    int modCount;
    boolean isCircular;

    public LinkedList() {
      this(false);
    }

    public LinkedList(boolean isCircular) {
      this.isCircular = isCircular;
    }

    void addLast(Item item) {
      LinkedNode<Item> newNode = new LinkedNode<>(tail, item, isCircular ? head : null);
      if (tail != null) {
        tail.next = newNode;
      } else {
        head = newNode;
      }
      tail = newNode;
      if (isCircular) {
        tail.next = head;
        head.prev = tail;
      }
      size++;
      modCount++;
    }

    void addBefore(LinkedNode<Item> currentNode, Item item) {
      LinkedNode<Item> newNode = new LinkedNode<>(currentNode.prev, item, currentNode);
      if (currentNode.prev != null) {
        currentNode.prev.next = newNode;
      } else {
        head = newNode;
        if (isCircular) {
          head.prev = tail;
          tail.next = head;
        }
      }
      currentNode.prev = newNode;
      size++;
      modCount++;
    }

    @Override public void add(Item item) {
      addLast(item);
    }

    @Override public void add(int index, Item item) {
      validateAdderIndex(index);
      if (index == size) {
        addLast(item);
      }
      else {
        LinkedNode<Item> currentNode = head;
        for (int i = 0; i < index; i++) {
          currentNode = currentNode.next;
        }
        addBefore(currentNode, item);
      }
    }

    @Override public void remove(Item item) {
      for (LinkedNode<Item> currentNode = head; currentNode != null;)
      while (currentNode != null) {
        LinkedNode<Item> temp = currentNode.next;
        if (currentNode.data.equals(item)) {
          internalRemove(currentNode);
        }
        currentNode = temp;
      }
    }

    @Override public Item remove(int index) {
      LinkedNode<Item> currentNode = internalGet(index);
      return internalRemove(currentNode);
    }

    Item internalRemove(LinkedNode<Item> currentNode) {
      if (currentNode.prev != null) {
        currentNode.prev.next = currentNode.next;
      }
      if (currentNode.next != null) {
        currentNode.next.prev = currentNode.prev;
      }
      Item data = currentNode.data;
      currentNode = null;
      size--;
      modCount--;
      return data;
    }

    @Override public Item get(int index) {
      return internalGet(index).data;
    }

    private LinkedNode<Item> internalGet(int index) {
      validateGetterIndex(index);
      LinkedNode<Item> currentNode = head;
      for (int i = 0; i < index; i++) {
        currentNode = currentNode.next;
      }
      return currentNode;
    }

    void validateGetterIndex(int index) {
      if (index < 0 || index >= size) {
        throw new IllegalArgumentException();
      }
    }

    void validateAdderIndex(int index) {
      if (index < 0 || index > size) {
        throw new IllegalArgumentException();
      }
    }

    @Override public boolean isEmpty() {
      return size == 0;
    }

    @Override public int size() {
      return size;
    }

    @Override public Iterator<Item> iterator() {
      return new Iterator<Item>() {
        int version = modCount;
        LinkedNode<Item> cursor = head;

        @Override public boolean hasNext() {
          return (cursor != null);
        }

        @Override public Item next() {
          if (version != modCount) {
            throw new ConcurrentModificationException();
          }
          Item data = cursor.data;
          cursor = cursor.next;
          return data;
        }

        @Override public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }

  public interface Bag<Item> extends Iterable<Item> {
    void add(Item item);
    boolean isEmpty();
    int size();
  }
  
  public static class LinkedBag<Item> implements Bag<Item>  {
    LinkedNode<Item> head;
    int size = 0;
    int modCount = 0;

    @Override public void add(Item item) {
      LinkedNode<Item> newNode = new LinkedNode<>();
      newNode.data = item;
      newNode.next = head;
      head = newNode;
      size++;
      modCount++;
    }

    @Override public boolean isEmpty() {
      return size <= 0;
    }

    @Override public int size() {
      return size;
    }

    @Override public Iterator<Item> iterator() {
      return new Iterator<Item>() {
        LinkedNode<Item> top = head;
        int iterationIndex = size;
        int version = modCount;
        
        @Override public boolean hasNext() {
          return iterationIndex > 0;
        }

        @Override public Item next() {
          if (version != modCount) {
            throw new ConcurrentModificationException();
          }
          Item value = top.data;
          top = top.next;
          iterationIndex--;
          return value;
        }

        @Override public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }

  public interface Queue<Item> extends Iterable<Item> {
    void enqueue(Item item);
    Item dequeue();
    boolean isEmpty();
    int size();
  }
  
  public static class LinkedQueue<Item> implements Queue<Item> {
    LinkedNode<Item> head;
    LinkedNode<Item> tail;
    int size = 0;
    int modCount = 0;
    
    @Override public void enqueue(Item item) {
      LinkedNode<Item> newTail = new LinkedNode<>();
      newTail.data = item;
      if (isEmpty()) {
        head = newTail;
        tail = newTail; 
      } else { 
        tail.next = newTail; 
      }
      tail = newTail;
      size++;
      modCount++;
    }

    @Override public Item dequeue() {
      Item value = head.data;
      head = head.next;
      size--;
      modCount++;
      return value;
    }

    @Override public boolean isEmpty() {
      return (size <= 0);
    }

    @Override public int size() {
      return size;
    }

    @Override public Iterator<Item> iterator() {
      return new Iterator<Item>() {
        final int version = modCount;
        int iterationIndex = size;
        LinkedNode<Item> top = head;
        
        @Override public boolean hasNext() {
          return iterationIndex > 0;
        }

        @Override public Item next() {
          if (version != modCount) {
            throw new ConcurrentModificationException();
          }
          Item value = top.data;
          top = top.next;
          iterationIndex--;
          return value;
        }

        @Override public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
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
  
  public static class LinkedStack<Item> implements Stack<Item> {
    LinkedNode<Item> head;
    int size;
    int modCount;

    public LinkedStack() {
      size = 0;
      modCount = 0;
    }

    @Override public void push(Item item) {
      LinkedNode<Item> newNode = new LinkedNode<>();
      newNode.data = item;
      newNode.next = head;
      head = newNode;
      size++;
      modCount++;
    }

    @Override public Item pop() {
      Item value = head.data;
      head = head.next;
      size--;
      modCount++;
      return value;
    }

    @Override public boolean isEmpty() {
      return (size <= 0);
    }

    @Override public int size() {
      return size;
    }

    @Override public Iterator<Item> iterator() {
      return new Iterator<Item>() {
        final int version = modCount;
        LinkedNode<Item> top = head;
        int iterationIndex = size;
        
        @Override public boolean hasNext() {
          return iterationIndex > 0;
        }

        @Override public Item next() {
          if (version != modCount) {
            throw new ConcurrentModificationException();
          }
          Item value = top.data;
          top = top.next;
          iterationIndex--;
          return value;
        }

        @Override public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }
  
  public interface Exercise {
    public static class E_1_3_1<Item> implements Stack<Item> {
      Item[] stack;
      int index;
      int modCount;

      public E_1_3_1() {
        this(10); //random default
      }

      public E_1_3_1(int capacity) {
        stack = (Item[]) new Object[capacity];
        index = 0;
        modCount = 0;
      }

      @Override public void push(Item item) {
        if (isFull()) {
          throw new RuntimeException("Stack is full");
        }
        modCount++;
        stack[index++] = item;
      }

      boolean isFull() {
        return index == stack.length;
      }
      
      @Override public Item pop() {
        modCount++;
        int currentIndex = --index;
        Item item = stack[currentIndex];
        stack[currentIndex] = null; //free up memory
        return item;
      }

      @Override public boolean isEmpty() {
        return index == 0;
      }

      @Override public int size() {
        return index;
      }

      @Override public Iterator<Item> iterator() {
        return new Iterator<Item>() {
          int version = modCount;
          int iterIndex = index;
          
          @Override public boolean hasNext() {
            return iterIndex > 0;
          }

          @Override public Item next() {
            if (version != modCount) {
              throw new ConcurrentModificationException();
            }
            return stack[--iterIndex];
          }

          @Override public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    }
  }
}

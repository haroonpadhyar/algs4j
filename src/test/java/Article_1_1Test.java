import org.junit.Assert;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * @author Nabeel Ali Memon
 */
public class Article_1_1Test {
  public static class ResizingArrayStackTest {
    @Test public void shouldPushAndPopElement() {
      Article_1_1.Stack<String> dynamicStack = new Article_1_1.ResizingArrayStack<>(2);
      Assert.assertEquals(0, dynamicStack.size());
      dynamicStack.push("Nabeel");
      Assert.assertEquals(1, dynamicStack.size());
      dynamicStack.push("Memon");
      Assert.assertEquals(2, dynamicStack.size());
      Assert.assertEquals("Memon", dynamicStack.pop());
      Assert.assertEquals(1, dynamicStack.size());
      Assert.assertEquals("Nabeel", dynamicStack.pop());
      Assert.assertEquals(0, dynamicStack.size());
    }

    @Test public void shouldIterateOverStack() {
      Article_1_1.Stack<String> dynamicStack = new Article_1_1.ResizingArrayStack<>(5);
      dynamicStack.push("Engineer");
      dynamicStack.push("Software");
      dynamicStack.push("Memon");
      dynamicStack.push("Ali");
      dynamicStack.push("Nabeel");
      Iterator<String> stackIterator = dynamicStack.iterator();
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Nabeel", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Ali", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Memon", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Software", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Engineer", stackIterator.next());
      Assert.assertFalse(stackIterator.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void shouldThrowCMEIfModifiedDuringIteration() {
      Article_1_1.Stack<String> dynamicStack = new Article_1_1.ResizingArrayStack<>(5);
      dynamicStack.push("Engineer");
      dynamicStack.push("Software");
      Iterator<String> stackIterator = dynamicStack.iterator();
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Software", stackIterator.next());
      dynamicStack.push("Nabeel");
      Assert.assertTrue(stackIterator.hasNext());
      stackIterator.next();
    }
  }
  
  public static class LinkedStackTest {
    @Test public void shouldPushAndPopElement() {
      Article_1_1.Stack<String> linkedStack = new Article_1_1.LinkedStack<>();
      Assert.assertEquals(0, linkedStack.size());
      linkedStack.push("Nabeel");
      Assert.assertEquals(1, linkedStack.size());
      linkedStack.push("Memon");
      Assert.assertEquals(2, linkedStack.size());
      Assert.assertEquals("Memon", linkedStack.pop());
      Assert.assertEquals(1, linkedStack.size());
      Assert.assertEquals("Nabeel", linkedStack.pop());
      Assert.assertEquals(0, linkedStack.size());
    }

    @Test public void shouldIterateOverStack() {
      Article_1_1.Stack<String> linkedStack = new Article_1_1.LinkedStack<>();
      linkedStack.push("Engineer");
      linkedStack.push("Software");
      linkedStack.push("Memon");
      linkedStack.push("Ali");
      linkedStack.push("Nabeel");
      Iterator<String> stackIterator = linkedStack.iterator();
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Nabeel", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Ali", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Memon", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Software", stackIterator.next());
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Engineer", stackIterator.next());
      Assert.assertFalse(stackIterator.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void shouldThrowCMEIfModifiedDuringIteration() {
      Article_1_1.Stack<String> linkedStack = new Article_1_1.LinkedStack<>();
      linkedStack.push("Engineer");
      linkedStack.push("Software");
      Iterator<String> stackIterator = linkedStack.iterator();
      Assert.assertTrue(stackIterator.hasNext());
      Assert.assertEquals("Software", stackIterator.next());
      linkedStack.push("Nabeel");
      Assert.assertTrue(stackIterator.hasNext());
      stackIterator.next();
    }
  }
  
  public static class LinkedQueueTest {
    @Test public void shouldPushAndPopElement() {
      Article_1_1.Queue<String> linkedQueue = new Article_1_1.LinkedQueue<>();
      Assert.assertEquals(0, linkedQueue.size());
      linkedQueue.enqueue("Nabeel");
      Assert.assertEquals(1, linkedQueue.size());
      linkedQueue.enqueue("Memon");
      Assert.assertEquals(2, linkedQueue.size());
      Assert.assertEquals("Nabeel", linkedQueue.dequeue());
      Assert.assertEquals(1, linkedQueue.size());
      Assert.assertEquals("Memon", linkedQueue.dequeue());
      Assert.assertEquals(0, linkedQueue.size());
    }

    @Test public void shouldIterateOverStack() {
      Article_1_1.Queue<String> linkedQueue = new Article_1_1.LinkedQueue<>();
      linkedQueue.enqueue("Nabeel");
      linkedQueue.enqueue("Ali");
      linkedQueue.enqueue("Memon");
      linkedQueue.enqueue("Software");
      linkedQueue.enqueue("Engineer");
      Iterator<String> queueIterator = linkedQueue.iterator();
      Assert.assertTrue(queueIterator.hasNext());
      Assert.assertEquals("Nabeel", queueIterator.next());
      Assert.assertTrue(queueIterator.hasNext());
      Assert.assertEquals("Ali", queueIterator.next());
      Assert.assertTrue(queueIterator.hasNext());
      Assert.assertEquals("Memon", queueIterator.next());
      Assert.assertTrue(queueIterator.hasNext());
      Assert.assertEquals("Software", queueIterator.next());
      Assert.assertTrue(queueIterator.hasNext());
      Assert.assertEquals("Engineer", queueIterator.next());
      Assert.assertFalse(queueIterator.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void shouldThrowCMEIfModifiedDuringIteration() {
      Article_1_1.Queue<String> linkedQueue = new Article_1_1.LinkedQueue<>();
      linkedQueue.enqueue("Software");
      linkedQueue.enqueue("Engineer");
      Iterator<String> queueIterator = linkedQueue.iterator();
      Assert.assertTrue(queueIterator.hasNext());
      Assert.assertEquals("Software", queueIterator.next());
      linkedQueue.enqueue("Nabeel");
      Assert.assertTrue(queueIterator.hasNext());
      queueIterator.next();
    }
  }
}

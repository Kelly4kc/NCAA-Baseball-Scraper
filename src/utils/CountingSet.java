package utils;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A set that counts the number of times each element is added to it.
 *
 * @param <K> the key type
 * @author Kevin Kelly
 */
public class CountingSet<K> extends HashSet<K> {

  private HashMap<K, Integer> counts;

  /**
   * Initializes a new counting set.
   */
  public CountingSet() {
    super();
    counts = new HashMap<>();
  }

  /**
   * Adds the specified element to this counting set. Always returns true.
   *
   * @param k the element to add
   * @return true
   */
  @Override
  public boolean add(K k) {
    if (counts.containsKey(k)) {
      counts.put(k, counts.get(k) + 1);
    } else {
      counts.put(k, 1);
    }
    super.add(k);
    return true;
  }

  /**
   * Gets the number this element in this set.
   *
   * @param k the element
   * @return the number of this element in this set
   */
  public Integer getCount(K k) {
    return counts.get(k);
  }

  /**
   * Puts a specific count for an element.
   *
   * @param k the element
   * @param i the count of the element
   * @return the previous count of the element or null if there was no value previously
   */
  public Integer putCount(K k, Integer i) {
    return counts.put(k, i);
  }
}

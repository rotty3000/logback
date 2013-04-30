package ch.qos.logback.core.joran.spi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ceki
 * Date: 29.04.13
 * Time: 23:19
 * To change this template use File | Settings | File Templates.
 */
public class ElementPath {
  // contains String instances
  ArrayList<String> partList = new ArrayList<String>();

  public ElementPath() {
  }

  public ElementPath(List<String> list) {
    partList.addAll(list);
  }

  /**
   * Build an elementPath from a string.
   * <p/>
   * Note that "/x" is considered equivalent to "x" and to "x/"
   */
  public ElementPath(String p) {
    this();

    if (p == null) {
      return;
    }

    int lastIndex = 0;

    while (true) {
      int k = p.indexOf('/', lastIndex);
      if (k == -1) {
        String lastPart = p.substring(lastIndex);
        if (lastPart != null && lastPart.length() > 0) {
          partList.add(p.substring(lastIndex));
        }
        break;
      } else {
        String c = p.substring(lastIndex, k);
        if (c.length() > 0) {
          partList.add(c);
        }

        lastIndex = k + 1;
      }
    }
  }

  public Object clone() {
    ElementPath p = new ElementPath();
    p.partList.addAll(this.partList);
    return p;
  }

  // Joran error skipping relies on the equals method
  @Override
  public boolean equals(Object o) {
    if ((o == null) || !(o instanceof ElementPath)) {
      return false;
    }

    ElementPath r = (ElementPath) o;

    if (r.size() != size()) {
      return false;
    }

    int len = size();

    for (int i = 0; i < len; i++) {
      if (!equalityCheck(get(i), r.get(i))) {
        return false;
      }
    }

    // if everything matches, then the two patterns are equal
    return true;
  }

  private boolean equalityCheck(String x, String y) {
    return x.equalsIgnoreCase(y);
  }

  public List<String> getCopyOfPartList() {
    return new ArrayList<String>(partList);
  }

  public void push(String s) {
    partList.add(s);
  }

  public String get(int i) {
    return (String) partList.get(i);
  }

  public void pop() {
    if (!partList.isEmpty()) {
      partList.remove(partList.size() - 1);
    }
  }

  public String peekLast() {
    if (!partList.isEmpty()) {
      int size = partList.size();
      return (String) partList.get(size - 1);
    } else {
      return null;
    }
  }

  public int size() {
    return partList.size();
  }


  protected String toStableString() {
    StringBuilder result = new StringBuilder();
    for (String current : partList) {
      result.append("[").append(current).append("]");
    }
    return result.toString();
  }

  @Override
  public String toString() {
    return toStableString();
  }
}
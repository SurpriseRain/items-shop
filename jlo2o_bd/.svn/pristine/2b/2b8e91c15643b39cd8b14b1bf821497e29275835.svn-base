package com.jlsoft.framework.pi.util.orderedmap;

import com.jlsoft.framework.pi.util.simplerecord.ISimpleRecord;
import java.util.List;
import java.util.Map;

public interface IOrderedMap extends Map, ISimpleRecord {
    // BEGIN chosen methods from List interface
    // public int size();
    // public boolean isEmpty();
    // public boolean contains(Object object) ;
    // public Iterator iterator();
    // public Object[] toArray() ;
    // public Object[] toArray(Object[] objects) ;

    /**
     * Inserts the specified mapping (key->value) at the specified position in
     * this list. <p> Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param key the key to be associated with the element.
     * @param value element to be inserted.
     * @throws IndexOutOfBoundsException if index out of range (index < 0 ||
     * index >= size()).
     */
    public void add(int index, Object key, Object value);

    /**
     * Inserts the specified element at the specified position in this list,
     * having associated it with an auto-generated key. <p> Shifts the element
     * currently at that position (if any) and any subsequent elements to the
     * right (adds one to their indices). Other than for key generation, this
     * should have identical behaviour to java.util.List.add(int i,Object
     * object)
     *
     * @param index index at which the specified element is to be inserted.
     * @param object element to be inserted.
     * @see java.util.List
     */
    public void add(int index, Object object);

    /**
     * Add an object, associating it with an auto-generated key in the process.
     * <p> To keep it
     * <code>List</code>-like, it returns a
     * <code>boolean</code>. A viable alternative might be to return the
     * auto-generated key.
     *
     * @param object the object to add.
     * @return true if the add succeeded, false otherwise.
     */
    public boolean add(Object object); // ToDo: Discuss option of cheating and returning key.

    // Clashes with Map!
    // public boolean remove(Object object) ;
    // public boolean containsAll(Collection collection);
    // public boolean addAll(Collection collection);
    // public boolean addAll(int i, Collection collection);
    // public boolean removeAll(Collection collection) ;
    // public boolean retainAll(Collection collection) ;
    // public void clear();
    /**
     * Return a shallow copy of this implementation of IOrderedMap. <p> The copy
     * maintains the correct key order. Keys and Values are not copied. The
     * clone() method is provided IOrderedMap interface to force implementors to
     * implement it correctly. <p> The return type is Object to avoid potential
     * conflicts with superclass implementations of IOrderedMap
     * implemenatations.
     *
     * @return Object containing a clone of the current ordered map.
     */
    public Object clone();

    /**
     * Returns the element at the specified position in this map. <p> This
     * should have similar behaviour to java.util.List.get(int i);
     *
     * @param index the index of the required value.
     * @return the value stored at the index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0
     * || index >= size())
     * @see java.util.List
     */
    public Object get(int index);

    /**
     * Returns the index in this list of the specified element, or -1 if this
     * list does not contain this element.
     *
     * @param object element to search for.
     * @return the index in this list of the occurrence of the specified
     * element, or -1 if this list does not contain this element.
     */
    public int indexOf(Object object);

    /**
     * Get an ordered
     * <code>List</code> of the keys. <p> Differs from keySet() in that it
     * guarantees that the ordering of the keys in the list matches their order
     * in the
     * <code>IOrderedMap</code>
     *
     * @return Ordered List containing the keys for the values in the map.
     */
    public List keys();

    /**
     * Replaces the element at the specified position in this list with the
     * specified element. <p> This should have similar behaviour to
     * java.util.List.set(int i,Object object)
     *
     * @param index the index of the element to replace.
     * @param object the element to be stored at the specified position
     * @return the value previously stored at the specified position
     * @see java.util.List
     */
    public Object set(int index, Object object);

    /**
     * Removes the element at the specified position in this list. <p> Shifts
     * any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list. This should have
     * similar behaviour to java.util.List.remove(int)
     *
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     * @see java.util.List
     */
    public Object remove(int index);
    // public int lastIndexOf(Object object);
    // public ListIterator listIterator() ;
    // public ListIterator listIterator(int i) ;
    // public List subList(int i, int i1);
    // END chosen methods from List interface
}

package user11681.usersmanual.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import user11681.usersmanual.util.Stringified;

public class ArraySet<E> implements Set<E>, List<E>, Stringified {
    E[] elements;
    int length;
    int size;

    public ArraySet() {
        this(10);
    }

    public ArraySet(final int initialLength) {
        this.elements = ArrayUtil.create(initialLength);
        this.length = initialLength;
    }

    public ArraySet(final int length, final Collection<E> from) {
        this.elements = ArrayUtil.create(length);
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(from.toArray(), 0, elements, 0, length);
        this.size = this.length = length;
    }

    @SafeVarargs
    public ArraySet(final int length, final E... from) {
        this.elements = Arrays.copyOf(from, length);
        this.size = this.length = length;
    }

    @Override
    public boolean addAll(@Nonnull final Collection<? extends E> collection) {
        return this.addAll(this.size, collection);
    }

    @Override
    public boolean addAll(final int index, @Nonnull final Collection<? extends E> collection) {
        boolean changed = false;

        while (this.size + collection.size() >= this.length) {
            this.expand();
        }

        for (final E element : collection) {
            int elementIndex = this.indexOf(element);

            if (elementIndex < 0) {
                elementIndex = -elementIndex - 1;
                final int oldSize = this.size++;

                if (elementIndex < oldSize) {
                    System.arraycopy(this.elements, elementIndex, this.elements, elementIndex + 1, oldSize);
                }

                this.elements[elementIndex] = element;

                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean add(final E element) {
        final boolean contains = this.contains(element);

        this.add(this.size, element);

        return !contains;
    }

    @Override
    public void add(final int index, final E element) {
        int size = this.size;

        if (index > size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        if (!this.contains(element)) {
            E[] elements = this.elements;

            if (index < size) {
                System.arraycopy(elements, index, elements, index + 1, size - index);
            } else if (size == this.length) {
                this.expand();
                elements = this.elements;
            }

            elements[index] = element;

            ++this.size;
        }
    }

    public void trimToSize() {
        final int size = this.size;

        this.resize(size);
        this.length = size;
    }

    public void expand() {
        final int length = this.size * 2;

        this.elements = Arrays.copyOf(this.elements, length);
        this.length = length;
    }

    public void resize(final int length) {
        this.elements = Arrays.copyOf(this.elements, length);
        this.length = length;
    }

    @Override
    public E get(final int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        return this.elements[index];
    }

    @Override
    public E set(final int index, final E element) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        final E[] elements = this.elements;
        final E previous = elements[index];

        elements[index] = element;

        return previous;
    }

    @Override
    public E remove(final int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        final E[] elements = this.elements;
        final E previous = elements[index];
        final int start = index + 1;

        System.arraycopy(elements, start, elements, index, this.size - start);

        elements[--this.size] = null;

        return previous;
    }

    @Override
    public int indexOf(final Object object) {
        final E[] elements = this.elements;
        final int size = this.size;

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(object)) {
                return i;
            }
        }

        return -size - 1;
    }

    @Override
    public int lastIndexOf(final Object object) {
        final E[] elements = this.elements;
        final int size = this.size;
        int index = -size - 1;

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(object)) {
                index = i;
            }
        }

        return index;
    }

    @Override
    @Nonnull
    public ListIterator<E> listIterator() {
        return new ArraySetListIterator();
    }

    @Override
    @Nonnull
    public ListIterator<E> listIterator(final int index) {
        return new ArraySetListIterator(index);
    }

    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return new ArraySetIterator();
    }

    @Override
    @Nonnull
    public List<E> subList(final int from, final int to) {
        return new ArraySet<>(to - from, Arrays.copyOfRange(this.elements, from, to));
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(final Object object) {
        //noinspection ListIndexOfReplaceableByContains
        return this.indexOf(object) > -1;
    }

    @Nonnull
    public E[] toArray() {
        return Arrays.copyOf(this.elements, this.size);
    }

    @Override
    @Nonnull
    public <T> T[] toArray(@Nonnull final T[] array) {
        final int size = this.size;

        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(this.elements, size, array.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(this.elements, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public boolean remove(final Object object) {
        final int index = this.indexOf(object);

        if (index > -1) {
            this.remove(index);

            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(@Nonnull final Collection<?> collection) {
        for (final Object object : collection) {
            if (!this.contains(object)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean retainAll(@Nonnull final Collection<?> collection) {
        boolean changed = false;

        for (final E element : this.elements) {
            if (!collection.contains(element)) {
                this.remove(element);

                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean removeAll(@Nonnull final Collection<?> collection) {
        boolean changed = false;

        for (final E element : this.elements) {
            if (collection.contains(element)) {
                this.remove(element);

                changed = true;
            }
        }

        return changed;
    }

    @Override
    public void clear() {
        this.elements = ArrayUtil.create(this.length);
        this.size = 0;
    }

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }

    @Override
    public String asString() {
        final StringBuilder string = new StringBuilder("{");

        final E[] elements = this.elements;

        for (int i = 0, size = this.size; i < size; i++) {
            string.append(elements[i]);

            if (i < size - 1) {
                string.append(", ");
            }
        }

        return string.append('}').toString();
    }

    public class ArraySetIterator implements Iterator<E> {
        int index;
        int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return this.index < ArraySet.this.size;
        }

        @Override
        public E next() {
            final int index = this.index;

            if (index >= ArraySet.this.size) {
                throw new NoSuchElementException();
            }

            if (index >= ArraySet.this.length) {
                throw new ConcurrentModificationException();
            }

            return ArraySet.this.elements[this.lastReturned = this.index++];
        }

        @Override
        public void remove() {
            int lastReturned = this.lastReturned;

            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            try {
                ArraySet.this.remove(lastReturned);
                index = lastReturned;
                this.lastReturned = -1;
            } catch (final IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(@Nonnull final Consumer<? super E> action) {
            Objects.requireNonNull(action);

            final int size = ArraySet.this.size;
            int index = this.index;

            if (index < size) {
                if (index >= ArraySet.this.length) {
                    throw new ConcurrentModificationException();
                }

                while (index != size) {
                    action.accept(ArraySet.this.elements[index++]);
                }

                this.index = index;
                this.lastReturned = index - 1;
            }
        }
    }

    public class ArraySetListIterator extends ArraySetIterator implements ListIterator<E> {
        public ArraySetListIterator() {}

        public ArraySetListIterator(final int index) {
            this.index = index;
        }

        @Override
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override
        public E previous() {
            final int previous = this.index - 1;

            if (previous < 0) {
                throw new NoSuchElementException();
            }

            if (previous >= ArraySet.this.length) {
                throw new ConcurrentModificationException();
            }

            this.index = previous;

            return ArraySet.this.elements[this.lastReturned = previous];
        }

        @Override
        public int nextIndex() {
            return this.index;
        }

        @Override
        public int previousIndex() {
            return this.index - 1;
        }

        @Override
        public void set(final E element) {
            final int lastReturned = this.lastReturned;

            if (lastReturned < 0) {
                throw new NoSuchElementException();
            }

            try {
                ArraySet.this.set(lastReturned, element);
            } catch (final IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(final E element) {
            final int index = this.index;

            try {
                ArraySet.this.add(index, element);
                ++this.index;
                this.lastReturned = -1;
            } catch (final IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

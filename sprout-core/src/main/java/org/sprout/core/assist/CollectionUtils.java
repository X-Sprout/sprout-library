/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合工具类
 * <p/>
 *
 * @author Wythe
 */
public final class CollectionUtils {

    /**
     * 集合转换接口
     * <p/>
     *
     * @author Wythe
     */
    public interface Transformer<T> {
        T transform(final Object object);
    }

    /**
     * 判断是否空映射器
     * <p/>
     *
     * @param map 映射器
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断是否空迭代器
     * <p/>
     *
     * @param iterator 迭代器
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final Iterator iterator) {
        return iterator == null || iterator.hasNext();
    }

    /**
     * 判断是否空集合
     * <p/>
     *
     * @param collection 集合
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断是否存在空映射器
     * <p/>
     *
     * @param maps 映射器（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final Map... maps) {
        if (ArrayUtils.isEmpty(maps)) {
            return true;
        }
        for (final Map map : maps) {
            if (CollectionUtils.isEmpty(map)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在空迭代器
     * <p/>
     *
     * @param iterators 迭代器（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final Iterator... iterators) {
        if (ArrayUtils.isEmpty(iterators)) {
            return true;
        }
        for (final Iterator iterator : iterators) {
            if (CollectionUtils.isEmpty(iterator)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在空集合
     * <p/>
     *
     * @param collections 集合（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final Collection... collections) {
        if (ArrayUtils.isEmpty(collections)) {
            return true;
        }
        for (final Collection collection : collections) {
            if (CollectionUtils.isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较两个集合是否相同
     * <p/>
     *
     * @param collection1 集合1
     * @param collection2 集合2
     * @return 是否相同
     * @author Wythe
     */
    @SuppressWarnings("unchecked")
    public static boolean compare(final Collection collection1, final Collection collection2) {
        if (collection1 == null && collection2 == null) {
            return true;
        }
        if (collection1 == null || collection2 == null) {
            return false;
        }
        if (collection1.size() != collection2.size()) {
            return false;
        }
        final CardinalityHelper<Object> cHelper = new CardinalityHelper<>(collection1, collection2);
        if (cHelper.cardinalityA.size() != cHelper.cardinalityB.size()) {
            return false;
        }
        for (final Object obj : cHelper.cardinalityA.keySet()) {
            if (cHelper.freqA(obj) != cHelper.freqB(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断集合1是否包含集合2
     * <p/>
     *
     * @param collection1 集合1
     * @param collection2 集合2
     * @return 是否包含
     * @author Wythe
     */
    public static boolean containsAll(final Collection collection1, final Collection collection2) {
        if (collection1 == null && collection2 == null) {
            return true;
        }
        if (collection2 == null) {
            return true;
        }
        if (collection1 == null) {
            return false;
        }
        final Set<Object> hs = new HashSet<>();
        final Iterator<?> it = collection1.iterator();
        for (final Object ne : collection2) {
            if (hs.contains(ne)) {
                continue;
            }
            Object po;
            boolean found = false;
            while (it.hasNext()) {
                hs.add(po = it.next());
                if (ne == null ? po == null : ne.equals(po)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断集合1与集合2是否包含相同元素
     * <p/>
     *
     * @param collection1 集合1
     * @param collection2 集合2
     * @return 是否包含
     * @author Wythe
     */
    public static boolean containsAny(final Collection collection1, final Collection collection2) {
        if (collection1 == null && collection2 == null) {
            return true;
        }
        if (collection1 == null || collection2 == null) {
            return false;
        }
        if (collection1.size() < collection2.size()) {
            for (final Object obj : collection1) {
                if (collection2.contains(obj)) {
                    return true;
                }
            }
        } else {
            for (final Object obj : collection2) {
                if (collection1.contains(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 集合数据类型转换
     * <p/>
     *
     * @param collection  转换集合
     * @param transformer 转换工具
     * @return 期望结果列表
     * @author Wythe
     */
    public static <T> List<T> transform(final Collection collection, final Transformer<T> transformer) {
        if (collection != null) {
            final List<T> result = new ArrayList<>();
            for (final Object object : collection) {
                result.add(object != null ? transformer.transform(object) : null);
            }
            return result;
        }
        return null;
    }

    /**
     * 获得集合对象基数映射
     * <p/>
     *
     * @param iterable 集合
     * @param <O>      类型
     * @return 基数映射
     * @author Wythe
     */
    public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> iterable) {
        final Map<O, Integer> map = new HashMap<>();
        for (final O o : iterable) {
            final Integer c = map.get(o);
            if (c == null) {
                map.put(o, 1);
            } else {
                map.put(o, c.intValue() + 1);
            }
        }
        return map;
    }

    /**
     * 集合基数辅助类
     * <p/>
     *
     * @param <O> 类型
     * @author Wythe
     */
    private final static class CardinalityHelper<O> {
        final Map<O, Integer> cardinalityA;
        final Map<O, Integer> cardinalityB;

        private CardinalityHelper(final Iterable<? extends O> a, final Iterable<? extends O> b) {
            this.cardinalityA = CollectionUtils.getCardinalityMap(a);
            this.cardinalityB = CollectionUtils.getCardinalityMap(b);
        }

        private int max(final Object obj) {
            return Math.max(freqA(obj), freqB(obj));
        }

        private int min(final Object obj) {
            return Math.min(freqA(obj), freqB(obj));
        }

        private int freqA(final Object obj) {
            return getFreq(obj, this.cardinalityA);
        }

        private int freqB(final Object obj) {
            return getFreq(obj, this.cardinalityB);
        }

        private int getFreq(final Object obj, final Map<?, Integer> freqMap) {
            final Integer count = freqMap.get(obj);
            return count != null ? count.intValue() : 0;
        }
    }

}
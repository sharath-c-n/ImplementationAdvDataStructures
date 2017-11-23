package cs6301.g26;

import java.util.*;

/**
 * @author Sharath Chalya Nagaraju, Ankitha Karunakar Shetty
 */
public class MDS {
    /**
     * Each entry in this table : (ItemId,(Description,(SupplierId,ItemPrice)))
     * This table holds an entry for each item as key
     * and its value holds the item description,SupplierPrice Map
     **/
    private Map<Long, Item> itemTable;

    /**
     * Each entry in this table : (SupplierId,Reputation)
     * Has SupplierId as key and a supplier's reputation as value
     **/
    private Map<Long, Float> supplierTable;

    /**
     * Each entry in this table : (Description,{Items})
     */
    private Map<Long, Set<Long>> descriptionMap;


    public MDS() {
        itemTable = new HashMap<>();
        supplierTable = new HashMap<>();
        descriptionMap = new HashMap<>();
    }

    public static class Pair {
        long id;
        int price;

        public Pair(long id, int price) {
            this.id = id;
            this.price = price;
        }

        public int hashCode() {
            return Long.hashCode(id);
        }

        public boolean equals(Object o) {
            Pair otherPair = (Pair) o;
            return o != null && otherPair.id == id;
        }
    }

    public static class Item {
        Set<Long> description;
        Map<Long, Integer> supplierMap;

        public Item() {
            this.description = new HashSet<>();
            this.supplierMap = new HashMap<>();
        }
    }

    /* add a new item.  If an entry with the same id already exists,
       the new description is merged with the existing description of
       the item.  Returns true if the item is new, and false otherwise.
    */
    public boolean add(Long id, Long[] description) {
        boolean isNewEntry = false;
        Item item = itemTable.get(id);
        if (item == null) {
            item = new Item();
            itemTable.put(id, item);
            isNewEntry = true;
        }
        item.description.addAll(Arrays.asList(description));
        for (long desc : description) {
            Set<Long> descSet = descriptionMap.computeIfAbsent(desc, k -> new HashSet<>());
            descSet.add(id);
        }
        return isNewEntry;
    }

    /* add a new supplier (Long) and their reputation (float in
       [0.0-5.0], single decimal place). If the supplier exists, their
       reputation is replaced by the new value.  Return true if the
       supplier is new, and false otherwise.
    */
    public boolean add(Long supplier, float reputation) {
        return supplierTable.put(supplier, reputation) == null;
    }

    /* add products and their prices at which the supplier sells the
      product.  If there is an entry for the price of an id by the
      same supplier, then the price is replaced by the new price.
      Returns the number of new entries created.
    */
    public int add(Long supplier, Pair[] idPrice) {
        //Check to see if supplier is present or not
        if (!supplierTable.containsKey(supplier)) {
            return 0;
        }
        int newEntries = 0;
        for (Pair pair : idPrice) {
            Item item = itemTable.get(pair.id);
            if (item != null) {
                //new item
                if (item.supplierMap.put(supplier, pair.price) == null)
                    newEntries++;
            }
        }
        return newEntries;
    }

    /* return an array with the description of id.  Return null if
      there is no item with this id.
    */
    public Long[] description(Long id) {
        Item item = itemTable.get(id);
        return item != null ? objToLong(item.description) : null;
    }

    /* given an array of Longs, return an array of items whose
      description contains one or more elements of the array, sorted
      by the number of elements of the array that are in the item's
      description (non-increasing order).
    */
    public Long[] findItem(Long[] arr) {
        Map<Long, Integer> matchedItems = new HashMap<>();
        for (Long description : arr) {
            Set<Long> items = descriptionMap.get(description);
            if (items != null) {
                for (Long item : items) {
                    matchedItems.put(item, matchedItems.getOrDefault(item, 0) + 1);
                }
            }
        }
        if (matchedItems.size() == 0) {
            return null;
        }

        PriorityQueue<Map.Entry<Long, Integer>> pq = new PriorityQueue<>((x, y) -> y.getValue() - x.getValue());

        // insert in the queue
        for (Map.Entry<Long, Integer> entry : matchedItems.entrySet()) {
            pq.offer(entry);
        }

        //add and return adjItr to a long array
        Long[] list = new Long[matchedItems.size()];
        int i = 0;
        while (!pq.isEmpty()) {
            list[i++] = pq.poll().getKey();
        }
        return list;
    }

    /* given a Long n, return an array of items whose description
      contains n, which have one or more suppliers whose reputation
      meets or exceeds the given minimum reputation, that sell that
      item at a price that falls within the price range [minPrice,
      maxPrice] given.  Items should be sorted in order of their
      minimum price charged by a supplier for that item
      (non-decreasing order).
    */
    public Long[] findItem(Long n, int minPrice, int maxPrice, float minReputation) {
        Map<Long, Integer> itemPrice = new HashMap<>();
        Set<Long> items = descriptionMap.get(n);
        Queue<Map.Entry<Long, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (long item : items) {
            for (Map.Entry<Long, Integer> entry : itemTable.get(item).supplierMap.entrySet()) {
                int price = entry.getValue();
                if (supplierTable.get(entry.getKey()) >= minReputation &&
                        price >= minPrice && price <= maxPrice) {
                    if (itemPrice.getOrDefault(item, Integer.MAX_VALUE) > price) {
                        itemPrice.put(item, price);
                    }
                }
            }
        }
        if (itemPrice.size() == 0) {
            return null;
        }
        // insert in the queue
        for (Map.Entry<Long, Integer> entry : itemPrice.entrySet()) {
            pq.offer(entry);
        }

        return qToArray(pq);
    }

    /* given an id, return an array of suppliers who sell that item,
      ordered by the price at which they sell the item (non-decreasing order).
    */
    public Long[] findSupplier(Long id) {
        Item item = itemTable.get(id);
        if (item == null) {
            System.out.println("Item with Id : " + id + " not found");
            return null;
        }
        Queue<Map.Entry<Long, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        pq.addAll(item.supplierMap.entrySet());
        return qToArray(pq);
    }

    /* given an id and a minimum reputation, return an array of
      suppliers who sell that item, whose reputation meets or exceeds
      the given reputation.  The array should be ordered by the price
      at which they sell the item (non-decreasing order).
    */
    public Long[] findSupplier(Long id, float minReputation) {
        Item item = itemTable.get(id);
        if (item == null) {
            System.out.println("Item with Id : " + id + " not found");
            return null;
        }
        Queue<Map.Entry<Long, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<Long, Integer> entry : item.supplierMap.entrySet()) {
            if (supplierTable.get(entry.getKey()) >= minReputation) {
                pq.add(entry);
            }
        }
        return qToArray(pq);
    }

    /* find suppliers selling 5 or more products, who have the same
       identical profile as another supplier: same reputation, and,
       sell the same set of products, at identical prices.  This is a
       rare operation, so do not do additional work in the other
       operations so that this operation is fast.  Creative solutions
       that are elegant and efficient will be awarded excellence credit.
       Return array of suppliers satisfying above condition.  Make sure
       that each supplier appears only once in the returned array.
    */
    public Long[] identical() {
        HashMap<Long, Map<Long, Integer>> supplierSet = new HashMap<>();
        Set<Long> identicalSuppliers = new HashSet<>();
        for (Map.Entry<Long, Item> item : itemTable.entrySet()) {
            for (Map.Entry<Long, Integer> supplier : item.getValue().supplierMap.entrySet()) {
                Map<Long, Integer> s = supplierSet.computeIfAbsent(supplier.getKey(), k -> new HashMap<>());
                s.put(item.getKey(), supplier.getValue());
            }
        }

        supplierSet.entrySet().removeIf(longMapEntry -> longMapEntry.getValue().size() < 1);

        for (Map.Entry<Long, Map<Long, Integer>> supplier : supplierSet.entrySet()) {
            for (Map.Entry<Long, Map<Long, Integer>> otherSupplier : supplierSet.entrySet()) {
                if (otherSupplier != supplier && areIdentical(supplier, otherSupplier)) {
                    identicalSuppliers.add(supplier.getKey());
                    identicalSuppliers.add(otherSupplier.getKey());
                }
            }
        }

        return identicalSuppliers.isEmpty() ? null : objToLong(identicalSuppliers);
    }

    private boolean areIdentical(Map.Entry<Long, Map<Long, Integer>> supplier1,
                                 Map.Entry<Long, Map<Long, Integer>> supplier2) {
        Map<Long, Integer> valueMap1 = supplier1.getValue();
        Map<Long, Integer> valueMap2 = supplier2.getValue();
        if (valueMap1.size() != valueMap2.size() ||
                supplierTable.get(supplier1.getKey()) != (float) supplierTable.get(supplier2.getKey())) {
            return false;
        }
        for (Map.Entry<Long, Integer> p : valueMap2.entrySet()) {
            Integer price = valueMap1.get(p.getKey());
            if (price == null || (int) price != p.getValue()) {
                return false;
            }
        }
        return true;
    }

    /* given an array of ids, find the total price of those items, if
       those items were purchased at the lowest prices, but only from
       sellers meeting or exceeding the given minimum reputation.
       Each item can be purchased from a different seller.
    */
    public int invoice(Long[] arr, float minReputation) {
        int total = 0;
        for (Long item : arr) {
            total += findLeastPrice(item, minReputation);
        }
        return total;
    }

    private int findLeastPrice(Long item, float minReputation) {
        Item itemObj = itemTable.get(item);
        Integer leastPrice = null;
        if (itemObj != null) {
            for (Map.Entry<Long, Integer> entry : itemObj.supplierMap.entrySet()) {
                if (supplierTable.get(entry.getKey()) >= minReputation && (leastPrice == null || (int) leastPrice > entry.getValue())) {
                    leastPrice = entry.getValue();
                }
            }
        }
        if (leastPrice == null) {
            System.out.println("Invoice: Skipping id " + item + ": not available from seller with at least " +
                    minReputation + " reputation");
            leastPrice = 0;
        }
        return leastPrice;
    }

    /* remove all items, all of whose suppliers have a reputation that
       is equal or lower than the given maximum reputation.  Returns
       an array with the items removed.
    */
    public Long[] purge(float maxReputation) {
        boolean shouldRemove;
        ArrayList<Long> removedItems = new ArrayList<>();
        for (Map.Entry<Long, Item> item : itemTable.entrySet()) {
            shouldRemove = true;
            for (Long seller : item.getValue().supplierMap.keySet()) {
                if (supplierTable.get(seller) > maxReputation) {
                    shouldRemove = false;
                    break;
                }
            }
            if (shouldRemove) {
                removedItems.add(item.getKey());
            }
        }
        for(Long i : removedItems){
            itemTable.remove(i);
        }
        return objToLong(removedItems);
    }

    /* remove item from storage.  Returns the sum of the Longs that
       are in the description of the item deleted (or 0, if such an id
       did not exist).
    */
    public Long remove(Long id) {
        Long sum = 0L;
        Item item = itemTable.remove(id);
        if (item != null) {
            for (long description : item.description) {
                sum += description;
                descriptionMap.get(description).remove(id);
            }
        }
        return sum;
    }

    /* remove from the given id's description those elements that are
       in the given array.  It is possible that some elements of the
       array are not part of the item's description.  Return the
       number of elements that were actually removed from the description.
    */
    public int remove(Long id, Long[] arr) {
        int count = 0;
        Item item = itemTable.get(id);
        if (item != null) {
            for (Long description : arr) {
                if (item.description.remove(description)) {
                    count++;
                    descriptionMap.get(description).remove(id);
                    //can remove description from descriptionMap if set becomes empty
                }
            }
            /*if (item.description.isEmpty()) {
                itemTable.remove(id);
            }*/
        }
        return count;
    }

    /* remove the elements of the array from the description of all
       items.  Return the number of items that lost one or more terms
       from their descriptions.
    */
    public int removeAll(Long[] arr) {
        int count = 0;
        for (long item : itemTable.keySet()) {
            if (remove(item, arr) > 0)
                count++;
        }
        return count;
    }

    private Long[] qToArray(Queue<Map.Entry<Long, Integer>> pq) {
        Long[] list = new Long[pq.size()];
        for (int i = 0; i < list.length; i++) {
            Map.Entry<Long, Integer> entry = pq.poll();
            list[i] = entry.getKey();
        }
        return list;
    }

    private Long[] objToLong(Collection<Long> arr) {
        Long[] list = new Long[arr.size()];
        int i = 0;
        for (Long o : arr) list[i++] = o;
        return list;
    }

}
package cs6301.g26;

import java.util.*;

public class MDS {

    Map<Long,Item> itemTable;
    Map<Long,Float> supplierTable;
    Map<Long,HashSet<Pair>> supplierItemTable;
    Map<Long,Set<Long>> descriptionItemTable;

    public MDS() {
        itemTable = new HashMap<>();
        supplierTable = new HashMap<>();
    }

    public static class Pair {
        long id;
        int price;
        public Pair(long id, int price) {
            this.id = id;
            this.price = price;
        }

        /**
         * hashCode of a vertex can be its name, since name is unique
         */
        public int hashCode() {
            return Long.hashCode(id);
        }


        /** name of vertex is unique, so use that to implement equals
         */
        @Override
        public  boolean equals(Object other) {
            Supplier otherItem = (Supplier) other;
            if(otherItem == null) {
                return false;
            }
            return this.id == otherItem.id;
        }
    }

    public static class Item {
        Set<Long> description;
        Set<Long> supplierMap;

        public Item() {
            this.description = null;
            this.supplierMap = null;
        }
    }

    public static class Supplier {
        long id;
        float reputation;

        public Supplier(Long id, Float reputation) {
            this.id = id;
            this.reputation = reputation;
        }

        /**
         * hashCode of a vertex can be its name, since name is unique
         */
        public int hashCode() {
            return Long.hashCode(id);
        }


        /** name of vertex is unique, so use that to implement equals
         */
        @Override
        public boolean equals(Object other) {
            Supplier otherSupplier = (Supplier) other;
            if(otherSupplier == null) {
                return false;
            }
            return this.id == otherSupplier.id;
        }

    }

    /* add a new item.  If an entry with the same id already exists,
       the new description is merged with the existing description of
       the item.  Returns true if the item is new, and false otherwise.
    */
    public boolean add(Long id, Long[ ] description) {
         if(itemTable.containsKey(id)){
             return false;
         }
             Item newItem = new Item();
             newItem.description = new HashSet<Long>(Arrays.asList(description));
             itemTable.put(id,newItem);
             return true;
    }

    /* add a new supplier (Long) and their reputation (float in
       [0.0-5.0], single decimal place). If the supplier exists, their
       reputation is replaced by the new value.  Return true if the
       supplier is new, and false otherwise.
    */
    public boolean add(Long supplier, float reputation) {
        if(supplierTable.containsKey(supplier)){
            return false;
        }
        supplierTable.put(supplier,reputation);
        return true;
    }

    /* add products and their prices at which the supplier sells the
      product.  If there is an entry for the price of an id by the
      same supplier, then the price is replaced by the new price.
      Returns the number of new entries created.
    */
    public int add(Long supplier, Pair[ ] idPrice) {
        int newEntries =0;
        for(Pair curItem : idPrice){
            if(itemTable.containsKey(curItem.id)){
                if(!supplierItemTable.containsKey(supplier)){
                    supplierItemTable.put(supplier,null);
                    newEntries++;
                }
                supplierItemTable.get(supplier).add(curItem);
                itemTable.get(curItem.id).supplierMap.add(supplier);
            }
        }
        return newEntries;
    }

    /* return an array with the description of id.  Return null if
      there is no item with this id.
    */
    public Long[ ] description(Long id) {
        if(itemTable.containsKey(id)){
            return (Long[])itemTable.get(id).description.toArray();
        }
        return null;
    }

    /* given an array of Longs, return an array of items whose
      description contains one or more elements of the array, sorted
      by the number of elements of the array that are in the item's
      description (non-increasing order).
    */
    public Long[ ] findItem(Long[ ] arr) {
        Map<Long,Integer> itemDescriptionCount = new HashMap<>();
        int count;
        for(int i=0;i<arr.length;i++){
            if(descriptionItemTable.containsKey(arr[i])){
                for(Long item : descriptionItemTable.get(arr[i])){
                     count = itemDescriptionCount.getOrDefault(item,0);
                     itemDescriptionCount.put(item,count++);
                    // itemDescriptionCount.put(item, itemDescriptionCount.getOrDefault(item, 0) + 1);
                }
            }
        }

        PriorityQueue<Map.Entry<Long, Integer>> pq =
                new PriorityQueue<Map.Entry<Long, Integer>>(new Comparator<Map.Entry<Long, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<Long, Integer> entry1, Map.Entry<Long, Integer> entry2)
                    {
                        return entry1.getValue() - entry2.getValue();
                    }
                });


        // insert in the queue
        for(Map.Entry<Long, Integer> entry : itemDescriptionCount.entrySet()){
            pq.offer(entry);
        }

        //add and return it to a long array
        Long[] list = new Long[itemDescriptionCount.size()];
        for(int i = 0; i < list.length; i ++){
            Map.Entry<Long, Integer> entry = pq.poll();
            list[i] = entry.getKey();
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
    public Long[ ] findItem(Long n, int minPrice, int maxPrice, float minReputation) {
        Integer leastPrice = Integer.MAX_VALUE;
        Map<Long,Integer> itemPrice = new HashMap<>();
        Set<Long> items = descriptionItemTable.get(n);
        PriorityQueue<Map.Entry<Long, Integer>> pq =
                new PriorityQueue<Map.Entry<Long, Integer>>(new Comparator<Map.Entry<Long, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<Long, Integer> entry1, Map.Entry<Long, Integer> entry2)
                    {
                        return entry1.getValue() - entry2.getValue();
                    }
                });


        for(Long item:items){
            Set<Long> Suppliers = itemTable.get(item).supplierMap;
            for(Long supplier :  Suppliers){
                if(supplierTable.get(supplier)>= minReputation){
                    for (Pair curItem : supplierItemTable.get(supplier)) {
                        if (curItem.equals(item)) {
                            if(curItem.price>=minPrice && curItem.price<=maxPrice){
                                if(curItem.price < leastPrice) leastPrice = curItem.price;
                            }
                        }
                        itemPrice.put(curItem.id,leastPrice);
                    }
                }
            }
        }

        // insert in the queue
        for(Map.Entry<Long, Integer> entry : itemPrice.entrySet()){
            pq.offer(entry);
        }

        //add and return it to a long array
        Long[] list = new Long[itemPrice.size()];
        for(int i = 0; i < list.length; i ++){
            Map.Entry<Long, Integer> entry = pq.poll();
            list[i] = entry.getKey();
        }
        return list;
    }

    /* given an id, return an array of suppliers who sell that item,
      ordered by the price at which they sell the item (non-decreasing order).
    */
    public Long[ ] findSupplier(Long id) {
        Set<Long> suppliers = itemTable.get(id).supplierMap;
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(new Comparator<Pair>()
        {
            @Override
            public int compare(Pair entry1, Pair entry2)
            {
                return entry1.price - entry2.price;
            }
        });
        for(Long supplier:suppliers){
            for(Pair curItem :supplierItemTable.get(supplier)){
                if(curItem.id == id){
                    pq.offer(curItem);
                }
            }
        }

        Long[] suppliersList = new Long[pq.size()];
        for(int i=0;i<suppliersList.length;i++){
            suppliersList[i] = pq.remove().id;
        }
        return suppliersList;
    }

    /* given an id and a minimum reputation, return an array of
      suppliers who sell that item, whose reputation meets or exceeds
      the given reputation.  The array should be ordered by the price
      at which they sell the item (non-decreasing order).
    */
    public Long[ ] findSupplier(Long id, float minReputation) {
        Set<Long> Suppliers = itemTable.get(id).supplierMap;
        Integer leastPrice = Integer.MAX_VALUE;
        Map<Long,Integer> itemPrice = new HashMap<>();
        PriorityQueue<Map.Entry<Long, Integer>> pq =
                new PriorityQueue<Map.Entry<Long, Integer>>(new Comparator<Map.Entry<Long, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<Long, Integer> entry1, Map.Entry<Long, Integer> entry2)
                    {
                        return entry1.getValue() - entry2.getValue();
                    }
                });


        for(Long supplier :  Suppliers){
            if(supplierTable.get(supplier)>= minReputation){
                for (Pair curItem : supplierItemTable.get(supplier)) {
                    if (curItem.equals(id)) {
                            if(curItem.price < leastPrice) leastPrice = curItem.price;
                    }
                    itemPrice.put(curItem.id,leastPrice);
                }
            }
        }

        for(Map.Entry<Long, Integer> entry : itemPrice.entrySet()){
            pq.offer(entry);
        }

        //add and return it to a long array
        Long[] list = new Long[itemPrice.size()];
        for(int i = 0; i < list.length; i ++){
            Map.Entry<Long, Integer> entry = pq.poll();
            list[i] = entry.getKey();
        }
        return list;
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
    public Long[ ] identical() {
        return null;
    }

    /* given an array of ids, find the total price of those items, if
       those items were purchased at the lowest prices, but only from
       sellers meeting or exceeding the given minimum reputation.
       Each item can be purchased from a different seller.
    */
    public int invoice(Long[ ] arr, float minReputation) {
        int total = 0;
        for(Long item : arr){
            total += findLeastPrice(item,minReputation);
        }
        return total;
    }

    private int findLeastPrice(Long item, float minReputation) {
        Set<Long> Suppliers = itemTable.get(item).supplierMap;
        Integer leastPrice = Integer.MAX_VALUE;
        Map<Long,Integer> itemPrice = new HashMap<>();

        for(Long supplier :  Suppliers){
            if(supplierTable.get(supplier)>= minReputation){
                for (Pair curItem : supplierItemTable.get(supplier)) {
                    if (curItem.equals(item)) {
                        if(curItem.price < leastPrice) leastPrice = curItem.price;
                    }
                }
            }
        }
        return leastPrice;
    }

    /* remove all items, all of whose suppliers have a reputation that
       is equal or lower than the given maximum reputation.  Returns
       an array with the items removed.
    */
    public Long[ ] purge(float maxReputation) {
        return null;
       // three tables update
    }

    /* remove item from storage.  Returns the sum of the Longs that
       are in the description of the item deleted (or 0, if such an id
       did not exist).
    */
    public Long remove(Long id) {
        return 0L;
    }

    /* remove from the given id's description those elements that are
       in the given array.  It is possible that some elements of the
       array are not part of the item's description.  Return the
       number of elements that were actually removed from the description.
    */
    public int remove(Long id, Long[ ] arr) {
        return 0;
    }

    /* remove the elements of the array from the description of all
       items.  Return the number of items that lost one or more terms
       from their descriptions.
    */
    public int removeAll(Long[ ] arr) {
        return 0;
    }
}
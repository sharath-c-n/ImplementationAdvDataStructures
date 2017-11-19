package cs6301.g26;

import java.util.*;

public class MDS {
    /**Each entry in this table : (ItemId,(Description,(SupplierId,ItemPrice)))
     * This table holds an entry for each item as key
     * and its value holds the item description,SupplierPrice Map
    **/

    private Map<Long,Item> itemTable;
    /**Each entry in this table : (SupplierId,Reputation)
     * Has SupplierId as key and a supplier's reputation as value
     **/
    private Map<Long,Float> supplierTable;
    //Each entry in this table : (Description,{Items})
    private Map<Long,Set<Long>> descriptionItemTable;

    public MDS() {
        itemTable = new HashMap<>();
        supplierTable = new HashMap<>();
        descriptionItemTable = new HashMap<>() ;
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
            Pair otherItem = (Pair) other;
            if(otherItem == null) {
                return false;
            }
            return this.id == otherItem.id;
        }
    }

    public static class Item {
        Set<Long> description;
        Map<Long,Integer> supplierPriceMap;

        public Item() {
            this.description = new HashSet<>();
            this.supplierPriceMap = new TreeMap<>();
        }
        public Item(Long desc,Long supplierId,Integer price) {
            if(desc!=null){
                this.description.add(desc);
            }
            this.supplierPriceMap.put(supplierId,price);
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
        for(Pair pair : idPrice){
            Item curItem = itemTable.get(pair.id);
            if(curItem != null){
                if(curItem.supplierPriceMap.put(supplier,pair.price)== null)  newEntries++;
            }
        }
        return newEntries;
    }

    /* return an array with the description of id.  Return null if
      there is no item with this id.
    */
    public Long[ ] description(Long id) {
        Item item = itemTable.get(id);
        return item == null ? null : (Long[])item.description.toArray();
    }

    /* given an array of Longs, return an array of items whose
      description contains one or more elements of the array, sorted
      by the number of elements of the array that are in the item's
      description (non-increasing order).
    */
    public Long[ ] findItem(Long[ ] arr) {
        Map<Long,Integer> itemDescriptionCount = new HashMap<>();
        int count;
        for(Long description:arr){
            if(descriptionItemTable.containsKey(description)){
                for(Long item : descriptionItemTable.get(description)){
                     count = itemDescriptionCount.getOrDefault(item,0);
                     itemDescriptionCount.put(item,count++);
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
            Map<Long, Integer> supplierPriceMap = itemTable.get(item).supplierPriceMap;
            for(Long supplier :  supplierPriceMap.keySet()){
                if(supplierTable.get(supplier)>= minReputation){
                            int curSupplierPrice =  supplierPriceMap.get(supplier);
                            if(curSupplierPrice>=minPrice && curSupplierPrice<=maxPrice && curSupplierPrice<leastPrice){
                                leastPrice = curSupplierPrice;
                            }
                        }
                        itemPrice.put(item,leastPrice);
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
        Map<Long, Integer> supplierPrice = itemTable.get(id).supplierPriceMap;
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
        for(Map.Entry<Long, Integer> entry : supplierPrice.entrySet()){
            pq.offer(entry);
        }

        //add and return it to a long array
        Long[] suppliersList = new Long[supplierPrice.size()];
        for(int i = 0; i < suppliersList.length; i ++){
            Map.Entry<Long, Integer> entry = pq.poll();
            suppliersList[i] = entry.getKey();
        }
        return suppliersList;
    }

    /* given an id and a minimum reputation, return an array of
      suppliers who sell that item, whose reputation meets or exceeds
      the given reputation.  The array should be ordered by the price
      at which they sell the item (non-decreasing order).
    */
    public Long[ ] findSupplier(Long id, float minReputation) {
        Map<Long, Integer> supplierPrice = itemTable.get(id).supplierPriceMap;
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
        for(Map.Entry<Long, Integer> entry : supplierPrice.entrySet()){
            float supplierReputation = supplierTable.get(entry.getKey());
            if(supplierReputation >= minReputation){
                pq.offer(entry);
            }
        }

        //add and return it to a long array
        Long[] suppliersList = new Long[supplierPrice.size()];
        for(int i = 0; i < suppliersList.length; i ++){
            Map.Entry<Long, Integer> entry = pq.poll();
            suppliersList[i] = entry.getKey();
        }
        return suppliersList;
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
        Map<Long, Integer> supplierPriceMap = itemTable.get(item).supplierPriceMap;
        Integer leastPrice = Integer.MAX_VALUE;
        Map<Long,Integer> itemPrice = new HashMap<>();

        for(Map.Entry<Long, Integer> cur : supplierPriceMap.entrySet()){
            if(supplierTable.get(cur.getKey())>= minReputation){
                        if(cur.getValue() < leastPrice) leastPrice = cur.getValue();
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
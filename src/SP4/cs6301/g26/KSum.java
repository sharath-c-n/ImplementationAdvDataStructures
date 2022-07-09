class kSum {

    /**
    * Run time O(n^k-1)
    **/
    List<List<Integer>> kSum(int k, int[] arr, int index,long target){
        List<List<Integer>> result = new ArrayList<>();
        int size = arr.length;
        if(index >= size)
            return result;
        if(k > 2){
            for(int i = index ; i < size ; i++){
                for(List<Integer> res : kSum( k -1, arr, i+1, target - (long)arr[i])){
                    result.add(getNewList(arr[i],res));
                }
                while( i < size -1 && arr[i] == arr[i+1]) i++;
            }
            return result;
        } else {
                int start = index;
                int end = size - 1;
                while(start < end){
                    long remSum =(long)arr[start] + (long)arr[end]; 
                    if(remSum < target)
                        start++;
                    else if (remSum > target )
                        end--;
                    else {
                        result.add(List.of(arr[start],arr[end]));
                        while(start < end && arr[start] == arr[start+1]) ++start;
                        while(start < end && arr[end] == arr[end-1]) --end;
                        start++; end--;
                    } 
            }
            
        }
        return result;
    }

        List<Integer> getNewList(int ele, List<Integer> eleList){
            List<Integer> result = new ArrayList<>();
            result.add(ele);
            for(int i : eleList) result.add(i);
            return result;
        }
  
  
      public void main() {
        int[] nums = new int[1,0,-1,0,-2,2];
        int target = 0;
         Arrays.sort(nums);
         List<List<Integer>> result =  kSum(4,nums,0,target);
        for(List<Integer> list : result){
          System.out.println(list);
        
    }
}

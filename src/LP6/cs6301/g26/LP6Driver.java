package cs6301.g26;

import java.util.Arrays;

/**
 * LP6Driver:
 * @author : Sharath
 * 18/11/2017
 */
public class LP6Driver {
    public static void main(String[] args) {
        MDS mds = new MDS();
        mds.add(123456L,new Long[]{23561L,63176812L,6136781L});
        mds.add(128945L,3.5F);
        mds.add(128947L,2.5F);
        mds.add(128942L,4.6F);
        mds.add(128943L,4.6F);
        mds.add(128943L,new MDS.Pair[]{new MDS.Pair(123456L,229)});
        mds.add(128947L,new MDS.Pair[]{new MDS.Pair(123456L,230)});
        mds.add(128945L,new MDS.Pair[]{new MDS.Pair(123456L,231)});
        mds.add(128942L,new MDS.Pair[]{new MDS.Pair(123456L,229)});
        mds.add(123456L,new Long[]{4678L,5743793L});
        mds.add(123457L,new Long[]{4678L,5743793L,7543839L});
        System.out.println(Arrays.toString(mds.findSupplier(123456L)));
        System.out.println("Description of : 123456L = "+Arrays.toString(mds.description(123456L)));
        System.out.println("Find by description : "+Arrays.toString(mds.findItem(new Long[]{23561L,4678L,5743793L,7543839L,63176812L})));
        System.out.println(Arrays.toString(mds.identical()));

    }
}

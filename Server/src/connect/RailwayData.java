package connect;

import java.util.Arrays;

public class RailwayData {
	Object[] interArray;

	public RailwayData(Object[] arr){
		interArray = arr;
	}
	
	@Override
    public boolean equals(Object o) {
        /* 谨慎地编写equals方法，这里的代码没有考虑所有的情况 */
        if (o != null) {
            if (o instanceof RailwayData) {
                /* Arrays.equals会对两个数组进行基于值的比较 */
                return Arrays.equals(interArray, ((RailwayData) o).interArray);
            }
        }
        return false;
    }

	
	@Override
	public int hashCode() {
		/* Arrays.deepHashCode 会根据数组内的元素的值计算 hashCode */
		return Arrays.deepHashCode(interArray);
	}	
}


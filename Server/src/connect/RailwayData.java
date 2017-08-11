package connect;

import java.util.Arrays;

public class RailwayData {
	Object[] interArray;

	public RailwayData(Object[] arr){
		interArray = arr;
	}
	
	@Override
    public boolean equals(Object o) {
        /* �����ر�дequals����������Ĵ���û�п������е���� */
        if (o != null) {
            if (o instanceof RailwayData) {
                /* Arrays.equals�������������л���ֵ�ıȽ� */
                return Arrays.equals(interArray, ((RailwayData) o).interArray);
            }
        }
        return false;
    }

	
	@Override
	public int hashCode() {
		/* Arrays.deepHashCode ����������ڵ�Ԫ�ص�ֵ���� hashCode */
		return Arrays.deepHashCode(interArray);
	}	
}


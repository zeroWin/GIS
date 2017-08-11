package connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import KDtree.Checker;
import KDtree.KDTree;
import KDtree.KeyDuplicateException;
import KDtree.KeyMissingException;
import KDtree.KeySizeException;

class test {

	public static void main(String[] args) throws KeySizeException, KeyDuplicateException, KeyMissingException {
		// TODO Auto-generated method stub
		connectDB con = new connectDB();
		System.out.println(con.judgeLogin(1,"001"));
		System.out.println(con.judgeLogin(1,"2"));
		System.out.println(con.judgeLogin(1,"3"));
		System.out.println(con.judgeLogin(1,"6"));
		System.out.println(con.judgeLogin(0,"6"));
		con.loadSqlData();
		
		long start = System.currentTimeMillis(); 
		System.out.println(con.judgeDistance(39.963736099800, 116.354898219000));
		System.out.println(con.judgeDistance(39.956702406000, 116.355234699000));
		long timekdtree = System.currentTimeMillis()-start;
		System.out.println("kdtree:"+timekdtree);  
		con.insertLocal(0, "1213", 125.1231, 23.555);
		con.judgeLogin(1, "1");
//		
//		int dims = 3;
//		int samples = 10000;
//		KDTree<Integer> kt = new KDTree<Integer>(dims);
//		double[] targ = makeSample(dims);
//
//		int min_index = 0;
//		double min_value = Double.MAX_VALUE;
//		for (int i = 0; i < samples; ++i) {
//			double[] keys = makeSample(dims);
//			kt.insert(keys, new Integer(i));
//
//			/*
//			 * for the purposes of test, we want the nearest EVEN-NUMBERED point
//			 */
//			if ((i % 2) == 0) {
//				double dist = distSquared(targ, keys);
//				if (dist < min_value) {
//					min_value = dist;
//					min_index = i;
//				}
//			}
//		}
//
//		int nbrs = kt.nearest(targ);
//		System.out.println(nbrs);
		
	}
	static java.util.Random rand = new java.util.Random();

	static double[] makeSample(int dims) {
		double[] rv = new double[dims];
		for (int j = 0; j < dims; ++j) {
			rv[j] = rand.nextDouble();
		}
		return rv;
	}
	
	static double distSquared(double[] p0, double[] p1) {
		double rv = 0;
		for (int i = 0; i < p0.length; i++) {
			double diff = p0[i] - p1[i];
			rv += (diff * diff);
		}
		return rv;
	}	
}

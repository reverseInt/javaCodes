import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

// Given an int array, find the max product of any 3 elements 
public class MaxProduct {

	// O(nlogn)
	public static int maxProduct(int[] a) {
		int n = a.length;
		if (n < 3) {
			throw new RuntimeException("invalid input");
		}
		if (n == 3) return a[0]*a[1]*a[2];
		Arrays.sort(a);
		return Math.max(a[0]*a[1]*a[n-1], a[n-3]*a[n-2]*a[n-1]);
	}
	
	// O(n)
	public static int maxProductII(int[] a) {
		int n = a.length;
		if (n < 3) {
			throw new RuntimeException("invalid input");
		}
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(3);
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(3, Collections.reverseOrder());
		for (int i = 0; i < n; ++i) {
			minHeap.offer(a[i]);
			maxHeap.offer(a[i]);
			if (i > 2) {
				minHeap.poll();
				maxHeap.poll();
			}
		}
		maxHeap.poll();
		int third = minHeap.poll();
		int second = minHeap.poll();
		int first = minHeap.poll();
		int negSecond = maxHeap.poll();
		int negFirst = maxHeap.poll();
		return Math.max(first * second * third, first * negFirst * negSecond);
	}
	
	
	public static void main(String[] args) {
		int[] a = {1,2,3,4,5};
		int[] b = {1,2,4,-5};
		int[] c = {1,-5,3,-6,0,7};
		int[] d = {-2,-10,-9,-8};
		int[] e = {0,1,-8,0,-9};
		System.out.println(maxProduct(a));
		System.out.println(maxProduct(b));
		System.out.println(maxProduct(c));
		System.out.println(maxProduct(d));
		System.out.println(maxProduct(e));
		System.out.println(maxProductII(a));
		System.out.println(maxProductII(b));
		System.out.println(maxProductII(c));
		System.out.println(maxProductII(d));
		System.out.println(maxProductII(e));
	}

}

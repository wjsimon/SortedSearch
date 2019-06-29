import java.util.Comparator;
import java.util.Random;

public class SortedSearch {

	public static abstract class Search {

		protected CountingComparator comparator;

		public Search() {
			this.comparator = new CountingComparator();
		}

		public abstract boolean search(Long[] sortedList, Long key);

		public int getNumberOfComparisons() {
			return this.comparator.getNumberOfComparisons();
		}
	}

	public static class LinearSearch extends Search {

		@Override
		public boolean search(Long[] sortedList, Long key) {
			for (Long element : sortedList) {
				// compare returns a negative integer, zero, or a positive integer if
				// the first argument is less than, equal to, or greater than the
				// second.
				int comparison = this.comparator.compare(element, key);
				if (comparison == 0) {
					return true;
				}
			}
			return false;
		}
	}

	public static class BinarySearch extends Search {

		@Override
		public boolean search(Long[] sortedList, Long key) {
			// TODO: Code hier einfuegen
			
			//Implementation nach VL-Folien!
			
			int m = 0;
			int comparison;
			int l = 0; //0 da echtes Array
			int r = sortedList.length-1; // -1 da echtes Array
			//in VL geht Index von 1 bis length; in echtem Array von 0 bis length-1

			
			//1-zu-1 Umsetzung des VL-Pseudocodes in Java
			while (l <= r)
			{
				m = l + ((r-l) / 2);
				if(m >= sortedList.length || m < 0) return false;
				
				//comparison wie verlangt statt den normalen Vergleichen aus VL
				comparison = this.comparator.compare(key, sortedList[m]);
				
				if(comparison < 0)
				{
					r = m-1;
				}
				else if (comparison > 0)
				{
					l = m+1;
				}
				else
					return true;
			}
			
			return false;
		}
	}

	public static class InterpolationSearch extends Search {

		@Override
		public boolean search(Long[] sortedList, Long key) {
			//Identisch zu BinarySearch, jedoch wird m anders berechnet
			int m = 0;				
			int l = 0;
			int r = sortedList.length-1;
			int div;
			
			int comparison;

			while (l <= r)
			{
				//umsetzung der Formel aus der VL fuer m		
				m = (int) (l+((key-sortedList[l])*(r-l))/(sortedList[r]-sortedList[l]));; 
				if(m >= sortedList.length || m < 0) return false;
				
				//rest laeuft mit modifiziertem m exakt wie BinarySearch
				
				comparison = this.comparator.compare(key, sortedList[m]);
				
				if(comparison < 0)
				{
					r = m-1;
				}
				else if (comparison > 0)
				{
					l = m+1;
				}
				else
					return true;
			}
			
			return false;
		}
	}

	public static class CountingComparator implements Comparator<Long> {

		private int numberOfComparisons;

		public CountingComparator() {
			this.numberOfComparisons = 0;
		}

		@Override
		public int compare(Long o1, Long o2) {
			this.numberOfComparisons++;
			return o1.compareTo(o2);
		}

		public int getNumberOfComparisons() {
			return this.numberOfComparisons;
		}
	}

	public static void main(String[] args) {
		Search search;
		Random random = new Random(1l);

		System.out.println("Preliminary testing. Initializing list [1, 2, 3].");
		Long[] list = { 1l, 2l, 3l };

		System.out.println("Searching for Element \"0\", which should not be found.");

		search = new LinearSearch();
		boolean located = search.search(list, 0l);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"0\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, 0l);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"0\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, 0l);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"0\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		// ----

		System.out.println("Searching for Element \"1\", which should be found.");

		search = new LinearSearch();
		located = search.search(list, 1l);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"1\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, 1l);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"1\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, 1l);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"1\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		// ----

		System.out.println("Searching for Element \"2\", which should be found.");

		search = new LinearSearch();
		located = search.search(list, 2l);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"2\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, 2l);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"2\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, 2l);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"2\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		// ---

		System.out.println("Searching for Element \"3\", which should be found.");

		search = new LinearSearch();
		located = search.search(list, 3l);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"3\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, 3l);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"3\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, 3l);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"3\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		// ---------

		System.out.println("Searching for Element \"4\", which should not be found.");

		search = new LinearSearch();
		located = search.search(list, 4l);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"4\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, 4l);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"4\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, 4l);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"4\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		// ---------

		System.out.println("\nInitializing pseudo-randomized, sorted list with one million elements. "
				+ "The list starts with a zero and neighboring elements have a difference of 1-3.");

		list = new Long[1000000];
		list[0] = 0l;
		for (int i = 1; i < list.length; i++) {
			list[i] = list[i - 1] + random.nextInt(3) + 1;
		}
		System.out.println("Searching for Element \"0\", which should be the first element.");
		search = new LinearSearch();
		located = search.search(list, 0l);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"0\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, 0l);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"0\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, 0l);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"0\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		// ---------
		
		System.out.println("Searching for the 300.000th element of the list.");

		search = new LinearSearch();
		located = search.search(list, list[300000 - 1]);
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \""
				+ list[300000 - 1].intValue() + "\" (" + search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, list[300000 - 1]);
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \""
				+ list[300000 - 1].intValue() + "\" (" + search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, list[300000 - 1]);
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \""
				+ list[300000 - 1].intValue() + "\" (" + search.getNumberOfComparisons() + " comparisons).");
		
		/**/
		
		
		// ---------

		System.out.println("\nInitializing list with 63 elements. The list starts with a one and following "
				+ "elements have 2 times the value of its predecessor.");
		list = new Long[63];
		list[0] = 1l;
		for (int i = 1; i < list.length; i++) {
			list[i] = list[i - 1] * 2l;
		}

		System.out.println("Searching for Element \"2^31\", which should be right in the middle.");

		search = new LinearSearch();
		located = search.search(list, (long) Math.pow(2d, 31d));
		System.out.println("\tLinear search:        " + (located ? "located" : "did not locate") + " element \"2^31\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new BinarySearch();
		located = search.search(list, (long) Math.pow(2d, 31d));
		System.out.println("\tBinary search:        " + (located ? "located" : "did not locate") + " element \"2^31\" ("
				+ search.getNumberOfComparisons() + " comparisons).");

		search = new InterpolationSearch();
		located = search.search(list, (long) Math.pow(2d, 31d));
		System.out.println("\tInterpolation search: " + (located ? "located" : "did not locate") + " element \"2^31\" ("
			+ search.getNumberOfComparisons() + " comparisons).");
		/**/
	}

}

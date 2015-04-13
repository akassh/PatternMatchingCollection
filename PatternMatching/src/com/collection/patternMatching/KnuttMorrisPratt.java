
/**
 *  Knutt Morris Pratt Pattern Matching Algorithm.
 *	@author Akassh 
 */
package com.collection.patternMatching;


public class KnuttMorrisPratt implements PatternMatching {
	private int[] failureTable(String s) {
		if(s==null||s.isEmpty())
			return null;
		int[] fTable = new int[s.length()];
		for(int i=1,l=0;i<fTable.length;) {
			if(s.charAt(l)==s.charAt(i))
				fTable[i++] = ++l;
			else if(l==0)
				fTable[i++] = l;
			else 
				l = fTable[l-1];
		}
		return fTable;
	}
	
	public int search(String txt, String pattern) {
		if(txt==null || txt.isEmpty() || pattern==null || pattern.isEmpty())
			return -1;
		int[] failureTable = failureTable(pattern);
		for(int i=0,j=0;i<txt.length();++i) {
			if(txt.charAt(i)==pattern.charAt(j)) {
				++i;
				++j;
			} else 
				j = failureTable[j];
			if(j==pattern.length())
				return i+1-pattern.length();
		}
		return -1;
	}
	
	public static void main(String[] args) {
		KnuttMorrisPratt f = new KnuttMorrisPratt();
		String txt = "GEEKS FORS GEEKS";
		String pattern = "FOR";
		System.out.printf("String: %s, Pattern: %s found at index: %d", txt, pattern, f.search(txt, pattern));
	}
}

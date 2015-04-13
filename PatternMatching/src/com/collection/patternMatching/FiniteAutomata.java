/**
 * @author Akassh
 * Finite Automata Pattern Matching
 * Time Complexity(m*n) worst case
 */
package com.collection.patternMatching;

import java.util.Arrays;
import java.util.HashMap;

public class FiniteAutomata implements PatternMatching {
	
	private int numberofUniqueCharactersInString(String s) {
		if(s==null)
			return 0;
		long l = 0l;
		s = s.toUpperCase();
		for(int i=0;i<s.length();++i)
			l = l | (1 << s.charAt(i) - 65);
		int count = 0;
		for(;l>0;l=l&(l-1), ++count);
		return count;	
	}
	
	private HashMap<Character, Integer> getCharactertoIndexMapping(String s) {
		if(s==null||s.isEmpty())
			return null;
		HashMap<Character, Integer> indexer = new HashMap<Character, Integer>();
		char[] c = s.toCharArray();
		Arrays.sort(c);
		for(int i=0,index = 0;i<c.length;++i)
			if(!indexer.containsKey(c[i]))
				indexer.put(c[i], index++);
		return indexer;
	}
	
	private int[][] finiteAutomataPatternTableGenerator(String pattern) {
		if(pattern==null||pattern.isEmpty())
			return null;
		int[][] pTable = new int[pattern.length()+1][numberofUniqueCharactersInString(pattern)];
		HashMap<Character, Integer> indexer = getCharactertoIndexMapping(pattern);
		int lps = 0;
		pTable[lps][indexer.get(pattern.charAt(0))] = 1;
		for(int i=1;i<pTable.length;++i) {
			for(int j=0;j<pTable[i].length;++j)
				pTable[i][j] = pTable[lps][j];
			if(i<pattern.length()){
				lps = pTable[lps][indexer.get(pattern.charAt(i))];
				pTable[i][indexer.get(pattern.charAt(i))] = i+1;
			}	
		}
		return pTable;
	}
	
	public int search(String txt, String pattern) {
		int[][] pTable = finiteAutomataPatternTableGenerator(pattern);
		HashMap<Character, Integer> indexer = this.getCharactertoIndexMapping(pattern);
		for(int i=0,j=0;i<txt.length();++i) {
			j = !indexer.containsKey(txt.charAt(i)) ? 0 : pTable[j][indexer.get(txt.charAt(i))];
			if(j==pattern.length())
				return i+1-pattern.length();
		}
		return -1;
	}
	
	public static void main(String[] args) {
		FiniteAutomata f = new FiniteAutomata();
		String txt = "GEEKS FORS GEEKS";
		String pattern = "FOR";
		System.out.printf("String: %s, Pattern: %s found at index: %d", txt, pattern, f.search(txt, pattern));
	}
	
}

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.Set;
import java.util.HashSet;

/* Findings: Reg and Recursive fail at 10,000 1 2 10,000
*/


/*
* n is number of people
* frenemy is relation matrix
* x is starting person's index
* y is ending person's index
* relation is the sequence of relations from x to y.
* Return 1 if the relation reaches y from x, 0 if not.
*/



/* TODO:
- for new approach:
  - if the set cardinality == N, then no need to keep updating b/c all people are 
  already in the set.

*/
public class Solution {
	static int isFrenemy(int n, String[] frenemy, int x, int y, String relation) {
		if (relation.length() == 0) {
			if(x == y) {
				return 1;
			}
			return 0;
		}
		if (relation.length() == 1) {
			if (frenemy[x].charAt(y) == relation.charAt(0)) {
				return 1;
			}
			return 0;
		}

		for (int front_i = 0; front_i < frenemy.length; front_i++) {
			if (frenemy[x].charAt(front_i) == relation.charAt(0)) {
				
				for (int back_i = 0; back_i < frenemy.length; back_i++) {
					if (frenemy[y].charAt(back_i) == relation.charAt(relation.length()-1)) {

						if (isFrenemy(n, frenemy, front_i, back_i,
							relation.substring(1, relation.length()-1)) == 1) {
							return 1;
						}

					}
				}

			}
		}
		return 0;
	}
	static int isFrenemyRecursive(int n, String[] frenemy, int x, int y, String relation) {
		if (relation.length() == 0) {
			if(x == y) {
				return 1;
			}
			return 0;
		} 

		for (int frenemy_i = 0; frenemy_i < frenemy.length; frenemy_i++) {
			if (frenemy[x].charAt(frenemy_i) == relation.charAt(0)) {
				if (isFrenemyRecursive(n, frenemy, frenemy_i, y, relation.substring(1)) == 1) {
					return 1;
				}
			}
		}
		return 0;
	}
	/* Theoretically sound, issue is that in the worst case is O(N^2).
	*
	* Idea is to keep a set of people adjacent to first person. 
	* Keep updating this set based on new adjacencies from last round.
	* Keep going for each relation in relationship chain.
	* In the end, check if the last person is in the final set of adjacencies.
	*/
	static int isFrenemyNewApproach(int n, String[] frenemy, int x, int y, String relation) {
		HashSet<Integer> adjacencies = new HashSet<Integer>();
		adjacencies.add(x);
		for (int relation_i = 0; relation_i < relation.length(); relation_i++) {
			char friendorfoe = relation.charAt(relation_i);
			// for debugging purposes.
			/*if (relation_i % 10 == 0) {
				System.out.println(friendorfoe);
			} else {
				System.out.print(friendorfoe);
			}*/
			adjacencies = adjacenciesUpdater(adjacencies, frenemy, friendorfoe);
		}
		boolean success = adjacencies.contains(y);
		if (success) return 1;
		return 0;
	}
	/* Returns a set of all the people connected to any person in the given set.
	* Since it is returning a set, repeats are not allowed, cutting down on complexity.
	*/
	private static HashSet<Integer> adjacenciesUpdater(HashSet<Integer> adjacencies, String[] frenemy, char relation) {
		HashSet<Integer> newAdjacencies = new HashSet<Integer>();
		for (int adjacentPerson : adjacencies) {
			adjacenciesFinder(newAdjacencies, frenemy[adjacentPerson], relation);
		}
		return newAdjacencies;
	}
	/* Returns a set of all the people connected to a specific person by the given relation.
	* frenemy: relationships of specific person
	* relation: relation desired.
	*/
	private static void adjacenciesFinder(HashSet<Integer> adjacencies, String frenemy, char relation) {
		for (int frenemy_i = 0; frenemy_i < frenemy.length(); frenemy_i++) {
			if (frenemy.charAt(frenemy_i) == relation) {
				adjacencies.add(frenemy_i);
			}
		}
	}

	/*
	Pass in as command line arguments in the following order:
	- number of people in the simulation.
	- index of starting person.
	- index of ending person.
	- length of relationship chain desired.
	Index starting at 0 for people because we are Computer Scientists.
	*/
	public static void main(String[] args) throws IOException {
		MatrixCreator mc = new MatrixCreator();
		int args_index = 0;
		int n = Integer.parseInt(args[args_index++]);
		String[] frenemy = mc.makeMatrix(n);

		int x = Integer.parseInt(args[args_index++]);
		int y = Integer.parseInt(args[args_index++]);
		if (x < 0 || y < 0 || x >= n || y >= n) {
			System.out.println("Invalid index given. Indices must be within range [0, " + n + ") for the given inputs.");
			System.exit(0);
		}
		int relation_length = Integer.parseInt(args[args_index++]);
		String relation = mc.createRelation(relation_length);

		if(relation.length() < 10) {
			System.out.println(x + " ---> " + y + " through " + relation);
		}
		else {
			System.out.println("Running...");
		}

		if(n < 20) {
			for(int frenemy_i = 0; frenemy_i < frenemy.length; frenemy_i++) {
				System.out.println(frenemy[frenemy_i]);
			}
		}

		int regResult;
		int recResult;
		int newResult;
		
		try {
			long startRegTime = System.nanoTime();
			regResult = isFrenemy(n, frenemy, x, y, relation);
			long endRegTime = System.nanoTime();
			double totalRegTime = (endRegTime - startRegTime) / 1000000;

			System.out.println("Regular  : " + regResult + " | Time: " + totalRegTime);
		}
		catch(StackOverflowError e) {
			System.out.println("Regular version failed.");
		}

		try {
			long startRecTime = System.nanoTime();
			recResult = isFrenemyRecursive(n, frenemy, x, y, relation);
			long endRecTime = System.nanoTime();
			double totalRecTime = (endRecTime - startRecTime) / 1000000;
			System.out.println("Recursive: " + recResult + " | Time: " + totalRecTime);
		}
		catch(StackOverflowError e) {
			System.out.println("Recursive version failed.");
		}
		
		try {
			long startNewTime = System.nanoTime();
			newResult = isFrenemyNewApproach(n, frenemy, x, y, relation);
			long endNewTime = System.nanoTime();
			double totalNewTime = (endNewTime - startNewTime) / 1000000;
			System.out.println("New: " + newResult + " | Time: " + totalNewTime);
		}
		catch(StackOverflowError e) {
			System.out.println("New version failed.");
		}

	}
}

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	static int isFrenemy(int n, String[] frenemy, int x, int y, String relation) {
		if(relation.length() == 0) {
			if(x == y) {
				return 1;
			}
			return 0;
		}
		if(relation.length() == 1) {
			if(frenemy[x].charAt(y) == relation.charAt(0)) {
				return 1;
			}
			return 0;
		}

		for(int front_i = 0; front_i < frenemy.length; front_i++) {
			if(frenemy[x].charAt(front_i) == relation.charAt(0)) {
				
				for(int back_i = 0; back_i < frenemy.length; back_i++) {
					if(frenemy[y].charAt(back_i) == relation.charAt(relation.length()-1)) {

						if(isFrenemy(n, frenemy, front_i, back_i,
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
		if(relation.length() == 0) {
			if(x == y) {
				return 1;
			}
			return 0;
		} 

		for(int frenemy_i = 0; frenemy_i < frenemy.length; frenemy_i++) {
			if(frenemy[x].charAt(frenemy_i) == relation.charAt(0)) {
				if(isFrenemyRecursive(n, frenemy, frenemy_i, y, relation.substring(1)) == 1) {
					return 1;
				}
			}
		}
		return 0;
	}
	public static void main(String[] args) throws IOException {
		MatrixCreator mc = new MatrixCreator();
		int args_index = 0;
		int n = Integer.parseInt(args[args_index++]);
		String[] frenemy = mc.makeMatrix(n);

		int x = Integer.parseInt(args[args_index++]);
		int y = Integer.parseInt(args[args_index++]);
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

		long startRegTime = System.nanoTime();
		regResult = isFrenemy(n, frenemy, x, y, relation);
		long endRegTime = System.nanoTime();
		double totalRegTime = (endRegTime - startRegTime) / 1000000;

		System.out.println("Regular  : " + regResult + " | Time: " + totalRegTime);


		long startRecTime = System.nanoTime();
		recResult = isFrenemyRecursive(n, frenemy, x, y, relation);
		long endRecTime = System.nanoTime();
		double totalRecTime = (endRecTime - startRecTime) / 1000000;

		System.out.println("Recursive: " + recResult + " | Time: " + totalRecTime);

	}
}
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
		/*
		int[] backs = new int[n];
		int[] fronts = new int[n];
		int back = 0;
		int front = 0;

		for(int _backs_fronts_i = 0; _backs_fronts_i < n; _backs_fronts_i++) {
			if(frenemy[x].charAt(_backs_fronts_i) == relation.charAt(0)) {
				fronts[front++] = _backs_fronts_i;
			}
			if(frenemy[y].charAt(_backs_fronts_i) == relation.charAt(relation.length()-1)) {
				backs[back++] = _backs_fronts_i;
			}
		}
		if(back == 0 || front == 0) {
			return 0;
		}
		int[] confirmedBacks = new int[back];
		int[] confirmedFronts = new int[front];
		System.arraycopy(backs, 0, confirmedBacks, 0, back);
		System.arraycopy(fronts, 0, confirmedFronts, 0, front);

		String newRelation = relation.substring(1, relation.length()-1);

		for(int confirmed_fronts_i = 0; confirmed_fronts_i < front; confirmed_fronts_i++) {
			for(int confirmed_backs_j = 0; confirmed_backs_j < back; confirmed_backs_j++) {

				if(isFrenemy(n, frenemy, confirmedFronts[confirmed_fronts_i], 
					confirmedBacks[confirmed_backs_j], newRelation) == 1) {
					return 1;
				}
			}
		}
		return 0;
		*/
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


		/*
		int _args_index = 0;
		int _n = Integer.parseInt(args[_args_index++]);
		int _frenemy_size = _n;
		String[] _frenemy = new String[_frenemy_size];
		String _frenemy_item;
		int res;

		for(int _frenemy_i = 0; _frenemy_i < _frenemy_size; _frenemy_i++) {
			try {
				_frenemy_item = args[_args_index++];
			} catch (Exception e) { 
				_frenemy_item = null;
			}
			_frenemy[_frenemy_i] = _frenemy_item;
		}
		int _x = Integer.parseInt(args[_args_index++]);
		int _y = Integer.parseInt(args[_args_index++]);
		String _relation = args[_args_index++];

		res = isFrenemy(_n, _frenemy, _x, _y, _relation);
		System.out.println(res);
		*/

		/* Their way
		Scanner in = new Scanner(System.in);
		final String fileName = System.getenv("OUTPUT_PATH");
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		int res;
		int _n;
		_n = Integer.parseInt(in.nextLine());





		int _x;
		_x = Integer.parseInt(in.nextLine());

		int _y;
		_y = Integer.parseInt(in.nextLine());

		String _relation;
		try {
			_relation = in.nextLine();
		} catch (Exception e) {
			_relation = null;
		}

		res = isFrenemy(_n, _frenemy, _x, _y, _relation);
		bw.write(String.valueOf(res));
		bw.newLine();

		bw.close();
		*/
	}
}
import java.util.*;

public class MatrixCreator {
	
	public static String[] makeMatrix(int n) {
		char[][] res = new char[n][n];
		Random gen = new Random();

		for(int res_i = 0; res_i < res.length; res_i++) {
			for(int person_i = res_i; person_i < res.length; person_i++) {
				char frenemy = friendOrFoe(gen.nextInt(2));
				if(res_i == person_i) {
					res[res_i][person_i] = '-';
				}
				else {
					res[res_i][person_i] = frenemy;
					res[person_i][res_i] = frenemy;
				}
			}
		}

		String[] relations = new String[n];
		for(int res_i = 0; res_i < res.length; res_i++) {
			relations[res_i] = String.valueOf(res[res_i]);
		}

		return relations;
	}
	private static char friendOrFoe(int a) {
		if(a == 0) {
			return 'F';
		}
		return 'E';
	}
	public static void main(String[] args) {
		int _n = Integer.parseInt(args[0]);
		makeMatrix(_n);
	}
}
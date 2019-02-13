import java.util.*;
import java.io.*;

class GeneralChecker{
	static Stack<Integer>[] peg;
	static boolean final_check(int num_disks, int r, int b){
		if(r==b){
			int to=r;
			int aux1 = 4 - to;
			int aux2 = 6 - to - aux1;
			if(to==2){
				aux1 = 1;
				aux2 = 3;
			}
			if(!peg[aux1-1].empty() || !peg[aux2-1].empty()){
				return false;
			}
			return true;
		}
		else{
			int aux = 6 - r - b;
			while(!peg[r-1].empty()){
				int disk = peg[r-1].pop();
				if(disk%2==0)
					return false;
			}
			while(!peg[b-1].empty()){
				int disk = peg[b-1].pop();
				if(disk%2==1)
					return false;	
			}
		}
		return true;
	}
	static boolean valid_move(int from, int to){
		if(from==to)
			return true;
		if(peg[from-1].empty()){
			return false;	
		}
		if(peg[to-1].empty()){
			return true;
		}
		if(peg[to-1].peek() > peg[from-1].peek()){
			return true;
		}
		return false;
	}
	static boolean tester(int num_disks, int from, int r, int b, String file_name){
		peg = (Stack<Integer>[]) new Stack[3];
		for(int i=0;i<3;i++){
			peg[i] = new Stack<Integer>();
		}
		try{
			FileInputStream fstream = new FileInputStream(file_name);
			Scanner s = new Scanner(fstream);
			int counter = num_disks-1;
			while(counter!=-1){
				peg[from-1].push(counter);
				counter--;
			}
			try{	
				while(s.hasNext()){
					int from_move = s.nextInt();
					int to_move = s.nextInt();
					if(valid_move(from_move, to_move)){
						int disk = peg[from_move-1].pop();
						peg[to_move-1].push(disk);
					}
					else{
						return false;
					}
				}
			}
			catch(Exception e){
				System.out.println("Wrong File Format");
				return false;
			}
		}
		catch(Exception e){

		}
		return final_check(num_disks, r, b);
	}
}

class NormalChecker{
	static Stack<Integer>[] peg;
	static boolean final_check(int num_disks, int to){
		int aux1 = 4 - to;
		int aux2 = 6 - to - aux1;
		if(to==2){
			aux1 = 1;
			aux2 = 3;
		}
		int counter = 0;
		while(!peg[to-1].empty()){
			counter++;
			peg[to-1].pop();	
		}
		if(!peg[aux1-1].empty() || !peg[aux2-1].empty() || counter!=num_disks){
			return false;
		}
		return true;
	}
	static boolean valid_move(int from, int to){
		if(from==to)
			return true;
		if(peg[from-1].empty()){
			return false;	
		}
		if(peg[to-1].empty()){
			return true;
		}
		if(peg[to-1].peek() > peg[from-1].peek()){
			return true;
		}
		return false;
	}
	static boolean tester (int num_disks, int from, int to, String file_name){
		peg = (Stack<Integer>[]) new Stack[3];
		for(int i=0;i<3;i++){
			peg[i] = new Stack<Integer>();
		}
		try{
			FileInputStream fstream = new FileInputStream(file_name);
			Scanner s = new Scanner(fstream);
			int counter = num_disks-1;
			while(counter!=-1){
				peg[from-1].push(counter);
				counter--;
			}
			try{	
				while(s.hasNext()){
					int from_move = s.nextInt();
					int to_move = s.nextInt();
					if(valid_move(from_move, to_move)){
						int disk = peg[from_move-1].pop();
						peg[to_move-1].push(disk);
					}
					else{
						return false;
					}
				}
			}
			catch(Exception e){
				System.out.println("Wrong File Format");
				return false;
			}
		}
		catch(Exception e){

		}
		return final_check(num_disks, to);
	}
}

public class Checker{
	public static void main ( String args []){
		 PrintStream stdout = System.out;
        try{
			PrintStream file1 = new PrintStream("test1.txt");
			System.setOut(file1);
	        GeneralizedTowerOfHanoi.gtoh_with_recursion(6,1,2,3);
	        file1.close();

	        PrintStream file2 = new PrintStream("test2.txt");
	        System.setOut(file2);
	        GeneralizedTowerOfHanoi.gtoh_without_recursion(8,1,2,2);
	        file2.close();
	        
	        PrintStream file3 = new PrintStream("test3.txt");
			System.setOut(file3);
	        TowerOfHanoi.toh_with_recursion(6,1,2);
	        file3.close();
	        
	        PrintStream file4 = new PrintStream("test4.txt");
			System.setOut(file4);
	        TowerOfHanoi.toh_without_recursion(8,1,2);
	        file4.close();
			System.setOut(stdout);	
        }
        catch(Exception e){

        }
		boolean file1_check = GeneralChecker.tester(6,1,2,3,"test1.txt");
		boolean file2_check = GeneralChecker.tester(8,1,2,2,"test2.txt");
		boolean file3_check = NormalChecker.tester(6,1,2,"test3.txt");
		boolean file4_check = NormalChecker.tester(8,1,2,"test4.txt");
		if(!file1_check){
			System.out.println("Test 1 failed");
		}
		if(!file2_check){
			System.out.println("Test 2 failed");
		}
		if(!file3_check){
			System.out.println("Test 3 failed");
		}
		if(!file4_check){
			System.out.println("Test 4 failed");
		}
		if(file1_check && file2_check && file3_check && file4_check){
			System.out.println("All Test Cases Passed");
		}
	}
}

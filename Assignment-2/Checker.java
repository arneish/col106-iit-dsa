import java.util.*;
import java.io.*;
import java.security.SecureRandom;

public class Checker{
	public static void main(String[] args){
		String test_case_file = args[0];
		String student_out_file = args[1];
		try{
			FileInputStream fstream = new FileInputStream(test_case_file);
			Scanner s_test = new Scanner(fstream);
			FileInputStream fstream2 = new FileInputStream(student_out_file);
			Scanner o_test = new Scanner(fstream2);
			int num_employees = s_test.nextInt();
			String firstEmployee = s_test.next();
			String ceo_name = s_test.next();
        	Tree<String> myTree = new Tree<String>(ceo_name);
			myTree.insert(firstEmployee, ceo_name);
			for(int i=1; i<(num_employees-1); i++){
				String employee1 = s_test.next();
				String employee2 = s_test.next();
				myTree.insert(employee1, employee2);
			}
			int num_testcases = s_test.nextInt();
			for(int i=0;i<num_testcases; i++){
				int query_type = s_test.nextInt();
				if(query_type==0){
					String employee1 = s_test.next();
					String employee2 = s_test.next();
					myTree.insert(employee1, employee2);		
					continue;
				}
				if(query_type==1){
					String employee1 = s_test.next();
					String employee2 = s_test.next();
					myTree.delete_node(employee1, employee2);
					continue;
				}
				if(query_type==2){
					String employee1 = s_test.next();
					String employee2 = s_test.next();
					String boss = myTree.lowest_common_boss(employee1, employee2);
					String boss_out = o_test.next();
					if(!boss.equals(boss_out)){
						System.out.println("Boss name incorrect for testcase number" + Integer.toString(i));
						System.exit(0);
					}
					continue;	
				}
				HashMap<String, Integer> level_map = new HashMap<String, Integer>();
				myTree.level_map_build(myTree.root_node, level_map, 0);
				int total_employees = level_map.size();
				int prev_level = -1;
				for(int j=0; j<total_employees; j++){
					try{
						String emp_name = o_test.next();
						if(level_map.containsKey(emp_name)){
							if(level_map.get(emp_name) >= prev_level){
								prev_level = level_map.get(emp_name);
							}
							else{
								System.out.println("Test Case failed");
								// System.out.println("The employee name " + emp_name+ " has wrong level for testcase number " + Integer.toString(i));
								System.exit(0);	
							}
						}
						else{
							System.out.println("Test Case failed");
							// System.out.println("The employee name " + emp_name+ " is not a valid employee for testcase number " + Integer.toString(i));
							System.exit(0);
						}
					}
					catch(Exception e){
						System.out.println("Test Case failed");
						// System.out.println("Some employee(s) is(are) missing in testcase number " + Integer.toString(i));
						System.exit(0);
					}
				}
			}
		}
		catch(Exception e){
			System.out.println("input output files not found");
			System.exit(0);
		}
		System.out.println("Test Case Passed");
	}
}

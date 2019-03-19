import java.io.*;
import java.util.*;
import javafx.util.*;
public class Checker{
	public static void main(String[] args) throws FileNotFoundException{
		BestFit bfobject = new BestFit();
		String input_file_name = args[0];
		String output_file_name = args[1];

		File file = new File(input_file_name);
		Scanner s1 = new Scanner(file);
		try{
			PrintWriter writer = new PrintWriter(output_file_name, "UTF-8");;
			String temp;
			while(s1.hasNextLine())
			{
				temp=s1.nextLine();
				switch (Integer.valueOf(temp.substring(0,1)))
				{
					case 1: String[] query0 = temp.split(" ",3); 
						bfobject.add_bin(Integer.valueOf(query0[1]),Integer.valueOf(query0[2]));
						break;
					case 2: String[] query1 = temp.split(" ",3);
						writer.println(bfobject.add_object(Integer.valueOf(query1[1]),Integer.valueOf(query1[2])));
						break;
					case 3: String[] query2 = temp.split(" ",2);
						writer.println(bfobject.delete_object(Integer.valueOf(query2[1])));
						break;
					case 4: String[] query3 = temp.split(" ",2); 
						List<Pair<Integer,Integer>> x = bfobject.contents(Integer.valueOf(query3[1]));
						if(x == null){
							break;
						}
						int s=x.size();
						for (int i=0;i<s;i++)
							writer.println(x.get(i).getKey() + " " + x.get(i).getValue()); 
						break;
				}
			}	
			writer.close();
		}
		catch(Exception e){e.printStackTrace();}
		System.out.println("End of Checker");
	}
}

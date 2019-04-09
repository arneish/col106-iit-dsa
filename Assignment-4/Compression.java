import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import sun.java2d.pipe.BufferedBufImgOps;

public class Compression{
    public static byte[] array_of_int(int x){
        byte[] result = new byte[2];
        result[0] = (byte) (x >> 8);
        result[1] = (byte) (x >> 0);
        return result;
    }
    public static int int_of_array(byte[] b){
        int result = 0;
        int a1 = ((int) (b[0])) & 0xff;
        int b1 = ((int) b[1]) & 0xff;
        result += a1 << 8;
        result += b1;
        return result;
    }

    public static String read_string_from_file(String filename) throws IOException { 
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content;
    }

    public static void decompress(String input_file_name, String output_file_name){
        try{
            FileOutputStream os = new FileOutputStream(output_file_name);
            PrintWriter p = new PrintWriter(os);
            HashMap<Integer,String> dictionary = new HashMap<>();
            FileInputStream is = new FileInputStream(input_file_name);
            int count = 0;
            for(int i = 0; i < 128; i++){
                char c = (char)i;
                dictionary.put(count,Character.toString(c));
                count++;
            }
            byte[] next_two_bytes = new byte[2];
            if(is.read(next_two_bytes) <= 0){
                System.out.println("Exiting");
                p.close();
                is.close();
                return;
            }
            while(true){
                String to_add = dictionary.get(int_of_array(next_two_bytes));
                // System.out.print(to_add);
                p.write(to_add);
                if(is.read(next_two_bytes) <= 0){
                    break;
                }
                if(count < 65536){
                    if(dictionary.containsKey(int_of_array(next_two_bytes))){
                        to_add = to_add + dictionary.get(int_of_array(next_two_bytes)).substring(0,1);
                        dictionary.put(count,to_add);
                        count++;
                    }else{
                        to_add = to_add + to_add.substring(0,1);
                        dictionary.put(count,to_add);
                        count++;
                    }
                }
            }
            p.close();
            is.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.print("Decompressed");
    }

    public static void main(String args[]){
        try{
            String tobecompressed = read_string_from_file(args[0]);
            HashMap<String,byte[]> dictionary = new HashMap<>();
            List<byte[]> result = new LinkedList<>();
            int count = 0;
            for(int i = 0; i < 128; i++){
                char c = (char)i;
                dictionary.put(Character.toString(c),array_of_int(count));
                count++;
            }
            int compressed_till = 0;
            int length = tobecompressed.length();
            while(true){
                int end_index = compressed_till + 1;
                while(dictionary.containsKey(tobecompressed.substring(compressed_till, end_index))){
                    end_index++;
                    if(end_index > length){
                        result.add(dictionary.get(tobecompressed.substring(compressed_till, length)));
                        break;
                    }
                }
                if(end_index > length){
                    System.out.println("End");
                    break;
                }
                // System.out.print(tobecompressed.substring(compressed_till, length));
                result.add(dictionary.get(tobecompressed.substring(compressed_till,end_index-1)));
                if(count < 65536){
                    // System.out.println(tobecompressed.substring(compressed_till,end_index));
                    dictionary.put(tobecompressed.substring(compressed_till,end_index),array_of_int(count));
                    count++;
                }
                compressed_till = end_index - 1;
            }
            try{
                OutputStream os = new FileOutputStream("output_"+args[0]);
                Iterator<byte[]> itr = result.iterator();
                while(itr.hasNext()){
                    os.write(itr.next());
                }
                os.close();
            }
            catch(Exception e){}
        }catch(Exception e){
            e.printStackTrace();
        }
        decompress("output_"+args[0], "decompressed_output_"+args[0]);
        }
}

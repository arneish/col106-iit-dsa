public class intro implements Runnable {
	
	public void run(){
	   System.out.println("Thread says Hello World!");
	}
	
	public static void main(String args[]){
		intro thread = new intro();
		Thread tobj= new Thread(thread);
		tobj.start();
	}
		
}

class Xcord extends Thread {
	double x,sqx;
	public Xcord(double xval){
	 x=xval;
	}
	public void run(){
		System.out.println("Calculating square of "+x);
		sqx=x*x;
		System.out.println("Calculated square of "+sqx);
	}
}

class Ycord extends Thread {
	double y,sqy;
	public Ycord(double yval){
	 y=yval;
	}
	public void run(){
		System.out.println("Calculating square of "+y);
		sqy=y*y;
		System.out.println("Calculated square of "+sqy);
	}
}
public class intro{
 public static void main(String args[]){
    Xcord a1= new Xcord(5);
    Ycord b1= new Ycord(2);
    a1.start();
    b1.start();
    try{   // wait for completion of all thread and then sum
        a1.join();
        b1.join(); 
        double r =a1.sqx+b1.sqy;
        System.out.println("Radius square of circle with center at origin is "+r);
    } 
    catch(InterruptedException IntExp) {}
 }
}

public class intro {
	public void test (){
		System.out.println( " Function : test with no parameter " );
	}
	
	public void test ( int a ){
		System.out.println( " Function : test with one parameter " );
	}

	public static void main ( String args [])
	{
		intro fun = new intro ();
		fun.test ();
		fun.test (3);
	}
}

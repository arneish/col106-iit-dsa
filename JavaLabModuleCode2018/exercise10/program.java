public class program
{
	public boolean test(int a, int b)
	{
		/*
		Exercise 10: Same last digit- Given two non-negative integers, return true if they
		have the same last digit, such as with 27 and 57. Note that the % ”mod” operator
		computes remainder, so 17%10 is 7.
		*/
		int lastdigit_a = a%10;
		int lastdigit_b = b%10;
		if (lastdigit_a==lastdigit_b)
			return true;
			return false;
	}
}

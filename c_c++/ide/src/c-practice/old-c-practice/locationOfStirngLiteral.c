#include <stdio.h>

char * stringLiterals();
void disturb();

int main(int argc, char const *argv[])
{
	char *a = "This is a string";
	/*If you declare a local array, then space is made 
	on the stack for each element of that array, 
	and the string literal (which is stored in the executable) is copied to that space in the stack.*/
	
//	char a[] = "This is a string";

	char *b = "new string";

	a[2] = b[1];//error  : When you write char *a = "This is a string", 
	            //the location of "This is a string" is in the executable, 
	            //and the location a points to, is in the executable. The data in the executable image is read-only.
	return 0;
}

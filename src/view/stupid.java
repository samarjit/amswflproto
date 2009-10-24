package view;

import java.util.ArrayList;
import java.util.Arrays;

class Var{
private int p=0;
public Var(int x){
	p=x;
}

public int getP(){
	return p;
}

public void incr(){
	p=p+1;
}
}

 
public class stupid {
public void func(Var s){
	s = new Var(2);//
	System.out.println(s.getP());
	s.incr();
	System.out.println(s.getP());
}

public void manipl(){
	Var y =null;// new Var(2);
	func(y);
	//System.out.println(y.getP());
}
	
	public static void main(String[] args) {

		 stupid s = new stupid();
		 s.manipl();
	}

}

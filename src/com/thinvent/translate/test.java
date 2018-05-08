package com.thinvent.translate;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    
		Translation a =new Translation("192.168.65.41:1517","en","zh");
		System.out.println(a.doJob("His eighth book came out earlier this year and was an instant best-seller 小雨 "));
		//System.out.println(a.doJob("hello"));
	    //System.out.println("hello");
		
	}

}

package com.example.demo.algorithm;

import java.math.*;
import java.util.HashMap;
public class BigDecimalMasking implements DataMasking<BigDecimal>{

	public BigDecimal maskingfun(int val,BigDecimal b)
	{
		String s=b.toString();
	     
	     HashMap<Character,Character> map=new HashMap<>();
			HashMap<Character,Character> revmap=new HashMap<>();
			String alphabets=" AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz4612357980@/-.+,";
			String maskedalphabets=" PpXxYyGgAaMmRrTtBbLlDdQqFfCcWwHhIiSsEeZzVvOoJjKkNnUu2136458970@/-.+,";
	       
	       
			for(int i=0;i<68;i++)
			{
				map.put(alphabets.charAt(i),maskedalphabets.charAt(i));
	            revmap.put(maskedalphabets.charAt(i),alphabets.charAt(i));
			}
			int k=s.indexOf('@',0);
			String sub="";
			int index;
			if(k!=-1)
			{
				index=k;
				 sub=s.substring(k,s.length());
			}
			else
			{
				index=s.length();
			}
			
			 String result="";
			if(val==0)
			{
	             
	             for(int i=0;i<index;i++)
	             {
	             	result=result+map.get(s.charAt(i));
	             	
	             }
			}
			else
			{
	           for(int i=0;i<index;i++)
	           {
	              result=result+revmap.get(s.charAt(i));
	             
	           }
			}
			result=result+sub;
	          BigDecimal maskeddecimal=new BigDecimal(result);
	          return maskeddecimal;
	}
}

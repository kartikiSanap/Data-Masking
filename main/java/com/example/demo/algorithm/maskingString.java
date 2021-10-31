package com.example.demo.algorithm;
import java.util.*;
public class maskingString {


	  
	// This function generates the key in 
	// a cyclic manner until it's length isi'nt 
	// equal to the length of original text 
	public String generateKey(String str, String key) 
	{ 
	    int x = key.length(); 
	    if(x>=str.length())
	    {
	    	return key.substring(0,str.length());
	    }
	  
	    for (int i = 0; ; i++) 
	    { 
	        if (x == i) 
	            i = 0; 
	        if (key.length() == str.length()) 
	            break; 
	        key+=(key.charAt(i)); 
	    } 
	    return key; 
	} 
	  
	// This function returns the encrypted text 
	// generated with the help of the key 
	public String cipherText(String str, String key) 
	{ 
	    String cipher_text=""; 
	  
	    for (int i = 0; i < str.length(); i++) 
	    { 
	        // converting in range 0-25 
	        int x = (str.charAt(i) + key.charAt(i)) %26; 
	  
	        // convert into alphabets(ASCII) 
	        x += 'A'; 
	  
	        cipher_text+=(char)(x); 
	    } 
	    return cipher_text; 
	} 
	  
	// This function decrypts the encrypted text 
	// and returns the original text 
	public String originalText(String cipher_text, String key) 
	{ 
	    String orig_text=""; 
	  
	    for (int i = 0 ; i < cipher_text.length() &&  
	                            i < key.length(); i++) 
	    { 
	        // converting in range 0-25 
	        int x = (cipher_text.charAt(i) -  
	                    key.charAt(i) + 26) %26; 
	  
	        // convert into alphabets(ASCII) 
	        x += 'A'; 
	        orig_text+=(char)(x); 
	    } 
	    return orig_text; 
	} 
	  
	// Driver code 
	public  String  mainOperation(String str, String keyword, boolean mask)  
	{ 
	    
		ArrayList<String> l = new ArrayList<String>();
		int n = str.length();
		String newstr = "";
		for(int i=0; i<n; i++)
		{
			if((str.charAt(i)>='a'&&str.charAt(i)<='z')||(str.charAt(i)>='A'&&str.charAt(i)<='Z'))
			{
				newstr+=str.charAt(i);
			}
			else
			{
				l.add(""+i+"#"+str.charAt(i));
			}
		}
		boolean small[] = new boolean[newstr.length()];
		for(int i=0; i< small.length; i++)
		{
			if(newstr.charAt(i)>='a'&&newstr.charAt(i)<='z')
			{
				small[i] = true;
			}
			else
			{
				small[i] =false;
			}
		}
		newstr = newstr.toUpperCase();
		
		
	    String temp = "";
	    //System.out.println(keyword);
	    for(int i=0; i< keyword.length(); i++)
	    {
	    	temp += (char)('A'+(keyword.charAt(i)-'0'));
	    	//System.out.println(('A'+(keyword.charAt(i)-'0')));
	    	
	    }
	   // System.out.println(temp);
	    keyword = temp;
	  
	    String key = generateKey(str, keyword); 
	    
	    
	    
	    if(!mask)
	    {
	    	String cipher_text = cipherText(newstr, key); 
	    	
	    	String masked="";
	    	for(int i=0; i<cipher_text.length(); i++)
	    	{
	    		if(small[i]){masked += (char)(cipher_text.charAt(i)-'A'+'a');}
	    		else {masked+=cipher_text.charAt(i);}
	    	}
	    	int prev = 0;
	    	
	    	String fmasked ="";
	    	for(int i=0; i<l.size(); i++)
	    	{
	    		String curr = l.get(i);
	    		int index = Integer.parseInt(curr.split("#")[0]);
	    		char c = curr.split("#")[1].charAt(0);
	    		
	    		fmasked +=masked.substring(prev,index-i)+c;
	    		prev = index-i;
	    		
	    		
	    	}
	    	fmasked +=masked.substring(prev,masked.length());
	    	
	    	
	    	//System.out.println(cipher_text.substring(0,1)+cipher_text.substring(1,n).toLowerCase());
	    	return fmasked;
	    	
	    }
	    else
	    {
	    	String uncipher_text = originalText(newstr, key); 
	    	//System.out.println(uncipher_text);
	    	//System.out.println(uncipher_text.substring(0,1)+uncipher_text.substring(1,n).toLowerCase());
	    	
	    	//System.out.println(cipher_text);
	    	String unmasked="";
	    	for(int i=0; i<uncipher_text.length(); i++)
	    	{
	    		if(small[i]){unmasked += (char)(uncipher_text.charAt(i)-'A'+'a');}
	    		else {unmasked+=uncipher_text.charAt(i);}

	    	}
	    	int prev = 0;

	    	String funmasked ="";
	    	for(int i=0; i<l.size(); i++)
	    	{
	    		String curr = l.get(i);
	    		int index = Integer.parseInt(curr.split("#")[0]);
	    		char c = curr.split("#")[1].charAt(0);
	    		
	    		funmasked +=unmasked.substring(prev,index-i)+c;
	    		prev = index-i;
	    		
	    		
	    	}
	    	funmasked +=unmasked.substring(prev,unmasked.length());
	    	
	    	return funmasked;
	    }
	    
	  
	    
	    } 
	 
}

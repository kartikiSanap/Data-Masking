package com.example.demo.algorithm;

public class maskingNumber {

	 String generateKey(String str, String key) 
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
	 static String cipherText(String str, String key) 
	 { 
	     String masked_text=""; 
	   
	     for (int i = 0; i < str.length(); i++) 
	     { 
	         // converting in range 0-10
	         int x = (str.charAt(i)-'0' + key.charAt(i)-'0') %10; 
	   
	        
	         
	   
	         masked_text+=(x); 
	     } 
	     return masked_text; 
	 } 
	   
	 // This function unmasks the masked text 
	 // and returns the original text 
	 static String originalText(String masked_text, String key) 
	 { 
	     String orig_text=""; 
	   
	     for (int i = 0 ; i < masked_text.length() &&  
	                             i < key.length(); i++) 
	     { 
	         // converting in range 0-10
	         int x = (masked_text.charAt(i)- 
	                     key.charAt(i) + 10) %10; 
	   
	         
	         orig_text+=(x); 
	     } 
	     return orig_text; 
	 } 
	  
	// Driver code 
	public  String mainOperation(String str, String keyword, boolean mask)  
	{ 
	   
	  
	    
	    int f = Integer.parseInt(str.substring(0,1));
	    int n = str.length();
	    String key = generateKey(str.substring(1,n), keyword); 
	    if(!mask)
	    {
	    
	    	
	    	
	    	if(f==1)
	    	{
	    		f=1;
	    	}
	    	else if(f%2 == 1)
	    	{
	    		f = f-1;
	    	}
	    	else
	    	{
	    		f+=1;
	    	}
	    
	    	String cipher_text = cipherText(str.substring(1,n), key); 
	    	return ""+f+cipher_text;
	    
	    }
	    else {
	    	if(f==1)
	    	{
	    		f=1;
	    	}
	    	else if(f%2==0)
	    	{
	    		f=f+1;
	    	}
	    	else
	    	{
	    		f=f-1;
	    	}
	    	String  unmasked= originalText(str.substring(1,n), key); 
	    	
	    	return ""+f+unmasked;
	    	
	    }
	} 
}


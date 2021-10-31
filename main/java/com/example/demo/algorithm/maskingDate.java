package com.example.demo.algorithm;

import java.util.*;
import java.io.*;
public class maskingDate  
{ 
  
// This function generates the key in 
// a cyclic manner until it's length isi'nt 
// equal to the length of original text 

 String generateKey( String key) 
{ 
    int x = 8; 
  
    for (int i = 0; ; i++) 
    { 
        if (x == i) 
            i = 0; 
        if (key.length() >= 8) 
            break; 
        key+=(key.charAt(i)); 
    } 
    return key; 
} 
  
// This function returns the masked text 
// generated with the help of the key 
 int maskedText(int a,int b, int c) 
{   
    int temp = (a+b)%c;
    /*System.out.println(a+b+"%"+c+"="+temp);
*/
    return (a+b)%c; 
}

  
// This function unmasks the masked text 
// and returns the original text 

 int unmaskedText(int a,int b, int c)
{
    
    int t=a-b;
    while(t<0)
    {
        t+=c;
    }
    int ans=t%c;
    //System.out.println(a-b+"  "+t+"%"+c+"="+ans);
    
    
    return ans;
} 

// this function returns index of the month in the array
 int Index(int mon, int arr[])
{
    int i=0;
    for(i=0;i<arr.length; i++)
    {
        if(mon==arr[i])
        {
            return i;
        }
    }
    return -1;
}

//This function does masking  .s1 or arr1 contains months which have 31 days AND s2 or arr2 contains months which have 30 days
 String maskedDate(Date date,String keyword,HashSet<Integer> s1,int arr1[],HashSet<Integer> s2, int arr2[])
{
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int mon = calendar.get(Calendar.MONTH);
    /*System.out.println("Masking");
    System.out.println(year+"/"+mon+"/"+day);*/
    int c=0;
    
    for(int i = 0; i<8; i++)
    {
         c += Integer.parseInt(""+keyword.charAt(i));
    }
    c=c%4;
    year=year+4*c;    //Considering leap years too
    
    if(s1.contains(mon))
    {   
        int monIndex = Index(mon,arr1);
        monIndex = maskedText(monIndex,Integer.parseInt(keyword.substring(0,4)), arr1.length);
        mon = arr1[monIndex];
        day=maskedText(day,Integer.parseInt(keyword.substring(4,8)), 31);
        if(day==0)
        {
            day=31;
        }


    }
    else if(s2.contains(mon))
    {   
        int monIndex = Index(mon,arr2);
        monIndex = maskedText(monIndex,Integer.parseInt(keyword.substring(0,4)), arr2.length);
        mon = arr2[monIndex];
        day=maskedText(day,Integer.parseInt(keyword.substring(4,8)), 30);
        if(day==0)
        {
            day=30;
        }


    }
    else       //for february
    {   
        if(year%4!=0)
        {
            day=maskedText(day,Integer.parseInt(keyword.substring(4,8)), 28);
        }
        else{
            day=maskedText(day,Integer.parseInt(keyword.substring(4,8)), 29);
        }
    }
    
    
    //System.out.println(year+"/"+mon+"/"+day);

    mon+=1;
   // Date temp = new Date(year-1900,mon, day); 
    /*System.out.println(temp);
    System.out.println();*/
    return  year+"-"+mon+"-"+day;


}


//This function does masking  s1 or arr1 contains months which have 31 days AND s2 or arr2 contains months which have 30 days
 String unmaskedDate(Date date,String keyword,HashSet<Integer> s1,int arr1[],HashSet<Integer> s2, int arr2[])
{
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int mon = calendar.get(Calendar.MONTH);
    
    /*System.out.println("unMasking");
    System.out.println(year+"/"+mon+"/"+day);*/
    int c=0;
    
    for(int i = 0; i<8; i++)
    {
         c += Integer.parseInt(""+keyword.charAt(i));
    }
    c=c%4;
    year=year-4*c;
    if(s1.contains(mon))
    {   
        int monIndex = Index(mon,arr1);
        monIndex = unmaskedText(monIndex,Integer.parseInt(keyword.substring(0,4)), arr1.length);
        mon = arr1[monIndex];
        day=unmaskedText(day,Integer.parseInt(keyword.substring(4,8)), 31);
        if(day==0)
        {
            day=31;
        }

    }
    else if(s2.contains(mon))
    {   
        int monIndex = Index(mon,arr2);
        monIndex = unmaskedText(monIndex,Integer.parseInt(keyword.substring(0,4)), arr2.length);
        mon = arr2[monIndex];
        day=unmaskedText(day,Integer.parseInt(keyword.substring(4,8)), 30);
        if(day==0)
        {
            day=30;
        }

    }
    else 
    {
        if(year%4!=0)
        {
            day=unmaskedText(day,Integer.parseInt(keyword.substring(4,8)), 28);
            if(day==0)
        {
            day=28;
        }
        }
        else{
            day=unmaskedText(day,Integer.parseInt(keyword.substring(4,8)), 29);
            if(day==0)
        {
            day=29;
        }
        }
    }

    
    
    //System.out.println(year+"/"+mon+"/"+day);
    //Date temp = new Date(year-1900,mon, day);
    /*System.out.println(temp);
    System.out.println();*/
    mon+=1;
    return  year+"-"+mon+"-"+day;

}
  
// Driver code 
public  String mainOperation(String temp, String keyword, boolean mask) throws Exception  
{   
	
    
    String temp1[] = temp.split("-");

   // System.out.println("Enter key");
    //String keyword = in.readLine();
    int y = Integer.parseInt(temp1[0]);
    int m = Integer.parseInt(temp1[1])-1;
    int d = Integer.parseInt(temp1[2]); 
    //System.out.println(y+"   "+ m+"  "+d);
    String key = generateKey(keyword); 
    Date date = new Date(y-1900,m,d);                   //-1900 to get correct year of date
    
   /* System.out.println(date);
    System.out.println();
    System.out.println();
*/
    //s1 or arr1 contains months which have 31 days AND s2 or arr2 contains months which have 30 days

    HashSet<Integer> s1 = new HashSet<Integer>();
    HashSet<Integer> s2 = new HashSet<Integer>();
    s1.add(0);s1.add(2);s1.add(4);s1.add(6);s1.add(7);s1.add(9);s1.add(11);
    s2.add(3);s2.add(5);s2.add(8);s2.add(10);

    int arr1[] = {0,2,4,6,7,9,11};
    int arr2[] = {3,5,8,10};
    if(!mask)
    {
    	//Date maskedDate = maskedDate(date,key,s1,arr1,s2,arr2);
    	return maskedDate(date,key,s1,arr1,s2,arr2);
    
    }
    else
    {
    //Date unmaskedDate = unmaskedDate(date,key,s1,arr1,s2,arr2);
    return unmaskedDate(date,key,s1,arr1,s2,arr2);
    }


    //String d = ""+ name.substring(0,2) + name.substring(3,5)+ name.substring(6,10);
    
    
    /*
    System.out.println("Original                :"+date);
    System.out.println("Masked                  :"+maskedDate);
    System.out.println("Unmasked                :"+unmaskedDate); 
  
    */
    
        
    } 
} 

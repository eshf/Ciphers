import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class SubstitutionCipher {


//create HashMap for encryption
public static ArrayList<Character> createHashMap(String keyword)
{

    String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    LinkedHashSet<Character> map= new LinkedHashSet<Character>(); //creating HashMap
    for (int i = 0; i < keyword.length(); i++) 
    {
    	map.add(keyword.charAt(i));
    }
    for (int j = 0; j < a.length(); j++) 
    {
    	map.add(a.charAt(j));
    }
    
    ArrayList<Character> hashlist = new ArrayList<Character>();
    hashlist.addAll(map);
    
    return hashlist;
}


//create HashSet for decryption
public static ArrayList<Character> createDecryptedMap(String keyword)
{

    String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    LinkedHashSet<Character> map= new LinkedHashSet<Character>();
    for (int i = 0; i < keyword.length(); i++) 
    {
    	map.add(keyword.charAt(i));
    }
    for (int j = 0; j < a.length(); j++) 
    {
    	map.add(a.charAt(j));
    }
    
    //keywordalpha list
    ArrayList<Character> hashlist = new ArrayList<Character>();
    hashlist.addAll(map);
    
    //alphabet list
    ArrayList<Character> alist = new ArrayList<Character>();
    for(int letter = 0; letter < a.length(); letter++)
    {
    	alist.add(a.charAt(letter));
    }
 
    
    ArrayList<Character> officialmap = new ArrayList<Character>();
    for (int k = 0; k < 26; k++)
    {
    	officialmap.add('a');
    }
    for (int l = 0; l < 26; l++)
    {
    	int hashno = (int)hashlist.get(l) - 65;
    	officialmap.set(hashno, alist.get(l));
    }
    
    
   return officialmap;

}


/*
//remove duplicates and add remaining letters to the string
public static String createDecryptedString(String str) {
	
	Set<Character> chars = new HashSet<>(); 
    StringBuilder output = new StringBuilder(str.length());   
    for (int i = 0; i < str.length(); i++) { 
        char ch = str.charAt(i); 
        if (chars.add(ch)) { 
            output.append(ch); 
            } 
        }  
        char s[]=output.toString().toCharArray();
        int c[]=new int[26];
        for(int i=0;i<s.length;i++){
            if(s[i]!=' '){
                c[s[i]-'A']++;
            }
        }
        String s2="";
        for(int i=0;i<26;i++){
            if(c[i]==0){
                s2=s2+(char)(i+'A');
            }
        }
        return str + s2;
        
    }
*/

public static void encryptPlainText(String keyword, String inputFile, String outputFile) throws FileNotFoundException
{
   
	ArrayList<Character> hashlist = new ArrayList<Character>();
	hashlist = SubstitutionCipher.createHashMap(keyword);
	//System.out.println(hashlist);
	//String encryptedString = SubstitutionCipher.createEncryptedString(keyword);
	//HashMap<String, String> hm= SubstitutionCipher.createHashMap(encryptedString);
	
	//take in the message file as input(in assignment specification)
	//encryptPlaintext method takes in inputFile as parameter and called in command-line as args[2], output file as args[3]
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    StringBuilder stringBuilder = new StringBuilder();
    String line = null;
    String ls = System.getProperty("line.separator");
    try {
		while ((line = reader.readLine()) != null) 
		{
			if(line.trim().length() > 0) 
			{
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // delete the last new line separator
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    try {
		reader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    //accept uppercase characters
    String cipher = stringBuilder.toString().toUpperCase();
    System.out.println("Plain Text is:"+ cipher);
    
    String encrypted = "";
    
    char [] plaintext = cipher.toCharArray();
    for(int i = 0; i < plaintext.length; i++)
    {
    	int alphabet = (int) plaintext[i];
    	if(alphabet <= 90 && alphabet >= 65)
		{
    		alphabet = alphabet - 65;
			alphabet = hashlist.get(alphabet);
			plaintext[i] = (char) alphabet;
		}
    }
    cipher = String.copyValueOf(plaintext);
  
    System.out.println("Encrypted text is:" + cipher);
    //write file 
	try
	{
        //output(write) CipherText as a file (in assignment specification)
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		 for (int i = 0; i < cipher.length(); i++) 
		 {
            writer.write(cipher.substring(i, i+1));
        }
        writer.close();
		 }
	catch (IOException ex) 
	{
		System.out.println("Error: File cannot be output."); 
	}
	
	System.out.println("File has been encrypted.");	
    
    }


public static void decryptCipherText(String keyword, String inputFile, String outputFile) throws FileNotFoundException
{
	
	ArrayList<Character> alphalist = new ArrayList<Character>();
	alphalist = SubstitutionCipher.createDecryptedMap(keyword);
	System.out.println(alphalist);
	//HashMap<String, String> hm= SubstitutionCipher.createHashMap(decryptedString);
	//take in the ciphertext file as input(in assignment specification)
	//decryptPlaintext method takes in inputFile as parameter and called in command-line as args[2], output file as args[3]
	BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    StringBuilder stringBuilder = new StringBuilder();
    String line = null;
    String ls = System.getProperty("line.separator");
    try {
		while ((line = reader.readLine()) != null) 
		{
			if(line.trim().length() > 0) 
			{
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // delete the last new line separator
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    try {
		reader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    //accept uppercase characters
    String cipher = stringBuilder.toString().toUpperCase();
    System.out.println("Encrypted Text is:"+ cipher);
    
    String encrypted = "";
    
    char [] plaintext = cipher.toCharArray();
    for(int i = 0; i < plaintext.length; i++)
    {
    	int dealphabet = (int) plaintext[i];
    	if(dealphabet <= 90 && dealphabet >= 65)
		{
    		dealphabet = dealphabet - 65;
    		dealphabet = alphalist.get(dealphabet);
    		plaintext[i] = (char) dealphabet;
		}
    }
    cipher = String.copyValueOf(plaintext);
  
    System.out.println("Decrypted text is:" + cipher);
    //write file 
	try
	{
        //output(write) CipherText as a file (in assignment specification)
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		 for (int i = 0; i < cipher.length(); i++) {
            writer.write(cipher.substring(i, i+1));
        }
        writer.close();
		 }
	catch (IOException ex) 
	{
		System.out.println("Error: File cannot be output."); 
	}
	
	System.out.println("File has been decrypted.");	
    
    }

public static void main(String[] args) throws FileNotFoundException{
	
	System.out.println(args[1] + " " + args[2] + " " + args[3]);
	
	if(args.length!=4)
	{
		System.out.println("Invalid parameters entered.");
	}
	else
	{
		if (args[0].equalsIgnoreCase("-e")) 
		{
			SubstitutionCipher.encryptPlainText(args[1], args[2], args[3]);
		}
		else if(args[0].equalsIgnoreCase("-d"))
		{
			SubstitutionCipher.decryptCipherText(args[1], args[2], args[3]);
		
		}
	}   

}
}

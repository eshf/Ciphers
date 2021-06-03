import java.io.*;
import java.util.*;


public class Kamasutra
{

     //Generate Key Values 
   public static String keyfile(int len){    //Pass in 26 Alphabet letters 
    Random r = new Random();
    String key = "";
    for (int i = 0; i < len;) {
        char c = (char) (r.nextInt(26) + 'a');
        if(!key.toString().contains(""+c)){
           key = key + c;
           i++;
        }
    }      
    return key;
 }
 
 //Kamasutra Encryption & Decryption Algorithm 
 public static String KamaSutra(String text,String key)
 {
    int keyLen = key.length()/2;
    
    // arrange random key
    char[][] keyRow = new char[2][keyLen];    
    int count=0;
    for(int x=0;x<2;x++)
    {
       for(int y=0;y<keyLen;y++){
          keyRow[x][y] = key.charAt(count);
              //System.out.print(keyRow[x][y]+" ");
          count++;
       }
       //System.out.println();
    }
    
    String sb = "";
    
    count=0;
    for(int x=0;x<text.length();x++){
       for(int y=0;y<2;y++){
          for(int z=0;z<keyLen;z++){
             if(y == 0){
                if(text.charAt(x) == keyRow[y][z])
                   sb += keyRow[y+1][z];
             }
             else if (y == 1){
                if(text.charAt(x) == keyRow[y][z])
                   sb += keyRow[y-1][z];
             }
          }
       }
       if(text.charAt(x) == ' ')
          sb += text.charAt(x);
    }
  return sb;
 }

   public static void main(String[] args) throws IOException
   {
        if( args.length > 0)
        {
          for ( int i = 0; i< args.length; i++)
          {
              System.out.print(args[i]+ " "); 
          }
          System.out.println("\n");
         
        

          if ( args[0].equalsIgnoreCase("-k"))
          {
            try
            {
                File file = new File(args[1]);   //keyfile.txt
                if (file.createNewFile())
                {
                    System.out.println("Key File successfully created: " + file.getName());
                    
                //Write to "keyfile.txt"
                String key = keyfile(26);     //get key values
                FileWriter fout = new FileWriter(args[1],true);

                System.out.println("Key Values: ");     //Print out Key values
                System.out.println("=============");
                for ( int i=0; i< 13; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 1st Row key-values 
                }
                System.out.println("");
                for ( int i=13; i< 26; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 2nd Row key-values 
                }
                System.out.println("\n");
                //Write to "keyfile.txt"
                fout.write(key);
                fout.close();
            }
            else
            {
                System.out.println("Key File already exists.");
                System.out.println();
            }
            }
            catch(IOException e)
              {
                System.out.println("Error..");
                e.printStackTrace();
              }
            
        }//End of generate keyfile.txt 
          
      
   }
    else if( args.length < 0)
    {
        System.out.println("Enter below command lines to run the file!");
        System.out.println(" To Generate Key File: \"java Kamasutra -k keyfile.txt\" ");
        System.out.println(" Encryption: \"java Kamasutra -e keyfile.txt PText-1.txt Ctext-3.txt\" ");
        System.out.println(" Decryption : \"java Kamasutra -d keyfile.txt Ctext-3.txt Output.txt\" ");
    }

    if(args[0].equalsIgnoreCase("-e")){
        Kamasutra.EncryptCipher(args[1], args[2], args[3]); 
    }

    if (args[0].equalsIgnoreCase("-d")){
        Kamasutra.DecryptCipher(args[1], args[2], args[3]);
    }
        
}
public static void EncryptCipher(String fileWordKey, String PlaintextFile, String encryptCipherTextFile){
 //=======================Encryption-->Generate ciphertext.txt===============
 //"-e keyfile.txt PText-1.txt Ctext-3.txt"
  
     String key= "";
     //Read "keyfile.txt"
     try{
         File file1 = new File(fileWordKey);     //Read "keyfile.txt"
         Scanner keyInput = new Scanner(file1);
         while(keyInput.hasNextLine())
         {
             key = keyInput.nextLine();    
         }
         System.out.println("Key Values: ");   //Print out Key values
         System.out.println("=============");
         for ( int i=0; i< 13; i++)
         {
             System.out.print(key.charAt(i));  //Print out 1st Row key values 
         }
         System.out.println("");   
         for ( int i=13; i< 26; i++)
         {
             System.out.print(key.charAt(i));  //Print out 2nd Row key values 
         }
         System.out.println("\n");
        }
     catch(IOException e)
     {
         System.out.println("Error...");
         e.getStackTrace();
     }
     //String data;
     String cipher;
     //Read Plain Text
     try{ 
     //File file2 = new File("PText-1.txt");        //Read "PText-1.txt"
     //Scanner read = new Scanner(file2);
     //BufferedReader reader = new BufferedReader(new FileReader(inputFile));
     FileInputStream fin=new FileInputStream(PlaintextFile);
     FileOutputStream fon=new FileOutputStream(encryptCipherTextFile);
     BufferedReader br = new BufferedReader(new InputStreamReader(fin));
     BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fon));
     String strCurrentLine;
     while ((strCurrentLine = br.readLine()) != null) {
        System.out.println(strCurrentLine);
        cipher = KamaSutra(strCurrentLine, key);    //Encrypt the plain data
        System.out.println(cipher);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cipher).append(" "); 
        //Write to CText-3.txt
        FileWriter file = new FileWriter(encryptCipherTextFile);
        BufferedWriter Ciphout = new BufferedWriter(file);
        Ciphout.write(cipher+ " ");  
        Ciphout.close();

     }

     br.close();
    }
    catch(IOException e){
        System.out.println("Error...");
        e.getStackTrace();
    }
}
public static void DecryptCipher(String fileWordKey, String encryptCipherTextFile, String outputText){
     //==================Decryption=================================
 //-d keyfile.txt Ctext-3.txt Output.txt
  
    String key = "";
    //Read "keyfile.txt"
    try{
        File file1 = new File(fileWordKey);     //Read "keyfile.txt"
        Scanner keyInput = new Scanner(file1);
        while(keyInput.hasNextLine())
        {
            key = keyInput.nextLine();    
        }
        System.out.println("Key Values: ");   //Print out Key values
        System.out.println("=============");
        for ( int i=0; i< 13; i++)
        {
            System.out.print(key.charAt(i));  //Print out 1st Row key values 
        }
        System.out.println("");   
        for ( int i=13; i< 26; i++)
        {
            System.out.print(key.charAt(i));  //Print out 2nd Row key values 
        }
        System.out.println("\n");
       }
    catch(IOException e)
    {
        System.out.println("Error..");
        e.getStackTrace();
    }
    
    //Read CText-3.txt 
    //String Cdata;
    String plain;
    //File Cfile = new File(encryptCipherTextFile);        //Read "Ctext-3.txt"
    //Scanner readCtext = new Scanner(Cfile);
    try{
   //Create Output file 
    //File Outputfile = new File(outputText);
    FileInputStream fin=new FileInputStream(encryptCipherTextFile);
    FileOutputStream fon=new FileOutputStream(outputText);
    BufferedReader br = new BufferedReader(new InputStreamReader(fin));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fon));
    String strCurrentLine;
    while ((strCurrentLine = br.readLine()) != null) {
        System.out.println(strCurrentLine);
        plain = KamaSutra(strCurrentLine, key);    //Decrypt the Cipher text  
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(plain).append(" "); 
            
      //Write to CText-3.txt
      FileWriter file = new FileWriter(outputText);
      BufferedWriter Ciphout = new BufferedWriter(file);
      Ciphout.write(plain+ " ");  
      Ciphout.close();
    }
    br.close();
}
catch(IOException e){
    System.out.println("Error...");
    e.getStackTrace();
}
} 
}
    // if(Outputfile.createNewFile())
    // {
    //     System.out.println("Output text file was created: "+ Outputfile.getName());
    // }
    // else
    // {
    //     System.out.println("Output file \"Output.txt\" already exists!");
    // }
    // System.out.println("");
    
    // catch(IOException e){
    //   System.out.println("Error...");
    //   e.getStackTrace();
    // }
    // while(readCtext.hasNextLine())        
    // {
    //     Cdata = readCtext.nextLine();     
    //     plain = KamaSutra(Cdata, key);    //Decrypt the Cipher text  
    //     StringBuilder stringBuilder = new StringBuilder();
    //     stringBuilder.append(plain).append(" "); 
            
    //     //Write to Output.txt
    //     FileWriter fout = new FileWriter(outputText,true);
    //     fout.write(plain+ " "); 
    //     System.out.println(" ");
    //     fout.close();
    // } 
//     readCtext.close(); 
  

// }
// }
//     //File file = new File("CText-3.txt");
    //FileWriter fw = new FileWriter(file);
    //bw = new BufferedWriter(fw);
    
    // Creates a BufferedWriter
    // FileWriter file = new FileWriter(encryptCipherTextFile);
    // BufferedWriter Ciphout = new BufferedWriter(file);
    // Ciphout.write(file+ " ");  
    // Ciphout.close();
    // fon.write(cipher+ "");
    // fon.close();
    
    


    //  while((strCurrentLine = br.readLine()) != null)            //Read in PText-1 
    //  {
    //     System.out.println(strCurrentLine);   
    //     cipher = KamaSutra(strCurrentLine, key);    //Encrypt the plain data 
    //     StringBuilder stringBuilder = new StringBuilder();
    //     stringBuilder.append(cipher).append(" "); 
       
        //Create Ciphertext file
     
      
        //End of generating "CText-3".txt
    
     
     //bw.close();


        //       //Write to CText-3.txt
    //  FileWriter Ciphout = new FileWriter(encryptCipherTextFile,true);
    //      Ciphout.write(cipher+ " ");  
    //       Ciphout.close();
    //   } 
    //   read.close(); 
 



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VigenereCipher {
	private Scanner kb;

	private char character;
	//private char [] alphabet;
	private char [] letters;
	private float [] frequencies; 
	private String plaintext;
	private String ciphertext;
	private String key;
	private String keyword;
	private String encryptedText;
	
	
	//default no arg constructor
	public VigenereCipher()  {
		kb = new Scanner (System.in);
		
		character =' ';
		
		plaintext = ""; //jacksonheights
		keyword = ""; //grace
		
		ciphertext = "";
		key = "";
		
		//key = newKey(plaintext,keyword);
		
			try {
				init();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
	}
	
	//init sequence ask user
	private void init() throws IOException {
		
		while (true) {
			
			System.out.println("Vigenere Cipher Menu");
			System.out.print("(N)ewKey,(G)etKey,(E)ncrypt,(D)ecrypt,(M)ergeFiles,(O)penFile,(Q)uit");
			if(kb.hasNextLine()) {
			String ans = kb.nextLine();
			char response = Character.toUpperCase(ans.charAt(0)); //take first letter and take it to uppercase and store in response

			//String encryptedText = encrypt(str, key);
			//String decryptedText = decrypt(encryptedText,key);

			
			//System.out.println(key);
			//try {
			switch(response) {
			case 'N':
				System.out.println("Pleae type in your key word");
				keyword = kb.nextLine().toUpperCase();
				
				System.out.println("Do you want to enter your own (P)laintext or (O)pen corpus file?");
				String in = kb.nextLine();
				char inner = Character.toUpperCase(in.charAt(0));
				if (inner == 'P') {
					System.out.println("Please type in your plaintext");
					plaintext = kb.nextLine().toUpperCase();
				}else if (inner == 'O') {
					plaintext = openFile().toUpperCase();
				}else {
					System.out.println("Try again");
					quit();
				}
				
				plaintext = plaintext.replaceAll("\\P{L}+", "").toLowerCase();
				key = newKey(plaintext,keyword);
				
				
				
				ciphertext = encrypt(plaintext,getKey());
				
				System.out.println(ciphertext);
				letterFrequency(ciphertext);
				System.out.println("Key length :" +ciphertext.length());
				System.out.println("A new key has been generated");
				//System.out.println(key);
				
				
				
				break;
			case 'G':
				System.out.println(getKey());
				break;
			case 'E':
				ciphertext = encrypt(plaintext, key);
				System.out.println(ciphertext);
				break;
			case 'D':
				String decryptedText = decrypt(ciphertext,key);
				//decryptedText = decrypt(ciphertext, key);

				System.out.println(decryptedText);
				break;
			case 'M':
				mergeFiles();
				break;
			case 'O':
				openFile();
				break;
				
			case 'Q':
				quit();
				break;
				
			default:
				System.out.println("Not a valid answer, try again");
				break;
			}}
	
		}
	}
	
	//generate key
	private String newKey(String str, String key) { 
		
	    int x = str.length(); 
	  
	    for (int i = 0; ; i++) { 
	        if (x == i) 
	            i = 0; 
	        if (key.length() == str.length()) 
	            break; 
	        key+=(key.charAt(i)); 
	    } 
	    return key; 
	} 
	  
	
	private String getKey() {
		return key;	
	}
	
	private static String encrypt(String str, String key){ 
		
	    String cipher_text=""; 
	  
	    for (int i = 0; i < str.length(); i++) 
	    { 
	        // converting in range 0-25 
	        int x = (str.charAt(i) + key.charAt(i)) % 26; 
	  
	        x += 'A'; 
	  
	        cipher_text+=(char)(x); 
	        
	    } 
	    return cipher_text; 
	} 
	
	private static String decrypt(String cipher_text, String key) 	{ 
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
	
	private void letterFrequency (String w ) {
		String s = w.replaceAll("\\P{L}+", "").toLowerCase();
		Map<Character, Integer> d = new HashMap <Character, Integer>();
		System.out.println("Letter Frequency method");
		
        double percentage; 
        
        for(int i = 0; i < s.length(); i++) {
            if(d.containsKey(s.charAt(i)) && Character.isLetter(s.charAt(i)))  {
                d.put(s.charAt(i), d.get(s.charAt(i)) + 1);
            }
            else{
                d.put(s.charAt(i), 1);
            }    
        }
      
        double total = 0; 
        for (int i = 0; i < s.length(); i++) {
        	
            if(d.get(s.charAt(i)) != 0) {
            	
            	percentage = (d.get(s.charAt(i)) * 100)/(double)s.length();
                total+=percentage;
                System.out.print("Char: " + "'" + s.charAt(i) + "' | ");
                System.out.print("Count: " + d.get(s.charAt(i)) + " | " + "Percentage: " + percentage + "%");
                System.out.println();
                d.put(s.charAt(i), 0);
            }            
        }
        System.out.println(total);
    
    
	}
	
	
	private void mergeFiles() throws IOException {
	
		   String path = "/Users/gracelamalva/Downloads/text0";
		   File dir = new File(path); 
		   
	        // create obejct of PrintWriter for output file 
	        PrintWriter pw = new PrintWriter("output.txt"); 
	  
	        // Get list of all the files in form of String Array 
	        String[] fileNames = dir.list(); 
	 
	        for (String fileName : fileNames) { 
	            System.out.println("Reading from " + fileName); 
	          
	            File f = new File(dir, fileName); 
	          
	            BufferedReader br = new BufferedReader(new FileReader(f)); 
	            pw.println("Contents of file " + fileName); 
	  
	            // Read from current file 
	            
	            String line = br.readLine(); 
	            line = line.replaceAll("[^\\p{Alpha}]", " ");
	            while (line != null) { 
	  
	                // write to the output file 
	                pw.println(line); 
	                line = br.readLine();
	                
	                //System.out.println(line);
	                
	            } 
	            br.close();
	        	
	            pw.flush(); 
	        } 
	        System.out.println("Reading from all files" +  
	        " in directory " + dir.getName() + " Completed"); 
	        
	        pw.close();
		}
	
	private String openFile() throws IOException {
		String word = " ";
		String path = "/Users/gracelamalva/Desktop/output-s.txt";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			int theCharNum = reader.read();
			System.out.println("Please wait while we open the large file");
			while(theCharNum != -1) {
			    char theChar = (char) theCharNum;
			    word+=theChar;
			    System.out.print(theChar);

			    theCharNum = reader.read();
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("We have turned the chars to a string");
		
		plaintext = word.toUpperCase();
		letterFrequency(plaintext);
		
		//plaintext = plaintext.replaceAll("\\P{L}+", "").toLowerCase();
		
		//ciphertext = encrypt(plaintext,getKey());
		
		//System.out.println(ciphertext);
		//letterFrequency(ciphertext);
		
		return word;
			
	}
	
	private void quit() {
		System.out.println("You have successfully quit");
		System.exit(0);
	}
}


package kodowanie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

class Main {

	public static void main(String[] args) {
		Code s=new Code();
        String s2 = "";
		String Authors = "Krzysztof Barden 210139 ; Przemyslaw Fortuna 210172";
        int character;

        BufferedReader fileReader= null;
		PrintWriter zapis = null;
        String inputString = JOptionPane.showInputDialog(null, "Wpisz wiadomosc",Authors,JOptionPane.PLAIN_MESSAGE);

        PrintWriter saveMessage = null;

        try {
            saveMessage = new PrintWriter("wyslana.txt");}
        catch(FileNotFoundException e) {
            System.out.println("BLAD PRZY OTWIERANIU PLIKU!");
            System.exit(1);}

            saveMessage.print(inputString);

        try {
            saveMessage.close();}
        catch(Exception e) {
            System.out.println("BLADD PRZY ZAMYKANIU PLIKU!");
            System.exit(3);}

		try {
	 		fileReader = new BufferedReader(new FileReader("wyslana.txt"));
	 	    zapis = new PrintWriter("odebrana.txt");}
	 		catch(FileNotFoundException e) {
	 			System.out.println("BLADD PRZY OTWIERANIU PLIKU!");
	 			System.exit(1);}
		String wiadomosc="";
		try {
			wiadomosc = fileReader.readLine();}
		catch(IOException e) {
			System.out.println("BLADD PRZY WCZYTYWANIU Z PLIKU!");
 			System.exit(2);}

		try {
		    fileReader.close();}
			catch(IOException e) {
				System.out.println("BLADD PRZY ZAMYKANIU PLIKU!");
	 			System.exit(3);}

		
		for(int it = 0, n = wiadomosc.length(); it < n; it++)
	    {
			
				char w = wiadomosc.charAt(it);
				s.kodowanie(w);
				s.firstMessage += w;
				s.firstMessage += ": ";
				
				for(int i = 0; i < s.n; i++)
				{
					zapis.print(s.T[i]);
					if(i == s.n-1) zapis.println("");
					s.firstMessage += s.T[i];
									}
				s.firstMessage += "\n";
		
	    }
		zapis.close();
		JOptionPane.showMessageDialog(null,s.firstMessage,Authors,JOptionPane.PLAIN_MESSAGE);
		
		
		//DEKODOWANIE
		BufferedReader odczyt= null;

		try {
			odczyt = new BufferedReader(new FileReader("odebrana.txt"));}
	 		catch(FileNotFoundException e) {
	 			System.out.println("BLADD PRZY OTWIERANIU PLIKU!");
	 			System.exit(1);}
		
		String wiadomosc_in;

		PrintWriter zapiszchar = null;

		try {
			zapiszchar = new PrintWriter("OdebranaChar.txt");}
		catch(FileNotFoundException e) {
			System.out.println("BLAD PRZY OTWIERANIU PLIKU!");
			System.exit(1);}
		try {

			StringBuilder s2Builder = new StringBuilder();
			for(int i = 0; i < wiadomosc.length(); i++)
		    {
				wiadomosc_in = odczyt.readLine();
				character = Integer.parseInt(wiadomosc_in.substring(0,7),2);
                s2Builder.append((char) character);
                character = Integer.parseInt(wiadomosc_in.substring(8,15),2);
				s2Builder.append((char) character);

		        for(int j = 0; j < s.n; j++)
		        {
		        	int w = wiadomosc_in.charAt(j);
		            s.ASCII[j] = w-48;
		            s.secondMessage += s.ASCII[j];
		        }
		        for(int l = 0; l < s.rows; l++)
		            s.Decode[l] = 0;

		        s.secondMessage += " : ";
		        s.secondMessage += s.dekodowanie() ;
		        s.secondMessage +="\n";
		    }
			s2 = s2Builder.toString();
		}
		catch(IOException e) {
			System.out.println("BLAD PRZY WCZYTYWANIU Z PLIKU!");
 			System.exit(2);}
 			//Jako char
 			zapiszchar.print(s2);
	    zapiszchar.close();
		try {
		    odczyt.close();}
			catch(IOException e) {
				System.out.println("BLAD PRZY ZAMYKANIU PLIKU!");
	 			System.exit(3);}
		JOptionPane.showMessageDialog(null,s.secondMessage + "\n Jako char: \n" + s2,Authors,JOptionPane.PLAIN_MESSAGE);
		if(!s.errorMessage.isEmpty())
		    JOptionPane.showMessageDialog(null,s.errorMessage,Authors,JOptionPane.PLAIN_MESSAGE);

	}

	}



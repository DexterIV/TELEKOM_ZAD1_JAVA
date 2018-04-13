package kodowanie;

class Code {

	public final int rows = 8;
	public final int n = 16;
	private final int [][] H = {
            {1,1,1,0,0,1,1,1,1,0,0,0,0,0,0,0},
            {0,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0},
            {1,0,0,1,0,1,0,1,0,0,1,0,0,0,0,0},
            {1,1,1,0,1,0,0,1,0,0,0,1,0,0,0,0},
            {0,1,0,1,0,1,1,0,0,0,0,0,1,0,0,0},
            {1,0,1,0,1,0,1,0,0,0,0,0,0,1,0,0},
            {1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,0},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1}};
	public final int [] ASCII;
	public final int [] T;
	public final int [] Decode;
	private final int [] AC;
	public String secondMessage;
	public String firstMessage ="";
	public String errorMessage;
	private int zmienna = 0;

	public Code()
	{
		T=new int [n];
		ASCII = new int [n];
		Decode = new int [rows];
		AC = new int [rows];
		secondMessage ="";
		errorMessage ="";
	}
	

	private void mnozenie(int wektor[])
	{
	    for(int i = 0; i < rows; i++)
	    {
	        for(int j = 0; j < n; j++)
	        {
	            AC[i] += wektor[j] * H[i][j];
	        }
	        AC[i] %= 2;
	    }
	}
	private void mnozenie1(int wektor[])
	{
	    for(int i = 0; i < rows; i++)
	    {
	        for(int j = 0; j < n; j++)
	        {
	            Decode[i] += wektor[j] * H[i][j];
	        }
	        Decode[i] %= 2;
	    }
	}


	private void ASCIIbin(int znakASCII)
	{
		int tmp;
		for(int i = 7; i != 0; i--)
	    {
	        tmp = znakASCII%2;
	        T[i] = tmp;
	        znakASCII /= 2;

			if(i == 1) T[0] = 0; //najstarszy bit musi wynosic zero
	    }
	}


	private void ASCIIbinPL(int znakASCII)
	{
		int tmp;
		int ifOne = 0;
		for(int i = 7; i != 0; i--)
	    {
			tmp = znakASCII%2;
			if(ifOne == 0)
			{
				T[i] = tmp;
				if(tmp == 1) ifOne = 1;
			}
			else
			{
				if(tmp == 1) T[i] = 0;
				else T[i] = 1;
			}

			if(i == 1) T[0] = 1;

	        znakASCII /= 2;
	    }
	}
	public void kodowanie(char znak)
	{

		//przeksztalcenie na kod ASCII
		if((int) znak > 0) ASCIIbin((int) znak);
		else ASCIIbinPL((int) znak);

		//obliczanie sumy kontrolnej na podstawie kodu ASCII
	    mnozenie(T);

	    //uzupelnij tablice o bity kontrolne
	    for(int i = 8, j = 0; i < n; i++, j++)
	        T[i] = AC[j];
	}
	private int naASCII()
	{
	    double kodASCII = 0.0;

	    for(int i = 0, j = 7; i < 8; i++, j--)
	        kodASCII += (ASCII[i] * Math.pow(2.0,j));

	    return (int)kodASCII;
	}
	private boolean czyBlad()
	{
	    for(int i = 0; i < rows; i++)
	        if(Decode[i] != 0) return true;

	    return false;
	}
	private int szukaj1Blad() throws Exception
	{
	    boolean found = false;
	    String e = " ";
	  
	    for(int i = 0; i < n; i++)
	    {
	        found = true;
	        for(int j = 0; j < rows; j++)
	        {
	            if(H[j][i] != Decode[j])
	            {
	                found = false;
	                break;
	            }
	        }
	        if(found) return i; 
	    }
	    if(!found) throw new Exception(e);
	    return 1;
	}
	private int [] szukaj2Bledy()
	{
	    boolean found;
	    int[] k = new int[2];
	    int[] x = new int[2];
	    //string e = "  Wiecej niz 2 bledy!";

	    for(int i = 0; i < n-1; i++)
	    {
	        for(int l = i+1; l < n; l++)
	        {
	            found = true;

	            for(int j = 0; j < rows; j++)
	            {
	                if(((H[j][i] + H[j][l])%2) != Decode[j])
	                {
	                    found = false;
	                    break;
	                }
	            }
	            if(found)
	            {
	                k[0] = i;
	                k[1] = l;
	                return k;
	            }
	        }
	    }
	    return x ;
	}
	private void naprawBit(int pozycjaBledu)
	{
	    ASCII[pozycjaBledu] += 1;
	    ASCII[pozycjaBledu] %= 2;
	    zmienna++;
	}
	private void napraw2Bledy()
	{
	        int [] pozycjeBledow = new int[3];
	        pozycjeBledow = szukaj2Bledy();

	        if((pozycjeBledow[0] == 0 )&& (pozycjeBledow[1] == 0))
	        	errorMessage = "Za duzo bledow!";
	        else {
				//NAPRAWIANIE
				naprawBit(pozycjeBledow[0]);
				naprawBit(pozycjeBledow[1]);
				errorMessage += "2 Errors, positions: " + pozycjeBledow[0] + " and " + pozycjeBledow[1];
			}
	}
	private void napraw1Blad()
	{
	        
	        try
	        {
	            int pozycjaBledu;
	            pozycjaBledu = szukaj1Blad();

	            //NAPRAWIANIE
	            naprawBit(pozycjaBledu);
	            errorMessage += "1 Error on position: " + pozycjaBledu + ".";
	        }
	        catch(Exception e)
	        {
	            napraw2Bledy();
	        }
	        
	}

	char dekodowanie()
	{
		for(int i = 0; i < rows; i++)
			Decode[i] = 0;
		mnozenie1(ASCII);
		if(czyBlad()) {
			napraw1Blad();
		}
		int kodZnaku = naASCII();
		char cos = (char) kodZnaku;
		if(zmienna == 1)
		errorMessage +="  Letter "+ cos + "\n";
		if(zmienna == 3)
			errorMessage ="Za duzo bledow";
		return  cos;
	}



}

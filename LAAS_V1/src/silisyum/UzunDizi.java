package silisyum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UzunDizi {

	public double[] nec2icin = new double[1441];
	
	UzunDizi() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Suad\\Desktop\\esas_insallah\\02_son.txt"));
		String str;

		int sayac = 0;
		while((str = in.readLine()) != null){
			nec2icin[sayac] = Double.parseDouble(str);
			System.out.println(nec2icin[sayac]);
		    sayac++;
		}
		
		in.close();
	}
	
}

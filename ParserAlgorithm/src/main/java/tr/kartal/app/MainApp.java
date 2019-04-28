package tr.kartal.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainApp {

	public static void main(String[] args) throws IOException {

		String fName = "config/data.txt"; // read data
		int index = 0;

		int[] firstElements = readNumbersOfFile(index, fName); // test sayısını ve setlerdeki account
																// sayısını verir.

		int numberOfTests = firstElements[0]; // test sayısı

		index = 1;

		for (int i = 0; i < numberOfTests; i++) // test sayısı kadar döngü
		{

			firstElements = readNumbersOfFile(index, fName); // ilk setin account sayısı
			sortAccountsOfSet(++index, firstElements[0]); // setleri kendi içinde sıralar ve her bir accountu sayan
															// fonksiyon
			index = index + firstElements[0];
			System.out.println();
		}

	}

	private static void sortAccountsOfSet(int index, int numberOfAccounts) throws IOException {

		try (Stream<String> stream = Files.lines(Paths.get("config/data.txt"))) {

			Object[][] data = stream // setleri kendi içinde sıralma ve sayma streami
					.skip(index).limit(numberOfAccounts)
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
					.sorted(Map.Entry.comparingByKey())
					.map((entry) -> new Object[] { entry.getKey(), entry.getValue() }).toArray(Object[][]::new);

			stream.close();
			// print the data
			for (Object[] row : data) {
				System.out.println(MainApp.toString(row)); // yazdırma fonksiyonu
			}

		}

	}

	private static int[] readNumbersOfFile(int index, String fName) {

		int[] ints = null;

		try {

			Stream<String> rows = Files.lines(Paths.get(fName));

			ints = rows.skip(index).limit(1).mapToInt(Integer::parseInt).toArray();

			rows.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return ints;

	}

	public static String toString(Object[] a) {
		if (a == null)
			return "";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "";

		StringBuilder b = new StringBuilder();

		for (int i = 0; i <= iMax; i++) {
			b.append(String.valueOf(a[i]) + " ");

		}
		return b.toString();
	}

}

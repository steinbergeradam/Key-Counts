import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Key Counts reads in a list of comma-separated key-value pairs from file where
 * the value of each key is an integer value of that key's frequency. Then the
 * program sums up the frequencies of each key.
 * 
 * @author Adam Steinberger
 * 
 */
public class KeyCounter {

	/**
	 * The name of the input file to parse through
	 */
	private static String fileName;

	/**
	 * A hash map of keys read in from the input file and their frequencies
	 */
	private static LinkedHashMap<String, Integer> keyCounts;

	/**
	 * Read in key-value pairs and get each key's total count
	 * 
	 * @param file
	 *            the name of the input file to parse through
	 */
	public KeyCounter(String file) {

		fileName = file;
		keyCounts = new LinkedHashMap<String, Integer>();

		// parse through the input file, or exit the program if an error occurs
		try {
			parseInputFile();
		} catch (Exception e) {
			System.err
					.println("Error:  There was a problem reading the input file!");
		}

	}

	/**
	 * Read through the input file line by line and parse it
	 * 
	 * @throws Exception
	 *             the input file must exist for this program to run correctly
	 */
	private static void parseInputFile() throws Exception {

		// get ready to read the input file line by line
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		// this is where each line is stored during file reading
		String line;

		// read the input file line by line
		while ((line = bufferedReader.readLine()) != null) {

			// remove the "non-commit" character automatically added to the
			// beginning of the input file by the source control manager
			line = line.trim().replace("\ufeff", "");

			// separate the current line of the input file at each comma into an
			// array of strings
			String[] words = line.split(",");

			// determine how many comma separated strings are in the current
			// line of the input file
			int numWords = words.length;

			// skip the current line of the input file if it doesn't contain a
			// key-value pair of length two
			if (numWords != 2) {
				System.err
						.println("Error:  The current line of the input file does not contain a key-value pair of length two!");
				continue;
			}

			// get key from key-value pair
			String key = words[0].trim();

			// get value string from key-value pair
			String valueDisplay = words[1].trim();

			// null-protect the variable used to hold the key's value number
			int value = 0;

			// try to parse the value as an integer, and if that fails then skip
			// this line in the input file
			try {
				value = Integer.parseInt(valueDisplay);
			} catch (NumberFormatException e) {
				System.err.println("Error:  The value for key '" + key
						+ "' is not an integer!");
				continue;
			}

			// if the current key has already been read, add the current value
			// of that key to its existing value count
			if (keyCounts.containsKey(key))
				value += keyCounts.get(key);

			// add the key-value pair to a key frequency hash map
			keyCounts.put(key, value);

		}

		// close the input file reader
		bufferedReader.close();

	}

	/**
	 * Run the Key Counts program from here.
	 * 
	 * @param args
	 *            inputFileName
	 */
	public static void main(String[] args) {

		// get the number of command line arguments provided from the console
		int numArgs = args.length;

		// exit the program if the input file name is not provided as a command
		// line argument
		if (numArgs != 1) {
			System.err
					.println("Error:  No input file was provided as a command line argument for this program!");
			return;
		}

		// get input file name from the command line argument provided
		String inputFileName = args[0];

		// exit the program if there's a problem reading the input file
		try {

			// run a new instance of the key counter for the input file
			new KeyCounter(inputFileName);

			// get the set of key-value pairs from the key frequencies hash map
			Set<Entry<String, Integer>> set = keyCounts.entrySet();

			// get the iterator for the key-value pair set
			Iterator<Entry<String, Integer>> iterator = set.iterator();

			// setup output string builder to contain all key frequency results.
			StringBuilder builder = new StringBuilder();

			// iterate through each key-value pair in the key frequencies set
			while (iterator.hasNext()) {

				// get the current key-value pair in the key frequencies set
				Entry<String, Integer> next = iterator.next();

				// get the current key-value pair's key and value
				String key = next.getKey();
				int value = next.getValue();

				// add the current key-value pair results to the string builder
				builder.append("The total for " + key + " is " + value + ". ");

			}

			// get the output string from string builder
			String output = builder.toString().trim();

			// print the output string to the console
			System.out.println(output);

		} catch (Exception e) {
			System.err
					.println("Error:  There was a problem reading the input file!");
			return;
		}

	}

}

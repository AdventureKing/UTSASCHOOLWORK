package Assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class Driver {

	
	private static String traceFlag = null;
	private static String fileName = null;
	private static Boolean trace = false;

	private static ArrayList<Long> memoryAddress = new ArrayList<Long>();
	static int numOfBlocks = 0;
	private static BufferedReader br;
	private static String replacementPolicy;
	private static int assoc_of_cache;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cacheSize = 0;
		int blockSize = 0;

		int originalBlockSize = 0;
		int originalCacheSize = 0;
		
		
		
		// check arguments
		argsCheck(args);

		originalCacheSize = Integer.parseInt(args[0]);
		originalBlockSize = Integer.parseInt(args[1]);
		traceFlag = args[4];
		fileName = args[5];

		// math bullshit
		// get cache size and block size
		cacheSize = (int) Math.pow(2, cacheSize);
		blockSize = (int) Math.pow(2, blockSize);
		
		//read all the lines into a long array
		getMemoryAddressFromFile(fileName);

		numOfBlocks = ((int) ((Math.pow(2, originalCacheSize)) / (Math.pow(2, originalBlockSize))));
		

		

		sysRunSimulate(memoryAddress, originalCacheSize, originalBlockSize);

		// final print that is always needed

	}

	private static void getMemoryAddressFromFile(String fileName) {
		// TODO Auto-generated method stub
		try {
			Long memoryLocation = null;
			br = new BufferedReader(new FileReader(fileName));
			 
			String line = null;
			while ((line = br.readLine()) != null) {
				// convert all values to hex decimal
				// remove all whitespaces
				line = line.replaceAll("[\\s+]", "");
				if(!line.equals("")){

				if (line.contains("0x")) {
					line = line.replace("0x", "");
					memoryLocation = Long.parseLong(line, 16);
				} else {
					
					memoryLocation = Long.parseLong(line);
					}
				memoryAddress.add(memoryLocation);
				}
			
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(
					"Was unable to open file or parse file. Please check if file exist or has correct format.");
			System.exit(-1);
		}
	}

	private static void argsCheck(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("args " + args.length);
		if (args.length != 6) {
			System.out.println("Wrong amount of command line paramaters!");
			System.exit(-1);
		} else {
			// assign variables
			int originalCacheSize = Integer.parseInt(args[0]);
			int originalBlockSize = Integer.parseInt(args[1]);
			int Assoc = Integer.parseInt(args[2]);
			String replacePolicy = args[3];
			String traceFlag = args[4];
			File file = new File(args[5]);
			if (traceFlag.equals("on") || traceFlag.equals("off")) {
				if (traceFlag.equals("on")) {
					trace = true;
				}
			} else {
				System.out.println("Wrong value of command line parameter TRACE FLAG");
				System.exit(-1);
			}
			if (!file.exists() || !file.isFile()) {
				System.out.println("Not a vaild file");
				System.exit(1);
			}
			
			if ((originalBlockSize > originalCacheSize) || (originalCacheSize <= 0) || (originalBlockSize <= 0)) {
				System.out.println("Wrong value of command line paramaters! Cache Or Block Size");
				System.exit(-1);
			}
			if(Assoc < 0 ||(Assoc > (originalCacheSize - originalBlockSize))){
				assoc_of_cache = originalCacheSize - originalBlockSize;
			}else{
				assoc_of_cache = Assoc;
			}
			if (replacePolicy.equals("fifo") || replacePolicy.equals("lru")) {
				replacementPolicy = replacePolicy;
			} else {
				System.out.println("Wrong value of command line parameter! LRU OR FIFO CHECK");
				System.exit(-1);
			}
			
		}

	}

	public static void sysRunSimulate(ArrayList<Long> memoryAddress, int originalCacheSize, int originalBlockSize) {
		String RightAlignFormat = "";
		if (trace) {
			RightAlignFormat = "|%8x|%8x|%8x|%4s|%8d|%8d|%8d|%10.8f|%1s%n";

			System.out.format(
					"+--------+--------+--------+----+--------+--------+--------+----------+----------+%n");
			System.out.format(
					"| address|     tag|     set| h/m|    hits|  misses|Accesses|missRatio |Tags      |%n");
			System.out.format(
					"+--------+--------+--------+----+--------+--------+--------+----------+----------+%n");
		}
		// declare
		cache userCache = new cache(originalCacheSize, originalBlockSize, memoryAddress,replacementPolicy,assoc_of_cache);

		userCache.setNumOfBlocks(numOfBlocks);
		// theirs only one set

		double missRatio = 0.00;
		
		int numOfAccess = 0;
		for (int i = 0; i < memoryAddress.size(); i++) {
			String memoryAddressCurrent = Long.toHexString(memoryAddress.get(i));
			Long memoryAddress_Current = memoryAddress.get(i);
			                                                                
			int tag = userCache.getTag(memoryAddress.get(i));
			// get the current stored tagg
			int blockNumber = userCache.getBlockPosition(memoryAddress.get(i));
			String storedTag = userCache.getStoredTag(blockNumber);
			
			                                                           
			String hitOrMiss = userCache.Access(memoryAddress_Current);
			
			numOfAccess = userCache.getAccesses();

			                                                                      
			missRatio = (double) userCache.getNumMisses() / (double) numOfAccess;
			                                                                      
			if (trace) {
				System.out.format(RightAlignFormat, memoryAddress_Current, tag, blockNumber, hitOrMiss,
						userCache.getNumHits(), userCache.getNumMisses(), numOfAccess, missRatio,storedTag);
			}
		}
		if (trace) {
			System.out.format(
					"+--------+--------+--------+----+--------+--------+--------+----------+----------+%n");
		}
		finalOut(originalCacheSize, originalBlockSize, traceFlag, fileName, numOfAccess, userCache.getNumHits(),
				userCache.getNumMisses(), missRatio);

	}

	public static void finalOut(int cacheSize, int blockSize, String traceFlag, String fileName, int numAccess,
			int numHits, int numMisses, double missRatio) {
		System.out.println("Written by Brandon Snow");
		System.out.println("Args: " + cacheSize + " " + blockSize + " " + assoc_of_cache + " " + replacementPolicy + " " +  traceFlag + " " + fileName);
		System.out.println("memory accesses: " + numAccess);
		System.out.println("hits: " + numHits);
		System.out.println("misses: " + numMisses);
		System.out.printf("miss ratio: %.8f\n", missRatio);
	}

	static int log(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}

	public static ArrayList<String> memoryInHex(ArrayList<Long> memoryAddress2) {
		ArrayList<String> memoryAddress_in_Hex = new ArrayList<String>();
		for (Long memoryA : memoryAddress2) {
			String hexStr = Long.toHexString(memoryA);
			memoryAddress_in_Hex.add(hexStr);
		}
		return memoryAddress_in_Hex;
	}

	public static ArrayList<String> memoryInBinary(ArrayList<Long> memoryAddress2) {
		ArrayList<String> memoryAddress_in_Binary = new ArrayList<String>();
		for (Long memoryA : memoryAddress2) {
			String memoryTemp = Long.toBinaryString(memoryA);
			memoryAddress_in_Binary.add(memoryTemp);
		}
		return memoryAddress_in_Binary;
	}

}
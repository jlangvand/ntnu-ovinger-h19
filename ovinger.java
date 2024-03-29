import java.lang.Math;
import java.math.*;

/* Konverterer timer, minutter og sekunder til sekunder.								*|
|* Tar timer, minutter og sekunder som argument. Eks:										*|
|* java Hm2S 10 33 25																										*/

class Hm2S {
	public static void main(String args[]) {
		int s = args.length > 0 ? Integer.parseInt(args[0])*3600 : 0;
		s += args.length > 1 ? Integer.parseInt(args[1])*60 : 0;
		s += args.length > 2 ? Integer.parseInt(args[2]) : 0;
		System.out.println(String.format("%ss",s));
	}
}

class InToCm {
	public static void main(String args[]) {
		for(int i=0; i<args.length; i++) {
			System.out.print(args.length > 0 ? String.format("%.2fin = %.2fcm", Double.parseDouble(args[0]), Double.parseDouble(args[0])*2.54) : "Must be run with an argument");
		}
	}
}

class KGprice {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage:\nKGprice price mass(grams)\nExample:\nKGprice 35.90 450");
      return;
    }
    double price = Double.parseDouble(args[0]);
    double mass = Double.parseDouble(args[1]);
    System.out.println(String.format("Price per kilogram: %.2f", price*1000/mass));
  }
}

class S2hms {
  public static void main(String args[]) {
    for (int i=0; i<args.length; i++) {
      int s = Integer.parseInt(args[i]);
      System.out.print(String.format("%ds = ", s));
			System.out.println(String.format("%dh %dm %ds",s/3600,(s%3600)/60,(s%3600)%60));
    }
  }
}

class IsPrime { // Printed statement is wrong. Test works.
		public static void main(String[] args) {
			for (int arg = 0; arg < args.length; arg++) {
				int test = Integer.parseInt(args[arg]);
				int i = 2;
				boolean isPrime = test%2==0||test==1?(test==2?true:false):true;
				if (isPrime) for (i=3; i<(i<100?test:Math.sqrt(test)); i+=2) {
					if (test % i == 0) {
						isPrime = false;
						break;
					}
				}
				System.out.println(String.format("%d is "+(test==0||test==1?"not ":isPrime?"":"divisable by %d and is not ")+"a prime!",test, i));
			}
		}
}

class PrimeThread extends Thread {
	private long lower;
	private long upper;
	private int k;
	private long a;
	private int tests;

	PrimeThread(long l, long u) {
		lower = l;
		upper = u;
		tests = 5;
	}

	public void run() {
		try {
			boolean prime;
			lower=(lower%2==0?lower+1:lower);
			for (long i = lower; i<=upper; i+=2) {
				// k = tests;
				// while (k > 0) {
				// 	a = (long)2.0+(long)(Math.random() % (i-4));
				// 	if (power(a,i-1,i) != 1) {
				// 		prime = false;
				// 		break;
				// 	}
				// 	k--;
				// }

				prime = true;
				if (i%2==0) prime = false;
				else for (long j = 3; j < Math.sqrt(i); j+=2) {
					if (i%j==0) {
						prime = false;
						break;
					}
				}
				if (prime) {
					System.out.print(String.format("%d ",i));
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception error!");
		}
	}
}

class MultiPrime {
	public static void main(String[] args) {
		int threads = 16;
		long lower = Long.parseLong(args[0]);
		long upper = Long.parseLong(args[1]);
		long interval = (upper-lower)/threads;
		for (int i = 0; i < threads; i++) {
			long start = lower + i * interval;
			PrimeThread primeThread = new PrimeThread(start, start+interval);
			primeThread.start();
		}
	}
}

class PrintPrimes {
	public static void main(String[] args) {
		long lower = Integer.parseInt(args[0]);
		long upper = Integer.parseInt(args[1]);
		boolean prime;
		lower=(lower%2==0?lower+1:lower);
		for (long i = lower; i<=upper; i+=2) {
			prime = true;
			if (i%2==0) prime = false;
			else for (long j = 3; j < Math.sqrt(i); j+=2) {
				if (i%j==0) prime = false;
			}
			if (prime) {
				System.out.print(String.format("%d ",i));
			}
		}
		System.out.println();
	}
}

class PrintMersennes {
	public static void main(String[] args) {
		long lower = Long.parseLong(args[0]);
		long upper = Long.parseLong(args[1]);
		//System.out.print("2, ");
		for (long i = lower; i<=upper; i++) {
			boolean prime = true;
			if (i%2==0) prime = false;
			else for (long j = 3; j < Math.sqrt(i); j+=2) {
				if (i%j==0) prime = false;
			}
			if (prime) {
				boolean mersenne = true;
				long m = (2^i)-1;
				if (m%2==0) mersenne = false;
				else for (long k = 3; k < Math.sqrt(m); k+=2) {
					if (m%k==0) mersenne = false;
				}
				if (mersenne) {
					System.out.println(String.format("2^%d is prime!",i));
				}
			}
		}
		System.out.println();
	}
}

class LeapYear {
	public static void main(String[] args) {
		int test;
		try {
			Integer.parseInt(args[0]);
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Tast inn et årstall som argument. Eks:\nLeapYear 2011");
			return;
		} catch (NumberFormatException e) {
			System.out.println("Vennligst bruk kun (hel)tall");
			return;
		}
		System.out.println((((test%100==0)&&(test%400==0))||((test%100!=0)&&(test%4==0)))?"Leap year":"Not a leap year");
	}
}

// Gangetabell
class Table {
	public static void main(String[] args) {
		int start = 0;
		int stop = 0;
		try {
			start = Integer.parseInt(args[0]);
			stop = Integer.parseInt(args[1]);
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Programmet tar to heltall som argumenter, 'fra' og 'til'");
			return;
		} catch(NumberFormatException e) {
			System.out.println("Kun heltall som argumenter");
			return;
		}
		int l = (int) (Math.log10(stop)+1);
		System.out.println();
		for (int i = start; i <= stop; i++) {
			System.out.println(String.format("%d-GANGETABELL:", i));
			for (int j = 1; j <= 10; j++) {
				System.out.println(String.format("%"+l+"d * %2d = %"+(l+1)+"d", i, j, i*j));
			}
			System.out.println();
		}
	}
}

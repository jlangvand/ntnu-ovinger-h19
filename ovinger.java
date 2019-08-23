class Hm2S {
	public static void main(String args[]) {
		double h = Double.parseDouble(args[0]);
		double m = Double.parseDouble(args[1]);
		double s = Double.parseDouble(args[2]);
		s+=m*60;
		s+=h*3600;
		System.out.print(args[0]+"h, "+args[1]+"m, "+args[2]+"s -> ");
		System.out.print(s);
		System.out.println("s");
	}
}

class InToCm {
	public static void main(String args[]) {
		for(int i=0; i<args.length; i++) {
			System.out.print(args[i]+"in -> ");
			System.out.print(Double.parseDouble(args[i])*2.54);
			System.out.println("cm");
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
      double s = Double.parseDouble(args[i]);
      int m = 0;
      int h = m;
      System.out.print(String.format("%.2fs = ", s));
      while (s > 3600) {
        h++;
        s-=3600;
      }
      while (s > 60) {
        m++;
        s-=60;
      }
      if (h>0) System.out.print(Integer.toString(h)+"h, ");
      if (m>0) System.out.print(Integer.toString(m)+"m, ");
      System.out.println(String.format("%.2fs", s));
    }
  }
}

class IsPrime {
		public static void main(String[] args) {
			int test = Integer.parseInt(args[0]);
			boolean isPrime = true;
			for (int i = 2; i<test; i++) if (test % i == 0) isPrime = false;
			System.out.println(isPrime?"true":"false");
		}
}

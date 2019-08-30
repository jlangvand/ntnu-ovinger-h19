class InToCm {
	public static void main(String args[]) {
		for(int i=0; i<args.length; i++) {
			System.out.print(args.length > 0 ? String.format("%.2fin = %.2fcm", Double.parseDouble(args[0]), Double.parseDouble(args[0])*2.54) : "Must be run with an argument");
		}
	}
}

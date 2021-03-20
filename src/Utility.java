class Utility {
   public static void run(AbstractPolynomial p, AbstractPolynomial q) throws Exception {
      System.out.println("Polynomials\np = " + p + "\nq = " + q);
      System.out.println("Sum " + p.add(q));
      System.out.println("Difference " + p.subtract(q));
      System.out.println("Product " + p.multiply(q));
   }
}
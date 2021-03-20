import java.util.*;
import java.util.regex.Pattern;

public class Polynomial extends AbstractPolynomial {


	protected static void Sort(Term[] TA) { // sort method to sort terms

		for (int i = 0; i < TA.length; i++) {
//			 System.out.println(TA[i] + " ");
			for (int j = i + 1; j < TA.length; j++) {
				if (TA[i].coefficient == 0.0d && TA[i].degree == 0) // skip the 0 terms
					continue;
				if (TA[i].getDegree() < TA[j].getDegree()) { // check if which term is larger, term with higher degree
																// will move to the front.
					Term tempInSwap = TA[i];
					TA[i] = TA[j];
					TA[j] = tempInSwap;
				} else if (TA[i].getDegree() == TA[j].getDegree()) { // here to check if two terms are the same degree,
																		// add their coef then empty out one of the
									   						           // term;
					TA[i].setCoefficient(TA[i].coefficient + TA[j].coefficient);
					TA[i].setDegree(TA[i].degree);
					TA[j].setCoefficient(0.0d);
					TA[j].setDegree(0);
//	                	for (int k = j; k < TA.length - 1; k++) {
//	                		TA[k] = TA[k + 1];
//						}

				} else // else indicates the next term is lower in degree than the previous term, so go
						// to next term for compare.
					continue;
			}
		}
	}


	public Polynomial(String s) {
		super(); // call super to initalize a Dlist called data.

		String Uppercase = s.toUpperCase().replaceAll("\\s", ""); // x + 3.0 x^2 + 2.0 x^2 - 1.0 x^2+5.0
																	// X+3.0X^2+2.0X^2-1.0X^2+5.0
		// add + for negative coefficient , make it add a negative number to
		// take care negative
		Uppercase = Uppercase.replaceAll("-", "+-"); // X+3.0X^2+2.0X^2+-1.0X^2+5.0,

//		System.out.println(Uppercase);

		String[] terms = Uppercase.split("\\+"); // take out all + signs and store them into String array named terms
		// X
		// 3.0X^2
		// 2.0X^2
		// -1.0X^2
		// 5.0
		List<Term> termList = new ArrayList<>(); // call an temporary dynamic arraylist to store terms

		for (int i = 0; i < terms.length; i++) {
//			System.out.println("inside array: " + terms[i] + " ") ;
			String coefString; // use to store substring for coefficient, it will be used as
								// Double.parseDouble(coefString)
			String degString; // use to store substring for degree. it will be used as
								// Integer.parseInt(degString)
			Term temp; // a temporary Term varible for instantiation and add this temp term into
						// termList
			double coef; // use to store coef number for instantiate Term objects
			int deg; // use to store coef number for instantiate Term objects

			int indexOfX = terms[i].indexOf("X"); // use to check cases of = -1 case , = 0 case , = 1 case , >= 2 case

			int indexOfCaret = terms[i].indexOf("^"); // use to get the index of '^'

			if (terms[i].equals("")) // to avoid a negative term in the array ex. - 5.0 X + X ^ 2 , split '+' will
										// add
				continue; // an empty string to terms[0]

//			

			if (indexOfX == -1) { // check the case if X is not in the string that will left with whole thing is
									// the coef, and degree = 0;
				coef = Double.parseDouble(terms[i]);
				deg = 0;
				temp = new Term(coef, deg); // instantiate the term
				termList.add(temp); // add the term into arraylist

//				System.out.println(temp);

			} else if (indexOfX == 1) { // check if X is at the second place of string. that means either -X or [1-9]X
				if (terms[i].subSequence(0, indexOfX).equals("-")) { // here checks if that is a '-' sign
					coef = -1; // if yes, just initialize coef = -1
					if (terms[i].contains("^")) { // then check for string contains character "^"
						degString = terms[i].substring(indexOfCaret + 1); // if yes, take out the rest of the string and
																			// turn it into integer
						deg = Integer.parseInt(degString);
						temp = new Term(coef, deg); // instantiate the term
						termList.add(temp); // add the term into arraylist
					} else { // here will just be coef = -1 and with no '^' means -X
//						coefString = terms[i].substring(0, indexOfX);
//						coef = Double.parseDouble(coefString);
						deg = 1;
						temp = new Term(coef, deg); // instantiate the term
						termList.add(temp); // add the term into arraylist
					}

				} else { // here will check if there are single digit coef before X
					coefString = terms[i].substring(0, indexOfX);
					coef = Double.parseDouble(coefString);
					if (terms[i].contains("^")) { // if string contains '^' that means there will be a degree

						degString = terms[i].substring(indexOfCaret + 1); // take out the string after the '^' and
																			// parseInt
																			// that substring will be the degree
						deg = Integer.parseInt(degString);
						temp = new Term(coef, deg);
						termList.add(temp);
//					System.out.println(temp);
					} else { // if no '^' exist, means it is "-X"
						deg = 1;
						temp = new Term(coef, deg);
						termList.add(temp);
//					System.out.println(temp);
					}
				}

			} else if (indexOfX == 0) { // if X is at the beginning of the string
				coef = 1; // that means no coef for this term
				if (terms[i].contains("^")) { // here will check if '^' in there to get the degree value.
					degString = terms[i].substring(indexOfCaret + 1); // if yes, turn the substring after '^' into
																		// integer and store to degree
					deg = Integer.parseInt(degString);
					temp = new Term(coef, deg);
					termList.add(temp);
//					System.out.println(temp);
				} else { // if reach here, that means this string is a solo X
					deg = 1;
					temp = new Term(coef, deg);
					termList.add(temp);
//					System.out.println(temp);
				}
			} else if (indexOfX >= 2) { // here will take care all case from 2 to above meaning - 199 X^2 , - 199 X^6
										// etc.
				coefString = terms[i].substring(0, indexOfX); // take out the substring before X and turn it into double
																// value and store into coef
				coef = Double.parseDouble(coefString);
				if (terms[i].contains("^")) { // check to see if there is '^' , if yes, turn the substring after '^' and
												// store it into degree
					degString = terms[i].substring(indexOfCaret + 1);
					deg = Integer.parseInt(degString);
					temp = new Term(coef, deg);
					termList.add(temp);
				} else { // if '^' does not exist, means there will be no degree variable, means deg = 1;
					deg = 1;
					temp = new Term(coef, deg);
					termList.add(temp);
				}

			}
		}

		Term[] termArray = termList.toArray(new Term[0]); // change my arraylist to a term array to sort.
		Sort(termArray);                               // sort method that sorts the terms into ascending/descending order. it does not
						                               // matter the order because the Dlist later
		for (int j = 0; j < termArray.length; j++) {
			if (termArray[j].degree == 0 & termArray[j].coefficient == 0.0d)
				continue; // disregard any 'empty' terms and left them out of our own list.
			else
				data.addLast(termArray[j]); // add all other terms with coef and degree into the Dlist
		}
	}

	public Polynomial() {
		super();
	}
	public AbstractPolynomial add(AbstractPolynomial p) {
		AbstractPolynomial ans = new Polynomial();   // result list that will be return
		
			try {                                           // try and catch block to avoid null pointer
				 DNode<Term> temp1 = p.data.getFirst();     // pointer pointing to the first element p(the first polynomial)
				 DNode<Term> temp2 = this.data.getFirst();   //pointer pointing to the first element q(the second polynomial)
				 
				while(temp1.getNext() != null && temp2.getNext() != null) {     
					// while both pointers are not pointing to a null
					
					if(temp1.getData().getDegree() > temp2.getData().getDegree()) {
						
						ans.data.addLast(temp1.getData());       // add to the result list
						temp1 = temp1.getNext();                 // update pointers to point to next term in p
						
				
					}
					
					else if(temp1.getData().getDegree() == temp2.getData().getDegree()) {        // when degree of both term are equal
						
						double coef = temp1.getData().getCoefficient() + temp2.getData().getCoefficient();  
						// declare a variable to keep the new coefficient
						
						
						Term temp_term =  new Term(coef,temp1.getData().getDegree() ); 
						// declare a new term using the new COEF and the same degree of either term
						
						ans.data.addLast(temp_term);  // add this TEMP_TERM to the result list.
					
						temp1 = temp1.getNext();     // update both pointer pointing to the next term of each polynomial
						temp2 = temp2.getNext();

						
					}
					
					else {   // every else case or temp1 is less than temp2
						
						ans.data.addLast(temp2.getData());         // add temp2 to the result list
						temp2 = temp2.getNext();                   // update the pointer to next term

					}
				}
					
                    while(temp1.getNext() != null) {             // if temp2 reach null, or added all terms in q to ans already
						ans.data.addLast(temp1.getData());       // add all remaining terms in p to ans
						temp1 = temp1.getNext();                 // update pointer until no more remaining term in temp2
					}
                    
					while(temp2.getNext() != null) {             // if temp1 reach to null, or added all terms in p to ans already
						ans.data.addLast(temp2.getData());       // add all remaining terms in q to ans
						temp2 = temp2.getNext();                 // update pointer until no more remaining term in temp2
					}
					

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return ans;
	}

	public AbstractPolynomial subtract(AbstractPolynomial p) {
		
		AbstractPolynomial ans = new Polynomial();
		try {
			 DNode<Term> temp1 = this.data.getFirst();      // pointer point to the head of p
			 DNode<Term> temp2 = p.data.getFirst();         // pointer point to he head of q
			 
			 Term temp_term = new Term();                   // temporary term use to store term for degree equals case
			 
			 while(temp2.getNext() != null) {              
				 // go through the terms in second polynomial
				 
				 temp2.getData().setCoefficient(-1 * temp2.getData().getCoefficient()); 
				 // multiply by -1 because adding a -1 equivalent to subtraction
				 temp2 = temp2.getNext();                  
				 // update the pointer to change all terms in temp2
			 }
			 temp2 = p.data.getFirst();  // update temp2 back to first term in q
			 
			while(temp2.getNext() != null && temp1.getNext() != null) {
				
				// if either pointer points to null exit.
				
				if(temp1.getData().getDegree() > temp2.getData().getDegree()) { 
					// check if temp1 degree is greater than temp2's degree
					
					ans.data.addLast(temp1.getData()); 
					// add to result list

					temp1 = temp1.getNext(); 
					// update the pointer to next term

				}
				else if(temp1.getData().getDegree() == temp2.getData().getDegree()) {   
					
					double coef = temp1.getData().getCoefficient() + temp2.getData().getCoefficient();
					// COEF variable to store the new coef and used to declared in temp term
					
					
					temp_term = new Term(coef,temp2.getData().getDegree());    
					// instantiate a temporary term to store new coef and same degree

					ans.data.addLast(temp_term);
					// add this TEMP_TERM to the result list

					temp2 = temp2.getNext();   // update each pointer
					temp1 = temp1.getNext();
				}
				else if(temp1.getData().getDegree() < temp2.getData().getDegree()){
					ans.data.addLast(temp2.getData()); 
					temp2 = temp2.getNext();
				}
			}
			    while(temp2.getNext() != null) {
					ans.data.addLast(temp2.getData());
					temp2 = temp2.getNext();
				}                                               // add all remaining terms in either p or q to ans
				while(temp1.getNext() != null) {
					ans.data.addLast(temp1.getData());
					temp1 = temp1.getNext();
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	public AbstractPolynomial multiply(AbstractPolynomial p) {
		
		AbstractPolynomial ans = new Polynomial();
		try {
			
			DNode<Term> temp1 = this.data.getFirst();
			DNode<Term> temp2 = p.data.getFirst();
			
			while(temp2.getNext() != null) {  
				// all terms were updated by reference in subtract method by multiply by -1 
				// so we need to change q back to its original again by reference
				
				 temp2.getData().setCoefficient(-1 * temp2.getData().getCoefficient());
				 temp2 = temp2.getNext();
			}
			
			temp2 = p.data.getFirst();      // point the pointer back to the first term of q
			
			Term temp_term = new Term();    
			// instantiate a temporary term to store new coefficient
			
			List<Term> termList = new ArrayList<>();  
			// create a new arraylist to store all temporary terms 
			
			double coef;
			int deg;
			
			while(true) {

				coef = temp1.getData().getCoefficient() * temp2.getData().getCoefficient();
				// multiply coefficient
				
				deg = temp1.getData().getDegree() + temp2.getData().getDegree();
				// add  degree
				
				temp_term = new Term(coef, deg);
				// instantiate the new term 
				termList.add(temp_term);
				// add this new term to the arraylist
				
				temp2 = temp2.getNext();
				// update the pointer of q to next term but keep the pointer of temp1
				// multiply temp1 to all terms in q
				
				if(temp2.getNext() == null) {    
					
					// if temps points to null, finish the mulplication of first term of q with all terms of p
					temp1 = temp1.getNext();
					// update temp1 to next term in q. multiply this new term to all the term in temps again
					temp2 = p.data.getFirst();
					// update back to the first term in q
					
				}
				if (temp1.getNext() == null) {
					break;
				}
			}
			Term[] termArray = termList.toArray(new Term[0]); 
			// turn the arraylist to a term array to sort them.
			Sort(termArray);
			for (int i = 0; i < termArray.length; i++) {
				if (termArray[i].degree == 0 & termArray[i].coefficient == 0.0d)
					continue; 
				// disregard any 'empty' terms and left them out of our own list.
				ans.data.addLast(termArray[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return ans;		
	}


	/****************
	 * MAIN FUNCTION
	 ****************/

	public static void main(String args[]) throws Exception {
		// Variables
		Scanner scnr = new Scanner(System.in);
		AbstractPolynomial p, q;
		String testInput = "";
		// Custom test case
		if (scnr.hasNext()) {
			testInput = scnr.nextLine();
			p = new Polynomial(testInput);
			testInput = scnr.nextLine();
			q = new Polynomial(testInput);
			System.out.println("User Input\n***************************");
			Utility.run(p, q);
		}
		// Default test case

		else {
			p = new Polynomial(" X^5");
			q = new Polynomial("X^2 - X + 1");
			System.out.println("Default Input\n***************************");
			Utility.run(p, q);
		}
	}
}

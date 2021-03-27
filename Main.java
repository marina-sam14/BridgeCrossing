import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	
	public static ArrayList<Member> start = new ArrayList<Member>();
	
	public static void main (String[] args) {
		
		System.out.println ("Welcome to the Bridge Crossing Problem.\n Person Pn can cross the bridge"
				+ " in n minutes.\n Only one or two persons can cross at a time because it is dark, "
				+ "and the flashlight must be taken on every crossing.\n When two people cross, "
				+ "they travel at the speed of the slowest person.");
		
		
		System.out.println("Type how many people you want to cross the bridge: ");
		
		Scanner num = new Scanner(System.in);
		int N=num.nextInt();
		ArrayList<Member> members = new ArrayList<Member>(N); //apothikeusi atomwn pou theloun na kanoun to crossing 

		
		int i=0;
		while (i<N) {
			System.out.println("Type how much time the specific person needs to cross the bridge. Press enter to go to the next person  ");
			int seconds = num.nextInt();
			
			Member member = new Member (i+1,seconds); 
			members.add(member);
			
			i++;
		}
		
		
		System.out.println("Type the torch's time: ");
		int torchTime=num.nextInt(); //diarkeia zwis tou torch 
		
		
		State root = new State(members,null,0,"R"); //arxiki katastasi
		BridgeCrossing bridgecrossing = new BridgeCrossing();
		State finalState=null; 
		long start = System.currentTimeMillis();//arxi xronou
		finalState = bridgecrossing.aStar(root); //klisi tou A* algorithmou
		long end = System.currentTimeMillis();//telos xronou 
		if(finalState == null) {
			System.out.println("***************");
			System.out.println("Could not find solution");
			
		}
		else if (finalState.getTotalTime() > torchTime) {
			System.out.println("***************");
			System.out.println("Torch time exceeded.");
			System.out.println ("Crossing Time lasts " + finalState.getTotalTime());
			//to crossing kratise parapanw apo tin lampa,opote den egine
		}
		else {
			State temp = finalState;
			ArrayList<State> path = new ArrayList<State>();
			path.add(finalState);
			while(temp.getPred()!=null) {
				path.add(temp.getPred());
				temp = temp.getPred();
			}
			Collections.reverse(path);
			for(State item : path){
				item.print();
			}
			System.out.println("***************");
			System.out.println("Finished in "+(path.size()-1)+" steps. ");
			//afairoume 1 giati alliws to prwto vima tha einai to mideniko
		}
		System.out.println("Search time: " + (double)(end - start) / 1000 + " sec.");
			
	}	
	
}
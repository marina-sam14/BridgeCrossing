import java.util.ArrayList;
import java.util.Collections;

public class State implements Comparable<State>{
	
	private ArrayList <Member> start;
	private ArrayList <Member> end;
	
	private int totalTime;
	private int cost;
	private String torch;
	private State predecessor=null;

	
	public State () {
		this.start= new ArrayList<Member>();
		this.end= new ArrayList<Member>();
		this.totalTime=0;
		this.torch="R";
	}
	
	public State (ArrayList<Member> start, ArrayList<Member> end, int totalTime,String torch) {
		this.start=new ArrayList<Member> (start);
		if(end==null)
			this.end=new ArrayList<Member> (); 
		else
			this.end=new ArrayList<Member>(end);
		this.totalTime=totalTime;
		this.torch=torch;	
	}
	
	public ArrayList<Member> getStart() {
		return start;
	}
	
	public ArrayList<Member> getEnd() {
		return end;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
	public int getCost() {
		return cost;
	}
	
	
	public String getTorch() {
		return torch;
	}
	
	public State getPred() {
		return predecessor;
	}
	
	public void setStart(ArrayList<Member> start) {
		this.start=start;
	}
		
	public void setEnd(ArrayList<Member> end) {
		this.end=end;
	}
	
	public void setTotalTime(int totalTime) {
		this.totalTime+=totalTime;
		//auto to kanoume gt i euretiki mas kapoia stigmi tha xreiastei to totaltime mexri mia katastasi gia na dwsei to kostos kai kanontas += den xreiazetai na to ypologizoume kathe fora
	}
	
	public void setCost(int cost) {
		this.cost=cost;
	}

	
	public void setTorch(String torch) { 
		this.torch=torch;
	}
	
	public void setPred(State predecessor) {
		this.predecessor=predecessor;
	}
	
	public String isTorch() {
		return torch;
	}
	
	
	public ArrayList<State> getChildren() {
		ArrayList<State> children = new ArrayList<State>();
		//State child = new State();
		if(torch=="R") {
			for(int i = 0; i < this.start.size(); i++){
				//if only one person wants to cross the bridge
				if (this.start.size()==1) {
					State temp = new State(this.start,this.end,this.totalTime,"R"); //eimaste deksia
					temp.setPred(this);
					temp.setTotalTime(this.start.get(i).getTotal_sec());  //prosthetei sto totalTime ton xrono tou member pou thelei na perasei apenanti
					temp.end.add(temp.start.get(i)); //to member perase apenanti
					temp.setTorch("L");//i lampa perase apenanti
					temp.start.remove(i); //diagrafoume ton crosser apo tin arxiki pleura
					temp.evaluate(); //ypologismos neou kostos
					children.add(temp); //prosthetoume tin katastasi stin arraylist children
				}
				 
				else {
					//more than one person want to cross the bridge
		            for(int j = i+1; j < this.start.size(); j++){
		            	State temp = new State(this.start,this.end,this.totalTime,"R");//eimaste deksia 
		                temp.setPred(this);
		                ArrayList<Member> temp2 = new ArrayList<Member>(); //xrisimopoioume arraylist giati parapanw apo 1 atomo theloun na perasoun apenanti
		                int itime = temp.start.get(i).getTotal_sec(); //xronos tou i na kanei to cross
		                int jtime = temp.start.get(j).getTotal_sec(); //xronos tou j na kanei to cross
		                temp.setTotalTime ((itime <= jtime) ? jtime: itime); //sygkrisi xronwn kai prosthesi sto totalTime
		                
		                //prosthiki stin arraylist autwn pou tha perasoun apenanti
						temp2.add(this.start.get(i));
		                temp2.add(this.start.get(j)); 
		                temp.moveForward(temp2); //kanoun to cross
		                temp.setTorch("L"); //i lampa pleon einai aristera,kanei cross
		                temp.evaluate(); //ypologismos neou kostos
		                children.add(temp);//prosthetoume tin katastasi stin arraylist children
		            }
			    }
			}
		}
			 
		else if (torch=="L"){
			//edw gyrizei panta enas
        	for(int i = 0; i < this.end.size(); i++) {
				State temp = new State(this.start,this.end,this.totalTime,"L"); //eimaste aristera
        		temp.setPred(this);
        		temp.setTotalTime(this.end.get(i).getTotal_sec()); //prosthesi tou xronou autou pou gyrizei pisw ston xrono pou exoume idi
        		//System.out.println(temp.getTotalTime());
        		temp.moveBack(this.end.get(i)); //kanei to cross pros ta pisw
        		temp.setTorch("R");//i lampa pleon einai deksia,kanei cross
        		temp.evaluate();//ypologismos neou kostos
        		children.add(temp); //prosthetoume tin katastasi stin arraylist children
        	}
        }
        return children;
	}
	
	
	
	//aristera->deksia mono enas 
	public void moveBack(Member member) {
		member.setCoast("R"); //i lampa tha paei deksia 
		this.end.remove(member); 
		this.start.add(member); //egine to cross
	}
	
	
	//aristera<-deksia,osoi theloun (vasei euretikis)
	public void moveForward(ArrayList<Member> members) {
		for (Member member:members) {
			this.start.remove(member);
			this.end.add(member); //egine to cross 
		}
	}
	
	/*kanoume relax ston periorismo twn atomwn pou mporoun na diasxisoun tin gefyra. otan i lampa einai aristera epilegoume ton speedest+slowest kai 
	otan einai deksia, ton slowest*/
	public int heuristic() {
		int shortest_time=100;
		int longest_time=-10;
		int temp_time;
		
		if(this.getTorch()=="R") {
			if (this.start.isEmpty()) { //mono gia logous plirotitas checkaroume an iparxoun members pou theloun na diasxisoun tin gefyra
				return 0;
			}
			
			for (Member member:start) {
				temp_time=member.getTotal_sec(); //apothikeusi xronou kathe member deksia
				if (temp_time>longest_time) {
					longest_time=temp_time;
				}
				return longest_time;        
			}
		}
		else if(this.getTorch()=="L") {
			
			if (this.end.isEmpty()) { //checkaroume oti iparxoun members aristera 
				return 0;
			}
			
			for (Member member: end) {
				temp_time = member.getTotal_sec(); //apothikeusi xronou kathe member aristera
				if (temp_time < shortest_time) {
					shortest_time = temp_time;
				}
			}                
			
			for (Member member:start) {
				temp_time=member.getTotal_sec(); //apothikeusi xronou kathe member deksia 
				if (temp_time>longest_time) {
					longest_time=temp_time;
				}
			}
		
		}
		return shortest_time+longest_time;
	}
	
	public void evaluate() {
		this.cost=this.heuristic()+this.getTotalTime();
	}
	
	//prints the pathing the heuristic chose
	public void print() {
		ArrayList<Integer> sm = new ArrayList<Integer>();
		ArrayList<Integer> em = new ArrayList<Integer>();
		for (Member m: this.start) 
			sm.add(m.getTotal_sec());
		for (Member r: this.end) 
			em.add(r.getTotal_sec());
		System.out.println("============================");
		System.out.println("\n" + "Start: " + sm);
		System.out.println("\n" + "End: " + em);
		System.out.println("============================");
		System.out.println("\n" + "Time elapsed: " + this.totalTime);
		System.out.println("\n" + "Torch's Side: " + (this.getTorch()));
		
	}
		
	public boolean isTerminal() { //checkaroume an i katastasi einai teliki,an dld den iparxoun alla members pou theloun na perasoun apenanti
		if (start.isEmpty()) {
			return true;
		}
		return false;
	}
			

	@Override
	//We override the compareTo function of this class so only the heuristic costs are compared
	public int compareTo(State s){
		return Double.compare(this.cost, s.cost);
	}
}
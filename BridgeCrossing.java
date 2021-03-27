import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class BridgeCrossing {
	
	private ArrayList<State> openList;
	private HashSet<State> closedList;
	
	BridgeCrossing() {
		this.closedList=null;
		this.openList=null;
	}
	
	//A* Algorithm Implementation with Closed Set
	public State aStar(State initialState) {
		this.openList=new ArrayList<State>();
		this.closedList=new HashSet<State>();
		this.openList.add(initialState);
		
		while (this.openList.size()>0) {
			State currentState = this.openList.remove(0);
			if(currentState.isTerminal()) 
				return currentState;
			if(!closedList.contains(currentState)) {
				this.closedList.add(currentState);
				this.openList.addAll(currentState.getChildren());
				Collections.sort(this.openList);
			}
		}
		return null;
	}
}
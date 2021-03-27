import java.util.*;
public class Member {
	
	private int code; 
	private int total_sec;
	private String coast; //se poia pleura tis gefiras einai
	
	Member(){
		
		this.code=0;
		this.total_sec=0;
		this.coast = "R";
		
	}
	
	Member(int code,int total_sec,String coast){
		this.code=code;
		this.total_sec=total_sec;
		this.coast = coast; 
	}
	
	Member(int code,int total_sec){
		this.code=code;
		this.total_sec=total_sec;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code=code;
	}
	
	public int getTotal_sec() {
		return total_sec;
	}
	
	public void setTotal_Sec(int total_sec) {
		this.total_sec=total_sec;
	}
	
	public String getCoast() {
		return coast;
	}
	
	public void setCoast(String coast) {
		this.coast=coast;
	}
	
}

package Product;

public class Clipboard {
	String str;
	Clipboard(){
		str="";
	}
	void copy(String str){
		this.str=str;
	}
	String pasteData(){
		return str;
	}
}
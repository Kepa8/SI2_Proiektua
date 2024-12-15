package domain;


public class IntegerAdapter  {
    
    public Integer unmarshal(String s) {
        return Integer.parseInt(s);
    }
 
    public String marshal(Integer number) {
        if (number == null) return "";
         
        return number.toString();
    }
}
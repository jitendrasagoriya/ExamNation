package eduapp.examnation.com.examnation.helper;

public class Utility {

    public static String getSucjectNameById(long id){
        if(id == 1){
            return "BIOLOGY";
        }else if(id == 2){
            return "CHEMISTRY";
        }else if(id == 3){
            return "PHYSICS";
        }else if(id == 4){
            return "MATHS";
        }else{
            return "";
        }
    }
}

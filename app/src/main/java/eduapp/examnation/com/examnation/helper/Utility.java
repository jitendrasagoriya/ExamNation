package eduapp.examnation.com.examnation.helper;

import eduapp.examnation.com.examnation.R;

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

    public static int getBackGroungImageName(int id){
        if(id ==1){
            return R.drawable.biology_bg;
        }else if(id == 2){
            return R.drawable.chemistry_bg;
        }else if(id == 3){
            return R.drawable.physics_bg_new;
        }else if(id == 4){
            return R.drawable.math_bg;
        }
        return 0;
    }

    public static int getIconBySubjectId(int id){
        if(id ==1){
            return R.drawable.icons8microscope40;
        }else if(id == 2){
            return R.drawable.icons8testtube48;
        }else if(id == 3){
            return R.drawable.icons8physics40;
        }else if(id == 4){
            return R.drawable.icons8sigma48;
        }
        return 0;
    }
}

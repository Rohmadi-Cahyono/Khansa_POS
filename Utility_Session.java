package khansapos;

public class Utility_Session {
         private static String userId, userName, userLevel;
         private static Integer panelW,panelH;
         
//-----------------------userId------------------------------------------ 
    public static void setUserId(String userId){
        Utility_Session.userId = userId;        
    }    
    public static String getUserId(){
        return userId;
    }
 
//------------------------userName------------------------------------
    public static void setUserName(String userName){
        Utility_Session.userName=userName;        
    }    
    public static String getUserName(){
        return userName;
    }
    
//-------------------------userLevel------------------------------------    
    public static void setUserLevel(String userLevel){
        Utility_Session.userLevel=userLevel;        
    }    
    public static String getUserLevel(){
        return userLevel;
    }
 
//-----------ukuran panelUtama(JdesktopPane)------------  
    public static void setPanelW(int W){
        Utility_Session.panelW=W;        
    }    
    public static int getPanelW(){
        return panelW;
    }   
    
    public static void setPanelH(int H){
        Utility_Session.panelH=H;        
    }     
    public static int getPanelH(){
        return panelH;
    } 

    
}

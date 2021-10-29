package khansapos;

public class Utility_Session {
         private static String userId, userName, userLevel;
    
    public static void setUserId(String userId){
        Utility_Session.userId = userId;        
    }    
    public static String getUserId(){
    return userId;
    }
    
    public static void setUserName(String userName){
        Utility_Session.userName=userName;        
    }    
    public static String getUserName(){
    return userName;
    }
    
    public static void setUserLevel(String userLevel){
        Utility_Session.userLevel=userLevel;        
    }    
    public static String getUserLevel(){
    return userLevel;
    }
    
}

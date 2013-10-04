package org.bjdrgs.bjwt.authority.utils;  
  
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
  
/**  
 * 对密码进行加密和验证的类 
 */  
public class CipherUtil{  
      
    /** * 把inputString加密     */  
    public static String generatePassword(String inputString){
        return encodeByShiro(inputString);  
    }  
      
      /** 
       * 验证输入的密码是否正确 
     * @param password    加密后的密码 
     * @param inputString    输入的字符串 
     * @return    验证结果，TRUE:正确 FALSE:错误 
     */  
    public static boolean validatePassword(String password, String inputString){  
        if(password.equals(encodeByShiro(inputString))){  
            return true;  
        } else{  
            return false;  
        }  
    } 
    
    private static String encodeByShiro(String originString){
    	Hash hash = new Md5Hash(originString);
    	return hash.toHex();
    }
    
}  
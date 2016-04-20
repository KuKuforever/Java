import java.io.*;

public class Driver{
   public static void main(String args[]){
   MainThread mainT = null;
      try{
         mainT = new MainThread();
      }catch(IllegalArgumentException e){
         System.out.println("bug dectected");
      }
   }
}
import java.util.*;

class Clerk extends Thread {
static volatile     int online;
static long         time;
static boolean      closing  = false;
static Advanturer[] waitList = null;
private Thread      t        = null;
private int         size;

   Clerk(int id, int num){
      setName("clerk");
      setSize(num);
      online   = 0;
      waitList = new Advanturer[getSize()];
      setName("Clerk" + id);
      time = System.currentTimeMillis(); 
   }
   
   synchronized private void serve(){
   Advanturer temp = null;
     if(online > 0){
        msg(" is trying to take care of customer");
        temp = deque();
         if(temp != null){
            temp.setNeedAssistance(false);
         }
        msg(online + " are waiting");
     }
   }
   
   synchronized private Advanturer deque(){
   Advanturer temp = null;

      if(online > 0){
      temp = waitList[0];
         for(int i=0; i < online-1; i++){
            waitList[i] = waitList[i+1];
         }
      online--;
      }
      return temp;
   }
   
   synchronized static void joinLine(Advanturer adv){
      waitList[online] = adv;
      online++;
   }
   
@Override
   public void run(){
      msg(" is how available");
      
      while(!closing){
         try{
            Thread.sleep(100);
         }catch(InterruptedException e){
            msg(" starts working");
         }
         if(online > 0){
            serve();
         }
      }
      msg(" is going home");
   }

   public void setSize(int s){
      if(s < 1){
        throw new IllegalArgumentException("Clerk_set_size: Illegal argument encountered.");
      }else
      size = s;
   }
   
   public int getSize(){
      return size;
   }
   
   public void msg(String m){
      System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m); 
   }

}
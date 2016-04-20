import java.util.*;

class Dragon extends Thread {
static long     time;
static boolean  isPlaying  = false;
static boolean  goHome     = false;
private Thread  t          = null;
private int     challengers;

   Dragon(int chal){
      challengers = chal;
      setName("Dragon");
      time = System.currentTimeMillis(); 
   }

   private void chooseOpponent(){
   Random rand = null;
   int advID;
   
      rand   = new Random();
      advID  = rand.nextInt(challengers);
      if((MainThread.advList[advID] != null) && (MainThread.advList[advID].interrupted() == false) && MainThread.advList[advID].isAlive()){
         msg(" is picking advanturer " + MainThread.advList[advID].getName() + MainThread.advList[advID].getAdvId());
         isPlaying = true;
         MainThread.advList[advID].interrupt();
         while(!isPlaying){
            gaming();
         }
      }

   }
   
@Override
   public void run(){
      
      while(!goHome){
         try{
            msg(" zzZZZ");
            Thread.sleep(1000);
         }catch(InterruptedException e){
            msg(" woke up");
         }
         chooseOpponent();   
      }
      msg(" is going home");
   }
   
   private void gaming(){
   }
   
   public void msg(String m){
      System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m); 
   }
}
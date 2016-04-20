import java.util.*;

class Advanturer extends Thread{
public static final int VERY_LONG = 200000;

static  long       time;
private int        fortune;
private int        fortuneSize;
private int        advId;
private boolean    needAssistance = false;
private Thread     advanturer     = null;
private int[]      itemList       = null; //0 = stone, 1 = ring, 2 = chain, 3 = earrings
private String[]   itemName       = {"stone", "ring", "chain", "earrings"};


   Advanturer(String name, int id, int size){
   Random rand = null;
   
      rand        =  new Random();
      fortune     =  rand.nextInt(4);
      fortuneSize =  size;
      time        =  System.currentTimeMillis(); 
      itemList    =  new int[4];
      
      for(int i=0; i<2; i++){
         itemList[i] = 0;
      }
      
      setAdvId(id);
      setName(name);
      msg(id + " is now available");
   }
   
   private void playDiceGame(){
   Random rand = null;
   boolean win = false;
   int dragon;
   int advanturer;
   
        rand  =  new Random();
        
        while(!win){
           advanturer =  rand.nextInt(6);
           dragon     =  rand.nextInt(6);
        
           System.out.println(currentThread());
           
           if(advanturer > dragon){
              msg(getAdvId() + " wins the game");
              yield();
              msg(getAdvId() + " yields");
              setPriority(NORM_PRIORITY);
              win = true;
              Dragon.isPlaying = false;
           }else if(dragon > advanturer){
              msg(getAdvId() + " loses the game");
              if(getPriority() < 10)
                 setPriority(getPriority()+1);
           }
           
           msg(getAdvId() + " priority is now " + getPriority());
        }
        Dragon.isPlaying = false;
   } 
     
@Override
   public void run() {
     msg(getAdvId() + " has " + getFortune() + " fortunes");       
     while(fortune < fortuneSize){
        if(itemList[0]<1 || ((itemList[1] < 1) && (itemList[2] < 1) && (itemList[3] < 2))){
           fightDragon();
        }
        else{
              visitShop();
        }
        msg(getAdvId() + " has " + getFortune() + " fortunes");         
     }
     
     leave(); 
     
   }
   
   public void msg(String m){
      System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m); 
   }
   
   private void fightDragon(){
   Random rand = null;
   int    drop;
      try{
        rand  =  new Random();
        msg(getAdvId() + " is waiting for dragon");
        MainThread.advList[getAdvId()] = this;
        
        Thread.sleep(VERY_LONG);

      }catch(InterruptedException e){
          msg(getAdvId() + " encounters dragon");
       }
       
      msg(getAdvId() + " is fighting dragon");
      playDiceGame();
      drop  =  rand.nextInt(4);
      itemList[drop]++;
      
      for(int i = 0; i < 4; i++){
         msg(getAdvId() + " now has " + itemList[i] + " " + itemName[i]);

      }
   }
   
   synchronized private void leave(){
      msg(getAdvId() + " is leaving the town");
      
      for(int i = 0; i < MainThread.numAdv; i++){
         if(MainThread.advList[i].isAlive()){
            try{
               this.join();
               msg(getAdvId() + " is joining advanturer " + MainThread.advList[i].getAdvId());
            }catch (InterruptedException e) {
               msg(getAdvId() + " is trying to leave");
            }
            break;
         }
      }
      MainThread.numAdv--;
      System.out.println(MainThread.numAdv + " advanturers left");
      if(MainThread.numAdv == 0){
         Dragon.goHome = true;
         Clerk.closing = true;
      }
   }
   
   private void visitShop(){
   Random rand = null;
   
      rand  =  new Random();
      
      try{
        msg(getAdvId() + " going to shop");
        
        Thread.sleep((rand.nextInt(4)+1)*100);

      }catch(InterruptedException e){
          msg("ah");
       }
       
      setNeedAssistance(true);
      Clerk.joinLine(this);
      
      msg(" is waiting to get served");
      while(getNeedAssistance()){System.out.print(""); }
      msg(" is leaving the shop");
      
      if((itemList[0] > 0) && (itemList[1] > 0)){  //getting ring
         itemList[0]--;
         itemList[1]--;
      }else if((itemList[0] > 0) && (itemList[2] > 0)){ //getting necklace
         itemList[0]--;
         itemList[2]--;
      }else { // getting pair of earrings
         itemList[0]--;
         itemList[3]--;
      }
      setFortune(getFortune()+1);
   }
   
   private void setFortune(int f){
      fortune = f;
   }
   
   public void setNeedAssistance(boolean yes){
      needAssistance = yes;
   }
   
   public void setAdvId(int id){
      if(id >= 0)
         advId = id;
      else{
         throw new IllegalArgumentException("Advanturer: not a valid ID.");
      }
   }
   
   public int getAdvId(){
      return advId;
   }
   
   public boolean getNeedAssistance(){
      return needAssistance;
   }
   
   public int getFortune(){
      return fortune;
   }
}



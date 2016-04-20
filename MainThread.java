import java.util.*;

public class MainThread{
static  int          numClerk;
static  int          numAdv;
static  int          numFortune;
static  Clerk[]      clerkList = null;
static  Advanturer[] advList   = null;
private Clerk        clerk     =  null;
private Advanturer   adv       =  null;
private Dragon       dragon    =  null;

   MainThread(){
   Scanner keyboard = null;
   
      keyboard = new Scanner(System.in);
      
      System.out.println("Please enter the number of Advanturers: ");
      numAdv = keyboard.nextInt();
      
      System.out.println("Please enter the number of clerks: ");
      numClerk = keyboard.nextInt();
      
      System.out.println("Please enter the size of fortune: ");
      numFortune = keyboard.nextInt();
            
      advList = new Advanturer[numAdv];
      for(int i=0; i<numAdv; i++){
         advList[i] = new Advanturer("Adv", i, numFortune);
         advList[i].start();
      }
      
      clerkList = new Clerk[numClerk];
         for(int i=0; i<numClerk; i++){
         clerkList[i] = new Clerk(i, numAdv);
         clerkList[i].start();
      }

      dragon = new Dragon(numAdv);
      dragon.start();
     
   }
   
}
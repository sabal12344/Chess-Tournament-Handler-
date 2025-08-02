import java.util.Scanner;
class Player{
    double points;
    String name;
   
    int id;

    Player(String name,int id){
        this.name = name;
        this.id = id;
    }
   
    
}
class Tournament{
    Scanner sc = new Scanner(System.in);
    int bye_player=-1;
    
    int total_rounds;
    int players;//number of players 
    Player [] p ;
    Player [] p2 ;
   
    int [][][] schedule;


    Tournament(int players){
        this.players = players;
        if(players%2==1) {
            this.players++;
            bye_player = this.players;
        }
        total_rounds = this.players -1;

        this.p = new Player[players+1];

    this.p2 = new Player[players+1];

    this.schedule = new int [total_rounds+1][this.players/2+1][3];//1-based indexing

    }
    
    
    
     void setPlayers(){
        System.out.println("\nEnter the name of tournament participants :\n");
        int n = players;
        if(bye_player!=-1)
        n= players-1;

        for(int i=1;i<=n;i++){
            System.out.print("Player no. "+(i)+" : ");
            String name = sc.nextLine();
            //int id = 1;
            this.p[i]= new Player(name,i);
            this.p2[i]= this.p[i];
            
        }

     }
     int rotatorDecre(int i){
        if(i==2){
            return players;
        }
        return i-1;
     }
     int rotatorIncre(int i){
        if(i==players)
        {
            return 2;
        }
        return i+1;
     }
     void scheduler(){
       
        int n=players;
        
       for(int i=1;i<=total_rounds;i++){
        
        int circleplus =n;
        int circleminus =n;
      
        schedule[i][1][1]=1;
        schedule[i][1][2]= n;
        
       

   


        for(int j=2;j<=players/2;j++){
            circleplus =rotatorIncre(circleplus);
            schedule[i][j][1] = circleplus;
            circleminus = rotatorDecre(circleminus);
            schedule[i][j][2] = circleminus;
             
        }
        n--;
       }
    


     }

     
    

    
     void promptResults(){
        
        
        System.out.println("\nThis is where you enter results. Here are the instructions :\n\nPlayer1 wins : you press 1\nPlayer2 wins : you press 2\nDraw : any other keys");
        String bye_message = null;
        for(int i=1;i<=total_rounds;i++){
            System.out.println("\nIn round "+(i)+" :\n");

            for(int j=1;j<=players/2;j++){


                if((schedule[i][j][1]==bye_player)||(schedule[i][j][2]==bye_player)){

                    
                    int bye_id = schedule[i][j][1]==bye_player  ?  schedule[i][j][2] : schedule[i][j][1];
                p[bye_id].points++;
                bye_message = "\n\nThe player named "+p[bye_id].name +" is on bye and received a free point this round.";

                    continue;

                }
                

            System.out.print("Match number "+j+"\n\t"+p[schedule[i][j][1]].name+" (player 1)   vs   "+p[schedule[i][j][2]].name+"(player 2) :\n\t- ");

            int result = sc.nextInt();

            if(result == 1){
               this.p[schedule[i][j][1]].points++;
            }
            else if(result == 2){
                this.p[schedule[i][j][2]].points++;

            }
            else{
                this.p[schedule[i][j][1]].points += 0.5;
                this.p[schedule[i][j][2]].points += 0.5;

            } 

        

            }
            if(bye_message != null){
                System.out.println(bye_message);
            }
            standings(i);          

            
            

        }
     
       
        
        
     }

    void standings(int round){
        System.out.println("\n\nThe current standings after round "+round+"\n");
       for(int i =1;i<p2.length-1;i++){
        for(int j=i+1;j<p2.length;j++){
            if(this.p2[i].points < this.p2[j].points){
                Player temp = this.p2[i];
                this.p2[i]= this.p2[j];
                this.p2[j] = temp;
               

            }
        }
       }

       for(int i =1;i<p2.length;i++){
         System.out.println((i)+". "+this.p2[i].name+"\t"+this.p2[i].points+" pts");
       }
       
    }

}


public class TournamentManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the number of players : ");
        int players = sc.nextInt();
        Tournament t1 = new Tournament(players);
        t1.setPlayers();
        t1.scheduler();
       
            t1.promptResults();
        
        System.out.println("\n\nTournament successfully ended, Our winner is "+t1.p2[1].name+"!!!!! congrats.");


    }
    
    


    
}

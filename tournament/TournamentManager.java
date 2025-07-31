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
    int current_round;
    int total_rounds;
    int players;//number of players 
    Player [] p ;
    Player [] p2 ;
    int [][] record ;


    Tournament(int players){
        this.players = players;
        this.bye_player = players;
        if(players%2==0)
        this.total_rounds=players-1;
        else
        this.total_rounds =players;

        this.p = new Player[players];

    this.p2 = new Player[players];
    
   
    this.record = new int [players][this.total_rounds];
    }
    
    
    
     void setPlayers(){
        System.out.println("\nEnter the name of tournament participants :\n");
        for(int i=0;i<this.players;i++){
            System.out.print("Player no. "+(i+1)+" : ");
            String name = sc.nextLine();
            //int id = 1;
            this.p[i]= new Player(name,i+1);
            this.p2[i]= this.p[i];
            
        }

     }

     void recordKeeper(int id1, int id2){
               
              this.record[id1-1][this.current_round] = id2;
              this.record[id2 -1 ] [this.current_round] = id1;      


     }

     boolean alreadyPlayed(int id1, int id2){

    for (int i = 0; i < this.current_round; i++) {
        if (record[id1 - 1][i] == id2 || record[id2 - 1][i] == id1) {
            return true;
        }
    }
    return false;
        


     }

    void matchmaker() {
    int[][] matches = new int[this.players / 2][2];
    boolean[] ispaired = new boolean[this.players + 1]; 
    int a = 0;

    

    for (int i = 1; i <= this.players; i++) {
        
        if (ispaired[i] || i == bye_player){
            continue;
        }
        

        for (int j = i + 1; j <= this.players; j++) {
            if (ispaired[j] || j == bye_player){
                continue;
            }
            int final1=i,final2=j;
            matches[a][0] = final1;
                matches[a][1] = final2;
                ispaired[final1] = true;
                ispaired[final2] = true;
                
                

            
                
                
                a++;
                break;

                
        

        }
    }
    for(int i=0;i<players/2;i++){
        if(alreadyPlayed(matches[i][0], matches[i][1])){
            int w=i;
            while(w<players/2){
                if (!alreadyPlayed(matches[i][0],matches[w][1])) {
                    int temp=matches[i][1];
                    matches[i][1] = matches[w][1];
                    matches[w][1] = temp;
                    
                    break;
                    
                }
                w++;
            }
        }
    }
    System.out.println("\nThe scheduled matches for round " + (current_round + 1));
    for(int i =0;i<players/2;i++){
        System.out.println(p[matches[i][0]-1].name + " vs " + p[matches[i][1]-1].name);
        recordKeeper(matches[i][0], matches[i][1]);

    }

    


    if (players % 2 == 1) {
        System.out.println(p[bye_player - 1].name + " is getting a bye.");
    }

    promptResults(matches);
}

    
     void promptResults(int [][]matches){
        
        System.out.println("\nThis is where you enter results. Here are the instructions :\n\nPlayer1 wins : you press 1\nPlayer2 wins : you press 2\nDraw : any other keys");
        System.out.println("\nIn round "+(current_round+1)+" :\n");
        for(int i=0;i<players/2;i++){
            System.out.print("\n\nMatch number "+(i+1)+"\n\t"+p[matches[i][0]-1].name+" (player1) vs "+this.p[matches[i][1]-1].name+" (player2) : ");
            int result = sc.nextInt();

            if(result == 1){
               this.p[matches[i][0]-1].points++;
            }
            else if(result == 2){
                this.p[matches[i][1]-1].points++;

            }
            else{
                this.p[matches[i][0]-1].points += 0.5;
                this.p[matches[i][1]-1].points += 0.5;

            } 
            

        }
        if(players%2==1){
        System.out.println("\n"+p[bye_player-1].name+" got a bye and secured a free point.");
        this.p[bye_player-1].points++;
        bye_player--;
    }

        this.current_round++;
        
        standings();
        
        
        
     }

    void standings(){
        System.out.println("\n\nThe current standings after round "+this.current_round+"\n");
       for(int i =0;i<p2.length-1;i++){
        for(int j=i+1;j<p2.length;j++){
            if(this.p2[i].points < this.p2[j].points){
                Player temp = this.p2[i];
                this.p2[i]= this.p2[j];
                this.p2[j] = temp;
               

            }
        }
       }

       for(int i =0;i<p2.length;i++){
         System.out.println((i+1)+". "+this.p2[i].name+"\t"+this.p2[i].points+" pts");
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
        for(int i = 1; i<= t1.total_rounds;i++){
            t1.matchmaker();
        }
        System.out.println("\n\nTournament successfully ended, Our winner is "+t1.p2[0].name+"!!!!! congrats.");


    }
    
    


    
}

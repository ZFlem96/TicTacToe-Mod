package tictactoemod.game.tictactoemod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Game extends AppCompatActivity {
//    private class Records{
//        private String winner;
//
//    }
    private TextView playerView1, playerView2, cpuView1, cpuView2, victory, won;
    private ImageView space1,space2,space3,space4,space5,space6,space7,space8,space9;
    private boolean space1Taken = false, space2Taken = false, space3Taken = false, space4Taken = false, space5Taken = false,
            space6Taken = false, space7Taken = false, space8Taken = false, space9Taken = false, noSpaceLeft = false,
            playerXOrPlayerO = true, gameOver = false, draworWin = false;
    static ArrayList<String> loaddata;
    private String winnerData = "";
    private char[] spaces = new char[9];
    private Button playAgain, restart;
    private int spaceCheck=0,lastCheck=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playerView1 =(TextView)findViewById(R.id.playerView);
        playerView2 =(TextView)findViewById(R.id.playerView2);
        cpuView1 =(TextView)findViewById(R.id.cpuView);
        cpuView2 =(TextView)findViewById(R.id.cpuView2);
        playerView1.setText(MainActivity.human.getPlayerName()+"(");
        playerView2.setText(MainActivity.human.getPlayerSymbol().toUpperCase()+")");
        cpuView2.setText(MainActivity.CPU.getPlayerSymbol().toUpperCase()+")");
        space1 = (ImageView) findViewById(R.id.space1);
        space2 = (ImageView) findViewById(R.id.space2);
        space3 = (ImageView) findViewById(R.id.space3);
        space4 = (ImageView) findViewById(R.id.space4);
        space5 = (ImageView) findViewById(R.id.space5);
        space6 = (ImageView) findViewById(R.id.space6);
        space7 = (ImageView) findViewById(R.id.space7);
        space8 = (ImageView) findViewById(R.id.space8);
        space9 = (ImageView) findViewById(R.id.space9);
        victory = (TextView)findViewById(R.id.victoryID);
        won = (TextView)findViewById(R.id.wonID);
        playAgain = (Button)findViewById(R.id.playAgainBttn);
        restart = (Button)findViewById(R.id.restartBttn);
        loaddata = new ArrayList<>();
        load();
//        save();
    }
//public ArrayList<String> getLoaddata(){
//    return loaddata;
//}
    public void PlayAgain(View v){
             Intent intent = new Intent(this, Game.class);
                startActivity(intent);

    }
    public void Restart(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void endGameOptions() {
        won.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);
        restart.setVisibility(View.VISIBLE);
        String human = MainActivity.human.getPlayerName(), humanSymbol = MainActivity.human.getPlayerSymbol(),
                cpu = MainActivity.CPU.getPlayerName(), cpuSymbol = MainActivity.CPU.getPlayerSymbol();
        if (noSpaceLeft&&!draworWin) {
            won.setText("Draw!");
            winnerData = "DRAW|"+human+"("+humanSymbol+") vs "+cpu+"("+cpuSymbol+")";
        } else if (playerXOrPlayerO) {
            victory.setVisibility(View.VISIBLE);
            if (MainActivity.human.getPlayerSymbol().equalsIgnoreCase("X")) {
                winnerData = "Winner: "+human+"|"+human+"("+humanSymbol+") vs "+cpu+"("+cpuSymbol+")";
                victory.setText(MainActivity.human.getPlayerName());
            } else if (MainActivity.CPU.getPlayerSymbol().equalsIgnoreCase("X")) {
                victory.setText(MainActivity.CPU.getPlayerName());
                winnerData = "Winner: "+cpu+"|"+human+"("+humanSymbol+") vs "+cpu+"("+cpuSymbol+")";
            }
        } else if (!playerXOrPlayerO) {
            victory.setVisibility(View.VISIBLE);
            if (MainActivity.human.getPlayerSymbol().equalsIgnoreCase("O")) {
                victory.setText(MainActivity.human.getPlayerName());
                winnerData = "Winner: "+human+"|"+human+"("+humanSymbol+") vs "+cpu+"("+cpuSymbol+")";
            } else if (MainActivity.CPU.getPlayerSymbol().equalsIgnoreCase("O")) {
                {
                    winnerData = "Winner: "+cpu+"|"+human+"("+humanSymbol+") vs "+cpu+"("+cpuSymbol+")";
                    victory.setText(MainActivity.CPU.getPlayerName());
                }
            }

        }
        save();
    }

    public void check123(){
        if(space1Taken && space2Taken && space3Taken) {
            if (spaces[0] == spaces[1] && spaces[1] == spaces[2]) {
                gameOver = true;
                draworWin = true;
                if (spaces[0] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[0] == 'o'){
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[0] == spaces[1] && spaces[1] == spaces[2])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }
    public void check456(){
        if(space4Taken && space5Taken && space6Taken) {
            if (spaces[3] == spaces[4] && spaces[4] == spaces[5]) {
                gameOver = true;
                draworWin = true;

                if (spaces[3] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[3] == 'o') {
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[3] == spaces[4] && spaces[4] == spaces[5])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }
    public void check789(){
        if(space7Taken && space8Taken && space9Taken) {
            if (spaces[6] == spaces[7] && spaces[7] == spaces[8]) {
                gameOver = true;
                draworWin = true;

                if (spaces[6] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[6] == 'o') {
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[6] == spaces[7] && spaces[7] == spaces[8])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }

    public void check159(){
        if (space1Taken && space5Taken && space9Taken) {
            if (spaces[0] == spaces[4] && spaces[4] == spaces[8]) {
                gameOver = true;
                draworWin = true;

                if (spaces[0] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[0] == 'o'){
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[0] == spaces[4] && spaces[4] == spaces[8])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }
    public void check357(){
        if(space3Taken && space5Taken && space7Taken) {
            if (spaces[2] == spaces[4] && spaces[4] == spaces[6]) {
                gameOver = true;
                draworWin = true;

                if (spaces[2] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[2] == 'o'){
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[2] == spaces[4] && spaces[4] == spaces[6])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }

    public void check147(){
        if(space1Taken && space4Taken && space7Taken) {
            if (spaces[0] == spaces[3] && spaces[3] == spaces[6]) {
                gameOver = true;
                draworWin = true;

                if (spaces[0] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[0] == 'o'){
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[0] == spaces[3] && spaces[3] == spaces[6])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }
    public void check258(){
        if(space2Taken && space5Taken && space8Taken) {
            if (spaces[1] == spaces[4] && spaces[4] == spaces[7]) {
                gameOver = true;
                draworWin = true;

                if (spaces[1] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[1] == 'o'){
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[1] == spaces[4] && spaces[4] == spaces[7])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }
    public void check369(){
        if(space3Taken && space6Taken && space9Taken) {
            if (spaces[2] == spaces[5] && spaces[5] == spaces[8]) {
                gameOver = true;
                draworWin = true;

                if (spaces[2] == 'x') {
                    playerXOrPlayerO = true;
                } else if(spaces[2] == 'o'){
                    playerXOrPlayerO = false;
                }
            }
            else if (!(spaces[2] == spaces[5] && spaces[5] == spaces[8])&&noSpaceLeft){
                lastCheck++;
            }
        }
    }

    public void checkGame(){
        check123();
        check456();
        check789();
        check159();
        check357();
        check147();
        check258();
        check369();
        if(noSpaceLeft&&lastCheck>=9){
            gameOver = true;
        }
        if(gameOver){
            endGameOptions();
        }

    }

    public void takeSpace(View v){
        checkGame();
        switch (v.getId()){
            case R.id.space1:
                if (!gameOver){
                    checkGame();
                    if(!space1Taken&&!noSpaceLeft){
                 space1Taken =true;
                        spaceCheck++;
                        if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space1.setImageResource(R.drawable.x);
                                spaces[0] = 'x';
                            }
                            else{
                                space1.setImageResource(R.drawable.o);
                                spaces[0] = 'o';
                            }
                        }
                        if(spaceCheck==9){
                            noSpaceLeft=true;
                        }
                        checkGame();
                        cpuPick();
                    }

                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space2:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space2Taken) {
                        space2Taken =true;
                        spaceCheck++;
                        if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space2.setImageResource(R.drawable.x);
                                spaces[1] = 'x';
                            }
                            else{
                                space2.setImageResource(R.drawable.o);
                                spaces[1] = 'o';
                            }
                            }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();

                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space3:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space3Taken) {
                        spaceCheck++;
                        space3Taken =true;
                            if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space3.setImageResource(R.drawable.x);
                                spaces[2] = 'x';
                            }
                            else{
                                space3.setImageResource(R.drawable.o);
                                spaces[2] = 'o';
                            }
                            }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();
                                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space4:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space4Taken) {
                        space4Taken =true;
                        spaceCheck++;
                            if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space4.setImageResource(R.drawable.x);
                                spaces[3] = 'x';
                            }
                            else{
                                space4.setImageResource(R.drawable.o);
                                spaces[3] = 'o';
                            }
                        }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();

                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space5:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space5Taken) {
                        space5Taken =true;
                        spaceCheck++;
                            if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space5.setImageResource(R.drawable.x);
                                spaces[4] = 'x';
                            }
                            else{
                                space5.setImageResource(R.drawable.o);
                                spaces[4] = 'o';
                            }
                        }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();

                if(noSpaceLeft||spaceCheck>=9) {
                    noSpaceLeft = true;
                    checkGame();
                }
                break;
            case R.id.space6:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space6Taken) {
                        space6Taken =true;
                        spaceCheck++;
                            if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space6.setImageResource(R.drawable.x);
                                spaces[5] = 'x';
                            }
                            else{
                                space6.setImageResource(R.drawable.o);
                                spaces[5] = 'o';
                            }
                        }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();

                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space7:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space7Taken) {
                        spaceCheck++;
                        space7Taken =true;
                        if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space7.setImageResource(R.drawable.x);
                                spaces[6] = 'x';
                            }
                            else{
                                space7.setImageResource(R.drawable.o);
                                spaces[6] = 'o';
                            }
                        }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();
                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space8:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space8Taken) {
                        spaceCheck++;
                        space8Taken =true;
                        if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space8.setImageResource(R.drawable.x);
                                spaces[7] = 'x';
                            }
                            else{
                                space8.setImageResource(R.drawable.o);
                                spaces[7] = 'o';
                            }
                        }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();
                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
            case R.id.space9:
                if (!gameOver&&!noSpaceLeft) {
                    if (!space9Taken) {
                        spaceCheck++;
                        space9Taken =true;
                            if(MainActivity.human.getPlayerSymbol().equalsIgnoreCase("x")){
                                space9.setImageResource(R.drawable.x);
                                spaces[8] = 'x';
                            }
                            else{
                                space9.setImageResource(R.drawable.o);
                                spaces[8] = 'o';
                            }
                        }
                    }
                    if(spaceCheck==9){
                        noSpaceLeft=true;
                    }
                    checkGame();
                cpuPick();
                if(noSpaceLeft||spaceCheck>=9){
                    noSpaceLeft=true;
                    checkGame();
                }
                break;
        }

    }

    private void cpuPick(){
        if(!noSpaceLeft||spaceCheck<9){
            ArrayList<Integer> cpuchoice = new ArrayList<Integer>();
            for(int x =0;x<spaces.length;x++){
                if(spaces[x]!='x'&&spaces[x]!='o'){
                    cpuchoice.add(x);
                }
            }
            ArrayList<ImageView> spaceIV = new ArrayList<ImageView>();
            for(int x=0;x<cpuchoice.size();x++){
                if(cpuchoice.get(x)==0){
                    spaceIV.add(space1);
                } else if(cpuchoice.get(x)==1){
                    spaceIV.add(space2);
                } else if(cpuchoice.get(x)==2){
                    spaceIV.add(space3);
                } else if(cpuchoice.get(x)==3){
                    spaceIV.add(space4);
                } else if(cpuchoice.get(x)==4){
                    spaceIV.add(space5);
                } else if(cpuchoice.get(x)==5){
                    spaceIV.add(space6);
                } else if(cpuchoice.get(x)==6){
                    spaceIV.add(space7);
                } else if(cpuchoice.get(x)==7){
                    spaceIV.add(space8);
                } else if(cpuchoice.get(x)==8){
                    spaceIV.add(space9);
                }
            }
            Random random = new Random();
            int[] xopic = {R.drawable.o,R.drawable.x};
            int xo = 0;
            int pick = random.nextInt(spaceIV.size());
            if(MainActivity.CPU.getPlayerSymbol().equalsIgnoreCase("x")){
                xo = xopic[1];
                spaces[cpuchoice.get(pick)] = 'x';
            } else {
                xo = xopic[0];
                spaces[cpuchoice.get(pick)] = 'o';
            }
            if(cpuchoice.get(pick)==0){
                space1Taken = true;
                space1.setImageResource(xo);
            } else if(cpuchoice.get(pick)==1){
                space2Taken = true;
                space2.setImageResource(xo);

            } else if(cpuchoice.get(pick)==2){
                space3Taken = true;
                space3.setImageResource(xo);

            } else if(cpuchoice.get(pick)==3){
                space4Taken = true;
                space4.setImageResource(xo);
            } else if(cpuchoice.get(pick)==4){
                space5Taken = true;
                space5.setImageResource(xo);
            } else if(cpuchoice.get(pick)==5){
                space6Taken = true;
                space6.setImageResource(xo);
            } else if(cpuchoice.get(pick)==6){
                space7.setImageResource(xo);
                space7Taken = true;
            } else if(cpuchoice.get(pick)==7){
                space8.setImageResource(xo);
                space8Taken = true;
            } else if(cpuchoice.get(pick)==8){
                space9.setImageResource(xo);
                space9Taken = true;
            }
            spaceCheck++;
//            spaceIV.get(pick).setImageResource(xopic[xo]);
            if(spaceCheck==9){
                noSpaceLeft=true;
            }
            checkGame();
            if(noSpaceLeft||spaceCheck>=9){
                noSpaceLeft=true;
                checkGame();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.replayOption:
                PlayAgain(playAgain);
                break;
            case R.id.restartOption:
                Restart(restart);
                break;
            case R.id.exitOption:
                this.finishAffinity();
                break;
            case R.id.scoreOption:
                Intent intent = new Intent(this, ScoreList.class);
                startActivity(intent);
                break;
            default:
                return true;
        }
        return true;
    }

    public void save(){
//read winning results
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
//        choose how you want to save this data ie sqlite, timestamps to json, etc. | MM/dd/yyyy/hh:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy 'at' HH:mm:ss z");
        String time = sdf.format(new Date());
//        the object you pass in will need all the data of the game
        data.child("History").child(time).setValue(winnerData);
    }
    private void load(){
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("History");
        data.orderByKey().limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String var = dataSnapshot.getValue().toString();
//                System.out.println(dataSnapshot);
//                System.out.println(var);
                loaddata.add(var);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
//    test
}

package tictactoemod.game.tictactoemod;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static Player human, CPU;
    public EditText player1Edit;
    public Button play;
    public CheckBox x, o;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        human = new Player();
        CPU = new Player();
        player1Edit = (EditText)findViewById(R.id.playerEdit);
        play = (Button)findViewById(R.id.playBttn);
        x = (CheckBox)findViewById(R.id.xBox);
        o = (CheckBox)findViewById(R.id.oBox);
        sharedPreferences = this.getSharedPreferences("game.tictactoe", Context.MODE_PRIVATE);
    }

    public void setPlayerSymbols(View v){
        switch (v.getId()) {
            case R.id.xBox:
                human.setPlayerSymbol("X");
                CPU.setPlayerSymbol("O");
                x.setChecked(true);
                o.setChecked(false);
                break;
            case R.id.oBox:
                human.setPlayerSymbol("O");
                CPU.setPlayerSymbol("X");
                x.setChecked(false);
                o.setChecked(true);
                break;
        }
    }

    public void Play(View v){
        CPU.setPlayerName("CPU");
        if(v.getId()==R.id.playBttn){
            if(player1Edit.getText().toString().equalsIgnoreCase("")){
                if(human.getPlayerSymbol().equalsIgnoreCase("X")){
                    human.setPlayerName("XPlayer");
//                    CPU.setPlayerName("OPlayer");
                }
                else if(human.getPlayerSymbol().equalsIgnoreCase("O")){
                    human.setPlayerName("OPlayer");
//                    CPU.setPlayerName("XPlayer");
                }
            }
            else{
                human.setPlayerName(player1Edit.getText().toString());
            }
        }
        sharedPreferences.edit().putString("username1", human.getPlayerName()).apply();
        String username1 = sharedPreferences.getString("username1", "");
        Log.e("username1",username1);
        sharedPreferences.edit().putString("username2", CPU.getPlayerName()).apply();
        String username2 = sharedPreferences.getString("username2", "");
        Log.e("username2",username2);
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
}

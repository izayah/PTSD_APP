package com.example.ptsd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StressToolsActivity extends Activity {
Button breathing, muscle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_tools);
        /*breathing = (Button) findViewById(R.id.breathing_tool_button);
        breathing.setOnClickListener(new View.OnClickListener*/
    }

    public void startBreathingTool(View view) {
        Intent startBreathing = new Intent(StressToolsActivity.this, BreathingActivity.class);
        startActivity(startBreathing);
    }

    public void startMuscleTool(View view) {
        Intent startMuscle = new Intent(StressToolsActivity.this, MuscleRelaxationActivity.class);
        startActivity(startMuscle);
    }
}


/*------------------------------------------------------------------------------------------------*/
/*play=(Button) findViewById(R.id.play);
        pause=(Button) findViewById(R.id.pause);

        final MediaPlayer sound=MediaPlayer.create(this, R.raw.rain);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sound.start();

            }
        });
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sound.pause();

            }
        });
*/


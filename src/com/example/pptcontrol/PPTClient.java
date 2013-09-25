package com.example.pptcontrol;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
 
public class PPTClient extends Activity {
    private Button start;
    private Button escape;
    private Button forward;
    private Button back;
    private Socket sock;
    private ObjectOutputStream fromClient;
    private ObjectInputStream fromServer;
    private final static int RIGHT = 1;
    private final static int LEFT = 2;
    private final static int SHIFTF5 = 0;
    private final static int ESC = 3;
    RelativeLayout mouse ;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        try {
            //sock = new Socket(InetAddress.getByName("125.71.69.199"),2011);
            sock = new Socket(InetAddress.getByName("125.70.223.165"),2011);
            fromClient = new ObjectOutputStream(sock.getOutputStream());
            fromServer = new ObjectInputStream(sock.getInputStream());
             
            } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
         
        start = (Button)this.findViewById(R.id.start);
        escape = (Button)this.findViewById(R.id.escape);
        forward = (Button)this.findViewById(R.id.forward);
        back = (Button)this.findViewById(R.id.back);
        mouse = (RelativeLayout)this.findViewById(R.id.mouse);
        
        mouse.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				event.getAction();
				return false;
			}
        	
        });
         
        start.setOnClickListener(new Button.OnClickListener(){
 
            @Override
            public void onClick(View v) {
                Choices choice = new Choices(SHIFTF5);
                try {
                    fromClient.writeObject(choice);
                    System.out.println("send the start shift + f5");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
             
        });
         
        escape.setOnClickListener(new Button.OnClickListener(){
 
            @Override
            public void onClick(View arg0) {
                Choices  choice = new Choices(ESC);
                try {
                    fromClient.writeObject(choice);
                    System.out.println("send the escape");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        forward.setOnClickListener(new Button.OnClickListener(){
 
            @Override
            public void onClick(View v) {
                Choices choice = new Choices(RIGHT);
                try {
                    fromClient.writeObject(choice);
                    System.out.println("send the right (the next)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                 
            }
             
        });
        back.setOnClickListener(new Button.OnClickListener(){
 
            @Override
            public void onClick(View v) {
                Choices choice = new Choices(LEFT);
                try {
                    fromClient.writeObject(choice);
                    System.out.println("send the left (the last)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
     
    /**
     * ¼àÌýBACK¼ü
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {  
        if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("exit app");
            builder.setMessage("You will exit the app...");
            //builder.setIcon(R.drawable.stat_sys_warning);
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    System.exit(0);
                }
            });
            builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                 
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
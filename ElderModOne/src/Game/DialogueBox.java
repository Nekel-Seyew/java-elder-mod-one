/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Utilities.KeyBoard;
import Utilities.Vector2;
import java.awt.Rectangle;
import java.util.ArrayList;
import sgde.dialogue.DialogueInterface;

/**
 *
 * @author Nekel
 */
public class DialogueBox extends TextBox{
    DialogueInterface di;
    KeyBoard kb;
    boolean transitioned;
    
    boolean done;
    char keyWasDown;
    
    public DialogueBox(String diag, Vector2 centerlocation, Vector2 dimentions, int background,
            int type, int style, int textcolor,
            int delay, int size, int outlineThickness, int outlinecolor, int depth, KeyBoard key){
        super(centerlocation,dimentions,background,diag,type,style,textcolor,delay,size,outlineThickness,outlinecolor,depth);
        di = new DialogueInterface(diag);
        String giv = di.getCurrentResponse()+"\n";
        int i=1;
        for(String s: di.getCurrentOptions()){
            giv += i+") "+s+"\n";
            i++;
        }
        this.setText(giv);
        this.kb=key;
        this.transitioned=false;
        this.done=false;
        this.keyWasDown = 0;
    }
    
    @Override
    public void update(){
        super.update();
        if (!done) {
            //first, make sure we didn't just transition. Then, if we have not, get new input
            boolean notDown = true;
            if (!transitioned) {//check to see if any of the options are chosen. pick the first that is.
                for (int i = 0; i < di.getCurrentOptions().size(); i++) {
                    char c = Character.toString((char) ('1' + i)).charAt(0);
                    if (kb.isKeyDown(c)) {
                        di.PlayerChoice(di.getCurrentOptions().get(i));
                        this.transitioned = true;
                        this.keyWasDown = c;
                        //have changed, moving on
                        String giv = di.getCurrentResponse() + "\n";
                        int j = 1;
                        for (String s : di.getCurrentOptions()) {
                            giv += j + ") " + s + "\n";
                            j++;
                        }
                        this.setText(giv);
                        return;
                    }
                }
            }
//            for (int i = 0; i < di.getCurrentOptions().size(); i++) {
//                char c = Character.toString((char) ('1' + i)).charAt(0);
//            }
//            if(!notDown){
//                System.out.println("Hello, world!");
//            }
            if (transitioned && this.keyWasDown == 0) {
                transitioned = false;
            }
            if(this.keyWasDown != 0 && !this.kb.isKeyDown(keyWasDown)){
                keyWasDown = 0;
            }
        }
        this.done = di.isDone();
    }
    
    public boolean isDone(){
        return this.done;
    }
    
}

package game.control;

import game.text.Result;
import game.text.ResultGeneric;
import game.text.ResultPartial;
import game.text.TextGame;
import game.text.vampire.Vampire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Console implements Controller {
    private InputStream in = System.in;
    private OutputStream out = System.out;	
	
	private TextGame textGame;
	
	public Console(TextGame textGame) {
		this.textGame = textGame;
	}
	
    public void start() {
    	restart();
    	boolean finished = false;
    	while (!finished) {
	    	mainEventLoop();
	    	output("\n\nWould you like to try again? ");
	    	String choice = input().toLowerCase().substring(0, 1);
	    	if (choice.equals("y")) {
	    		restart();
	    	} else if (choice.equals("r")) {  // rewind the game ending to repeat last turn
	    		textGame.resetEnded();
	    	} else {
	    		finished = true;
	    	}
    	}   	
    }
    
    public void restart() {
    	String input;
    	
    	textGame.initialize();

    	output(textGame.welcome());
    	input = input().toLowerCase();
    	if (input.startsWith("y")) {
    		output(textGame.instructions());
    	}
    	output("\n");
    	output("\n"+textGame.takeTurn("look").getMessage());
    }    

    public void end() {
    	textGame.end(false);
    	output("\nGame suddenly ended by mysterious external forces. Goodbye.");
    }
    
    protected void mainEventLoop() {
    	String input;
    	
    	Result result = new ResultGeneric(true, "");
    	while (!textGame.isEnded()) {
    		if (result != null && !(result instanceof ResultPartial)) {    		
    			output("\n\nWhat do you want to do? ");
    		}
    		result = null;
    		input = input().trim();
    		if (!controllerAction(input)) {
	    		textGame.startTurn();
	    		result = this.textGame.takeTurn(input);
	    		output("\n"+result.getMessage());
	    		textGame.endTurn();
    		} else {
    			result = new ResultGeneric(true, "");
    		}
    	}
    }
    
    protected boolean controllerAction(String input) {
    	String lower = input.toLowerCase();
    	if (lower.startsWith("quit")) {
			output("\nAre you sure you want to quit and exit the game (y or n)? ");
			input = input();
			if (input.startsWith("y")) {
				// quit the game here
				end();
			} else {
				output("\nOK, let's keep exploring!\n");
			}
			return true;
    	}
    	return false;  // if no controller action was processed    
    }

	protected void output(String msg) {
		OutputStreamWriter output = new OutputStreamWriter(this.out);
		BufferedWriter writer = new BufferedWriter(output);
		try {
			writer.write(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	protected String input() {
		String string = "";
		InputStreamReader input = new InputStreamReader(this.in);
		BufferedReader reader = new BufferedReader(input);

		try	{
			string = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;		
	}
	
	
	public static void main(String[] args) {
		
		TextGame game = new Vampire(); // vampire is default
		
		if (args.length <= 0) {
			System.out.println("Usage: game.control.Console name_of_game");
			System.exit(1);
		}
		if (args[0].equalsIgnoreCase("vampire")) {
			game = new Vampire();
		} else {
			System.out.println("Unknown game "+args[0]+ ". Valid games are: vampire");
			System.exit(1);
		}
		
		final Console controller = new Console(game);
		controller.start();
		System.exit(0);
	}
}

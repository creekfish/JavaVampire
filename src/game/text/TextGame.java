package game.text;


public interface TextGame extends Game {
	
	public String welcome();
	public String instructions();
	public void start();
	public void end(boolean won, String message);
	public boolean ended();

	public void setGrammar(Grammar grammar);
	public String input();
	public String getWordPrefix(String word);	
	
	public void output(String msg);
}


package game.text;

public interface Grammar {
	public boolean parse(String input);
	public boolean isParsed();
	public Action getVerb();
	public Actionable getObject();	
}

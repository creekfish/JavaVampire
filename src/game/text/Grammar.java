package game.text;

public interface Grammar {
	public boolean parse(String input);
	public boolean isParsed();
	public ActionInitial getVerb();
	public Actionable getObject();
	public String getUnparsedText();
}

package game.text;


public interface TextGame extends Game {
	
	/**
	 * Returns welcome message to display at the start of the game.
	 * 
	 * @return 
	 */
	public String welcome();

	/**
	 * Returns text instructions for playing the game.
	 * 
	 * @return
	 */
	public String instructions();
	
	/**
	 * End the game with the given win/loss state.
	 * 
	 * @param won true if player won the game, false if the game is lost
	 */
	public void end(boolean won);
	
	/**
	 * Return true if the game has ended.
	 * 
	 * @return
	 */
	public boolean isEnded();
	
	/**
	 * Reset the to a "not ended" state.
	 */
	public void resetEnded();
	
	/**
	 * Set the grammar to be used to process game input.
	 * 
	 * @param grammar
	 */
	public void setGrammar(Grammar grammar);

	/**
	 * Get the "prefix" of a word parsed from the game input.
	 * Prefix is a fixed length beginning part of the word that is
	 * used to match verbs and nouns to produce actions.  May
	 * return the original string if a prefix match is not desired.
	 * 
	 * @param word
	 * @return
	 */
	public String getWordPrefix(String word);	

	/**
	 * Start a player's turn in the game.
	 * Take any actions needed to set up a turn.
	 * 
	 * @return false if the game has ended, otherwise true
	 */
	public boolean startTurn();
	
	/**
	 * Process player input fully, changing game state to reflect
	 * the result of the turn, and return a string that represents the
	 * result of the player taking the turn.
	 * 
	 * @param textInput the input string from the game controller / player
	 * @return the result of the turn
	 */
	public Result takeTurn(String textInput);

	/**
	 * End a player's turn in the game.
	 * Take any actions needed to before the next turn is taken.
	 * 
	 * @return false if the game has ended, otherwise true
	 */
	public boolean endTurn();
}


package engine;

public interface ICase {
	/*
	 * Composite design-pattern in order to use Case and CaseGroup same way.
	 */
	boolean calculateCase();
	char getLogicOperator();
}

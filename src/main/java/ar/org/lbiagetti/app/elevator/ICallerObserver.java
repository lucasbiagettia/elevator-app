package ar.org.lbiagetti.app.elevator;

public interface ICallerObserver {

	void goUp(Step step);

	void goDown(Step step);

}

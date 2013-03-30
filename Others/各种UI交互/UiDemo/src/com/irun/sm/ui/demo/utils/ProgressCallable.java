package com.irun.sm.ui.demo.utils;

public interface ProgressCallable<T> {
	public T call(final IProgressListener iProgressListener);

}

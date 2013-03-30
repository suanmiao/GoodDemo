package com.irun.sm.ui.demo.utils;

import java.util.concurrent.Callable;

import android.app.Activity;

/***
 * @author huangsm
 * @date 2012-11-12
 * @email huangsanm@gmail.com
 * @desc activity基类
 */
public abstract class BaseActivity extends Activity {

	/**
	 * 
	 * @param pTitleResourceID
	 * @param pMessageResourceID
	 * @param pCallable
	 * @param pCallback
	 */
	protected <T> void doAsync(final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback) {
		this.doAsync(pTitleResourceID, pMessageResourceID, pCallable, pCallback, null);
	}
	
	protected <T> void doAsync(final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback, final boolean pCancelable) {
		ActivityUtils.doAsync(this, getString(pTitleResourceID), getString(pMessageResourceID), pCallable, pCallback, pExceptionCallback, pCancelable);
	}

	/**
	 * 
	 * @param pTitleResourceID
	 * @param pMessageResourceID
	 * @param pCallable
	 * @param pCallback
	 * @param pExceptionCallback
	 */
	protected <T> void doAsync(final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(this, pTitleResourceID, pMessageResourceID, pCallable, pCallback, pExceptionCallback);
	}
	
	/**
	 * 
	 * @param pTitleResourceID
	 * @param pIconResourceID
	 * @param pCallable
	 * @param pCallback
	 */
	protected <T> void doProgressAsync(final int pTitleResourceID, final int pIconResourceID, final ProgressCallable<T> pCallable, final Callback<T> pCallback) {
		this.doProgressAsync(pTitleResourceID, pIconResourceID, pCallable, pCallback, null);
	}

	/**
	 * 
	 * @param pTitleResourceID
	 * @param pIconResourceID
	 * @param pCallable
	 * @param pCallback
	 * @param pExceptionCallback
	 */
	protected <T> void doProgressAsync(final int pTitleResourceID, final int pIconResourceID, final ProgressCallable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doProgressAsync(this, pTitleResourceID, pIconResourceID, pCallable, pCallback, pExceptionCallback);
	}
	
}

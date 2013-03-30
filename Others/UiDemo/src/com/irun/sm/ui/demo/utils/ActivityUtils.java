package com.irun.sm.ui.demo.utils;

import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class ActivityUtils {
	
	/**
	 * 去除标题栏
	 * @param mActivity
	 */
	public static void requestNotTitleBar(final Activity mActivity){
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 全屏
	 * @param mActivity
	 */
	public static void requestFullscreen(final Activity mActivity) {
		final Window window = mActivity.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		window.requestFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 屏幕亮度
	 * @param mActivity
	 * @param pScreenBrightness
	 */
	public static void setScreenBrightness(final Activity mActivity, final float pScreenBrightness) {
		final Window window = mActivity.getWindow();
		final WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
		windowLayoutParams.screenBrightness = pScreenBrightness;
		window.setAttributes(windowLayoutParams);
	}

	public static void keepScreenOn(final Activity mActivity) {
		mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public static <T> void doAsync(final Context pContext, final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doAsync(pContext, pTitleResourceID, pMessageResourceID, pCallable, pCallback, null, false);
	}

	public static <T> void doAsync(final Context pContext, final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback, null, false);
	}

	public static <T> void doAsync(final Context pContext, final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback, final boolean pCancelable) {
		ActivityUtils.doAsync(pContext, pTitleResourceID, pMessageResourceID, pCallable, pCallback, null, pCancelable);
	}

	public static <T> void doAsync(final Context pContext, final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable, final Callback<T> pCallback, final boolean pCancelable) {
		ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback, null, pCancelable);
	}

	public static <T> void doAsync(final Context pContext, final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(pContext, pTitleResourceID, pMessageResourceID, pCallable, pCallback, pExceptionCallback, false);
	}

	public static <T> void doAsync(final Context pContext, final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(pContext, pTitle, pMessage, pCallable, pCallback, pExceptionCallback, false);
	}

	public static <T> void doAsync(final Context pContext, final int pTitleResourceID, final int pMessageResourceID, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback, final boolean pCancelable) {
		ActivityUtils.doAsync(pContext, pContext.getString(pTitleResourceID), pContext.getString(pMessageResourceID), pCallable, pCallback, pExceptionCallback, pCancelable);
	}

	public static <T> void doAsync(final Context pContext, final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback, final boolean pCancelable) {
		new AsyncTask<Void, Void, T>() {
			private ProgressDialog mPD;
			private Exception mException = null;

			@Override
			public void onPreExecute() {
				this.mPD = ProgressDialog.show(pContext, pTitle, pMessage, true, pCancelable);
				if(pCancelable) {
					this.mPD.setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(final DialogInterface pDialogInterface) {
							pExceptionCallback.onCallback(new Exception());
							pDialogInterface.dismiss();
						}
					});
				}
				super.onPreExecute();
			}

			@Override
			public T doInBackground(final Void... params) {
				try {
					return pCallable.call();
				} catch (final Exception e) {
					this.mException = e;
				}
				return null;
			}

			@Override
			public void onPostExecute(final T result) {
				try {
					this.mPD.dismiss();
				} catch (final Exception e) {
					Log.e("Error", e.getMessage(), e);
					//Debug.e("Error", e);
				}

				if(this.isCancelled()) {
					this.mException = new Exception();
				}

				if(this.mException == null) {
					pCallback.onCallback(result);
				} else {
					if(pExceptionCallback == null) {
						Log.e("Error", mException.getMessage(), mException);
						//Debug.e("Error", this.mException);
					} else {
						pExceptionCallback.onCallback(this.mException);
					}
				}

				super.onPostExecute(result);
			}
		}.execute((Void[]) null);
	}
	
	public static <T> void doProgressAsync(final Context pContext, final int pTitleResourceID, final int pIconResourceID, final ProgressCallable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doProgressAsync(pContext, pTitleResourceID, pIconResourceID, pCallable, pCallback, null);
	}

	public static <T> void doProgressAsync(final Context pContext, final CharSequence pTitle, final int pIconResourceID, final ProgressCallable<T> pCallable, final Callback<T> pCallback) {
		ActivityUtils.doProgressAsync(pContext, pTitle, pIconResourceID, pCallable, pCallback, null);
	}

	public static <T> void doProgressAsync(final Context pContext, final int pTitleResourceID, final int pIconResourceID, final ProgressCallable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doProgressAsync(pContext, pContext.getString(pTitleResourceID), pIconResourceID, pCallable, pCallback, pExceptionCallback);
	}

	public static <T> void doProgressAsync(final Context pContext, final CharSequence pTitle, final int pIconResourceID, final ProgressCallable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		new AsyncTask<Void, Integer, T>() {
			private ProgressDialog mPD;
			private Exception mException = null;

			@Override
			public void onPreExecute() {
				this.mPD = new ProgressDialog(pContext);
				this.mPD.setTitle(pTitle);
				this.mPD.setIcon(pIconResourceID);
				this.mPD.setIndeterminate(false);
				this.mPD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				this.mPD.show();
				super.onPreExecute();
			}

			@Override
			public T doInBackground(final Void... params) {
				try {
					return pCallable.call(new IProgressListener() {
						@Override
						public void onProgressChanged(final int pProgress) {
							onProgressUpdate(pProgress);
						}
					});
				} catch (final Exception e) {
					this.mException = e;
				}
				return null;
			}

			@Override
			public void onProgressUpdate(final Integer... values) {
				this.mPD.setProgress(values[0]);
			}

			@Override
			public void onPostExecute(final T result) {
				try {
					this.mPD.dismiss();
				} catch (final Exception e) {
					Log.e("Error", e.getMessage(), e);
					//Debug.e("Error", e);
					/* Nothing. */
				}

				if(this.isCancelled()) {
					this.mException = new Exception();
				}

				if(this.mException == null) {
					pCallback.onCallback(result);
				} else {
					if(pExceptionCallback == null) {
						Log.e("Error", mException.getMessage(), mException);
						//Debug.e("Error", this.mException);
					} else {
						pExceptionCallback.onCallback(this.mException);
					}
				}

				super.onPostExecute(result);
			}
		}.execute((Void[]) null);
	}

	public static <T> void doAsync(final Context pContext, final int pTitleResourceID, final int pMessageResourceID, final AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		ActivityUtils.doAsync(pContext, pContext.getString(pTitleResourceID), pContext.getString(pMessageResourceID), pAsyncCallable, pCallback, pExceptionCallback);
	}

	public static <T> void doAsync(final Context pContext, final CharSequence pTitle, final CharSequence pMessage, final AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
		final ProgressDialog pd = ProgressDialog.show(pContext, pTitle, pMessage);
		pAsyncCallable.call(new Callback<T>() {
			@Override
			public void onCallback(final T result) {
				try {
					pd.dismiss();
				} catch (final Exception e) {
					Log.e("Error", e.getMessage(), e);
					//Debug.e("Error", e);
					/* Nothing. */
				}

				pCallback.onCallback(result);
			}
		}, pExceptionCallback);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

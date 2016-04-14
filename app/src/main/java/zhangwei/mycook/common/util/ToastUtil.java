package zhangwei.mycook.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

	private static Toast toast;

	public static void showLongToast(final Context context, final String text) {
		Runnable mUiThread = new Runnable() {

			@Override
			public void run() {
				if (toast != null) {
					// Close the view if it's showing, or don't show it if it
					// isn't showing yet
					toast.cancel();
				}

				if (context == null || TextUtils.isEmpty(text)) {
					return;
				}

				toast = Toast.makeText(
						context == context.getApplicationContext() ? context : context.getApplicationContext(), text,
						Toast.LENGTH_LONG);

				// toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
				// toast.setDuration(Toast.LENGTH_LONG);
				// toast.setView(layout);
				toast.show();
			}
		};

		if (Looper.myLooper() == Looper.getMainLooper()) {
			mUiThread.run();
		} else {
			new Handler(Looper.getMainLooper()).post(mUiThread);
		}

	}

	public static void showLongToast(final Context context, final int text) {
		Runnable mUiThread = new Runnable() {

			@Override
			public void run() {
				if (toast != null) {
					// Close the view if it's showing, or don't show it if it
					// isn't showing yet
					toast.cancel();
				}
				if (context == null) {
					return;
				}

				toast = Toast.makeText(
						context == context.getApplicationContext() ? context : context.getApplicationContext(), text,
						Toast.LENGTH_LONG);

				// toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
				// toast.setDuration(Toast.LENGTH_LONG);
				// toast.setView(layout);
				toast.show();
			}
		};

		if (Looper.myLooper() == Looper.getMainLooper()) {
			mUiThread.run();
		} else {
			new Handler(Looper.getMainLooper()).post(mUiThread);
		}

	}

//	public static void showShortToast(final Context context, final String text) {
//		Runnable mUiThread = new Runnable() {
//
//			@Override
//			public void run() {
//				if (toast != null) {
//					// Close the view if it's showing, or don't show it if it
//					// isn't showing yet
//					toast.cancel();
//				}
//
//				if (context == null || TextUtils.isEmpty(text)) {
//					return;
//				}
//
//				toast = Toast.makeText(
//						context == context.getApplicationContext() ? context : context.getApplicationContext(), text,
//						Toast.LENGTH_SHORT);
//
//				// toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
//				// toast.setDuration(Toast.LENGTH_LONG);
//				// toast.setView(layout);
//				toast.show();
//			}
//		};
//
//		if (Looper.myLooper() == Looper.getMainLooper()) {
//			mUiThread.run();
//		} else {
//			new Handler(Looper.getMainLooper()).post(mUiThread);
//		}
//	}
//
//	public static void showShortToast(final Context context, final int text) {
//		Runnable mUiThread = new Runnable() {
//
//			@Override
//			public void run() {
//				if (toast != null) {
//					// Close the view if it's showing, or don't show it if it
//					// isn't showing yet
//					toast.cancel();
//				}
//
//				if (context == null) {
//					return;
//				}
//				toast = Toast.makeText(
//						context == context.getApplicationContext() ? context : context.getApplicationContext(), text,
//						Toast.LENGTH_SHORT);
//
//				// toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
//				// toast.setDuration(Toast.LENGTH_LONG);
//				// toast.setView(layout);
//				toast.show();
//			}
//		};
//
//		if (Looper.myLooper() == Looper.getMainLooper()) {
//			mUiThread.run();
//		} else {
//			new Handler(Looper.getMainLooper()).post(mUiThread);
//		}
//
//	}
}

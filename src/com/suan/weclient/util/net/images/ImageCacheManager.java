package com.suan.weclient.util.net.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 * Implementation of volley's ImageCache interface. This manager tracks the
 * application image loader and cache.
 * 
 * @author Trey Robinson
 * 
 */
public class ImageCacheManager {

	private static ImageCacheManager mInstance;

	public static final String CACHE_USER_PROFILE = "userProfile";

	public static final String CACHE_MESSAGE_PROFILE = "messageProfile";
	public static final String CACHE_MESSAGE_CONTENT = "messageContent";

	/**
	 * Image cache used for local image storage
	 */
	private DiskLruImageCache mDiskCache;
	
	private BitmapLruCache mBitmapLruCache;

	/**
	 * @return instance of the cache manager
	 */
	public static ImageCacheManager getInstance() {
		if (mInstance == null)
			mInstance = new ImageCacheManager();

		return mInstance;
	}

	/**
	 * Initializer for the manager. Must be called prior to use.
	 * 
	 * @param context
	 *            application context
	 * @param uniqueName
	 *            name for the cache location
	 * @param cacheSize
	 *            max size for the cache
	 * @param compressFormat
	 *            file type compression format.
	 * @param quality
	 */
	public void init(Context context, String uniqueName, int cacheSize,
			CompressFormat compressFormat, int quality) {
		mDiskCache = new DiskLruImageCache(context, uniqueName, cacheSize,
				compressFormat, quality);
		mBitmapLruCache = new BitmapLruCache(cacheSize);
	}

	public Bitmap getDiskBitmap(String key) {
		try {
			return mDiskCache.getBitmap(createKey(key));
		} catch (NullPointerException e) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}

	public void putDiskBitmap(String key, Bitmap bitmap) {
		try {
			mDiskCache.put(createKey(key), bitmap);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}


	public Bitmap getRomBitmap(String key) {
		try {
			return mBitmapLruCache.getBitmap(createKey(key));
		} catch (NullPointerException e) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}

	public void putRomBitmap(String key, Bitmap bitmap) {
		try {
			mBitmapLruCache.put(createKey(key), bitmap);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}
	/**
	 * Creates a unique cache key based on a url value
	 * 
	 * @param url
	 *            url to be used in key creation
	 * @return cache key value
	 */
	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}

}

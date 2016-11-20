/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * <p/>
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * <p/>
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.vanguardmatrix.engine.android;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;

import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.data.BaseManagerInterface;
import org.vanguardmatrix.engine.android.data.BaseUIListener;
import org.vanguardmatrix.engine.android.data.LogManager;
import org.vanguardmatrix.engine.android.data.NetworkException;
import org.vanguardmatrix.engine.android.data.OnClearListener;
import org.vanguardmatrix.engine.android.data.OnCloseListener;
import org.vanguardmatrix.engine.android.data.OnErrorListener;
import org.vanguardmatrix.engine.android.data.OnInitializedListener;
import org.vanguardmatrix.engine.android.data.OnLoadListener;
import org.vanguardmatrix.engine.android.data.OnLowMemoryListener;
import org.vanguardmatrix.engine.android.data.OnTimerListener;
import org.vanguardmatrix.engine.android.data.OnUnloadListener;
import org.vanguardmatrix.engine.android.data.OnWipeListener;
import org.vanguardmatrix.engine.android.receiver.VgmBaseReceiver;
import org.vanguardmatrix.engine.android.service.XabberService;
import org.vanguardmatrix.engine.utils.ImageLoadingHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Base entry point.
 *
 * @author alexander.ivanov
 */
public class Application extends MultiDexApplication {

    @SuppressWarnings("deprecation")
    public static final int SDK_INT = Integer.valueOf(Build.VERSION.SDK);
    public static final String TAG = "IamKarachi Application";
    static Handler hdlr;
    private static Application instance;
    private static Context app_context;
    /**
     * Where data load was requested.
     */
    private static boolean serviceStarted;
    private final ArrayList<Object> registeredManagers;
    /**
     * Thread to execute tasks in background..
     */
    private final ExecutorService backgroundExecutor;
    /**
     * Handler to execute runnable in UI thread.
     */
    private final Handler handler;
    long address;
    String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    VgmBaseReceiver mVgmBaseReceiver = new VgmBaseReceiver();
    /**
     * Unmodifiable collections of managers that implement some common
     * interface.
     */
    private Map<Class<? extends BaseManagerInterface>, Collection<? extends BaseManagerInterface>> managerInterfaces;
    private Map<Class<? extends BaseUIListener>, Collection<? extends BaseUIListener>> uiListeners;
    /**
     * Whether application was initialized.
     */
    private boolean initialized;
    /**
     * Whether user was notified about some action in contact list activity
     * after application initialization.
     */
    private boolean notified;
    /**
     * Whether application is to be closed.
     */
    private boolean closing;
    /**
     * Whether {@link #onServiceDestroy()} has been called.
     */
    private boolean closed;
    private final Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            for (OnTimerListener listener : getManagers(OnTimerListener.class))
                listener.onTimer();
            if (!closing)
                startTimer();
        }

    };
    /**
     * Future for loading process.
     */
    private Future<Void> loadFuture;

    public Application() {
        instance = this;
        serviceStarted = false;
        initialized = false;
        notified = false;
        closing = false;
        closed = false;
        uiListeners = new HashMap<Class<? extends BaseUIListener>, Collection<? extends BaseUIListener>>();
        managerInterfaces = new HashMap<Class<? extends BaseManagerInterface>, Collection<? extends BaseManagerInterface>>();
        registeredManagers = new ArrayList<Object>();

        handler = new Handler();
        backgroundExecutor = Executors
                .newSingleThreadExecutor(new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable runnable) {
                        Thread thread = new Thread(runnable,
                                "Background executor service");
                        thread.setPriority(Thread.MIN_PRIORITY);
                        thread.setDaemon(true);
                        return thread;
                    }
                });

        // hdlr = new Handler();
    }

    public static Application getInstance() {

        if (instance == null) {

            if (XabberService.appTempInstance != null) {
                instance = XabberService.appTempInstance;
                // XabberService.appTempInstance = null;
                return instance;
            }
            Log.e("aw",
                    "instance==nulll at VidyoSampleApplication.getInstance()");
            throw new IllegalStateException();
        } else {
            // Log.e("aw", "instance!=nulll");
        }
        if (XabberService.appTempInstance != null) {
            instance = XabberService.appTempInstance;
            // XabberService.appTempInstance = null;
        }

        return instance;

    }

    public static boolean isAppStarted() {
        return serviceStarted;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Whether application is initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    private void onLoad() {
        for (OnLoadListener listener : getManagers(OnLoadListener.class)) {
            LogManager.i(listener, "onLoad");
            listener.onLoad();
        }
    }

    public void ready() {
        initialized = true;
    }

    private void onInitialized() {
        initialized = true;
        for (OnInitializedListener listener : getManagers(OnInitializedListener.class)) {
            LogManager.i(listener, "onInitialized");
            listener.onInitialized();
        }
        XabberService.getInstance().changeForeground();
        startTimer();
    }

    private void onClose() {
        LogManager.i(this, "onClose");
        Log.e("awapp", "onClose");
        for (Object manager : registeredManagers)
            if (manager instanceof OnCloseListener)
                ((OnCloseListener) manager).onClose();
        closed = true;

        try {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onUnload() {
        LogManager.i(this, "onUnload");
        Log.e("awapp", "onUnload");
        for (Object manager : registeredManagers)
            if (manager instanceof OnUnloadListener)
                ((OnUnloadListener) manager).onUnload();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * @return <code>true</code> only once per application life. Subsequent
     * calls will always returns <code>false</code>.
     */
    public boolean doNotify() {
        if (notified)
            return false;
        notified = true;
        return true;
    }

    /**
     * Starts data loading in background if not started yet.
     *
     * @return
     */
    public void onServiceStarted() {
        if (serviceStarted)
            return;
        serviceStarted = true;
        LogManager.i(this, "onStart");

        loadFuture = backgroundExecutor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    onLoad();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Throw exceptions in UI thread if any.
                            onInitialized();
                            try {
                                loadFuture.get();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                }
                return null;
            }
        });

    }

    /**
     * Requests to close application in some time in future.
     */
    public void requestToClose() {
        closing = true;
        stopService(XabberService.createIntent(this));
    }

    /**
     * @return Whether application is to be closed.
     */
    public boolean isClosing() {
        return closing;
    }

    /**
     * Returns whether system contact storage is supported.
     * <p/>
     * Note:
     * <p/>
     * Please remove *_CONTACTS, *_ACCOUNTS, *_SETTINGS permissions,
     * SyncAdapterService and AccountAuthenticatorService together from manifest
     * file.
     *
     * @return
     */
    public boolean isContactsSupported() {
        return SDK_INT >= 5
                && checkCallingOrSelfPermission("android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        try {
//            RealmConfiguration config = new RealmConfiguration.Builder(this).build();
//            Realm.setDefaultConfiguration(config);
//
//        } catch (Exception e) {
//
//        }
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        ArrayList<String> contactManager = new ArrayList<String>();
        TypedArray contactManagerClasses = getResources().obtainTypedArray(
                R.array.contact_managers);
        for (int index = 0; index < contactManagerClasses.length(); index++)
            contactManager.add(contactManagerClasses.getString(index));
        contactManagerClasses.recycle();

        TypedArray managerClasses = getResources().obtainTypedArray(
                R.array.managers);
        for (int index = 0; index < managerClasses.length(); index++)
            if (isContactsSupported()
                    || !contactManager
                    .contains(managerClasses.getString(index)))
                try {
                    Class.forName(managerClasses.getString(index));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
        managerClasses.recycle();

        TypedArray tableClasses = getResources().obtainTypedArray(
                R.array.tables);
        for (int index = 0; index < tableClasses.length(); index++)
            try {
                Class.forName(tableClasses.getString(index));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        tableClasses.recycle();

        initImageLoader(getApplicationContext());

//        initTwitterFabric();

        initFacebookSDK();

        IntentFilter filter = new IntentFilter(ACTION);
//        activity.getApplication().registerReceiver(mVgmBaseReceiver, filter);
        LocalBroadcastManager.getInstance(this).registerReceiver(mVgmBaseReceiver, filter);

    }

    private void initFacebookSDK() {
        // com.facebook.FacebookSdk.sdkInitialize(getApplicationContext());
    }

    private void initTwitterFabric() {
//		Fabric.with(this, new TweetComposer());
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_image)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.loading_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.defaultDisplayImageOptions(defaultOptions);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.diskCacheFileCount(500);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

        ImageLoadingHandler.initialize();
    }

    @Override
    public void onLowMemory() {
        for (OnLowMemoryListener listener : getManagers(OnLowMemoryListener.class))
            listener.onLowMemory();
        super.onLowMemory();
    }

    /**
     * Service have been destroyed.
     */
    public void onServiceDestroy() {
        if (closed)
            return;
        onClose();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mVgmBaseReceiver);

//        try {
//            FileTransfer.stopListener();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        runInBackground(new Runnable() {
            @Override
            public void run() {
                onUnload();
            }
        });
    }

    @Override
    public void onTerminate() {
        requestToClose();
        super.onTerminate();
    }

    /**
     * Start periodically callbacks.
     */
    private void startTimer() {
        runOnUiThreadDelay(timerRunnable, OnTimerListener.DELAY);
    }

    /**
     * Register new manager.
     *
     * @param manager
     */
    public void addManager(Object manager) {
        registeredManagers.add(manager);
    }

    /**
     * @param cls Requested class of managers.
     * @return List of registered manager.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseManagerInterface> Collection<T> getManagers(
            Class<T> cls) {
        if (closed)
            return Collections.emptyList();
        Collection<T> collection = (Collection<T>) managerInterfaces.get(cls);
        if (collection == null) {
            collection = new ArrayList<T>();
            for (Object manager : registeredManagers)
                if (cls.isInstance(manager))
                    collection.add((T) manager);
            collection = Collections.unmodifiableCollection(collection);
            managerInterfaces.put(cls, collection);
        }
        return collection;
    }

    /**
     * Request to clear application data.
     */
    public void requestToClear() {
        runInBackground(new Runnable() {
            @Override
            public void run() {
                clear();
            }
        });
    }

    private void clear() {
        for (Object manager : registeredManagers)
            if (manager instanceof OnClearListener)
                ((OnClearListener) manager).onClear();
    }

    /**
     * Request to wipe all sensitive application data.
     */
    public void requestToWipe() {
        runInBackground(new Runnable() {
            @Override
            public void run() {
                clear();
                for (Object manager : registeredManagers)
                    if (manager instanceof OnWipeListener)
                        ((OnWipeListener) manager).onWipe();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends BaseUIListener> Collection<T> getOrCreateUIListeners(
            Class<T> cls) {
        Collection<T> collection = (Collection<T>) uiListeners.get(cls);
        if (collection == null) {
            collection = new ArrayList<T>();
            uiListeners.put(cls, collection);
        }
        return collection;
    }

    /**
     * @param cls Requested class of listeners.
     * @return List of registered UI listeners.
     */
    public <T extends BaseUIListener> Collection<T> getUIListeners(Class<T> cls) {
        if (closed)
            return Collections.emptyList();
        return Collections.unmodifiableCollection(getOrCreateUIListeners(cls));
    }

    /**
     * Register new listener.
     * <p/>
     * Should be called from {@link Activity#onResume()}.
     *
     * @param cls
     * @param listener
     */
    public <T extends BaseUIListener> void addUIListener(Class<T> cls,
                                                         T listener) {
        getOrCreateUIListeners(cls).add(listener);
    }

    /**
     * Unregister listener.
     * <p/>
     * Should be called from {@link Activity#onPause()}.
     *
     * @param cls
     * @param listener
     */
    public <T extends BaseUIListener> void removeUIListener(Class<T> cls,
                                                            T listener) {
        getOrCreateUIListeners(cls).remove(listener);
    }

    /**
     * Notify about error.
     *
     * @param resourceId
     */
    public void onError(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (OnErrorListener onErrorListener : getUIListeners(OnErrorListener.class))
                    onErrorListener.onError(resourceId);
            }
        });
    }

    /**
     * Notify about error.
     *
     * @param networkException
     */
    public void onError(NetworkException networkException) {
        LogManager.exception(this, networkException);
        onError(networkException.getResourceId());
    }

    /**
     * Submits request to be executed in background.
     *
     * @param runnable
     */
    public void runInBackground(final Runnable runnable) {
        backgroundExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    LogManager.exception(runnable, e);
                }
            }
        });
    }

    /**
     * Submits request to be executed in UI thread.
     *
     * @param runnable
     */
    public void runOnUiThread(final Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * Submits request to be executed in UI thread.
     *
     * @param runnable
     * @param delayMillis
     */
    public void runOnUiThreadDelay(final Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }

}

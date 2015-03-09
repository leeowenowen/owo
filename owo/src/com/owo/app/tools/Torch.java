package com.owo.app.tools;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.owo.app.common.ContextManager;

public class Torch
{
    private static final String WAKE_LOCK_TAG = "TORCH_WAKE_LOCK";

    private static Camera sCamera;
    private static Camera.Parameters sParameters;
    private static SurfaceView sSurfaceView;
    private static WindowManager.LayoutParams sLayoutParams;
    private static WakeLock mWakeLock;
    private static WindowManager swWindowManager;

    public static boolean isOn()
    {
        return sParameters == null ? false : Camera.Parameters.FLASH_MODE_TORCH == sParameters.getFlashMode();
    }

    public static void turnOn()
    {
        ensureSurfaceView();
        ensureCamera();
        if (sCamera == null)
        {
            return;
        }
        swWindowManager = ContextManager.systemWindowManager();
        swWindowManager.addView(sSurfaceView, sLayoutParams);
        sCamera.startPreview();
        sParameters = sCamera.getParameters();
        if (sParameters == null)
        {
            return;
        }
        List<String> flashModes = sParameters.getSupportedFlashModes();
        if (flashModes == null)
        {
            return;
        }
        String flashMode = sParameters.getFlashMode();
        if (!Parameters.FLASH_MODE_TORCH.equals(flashMode))
        {
            // Turn on the flash
            if (flashModes.contains(Parameters.FLASH_MODE_TORCH))
            {
                sParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
                sCamera.setParameters(sParameters);
                startWakeLock();
            }
        }
    }

    public static void turnOff()
    {
        if (sCamera == null || sParameters == null)
        {
            return;
        }
        List<String> flashModes = sParameters.getSupportedFlashModes();
        String flashMode = sParameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null)
        {
            return;
        }
        if (!Parameters.FLASH_MODE_OFF.equals(flashMode))
        {
            // Turn off the flash
            if (flashModes.contains(Parameters.FLASH_MODE_OFF))
            {
                sParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                sCamera.setParameters(sParameters);
                sCamera.stopPreview();
                sCamera.release();
                stopWakeLock();
                swWindowManager.removeView(sSurfaceView);
                sCamera = null;
                sParameters = null;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void ensureSurfaceView()
    {
        if (sSurfaceView == null)
        {
            sSurfaceView = new SurfaceView(ContextManager.context());
            SurfaceHolder surfaceHolder = sSurfaceView.getHolder();
            surfaceHolder.addCallback(new SurfaceCallBack());
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            sLayoutParams = new WindowManager.LayoutParams();
            sLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            sLayoutParams.format = PixelFormat.RGBA_8888;
            sLayoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            sLayoutParams.width = 1;
            sLayoutParams.height = 1;
            sLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
    }

    private static void ensureCamera()
    {
        if (sCamera == null)
        {
            try
            {
                sCamera = Camera.open();
            }
            catch (RuntimeException e)
            {
            }
        }
    }

    private static void startWakeLock()
    {
        if (mWakeLock == null)
        {
            PowerManager pm = (PowerManager) ContextManager.systemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
        }
        mWakeLock.acquire();
    }

    private static void stopWakeLock()
    {
        if (mWakeLock != null)
        {
            mWakeLock.release();
        }
    }

    private static class SurfaceCallBack implements SurfaceHolder.Callback
    {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int I, int J, int K)
        {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder)
        {
            try
            {
                sCamera.setPreviewDisplay(holder);
            }
            catch (IOException e)
            {
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        {
        }
    }
}

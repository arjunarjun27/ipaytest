package com.example.arjun_mu.ipaytest;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by arjun_mu on 5/15/2017.
 */

public class RequestGenerator {

    protected static String getNewRequestId(Context context)
    {
	/*
	 * an immutable universally unique identifier String representation.
	 */
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("android-id", androidId);
        String currentTime = "" + System.nanoTime();
        String randomUUID = UUID.randomUUID().toString();
        String key = androidId + currentTime + randomUUID;
        return getMd5Hash(key);
    }

    public static String getMd5Hash(String input)
    {
        try
        {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }
}

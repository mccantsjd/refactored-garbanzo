package com.suas;

import android.content.Context;

import com.secneo.sdk.Helper;

public class Application extends android.app.Application {

    @Override
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(paramContext);
        Helper.install(Application.this);
    }

}
package com.merabills.managers;

import android.content.Context;

import com.merabills.interfaces.IFileManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public class FileManagerModule {

    @Provides
    IFileManager providesFileManager(@ApplicationContext Context context) {
        return new FileManager(context);
    }
}

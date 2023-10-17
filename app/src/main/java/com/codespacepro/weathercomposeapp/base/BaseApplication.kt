package com.codespacepro.weathercomposeapp.base

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.codespacepro.weathercomposeapp.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            .crossfade(enable = true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.03)
                    .strongReferencesEnabled(enable = true)
                    .weakReferencesEnabled(enable = true)
                    .build()
            }
            .networkCachePolicy(policy = CachePolicy.ENABLED)
            .networkObserverEnabled(enable = true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.01)
                    .directory(cacheDir)
                    .build()
            }
            .addLastModifiedToFileCacheKey(enable = true)
            .placeholder(R.drawable.image)
            .error(R.drawable.report)
            .build()
    }
}
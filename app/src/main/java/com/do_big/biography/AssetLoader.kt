package com.do_big.biography

import android.content.res.AssetManager
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

interface AssetLoader {
    fun getNames(): Stories
}

private const val FILENAME = "mock/storiesName.json"

class AssetLoaderImpl(private val assetManager: AssetManager) : AssetLoader {
    override fun getNames(): Stories {
        return loadConfig(FILENAME, Stories::class.java)
    }

    private fun <T> loadConfig(fileName: String, clazz: Class<T>): T {
        return Gson().fromJson(BufferedReader(InputStreamReader(assetManager.open(fileName))), clazz)
    }
}

package com.fuckcoolapk.view

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.widget.ImageView
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.DownloadUtil
import com.fuckcoolapk.utils.GetUtil
import com.fuckcoolapk.utils.LogUtil
import java.io.File
import java.lang.Exception
import java.net.URL
import kotlin.math.ceil

typealias callback = (AdjustImageView) -> Unit

open class AdjustImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        drawable?.let {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = ceil(width.toDouble() * it.intrinsicHeight / it.intrinsicWidth).toInt()
            setMeasuredDimension(width, height)
        } ?: super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setUrl(url: String, callback: callback) {
        val imageDirectory = context.cacheDir.path + "/image_manager_disk_cache/"
        val fileName = url.substring(url.indexOf("//") + 2).replace("/", "-")
        if (File(imageDirectory + fileName).isFile) {
            setImageBitmap(BitmapFactory.decodeFile(imageDirectory + fileName))
            callback(this)
        } else {
            Thread {
                DownloadUtil().download(url, imageDirectory, fileName, object : DownloadUtil.OnDownloadListener {
                    override fun onDownloadSuccess(file: File?) {
                        post {
                            setImageBitmap(BitmapFactory.decodeFile(file?.path))
                            callback(this@AdjustImageView)
                        }
                    }

                    override fun onDownloading(progress: Int) {}
                    override fun onDownloadFailed(e: Exception?) {
                        LogUtil.e(e)
                    }
                })
            }.start()
        }
    }

    class Builder(private val mContext: Context = CoolContext.context, private val block: AdjustImageView.() -> Unit) {
        fun build() = AdjustImageView(mContext).apply(block)
    }

    init {
        adjustViewBounds = true
        scaleType = ScaleType.FIT_XY
    }
}
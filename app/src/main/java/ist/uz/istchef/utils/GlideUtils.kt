package ist.uz.istchef.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import de.hdodenhof.circleimageview.CircleImageView
import ist.uz.istchef.R
import kotlinx.android.synthetic.main.activity_main.*

class GlideUtils {
    companion object{
        fun loadImage(imageView: ImageView, url: String?){
            if (url == null){
                imageView.setImageDrawable(null)
                return
            }
            Glide.with(imageView).load(url)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("JW ERR", url + " | " + e?.localizedMessage)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .placeholder(R.drawable.placeholder)
                .into(imageView)
        }

        fun loadCircleImage(imageView: CircleImageView, url: String?){
            if (url == null){
                imageView.setImageDrawable(null)
                return
            }
            Glide.with(imageView).load(url).into(imageView)
        }
    }
}
package com.app.assessment.test.models.movie


import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.assessment.test.BuildConfig
import com.app.assessment.test.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * This class is used to access data from server and store data in local database using room
 */
@Parcelize
@Entity
data class MovieItem(

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	var timeStamp: Long? = null
) : Parcelable {
	companion object {

		/**
		 * This method is used for loading image from server to imageview using glide library.
		 * Image is center cropped for better display
		 */
		@JvmStatic
		@BindingAdapter("app:imageUrl")
		fun loadImage(view: ImageView, posterPath: String?) {
			view.transitionName = posterPath
			Glide.with(view.context)
				.load(BuildConfig.IMAGE_URL + posterPath)
				.apply(RequestOptions()
					.placeholder(R.drawable.grass_door_logo)
					.centerCrop()
					.useUnlimitedSourceGeneratorsPool(true))
				.into(view)
		}

		/**
		* This method is used for loading image from server to imageview using glide library.
		* Image is optionally fit to center for better display
		*/
		@JvmStatic
		@BindingAdapter("app:imageFullUrl")
		fun loadFullImage(view: ImageView, posterPath: String?) {
			view.transitionName = posterPath
			Glide.with(view.context)
				.load(BuildConfig.IMAGE_URL + posterPath)
				.apply(RequestOptions()
					.placeholder(R.drawable.grass_door_logo)
					.fitCenter()
					.useUnlimitedSourceGeneratorsPool(true))
				.into(view)
		}
	}
}

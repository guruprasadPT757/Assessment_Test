package com.app.assessment.test.models.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideoItem>? = null
)
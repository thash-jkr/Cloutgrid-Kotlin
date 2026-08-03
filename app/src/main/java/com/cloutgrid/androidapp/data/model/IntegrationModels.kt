package com.cloutgrid.androidapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class InstagramPageModel(
    val id: Int,
    @SerialName("ig_user_id") val igUserId: String,
    val username: String,
    @SerialName("profile_picture_url") val profilePicture: String,
    val followers: Int,
    val followings: Int,
    @SerialName("media_count") val mediaCount: Int,
    @SerialName("insights_raw") val insights: List<ProfileInsightModel>
)

@Serializable
data class InsightValue(
    val value: Int
) {
    val id: String get() = UUID.randomUUID().toString()
}

@Serializable
data class ProfileInsightModel(
    val id: String,
    val name: String,
    val title: String,
    val period: String,
    val description: String,
    @SerialName("total_value") val totalValue: InsightValue
)

@Serializable
data class InstagramMediaModel(
    val id: Int,
    val owner: Int,
    @SerialName("media_id") val mediaId: String,
    @SerialName("media_type") val mediaType: String,
    @SerialName("media_url") val mediaUrl: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String,
    val link: String,
    val caption: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("comments_count") val commentsCount: Int,
    @SerialName("insights_raw") val insights: List<MediaInsightModel>
)

@Serializable
data class MediaInsightModel(
    val id: String,
    val name: String,
    val title: String,
    val period: String,
    val description: String,
    val values: List<InsightValue>
)

@Serializable
data class YoutubeChannelModel(
    val id: Int,
    val title: String,
    @SerialName("channel_id") val channelId: String,
    val description: String,
    @SerialName("profile_picture_url") val profilePicture: String,
    @SerialName("banner_url") val banner: String,
    @SerialName("subscriber_count") val subscriberCount: Int,
    @SerialName("view_count") val viewCount: Int,
    @SerialName("video_count") val videoCount: Int
)

@Serializable
data class YoutubeMediaModel(
    val id: Int,
    @SerialName("media_id") val mediaId: String,
    val title: String,
    val description: String,
    @SerialName("thumbnail_url") val thumbnail: String,
    val views: Int,
    val likes: Int,
    val comments: Int
)

@Serializable
data class InstagramResponseModel(
    @SerialName("fb_page") val fbPage: String,
    @SerialName(value="ig_page") val igPage: String
)

@Serializable
data class InstagramPageResponse(
    @SerialName("profile_data") val profileData: InstagramPageModel
)

@Serializable
data class InstagramMediaResponse(
    val media: List<InstagramMediaModel>
)

@Serializable
data class YoutubeChannelResponse(
    @SerialName("channel_data") val channelData: YoutubeChannelModel
)

@Serializable
data class YoutubeMediaResponse(
    @SerialName("media_data") val data: List<YoutubeMediaModel>
)
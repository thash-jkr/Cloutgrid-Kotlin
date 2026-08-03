package com.cloutgrid.androidapp.data.repository

import com.cloutgrid.androidapp.data.model.*
import com.cloutgrid.androidapp.data.network.APIService
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntegrationRepository @Inject constructor(
    private val apiService: APIService,
    private val authRepository: AuthRepository
) {
    val instagramPage = mutableStateOf<InstagramPageModel?>(null)
    val instagramMedia = mutableStateListOf<InstagramMediaModel>()
    val youtubeChannel = mutableStateOf<YoutubeChannelModel?>(null)
    val youtubeMedia = mutableStateListOf<YoutubeMediaModel>()

    suspend fun connectInstagram(): InstagramResponseModel {
        val response: InstagramResponseModel = apiService.request(
            endpoint = "/instagram/connect/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )

        authRepository.setInstagramConnected(true)
        return response
    }

    suspend fun disconnectInstagram() {
        authRepository.setInstagramConnected(false)
        instagramPage.value = null
        instagramMedia.clear()

        apiService.request<EmptyResponse>(
            endpoint = "/auth/instagram/disconnect/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )
    }

    suspend fun purgeInstagram() {
        authRepository.setInstagramConnected(false)
        instagramPage.value = null
        instagramMedia.clear()

        apiService.request<EmptyResponse>(
            endpoint = "/auth/instagram/purge/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )
    }

    suspend fun fetchInstagramProfile() {
        apiService.request<EmptyResponse>(
            endpoint = "/instagram/profile/fetch/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )
    }

    suspend fun readInstagramProfile(username: String): InstagramPageModel {
        val response: InstagramPageResponse = apiService.request(
            endpoint = "/instagram/profile/read/$username/",
            method = "GET",
            requireAuth = true
        )
        return response.profileData
    }

    suspend fun loadOwnInstagramProfile(username: String) {
        instagramPage.value = readInstagramProfile(username)
    }

    suspend fun fetchInstagramMedia() {
        apiService.request<EmptyResponse>(
            endpoint = "/instagram/media/fetch/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )
    }

    suspend fun readInstagramMedia(username: String): List<InstagramMediaModel> {
        val response: InstagramMediaResponse = apiService.request(
            endpoint = "/instagram/media/read/$username/",
            method = "GET",
            requireAuth = true
        )
        return response.media
    }

    suspend fun loadOwnInstagramMedia(username: String) {
        val media = readInstagramMedia(username)
        instagramMedia.clear()
        instagramMedia.addAll(media)
    }

    suspend fun fetchYoutubeChannel() {
        apiService.request<EmptyResponse>(
            endpoint = "/youtube/channel/fetch/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )
    }

    suspend fun readYoutubeChannel(username: String): YoutubeChannelModel {
        val response: YoutubeChannelResponse = apiService.request(
            endpoint = "/youtube/channel/read/$username/",
            method = "GET",
            requireAuth = true
        )
        return response.channelData
    }

    suspend fun loadOwnYoutubeChannel(username: String) {
        youtubeChannel.value = readYoutubeChannel(username)
    }

    suspend fun fetchYoutubeMedia() {
        apiService.request<EmptyResponse>(
            endpoint = "/youtube/media/fetch/",
            method = "POST",
            body = emptyMap<String, String>(),
            requireAuth = true
        )
    }

    suspend fun readYoutubeMedia(username: String): List<YoutubeMediaModel> {
        val response: YoutubeMediaResponse = apiService.request(
            endpoint = "/youtube/media/read/$username/",
            method = "GET",
            requireAuth = true
        )
        return response.data
    }

    suspend fun loadOwnYoutubeMedia(username: String) {
        val media = readYoutubeMedia(username)
        youtubeMedia.clear()
        youtubeMedia.addAll(media)
    }
}
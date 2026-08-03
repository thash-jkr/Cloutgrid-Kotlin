package com.cloutgrid.androidapp.ui.screens.integration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloutgrid.androidapp.data.model.*
import com.cloutgrid.androidapp.data.repository.AuthRepository
import com.cloutgrid.androidapp.data.repository.IntegrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntegrationManager @Inject constructor(
    private val integrationRepository: IntegrationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val user get() = authRepository.user.value

    val instagramPage get() = integrationRepository.instagramPage.value
    val instagramMedia get() = integrationRepository.instagramMedia
    val youtubeChannel get() = integrationRepository.youtubeChannel.value
    val youtubeMedia get() = integrationRepository.youtubeMedia

    var otherInstagramPage by mutableStateOf<InstagramPageModel?>(null)
        private set
    val otherInstagramMedia = mutableStateListOf<InstagramMediaModel>()

    var otherYoutubeChannel by mutableStateOf<YoutubeChannelModel?>(null)
        private set
    val otherYoutubeMedia = mutableStateListOf<YoutubeMediaModel>()

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _successMessages = Channel<String>(Channel.BUFFERED)
    val successMessages = _successMessages.receiveAsFlow()

    fun connectInstagram() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = integrationRepository.connectInstagram()
                _successMessages.send("@${response.igPage} connected")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun disconnectInstagram() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.disconnectInstagram()
                _successMessages.send("Instagram disconnected")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun purgeInstagram() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.purgeInstagram()
                _successMessages.send("Instagram disconnected")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchInstagramProfile() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.fetchInstagramProfile()
                _successMessages.send("Instagram Profile fetched successfully")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadOwnInstagramProfile(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.loadOwnInstagramProfile(username)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun readOtherInstagramProfile(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                otherInstagramPage = integrationRepository.readInstagramProfile(username)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchInstagramMedia() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.fetchInstagramMedia()
                _successMessages.send("Instagram Media fetched successfully")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadOwnInstagramMedia(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.loadOwnInstagramMedia(username)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun readOtherInstagramMedia(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val media = integrationRepository.readInstagramMedia(username)
                otherInstagramMedia.clear()
                otherInstagramMedia.addAll(media)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchYoutubeChannel() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.fetchYoutubeChannel()
                _successMessages.send("YouTube channel details fetched successfully")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadOwnYoutubeChannel(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.loadOwnYoutubeChannel(username)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun readOtherYoutubeChannel(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                otherYoutubeChannel = integrationRepository.readYoutubeChannel(username)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchYoutubeMedia() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.fetchYoutubeMedia()
                _successMessages.send("YouTube media details fetched successfully")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadOwnYoutubeMedia(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                integrationRepository.loadOwnYoutubeMedia(username)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun readOtherYoutubeMedia(username: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val media = integrationRepository.readYoutubeMedia(username)
                otherYoutubeMedia.clear()
                otherYoutubeMedia.addAll(media)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }
}
package com.cloutgrid.androidapp.ui.screens.messaging

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloutgrid.androidapp.data.model.ConversationModel
import com.cloutgrid.androidapp.data.model.MessageModel
import com.cloutgrid.androidapp.data.repository.AuthRepository
import com.cloutgrid.androidapp.data.repository.ChatRepository
import com.cloutgrid.androidapp.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatManager @Inject constructor(
    authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {
    val user = authRepository.user
    val token = authRepository.access

    val results = searchRepository.results

    val chats = chatRepository.chats
    val messages = mutableStateListOf<MessageModel>()

    var isLoading by mutableStateOf(false)
        private set
    var socketConnected by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var newConversation by mutableStateOf<ConversationModel?>(null)
        private set

    init {
        viewModelScope.launch {
            chatRepository.socketConnected.collect { connected ->
                socketConnected = connected
            }
        }

        viewModelScope.launch {
            chatRepository.incomingMessages.collect { incoming ->
                if (messages.none { it.id == incoming.id }) {
                    messages.add(0, incoming)
                }
            }
        }
    }

    fun clearNewConversation() {
        newConversation = null
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                searchRepository.handleSearch(query)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchConversations() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                chatRepository.fetchConversations()
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun createConversation(id: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                newConversation = chatRepository.createConversation(id)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchMessages(id: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = chatRepository.fetchMessages(id)
                messages.clear()
                messages.addAll(response)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun connectWebSocket(conversationId: String) {
        viewModelScope.launch {
            val currentToken = token.first()
            if (currentToken != null) {
                chatRepository.connectWebSocket(conversationId, currentToken)
            } else {
                errorMessage = "Not authenticated"
            }
        }
    }

    fun disconnectWebSocket() {
        chatRepository.disconnectWebSocket()
    }

    fun sendLiveMessage(content: String) {
        chatRepository.sendLiveMessage(content)
    }

    override fun onCleared() {
        super.onCleared()
        disconnectWebSocket()
    }
}
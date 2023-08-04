package br.com.emersonmendes.hovertranslation.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "TranslationService", storages = [Storage("hover-translation.xml")])
class TranslationService : PersistentStateComponent<TranslationState> {
    private var state = TranslationState()

    fun addKeyValue(key: String, value: String) {
        state.map[key] = value
    }

    fun getValue(key: String): String {
        return state.map.getOrDefault(key, key)
    }

    override fun getState(): TranslationState {
        return state
    }

    override fun loadState(state: TranslationState) {
        this.state = state
    }
}

data class TranslationState(var map: MutableMap<String, String> = mutableMapOf())
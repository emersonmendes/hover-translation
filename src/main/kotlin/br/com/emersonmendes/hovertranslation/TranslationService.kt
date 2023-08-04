package br.com.emersonmendes.hovertranslation

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "TranslationService", storages = [Storage("br.com.emersonmendes.hovertranslation.xml")])
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
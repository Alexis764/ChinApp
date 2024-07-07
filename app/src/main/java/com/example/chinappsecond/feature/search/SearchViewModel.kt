package com.example.chinappsecond.feature.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chinappsecond.R
import com.example.chinappsecond.core.NewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }


    private val _newList = mutableListOf<NewModel>()
    val newList: List<NewModel> = _newList

    init {
        val newListJsonText = readFile()
        generateNewList(newListJsonText)
    }

    private fun readFile(): String {
        return appContext.assets.open("new_data_list.json")
            .bufferedReader()
            .use { it.readText() }
    }

    private fun generateNewList(newListJsonText: String) {
        val gson = Gson()

        _newList.addAll(
            gson.fromJson<List<NewModel>>(
                newListJsonText,
                object : TypeToken<List<NewModel>>() {}.type
            ).filter { it.hot }
        )
    }


    fun loadImage(newModel: NewModel) {
        val itemIndexList = _newList.indexOf(newModel)
        val imageName = searchImageName(newModel.text)
        _newList[itemIndexList] = _newList[itemIndexList].copy(image = searchIdImage(imageName))
    }

    private fun searchImageName(text: String): String {
        val initialWord = "[/images/"
        val indexInitialNameImage = text.indexOf(initialWord)

        val finishWord = "]"
        val indexFinishNameImage = text.indexOf(finishWord, indexInitialNameImage)

        return text.substring(
            (indexInitialNameImage + initialWord.length),
            indexFinishNameImage
        )
    }

    private fun searchIdImage(imageName: String): Int {
        return when (imageName) {
            "new_1_image_1.png" -> R.drawable.new_1_image_1
            "new_16_image_1.png" -> R.drawable.new_16_image_1
            "new_3_image_1.png" -> R.drawable.new_3_image_1
            "new_2_image_1.png" -> R.drawable.new_2_image_1
            "new_4_image_1.png" -> R.drawable.new_4_image_1
            "new_5_image_1.png" -> R.drawable.new_5_image_1
            "new_6_image_1.png" -> R.drawable.new_6_image_1
            "new_7_image_1.png" -> R.drawable.new_7_image_1
            "new_8_image_1.png" -> R.drawable.new_8_image_1
            "new_9_image_1.png" -> R.drawable.new_9_image_1
            "new_11_image_1.png" -> R.drawable.new_11_image_1
            "new_12_image_1.png" -> R.drawable.new_12_image_1
            "new_13_image_1.png" -> R.drawable.new_13_image_1
            "new_14_image_1.png" -> R.drawable.new_14_image_1
            "new_15_image_1.png" -> R.drawable.new_15_image_1
            else -> R.drawable.new_1_image_1
        }
    }

}
package com.example.chinappsecond.feature.search_result

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chinappsecond.R
import com.example.chinappsecond.core.NewModel
import com.example.chinappsecond.core.NewModelDate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _searchResultText = MutableLiveData<String>()
    val searchResultText: LiveData<String> = _searchResultText

    private val _isTimeAscending = MutableLiveData<Boolean>()
    val isTimeAscending: LiveData<Boolean> = _isTimeAscending

    private val _newList = mutableListOf<NewModelDate>()
    val newList: List<NewModelDate> = _newList


    fun onSearchResultTextChanged(text: String) {
        _searchResultText.value = text
    }


    fun onIsTimeAscendingChanged(value: Boolean) {
        _isTimeAscending.value = value
        if (value) {
            _newList.sortBy { it.time }
        } else {
            _newList.sortByDescending { it.time }
        }
    }


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
            ).map { it.toDate() }
        )

        _newList.sortBy { it.time }
    }


    fun loadImage(newModel: NewModelDate) {
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
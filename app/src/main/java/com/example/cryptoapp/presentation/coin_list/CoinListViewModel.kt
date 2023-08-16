package com.example.cryptoapp.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.Resource
import com.example.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
):ViewModel() {

    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state:State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
//        getCoinsUseCase.invoke().onEach {result->
//            when(result){
//                is Resource.Success ->{
//                    _state.value = CoinListState(
//                        coins = result.data?: emptyList()
//                    )
//                }
//                is Resource.Loading ->{
//                    _state.value = CoinListState(isLoading = true)
//                }
//                else ->{
//                    _state.value = CoinListState(error = result.message?:"Unexpected error")
//                }
//            }
//        }.launchIn(viewModelScope)
        viewModelScope.launch {
            getCoinsUseCase().collect{result->
                when(result){
                    is Resource.Success ->{
                        _state.value = CoinListState(
                            coins = result.data?: emptyList()
                        )
                    }
                    is Resource.Loading ->{
                        _state.value = CoinListState(isLoading = true)
                    }
                    else ->{
                        _state.value = CoinListState(error = result.message?:"Unexpected error")
                    }
                }
            }
        }
    }
}
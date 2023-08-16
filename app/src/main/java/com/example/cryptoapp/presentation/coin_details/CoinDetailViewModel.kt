package com.example.cryptoapp.presentation.coin_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.Constants
import com.example.cryptoapp.common.Resource
import com.example.cryptoapp.domain.use_case.get_coin.GetCoinUseCase
import com.example.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import com.example.cryptoapp.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf<CoinDetailState>(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let{coinId->
            getCoins(coinId)
        }
    }

    private fun getCoins(coinID:String){
        getCoinUseCase.invoke(coinID).onEach {result->
            when(result){
                is Resource.Success ->{
                    _state.value = CoinDetailState(
                        coin = result.data
                    )
                }
                is Resource.Loading ->{
                    _state.value = CoinDetailState(isLoading = true)
                }
                else ->{
                    _state.value = CoinDetailState(error = result.message?:"Unexpected error")
                }
            }
        }.launchIn(viewModelScope)
    }
}
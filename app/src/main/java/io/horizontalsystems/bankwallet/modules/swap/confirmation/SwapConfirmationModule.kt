package io.horizontalsystems.bankwallet.modules.swap.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.bankwallet.core.App
import io.horizontalsystems.bankwallet.core.ethereum.CoinService
import io.horizontalsystems.bankwallet.core.ethereum.EthereumTransactionService
import io.horizontalsystems.bankwallet.core.factories.FeeRateProviderFactory
import io.horizontalsystems.bankwallet.core.providers.EthereumFeeRateProvider
import io.horizontalsystems.bankwallet.core.providers.StringProvider
import io.horizontalsystems.bankwallet.modules.swap.SwapViewItemHelper
import io.horizontalsystems.bankwallet.modules.swap.SwapService
import io.horizontalsystems.bankwallet.modules.swap.SwapTradeService

object SwapConfirmationModule {

    class Factory(private val service: SwapService, private val tradeService: SwapTradeService) : ViewModelProvider.Factory {
        private val ethereumKit by lazy { App.ethereumKitManager.ethereumKit!! }

        private val ethereumCoinService by lazy { CoinService(App.appConfigProvider.ethereumCoin, App.currencyManager, App.xRateManager) }

        private val transactionService by lazy {
            val feeRateProvider = FeeRateProviderFactory.provider(App.appConfigProvider.ethereumCoin) as EthereumFeeRateProvider
            EthereumTransactionService(ethereumKit, feeRateProvider)
        }


        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val stringProvider = StringProvider(App.instance)
            val formatter = SwapViewItemHelper(stringProvider, App.numberFormatter)

            return SwapConfirmationViewModel(service, tradeService, transactionService, ethereumCoinService, App.numberFormatter, formatter, stringProvider) as T
        }
    }

}
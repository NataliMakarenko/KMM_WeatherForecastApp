//
//  ObservableForecastViewModel.swift
//  iosApp
//
//  Created by Natalia Makarenko on 08.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import Combine


class ObservableForecastViewModel: ObservableObject {
    
    private var viewModel: AdoptedMainViewModel?

    @Published
    var loading = false

    @Published
    var forecast: WeatherForecast?
    
    @Published
    var error: String?

    private var cancellables = [AnyCancellable]()

    //private let log = Logger()

    func activate() {
        let viewModel = AdoptedMainViewModel(viewModel: DependenciesProvider.shared.provideMainViewModel())

        doPublish(viewModel.uiState) { [weak self] uiState in
            self?.loading = uiState.isLoading
            self?.forecast = uiState.forecast
            self?.error = uiState.error

            //if let w = dogsState.walletList {
                //self.log.debug("")
              //  self?.log.debug("View updating with \(w.count) breeds")
            //}
            if let errorMessage = uiState.error {
                //self?.log.error("Displaying error: \(errorMessage)")
            }
        }.store(in: &cancellables)

        self.viewModel = viewModel
        viewModel.refreshForecast()
    }

    func deactivate() {
        cancellables.forEach { $0.cancel() }
        cancellables.removeAll()

        viewModel?.clear()
        viewModel = nil
    }
}

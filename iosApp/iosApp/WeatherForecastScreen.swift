//
//  WeatherForecastScreen.swift
//  iosApp
//
//  Created by Natalia Makarenko on 08.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct WeatherForecastScreen: View {
    @StateObject
    var observableModel = ObservableForecastViewModel()
    
    var body: some View {
        ForecastContent(loading: observableModel.loading,
                        forecast: observableModel.forecast,
                        error: observableModel.error)
        .onAppear(perform: {
            observableModel.activate()
        })
        .onDisappear(perform: {
            observableModel.deactivate()
        })
    }
    
}

struct ForecastContent: View {
    var loading: Bool
    var forecast: WeatherForecast?
    var error: String?
    
    var body: some View {
        ZStack {
            VStack {
                if let forecast = forecast {
                    WeatherView(forecast:forecast)
                }
                if let error = error {
                    ErrorContent(error:error)
                }
                
            }
            if loading { Text("Loading...")}
        }
    }
}


struct WeatherView: View{
    var forecast: WeatherForecast
    var body: some View{
        VStack(){
            Text("The weather in\n\(forecast.city), \(forecast.country)")
                .foregroundColor(.blue)
                .bold()
                .font(.title)
                .multilineTextAlignment(.center)
            Spacer()
            HStack{
                
                if let iconCode = forecast.icon {
                    WeatherIcon(iconCode:iconCode)
                }
                
                Spacer()
                VStack(alignment: .leading){
                    Text("Wind \(forecast.wind)").foregroundColor(.blue).fontWeight(.light)
                    Text("Temp \(forecast.temp)").foregroundColor(.blue).fontWeight(.light)
                    Text("Feels like \(forecast.feelsLike)").foregroundColor(.blue).fontWeight(.light)
                    Text("Pressure \(forecast.pressure)").foregroundColor(.blue).fontWeight(.light)
                }
            }
            Spacer()
        }
    }
}
struct ErrorContent: View {
    var error: String
    
    var body: some View {
        VStack{
            //Image() //TODO smth went wrong image
            Text(error)
                .foregroundColor(.red)
        }
    }
}

struct WeatherIcon: View{
    var iconCode: IconCode?
    var body: some View {
        Image(getIconId())
            .resizable()
            .aspectRatio(contentMode: .fit)
            .frame(maxWidth: 100)
        //.frame(width: 30, height: 30)
    }
    
    private func getIconId() -> String{
        let iconStr : String
        if let iconCode = iconCode {
            switch iconCode {
            case .clearSky:
                iconStr = "01d"
            case .fewClouds:
                iconStr = "02d"
            case .scatteredClouds:
                iconStr = "03d"
            case .brokenClouds:
                iconStr = "04d"
            case .showerRain:
                iconStr="09d"
            case .rain:
                iconStr="10d"
            case .thunderstorm:
                iconStr="11d"
            case .snow:
                iconStr="13d"
            case .mist:
                iconStr="50d"
                
            default: iconStr="01d" //TODO add default
                
            }
            return iconStr
        }else{
            return "01d" //TODO add default
        }
        
    }
}



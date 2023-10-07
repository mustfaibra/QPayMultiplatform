//
//  QPayAppView.swift
//  iosApp
//
//  Created by Mustafa Ahmed on 01/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct QPayAppView : UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context){
        
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        Ios_QPayAppKt.MainViewController(
            stringProvider: StringsProvider(),
            koinApplication: KoinAppKt.doInitKoinApplication(platformModules: [])
        )
    }
}

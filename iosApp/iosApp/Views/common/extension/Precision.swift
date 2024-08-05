//
//  Precision.swift
//  iosApp
//
//  Created by Debanshu Datta on 31/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

extension Double {
    func decimals(_ nbr: Int) -> String {
        String(self.formatted(.number.precision(.fractionLength(nbr))))
    }
}

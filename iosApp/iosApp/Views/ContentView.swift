import SwiftUI
import shared

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var activeScreen: Int = 0
    let screens: [Screen] = [
        Screen(name: "Stock", iconSystemName: "chart.bar",screen: AnyView(StockScreen())),
        Screen(name: "Home", iconSystemName: "house",screen: AnyView(HomeView()))
    ]
    
	var body: some View {
        VStack{
            ExpandableNavigationBar(screens[activeScreen].name)
            TabView(selection: $activeScreen) {
                ForEach(screens.indices, id: \.self) { index in
                    screens[index].screen.tabItem {
                        Image(systemName: screens[index].iconSystemName)
                        Text(screens[index].name)
                    }.tag(index)
                }
            }
        }
	}
    
    @ViewBuilder
    func ExpandableNavigationBar(_ title:String) -> some View{
        VStack(spacing: 10){
            HStack{
                Text(title).font(.largeTitle.bold())
                Spacer()
                Image(systemName: "person.fill").font(.body)
            }
            HStack{
                Image(systemName: "magnifyingglass").font(.body)
                TextField("Search stock, mutual funds etc", text: $searchText)
            }
            .frame(height: 45)
            .padding(.horizontal, 10)
            .background{
                RoundedRectangle(cornerRadius: 20.0)
                    .fill(.bar)
            }
        }.safeAreaPadding(.horizontal,10)
    }
}

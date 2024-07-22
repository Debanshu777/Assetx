import SwiftUI
import shared

struct ContentView: View {
    @State private var searchText:String = ""
    @State private var activeScreen: Screen = .stock
	var body: some View {
        VStack{
            ExpandableNavigationBar(activeScreen.rawValue)
            TabView(selection: $activeScreen) {
                StockScreen().tabItem {
                    Image(systemName: "chart.bar")
                    Text("Stock")
                }.tag(Screen.stock)
                HomeView().tabItem {
                    Image(systemName: "house")
                    Text("Home")
                }.tag(Screen.home)
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
